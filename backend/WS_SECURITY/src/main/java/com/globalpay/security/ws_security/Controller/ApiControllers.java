package com.globalpay.security.ws_security.Controller;

import com.globalpay.security.ws_security.Models.User;
import com.globalpay.security.ws_security.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ApiControllers {
    @Autowired
    private UserRepo userRepo;
    @GetMapping(value = "/")
    public String getPage(){
        return "Welcome to WS Security API";
    }

    @GetMapping(value = "list")
    public List<User> getUsers(){
        return userRepo.findAll();
    }

    @PostMapping(value = "save")
    private String SaveUser(@RequestBody User user){
        userRepo.save(user);
        return "Saved";
    }

    @PutMapping(value = "update/{id}")
    public String updateUser(@PathVariable long id, @RequestBody User user){
    User updateUser = userRepo.getById(id);
    updateUser.setName(user.getName());
    updateUser.setLastname(user.getLastname());
    updateUser.setEmail(user.getEmail());
    updateUser.setUsername(user.getUsername());
    updateUser.setPassword(user.getPassword());
    updateUser.setPhone(user.getPhone());
    updateUser.setStatus(user.getStatus());
    userRepo.save(updateUser);
    return "Updated";
    }

    @PostMapping(value = "login")
    public String loginUser(@RequestBody User user){
        User loginUser = userRepo.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        if(loginUser != null){
            return "Login Success,200+id:"+loginUser.getId_login_user()+",name:"+loginUser.getName()+",lastname:"+loginUser.getLastname()+",email:"+loginUser.getEmail()+",username:"+loginUser.getUsername()+",password:"+loginUser.getPassword()+",phone:"+loginUser.getPhone()+",status:"+loginUser.getStatus()+",";
        }else{
            return "Login Failed,401";
        }
    }
}
