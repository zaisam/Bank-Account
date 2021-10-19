package com.bank.service.operation;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.account.model.Operation;
import com.bank.account.repository.OperationRepository;

import lombok.experimental.var;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class OperationServiceImpl implements IOperationService {

	@Autowired
	private OperationRepository operationRepository;
	
	@Override
	public List<Operation> findOperations(String accountName) {
		if (accountName == null || accountName.isEmpty()) {
			var err = "entry params is null";
			OperationServiceImpl.log.error("Argument checking:" + err);
			throw new IllegalArgumentException(err);
		}

		List<Operation> operations = operationRepository.findOperationsByAccountName(accountName);
		if (null == operations || operations.isEmpty()) {
			var err = "transactions not found for the account: " + accountName;
			OperationServiceImpl.log.error("Result:" + err);
			throw new IllegalArgumentException(err);
		}
		return operations;
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
	
	@Override
	public void deleteOperation(Operation operation) {
		if (operation == null) {
			var err = "entry params is null";
			OperationServiceImpl.log.error("Argument checking:" + err);
			throw new IllegalArgumentException(err);
		}
		operationRepository.delete(operation);
	}

}
