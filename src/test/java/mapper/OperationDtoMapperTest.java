package mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.bank.account.model.Operation;
import com.bank.account.model.OperationType;
import com.bank.dto.AccountDTO;
import com.bank.dto.ClientDTO;
import com.bank.dto.OperationDTO;
import com.bank.mapper.OperationDtoMapper;

@RunWith(MockitoJUnitRunner.class)
public class OperationDtoMapperTest {

	@InjectMocks
	private OperationDtoMapper operationDtoMapper;
	private OperationDTO operationDto;
	private AccountDTO accountDto;
	private static final String NAME = "MYAccount";
	private static final String NAME2 = "MYAccount2";
	private static final String FIRSTNAME = "myFirstname";
	private static final String LASTNAME = "myLastname";
	private ClientDTO clientDto;

	private static final String OPERATIONTYPE_DEPOSIT = OperationType.DEPOSIT.getTypeOperation();
	private static final String OPERATIONTYPE_WITHDRAW = OperationType.WITHDRAWAL.getTypeOperation();
	private static final double VALUE = 500.0;
	private static final double AMOUNT = 100.0;


	@Before
	public void setup() {

		operationDto = new OperationDTO();
		clientDto = new ClientDTO();
		accountDto = new AccountDTO();
		clientDto.setFirstname(FIRSTNAME);
		clientDto.setLastname(LASTNAME);
		accountDto.setAmount(AMOUNT);
		accountDto.setClient(clientDto);
		accountDto.setName(NAME);

		
		operationDto.setAmount(AMOUNT);
		operationDto.setOperationType(OPERATIONTYPE_DEPOSIT);
		operationDto.setValue(VALUE);
		operationDto.setAccount(accountDto);

	}

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void shouldConvertResponseToEntity() {
		// GIVEN
		// WHEN
		Operation operation = operationDtoMapper.convertToEntity(operationDto);
		// THEN
		assertNotNull(operation);
		assertNotNull(operation.getAccount());
		assertNotNull(operation.getAccount().getClient());
		assertThat(operation.getOperationType()).isEqualTo(OPERATIONTYPE_DEPOSIT);
		assertThat(operation.getValue()).isEqualTo(VALUE);
	}

	@Test(expected = RuntimeException.class)
	public void shouldThrowExceptionWhenRegistrationDTOIsNull() {
		// GIVEN
		operationDto = null;
		// WHEN
		Operation operation = operationDtoMapper.convertToEntity(operationDto);
		// THEN
		assertNull(operation);
	}

	@Test(expected = AssertionError.class)
	public void shouldThrowExceptionWhenTryingToConvertDifferentOerationType() {
		// GIVEN
		operationDto.setOperationType(OPERATIONTYPE_WITHDRAW);
		// WHEN
		Operation operation = operationDtoMapper.convertToEntity(operationDto);
		// THEN
		assertNull(operation);
	}
//
	@Test(expected = Exception.class)
	public void shouldThrowExceptionWhenResponseIsNull() {
		operationDto = null;
		 operationDtoMapper.convertToEntity(operationDto);
	}

}
