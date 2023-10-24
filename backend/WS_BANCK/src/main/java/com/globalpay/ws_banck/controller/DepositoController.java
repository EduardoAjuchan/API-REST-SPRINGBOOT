package com.globalpay.ws_banck.controller;

import com.globalpay.ws_banck.model.dao.DepositoDao;
import com.globalpay.ws_banck.model.entity.Deposito;
import com.globalpay.ws_banck.model.payload.MensajeResponse;
import com.globalpay.ws_banck.service.IDepositoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class DepositoController {

    @Autowired
    private IDepositoService depositoService;
    @Autowired
    private DepositoDao depositoDao;


    //Obtenr todos los depositos de un cliente por su nombre
    @GetMapping("depositos/nombre/{nombre}")
    public ResponseEntity<?> obtenerDepositosPorNombre(@PathVariable String nombre){
        List<Map<String, Object>> depositos = depositoDao.findDepositsByPartialName(nombre);
        if (depositos.isEmpty()){
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje("No se encontraron depositos para el cliente.")
                            .object(null)
                            .build(),HttpStatus.OK);
        }
        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("Registros encontrados")
                        .object(depositos)
                        .build(),HttpStatus.OK);
    }
    //Obtener todos los depositos de un cliente
    @GetMapping("depositos/{idCliente}")
    public ResponseEntity<?> obtenerDepositosPorCliente(@PathVariable String idCliente){
        List<Map<String, Object>> depositos = depositoDao.findDepositsForClient(idCliente);
        if (depositos.isEmpty()){
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje("No se encontraron depositos para el cliente.")
                            .object(null)
                            .build(),HttpStatus.OK);
        }
        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("Registros encontrados")
                        .object(depositos)
                        .build(),HttpStatus.OK);
    }
    //Metodo para buscar un deposito por su id
    @GetMapping("deposito/{id}")
    public ResponseEntity<?> showById(@PathVariable Integer id){
        Deposito deposito= depositoService.findById(id);
        if (deposito == null){
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje("El registro que intenta buscar, no existe!!")
                            .object(null)
                            .build(),HttpStatus.OK);
        }

        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("Registro encontrado")
                        .object(Deposito.builder()
                                .idDeposito(deposito.getIdDeposito())
                                .monto(deposito.getMonto())
                                .fecha(deposito.getFecha())
                                .numeroCuenta(deposito.getNumeroCuenta())
                                .idCuenta(deposito.getIdCuenta())
                                .idCliente(deposito.getIdCliente())
                                .build()
                        )
                        .build(),HttpStatus.OK);
    }

    //Metodo para listar todos los depositos
    @GetMapping("depositos")
    public ResponseEntity<?> showAll(){
        List<Deposito> getListDeposito = depositoService.listAllDeposito();
        if (getListDeposito == null){
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("No hay registros ")
                    .object(null)
                    .build()
                    , HttpStatus.OK);
        }
        return new ResponseEntity<>(MensajeResponse.builder()
                .mensaje("")
                .object(getListDeposito)
                .build(),HttpStatus.OK);
    }
}
