package com.globalpay.ws_banck.model.dao;

import com.globalpay.ws_banck.model.entity.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface ClienteDao extends CrudRepository<Cliente,Integer> {
}
