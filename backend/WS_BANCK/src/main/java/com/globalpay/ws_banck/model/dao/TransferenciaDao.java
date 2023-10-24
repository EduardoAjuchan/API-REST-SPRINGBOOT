package com.globalpay.ws_banck.model.dao;

import com.globalpay.ws_banck.model.entity.Transferencia;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Map;

public interface TransferenciaDao extends CrudRepository<Transferencia,Integer>{

    //Obtener todos los usuarios que realizaron transferencias
    @Query(value = "SELECT DISTINCT CONCAT(c.nombre, ' ', c.apellido_paterno, ' ', c.apellido_materno) AS nombre_completo, " +
            "t.monto AS monto_transferido, t.fecha " +
            "FROM BA_CLIENTE_CUENTA c " +
            "JOIN BA_TRANSFERENCIA t ON c.id_cliente_cuenta = t.id_cuenta_remitente",
            nativeQuery = true)
    List<Map<String, Object>> findUsersWithTransfers();

    //Obtener todos los usuarios que recibieron transferencias
    @Query(value = "SELECT DISTINCT CONCAT(c.nombre, ' ', c.apellido_paterno, ' ', c.apellido_materno) AS nombre_completo, " +
            "t.monto AS monto_recibido, t.fecha " +
            "FROM BA_CLIENTE_CUENTA c " +
            "JOIN BA_TRANSFERENCIA t ON c.id_cliente_cuenta = t.id_cuenta_destinatario",
            nativeQuery = true)
    List<Map<String, Object>> findUsersWithTransfersReceived();
}
