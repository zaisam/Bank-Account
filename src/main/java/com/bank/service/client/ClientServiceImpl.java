package com.bank.service.client;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.account.model.Client;
import com.bank.account.repository.ClientRepository;

import lombok.experimental.var;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ClientServiceImpl implements IClientService {

	@Autowired
	private ClientRepository clientRepository;

	public Client createClient(Client client) {
		if (client == null) {
			var err = "entry params is null";
			ClientServiceImpl.log.error("Argument checking:" + err);
			throw new IllegalArgumentException(err);
		}
		return clientRepository.save(client);
	}

	public Client findClient(Long id) {
		if (id == null) {
			var err = "entry params is null";
			ClientServiceImpl.log.error("Argument checking:" + err);
			throw new IllegalArgumentException(err);
		}
		Optional<Client> optionalClient = clientRepository.findById(id);
		if (!optionalClient.isPresent()) {
			var err = "Client not found";
			ClientServiceImpl.log.error(err + "for id:" + id);
			throw new IllegalArgumentException(err);
		}

		return optionalClient.get();
	}

}
