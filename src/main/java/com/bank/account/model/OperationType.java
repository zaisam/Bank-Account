package com.bank.account.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * Enumeration allowing to return the type of operation performed.
 * 
 * @author sazaidi
 *
 */
@AllArgsConstructor
public enum OperationType {
	
	DEPOSIT("deposit"),
	WITHDRAWAL("withdrawal");

	@Getter
	private String  typeOperation;
}
