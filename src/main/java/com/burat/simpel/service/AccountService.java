package com.burat.simpel.service;

import com.burat.simpel.model.AccountModel;

public interface AccountService {
    void register(AccountModel accountModel);
    void registerUtil(AccountModel accountModel);

    void deleteAccountByUuid(String uuid);

    String encrypt(String rawpassword);

    void deleteAccountDependsOnRoleByUuid(String uuid, String role);

    boolean doesAccountExistsByUsername(String username);

    void logOutAllAccounts();
}
