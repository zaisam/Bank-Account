package mapper;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.MockitoJUnitRunner;

import com.bank.account.model.Client;
import com.bank.dto.ClientDTO;
import com.bank.mapper.ClientDtoMapper;

@RunWith(MockitoJUnitRunner.class)
public class ClientDtoMapperTest {

	 private ClientDtoMapper clientDtoMapper = Mappers.getMapper(ClientDtoMapper.class);
	 
	   @Test
	   public void toEntity_should_return_entity_Client() {
		 ClientDTO clientDTO = new ClientDTO();
		 clientDTO.setFirstname("testFirstname");
		 clientDTO.setLastname("testLastname");

		 Client clientExpected = clientDtoMapper.toEntity(clientDTO);
		 
	     assertEquals(String.valueOf(clientExpected.getFirstname()), String.valueOf(clientDTO.getFirstname()));
	     assertEquals(String.valueOf(clientExpected.getLastname()), String.valueOf(clientDTO.getLastname()));
	  
	   }
	   
	   @Test
	   public void toEntity_should_return_dto_ClientDTO() {
		 Client client= Client.builder().firstname("jean").lastname("pierre").build();
	
		 ClientDTO clientDtoExpected = clientDtoMapper.toDto(client);
		 
	     assertEquals(String.valueOf(clientDtoExpected.getFirstname()), String.valueOf(client.getFirstname()));
	     assertEquals(String.valueOf(clientDtoExpected.getLastname()), String.valueOf(client.getLastname()));
	   }


}


	

