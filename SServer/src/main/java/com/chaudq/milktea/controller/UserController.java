package com.chaudq.milktea.controller;

import com.chaudq.milktea.model.Account;
import com.chaudq.milktea.model.Registrator;
import com.chaudq.milktea.model.User;
import com.chaudq.milktea.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class UserController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/all")
    public List<Account> getAllUser() {
        return accountService.getAll();
    }

    @PostMapping("/checklogin")
    public User checkLogin(@RequestBody Account account) {
        return accountService.checkLogin(account);
    }

    @PostMapping("/registeraccount")
    public String registerAccount(@RequestBody Account account) {
        return accountService.registerAccount(account);
    }

    @PostMapping("/update")
    public String updateAccount(@RequestBody User user) {
        return accountService.updateAccount(user);
    }

    @PostMapping("/updatepoint")
    public Boolean updatePoint(@RequestBody User user) {
        return accountService.updatePoint(user);
    }

    @PostMapping("/updateUserInfo")
    public Boolean updateUserInfo(@RequestBody User user) {
        return accountService.updaterUserInfo(user);
    }

    @PostMapping("/updateRegister")
    private Boolean updateRegister(@RequestBody Registrator registrator) {
        return accountService.updateRegister(registrator);
    }
}
