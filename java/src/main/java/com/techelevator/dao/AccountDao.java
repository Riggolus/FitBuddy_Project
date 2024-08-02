package com.techelevator.dao;

import com.techelevator.model.Account;
import com.techelevator.model.AccountDto;

import java.security.Principal;

public interface AccountDao {

    Account createAccount(AccountDto accountDto, Principal principal);
    Account accountByAccountId(int accountId);
    Account accountByUserId(int userId);
    Account accountByUsername(String username);
    Account accountByEmail(String email);
}
