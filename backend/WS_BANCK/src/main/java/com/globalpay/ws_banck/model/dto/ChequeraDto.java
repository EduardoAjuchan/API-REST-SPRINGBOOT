package com.globalpay.ws_banck.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@Builder
public class ChequeraDto implements Serializable {
    private Integer idChequera;
    private Integer idCuenta;
    private Integer cantidad;
    private String estado;
    private Integer acumulado;
}
