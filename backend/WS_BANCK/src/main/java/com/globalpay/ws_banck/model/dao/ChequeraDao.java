package com.globalpay.ws_banck.model.dao;

import com.globalpay.ws_banck.model.entity.Chequera;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


public interface ChequeraDao extends CrudRepository<Chequera,Integer> {
}
