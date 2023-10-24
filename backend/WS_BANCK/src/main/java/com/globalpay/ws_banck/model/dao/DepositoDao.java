package com.globalpay.ws_banck.model.dao;

import com.globalpay.ws_banck.model.entity.Deposito;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface DepositoDao extends CrudRepository<Deposito, Integer>, JpaSpecificationExecutor<Deposito> {
    @Query(value = "SELECT c.id_cliente_cuenta, " +
            "CONCAT(c.nombre, ' ', c.apellido_paterno, ' ', c.apellido_materno) AS nombre_completo, " +
            "d.num_cuenta, d.monto, d.fecha " +
            "FROM BA_CLIENTE_CUENTA c " +
            "JOIN BA_DEPOSITO d ON c.id_cliente_cuenta = d.id_cliente " +
            "WHERE c.id_cliente_cuenta = :idCliente",
            nativeQuery = true)
    List<Map<String, Object>> findDepositsForClient(@Param("idCliente") String idCliente);
    @Query(value = "SELECT CONCAT(c.nombre, ' ', c.apellido_paterno, ' ', c.apellido_materno) AS nombre_completo, " +
            "d.monto AS deposito_monto, d.fecha, d.num_cuenta " +
            "FROM BA_CLIENTE_CUENTA c " +
            "JOIN BA_DEPOSITO d ON c.id_cliente_cuenta = d.id_cliente " +
            "WHERE c.nombre LIKE CONCAT('%', :nombre, '%') OR " +
            "c.apellido_paterno LIKE CONCAT('%', :nombre, '%') OR " +
            "c.apellido_materno LIKE CONCAT('%', :nombre, '%')",
            nativeQuery = true)
    List<Map<String,Object>> findDepositsByPartialName(@Param("nombre") String nombre);

}
