package com.thiago.picpaybackendchallenge.service;

import com.thiago.picpaybackendchallenge.domain.user.User;
import com.thiago.picpaybackendchallenge.domain.user.UserType;
import com.thiago.picpaybackendchallenge.dto.UserDTO;
import com.thiago.picpaybackendchallenge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public void validateTransaction(User sender, BigDecimal amount) throws Exception{
        if(sender.getUserType() == UserType.PAYEE){
            throw new Exception("User type Payee is not allowed to perform transactions.");
        }
        if(sender.getBalance().compareTo(amount) < 0) {
            throw new Exception("Insufficient founds.");
        }
    }

    public User findUserByID(UUID id) throws Exception{
        return this.repository.findUserById(id)
            .orElseThrow(() -> new Exception("User not found."));
    }

    public User createUser(UserDTO data){
        User newUser = new User(data);
        this.saveUser(newUser);
        return newUser;
    }

    public void saveUser(User user){
        this.repository.save(user);
    }

    public List<User> getAllUsers(){
        return this.repository.findAll();
    }
}
