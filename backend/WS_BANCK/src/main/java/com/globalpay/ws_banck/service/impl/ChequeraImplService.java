package com.globalpay.ws_banck.service.impl;

import com.globalpay.ws_banck.model.dao.ChequeDao;
import com.globalpay.ws_banck.model.dao.ChequeraDao;
import com.globalpay.ws_banck.model.dto.ChequeDto;
import com.globalpay.ws_banck.model.dto.ChequeraDto;
import com.globalpay.ws_banck.model.entity.Cheque;
import com.globalpay.ws_banck.model.entity.Chequera;
import com.globalpay.ws_banck.model.jap.ChequeJap;
import com.globalpay.ws_banck.model.jap.ChequeraJap;
import com.globalpay.ws_banck.model.jap.CuentaJap;
import com.globalpay.ws_banck.service.IChequeraService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ChequeraImplService implements IChequeraService {
    @Autowired
    private ChequeraDao chequeraDao;
    @Autowired
    private ChequeDao chequeDao;
    @Autowired
    private CuentaJap cuentaJap;
    @Override
    public List<Chequera> listAllChequera() {
        return (List) chequeraDao.findAll();
    }

    @Override
    public Chequera save(Integer idCuenta, Integer cantidad, String estado) {
        BigDecimal acumulado2 = encontrarMaximoDeMiCampo(BigDecimal.valueOf(idCuenta));
        int acumulado = 0;
        int acumuladDB = 0;
        if (acumulado2 != null) {
            acumulado = acumulado2.intValue();
        }
        acumuladDB = acumulado+cantidad;
        Chequera chequera = Chequera.builder()
                .idCuenta(idCuenta)
                .cantidad(cantidad)
                .estado(estado)
                .acumulado(acumuladDB)
                .build();
        return chequeraDao.save(chequera);
    }

    @Override
    public Chequera findByID(Integer id) {
        return chequeraDao.findById(id).orElse(null);
    }

    @Autowired
    private ChequeraJap chequreaJap;
    @Override
    public BigDecimal encontrarMaximoDeMiCampo(BigDecimal idCuenta) {
        return chequreaJap.encontrarMaximoDeAcumuladoPorIdCuenta(idCuenta);
    }

    @Override
    public Cheque saveCheque(Integer idCuenta,Integer idCheque, Integer cantidad, Integer acumulado) {
        BigDecimal numeroCuentaDB = numeroCuenta(BigDecimal.valueOf(idCuenta));
        Integer numeroCuenta = numeroCuentaDB.intValue();
        BigDecimal idChequeraDB = idChequera(BigDecimal.valueOf(idCuenta),BigDecimal.valueOf(acumulado));
        Integer idChequera = idChequeraDB.intValue();
        Integer inicio = 1;
        if (acumulado != 0) {
            inicio += acumulado - cantidad;
        }
        for (int i = 0; i < cantidad; i++) {
            Cheque cheque = Cheque.builder()
                    .numeroCheque(inicio+i)
                    .numeroCuenta(numeroCuenta)
                    .idChequera(idChequera)
                    .beneficiario(null)
                    .monto(null)
                    .estado("Disponible")
                    .fechaCobro(null)
                    .build();
            chequeDao.save(cheque);
        }
        return null;
    }

    @Override
    public BigDecimal numeroCuenta(BigDecimal idCuenta) {
        return cuentaJap.numeroCuenta(idCuenta);
    }

    @Override
    public BigDecimal idChequera(BigDecimal idCuenta, BigDecimal acumulado) {
        return chequreaJap.idChequera(idCuenta,acumulado);
    }
}
