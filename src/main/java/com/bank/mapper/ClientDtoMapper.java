package com.bank.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.bank.account.model.Client;
import com.bank.dto.ClientDTO;


@Mapper
public interface ClientDtoMapper {
	ClientDtoMapper INSTANCE = Mappers.getMapper(ClientDtoMapper.class);
    ClientDTO toDto(Client client);
    Client toEntity(ClientDTO cleintDto);
}

