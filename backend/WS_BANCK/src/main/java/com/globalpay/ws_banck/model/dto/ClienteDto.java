package com.globalpay.ws_banck.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
@Data
@ToString
@Builder
public class ClienteDto implements Serializable {

    private Integer idCliente;
    private String nombreCliente;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private Integer edad;
    private Integer fkBanco;
    private Integer fkLoginUser;
}
