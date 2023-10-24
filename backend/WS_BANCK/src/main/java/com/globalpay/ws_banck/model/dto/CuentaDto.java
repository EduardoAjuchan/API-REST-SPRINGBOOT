package com.globalpay.ws_banck.model.dto;

import lombok.*;

import java.io.Serializable;
@Data
@ToString
@Builder
public class CuentaDto implements Serializable {
    private Integer idCuenta;
    private Integer numeroCuenta;
    private Float saldo;
    private Integer idTipoCuenta;
    private Integer idCliente;

}
