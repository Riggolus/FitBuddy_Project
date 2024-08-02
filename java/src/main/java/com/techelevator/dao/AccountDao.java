package com.techelevator.dao;

import com.techelevator.model.Account;
import com.techelevator.model.AccountDto;

public interface AccountDao {

    Account createAccount(AccountDto accountDto);
    void accountByAccountId();
    void accountByUserId();
    void accountByUsername();
    void accountByEmail();
}
