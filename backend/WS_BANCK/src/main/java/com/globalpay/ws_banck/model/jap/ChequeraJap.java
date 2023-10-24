package com.globalpay.ws_banck.model.jap;

import com.globalpay.ws_banck.model.entity.Chequera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ChequeraJap extends JpaRepository<Chequera, Long> {
    @Query("SELECT MAX(c.acumulado) FROM Chequera c WHERE c.idCuenta = :idCuenta")
    BigDecimal encontrarMaximoDeAcumuladoPorIdCuenta(@Param("idCuenta") BigDecimal idCuenta);

    @Query("select c.idChequera from Chequera c where c.idCuenta = :idCuenta and c.acumulado = :acumulado")
    BigDecimal idChequera(@Param("idCuenta") BigDecimal idCuenta, @Param("acumulado") BigDecimal acumulado);
}
