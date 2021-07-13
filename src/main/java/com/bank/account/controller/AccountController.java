package com.bank.account.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank.account.model.Account;
import com.bank.account.model.Operation;
import com.bank.dto.AccountDTO;
import com.bank.dto.HistoryDTO;
import com.bank.mapper.AccountDtoMapper;
import com.bank.mapper.ClientDtoMapper;
import com.bank.mapper.OperationDtoMapper;
import com.bank.service.account.IAccountService;
import com.bank.service.client.IClientService;
import com.bank.service.operation.IOperationService;
import com.bank.service.operation.TransactionService;

import static helper.ValidatorHelper.validate;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

/**
 * Rest controller that exposes endpoints for Bank Account resource
 * 
 * @author sazaidi
 *
 */
@RestController
@Slf4j
@RequestMapping(path = "/bank")
public class AccountController {
	/**
	 * Account Service that handle logic
	 */
	@Autowired
	private IAccountService accountService;
	/**
	 * Client Service that handle logic
	 */
	@Autowired
	private IClientService clientService;
	/**
	 * Operation Service that handle logic
	 */
	@Autowired
	private IOperationService operationService;
	/**
	 * Transaction Service that handle logic
	 */
	@Autowired
	private TransactionService transactionService;
	@Autowired
	private ClientDtoMapper clientDtoMapper;
	@Autowired
	private AccountDtoMapper accountDtoMapper;
	@Autowired
	private OperationDtoMapper operationDtoMapper;

	/**
	 * Rest POST endpoint on ressoure Account We use Dto to decouple returned data
	 * from stored data if needed.
	 * 
	 * @param accountDto
	 *            the account to create
	 * @return the found account if any is found or an error (bad parameters or not
	 *         found).
	 */
	@PostMapping(path = "create/account")
	@ApiResponses(value = { @ApiResponse(code = 500, message = "Server error"),
			@ApiResponse(code = 404, message = "Service not found"),
			@ApiResponse(code = 200, message = "Successful retrieval", response = Account.class, responseContainer = "List") })
	@ApiOperation(value = "Save an account in database")
	public AccountDTO createAccount(@RequestBody AccountDTO accountDto) {
		AccountController.log.info("Entering methode.");
		// Validate request body data
		validate(accountDto);
		Account account = accountDtoMapper.convertToEntity(accountDto);
		account = accountService.createAccount(account);
		return accountDtoMapper.convertToDTO(account);
	}

	/**
	 * Rest POST endpoint on ressoure Account We use Dto to decouple returned data
	 * from stored data if needed.
	 * 
	 * @param accountName
	 *            the account name to update
	 * @param value
	 *            the value deposited
	 * @return the account updated with a new amount or an error (bad parameters or
	 *         not found).
	 */
	@PostMapping(path = "account/deposit")
	@ApiResponses(value = { @ApiResponse(code = 500, message = "Server error"),
			@ApiResponse(code = 404, message = "Service not found"),
			@ApiResponse(code = 200, message = "Successful retrieval", response = AccountDTO.class) })
	@ApiOperation(value = "Save an operation in database")
	public AccountDTO deposit(@RequestParam(required = true) String accountName,
			@RequestParam(value = "value", required = true) double value) {
		
		Account account = accountService.findAccountsByName(accountName);
		account = transactionService.deposit(value, account);
		return accountDtoMapper.convertToDTO(account);
	}

	/**
	 * Rest POST endpoint on ressoure Account We use Dto to decouple returned data
	 * from stored data if needed.
	 * 
	 * @param accountName
	 *            the account name to update
	 * @param value
	 *            the value to be withdrawn
	 * @return the account updated with a new amount or an error (bad parameters or
	 *         not found).
	 */
	@PostMapping(path = "account/withdraw")
	@ApiResponses(value = { @ApiResponse(code = 500, message = "Server error"),
			@ApiResponse(code = 404, message = "Service not found"),
			@ApiResponse(code = 200, message = "Successful retrieval", response = AccountDTO.class) })
	@ApiOperation(value = "Save an operation in database")
	public AccountDTO withdrawal(@RequestParam(required = true) String accountName,
			@RequestParam(value = "value", required = true) double value) {

		Account account = accountService.findAccountsByName(accountName);
		account = transactionService.withdrawal(value, account);
		return accountDtoMapper.convertToDTO(account);
	}

	/**
	 * Rest GET endpoint on ressoure Account We use Dto to decouple returned data
	 * from stored data if needed.
	 * 
	 * @param accountName
	 *            the account name to display
	 * @return a list of transaction history made by the customer
	 */
	@GetMapping(path = "account/history")
	@ApiResponses(value = { @ApiResponse(code = 500, message = "Server error"),
			@ApiResponse(code = 404, message = "Service not found"),
			@ApiResponse(code = 200, message = "Successful retrieval", response = Account.class, responseContainer = "List") })
	@ApiOperation(value = "Save an account in database")
	public List<HistoryDTO> getHistory(@RequestParam(required = true) String accountName) {

		List<Operation> operations = transactionService.getOperations(accountName);
		return operationDtoMapper.convertToDTO(operations);

	}

}
