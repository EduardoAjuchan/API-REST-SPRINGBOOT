package com.globalpay.ws_banck.controller;

import com.globalpay.ws_banck.model.dao.CuentaDao;
import com.globalpay.ws_banck.model.dto.CuentaDto;
import com.globalpay.ws_banck.model.entity.Cuenta;
import com.globalpay.ws_banck.model.entity.Deposito;
import com.globalpay.ws_banck.model.entity.Transferencia;
import com.globalpay.ws_banck.model.payload.MensajeResponse;
import com.globalpay.ws_banck.service.ICuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1")
public class CuentaController {
    @Autowired
    private ICuentaService  cuentaService;
    @Autowired
    private CuentaDao cuentaDao;

    //Busqueda de cuentas por id cliente
    @GetMapping("cuentas/idCliente")
    public ResponseEntity<?> showByClientId(@RequestParam Integer idCliente){
        List<Map<String, Object>> cuentaList = cuentaDao.findAccountsByClientId(idCliente);
        if (cuentaList == null){
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje("No hay registros!!")
                            .object(null)
                            .build(),HttpStatus.OK);
        }

        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("Registros con id cliente: "+idCliente)
                        .object(cuentaList)
                        .build(),HttpStatus.OK);
    }


    //Busqueda por saldo mayor a saldo recivido
    @GetMapping("cuenta/saldo")
    public ResponseEntity<?> showBySaldo(@RequestParam Float saldo){
        List<Map<String, Object>> cuentaList = cuentaDao.findAccountsWithBalanceGreaterThan(saldo);
        if (cuentaList == null){
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje("No hay registros!!")
                            .object(null)
                            .build(),HttpStatus.OK);
        }

        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("Registros con saldo mayor a: "+saldo)
                        .object(cuentaList)
                        .build(),HttpStatus.OK);
    }

    //Deposito
    @PostMapping("cuenta/deposito")
    public ResponseEntity<?> depositar(@RequestParam Integer numeroCuenta, @RequestParam Float monto) {
        Deposito deposito = cuentaService.depositar(numeroCuenta, monto);
        if(deposito != null) {
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("Deposito realizado con éxito")
                    .object(deposito)
                    .build()
                    , HttpStatus.CREATED
            );
        } else {
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("No se pudo realizar el deposito. Verifique los detalles de la cuenta.")
                    .object(null)
                    .build()
                    , HttpStatus.BAD_REQUEST
            );
        }
    }
    //Transferencia
    @PostMapping("cuenta/transferencia")
    public ResponseEntity<?> transferir(@RequestParam Integer cuentaRemitente, @RequestParam Integer cuentaDestinatario, @RequestParam Float monto, @RequestParam Integer idTipoTransferencia){
        Transferencia transferencia = cuentaService.transferir(cuentaRemitente,cuentaDestinatario,monto,idTipoTransferencia);
        if (transferencia != null) {
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("Transferencia realizada con éxito")
                    .object(transferencia)
                    .build()
                    , HttpStatus.CREATED
            );
        } else {
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("No se pudo realizar la transferencia. Verifique los detalles de las cuentas y el saldo.")
                    .object(null)
                    .build()
                    , HttpStatus.BAD_REQUEST
            );
        }
    }
    //listar cuentas
    @GetMapping("cuentas")
    public ResponseEntity<?> showAll(){
        List<Cuenta> getListCuenta = cuentaService.lisAllCuenta();
        if (getListCuenta == null){
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje("No hay registros!!")
                            .object(null)
                            .build(),HttpStatus.OK);
        }

        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("")
                        .object(getListCuenta)
                        .build(),HttpStatus.OK);
    }
    //Insertar un registro
    @PostMapping("cuenta")
    public ResponseEntity<?> create(@RequestBody CuentaDto cuentaDto){
        Cuenta cuentaSave = null;
        try {
            cuentaSave = cuentaService.save(cuentaDto);
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("Guardado con exito")
                    .object(CuentaDto.builder()
                            .idCuenta(cuentaSave.getIdCuenta())
                            .numeroCuenta(cuentaSave.getNumeroCuenta())
                            .saldo(cuentaSave.getSaldo())
                            .idTipoCuenta(cuentaSave.getIdTipoCuenta())
                            .idCliente(cuentaSave.getIdTipoCuenta())
                            .numeroCuenta(cuentaSave.getNumeroCuenta())
                            .saldo(cuentaSave.getSaldo())
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
    @PutMapping("cuenta/{id}")
    public ResponseEntity<?> update(@RequestBody  CuentaDto cuentaDto,@PathVariable Integer id){
        Cuenta cuentaUpdate = null;
        try {
            if (cuentaService.existsById(id) ){
                cuentaDto.setIdCuenta(id);
                cuentaUpdate = cuentaService.save(cuentaDto);
                return new ResponseEntity<>(MensajeResponse.builder()
                        .mensaje("Actualizado con exito")
                        .object(CuentaDto.builder()
                                .idCuenta(cuentaUpdate.getIdCuenta())
                                .numeroCuenta(cuentaUpdate.getNumeroCuenta())
                                .saldo(cuentaUpdate.getSaldo())
                                .idTipoCuenta(cuentaUpdate.getIdTipoCuenta())
                                .idCliente(cuentaUpdate.getIdCliente())
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
    @DeleteMapping("cuenta/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        try {
            Cuenta cuntaDelete = cuentaService.findById(id);
            cuentaService.dele(cuntaDelete);
            return new ResponseEntity<>(
                    cuntaDelete
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
    @GetMapping("cuenta/{id}")
    public ResponseEntity<?> showById(@PathVariable Integer id){
        Cuenta cuenta = cuentaService.findById(id);
        if (cuenta == null){
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("El registro que intenta buscar no existe!!").object(null).build()
            ,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(MensajeResponse.builder()
                .mensaje("Registro encontrado")
                .object(CuentaDto.builder()
                        .idCuenta(cuenta.getIdCuenta())
                        .numeroCuenta(cuenta.getNumeroCuenta())
                        .saldo(cuenta.getSaldo())
                        .idTipoCuenta(cuenta.getIdTipoCuenta())
                        .idCliente(cuenta.getIdCliente())
                        .saldo(cuenta.getSaldo())
                        .build()
                )
                .build()
                ,HttpStatus.OK
        );
    }

    @GetMapping("cuentasCliente/idCliente")
    public ResponseEntity<?> showCuentasIdClient(@RequestParam Integer idCliente){
        List<Map<String, Object>> cuentaList = cuentaDao.findsAccontsByIdCliente(idCliente);
        if (cuentaList == null){
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje("No hay registros!!")
                            .object(null)
                            .build(),HttpStatus.OK);
        }

        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("Registros con id cliente: "+idCliente)
                        .object(cuentaList)
                        .build(),HttpStatus.OK);
    }
}
