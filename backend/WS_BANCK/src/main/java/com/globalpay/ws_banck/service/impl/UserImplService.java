package com.globalpay.ws_banck.service.impl;

import com.globalpay.ws_banck.model.dao.UserDao;
import com.globalpay.ws_banck.model.jap.ClienteJpa;
import com.globalpay.ws_banck.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
@Service
public class UserImplService implements IUserService {
    @Autowired
    private ClienteJpa clienteJpa;
    @Override
    public BigDecimal findIdClienteCuentaByIdLoginUser(BigDecimal id_login_user) {
        return clienteJpa.findIdClienteCuentaByIdLoginUser(id_login_user);
    }
}
