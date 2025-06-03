package com.burat.simpel.service.implementation;

import com.burat.simpel.model.AdminModel;
import com.burat.simpel.model.UserModel;
import com.burat.simpel.repository.AdminDb;
import com.burat.simpel.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDb adminDb;

    @Override
    public List<AdminModel> getAllAdmin() {
        return adminDb.findAll();
    }

    @Override
    public AdminModel addAdmin(AdminModel admin) {
        adminDb.save(admin);
        return admin;
    }

    @Override
    public String encrypt(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }

    @Override
    public void deleteAccount(AdminModel adminModel){
        adminDb.delete(adminModel);
    }

    @Override
    public void deleteAccountByUuid(String uuid) {
        adminDb.deleteById(uuid);
    }

    @Override
    public Optional<AdminModel> getByUuid(String uuid) {
        return adminDb.findById(uuid);
    }

    @Override
    public AdminModel getByUsername(String username) {
        return adminDb.findByUsername(username);
    }
    @Override
    public List<String> getUsername() {
        return adminDb.findUsernameAdmin();
    }
}
