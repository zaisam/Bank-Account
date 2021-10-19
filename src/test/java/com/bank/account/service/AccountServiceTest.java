package com.bank.account.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertNotNull;

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
	public void create_should_return_success() {
		// Given
		Account account = Account.builder().build();

		// When
		Account accountCreated = accountService.createAccount(account);
		assertNotNull(accountCreated);
		assertNotNull(accountCreated.getId());
		Mockito.verify(accountService).createAccount(account);
	}
	@Test
	public void create_should_return_error_failure_400() {
		assertThatThrownBy(() -> {
			accountService.createAccount(null);
		}).isInstanceOf(IllegalArgumentException.class)
		  .hasMessageContaining("entry params is null");
		
	
	}
	@Test
	public void should_find_account_by_name_success() {
		// When
		Account accountFinded = accountService.findAccountsByName("accountName");
		assertNotNull(accountFinded);
		assertNotNull(accountFinded.getId());
		Mockito.verify(accountService).findAccountsByName("accountName");
	}
	
	@Test
	public void should_retur_error_find_account_by_name_failure_400() {
		assertThatThrownBy(() -> {
			accountService.findAccountsByName(null);
		}).isInstanceOf(IllegalArgumentException.class)
		  .hasMessageContaining("entry params is null");
		
	}
	
	

}
