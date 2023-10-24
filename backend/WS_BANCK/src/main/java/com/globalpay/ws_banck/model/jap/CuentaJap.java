package com.globalpay.ws_banck.model.jap;

import com.globalpay.ws_banck.model.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface CuentaJap extends JpaRepository<Cuenta, Long> {
    @Query("select c.saldo from Cuenta c where c.numeroCuenta = :numeroCuenta")
    BigDecimal saldo(@Param("numeroCuenta") BigDecimal numeroCuenta);

    @Query("select c.numeroCuenta from Cuenta c where c.idCuenta = :idCuenta")
    BigDecimal numeroCuenta(@Param("idCuenta") BigDecimal idCuenta);
}
