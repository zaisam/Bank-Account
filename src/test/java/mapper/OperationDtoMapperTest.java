package mapper;

import static org.junit.Assert.assertEquals;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.MockitoJUnitRunner;

import com.bank.account.model.Account;
import com.bank.account.model.Client;
import com.bank.account.model.Operation;
import com.bank.account.model.OperationType;
import com.bank.dto.HistoryDTO;
import com.bank.mapper.OperationDtoMapper;

@RunWith(MockitoJUnitRunner.class)
public class OperationDtoMapperTest {
	
	final static double AMOUNT_100 = 100;
	final static double AMOUNT_300 = 300;
	final static double AMOUNT_500 = 500;
	final static double ALOOW_NEGATIVE_AMOUNT_300 = -300;
	
	private OperationDtoMapper operationDtoMapper = Mappers.getMapper(OperationDtoMapper.class);
	 

	   @Test
	   public void toEntity_should_return_dto_HistoryDTO() {
			Client client = Client.builder().id(Long.valueOf(1)).build();
			Account account = Account.builder().id(Long.valueOf(1)).amount(AMOUNT_500).date(Instant.now()).client(client).name("SG")
					.allowNegativeAmount(ALOOW_NEGATIVE_AMOUNT_300).build();
			List<Operation> operations = Arrays.asList(
						Operation.builder().account(account).amount(AMOUNT_100)
								.operationType(OperationType.DEPOSIT.getTypeOperation()).date(Instant.now()).build(),
						Operation.builder().account(account).amount(AMOUNT_300)
								.operationType(OperationType.DEPOSIT.getTypeOperation()).date(Instant.now()).build());

	
		List<HistoryDTO> historyDtoExpected = operationDtoMapper.toHistoryDto(operations);
		 
	     assertEquals(historyDtoExpected.size(), operations.size());
	     assertEquals(String.valueOf(historyDtoExpected.get(0).getAmount()), String.valueOf(operations.get(0).getAmount()));
	     assertEquals(historyDtoExpected.get(0).getOperationType(), operations.get(0).getOperationType());
	 
	     
  }


}
