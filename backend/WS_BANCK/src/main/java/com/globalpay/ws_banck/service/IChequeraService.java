package com.globalpay.ws_banck.service;

import com.globalpay.ws_banck.model.dto.ChequeDto;
import com.globalpay.ws_banck.model.dto.ChequeraDto;
import com.globalpay.ws_banck.model.entity.Cheque;
import com.globalpay.ws_banck.model.entity.Chequera;
import com.globalpay.ws_banck.model.entity.Cuenta;

import java.math.BigDecimal;
import java.util.List;

public interface IChequeraService {
    List<Chequera> listAllChequera();
    Chequera save(Integer idCuenta, Integer cantidad, String estado);
    Chequera findByID(Integer id);
    BigDecimal encontrarMaximoDeMiCampo(BigDecimal idCuenta);
    Cheque saveCheque(Integer idCuenta, Integer idCheque, Integer cantidad, Integer acumulado);
    BigDecimal numeroCuenta(BigDecimal idCuenta);
    BigDecimal idChequera(BigDecimal idCuenta, BigDecimal acumulado);
}
