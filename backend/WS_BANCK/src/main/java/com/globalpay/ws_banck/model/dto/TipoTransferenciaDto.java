package com.globalpay.ws_banck.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
@Data
@ToString
@Builder
public class TipoTransferenciaDto implements Serializable {
    private Integer idTipoTransferencia;
    private String nombreTransferencia;
}
