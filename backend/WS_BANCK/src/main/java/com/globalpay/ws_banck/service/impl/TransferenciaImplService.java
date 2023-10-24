package com.globalpay.ws_banck.service.impl;

import com.globalpay.ws_banck.model.dao.TransferenciaDao;
import com.globalpay.ws_banck.model.dto.TransferenciaDto;
import com.globalpay.ws_banck.model.entity.Transferencia;
import com.globalpay.ws_banck.service.ITransferenciaService;
import jakarta.transaction.Transactional;
import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransferenciaImplService implements ITransferenciaService {
    @Autowired
    private TransferenciaDao transferenciaDao;

    @Override
    public Transferencia save(TransferenciaDto transferenciaDto) {
        Transferencia transferencia = Transferencia.builder()
                .idTransferencia(transferenciaDto.getIdTransferencia())
                .monto(transferenciaDto.getMonto())
                .fecha(transferenciaDto.getFecha())
                .numCuentaDestinatario(transferenciaDto.getNumCuentaDestinatario())
                .numCuentaRemitente(transferenciaDto.getNumCuentaRemitente())
                .idTipoTransferencia(transferenciaDto.getIdTipoTransferencia())
                .idCuentaRemitente(transferenciaDto.getIdCuentaRemitente())
                .idCuentaDestinatario(transferenciaDto.getIdCuentaDestinatario())
                .build();
        return transferenciaDao.save(transferencia);
    }

    @Override
    public Transferencia findById(Integer id) {
        return transferenciaDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Transferencia transferencia) {
    transferenciaDao.delete(transferencia);
    }

    @Override
    public boolean existsById(Integer id) {
        return transferenciaDao.existsById(id);
    }

    @Override
    public List<Transferencia> listAllTransferencia() {
        return (List) transferenciaDao.findAll();
    }
}