package com.bank.service.client;

import com.bank.account.model.Client;

public interface IClientService {
	
	Client createClient(Client client);
	
	Client findClient(Long id);
}
