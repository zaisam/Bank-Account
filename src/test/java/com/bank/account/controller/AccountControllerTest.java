package com.bank.account.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.eclipse.jdt.internal.compiler.util.Util;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.bank.account.helper.TestHelper;
import com.bank.account.model.Account;
import com.bank.account.model.Client;
import com.bank.account.model.OperationType;
import com.bank.dto.HistoriesDTO;
import com.bank.dto.HistoryDTO;
import com.bank.mapper.AccountDtoMapper;
import com.bank.mapper.ClientDtoMapper;
import com.bank.mapper.OperationDtoMapper;
import com.bank.service.account.IAccountService;
import com.bank.service.operation.IOperationService;
import com.bank.service.operation.TransactionService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {

	private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IAccountService accountService;
	@MockBean
	private IOperationService operationService;
	@MockBean
	private TransactionService transactionService;
	@Mock
	private ClientDtoMapper clientDtoMapper;
	@Mock
	private AccountDtoMapper accountDtoMapper;
	@Mock
	private OperationDtoMapper operationDtoMapper;

	Account account;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);

		account = Account.builder().name("SG").client(Client.builder().firstname("bruno").lastname("michel").build())
				.amount(500.0).balance(500.0).allowNegativeAmount(0.0).build();
	}

	@Test
	public void createAccount_should_return_success_status_200() throws Exception {
		
		Mockito.when(accountService.createAccount(Mockito.any(Account.class))).thenReturn(account);

		final MvcResult mvcResult = this.mockMvc
				.perform(post("/api/bank/create/account").contentType(APPLICATION_JSON_UTF8).content(TestHelper.convertObjectToJsonStrings(account)))
				.andDo(print()).andExpect(status().isOk()).andReturn();

		assertEquals(TestHelper.convertObjectToJsonStrings(account), mvcResult.getResponse().getContentAsString());
		ArgumentCaptor<Account> argAccount = ArgumentCaptor.forClass(Account.class);
		Mockito.verify(accountService).createAccount(argAccount.capture());

		assertEquals(String.valueOf(account.getAmount()), String.valueOf(argAccount.getValue().getAmount()));
		assertEquals(account.getName(), argAccount.getValue().getName());
		assertEquals(account.getClient().getFirstname(), argAccount.getValue().getClient().getFirstname());
		assertEquals(account.getClient().getLastname(), argAccount.getValue().getClient().getLastname());
	}

	@Test
	public void createAccount_should_return_illegalArgumentException_entry_params_is_null() throws Exception {
	
		final String errMsg = "entry params is null";
		Mockito.when(accountService.createAccount(Mockito.any(Account.class)))
				.thenThrow(new IllegalArgumentException(errMsg));
		try {
			this.mockMvc
					.perform(post("/api/bank/create/account").contentType(APPLICATION_JSON_UTF8).content(TestHelper.convertObjectToJsonStrings(account)))
					.andDo(print()).andExpect(status().isInternalServerError());
		} catch (Exception e) {

			assertThat(e.getMessage().contains(errMsg));

		}
		ArgumentCaptor<Account> argAccount = ArgumentCaptor.forClass(Account.class);
		Mockito.verify(accountService).createAccount(argAccount.capture());
		
		assertEquals(String.valueOf(account.getAmount()), String.valueOf(argAccount.getValue().getAmount()));
		assertEquals(account.getName(), argAccount.getValue().getName());
		assertEquals(account.getClient().getFirstname(), argAccount.getValue().getClient().getFirstname());
		assertEquals(account.getClient().getLastname(), argAccount.getValue().getClient().getLastname());

	}

	@Test
	public void createAccount_should_return_failure_status_400() throws Exception {

	
		 
		final MvcResult mvcResult = this.mockMvc
				.perform(post("/api/bank/create/account").contentType(APPLICATION_JSON_UTF8).content(""))
				.andDo(print()).andExpect(status().isBadRequest()).andReturn();

		assertEquals(Util.EMPTY_STRING, mvcResult.getResponse().getContentAsString());

	}

	@Test
	public void createAccount_should_return_illegalArgumentException_entry_params_amount_must_equal_balance() throws Exception {
	
		final String errMsg = "the amount of account must be equal to the balance";
		Mockito.when(accountService.createAccount(Mockito.any(Account.class)))
				.thenThrow(new IllegalArgumentException(errMsg));
		try {
			this.mockMvc
					.perform(post("/api/bank/create/account").contentType(APPLICATION_JSON_UTF8).content(TestHelper.convertObjectToJsonStrings(account)))
					.andDo(print()).andExpect(status().isInternalServerError());
		} catch (Exception e) {

			assertThat(e.getMessage().contains(errMsg));

		}
		ArgumentCaptor<Account> argAccount = ArgumentCaptor.forClass(Account.class);
		Mockito.verify(accountService).createAccount(argAccount.capture());
		
		assertEquals(String.valueOf(account.getAmount()), String.valueOf(argAccount.getValue().getAmount()));
		assertEquals(account.getName(), argAccount.getValue().getName());
		assertEquals(account.getClient().getFirstname(), argAccount.getValue().getClient().getFirstname());
		assertEquals(account.getClient().getLastname(), argAccount.getValue().getClient().getLastname());

	}

	@Test
	public void deposit_should_return_success_statut_200() throws Exception {
	
	
		Mockito.when(transactionService.doDeposit(Mockito.any(String.class), Mockito.any(double.class)))
				.thenReturn(account);

		final MvcResult mvcResult = this.mockMvc
				.perform(put("/api/bank/{accountName}/deposit", account.getName()).contentType(APPLICATION_JSON_UTF8)
						.param("amount", "500.0"))
				.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.balance").value(account.getBalance()))
				.andExpect(jsonPath("$.amount").value(account.getAmount())).andReturn();

		assertEquals(TestHelper.convertObjectToJsonStrings(account), mvcResult.getResponse().getContentAsString());
		ArgumentCaptor<Double> argAmount = ArgumentCaptor.forClass(Double.class);
		ArgumentCaptor<String> argAcountName = ArgumentCaptor.forClass(String.class);

		Mockito.verify(transactionService).doDeposit(argAcountName.capture(), argAmount.capture());
		assertEquals(String.valueOf(account.getAmount()), String.valueOf(argAmount.getValue()));
		assertEquals(account.getName(), argAcountName.getValue());

	}

	@Test
	public void deposit_should_return_failure_status_400() throws Exception {

		final MvcResult mvcResult = this.mockMvc.perform(
				put("/api/bank/{accountName}/deposit", " ").contentType(APPLICATION_JSON_UTF8).param("amount", ""))
				.andDo(print()).andExpect(status().isBadRequest()).andReturn();

		assertEquals(Util.EMPTY_STRING, mvcResult.getResponse().getContentAsString());

	}

	//
	@Test
	public void deposit_should_return_IllegalArgumentException_entry_params_is_null() throws Exception {

		final String errMsg = "entry params is empty";

		Mockito.when(transactionService.doDeposit(Mockito.any(String.class), Mockito.any(Double.class)))
				.thenThrow(new IllegalArgumentException(errMsg));
		try {
			this.mockMvc.perform(
					put("/api/bank/{accountName}/deposit", " ").contentType(APPLICATION_JSON_UTF8).param("amount", ""))
					.andDo(print()).andExpect(status().isBadRequest()).andReturn();
		} catch (Exception e) {

			assertThat(e.getMessage().contains(errMsg));

		}

	}

	@Test
	public void withdraw_should_return_success_200() throws Exception {
	
		Mockito.when(transactionService.doWithdrawal(Mockito.any(String.class), Mockito.any(double.class)))
				.thenReturn(account);

		final MvcResult mvcResult = this.mockMvc
				.perform(put("/api/bank/{accountName}/withdraw", account.getName()).contentType(APPLICATION_JSON_UTF8)
						.param("amount",String.valueOf(account.getAmount())))
				.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.balance").value(account.getBalance()))
				.andExpect(jsonPath("$.amount").value(account.getAmount())).andReturn();

		assertEquals(TestHelper.convertObjectToJsonStrings(account), mvcResult.getResponse().getContentAsString());

		ArgumentCaptor<Double> argAmount = ArgumentCaptor.forClass(Double.class);
		ArgumentCaptor<String> argAcountName = ArgumentCaptor.forClass(String.class);

		Mockito.verify(transactionService).doWithdrawal(argAcountName.capture(), argAmount.capture());

		assertEquals(String.valueOf(account.getAmount()), String.valueOf(argAmount.getValue()));
		assertEquals(account.getName(), argAcountName.getValue());

	}

	@Test
	public void withdraw_should_return_failure_status_400() throws Exception {

		final MvcResult mvcResult = this.mockMvc.perform(
				put("/api/bank/{accountName}/withdraw", " ").contentType(APPLICATION_JSON_UTF8).param("amount", ""))
				.andDo(print()).andExpect(status().isBadRequest()).andReturn();

		assertEquals(Util.EMPTY_STRING, mvcResult.getResponse().getContentAsString());

	}

	//
	@Test
	public void withdraw_should_return_IllegalArgumentException_entry_params_is_null() throws Exception {
		final String errMsg = "entry params is empty";

		Mockito.when(transactionService.doWithdrawal(Mockito.any(String.class), Mockito.any(Double.class)))
				.thenThrow(new IllegalArgumentException(errMsg));
		try {
			this.mockMvc.perform(
					put("/api/bank/{accountName}/withdraw", " ").contentType(APPLICATION_JSON_UTF8).param("amount", ""))
					.andDo(print()).andExpect(status().isBadRequest()).andReturn();
		} catch (Exception e) {

			assertThat(e.getMessage().contains(errMsg));

		}
	}

	@Test
	public void print_history_should_return_success_with_depost_transaction() throws Exception {
		
		HistoryDTO history = new HistoryDTO();
		history.setAmount(600);
		history.setOperationType(OperationType.DEPOSIT.getTypeOperation());

		HistoriesDTO histories = HistoriesDTO.builder().balance(600).operations(Arrays.asList(history)).build();
		Mockito.when(transactionService.printStatement(Mockito.any(String.class), Mockito.any(Integer.class),
				Mockito.any(Integer.class))).thenReturn(histories);

		final MvcResult mvcResult = this.mockMvc
				.perform(get("/api/bank/{accountName}/history", account.getName()).param("page", "1").param("size", "1")
						.contentType(APPLICATION_JSON_UTF8))
				.andDo(print()).andExpect(jsonPath("$.balance").value(histories.getBalance()))
				.andExpect(jsonPath("$.operations.[*].amount").value(histories.getOperations().get(0).getAmount()))
				.andExpect(jsonPath("$.operations.[*].operationType").value(OperationType.DEPOSIT.getTypeOperation()))
				.andExpect(status().isOk()).andReturn();

		assertNotNull(mvcResult);

		ArgumentCaptor<Integer> argPage = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<Integer> argSize = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<String> argAcountName = ArgumentCaptor.forClass(String.class);

		Mockito.verify(transactionService).printStatement(argAcountName.capture(), argPage.capture(),
				argSize.capture());

		assertEquals(account.getName(), argAcountName.getValue());
		assertEquals("1", argSize.getValue().toString());
		assertEquals("1", argPage.getValue().toString());

	}

	@Test
	public void print_history_should_return_success_with_withdrawal_transaction() throws Exception {
		Account account = Account.builder().amount(100).name("SG").build();
		HistoryDTO history = new HistoryDTO();
		history.setAmount(600);
		history.setOperationType(OperationType.WITHDRAWAL.getTypeOperation());

		HistoriesDTO histories = HistoriesDTO.builder().balance(600).operations(Arrays.asList(history)).build();
		Mockito.when(transactionService.printStatement(Mockito.any(String.class), Mockito.any(Integer.class),
				Mockito.any(Integer.class))).thenReturn(histories);

		final MvcResult mvcResult = this.mockMvc
				.perform(get("/api/bank/{accountName}/history", account.getName()).param("page", "1").param("size", "1")
						.contentType(APPLICATION_JSON_UTF8))
				.andDo(print()).andExpect(jsonPath("$.balance").value(histories.getBalance()))
				.andExpect(jsonPath("$.operations.[*].amount").value(histories.getOperations().get(0).getAmount()))
				.andExpect(
						jsonPath("$.operations.[*].operationType").value(OperationType.WITHDRAWAL.getTypeOperation()))
				.andExpect(status().isOk()).andReturn();

		assertNotNull(mvcResult);

		ArgumentCaptor<Integer> argPage = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<Integer> argSize = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<String> argAcountName = ArgumentCaptor.forClass(String.class);

		Mockito.verify(transactionService).printStatement(argAcountName.capture(), argPage.capture(),
				argSize.capture());

		assertEquals(account.getName(), argAcountName.getValue());
		assertEquals("1", argSize.getValue().toString());
		assertEquals("1", argPage.getValue().toString());

	}

	@Test
	public void print_history_should_return_error_status_400() throws Exception {
		String errMsg = "error";

		Mockito.when(transactionService.printStatement(Mockito.any(String.class), Mockito.any(Integer.class),
				Mockito.any(Integer.class))).thenThrow(new IllegalArgumentException(errMsg));

		try {
			this.mockMvc
					.perform(get("/api/bank/{accountName}/history", " ").param("page", "1").param("size", "1")
							.contentType(APPLICATION_JSON_UTF8))
					.andDo(print()).andExpect(status().isBadRequest()).andReturn();
		} catch (Exception e) {

			assertThat(e.getMessage().contains(errMsg));

		}

	}
	

}
