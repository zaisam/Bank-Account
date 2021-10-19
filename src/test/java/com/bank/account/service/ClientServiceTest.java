package com.bank.account.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.bank.account.model.Client;
import com.bank.account.repository.ClientRepository;
import com.bank.service.client.ClientServiceImpl;



@RunWith(MockitoJUnitRunner.Silent.class)
public class ClientServiceTest {

    private static final long CLIENT_ID =  1;
    
    private static final String FIRSTNAME = "firstnameTest";
    
    private static final String LASTNAME = "lastnameTest";
    
	@Spy
	@InjectMocks
	private ClientServiceImpl clientService;
    
	@Mock
	private ClientRepository clientRepository;


    @Before
    public void init() {

        Client
            client =
            Client.builder().id(CLIENT_ID).firstname(FIRSTNAME).lastname(LASTNAME).build();

        Mockito.when(clientRepository.findById(CLIENT_ID)).thenReturn(Optional.of(client));
        Mockito.when(clientRepository.save(Mockito.any(Client.class))).thenReturn(client);
    }

    @Test
    public void create_should_return_success() {
        // Given
    	Client client = Client.builder().build();
    
        // When
    	Client clientCreated = clientService.createClient(client);
        assertNotNull(clientCreated);
        assertNotNull(clientCreated.getId());
        assertEquals(FIRSTNAME, clientCreated.getFirstname());
        assertEquals(LASTNAME, clientCreated.getLastname());

    }
    @Test
    public void create_client_should_return_error_failure_400() {
        // When
		assertThatThrownBy(() -> {
			clientService.createClient(null);
		}).isInstanceOf(IllegalArgumentException.class)
		  .hasMessageContaining("entry params is null");

    }
    
    @Test
    public void find_client_should_return_success() {
        // Given
    	Client client = Client.builder().id(CLIENT_ID).build();
    
        // When
    	Client clienttoFind = clientService.findClient(client.getId());
        assertNotNull(clienttoFind);
        assertNotNull(clienttoFind.getId());
        assertEquals(FIRSTNAME, clienttoFind.getFirstname());
        assertEquals(LASTNAME, clienttoFind.getLastname());

    }
    @Test
    public void find_client_should_retun_error() {
        // When
		assertThatThrownBy(() -> {
			clientService.findClient(null);
		}).isInstanceOf(IllegalArgumentException.class)
		  .hasMessageContaining("entry params is null");


    }

}
