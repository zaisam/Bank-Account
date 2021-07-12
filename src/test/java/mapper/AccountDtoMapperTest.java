package mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.bank.account.model.Account;
import com.bank.dto.AccountDTO;
import com.bank.dto.ClientDTO;
import com.bank.mapper.AccountDtoMapper;

@RunWith(MockitoJUnitRunner.class)
public class AccountDtoMapperTest {


		@InjectMocks
		private AccountDtoMapper accountDtoMapper;
		private AccountDTO accountDto;

		private static final double AMOUNT = 100.0;
		private static final String NAME = "MYAccount";
		private static final String NAME2 = "MYAccount2";
		private static final String FIRSTNAME = "myFirstname";
		private static final String LASTNAME = "myLastname";
		private ClientDTO clientDto;
		@Before
		public void setup() {
			accountDto = new AccountDTO();
			clientDto = new ClientDTO();
			clientDto.setFirstname(FIRSTNAME);
			clientDto.setLastname(LASTNAME);
			accountDto.setAmount(AMOUNT);
			accountDto.setClient(clientDto);
			accountDto.setName(NAME);
		}
		
		@Rule
		public ExpectedException thrown = ExpectedException.none();

		@Test
		public void shouldConvertResponseToEntity() {
			// GIVEN
			// WHEN
			Account account = accountDtoMapper.convertToEntity(accountDto);
			// THEN
			assertNotNull(account);
			assertThat(account.getAmount()).isEqualTo(AMOUNT);
			assertThat(account.getName()).isEqualTo(NAME);
			assertThat(account.getClient().getFirstname()).isEqualTo(FIRSTNAME);
			assertThat(account.getClient().getLastname()).isEqualTo(LASTNAME);
		}

		@Test(expected = RuntimeException.class)
		public void shouldThrowExceptionWhenRegistrationDTOIsNull() {
			// GIVEN
			accountDto = null;
			// WHEN
			Account account = accountDtoMapper.convertToEntity(accountDto);
			// THEN
			assertNull(account);
		}

		@Test(expected = AssertionError.class)
		public void shouldThrowExceptionWhenTryingToConvertDifferentName() {
			// GIVEN
			accountDto.setName(NAME2);
			// WHEN
			Account account = accountDtoMapper.convertToEntity(accountDto);
			// THEN
			assertNull(account);
		}
		
		@Test(expected = Exception.class)
		public void shouldThrowExceptionWhenResponseIsNull(){
			accountDtoMapper.convertToEntity(null);
		}


	
}
