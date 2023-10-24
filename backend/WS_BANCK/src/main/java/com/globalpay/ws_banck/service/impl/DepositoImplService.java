package com.globalpay.ws_banck.service.impl;

import com.globalpay.ws_banck.model.dao.DepositoDao;
import com.globalpay.ws_banck.model.dto.DepositoDto;
import com.globalpay.ws_banck.model.entity.Deposito;
import com.globalpay.ws_banck.service.IDepositoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepositoImplService implements IDepositoService {

    @Autowired
    private DepositoDao depositoDao;

    @Override
    public Deposito save(DepositoDto depositoDto) {
       Deposito deposito = Deposito.builder()
                .idDeposito(depositoDto.getIdDeposito())
                .monto(depositoDto.getMonto())
                .fecha(depositoDto.getFecha())
                .numeroCuenta(depositoDto.getNumeroCuenta())
                .idCuenta(depositoDto.getIdCuenta())
                .idCliente(depositoDto.getIdCliente())
                .build();
        return depositoDao.save(deposito);
    }


    @Override
    public Deposito findById(Integer id) {
        return depositoDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Deposito deposito) {
        depositoDao.delete(deposito);
    }

    @Override
    public boolean existsById(Integer id) {
        return depositoDao.existsById(id);
    }

    @Override
    public List<Deposito> listAllDeposito() {
        return (List) depositoDao.findAll();
    }
}
