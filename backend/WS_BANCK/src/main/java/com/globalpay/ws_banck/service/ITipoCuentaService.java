package com.globalpay.ws_banck.service;

import com.globalpay.ws_banck.model.dto.TipoCuentaDto;
import com.globalpay.ws_banck.model.entity.Cuenta;
import com.globalpay.ws_banck.model.entity.TipoCuenta;

import java.util.List;

public interface ITipoCuentaService {
    TipoCuenta save(TipoCuentaDto tipoCuenta);
    TipoCuenta findById(Integer id);
    void delete(TipoCuenta tipoCuenta);
    boolean existsById(Integer id);
    List<TipoCuenta> listAllTipoCuenta();

}
