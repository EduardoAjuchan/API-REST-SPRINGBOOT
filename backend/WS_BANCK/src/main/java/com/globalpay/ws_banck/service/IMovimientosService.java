package com.globalpay.ws_banck.service;

import com.globalpay.ws_banck.model.entity.Cuenta;

public interface IMovimientosService{
    Cuenta debitar(Integer numeroCuenta, float monto);
}
