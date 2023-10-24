package com.globalpay.ws_banck.service;

import com.globalpay.ws_banck.model.dto.CuentaDto;
import com.globalpay.ws_banck.model.entity.Cuenta;
import com.globalpay.ws_banck.model.entity.Deposito;
import com.globalpay.ws_banck.model.entity.Transferencia;

import java.math.BigDecimal;
import java.util.List;

public interface ICuentaService {
    List<Cuenta> lisAllCuenta();
    Cuenta save(CuentaDto cuenta);
    Cuenta findById(Integer id);
    void dele(Cuenta cuenta);
    boolean existsById(Integer id);
    Deposito depositar(Integer numeroCuenta, Float monto);
    Transferencia transferir(Integer cuentaRemitente, Integer cuentaDestinatario, Float monto, Integer idTipoTransferencia);

}
