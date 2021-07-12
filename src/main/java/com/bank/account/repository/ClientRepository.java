package com.bank.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.account.model.Client;
@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{

}
