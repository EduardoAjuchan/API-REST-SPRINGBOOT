package com.globalpay.ws_banck.service.impl;

import com.globalpay.ws_banck.model.dao.ClienteDao;
import com.globalpay.ws_banck.model.dto.ClienteDto;
import com.globalpay.ws_banck.model.entity.Cliente;
import com.globalpay.ws_banck.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteImplService implements IClienteService {
    @Autowired
    private ClienteDao clienteDao;

    //Listar todos los registros
    @Override
    public List<Cliente> listAllCliente() {
        return (List) clienteDao.findAll();
    }


    //Ingresar un registro
    @Override
    public Cliente save(ClienteDto cliente) {
         Cliente clienteBanco = Cliente.builder()
                 .idCliente(cliente.getIdCliente())
                 .nombreCliente(cliente.getNombreCliente())
                 .apellidoPaterno(cliente.getApellidoPaterno())
                 .apellidoMaterno(cliente.getApellidoMaterno())
                 .edad(cliente.getEdad())
                 .fkBanco(cliente.getFkBanco())
                 .fkLoginUser(cliente.getFkLoginUser())
                 .build();
         return clienteDao.save(clienteBanco);
    }
    //Actualiza registro
    @Override
    public Cliente findById(Integer id) {
        return clienteDao.findById(id).orElse(null);
    }
    //Eliminar un registro
    @Override
    public void delete(Cliente cliente) {
        clienteDao.delete(cliente);
    }
    //Obtener un registro
    @Override
    public boolean existsById(Integer id) {
        return clienteDao.existsById(id);
    }
}
