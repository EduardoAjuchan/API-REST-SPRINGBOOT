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
@Table(name ="BA_TRANSFERENCIA" )
public class Transferencia implements Serializable {
    @Id
    @Column(name = "id_transferencia")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTransferencia;
    @Column(name = "fecha")
    private Date fecha;
    @Column(name = "monto")
    private Float monto;
    @Column(name="num_cuenta_remitente")
    Integer numCuentaRemitente;
    @Column(name="num_cuenta_destinatario")
    Integer numCuentaDestinatario;
    @Column(name = "id_tipo_transferencia")
    private Integer idTipoTransferencia;
    @Column(name = "id_cuenta_remitente")
    private Integer idCuentaRemitente;
    @Column(name = "id_cuenta_destinatario")
    private Integer idCuentaDestinatario;

}
