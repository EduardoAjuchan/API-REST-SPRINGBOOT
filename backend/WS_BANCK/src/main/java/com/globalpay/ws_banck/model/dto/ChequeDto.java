package com.globalpay.ws_banck.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
@Builder
public class ChequeDto implements Serializable {
    private Integer idCheque;
    private Integer numeroCheque;
    private Integer numeroCuenta;
    private Integer idChequera;
    private Date fechaEmision;
    private String beneficiario;
    private Float monto;
    private String estado;
    private Date fechaCobro;
}
