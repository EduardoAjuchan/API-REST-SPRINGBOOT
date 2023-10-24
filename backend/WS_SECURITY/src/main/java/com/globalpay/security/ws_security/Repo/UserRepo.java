package com.globalpay.security.ws_security.Repo;

import com.globalpay.security.ws_security.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {


    User findByUsernameAndPassword(String username, String password);
}

