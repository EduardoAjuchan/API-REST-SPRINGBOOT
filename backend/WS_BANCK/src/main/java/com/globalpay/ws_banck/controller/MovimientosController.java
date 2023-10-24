package com.globalpay.ws_banck.controller;


import com.globalpay.ws_banck.model.dto.CuentaDto;
import com.globalpay.ws_banck.model.entity.Cuenta;
import com.globalpay.ws_banck.model.payload.MensajeResponse;
import com.globalpay.ws_banck.service.IMovimientosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class MovimientosController {
    @Autowired
    private IMovimientosService movimientosService;

    @PostMapping("cuenta/debitar")
    public ResponseEntity<?> debitar(@RequestParam  Integer numeroCuenta, @RequestParam float monto){
        Cuenta cuenta = movimientosService.debitar(numeroCuenta,monto);
        if (cuenta != null){
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("Depósito realizado con éxito")
                    .object(CuentaDto.builder()
                            .idCuenta(cuenta.getIdCuenta())
                            .numeroCuenta(cuenta.getNumeroCuenta())
                            .saldo(cuenta.getSaldo())
                            .idTipoCuenta(cuenta.getIdTipoCuenta())
                            .idCliente(cuenta.getIdCliente())
                            .build()
                    )
                    .build()
                    , HttpStatus.CREATED
            );
        } else {
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("No se pudo realizar el depósito")
                    .object(null)
                    .build()
                    , HttpStatus.BAD_REQUEST
            );
        }
    }
}
