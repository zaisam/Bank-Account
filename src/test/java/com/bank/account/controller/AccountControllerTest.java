package com.bank.account.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

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

import com.bank.account.model.Account;
import com.bank.account.model.Operation;
import com.bank.account.model.OperationType;
import com.bank.dto.HistoryDTO;
import com.bank.mapper.AccountDtoMapper;
import com.bank.mapper.ClientDtoMapper;
import com.bank.mapper.OperationDtoMapper;
import com.bank.service.account.IAccountService;
import com.bank.service.client.IClientService;
import com.bank.service.operation.IOperationService;
import com.bank.service.operation.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	private IClientService clientService;
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

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);

	}

	@Test
	public void testCreateAccount_Success() throws Exception {
		String requestJson = "{\"name\":\"myBank\",\"client\":{\"firstname\":\"james\",\"lastname\":\"redriguez\"},\"amount\":500.0}";
		Account Account = new ObjectMapper().readValue(requestJson, Account.class);

		Mockito.when(accountService.createAccount(Mockito.any(Account.class))).thenReturn(Account);

		final MvcResult mvcResult = this.mockMvc
				.perform(post("/bank/create/account").contentType(APPLICATION_JSON_UTF8).content(requestJson))
				.andDo(print()).andExpect(status().isOk()).andReturn();

		assertEquals(requestJson, mvcResult.getResponse().getContentAsString());
		ArgumentCaptor<Account> argAccount = ArgumentCaptor.forClass(Account.class);
		Mockito.verify(accountService).createAccount(argAccount.capture());
		Account account = new ObjectMapper().readValue(requestJson, Account.class);
		assertEquals(String.valueOf(account.getAmount()), String.valueOf(argAccount.getValue().getAmount()));
		assertEquals(account.getName(), argAccount.getValue().getName());
		assertEquals(account.getClient().getFirstname(), argAccount.getValue().getClient().getFirstname());
		assertEquals(account.getClient().getLastname(), argAccount.getValue().getClient().getLastname());
	}

	@Test
	public void testCreateAccount_IllegalArgumentException() throws Exception {
		String requestJson = "{\"name\":\"myBank\",\"client\":{\"firstname\":\"james\",\"lastname\":\"redriguez\"},\"amount\":500.0}";

		final String errMsg = "entry params is null";
		Mockito.when(accountService.createAccount(Mockito.any(Account.class)))
				.thenThrow(new IllegalArgumentException(errMsg));
		try {
			this.mockMvc.perform(post("/bank/create/account").contentType(APPLICATION_JSON_UTF8).content(requestJson))
					.andDo(print()).andExpect(status().isInternalServerError());
		} catch (Exception e) {

			assertThat(e.getMessage().contains(errMsg));

		}
		Account Account = new ObjectMapper().readValue(requestJson, Account.class);
		ArgumentCaptor<Account> argAccount = ArgumentCaptor.forClass(Account.class);
		Mockito.verify(accountService).createAccount(argAccount.capture());
		Account account = new ObjectMapper().readValue(requestJson, Account.class);
		assertEquals(String.valueOf(account.getAmount()), String.valueOf(argAccount.getValue().getAmount()));
		assertEquals(account.getName(), argAccount.getValue().getName());
		assertEquals(account.getClient().getFirstname(), argAccount.getValue().getClient().getFirstname());
		assertEquals(account.getClient().getLastname(), argAccount.getValue().getClient().getLastname());

	}

	@Test
	public void testCreateAccount_Failure() throws Exception {
		String requestJson = "";

		final MvcResult mvcResult = this.mockMvc
				.perform(post("/bank/create/account").contentType(APPLICATION_JSON_UTF8).content(requestJson))
				.andDo(print()).andExpect(status().isBadRequest()).andReturn();

		assertEquals(requestJson, mvcResult.getResponse().getContentAsString());

	}

	@Test
	public void testDeposit_Success() throws Exception {
		String requestJson = "{\"name\":\"accountNameTest\",\"client\":{\"firstname\":\"james\",\"lastname\":\"redriguez\"},\"amount\":500.0}";
		Account account = new ObjectMapper().readValue(requestJson, Account.class);

		Mockito.when(accountService.findAccountsByName(Mockito.any(String.class))).thenReturn(account);

		Mockito.when(transactionService.deposit(Mockito.any(Double.class), Mockito.any(Account.class)))
				.thenReturn(account);

		final MvcResult mvcResult = this.mockMvc
				.perform(post("/bank/account/deposit").contentType(APPLICATION_JSON_UTF8)
						.param("accountName", account.getName()).param("value", "500.0"))
				.andDo(print()).andExpect(status().isOk()).andReturn();

		assertEquals(requestJson, mvcResult.getResponse().getContentAsString());
		ArgumentCaptor<String> argAccountName = ArgumentCaptor.forClass(String.class);
		Mockito.verify(accountService).findAccountsByName(argAccountName.capture());
		assertEquals(String.valueOf(account.getName()), String.valueOf(argAccountName.getValue()));

		ArgumentCaptor<Account> argAccount = ArgumentCaptor.forClass(Account.class);
		ArgumentCaptor<Double> argValue = ArgumentCaptor.forClass(double.class);

		Mockito.verify(transactionService).deposit(argValue.capture(), argAccount.capture());

		assertEquals(String.valueOf(account.getAmount()), String.valueOf(argAccount.getValue().getAmount()));
		assertEquals(account.getName(), argAccount.getValue().getName());
		assertEquals(account.getClient().getFirstname(), argAccount.getValue().getClient().getFirstname());
		assertEquals(account.getClient().getLastname(), argAccount.getValue().getClient().getLastname());

		assertEquals(String.valueOf(account.getAmount()), String.valueOf(argValue.getValue()));

	}

	@Test
	public void testAccountDeposit_Failure() throws Exception {

		String requestJson = "";

		final MvcResult mvcResult = this.mockMvc.perform(post("/bank/account/deposit")
				.contentType(APPLICATION_JSON_UTF8).param("accountName", "").param("value", "")).andDo(print())
				.andExpect(status().isBadRequest()).andReturn();

		assertEquals(requestJson, mvcResult.getResponse().getContentAsString());

	}
	
	@Test
	public void testDeposit_IllegalArgumentException() throws Exception {

		final String errMsg = "entry params is null";

		Mockito.when(accountService.findAccountsByName(Mockito.any(String.class))).thenThrow(new IllegalArgumentException(errMsg));
		Mockito.when(transactionService.deposit(Mockito.any(Double.class), Mockito.any(Account.class)))
		.thenThrow(new IllegalArgumentException(errMsg));
		try {
			final MvcResult mvcResult = this.mockMvc.perform(post("/bank/account/deposit")
					.contentType(APPLICATION_JSON_UTF8).param("accountName", null).param("value", null)).andDo(print())
					.andExpect(status().isInternalServerError()).andReturn();
		} catch (Exception e) {

			assertThat(e.getMessage().contains(errMsg));

		}
	
	}

	@Test
	public void testAccountWithdraw_Success() throws Exception {
		String requestJson = "{\"name\":\"accountNameTest\",\"client\":{\"firstname\":\"james\",\"lastname\":\"redriguez\"},\"amount\":500.0}";
		Account account = new ObjectMapper().readValue(requestJson, Account.class);

		Mockito.when(accountService.findAccountsByName(Mockito.any(String.class))).thenReturn(account);

		Mockito.when(transactionService.withdrawal(Mockito.any(Double.class), Mockito.any(Account.class)))
				.thenReturn(account);

		final MvcResult mvcResult = this.mockMvc
				.perform(post("/bank/account/withdraw").contentType(APPLICATION_JSON_UTF8)
						.param("accountName", account.getName()).param("value", "500.0"))
				.andDo(print()).andExpect(status().isOk()).andReturn();

		assertEquals(requestJson, mvcResult.getResponse().getContentAsString());
		ArgumentCaptor<String> argAccountName = ArgumentCaptor.forClass(String.class);
		Mockito.verify(accountService).findAccountsByName(argAccountName.capture());
		assertEquals(String.valueOf(account.getName()), String.valueOf(argAccountName.getValue()));

		ArgumentCaptor<Account> argAccount = ArgumentCaptor.forClass(Account.class);
		ArgumentCaptor<Double> argValue = ArgumentCaptor.forClass(double.class);

		Mockito.verify(transactionService).withdrawal(argValue.capture(), argAccount.capture());

		assertEquals(String.valueOf(account.getAmount()), String.valueOf(argAccount.getValue().getAmount()));
		assertEquals(account.getName(), argAccount.getValue().getName());
		assertEquals(account.getClient().getFirstname(), argAccount.getValue().getClient().getFirstname());
		assertEquals(account.getClient().getLastname(), argAccount.getValue().getClient().getLastname());

		assertEquals(String.valueOf(account.getAmount()), String.valueOf(argValue.getValue()));

	}

	@Test
	public void testWithdraw_Failure() throws Exception {

		String requestJson = "";

		final MvcResult mvcResult = this.mockMvc.perform(post("/bank/account/withdraw")
				.contentType(APPLICATION_JSON_UTF8).param("accountName", "").param("value", "")).andDo(print())
				.andExpect(status().isBadRequest()).andReturn();

		assertEquals(requestJson, mvcResult.getResponse().getContentAsString());

	}
	
	@Test
	public void testWithdraw_IllegalArgumentException() throws Exception {

		final String errMsg = "entry params is null";

		Mockito.when(accountService.findAccountsByName(Mockito.any(String.class))).thenThrow(new IllegalArgumentException(errMsg));
		Mockito.when(transactionService.withdrawal(Mockito.any(Double.class), Mockito.any(Account.class)))
		.thenThrow(new IllegalArgumentException(errMsg));
		try {
			final MvcResult mvcResult = this.mockMvc.perform(post("/bank/account/withdraw")
					.contentType(APPLICATION_JSON_UTF8).param("accountName", null).param("value", null)).andDo(print())
					.andExpect(status().isInternalServerError()).andReturn();
		} catch (Exception e) {

			assertThat(e.getMessage().contains(errMsg));

		}
	
	}

	@Test
	public void testAccountHistory_Success_deposit() throws Exception {
		Account account = Account.builder().amount(100).build();
		Operation operation = Operation.builder().operationType(OperationType.DEPOSIT.getTypeOperation()).value(500.0)
				.account(account).build();
		Mockito.when(transactionService.getOperations(Mockito.any(String.class))).thenReturn(Arrays.asList(operation));

		final MvcResult mvcResult = this.mockMvc.perform(
				get("/bank/account/history").contentType(APPLICATION_JSON_UTF8).param("accountName", "accountTest"))
				.andDo(print()).andExpect(status().isOk()).andReturn();

		List<HistoryDTO> listHistory = Arrays.asList(
				new ObjectMapper().readValue(mvcResult.getResponse().getContentAsByteArray(), HistoryDTO[].class));
		assertNotNull(listHistory);
		assertEquals(String.valueOf(operation.getAccount().getAmount()), String.valueOf(listHistory.get(0).getAmount()));
		assertEquals(operation.getOperationType(), listHistory.get(0).getOperationType());
		ArgumentCaptor<String> argAccountName = ArgumentCaptor.forClass(String.class);
		Mockito.verify(transactionService).getOperations(argAccountName.capture());
		assertEquals("accountTest", String.valueOf(argAccountName.getValue()));

	}
	@Test
	public void testAccountHistory_Success_withrdaw() throws Exception {
		Account account = Account.builder().amount(100).build();
		Operation operation = Operation.builder().operationType(OperationType.WITHDRAWAL.getTypeOperation()).value(500.0)
				.account(account).build();
		Mockito.when(transactionService.getOperations(Mockito.any(String.class))).thenReturn(Arrays.asList(operation));

		final MvcResult mvcResult = this.mockMvc.perform(
				get("/bank/account/history").contentType(APPLICATION_JSON_UTF8).param("accountName", "accountTest"))
				.andDo(print()).andExpect(status().isOk()).andReturn();

		List<HistoryDTO> listHistory = Arrays.asList(
				new ObjectMapper().readValue(mvcResult.getResponse().getContentAsByteArray(), HistoryDTO[].class));
		assertNotNull(listHistory);
		assertEquals(String.valueOf(operation.getAccount().getAmount()), String.valueOf(listHistory.get(0).getAmount()));
		assertEquals(operation.getOperationType(), listHistory.get(0).getOperationType());
		ArgumentCaptor<String> argAccountName = ArgumentCaptor.forClass(String.class);
		Mockito.verify(transactionService).getOperations(argAccountName.capture());
		assertEquals("accountTest", String.valueOf(argAccountName.getValue()));

	}
	

	
	@Test
	public void testAccountHistory_IllegalArgumentException() throws Exception {

		final String errMsg = "entry params is null";

		Mockito.when(transactionService.getOperations(Mockito.any(String.class))).thenThrow(new IllegalArgumentException(errMsg));

		try {
			final MvcResult mvcResult = this.mockMvc.perform(
					get("/bank/account/history").contentType(APPLICATION_JSON_UTF8).param("accountName", null))
					.andDo(print()).andExpect(status().isInternalServerError()).andReturn();
		} catch (Exception e) {

			assertThat(e.getMessage().contains(errMsg));

		}
	
	}


}
