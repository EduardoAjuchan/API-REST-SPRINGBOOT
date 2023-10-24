package com.globalpay.ws_banck.model.jap;

import com.globalpay.ws_banck.model.entity.Cheque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ChequeJap extends JpaRepository<Cheque, Long> {
    @Query("SELECT c.idCheque FROM Cheque c WHERE c.numeroCuenta = :numeroCuenta and c.numeroCheque = :numeroCheque")
    BigDecimal encontrarIdCheque(@Param("numeroCuenta") BigDecimal numeroCuenta, @Param("numeroCheque") BigDecimal numeroCheque);

    @Query("select c.estado from Cheque c where  c.idCheque = :idCheque")
    String estado(@Param("idCheque") BigDecimal idCheque);
}
