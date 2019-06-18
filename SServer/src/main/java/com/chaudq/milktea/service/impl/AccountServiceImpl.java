package com.chaudq.milktea.service.impl;

import com.chaudq.milktea.db.UserDatabase;
import com.chaudq.milktea.model.Account;
import com.chaudq.milktea.model.Registrator;
import com.chaudq.milktea.model.User;
import com.chaudq.milktea.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private UserDatabase store;

    @Override
    public List<Account> getAll() {
        store.intialize();
        return store.getAllAccount();

    }

    @Override
    public User checkLogin(Account account) {
        return store.checkLogin(account);
    }

    @Override
    public String registerAccount(Account account) {

        return store.registerAcccount(account);
    }

    @Override
    public String updateAccount(User user) {
        return store.updateUser(user);
    }

    @Override
    public Boolean updatePoint(User user) {
        return store.updatePoint(user);
    }

    @Override
    public Boolean updateRegister(Registrator registrator) {
        return store.updateRegisterID(registrator);
    }

    @Override
    public Boolean updaterUserInfo(User user) {
        return store.updateUserInfo(user);
    }

}
