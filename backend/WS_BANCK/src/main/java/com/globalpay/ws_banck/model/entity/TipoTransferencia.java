package com.globalpay.ws_banck.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "BA_TIPO_TRANSFERENCIA")
public class TipoTransferencia implements Serializable {
    @Id
    @Column(name = "id_tipo_transferencia")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTipoTransferencia;
    @Column(name = "nombre")
    private String nombreTransferencia;
}
