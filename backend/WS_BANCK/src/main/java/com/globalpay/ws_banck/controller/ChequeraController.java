package com.globalpay.ws_banck.controller;

import com.globalpay.ws_banck.model.dto.ChequeraDto;
import com.globalpay.ws_banck.model.dto.CuentaDto;
import com.globalpay.ws_banck.model.entity.Cheque;
import com.globalpay.ws_banck.model.entity.Chequera;
import com.globalpay.ws_banck.model.payload.MensajeResponse;
import com.globalpay.ws_banck.service.IChequeraService;
import com.globalpay.ws_banck.service.ICuentaService;
import com.globalpay.ws_banck.service.IMovimientosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("api/v1")
public class ChequeraController {
    @Autowired
    private IChequeraService chequeraService;

    @GetMapping("chequeras")
    public ResponseEntity<?> showAll(){
        List<Chequera> getListChequera = chequeraService.listAllChequera();
        if (getListChequera == null){
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje("No hay registros!!")
                            .object(null)
                            .build(), HttpStatus.OK);
        }

        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("")
                        .object(getListChequera)
                        .build(),HttpStatus.OK);
    }

    //Insertar un registro
    @PostMapping("chequera")
    public ResponseEntity<?> create(@RequestParam  Integer idCuenta, @RequestParam Integer cantidad){
        Chequera chequera = chequeraService.save(idCuenta,cantidad,"Disponible");
        if (chequera!=null){
            BigDecimal idChequeraDB = chequeraService.idChequera(BigDecimal.valueOf(idCuenta),BigDecimal.valueOf(chequera.getAcumulado()));
            Integer idChequera = idChequeraDB.intValue();
            Cheque cheque = chequeraService.saveCheque(idCuenta,idChequera,cantidad,chequera.getAcumulado());
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("Dato insertado con éxito" + idChequera)
                    .object(ChequeraDto.builder()
                            .idChequera(chequera.getIdChequera())
                            .idCuenta(chequera.getIdCuenta())
                            .cantidad(chequera.getCantidad())
                            .estado(chequera.getEstado())
                            .acumulado(chequera.getAcumulado())
                            .build()
                    )
                    .build()
                    , HttpStatus.CREATED
            );
        }else{
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("Registro encontrado")
                    .object(ChequeraDto.builder()
                            .idChequera(chequera.getIdChequera())
                            .idCuenta(chequera.getIdCuenta())
                            .cantidad(chequera.getCantidad())
                            .estado(chequera.getEstado())
                            .acumulado(chequera.getAcumulado())
                            .build()
                    )
                    .build()
                    ,HttpStatus.OK
            );
        }
    }

    //Obtener un registro
    @GetMapping("chequera/{id}")
    public ResponseEntity<?> showById(@PathVariable Integer id){
        Chequera chequera = chequeraService.findByID(id);
        if (chequera == null){
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("El registro que intenta buscar no existe!!").object(null).build()
                    ,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(MensajeResponse.builder()
                .mensaje("Registro encontrado")
                .object(ChequeraDto.builder()
                        .idChequera(chequera.getIdChequera())
                        .idCuenta(chequera.getIdCuenta())
                        .cantidad(chequera.getCantidad())
                        .estado(chequera.getEstado())
                        .acumulado(chequera.getAcumulado())
                        .build()
                )
                .build()
                ,HttpStatus.OK
        );
    }
        @GetMapping("/maximo-de-mi-campo")
        public ResponseEntity<BigDecimal> encontrarMaximoDeMiCampo(@RequestParam("idCuenta") Long idCuenta){
            BigDecimal maximo = chequeraService.encontrarMaximoDeMiCampo(BigDecimal.valueOf(idCuenta));
            return new ResponseEntity<>(maximo, HttpStatus.OK);
    }
    @GetMapping("/numero-cuenta")
    public ResponseEntity<BigDecimal> numeroCuenta(@RequestParam("idCuenta") Long idCuenta){
        BigDecimal numeroCuenta = chequeraService.numeroCuenta(BigDecimal.valueOf(idCuenta));
        return new ResponseEntity<>(numeroCuenta, HttpStatus.OK);
    }
    @GetMapping("/id-chequera")
    public ResponseEntity<BigDecimal> idChequera(@RequestParam("idCuenta") Long idCuenta, @RequestParam("acumulado") Long acumulado){
        BigDecimal idChequera = chequeraService.idChequera(BigDecimal.valueOf(idCuenta),BigDecimal.valueOf(acumulado));
        return new ResponseEntity<>(idChequera, HttpStatus.OK);
    }
}
