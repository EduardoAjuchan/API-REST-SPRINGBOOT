package com.globalpay.ws_banck.service.impl;

import com.globalpay.ws_banck.model.dao.CuentaDao;
import com.globalpay.ws_banck.model.dao.DepositoDao;
import com.globalpay.ws_banck.model.dao.TransferenciaDao;
import com.globalpay.ws_banck.model.dto.CuentaDto;
import com.globalpay.ws_banck.model.entity.Cuenta;
import com.globalpay.ws_banck.model.jap.CuentaJap;
import com.globalpay.ws_banck.model.entity.Deposito;
import com.globalpay.ws_banck.model.entity.Transferencia;
import com.globalpay.ws_banck.service.ICuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class CuentaImplService implements ICuentaService {
    @Autowired
    private CuentaDao cuentaDao;
    @Autowired
    private TransferenciaDao transferenciaDao;
    @Autowired
    DepositoDao depositoDao;

    // Genera un número de cuenta único como Integer
    private Integer generarNumeroCuentaUnico() {
        Random random = new Random();
        Integer numeroCuenta;

        // Genera números de cuenta hasta obtener uno único
        do {
            numeroCuenta = random.nextInt(1000000000); // Genera un número de 0 a 999,999,999
        } while (cuentaDao.existsByNumeroCuenta(numeroCuenta));

        return numeroCuenta;
    }

    @Override
    public List<Cuenta> lisAllCuenta() {
        return (List) cuentaDao.findAll();
    }

    @Override
    public Cuenta save(CuentaDto cuentaDto) {
        Integer numeroCuenta = generarNumeroCuentaUnico();
        Cuenta cuenta = Cuenta.builder()
                .idCuenta(cuentaDto.getIdCuenta())
                .numeroCuenta(cuentaDto.getNumeroCuenta())
                .saldo(cuentaDto.getSaldo())
                .numeroCuenta(numeroCuenta)
                .idTipoCuenta(cuentaDto.getIdTipoCuenta())
                .idCliente(cuentaDto.getIdCliente())
                .saldo(cuentaDto.getSaldo())
                .build();
        return cuentaDao.save(cuenta);
    }

    @Override
    public Cuenta findById(Integer id) {
        return cuentaDao.findById(id).orElse(null);
    }

    @Override
    public void dele(Cuenta cuenta) {
        cuentaDao.delete(cuenta);
    }

    @Override
    public boolean existsById(Integer id) {
        return cuentaDao.existsById(id);
    }
    //Deposito
    @Override
    public Deposito depositar(Integer numeroCuenta, Float monto) {
        Cuenta cuenta = cuentaDao.findByNumeroCuenta(numeroCuenta);

        if (cuenta != null) {
            cuenta.setSaldo(cuenta.getSaldo() + monto);
            cuentaDao.save(cuenta);

            // Registrar el depósito en la entidad Deposito
            Deposito deposito = new Deposito();
            deposito.setNumeroCuenta(numeroCuenta);
            deposito.setMonto(Double.valueOf(monto));
            deposito.setFecha(new Date());
            deposito.setIdCuenta(cuenta.getIdCuenta());
            deposito.setIdCliente(cuenta.getIdCliente());
            depositoDao.save(deposito);

            return deposito;
        } else {
            return null; // Depósito fallido, cuenta inválida
        }
    }


    //Tranferencias
    @Override
    public Transferencia transferir(Integer cuentaRemitente, Integer cuentaDestinatario, Float monto, Integer idTipoTransferencia) {
        Cuenta cuentaOrigen = cuentaDao.findByNumeroCuenta(cuentaRemitente);
        Cuenta cuentaDestino = cuentaDao.findByNumeroCuenta(cuentaDestinatario);

        if (cuentaOrigen != null && cuentaDestino != null && cuentaOrigen.getSaldo() >= monto) {
            cuentaOrigen.setSaldo(cuentaOrigen.getSaldo() - monto);
            cuentaDestino.setSaldo(cuentaDestino.getSaldo() + monto);
            cuentaDao.save(cuentaOrigen);
            cuentaDao.save(cuentaDestino);

            // Registrar la transferencia en la entidad Transferencia
            Transferencia transferencia = new Transferencia();
            transferencia.setNumCuentaRemitente(cuentaRemitente);
            transferencia.setNumCuentaDestinatario(cuentaDestinatario);
            transferencia.setMonto(monto);
            transferencia.setFecha(new Date());
            transferencia.setIdTipoTransferencia(idTipoTransferencia);
            transferencia.setIdCuentaRemitente(cuentaOrigen.getIdCuenta());
            transferencia.setIdCuentaDestinatario(cuentaDestino.getIdCuenta());
            transferenciaDao.save(transferencia);

            return transferencia;
        } else {
            return null; // Transferencia fallida, saldo insuficiente o cuentas inválidas
        }
    }


}
