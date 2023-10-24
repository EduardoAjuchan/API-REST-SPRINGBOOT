package com.globalpay.ws_banck.model.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Date;
@Data
@ToString
@Builder
public class DepositoDto implements Serializable {
        private Integer idDeposito;
        private Integer numeroCuenta;
        private Double monto;
        private Date fecha;
        private Integer idCuenta;
        private Integer idCliente;

}
