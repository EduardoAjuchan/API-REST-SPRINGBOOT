package com.globalpay.ws_banck.model.entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name ="BA_CHEQUE" )
public class Cheque implements Serializable{
    @Id
    @Column(name = "id_cheque")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCheque;
    @Column(name = "numero_cheque")
    private Integer numeroCheque;
    @Column(name = "numero_cuenta")
    private Integer numeroCuenta;
    @Column(name = "id_chequera")
    private Integer idChequera;
    @Temporal(TemporalType.DATE)
    @CreationTimestamp
    @Column(name = "fecha_emision")
    private Date fechaEmision;
    @Column(name = "beneficiario")
    private String beneficiario;
    @Column(name = "monto")
    private Float monto;
    @Column(name = "estado")
    private String estado;
    @Column(name = "fecha_cobro")
    private Date fechaCobro;
}
