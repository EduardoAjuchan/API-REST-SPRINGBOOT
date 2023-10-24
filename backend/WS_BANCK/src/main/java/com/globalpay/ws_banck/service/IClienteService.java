package com.globalpay.ws_banck.service;

import com.globalpay.ws_banck.model.dto.ClienteDto;
import com.globalpay.ws_banck.model.entity.Cliente;

import java.util.List;


public interface IClienteService {
    List<Cliente>listAllCliente();
    Cliente save(ClienteDto cliente);
    Cliente findById(Integer id);
    void delete(Cliente cliente);
    boolean existsById(Integer id);
}
