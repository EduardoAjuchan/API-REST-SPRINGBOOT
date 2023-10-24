package com.globalpay.ws_banck.service.impl;

import com.globalpay.ws_banck.model.dao.TipoTransferenciaDao;
import com.globalpay.ws_banck.model.dto.TipoTransferenciaDto;
import com.globalpay.ws_banck.model.entity.TipoTransferencia;
import com.globalpay.ws_banck.service.ITipoTransferenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TipoTransferenciaImplService implements ITipoTransferenciaService {
    @Autowired
    private TipoTransferenciaDao tipoTransferenciaDao;
    @Override
    public List<TipoTransferencia> listAllTipoTransferencia() {
        return (List) tipoTransferenciaDao.findAll();
    }

    @Override
    public TipoTransferencia save(TipoTransferenciaDto tipoTransferenciaDto) {
        TipoTransferencia tipoTransferenciaSave = TipoTransferencia.builder()
                .idTipoTransferencia(tipoTransferenciaDto.getIdTipoTransferencia())
                .nombreTransferencia(tipoTransferenciaDto.getNombreTransferencia())
                .build();
        return tipoTransferenciaDao.save(tipoTransferenciaSave);
    }

    @Override
    public TipoTransferencia findById(Integer id) {
        return tipoTransferenciaDao.findById(id).orElse(null);
    }

    @Override
    public void delete(TipoTransferencia tipoTransferencia) {
        tipoTransferenciaDao.delete(tipoTransferencia);
    }

    @Override
    public boolean existsById(Integer id) {
        return tipoTransferenciaDao.existsById(id);
    }
}
