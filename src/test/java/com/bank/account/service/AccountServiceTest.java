package com.bank.account.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.bank.account.model.Account;
import com.bank.account.repository.AccountRepository;
import com.bank.service.account.AccountServiceImpl;

import javassist.NotFoundException;

@RunWith(MockitoJUnitRunner.Silent.class)
public class AccountServiceTest {

	private static final long ACCOUNT_ID = 1;

	private static final long AMOUNT = 1;

	@Spy
	@InjectMocks
	private AccountServiceImpl accountService;

	@Mock
	private AccountRepository accountRepository;

	@Before
	public void init() {

		Account account = Account.builder().id(ACCOUNT_ID).amount(AMOUNT).build();

		Mockito.when(accountRepository.findAccountsByName(Mockito.any(String.class))).thenReturn(account);
		Mockito.when(accountRepository.save(Mockito.any(Account.class))).thenReturn(account);
	

	}

	@Test
	public void testCreate_Success() {
		// Given
		Account account = Account.builder().build();

		// When
		Account accountCreated = accountService.createAccount(account);
		assertNotNull(accountCreated);
		assertNotNull(accountCreated.getId());
		Mockito.verify(accountService).createAccount(account);
	}
	@Test
	public void testCreate_Failure_400() {
		assertThatThrownBy(() -> {
			accountService.createAccount(null);
		}).isInstanceOf(IllegalArgumentException.class)
		  .hasMessageContaining("entry params is null");
		
	
	}
	@Test
	public void testFind_Success() {
		// When
		Account accountFinded = accountService.findAccountsByName("accountName");
		assertNotNull(accountFinded);
		assertNotNull(accountFinded.getId());
		Mockito.verify(accountService).findAccountsByName("accountName");
	}
	
	@Test
	public void testFind_Failure_400() {
		assertThatThrownBy(() -> {
			accountService.findAccountsByName(null);
		}).isInstanceOf(IllegalArgumentException.class)
		  .hasMessageContaining("entry params is null");
		
	}
	
	@Test
	public void testDeposit_Success() {
		Account account = Account.builder().amount(400).build();
		Mockito.when(accountRepository.saveAndFlush(Mockito.any(Account.class))).thenReturn(account);
		// When
		Account accountAfterDeposit = accountService.deposit(400, account);
		assertNotNull(accountAfterDeposit);
		assertEquals(String.valueOf(800.0), String.valueOf(accountAfterDeposit.getAmount()));
		assertNotNull(accountAfterDeposit.getAllowNegativeAmount());
		Mockito.verify(accountService).deposit(400, account);
	}
	
	
	@Test
	public void testDeposit_Failure_400() {
		Account account = Account.builder().amount(400).build();
		Mockito.when(accountRepository.saveAndFlush(Mockito.any(Account.class))).thenReturn(account);
		// When
		assertThatThrownBy(() -> {
			accountService.deposit(400, null);
		}).isInstanceOf(IllegalArgumentException.class)
		  .hasMessageContaining("entry params is null");
		
	}
	
	@Test
	public void testwithdrawal_Success() {
		Account account = Account.builder().amount(500).build();
		Mockito.when(accountRepository.saveAndFlush(Mockito.any(Account.class))).thenReturn(account);
		// When
		Account accountAfterWithrdawal = accountService.withdrawal(500, account);
		assertNotNull(accountAfterWithrdawal);
		assertEquals(String.valueOf(0.0), String.valueOf(accountAfterWithrdawal.getAmount()));
		assertNotNull(accountAfterWithrdawal.getAllowNegativeAmount());
		Mockito.verify(accountService).withdrawal(500,account);
	}
	
	@Test
	public void testwithdrawal_Failure_400() {
		Account account = Account.builder().amount(500).build();
		Mockito.when(accountRepository.saveAndFlush(Mockito.any(Account.class))).thenReturn(account);
		// When
		assertThatThrownBy(() -> {
			accountService.withdrawal(400, null);
		}).isInstanceOf(IllegalArgumentException.class)
		  .hasMessageContaining("entry params is null");
		
	}
}
