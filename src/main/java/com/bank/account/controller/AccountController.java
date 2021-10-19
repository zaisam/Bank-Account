package com.bank.account.controller;

import static helper.ValidatorHelper.validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank.account.model.Account;
import com.bank.dto.AccountDTO;
import com.bank.dto.HistoriesDTO;
import com.bank.mapper.AccountDtoMapper;
import com.bank.service.account.IAccountService;
import com.bank.service.operation.TransactionService;

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
@RequestMapping(path = "/api/bank")
public class AccountController {
	/**
	 * Account Service that handle logic
	 */
	@Autowired
	private IAccountService accountService;

	/**
	 * Transaction Service that handle logic
	 */
	@Autowired
	private TransactionService transactionService;

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
			@ApiResponse(code = 200, message = "Successful retrieval", response = AccountDTO.class) })
	@ApiOperation(value = "Save an account in database")
	public AccountDTO createAccount(@RequestBody AccountDTO accountDto) {
		AccountController.log.info("Entering methode.");
		// Validate request body data
		validate(accountDto);
		Account account = AccountDtoMapper.INSTANCE.toEntity(accountDto);
		account = accountService.createAccount(account);
		return AccountDtoMapper.INSTANCE.toDto(account);
	}

	/**
	 * Rest POST endpoint on ressoure Account We use Dto to decouple returned data
	 * from stored data if needed.
	 * 
	 * @param accountName
	 *            the account name to update
	 * @param amount
	 *            the amount deposited
	 * @return the account updated with a new amount or an error (bad parameters or
	 *         not found).
	 */
	@PutMapping(path = "{accountName}/deposit")
	@ApiResponses(value = { @ApiResponse(code = 500, message = "Server error"),
			@ApiResponse(code = 404, message = "Service not found"),
			@ApiResponse(code = 200, message = "Successful retrieval", response = AccountDTO.class) })
	@ApiOperation(value = "Save an operation in database")
	public AccountDTO deposit(@PathVariable String accountName, @RequestParam double amount) {
		Account account = this.transactionService.doDeposit(accountName, amount);
		return AccountDtoMapper.INSTANCE.toDto(account);
	}

	/**
	 * Rest POST endpoint on ressoure Account We use Dto to decouple returned data
	 * from stored data if needed.
	 * 
	 * @param accountName
	 *            the account name to update
	 * @param amount
	 *            the amount to be withdrawn
	 * @return the account updated with a new amount or an error (bad parameters or
	 *         not found).
	 */
	@PutMapping(path = "{accountName}/withdraw")
	@ApiResponses(value = { @ApiResponse(code = 500, message = "Server error"),
			@ApiResponse(code = 404, message = "Service not found"),
			@ApiResponse(code = 200, message = "Successful retrieval", response = AccountDTO.class) })
	@ApiOperation(value = "Save an operation in database")
	public AccountDTO withdrawal(@PathVariable String accountName, @RequestParam double amount) {

		Account account = this.transactionService.doWithdrawal(accountName, amount);
		return AccountDtoMapper.INSTANCE.toDto(account);
	}

	/**
	 * Rest GET endpoint on ressoure Account We use Dto to decouple returned data
	 * from stored data if needed.
	 * 
	 * @param accountName
	 *            the account name to display
	 * @param page
	 *            the page number to display
	 * @param size
	 *            the size number to display
	 * @return a list of transaction history made by the customer
	 */
	@GetMapping("{accountName}/history")
	@ApiResponses(value = { @ApiResponse(code = 500, message = "Server error"),
			@ApiResponse(code = 404, message = "Service not found"),
			@ApiResponse(code = 200, message = "Successful retrieval", response = HistoriesDTO.class) })
	@ApiOperation(value = "Save an account in database")
	public HistoriesDTO getHistory(@PathVariable String accountName, @RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "5") int size) {

		return transactionService.printStatement(accountName, page, size);
	

	}

}
