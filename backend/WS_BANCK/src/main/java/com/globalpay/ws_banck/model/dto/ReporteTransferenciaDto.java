package com.globalpay.ws_banck.model.dto;

import java.util.Date;

public class ReporteTransferenciaDto {
    private String nombreCompleto;
    private Float montoTransferido;
    private Date fecha;

    public ReporteTransferenciaDto(String nombreCompleto, Float montoTransferido, Date fecha) {
        this.nombreCompleto = nombreCompleto;
        this.montoTransferido = montoTransferido;
        this.fecha = fecha;
    }
}
