package com.bank.service.operation;

import java.util.List;

import com.bank.account.model.Operation;

public interface IOperationService {
	/**
	 * 
	 * @param accountName the account Name
	 * @return list of @Operation
	 */
	public List<Operation> findOperations(String accountName);
	/**
	 * 
	 * @param the operation to add
	 * @return @Operation
	 */
	public Operation addOperation(Operation operation);
	
}
