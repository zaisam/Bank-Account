package com.bank.account.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.bank.account.model.Account;
import com.bank.account.model.Client;
import com.bank.account.model.Operation;
import com.bank.account.model.OperationType;
import com.bank.dto.AccountDTO;
import com.bank.dto.OperationDTO;
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

	@Before
	public void before() {
		Client client = Client.builder().id(Long.valueOf(1)).build();
		Account account = Account.builder().id(Long.valueOf(1)).amount(500).date(new Date()).client(client).build();
		AccountDTO accountDto = new AccountDTO();
		accountDto.setAmount(1.0);
		accountDto.setName("accountTest");
		Operation operation = Operation.builder().operationType(OperationType.DEPOSIT.getTypeOperation()).value(500)
				.account(account).build();
		Mockito.when(operationDtoMapper.convertToEntity(Mockito.any(OperationDTO.class))).thenReturn(operation);

		Mockito.when(accountDtoMapper.convertToDTO(Mockito.any(Account.class))).thenReturn(accountDto);

		Mockito.when(accountService.deposit(Mockito.any(double.class), Mockito.any(Account.class))).thenReturn(account);

		Mockito.when(accountService.withdrawal(Mockito.any(double.class), Mockito.any(Account.class)))
				.thenReturn(account);

	}

	@Test
	public void testDeposit_Success() {

		// Given
		Account accountAfterDeposit = transactionService.deposit(100, Account.builder().build());

		// Then
		assertNotNull(accountAfterDeposit);
		assertEquals("500.0", String.valueOf(accountAfterDeposit.getAmount()));
	}

	@Test
	public void testDeposit_Failure_400() {

		assertThatThrownBy(() -> {
			transactionService.deposit(100, null);
		}).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("entry params is null");

	}

	@Test
	public void testWithrdawal_Success() {

		// Given
		Account accountAfterDeposit = transactionService.withdrawal(100, Account.builder().build());

		// Then
		assertNotNull(accountAfterDeposit);
		assertEquals("500.0", String.valueOf(accountAfterDeposit.getAmount()));
	}

	@Test
	public void testWithrdawal_Failure_400() {

		// Given
		assertThatThrownBy(() -> {
			transactionService.withdrawal(100, null);
		}).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("entry params is null");

	}
}
