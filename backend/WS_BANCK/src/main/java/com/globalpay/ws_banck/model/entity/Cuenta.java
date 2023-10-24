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
@Table(name = "BA_CUENTA")
public class Cuenta implements Serializable {
    @Id
    @Column(name = "id_cuenta")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCuenta;
    @Column(name = "numero_cuenta")
    private Integer numeroCuenta;
    @Column(name = "saldo")
    private Float saldo;
    @Column(name = "id_tipo_cuenta")
    private Integer idTipoCuenta;
    @Column(name = "id_cliente")
    private Integer idCliente;
}
