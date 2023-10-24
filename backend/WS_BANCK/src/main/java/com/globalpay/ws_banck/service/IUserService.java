package com.globalpay.ws_banck.service;

import java.math.BigDecimal;

public interface IUserService {
    BigDecimal findIdClienteCuentaByIdLoginUser(BigDecimal id_login_user);
}
