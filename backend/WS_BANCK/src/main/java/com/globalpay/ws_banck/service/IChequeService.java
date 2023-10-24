package com.globalpay.ws_banck.service;

import com.globalpay.ws_banck.model.dto.ChequeDto;
import com.globalpay.ws_banck.model.entity.Cheque;

import java.math.BigDecimal;
import java.util.List;

public interface IChequeService {
    List<Cheque> listAllCheque();
    Cheque save(ChequeDto cheque);
    Cheque findById(Integer id);
    void delete(Cheque cheque);
    boolean existsById(Integer id);
    BigDecimal encontrarIdCheque(BigDecimal numeroCuenta, BigDecimal numeroCheque);
    BigDecimal saldo(BigDecimal numeroCuenta);
    String estado(BigDecimal idCheque);
}
