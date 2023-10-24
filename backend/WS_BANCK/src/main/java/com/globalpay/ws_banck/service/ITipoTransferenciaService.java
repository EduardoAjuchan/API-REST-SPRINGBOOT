package com.globalpay.ws_banck.service;

import com.globalpay.ws_banck.model.dto.TipoTransferenciaDto;
import com.globalpay.ws_banck.model.entity.TipoTransferencia;

import java.util.List;

public interface ITipoTransferenciaService {
    List<TipoTransferencia> listAllTipoTransferencia();
    TipoTransferencia save(TipoTransferenciaDto tipoTransferenciaDto);
    TipoTransferencia findById(Integer id);
    void delete(TipoTransferencia tipoTransferencia);
    boolean existsById(Integer id);
}
