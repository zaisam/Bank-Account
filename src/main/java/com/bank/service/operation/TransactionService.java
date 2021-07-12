package com.bank.service.operation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.account.exception.UnauthorizedOperationException;
import com.bank.account.model.Account;
import com.bank.account.model.Operation;
import com.bank.account.model.OperationType;
import com.bank.dto.AccountDTO;
import com.bank.dto.OperationDTO;
import com.bank.mapper.AccountDtoMapper;
import com.bank.mapper.OperationDtoMapper;
import com.bank.service.account.AccountServiceImpl;
import com.bank.service.account.IAccountService;
import com.bank.service.client.IClientService;

import lombok.experimental.var;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TransactionService {

	@Autowired
	private IAccountService accountService;
	@Autowired
	private IClientService clientService;
	@Autowired
	private IOperationService operationService;
	@Autowired
	private OperationDtoMapper operationDtoMapper;
	@Autowired
	private AccountDtoMapper accountDtoMapper;

	/**
	 * 
	 * @param value
	 * @param account
	 * @return
	 */
	public Account deposit(double value, Account account) {
		account = accountService.deposit(value, account);

		if (account == null) {
			var err = "entry params is null";
			TransactionService.log.error("Argument checking:" + err);
			throw new IllegalArgumentException(err);
		}
		AccountDTO accountDto = accountDtoMapper.convertToDTO(account);

		OperationDTO operationDTO = new OperationDTO();
		operationDTO.setAccount(accountDto);
		operationDTO.setAmount(account.getAmount());
		operationDTO.setValue(value);
		operationDTO.setDate(account.getDate());
		operationDTO.setOperationType(OperationType.DEPOSIT.getTypeOperation());

		Operation operation = operationDtoMapper.convertToEntity(operationDTO);
		operation.getAccount().setId(account.getId());
		operation.getAccount().getClient().setId(account.getClient().getId());
		operationService.addOperation(operation);

		return account;
	}

	/**
	 * 
	 * @param value
	 * @param account
	 * @return
	 */
	public Account withdrawal(double value, Account account) {
		account = accountService.withdrawal(value, account);
		if (account == null) {
			var err = "entry params is null";
			TransactionService.log.error("Argument checking:" + err);
			throw new IllegalArgumentException(err);
		}
		AccountDTO accountDto = accountDtoMapper.convertToDTO(account);
		OperationDTO operationDTO = new OperationDTO();
		operationDTO.setAccount(accountDto);
		operationDTO.setAmount(account.getAmount());
		operationDTO.setValue(-value);
		operationDTO.setDate(account.getDate());
		operationDTO.setOperationType(OperationType.WITHDRAWAL.getTypeOperation());

		Operation operation = operationDtoMapper.convertToEntity(operationDTO);
		operation.getAccount().setId(account.getId());
		operation.getAccount().getClient().setId(account.getClient().getId());
		value = -value;
		if (account.getAllowNegativeAmount() > account.getAmount()) {
			throw new UnauthorizedOperationException(account, operation);
		}
		operationService.addOperation(operation);

		return account;
	}

	/**
	 * 
	 * @param accountName
	 * @return
	 */
	public List<Operation> getOperations(String accountName) {
		return operationService.findOperations(accountName);
	}

}
