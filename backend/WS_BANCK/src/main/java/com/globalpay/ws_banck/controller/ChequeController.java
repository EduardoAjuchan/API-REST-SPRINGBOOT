package com.globalpay.ws_banck.controller;

import com.globalpay.ws_banck.model.dto.ChequeDto;
import com.globalpay.ws_banck.model.entity.Cheque;
import com.globalpay.ws_banck.model.payload.MensajeResponse;
import com.globalpay.ws_banck.service.IChequeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;

@RestController
@RequestMapping("/api/v1")
public class ChequeController {
    public final MovimientosController movimientosController;

    @Autowired
    private IChequeService chequeService;

    public ChequeController(MovimientosController movimientosController) {
        this.movimientosController = movimientosController;
    }

    @PostMapping("ActualizarCheque/{numeroCuenta}/{numeroCheque}/{beneficiario}/{monto}")
    public ResponseEntity<?> update(@PathVariable Integer numeroCuenta, @PathVariable Integer numeroCheque, @PathVariable String beneficiario, @PathVariable Float monto){
        Cheque chequeSave = null;
        BigDecimal idCheque = chequeService.encontrarIdCheque(BigDecimal.valueOf(numeroCuenta),BigDecimal.valueOf(numeroCheque));
        int id = idCheque.intValue();
        BigDecimal saldoDB = chequeService.saldo(BigDecimal.valueOf(numeroCuenta));
        String estadoDB = chequeService.estado(BigDecimal.valueOf(id));
        float saldoActual = saldoDB.floatValue();
        boolean fondos = false;
        boolean estadoCheque = false;

        if (saldoActual >= monto){
            fondos = true;
        }
        if (estadoDB.equals("Disponible")){
            estadoCheque = true;
        }
        Cheque cheque = chequeService.findById(id);
        try {
            Date fechaCobro = new Date();
            if (fondos & estadoCheque){
                movimientosController.debitar(numeroCuenta,monto);
                chequeSave = chequeService.save(ChequeDto.builder()
                        .idCheque(cheque.getIdCheque())
                        .numeroCheque(cheque.getNumeroCheque())
                        .numeroCuenta(cheque.getNumeroCuenta())
                        .idChequera(cheque.getIdChequera())
                        .fechaEmision(cheque.getFechaEmision())
                        .beneficiario(beneficiario)
                        .monto(monto)
                        .estado("Cobrado")
                        .fechaCobro(fechaCobro)
                        .build());
                return new ResponseEntity<>(MensajeResponse.builder()
                        .mensaje("Actualizado Correctamente")
                        .object(ChequeDto.builder()
                                .idCheque(cheque.getIdCheque())
                                .numeroCheque(cheque.getNumeroCheque())
                                .numeroCuenta(cheque.getNumeroCuenta())
                                .idChequera(cheque.getIdChequera())
                                .fechaEmision(cheque.getFechaEmision())
                                .beneficiario(beneficiario)
                                .monto(monto)
                                .estado("Cobrado")
                                .fechaCobro(fechaCobro)
                                .build()
                        )
                        .build()
                        ,HttpStatus.CREATED);
            }else if (!estadoCheque){
                return new ResponseEntity<>(
                        MensajeResponse.builder()
                                .mensaje("El cheque ya ha sido cobrado---")
                                .object(null)
                                .build()
                        ,HttpStatus.NOT_FOUND);
            }else if (!fondos){
                return new ResponseEntity<>(
                        MensajeResponse.builder()
                                .mensaje("La cuenta no tiene fondos")
                                .object(null)
                                .build()
                        ,HttpStatus.NOT_FOUND);
            }else {
                return new ResponseEntity<>(
                        MensajeResponse.builder()
                                .mensaje("Veremos que ponerle acá")
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
    @GetMapping("/idCheque")
    public ResponseEntity<BigDecimal> encontrarIdCheque(@RequestParam("numeroCuenta") Long numeroCuenta, @RequestParam("numeroCheque") Long numeroCheque){
        BigDecimal maximo = chequeService.encontrarIdCheque(BigDecimal.valueOf(numeroCuenta),BigDecimal.valueOf(numeroCheque));
        return new ResponseEntity<>(maximo, HttpStatus.OK);
    }

    @GetMapping("/saldo")
    public ResponseEntity<BigDecimal> saldo(@RequestParam("numeroCuenta") Long numeroCuenta){
        BigDecimal saldo = chequeService.saldo(BigDecimal.valueOf(numeroCuenta));
        return new ResponseEntity<>(saldo, HttpStatus.OK);
    }

    @GetMapping("/estado")
    public ResponseEntity<String> estado(@RequestParam("idCheque") Long idCheque){
        String estado = chequeService.estado(BigDecimal.valueOf(idCheque));
        return new ResponseEntity<>(estado, HttpStatus.OK);
    }
}
