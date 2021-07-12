package com.bank.service.operation;

import java.time.Instant;
import java.util.List;

import com.bank.account.model.Operation;

public interface IOperationService {

	public List<Operation> findOperations(String accountName);

	public Operation addOperation(Operation operation);
	
}
