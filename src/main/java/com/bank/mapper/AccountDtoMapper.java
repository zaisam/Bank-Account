package com.bank.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.bank.account.model.Account;
import com.bank.dto.AccountDTO;



@Mapper
public interface AccountDtoMapper {
	AccountDtoMapper INSTANCE = Mappers.getMapper(AccountDtoMapper.class);
    AccountDTO toDto(Account account);
    Account toEntity(AccountDTO account);
}
