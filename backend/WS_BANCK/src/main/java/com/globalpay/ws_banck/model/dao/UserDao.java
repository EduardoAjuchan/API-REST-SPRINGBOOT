package com.globalpay.ws_banck.model.dao;
import com.globalpay.ws_banck.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
public interface UserDao extends JpaRepository<User, Long> {

        User findByUsernameAndPassword(String username, String password);
}
