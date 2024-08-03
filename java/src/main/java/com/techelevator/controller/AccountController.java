package com.techelevator.controller;

import com.techelevator.dao.AccountDao;
import com.techelevator.model.Account;
import com.techelevator.model.AccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin
@RequestMapping("/account")
@PreAuthorize("isAuthenticated()")
public class AccountController {

    private final AccountDao accountDao;

    @Autowired
    public AccountController(AccountDao accountDao) {
        this.accountDao = accountDao;
    }
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public Account createAccount(@RequestBody AccountDto accountDto, Principal principal) {
        Account createdAccount = accountDao.createAccount(accountDto, principal);
        return createdAccount;
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/id/{accountId}")
    public Account accountByAccountId(@PathVariable ("accountId") int accountId)  {
            Account account = accountDao.accountByAccountId(accountId);
            return account;
    }


    @GetMapping("/user/{userId}")
    public Account accountByUserId(@PathVariable ("userId") int userId) {
        Account account = accountDao.accountByUserId(userId);
        return account;
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/username/{username}")
    public Account accountByUsername(@PathVariable ("username") String username) {
        Account account = accountDao.accountByUsername(username);
        return account;
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/email/{email}")
    public Account accountByEmail(@PathVariable ("email") String email) {
        Account account = accountDao.accountByEmail(email);
        return account;
    }
    @PutMapping("/update/{accountId}")
    public boolean editAccount(@RequestBody AccountDto accountDto, Principal principal){
        boolean account = accountDao.editAccount(accountDto, principal);
        return account;
    }
}
