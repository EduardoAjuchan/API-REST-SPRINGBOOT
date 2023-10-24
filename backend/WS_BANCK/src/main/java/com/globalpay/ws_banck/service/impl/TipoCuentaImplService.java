package com.globalpay.ws_banck.service.impl;

import com.globalpay.ws_banck.model.dao.TipoCuentaDao;
import com.globalpay.ws_banck.model.dto.TipoCuentaDto;
import com.globalpay.ws_banck.model.entity.TipoCuenta;
import com.globalpay.ws_banck.service.ITipoCuentaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class TipoCuentaImplService implements ITipoCuentaService {
    @Autowired
    private TipoCuentaDao tipoCuentaDao;
    @Transactional
    @Override
    public TipoCuenta save(TipoCuentaDto tipoCuentaDto) {
        TipoCuenta tipoCuenta = TipoCuenta.builder()
                .idTipoCuenta(tipoCuentaDto.getIdTipoCuenta())
                .nombreTipoCuenta(tipoCuentaDto.getNombreTipoCuenta())
                .build();
        return tipoCuentaDao.save(tipoCuenta);
    }
    @Transactional
    @Override
    public TipoCuenta findById(Integer id) {
        return tipoCuentaDao.findById(id).orElse(null);
    }

    @Override
    public void delete(TipoCuenta tipoCuenta) {
    tipoCuentaDao.delete(tipoCuenta);
    }

    @Override
    public boolean existsById(Integer id) {
        return tipoCuentaDao.existsById(id);
    }

    @Override
    public List<TipoCuenta> listAllTipoCuenta() {
        return (List) tipoCuentaDao.findAll();
    }



}
