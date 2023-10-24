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
@Table(name ="BA_BANCO" )
public class Banco  implements Serializable {
    @Id
    @Column(name = "id_banco")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idBanco;
    @Column(name = "nombre")
    private String nombreBanco;

}
