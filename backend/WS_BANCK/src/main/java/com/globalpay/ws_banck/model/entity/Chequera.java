package com.globalpay.ws_banck.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "BA_CHEQUERA")
public class Chequera implements Serializable{
    @Id
    @Column(name = "id_chequera")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idChequera;
    @Column(name = "id_cuenta")
    private Integer idCuenta;
    @Column(name = "cantidad")
    private Integer cantidad;
    @Column(name = "estado")
    private String estado;
    @Column(name = "acumulado")
    private  Integer acumulado;
}