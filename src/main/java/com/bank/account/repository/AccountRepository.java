package com.bank.account.repository;

import com.bank.account.model.Account;
import com.bank.account.model.Client;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface AccountRepository extends  JpaRepository<Account, String>{

    Account findAccountsByName(String name);

}
