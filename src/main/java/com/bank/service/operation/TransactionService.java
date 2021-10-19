package com.bank.service.operation;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.account.exception.UnauthorizedOperationException;
import com.bank.account.model.Account;
import com.bank.account.model.Operation;
import com.bank.account.model.OperationType;
import com.bank.dto.HistoriesDTO;
import com.bank.dto.HistoryDTO;
import com.bank.mapper.OperationDtoMapper;
import com.bank.service.account.IAccountService;

import lombok.experimental.var;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TransactionService {

	@Autowired
	private IAccountService accountService;
	@Autowired
	private IOperationService operationService;

	/***
	 * 
	 * @param amount
	 *            the amount of the transaction
	 * @param account
	 *            the account name
	 * @param operationType the operation type @OperationType
	 * @return Account
	 */
	public Account updateOperation(double amount, Account account, String operationType) {

		if (account == null) {
			var err = "entry params is null";
			TransactionService.log.error("Argument checking:" + err);
			throw new IllegalArgumentException(err);
		}

		int opType = OperationType.WITHDRAWAL.getTypeOperation().equals(operationType) ? -1 : 1;
		Operation operation = Operation.builder().account(account).amount(opType * amount).date(Instant.now())
				.operationType(operationType).build();
		account.setAmount(opType * amount );
		
		operationService.addOperation(operation);
		List<Operation> ops = operationService.findOperations(account.getName());
		double amountSum = ops.stream().mapToDouble(Operation::getAmount).sum();
		account.setBalance(amountSum);
		if (account.getAllowNegativeAmount() > account.getBalance()) {
			operationService.deleteOperation(operation);
			throw new UnauthorizedOperationException(account, operation);
		
		}
		


		return account;

	}

	/**
	 * print statment of all operation
	 * 
	 * @param accountName
	 *            the account name
	 * @param page
	 *            number of page to display
	 * @param size
	 *            size of each page
	 * @return @HistoriesDTO
	 */
	public HistoriesDTO printStatement(String accountName, int page, int size) {
		if (page < 1) {
			throw new IllegalArgumentException("Page must not be less than 1");
		}
		if (size < 1) {
			throw new IllegalArgumentException("Size must not be less than 1");
		}
		List<Operation> operations = operationService.findOperations(accountName);

		List<HistoryDTO> historyDto = OperationDtoMapper.INSTANCE.toHistoryDto(operations);
		double balance = historyDto.stream().mapToDouble(HistoryDTO::getAmount).sum();
		historyDto = historyDto.stream().skip((size * page) - size).limit(size).collect(Collectors.toList());
		return HistoriesDTO.builder().operations(historyDto).balance(balance).build();

	}

	/**
	 * 
	 * @param accountName
	 *            the account name
	 * @param amount
	 *            the amount of the transaction
	 * @return @Account
	 */
	public Account doDeposit(String accountName, double amount) {
		Account account = accountService.findAccountsByName(accountName);
		return this.updateOperation(amount, account, OperationType.DEPOSIT.getTypeOperation());
	}

	/**
	 * 
	 * @param accountName
	 *            the account name
	 * @param amount
	 *            the amount of the transaction
	 * @return @Account
	 */
	public Account doWithdrawal(String accountName, double amount) {
		Account account = accountService.findAccountsByName(accountName);
		return this.updateOperation(amount, account, OperationType.WITHDRAWAL.getTypeOperation());

	}

}
