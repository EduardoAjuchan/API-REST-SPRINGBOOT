package com.globalpay.ws_banck.controller;

import com.globalpay.ws_banck.model.dto.BancoDto;
import com.globalpay.ws_banck.model.entity.Banco;
import com.globalpay.ws_banck.model.payload.MensajeResponse;
import com.globalpay.ws_banck.service.IBancoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1")
public class BancoController {
    @GetMapping("bancos")
    public ResponseEntity<?> showAll(){
         List<Banco> getListBanco = bancoService.listAllBanco();
        if (getListBanco == null){
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje("No hay registros!!")
                            .object(null)
                            .build(),HttpStatus.OK);
        }

        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("")
                        .object(getListBanco)
                        .build(),HttpStatus.OK);
    }
    //Insertar un nuevo banco
    @Autowired
    private IBancoService bancoService;
    @PostMapping("banco")
    public ResponseEntity<?> create(@RequestBody BancoDto bancoDto){
        Banco bancoSave = null;
        try {
            bancoSave = bancoService.save(bancoDto);
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("Guardado Correctamente")
                        .object(BancoDto.builder()
                                .idBanco(bancoSave.getIdBanco())
                                .nombreBanco(bancoSave.getNombreBanco())
                                .build()
                        )
                    .build()
            ,HttpStatus.CREATED);
        }catch (DataAccessException exDt){
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje(exDt.getMessage())
                            .object(null)
                            .build()
                    ,HttpStatus.METHOD_NOT_ALLOWED);
        }
    }
    //Actualizar un banco
    @PutMapping("banco/{id}")
    public ResponseEntity<?>update(@RequestBody BancoDto bancoDto,@PathVariable Integer id){
        Banco bancoUpdate = null;

        try {
            if (bancoService.existsById(id)){
                bancoDto.setIdBanco(id);
                bancoUpdate = bancoService.save(bancoDto);
                return new ResponseEntity<>(MensajeResponse.builder()
                        .mensaje("Actualizado Correctamente")
                        .object(BancoDto.builder()
                                .idBanco(bancoUpdate.getIdBanco())
                                .nombreBanco(bancoUpdate.getNombreBanco())
                                .build()
                        )
                        .build()
                        ,HttpStatus.CREATED);
            }else {
                return new ResponseEntity<>(
                        MensajeResponse.builder()
                                .mensaje("El registro que intenta actualizar no se encuentra en la base de datos")
                                .object(null)
                                .build()
                        ,HttpStatus.NOT_FOUND);
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
    //Eliminar un banco
    @DeleteMapping("banco/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){

        try {
            Banco bancoDelete = bancoService.findById(id);
            bancoService.delete(bancoDelete);
            return new ResponseEntity<>(bancoDelete,HttpStatus.NO_CONTENT);
        }catch (DataAccessException exDt){
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                        .mensaje("El registro que intenta eliminar no existe")
                        .object(null)
                        .build()
                    ,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //Obtener un banco
    @GetMapping("banco/{id}")
    public ResponseEntity<?> showById(@PathVariable Integer id){
        Banco banco= bancoService.findById(id);
        if (banco == null){
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje("El registro que intenta buscar, no existe!!")
                            .object(null)
                            .build(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("Registro encontrado")
                        .object(BancoDto.builder()
                                        .idBanco(banco.getIdBanco())
                                        .nombreBanco(banco.getNombreBanco())
                                        .build()
                        )
                        .build(),HttpStatus.OK);
    }

}
