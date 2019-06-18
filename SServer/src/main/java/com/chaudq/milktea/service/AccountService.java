package com.chaudq.milktea.service;

import com.chaudq.milktea.model.Account;
import com.chaudq.milktea.model.Registrator;
import com.chaudq.milktea.model.User;

import java.util.List;

public interface AccountService {
    List<Account> getAll();
    User checkLogin(Account account);
    String registerAccount(Account account);
    String updateAccount(User user);
    Boolean updatePoint(User user);
    Boolean updateRegister(Registrator registrator);
    Boolean updaterUserInfo(User user);
}
