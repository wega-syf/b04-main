package com.burat.simpel.service.implementation;

import com.burat.simpel.model.UserModel;
import com.burat.simpel.repository.UserDb;
import com.burat.simpel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDb userDb;

    @Override
    public List<UserModel> getAllUser() {
        return userDb.findAll();
    }

    @Override
    public UserModel addUser(UserModel user) {
        userDb.save(user);
        return user;
    }


    @Override
    public void deleteAccountByUuid(String uuid) {
        userDb.deleteById(uuid);
    }

    @Override
    public Optional<UserModel> getByUuid(String uuid) {
        return userDb.findById(uuid);
    }

    @Override
    public UserModel getByUsername(String username) {
        return userDb.findByUsername(username);
    }
    @Override
    public List<String> getUsername() {
        return userDb.findUsernameUser();
    }
}
