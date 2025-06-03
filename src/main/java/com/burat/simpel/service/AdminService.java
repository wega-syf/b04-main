package com.burat.simpel.service;


import com.burat.simpel.model.AdminModel;


import java.util.List;
import java.util.Optional;

public interface AdminService {

    public List<AdminModel> getAllAdmin();
    public AdminModel addAdmin(AdminModel admin);
    public String encrypt(String password);

    public void deleteAccount(AdminModel adminModel);
    public void deleteAccountByUuid(String uuid);

    Optional<AdminModel> getByUuid(String uuid);
    AdminModel getByUsername(String username);
    public List<String> getUsername();

}
