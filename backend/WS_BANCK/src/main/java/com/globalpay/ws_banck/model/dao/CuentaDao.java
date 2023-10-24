package com.globalpay.ws_banck.model.dao;

import com.globalpay.ws_banck.model.entity.Cuenta;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface CuentaDao extends CrudRepository<Cuenta,Integer> {
    Cuenta findByNumeroCuenta(Integer idCuenta);


    boolean existsByNumeroCuenta(Integer numeroCuenta);
    @Query(value = "SELECT CONCAT(c.nombre, ' ', c.apellido_paterno, ' ', c.apellido_materno) AS nombre_completo, " +
            "a.saldo " +
            "FROM BA_CLIENTE_CUENTA c " +
            "JOIN BA_CUENTA a ON c.id_cliente_cuenta = a.id_cliente " +
            "WHERE a.saldo > :saldo",
            nativeQuery = true)
    List<Map<String,Object>> findAccountsWithBalanceGreaterThan(@Param("saldo") Float saldo);

    @Query(value = "SELECT c.id_cuenta, c.numero_cuenta, tc.nombre AS tipo_cuenta, c.saldo " +
            "FROM BA_CUENTA c " +
            "JOIN BA_TIPO_CUENTA tc ON c.id_tipo_cuenta = tc.id_tipo_cuenta " +
            "WHERE c.id_cliente = :idCliente",
            nativeQuery = true)
    List<Map<String,Object>> findAccountsByClientId(@Param("idCliente") Integer idCliente);

    @Query(value = "SELECT CONCAT(c.nombre, ' ', c.apellido_paterno, ' ', c.apellido_materno) AS nombre_completo, " +
            "d.nombre AS tipo_cuenta, e.saldo AS saldo," +
            "e.numero_cuenta AS numero_cuenta " +
            "FROM BA_CUENTA e " +
            "JOIN BA_CLIENTE_CUENTA c ON c.id_cliente_cuenta = e.id_cliente " +
            "JOIN BA_TIPO_CUENTA d ON d.id_tipo_cuenta = e.id_tipo_cuenta " +
            "WHERE e.id_cliente = :idCliente",
            nativeQuery = true)
    List<Map<String,Object>> findsAccontsByIdCliente(@Param("idCliente") Integer idCliente);

}


