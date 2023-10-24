package com.globalpay.ws_banck.service;

import com.globalpay.ws_banck.model.dto.BancoDto;
import com.globalpay.ws_banck.model.entity.Banco;

import java.util.List;

public interface IBancoService {

    List<Banco> listAllBanco();
    Banco save(BancoDto banco);
    Banco findById(Integer id);
    void delete(Banco banco);
    boolean existsById(Integer id);
}
