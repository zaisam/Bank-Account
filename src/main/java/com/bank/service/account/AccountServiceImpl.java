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
		return accountRepository.save(account);
	}

	@Override
	public Account findAccountsByName(String name) {
		if (name == null || name.isEmpty()) {
			var err = "entry params is null";
			AccountServiceImpl.log.error("Argument checking:" + err);
			throw new IllegalArgumentException(err);
		}
		return accountRepository.findAccountsByName(name);
	}

	@Override
	public Account deposit(double value, Account account) {
		if (account == null) {
			var err = "entry params is null";
			AccountServiceImpl.log.error("Argument checking:" + err);
			throw new IllegalArgumentException(err);
		}
		account.setAmount(value + account.getAmount());
		return accountRepository.saveAndFlush(account);
	}

	@Override
	public Account withdrawal(double value, Account account) {
		if (account == null) {
			var err = "entry params is null";
			AccountServiceImpl.log.error("Argument checking:" + err);
			throw new IllegalArgumentException(err);
		}
		account.setAmount(account.getAmount() - value);
		return accountRepository.saveAndFlush(account);
	}
}
