package com.globalpay.ws_banck.service.impl;

import com.globalpay.ws_banck.model.dao.CuentaDao;
import com.globalpay.ws_banck.model.entity.Cuenta;
import com.globalpay.ws_banck.service.IMovimientosService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovimientosImplService implements IMovimientosService {
    @Autowired
    private CuentaDao cuentaDao;
    @Override
    public Cuenta debitar(Integer numeroCuenta, float monto) {
        Cuenta cuenta = cuentaDao.findByNumeroCuenta(numeroCuenta);
        if (cuenta != null){
            Float saldoActual = cuenta.getSaldo();
            if (saldoActual < monto){
                return null;
            }else {
                cuenta.setSaldo(saldoActual - monto);
                return cuentaDao.save(cuenta);
            }
        }else {
            return null;
        }
    }
}
