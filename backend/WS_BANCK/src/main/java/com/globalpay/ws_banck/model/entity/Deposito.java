package com.globalpay.ws_banck.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "BA_DEPOSITO")
public class Deposito implements Serializable {
    @Id
    @Column(name = "id_deposito")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDeposito;
    @Column(name = "num_cuenta")
    private Integer numeroCuenta;
    @Column(name = "monto")
    private Double monto;
    @Column(name = "fecha")
    private Date fecha;
    @Column(name = "id_cuenta")
    private Integer idCuenta;
    @Column (name = "id_cliente")
    private Integer idCliente;
}
