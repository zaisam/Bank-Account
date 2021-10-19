package com.bank.service.account;

import com.bank.account.model.Account;

public interface IAccountService {

	Account findAccountsByName(String name);

	Account createAccount(Account account);

}
