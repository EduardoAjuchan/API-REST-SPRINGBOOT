package com.globalpay.ws_banck.controller;

import com.globalpay.ws_banck.model.dao.UserDao;
import com.globalpay.ws_banck.model.entity.User;
import com.globalpay.ws_banck.service.IClienteService;
import com.globalpay.ws_banck.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/user/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ApiControllers {
    @Autowired
    private IUserService userService;
    @Autowired
    private UserDao userDao;
    @GetMapping(value = "/")
    public String getPage(){
        return "Welcome to WS Security API";
    }

    @GetMapping(value = "list")
    public List<User> getUsers(){
        return userDao.findAll();
    }

    @PostMapping(value = "save")
    private String SaveUser(@RequestBody User user){
        userDao.save(user);
        return "Saved";
    }

    @PutMapping(value = "update/{id}")
    public String updateUser(@PathVariable long id, @RequestBody User user){
        User updateUser = userDao.getById(id);
        updateUser.setName(user.getName());
        updateUser.setLastname(user.getLastname());
        updateUser.setEmail(user.getEmail());
        updateUser.setUsername(user.getUsername());
        updateUser.setPassword(user.getPassword());
        updateUser.setPhone(user.getPhone());
        updateUser.setStatus(user.getStatus());
        userDao.save(updateUser);
        return "Updated";
    }

    @PostMapping(value = "login")
    public String loginUser(@RequestBody User user){
        User loginUser = userDao.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        if(loginUser != null){
            return "Login Success,200+id:"+loginUser.getId_login_user()+",name:"+loginUser.getName()+",lastname:"+loginUser.getLastname()+",email:"+loginUser.getEmail()+",username:"+loginUser.getUsername()+",password:"+loginUser.getPassword()+",phone:"+loginUser.getPhone()+",status:"+loginUser.getStatus()+",";
        }else{
            return "Login Failed,401";
        }
    }
    @GetMapping("/idClienteCuenta")
    public ResponseEntity<?> idClienteCuenta(@RequestParam("id_login_user") Long idLoginUser) {
        BigDecimal idCliente = userService.findIdClienteCuentaByIdLoginUser(BigDecimal.valueOf(idLoginUser));

        if (idCliente != null) {
            return new ResponseEntity<>(idCliente, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(BigDecimal.ZERO, HttpStatus.OK);
        }
    }

}
