package com.bank.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.bank.account.model.Account;
import com.bank.account.model.Client;
import com.bank.dto.AccountDTO;

@Service
public class AccountDtoMapper {
	private static final ModelMapper modelMapper = new ModelMapper();
	/**
	 * Convert Account to AccountDTO
	 * @param Account the 	account to convert
	 * @return AccountDTO
	 */
	public AccountDTO convertToDTO(Account Account) {
		return modelMapper.map(Account, AccountDTO.class);
	}	
	/**
	 * Convert AccountDTO to account
	 * @param AccountDTO  the accountDTO to convert
	 * @return Account
	 */
	public Account convertToEntity(AccountDTO AccountDTO) {	
		
		
		return modelMapper.map(AccountDTO, Account.class);

		
		
	}
}
