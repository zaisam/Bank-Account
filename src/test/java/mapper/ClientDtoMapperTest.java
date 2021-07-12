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
import com.bank.account.model.Client;
import com.bank.dto.AccountDTO;
import com.bank.dto.ClientDTO;
import com.bank.mapper.AccountDtoMapper;
import com.bank.mapper.ClientDtoMapper;
@RunWith(MockitoJUnitRunner.class)
public class ClientDtoMapperTest {

	@InjectMocks
	private ClientDtoMapper clientDtoMapper;
	private ClientDTO clientDto;


	private static final String FIRSTNAME = "myFirstname";
	private static final String LASTNAME = "myLastname";
	
	private static final String FIRSTNAME2 = "myFirstname2";
	private static final String LASTNAME2 = "myLastname2";

	@Before
	public void setup() {
		
		clientDto = new ClientDTO();
		clientDto.setFirstname(FIRSTNAME);
		clientDto.setLastname(LASTNAME);
		
	}
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void shouldConvertResponseToEntity() {
		// GIVEN
		// WHEN
		Client client = clientDtoMapper.convertToEntity(clientDto);
		// THEN
		assertNotNull(client);
		assertThat(client.getFirstname()).isEqualTo(FIRSTNAME);
		assertThat(client.getLastname()).isEqualTo(LASTNAME);
	}

	@Test(expected = RuntimeException.class)
	public void shouldThrowExceptionWhenRegistrationDTOIsNull() {
		// GIVEN
		clientDto = null;
		// WHEN
		Client client = clientDtoMapper.convertToEntity(clientDto);
		// THEN
		assertNull(client);
	}

	@Test(expected = AssertionError.class)
	public void shouldThrowExceptionWhenTryingToConvertDifferentFirstname() {
		// GIVEN
		clientDto.setFirstname(FIRSTNAME2);
		// WHEN
		Client client = clientDtoMapper.convertToEntity(clientDto);
		// THEN
		assertNull(client);
	}
	
	@Test(expected = Exception.class)
	public void shouldThrowExceptionWhenResponseIsNull(){
		clientDtoMapper.convertToEntity(null);
	}


	

}


	

