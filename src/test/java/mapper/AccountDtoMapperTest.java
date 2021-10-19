package mapper;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.MockitoJUnitRunner;

import com.bank.account.model.Account;
import com.bank.account.model.Client;
import com.bank.dto.AccountDTO;
import com.bank.dto.ClientDTO;
import com.bank.mapper.AccountDtoMapper;

@RunWith(MockitoJUnitRunner.class)
public class AccountDtoMapperTest {

	 private AccountDtoMapper accountDtoMapper = Mappers.getMapper(AccountDtoMapper.class);
	 
	   @Test
	   public void toEntity_should_return_entity_Account() {
		 AccountDTO accountDTO = new AccountDTO();
		 ClientDTO clientDTO = new ClientDTO();
		 clientDTO.setFirstname("testFirstname");
		 clientDTO.setLastname("testLastname");
		 accountDTO.setAllowNegativeAmount(500);
		 accountDTO.setAmount(200);
		 accountDTO.setBalance(100);
		 accountDTO.setClient(clientDTO);

		 Account accountExpected = accountDtoMapper.toEntity(accountDTO);
	     assertEquals(String.valueOf(accountExpected.getAllowNegativeAmount()), String.valueOf(accountDTO.getAllowNegativeAmount()));
	     assertEquals(String.valueOf(accountExpected.getAmount()), String.valueOf(accountDTO.getAmount()));
	     assertEquals(String.valueOf(accountExpected.getBalance()), String.valueOf(accountDTO.getBalance()));
	     assertEquals(String.valueOf(accountExpected.getClient().getFirstname()), String.valueOf(accountDTO.getClient().getFirstname()));
	     assertEquals(String.valueOf(accountExpected.getClient().getLastname()), String.valueOf(accountDTO.getClient().getLastname()));
	  
	   }
	   
	   @Test
	   public void toEntity_should_return_dto_AccountDTO() {
		 Client client= Client.builder().firstname("jean").lastname("pierre").build();
		 Account account = Account.builder().allowNegativeAmount(100).amount(300).balance(10000).client(client).build();
		
		 AccountDTO accountDtoExpected = accountDtoMapper.toDto(account);
	     assertEquals(String.valueOf(accountDtoExpected.getAllowNegativeAmount()), String.valueOf(account.getAllowNegativeAmount()));
	     assertEquals(String.valueOf(accountDtoExpected.getAmount()), String.valueOf(account.getAmount()));
	     assertEquals(String.valueOf(accountDtoExpected.getBalance()), String.valueOf(account.getBalance()));
	     assertEquals(String.valueOf(accountDtoExpected.getClient().getFirstname()), String.valueOf(account.getClient().getFirstname()));
	     assertEquals(String.valueOf(accountDtoExpected.getClient().getLastname()), String.valueOf(account.getClient().getLastname()));
	   }

}
