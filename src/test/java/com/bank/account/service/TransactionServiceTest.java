package com.bank.account.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.bank.account.exception.UnauthorizedOperationException;
import com.bank.account.model.Account;
import com.bank.account.model.Client;
import com.bank.account.model.Operation;
import com.bank.account.model.OperationType;
import com.bank.dto.AccountDTO;
import com.bank.dto.HistoriesDTO;
import com.bank.mapper.AccountDtoMapper;
import com.bank.mapper.OperationDtoMapper;
import com.bank.service.account.IAccountService;
import com.bank.service.client.IClientService;
import com.bank.service.operation.IOperationService;
import com.bank.service.operation.TransactionService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class TransactionServiceTest {

	@Spy
	@InjectMocks
	private TransactionService transactionService;
	@Mock
	private IAccountService accountService;
	@Mock
	private IClientService clientService;
	@Mock
	private IOperationService operationService;
	@Mock
	private OperationDtoMapper operationDtoMapper;
	@Mock
	private AccountDtoMapper accountDtoMapper;

	Account account;
	
	List<Operation> operations;

	final static String ERR_MSG_PAGE = "Page must not be less than 1";
	final static String ERR_MSG_SIZE = "Size must not be less than 1";
	final static String ERR_PARAMS = "entry params is null";
    private static final String ERR_MESSAGE = "Unauthorized operation value";
	final static double AMOUNT_100 = 100;
	final static double AMOUNT_300 = 300;
	final static double AMOUNT_500 = 500;
	final static double ALOOW_NEGATIVE_AMOUNT_300 = -300;
	String balanceExpected;

	@Before
	public void before() {
		Client client = Client.builder().id(Long.valueOf(1)).build();
		account = Account.builder().id(Long.valueOf(1)).amount(AMOUNT_500).date(Instant.now()).client(client).name("SG")
				.allowNegativeAmount(ALOOW_NEGATIVE_AMOUNT_300).build();
		AccountDTO accountDto = new AccountDTO();
		accountDto.setAmount(1.0);
		accountDto.setName("accountTest");

		Mockito.when(accountService.findAccountsByName(Mockito.any(String.class))).thenReturn(account);
		
		 operations = Arrays.asList(
				Operation.builder().account(account).amount(AMOUNT_100)
						.operationType(OperationType.DEPOSIT.getTypeOperation()).date(Instant.now()).build(),
				Operation.builder().account(account).amount(AMOUNT_300)
						.operationType(OperationType.DEPOSIT.getTypeOperation()).date(Instant.now()).build());

		Mockito.when(operationService.findOperations(Mockito.any(String.class))).thenReturn(operations);
		balanceExpected = String.valueOf(operations.stream().mapToDouble(Operation::getAmount).sum());

	}

	@Test
	public void deposit_should_return_success_200() {

		// Given
		Account accountAfterDeposit = transactionService.doDeposit("SG", AMOUNT_100);

		// Then
		assertNotNull(accountAfterDeposit);
		assertEquals(String.valueOf(AMOUNT_100), String.valueOf(accountAfterDeposit.getAmount()));
	}

	@Test
	public void deposit_should_return_error_400() {

		assertThatThrownBy(() -> {
			transactionService.doDeposit(null, AMOUNT_100);
		}).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("entry params is null");

	}

	@Test
	public void withrdawal_should_return_success_200() {

		// Given
		Account accountAfterDeposit = transactionService.doWithdrawal("SG", AMOUNT_300);

		// Then
		assertNotNull(accountAfterDeposit);
		assertEquals(String.valueOf(-AMOUNT_300), String.valueOf(accountAfterDeposit.getAmount()));
	}

	@Test
	public void withrdawal_should_return_error_400() {

		// Given
		assertThatThrownBy(() -> {
			transactionService.doWithdrawal(null, AMOUNT_100);
		}).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(ERR_PARAMS);

	}

	@Test
	public void printStatement_should_return_sucess_200() {

		// Given

		HistoriesDTO histories = transactionService.printStatement(account.getName(), 1, 1);

		// Then
		assertNotNull(histories);
		assertEquals(1, histories.getOperations().size());
		assertEquals(balanceExpected,String.valueOf(histories.getBalance()));

	}

	@Test
	public void printStatement_should_return_IllegalArgumentException_page_must_not_be_less_than_1() {
		
		assertThatThrownBy(() -> {
			transactionService.printStatement(account.getName(), 0, 1);
		}).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(ERR_MSG_PAGE);

	}

	@Test
	public void printStatement_should_return_IllegalArgumentException_size_must_not_be_less_than_1() {


		assertThatThrownBy(() -> {
			transactionService.printStatement(account.getName(), 1, 0);
		}).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(ERR_MSG_SIZE);


	}

	@Test
	public void updateOperation_with_deposit_transaction_should_return_success_status_200() {
		
		
		// Given
		Account accountUpdated = transactionService.updateOperation(AMOUNT_500, account, OperationType.DEPOSIT.getTypeOperation());
		// then
		assertNotNull(accountUpdated);
		assertEquals(balanceExpected, String.valueOf(accountUpdated.getBalance()));
		assertEquals(String.valueOf(AMOUNT_500),String.valueOf(accountUpdated.getAmount()));
		
	}
	

	@Test
	public void updateOperation_with_withdrawal_transaction_should_return_success_status_200() {
		
		
		// Given
		Account accountUpdated = transactionService.updateOperation(AMOUNT_500, account, OperationType.WITHDRAWAL.getTypeOperation());
		// then
		assertNotNull(accountUpdated);
		assertEquals(balanceExpected, String.valueOf(accountUpdated.getBalance()));
		assertEquals(String.valueOf(-AMOUNT_500),String.valueOf(accountUpdated.getAmount()));
		
	}
	
	@Test
	public void updateOperation_should_return_IllegalArgumentException_with_empty_account() {
		

		assertThatThrownBy(() -> {
			transactionService.updateOperation(AMOUNT_500, null, OperationType.WITHDRAWAL.getTypeOperation());
		}).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(ERR_PARAMS);

	}
	
	@Test
	public void updateOperation_should_return_UnauthorizedOperationException_with_AllowNegativeAmount() {
		
		
		operations.stream().forEach(u -> u.setAmount(u.getAmount()*-1));
		Mockito.when(operationService.findOperations(Mockito.any(String.class))).thenReturn(operations);
		assertThatThrownBy(() -> {
			transactionService.updateOperation(AMOUNT_300, account, OperationType.WITHDRAWAL.getTypeOperation());
		}).isInstanceOf(UnauthorizedOperationException.class).hasMessageContaining(ERR_MESSAGE);

		
		
	}
}
