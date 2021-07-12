package com.bank.service.account;

import java.util.List;

import com.bank.account.model.Account;
import com.bank.account.model.Client;

public interface IAccountService {

	Account findAccountsByName(String name);

	Account createAccount(Account account);

	Account deposit(double value, Account account);

	Account withdrawal(double value, Account account);

}
