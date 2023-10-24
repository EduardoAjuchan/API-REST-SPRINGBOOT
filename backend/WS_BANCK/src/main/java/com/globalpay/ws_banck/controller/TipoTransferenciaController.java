package com.globalpay.ws_banck.controller;

import com.globalpay.ws_banck.model.dto.CuentaDto;
import com.globalpay.ws_banck.model.dto.TipoTransferenciaDto;
import com.globalpay.ws_banck.model.entity.Cuenta;
import com.globalpay.ws_banck.model.entity.TipoTransferencia;
import com.globalpay.ws_banck.model.payload.MensajeResponse;
import com.globalpay.ws_banck.service.ITipoTransferenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class TipoTransferenciaController {
    @Autowired
    private ITipoTransferenciaService tipoTransferenciaService;

    //Obtener todos los registros
    @GetMapping("tiposTransferencia")
    public ResponseEntity<?> showAll(){
        List<TipoTransferencia> getListTipoTransferencia = tipoTransferenciaService.listAllTipoTransferencia();
        if (getListTipoTransferencia == null){
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("No hay registros ")
                    .object(null)
                    .build()
            , HttpStatus.OK);
        }
        return new ResponseEntity<>(MensajeResponse.builder()
                .mensaje("")
                .object(getListTipoTransferencia)
                .build()
        ,HttpStatus.OK);
    }
    //Insertar un registro
    @PostMapping("tipoTransferencia")
    public ResponseEntity<?> create(@RequestBody TipoTransferenciaDto tipoTransferenciaDto){
        TipoTransferencia tipoTransferenciaSave = null;
        try {
            tipoTransferenciaSave = tipoTransferenciaService.save(tipoTransferenciaDto);
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("Guardado con exito")
                    .object(tipoTransferenciaDto.builder()
                            .idTipoTransferencia(tipoTransferenciaSave.getIdTipoTransferencia())
                            .nombreTransferencia(tipoTransferenciaSave.getNombreTransferencia())
                            .build()
                    )
                    .build()
                    , HttpStatus.CREATED
            );
        }catch (DataAccessException exDt){
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje(exDt.getMessage())
                    .object(null)
                    .build()
                    ,HttpStatus.METHOD_NOT_ALLOWED
            );
        }
    }

    //Actualizar registro
    @PutMapping("tipoTransferencia/{id}")
    public ResponseEntity<?> update(@RequestBody  TipoTransferenciaDto tipTransferenciaDto,@PathVariable Integer id){
        TipoTransferencia tipoTransferenciaUpdate = null;
        try {
            if (tipoTransferenciaService.existsById(id) ){
                tipTransferenciaDto.setIdTipoTransferencia(id);
                tipoTransferenciaUpdate = tipoTransferenciaService.save(tipTransferenciaDto);
                return new ResponseEntity<>(MensajeResponse.builder()
                        .mensaje("Actualizado con exito")
                        .object(tipTransferenciaDto.builder()
                                .idTipoTransferencia(tipoTransferenciaUpdate.getIdTipoTransferencia())
                                .nombreTransferencia(tipoTransferenciaUpdate.getNombreTransferencia())
                                .build()
                        )
                        .build()
                        ,HttpStatus.CREATED
                );

            }else{
                return new ResponseEntity<>(MensajeResponse.builder()
                        .mensaje("El registro que intenta actualizar no se encuentra en la base de datos")
                        .object(null)
                        .build()
                        ,HttpStatus.METHOD_NOT_ALLOWED);

            }
        }catch (DataAccessException exDt){
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("El registro que intenta actualizar no se encuentra en la base de datos")
                    .object(null)
                    .build()
                    ,HttpStatus.METHOD_NOT_ALLOWED);
        }
    }


    //Eliminar un registro
    @DeleteMapping("tipoTransferencia/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        try {
           TipoTransferencia tipoTransferenciaDelete =tipoTransferenciaService.findById(id);
            tipoTransferenciaService.delete(tipoTransferenciaDelete);
            return new ResponseEntity<>(
                    tipoTransferenciaDelete
                    ,HttpStatus.NO_CONTENT
            );
        }catch (DataAccessException exDt){
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje("El registro que intenta eliminar no existe")
                            .object(null)
                            .build()
                    ,HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    //Obtener un registro
    @GetMapping("tipoTransferencia/{id}")
    public ResponseEntity<?> showById(@PathVariable Integer id){
        TipoTransferencia tipoTransferencia = tipoTransferenciaService.findById(id);
        if (tipoTransferencia == null){
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("El registro que intenta buscar no existe!!").object(null).build()
                    ,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(MensajeResponse.builder()
                .mensaje("Registro encontrado")
                .object(TipoTransferenciaDto.builder()
                        .idTipoTransferencia(tipoTransferencia.getIdTipoTransferencia())
                        .nombreTransferencia(tipoTransferencia.getNombreTransferencia())
                        .build()
                )
                .build()
                ,HttpStatus.OK
        );
    }


}
