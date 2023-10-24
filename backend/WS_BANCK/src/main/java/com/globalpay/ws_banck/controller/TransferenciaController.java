package com.globalpay.ws_banck.controller;

import com.globalpay.ws_banck.model.dao.TransferenciaDao;
import com.globalpay.ws_banck.model.dto.TransferenciaDto;
import com.globalpay.ws_banck.model.entity.Transferencia;
import com.globalpay.ws_banck.model.payload.MensajeResponse;
import com.globalpay.ws_banck.service.ITransferenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class TransferenciaController {
    @Autowired
    private TransferenciaDao transferenciaDao;
    @Autowired
    private ITransferenciaService transferenciaService;
    //Listar usuarios que han transferido
    @GetMapping("/usuarios-con-transferencias")
    public ResponseEntity<?> obtenerUsuariosConTransferencias() {
        List<Map<String, Object>> resultados = transferenciaDao.findUsersWithTransfers();

        if (resultados == null){
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje("No hay registros!!")
                            .object(null)
                            .build(),HttpStatus.OK);
        }

        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("Se encuentran estos registros")
                        .object(resultados)
                        .build(),HttpStatus.OK);
    }
    //Listar usuarios que han recibido transferencias
    @GetMapping("/usuarios-con-transferencias-recibidas")
    public ResponseEntity<?> obtenerUsuariosConTransferenciasRecibidas() {
        List<Map<String, Object>> resultados = transferenciaDao.findUsersWithTransfersReceived();

        if (resultados == null){
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje("No hay registros!!")
                            .object(null)
                            .build(),HttpStatus.OK);
        }

        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("Se encuentran estos registros")
                        .object(resultados)
                        .build(),HttpStatus.OK);
    }

    //Metodo para buscar un transferencia por su id
    @GetMapping("transferencia/{id}")
    public ResponseEntity<?> showById(@PathVariable Integer id) {
        Transferencia transferencia = transferenciaService.findById(id);

        if (transferencia == null) {
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje("El registro que intenta buscar, no existe!!")
                            .object(null)
                            .build(),HttpStatus.OK);
        }
        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("Registro encontrado")
                        .object(TransferenciaDto.builder()
                                .idTransferencia(transferencia.getIdTransferencia())
                                .monto(transferencia.getMonto())
                                .fecha(transferencia.getFecha())
                                .numCuentaRemitente(transferencia.getNumCuentaRemitente())
                                .numCuentaDestinatario(transferencia.getNumCuentaDestinatario())
                                .idTipoTransferencia(transferencia.getIdTipoTransferencia())
                                .idCuentaRemitente(transferencia.getIdCuentaRemitente())
                                .idCuentaDestinatario(transferencia.getIdCuentaDestinatario())
                                .build()
                        )
                        .build(),HttpStatus.OK);
    }
    //Metodo para listar todos los depositos
    @GetMapping("transferencias")
    public ResponseEntity<?> showAll() {
        List<Transferencia> getListTransferencia = transferenciaService.listAllTransferencia();
        if (getListTransferencia == null) {
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("No hay registros ")
                    .object(null)
                    .build()
                    ,HttpStatus.OK);
        }
        return new ResponseEntity<>(MensajeResponse.builder()
                .mensaje("")
                .object(getListTransferencia)
                .build()
                ,HttpStatus.OK);
    }
}
