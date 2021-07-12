package com.bank.account.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.account.model.Operation;

public interface OperationRepository extends JpaRepository<Operation, String>  {

    List<Operation> findOperationsByAccountName(String accountName);
}
