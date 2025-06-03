package com.burat.simpel.service.implementation;

import com.burat.simpel.model.*;
import com.burat.simpel.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AdminService adminService;

    @Autowired
    SessionRegistry sessionRegistry;
    @Autowired
    ExecutiveService executiveService;

    @Autowired
    AssessorService assessorService;

    @Autowired
    UserService userService;

    @Override
    public void register(AccountModel accountModel){
        // Encrypt password first then register the account
        accountModel.setPassword(encrypt(accountModel.getPassword()));
        registerUtil(accountModel);
    }

    @Override
    public void registerUtil(AccountModel account) {
        switch (account.getRole()) {
            case "admin": {
                AdminModel newAccount = new AdminModel();
                BeanUtils.copyProperties(account, newAccount);
                adminService.addAdmin(newAccount);
                break;
            }
            case "executive": {
                ExecutiveModel newAccount = new ExecutiveModel();
                BeanUtils.copyProperties(account, newAccount);
                executiveService.addExecutive(newAccount);
                break;
            }
            case "assessor": {
                AssessorModel newAccount = new AssessorModel();
                BeanUtils.copyProperties(account, newAccount);
                assessorService.addAssessor(newAccount);
                break;
            }
            default: {
                UserModel newAccount = new UserModel();
                BeanUtils.copyProperties(account, newAccount);
                userService.addUser(newAccount);
                break;
            }
        }
    }

    @Override
    public void deleteAccountByUuid(String uuid) {
        // Simply delete from all table that inherits Account
        adminService.deleteAccountByUuid(uuid);
        assessorService.deleteAccountByUuid(uuid);
        executiveService.deleteAccountByUuid(uuid);
        userService.deleteAccountByUuid(uuid);
    }

    @Override
    public void deleteAccountDependsOnRoleByUuid(String uuid, String role) {
        switch (role) {
            case "admin": {
                adminService.deleteAccountByUuid(uuid);
                break;
            }
            case "executive": {
                executiveService.deleteAccountByUuid(uuid);
                break;
            }
            case "assessor": {
                assessorService.deleteAccountByUuid(uuid);
                break;
            }
            default: {
                userService.deleteAccountByUuid(uuid);
                break;
            }
        }
    }


    @Override
    public boolean doesAccountExistsByUsername(String username) {
        // Check existence from all table
        boolean inAdmin = adminService.getByUsername(username) != null;
        boolean inExecutive = executiveService.getByUsername(username) != null;
        boolean inAssessor = assessorService.getByUsername(username) != null;
        boolean inUser = userService.getByUsername(username) != null;

        // only needs one to be true
        return inAdmin || inExecutive || inAssessor || inUser;
    }

    @Override
    public String encrypt(String rawpassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(rawpassword);
        return hashedPassword;
    }

    @Override
    public void logOutAllAccounts() {
        List<SessionInformation> activeSessions = new ArrayList<SessionInformation>();
        for (Object principal : sessionRegistry.getAllPrincipals()) {
//            System.out.println("Test");
            for (SessionInformation session : sessionRegistry.getAllSessions(principal, true)) {
                System.out.println(activeSessions);
                activeSessions.add(session);
            }
        }

    }
}
