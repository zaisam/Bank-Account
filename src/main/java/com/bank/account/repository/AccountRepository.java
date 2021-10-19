package com.bank.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.account.model.Account;


public interface AccountRepository extends  JpaRepository<Account, String>{

    Account findAccountsByName(String name);

}
