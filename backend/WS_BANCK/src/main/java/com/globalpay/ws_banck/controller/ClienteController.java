package com.globalpay.ws_banck.controller;

import com.globalpay.ws_banck.model.dto.ClienteDto;
import com.globalpay.ws_banck.model.entity.Cliente;
import com.globalpay.ws_banck.model.payload.MensajeResponse;
import com.globalpay.ws_banck.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ClienteController {

    @Autowired
    private IClienteService clienteService;
    @GetMapping("clientes")
    public ResponseEntity<?> showAll(){
        List<Cliente> getListCliente = clienteService.listAllCliente();
        if (getListCliente == null){
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje("No hay registros!!")
                            .object(null)
                            .build(),HttpStatus.OK);
        }

        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("")
                        .object(getListCliente)
                        .build(),HttpStatus.OK);
    }

    //Insertar un nuevo registro
    @PostMapping("cliente")
    public ResponseEntity<?> create(@RequestBody ClienteDto clienteDto){
        Cliente clienteSave = null;
        try {
            clienteSave = clienteService.save(clienteDto);
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("Guardado Correctamente")
                    .object(ClienteDto.builder()
                            .idCliente(clienteSave.getIdCliente())
                            .nombreCliente(clienteSave.getNombreCliente())
                            .apellidoPaterno(clienteSave.getApellidoPaterno())
                            .apellidoMaterno(clienteSave.getApellidoMaterno())
                            .edad(clienteSave.getEdad())
                            .fkBanco(clienteSave.getFkBanco())
                            .fkLoginUser(clienteSave.getFkLoginUser())
                            .build()
                    )
                    .build()
                    , HttpStatus.CREATED);
        }catch (DataAccessException exDt){
            return new ResponseEntity<>(MensajeResponse.builder()
                            .mensaje(exDt.getMessage())
                            .object(null)
                            .build()
                    ,HttpStatus.METHOD_NOT_ALLOWED
            );
        }
    }
    //Actualizar un registro
    @PutMapping("cliente/{id}")
    public ResponseEntity<?>update(@RequestBody ClienteDto clienteDto, @PathVariable Integer id){
        Cliente clienteUpdate = null;

        try {
            if (clienteService.existsById(id)){
                clienteDto.setIdCliente(id);
                clienteUpdate = clienteService.save(clienteDto);
                return new ResponseEntity<>(MensajeResponse.builder()
                        .mensaje("Actualizado Correctamente")
                        .object(ClienteDto.builder()
                                .idCliente(clienteUpdate.getIdCliente())
                                .nombreCliente(clienteUpdate.getNombreCliente())
                                .apellidoPaterno(clienteUpdate.getApellidoPaterno())
                                .apellidoMaterno(clienteUpdate.getApellidoMaterno())
                                .edad(clienteUpdate.getEdad())
                                .fkBanco(clienteUpdate.getFkBanco())
                                .build()
                        )
                        .build()
                        ,HttpStatus.CREATED);
            }else {
                return new ResponseEntity<>(MensajeResponse.builder()
                                .mensaje("El registro que intenta actualizar no se encuentra en la base de datos")
                                .object(null)
                                .build()
                        ,HttpStatus.NOT_FOUND
                );
            }
        }catch (DataAccessException exDt){
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje("El registro que intenta actualizar no se encuentra en la base de datos")
                            .object(null)
                            .build()
                    ,HttpStatus.METHOD_NOT_ALLOWED);

        }
    }

    //Eliminar un registro
    @DeleteMapping("cliente/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){

        try {
            Cliente clienteDelete = clienteService.findById(id);
            clienteService.delete(clienteDelete);
            return new ResponseEntity<>(clienteDelete,HttpStatus.NO_CONTENT);
        }catch (DataAccessException exDt){
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje("El registro que intenta eliminar no existe")
                            .object(null)
                            .build()
                    ,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Obtener un registro
    @GetMapping("cliente/{id}")
    public ResponseEntity<?> showById(@PathVariable Integer id){
       Cliente cliente = clienteService.findById(id);
        if (cliente == null){
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje("El registro que intenta buscar, no existe!!")
                            .object(null)
                            .build(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("Registro encontrado")
                        .object(ClienteDto.builder()
                                .idCliente(cliente.getIdCliente())
                                .nombreCliente(cliente.getNombreCliente())
                                .apellidoPaterno(cliente.getApellidoPaterno())
                                .apellidoMaterno(cliente.getApellidoMaterno())
                                .edad(cliente.getEdad())
                                .fkBanco(cliente.getFkBanco())
                                .build()
                        )
                        .build(),HttpStatus.OK);
    }
}
