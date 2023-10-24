package com.globalpay.ws_banck.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@Builder
public class TipoCuentaDto implements Serializable {
        private Integer idTipoCuenta;
        private String nombreTipoCuenta;
}
