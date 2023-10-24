package com.globalpay.ws_banck.model.jap;

import com.globalpay.ws_banck.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
@Repository
public interface ClienteJpa extends JpaRepository<Cliente, Long> {
    @Query("select c.idCliente from Cliente c where c.fkLoginUser =  :id_login_user")
    BigDecimal findIdClienteCuentaByIdLoginUser(@Param("id_login_user") BigDecimal id_login_user);
}
