package com.bank.service.account;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.account.model.Account;
import com.bank.account.repository.AccountRepository;

import lombok.experimental.var;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class AccountServiceImpl implements IAccountService {

	@Autowired
	private AccountRepository accountRepository;

	public Account createAccount(Account account) {
		if (null == account) {
			var err = "entry params is null";
			AccountServiceImpl.log.error("Argument checking:" + err);
			throw new IllegalArgumentException(err);
		}
		if (account.getAmount() != account.getBalance()) {
			var err = "the amount of account must be equal to the balance";
			AccountServiceImpl.log.error("Argument checking:" + err);
			throw new IllegalArgumentException(err);
		}
		account.setBalance(account.getAmount());
		return accountRepository.save(account);
	}

	@Override
	public Account findAccountsByName(String name) {
		if (name == null || name.isEmpty()) {
			var err = "entry params is null";
			AccountServiceImpl.log.error("Argument checking:" + err);
			throw new IllegalArgumentException(err);
		}
		Account account =  accountRepository.findAccountsByName(name);
		if (account == null ) {
			var err = "account not found ";
			AccountServiceImpl.log.error("Result:" + err);
			throw new IllegalArgumentException(err);
		}
		return account;
	}


}
