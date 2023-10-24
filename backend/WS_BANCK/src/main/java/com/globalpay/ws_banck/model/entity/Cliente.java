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
@Table(name = "BA_CLIENTE_CUENTA")
public class Cliente implements Serializable {
    @Id
    @Column(name = "id_cliente_cuenta")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCliente;
    @Column(name = "nombre")
    private String nombreCliente;
    @Column(name = "apellido_paterno")
    private String apellidoPaterno;
    @Column(name = "apellido_materno")
    private String apellidoMaterno;
    @Column(name = "edad")
    private Integer edad;
    @Column(name = "id_banco")
    private Integer fkBanco;
    @Column(name = "id_login_user")
    private Integer fkLoginUser;
}
