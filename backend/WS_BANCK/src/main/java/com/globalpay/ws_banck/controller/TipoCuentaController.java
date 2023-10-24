package com.globalpay.ws_banck.controller;

import com.globalpay.ws_banck.model.dto.TipoCuentaDto;
import com.globalpay.ws_banck.model.entity.TipoCuenta;
import com.globalpay.ws_banck.model.entity.Transferencia;
import com.globalpay.ws_banck.model.payload.MensajeResponse;
import com.globalpay.ws_banck.service.ITipoCuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TipoCuentaController {
    @Autowired
    private ITipoCuentaService tipoCuentaService;

    @PostMapping("tipoCuenta")
    public ResponseEntity<?> create(@RequestBody TipoCuentaDto tipoCuentaDto){
        TipoCuenta tipoCuentaSave = null;
        try {
            tipoCuentaSave = tipoCuentaService.save(tipoCuentaDto);
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("Guardado Correctamente")
                    .object(TipoCuentaDto.builder()
                            .idTipoCuenta(tipoCuentaSave.getIdTipoCuenta())
                            .nombreTipoCuenta(tipoCuentaSave.getNombreTipoCuenta())
                            .build()
                    )
                    .build()
                    , HttpStatus.CREATED);
        }catch (DataAccessException exDt){
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje(exDt.getMessage())
                            .object(null)
                            .build()
                    ,HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @PutMapping("tipoCuenta/{id}")
    public ResponseEntity<?>update(@RequestBody TipoCuentaDto tipoCuentaDto,@PathVariable Integer id){
        TipoCuenta tipoCuentaUpdate = null;

        try {
            if (tipoCuentaService.existsById(id)){
                tipoCuentaDto.setIdTipoCuenta(id);
                tipoCuentaUpdate = tipoCuentaService.save(tipoCuentaDto);
                return new ResponseEntity<>(MensajeResponse.builder()
                        .mensaje("Actualizado Correctamente")
                        .object(TipoCuentaDto.builder()
                                .idTipoCuenta(tipoCuentaUpdate.getIdTipoCuenta())
                                .nombreTipoCuenta(tipoCuentaUpdate.getNombreTipoCuenta())
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
    @DeleteMapping("tipoCuenta/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){

        try {
            TipoCuenta tipoCuentaDelete = tipoCuentaService.findById(id);
            tipoCuentaService.delete(tipoCuentaDelete);
            return new ResponseEntity<>(tipoCuentaDelete,HttpStatus.NO_CONTENT);
        }catch (DataAccessException exDt){
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje(exDt.getMessage())
                            .object(null)
                            .build()
                    ,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("tipoCuenta/{id}")
    public ResponseEntity<?> showById(@PathVariable Integer id){
        TipoCuenta tipoCuenta= tipoCuentaService.findById(id);
        if (tipoCuenta == null){
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje("El registro que intenta buscar, no existe!!")
                            .object(null)
                            .build(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("Registro encontrado")
                        .object(TipoCuentaDto.builder()
                                .idTipoCuenta(tipoCuenta.getIdTipoCuenta())
                                .nombreTipoCuenta(tipoCuenta.getNombreTipoCuenta())
                                .build()
                        )
                        .build(),HttpStatus.OK);
    }
    @GetMapping("tipoCuentas")
    public ResponseEntity<?> showAll(){
        List<TipoCuenta> getListTipoCuenta = tipoCuentaService.listAllTipoCuenta();
        if (getListTipoCuenta == null){
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("No hay registros ")
                    .object(null)
                    .build()
                    , HttpStatus.OK);
        }
        //Comentario random
        return new ResponseEntity<>(MensajeResponse.builder()
                .mensaje("")
                .object(getListTipoCuenta)
                .build(),HttpStatus.OK);
    }
}
