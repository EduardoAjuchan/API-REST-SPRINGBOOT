package com.globalpay.ws_banck.service.impl;

import com.globalpay.ws_banck.model.dao.BancoDao;
import com.globalpay.ws_banck.model.dto.BancoDto;
import com.globalpay.ws_banck.model.entity.Banco;
import com.globalpay.ws_banck.service.IBancoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BancoImplService implements IBancoService {

    @Autowired
    private BancoDao bancoDao;

    @Override
    public List<Banco> listAllBanco() {

        return (List) bancoDao.findAll();
    }

    @Transactional
    @Override
    public Banco save(BancoDto bancoDto) {
        Banco banco = Banco.builder()
                .idBanco(bancoDto.getIdBanco())
                .nombreBanco(bancoDto.getNombreBanco())
                .build();
        return bancoDao.save(banco);
    }
    @Transactional
    @Override
    public Banco findById(Integer id) {
        return bancoDao.findById(id).orElse(null);
    }
    @Transactional
    @Override
    public void delete(Banco banco) {
        bancoDao.delete(banco);
    }

    @Override
    public boolean existsById(Integer id) {

        return bancoDao.existsById(id);
    }
}
