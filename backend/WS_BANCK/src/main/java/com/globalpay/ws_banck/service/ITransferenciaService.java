package com.globalpay.ws_banck.service;

import com.globalpay.ws_banck.model.dto.TransferenciaDto;
import com.globalpay.ws_banck.model.entity.Transferencia;

import java.util.List;

public interface ITransferenciaService {
    Transferencia save(TransferenciaDto transferencia);
    Transferencia findById(Integer id);
    void delete(Transferencia transferencia);
    boolean existsById(Integer id);

    List<Transferencia> listAllTransferencia();
}
