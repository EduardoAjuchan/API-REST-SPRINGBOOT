package com.globalpay.ws_banck.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name ="BA_TIPO_CUENTA" )
public class TipoCuenta implements Serializable {
    @Id
    @Column(name = "id_tipo_cuenta")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTipoCuenta;
    @Column(name = "nombre")
    private String nombreTipoCuenta;
}
