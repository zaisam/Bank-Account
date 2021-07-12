package com.bank.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.bank.account.model.Client;
import com.bank.dto.ClientDTO;

@Service
public class ClientDtoMapper {
private static final ModelMapper modelMapper = new ModelMapper();
	/**
	 * Convert Client to ClientDTO
	 * @param client the client to convert
	 * @return ClientDTO
	 */
	public ClientDTO convertToDTO(Client client) {
		return modelMapper.map(client, ClientDTO.class);
	}	
	/**
	 * Convert ClientDTO to Client
	 * @param clientDTO the clientDTO to convert
	 * @return Client
	 */
	public Client convertToEntity(ClientDTO clientDTO) {	
		
		return modelMapper.map(clientDTO, Client.class);
		
	}
	
	
}
