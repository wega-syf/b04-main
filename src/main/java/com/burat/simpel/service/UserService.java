package com.burat.simpel.service;


import com.burat.simpel.model.ExecutiveModel;
import com.burat.simpel.model.UserModel;


import java.util.List;
import java.util.Optional;

public interface UserService {

    public List<UserModel> getAllUser();
    public UserModel addUser(UserModel user);

    void deleteAccountByUuid(String uuid);
    Optional<UserModel> getByUuid(String uuid);
    public List<String> getUsername();


    UserModel getByUsername(String username);

}
