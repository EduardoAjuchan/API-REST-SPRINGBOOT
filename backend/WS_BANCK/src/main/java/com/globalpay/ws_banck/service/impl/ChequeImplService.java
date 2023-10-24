package com.globalpay.ws_banck.service.impl;

import com.globalpay.ws_banck.model.dao.ChequeDao;
import com.globalpay.ws_banck.model.dto.ChequeDto;
import com.globalpay.ws_banck.model.entity.Cheque;
import com.globalpay.ws_banck.model.jap.ChequeJap;
import com.globalpay.ws_banck.model.jap.CuentaJap;
import com.globalpay.ws_banck.service.IChequeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ChequeImplService implements IChequeService {

    @Autowired
    private ChequeDao chequeDao;
    @Override
    public List<Cheque> listAllCheque() {
        return (List) chequeDao.findAll();
    }

    @Transactional
    @Override
    public Cheque save(ChequeDto chequeDto) {
        Cheque cheque = Cheque.builder()
                .idCheque(chequeDto.getIdCheque())
                .numeroCheque(chequeDto.getNumeroCheque())
                .numeroCuenta(chequeDto.getNumeroCuenta())
                .idChequera(chequeDto.getIdChequera())
                .fechaEmision(chequeDto.getFechaEmision())
                .beneficiario(chequeDto.getBeneficiario())
                .monto(chequeDto.getMonto())
                .estado(chequeDto.getEstado())
                .fechaCobro(chequeDto.getFechaCobro())
                .build();
        return chequeDao.save(cheque);
    }

    @Override
    public Cheque findById(Integer id) {
        return chequeDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Cheque cheque) {

    }

    @Override
    public boolean existsById(Integer id) {
        return chequeDao.existsById(id);
    }

    @Autowired
    private ChequeJap chequeJap;
    @Override
    public BigDecimal encontrarIdCheque(BigDecimal numeroCuenta, BigDecimal numeroCheque) {
        return chequeJap.encontrarIdCheque(numeroCuenta,numeroCheque);
    }

    @Override
    public String estado(BigDecimal idCheque) {
        return chequeJap.estado(idCheque);
    }

    @Autowired
    private CuentaJap cuentaJap;
    @Override
    public BigDecimal saldo(BigDecimal numeroCuenta) {
        return cuentaJap.saldo(numeroCuenta);
    }

}
