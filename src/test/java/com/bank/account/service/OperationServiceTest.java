package com.bank.account.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.bank.account.model.Account;
import com.bank.account.model.Operation;
import com.bank.account.model.OperationType;
import com.bank.account.repository.AccountRepository;
import com.bank.account.repository.OperationRepository;
import com.bank.service.operation.OperationServiceImpl;

@RunWith(MockitoJUnitRunner.Silent.class)
public class OperationServiceTest {

	private static final String CLIENT_ID = UUID.randomUUID().toString();
	private static final String ACCOUNT_ID = UUID.randomUUID().toString();

	@Spy
	@InjectMocks
	private OperationServiceImpl operationService;

	@Mock
	private AccountRepository accountRepository;

	@Mock
	private OperationRepository operationRepository;

	@Before
	public void before() {

	}

	@Test
	public void testFindOperations_Success() {
		// Given
		Account account = Account.builder().name("accountTest").build();
		Operation operation = Operation.builder().operationType(OperationType.DEPOSIT.getTypeOperation()).value(500)
				.account(account).build();
		Mockito.when(operationRepository.findOperationsByAccountName(Mockito.any(String.class)))
				.thenReturn(Arrays.asList(operation));
		// When
		List<Operation> operationsFounded = operationService.findOperations(account.getName());

		// Then
		assertNotNull(operationsFounded);
		assertEquals(1, operationsFounded.size());
		assertEquals(operation, operationsFounded.get(0));
		assertEquals(account.getName(), operationsFounded.get(0).getAccount().getName());
		assertEquals(OperationType.DEPOSIT.getTypeOperation(), operationsFounded.get(0).getOperationType());

	}

	@Test
	public void testFindOperations_Failure_400() {
		// When
		assertThatThrownBy(() -> {
			operationService.findOperations(null);
		}).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("entry params is null");

	}

	@Test
	public void testAddOperations_Success() {
		// Given
		Operation operation = Operation.builder().operationType(OperationType.DEPOSIT.getTypeOperation()).value(500)
				.build();
		Mockito.when(operationRepository.save(Mockito.any(Operation.class))).thenReturn(operation);
		// When
		Operation operationAdded = operationService.addOperation(operation);

		// Then
		assertNotNull(operationAdded);
		assertEquals(operation, operationAdded);
		assertEquals(OperationType.DEPOSIT.getTypeOperation(), operationAdded.getOperationType());

	}

	@Test
	public void testAddOperations_Failure_400() {
		// When
		assertThatThrownBy(() -> {
			operationService.addOperation(null);
		}).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("entry params is null");

	}
}
