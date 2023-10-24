package com.globalpay.ws_banck.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


@Data
@ToString
@Builder
public class TransferenciaDto implements Serializable {
    private Integer idTransferencia;
    private Date fecha;
    private Float monto;
    private Integer numCuentaRemitente;
    private Integer numCuentaDestinatario;
    private Integer idTipoTransferencia;
    private Integer idCuentaRemitente;
    private Integer idCuentaDestinatario;


}
