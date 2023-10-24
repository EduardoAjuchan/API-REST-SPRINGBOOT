package com.globalpay.ws_banck.service;

import com.globalpay.ws_banck.model.dto.DepositoDto;
import com.globalpay.ws_banck.model.entity.Deposito;

import java.util.List;

public interface IDepositoService {
    Deposito save(DepositoDto depositoDto);
    Deposito findById(Integer id);
    void delete(Deposito deposito);
    boolean existsById(Integer id);

    List<Deposito> listAllDeposito();
}
