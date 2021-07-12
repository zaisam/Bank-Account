package com.bank.service.operation;

import static java.util.Objects.requireNonNull;

import java.time.Instant;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.account.model.Operation;
import com.bank.account.repository.AccountRepository;
import com.bank.account.repository.OperationRepository;
import com.bank.service.client.ClientServiceImpl;

import lombok.experimental.var;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class OperationServiceImpl implements IOperationService {
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private OperationRepository operationRepository;

	@Override
	public List<Operation> findOperations(String accountName) {
		if (accountName == null || accountName.isEmpty()) {
			var err = "entry params is null";
			OperationServiceImpl.log.error("Argument checking:" + err);
			throw new IllegalArgumentException(err);
		}
		return operationRepository.findOperationsByAccountName(accountName);
	}

	@Override
	public Operation addOperation(Operation operation) {
		if (operation == null) {
			var err = "entry params is null";
			OperationServiceImpl.log.error("Argument checking:" + err);
			throw new IllegalArgumentException(err);
		}
		return operationRepository.save(operation);
	}


}
