package com.globalpay.ws_banck.model.dto;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@ToString
@Builder
public class BancoDto implements Serializable {
    private Integer idBanco;
    private String nombreBanco;
}
