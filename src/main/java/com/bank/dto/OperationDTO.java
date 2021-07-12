package com.bank.dto;

import java.util.Date;

import javax.validation.Valid;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OperationDTO {
	
	@Valid
    private AccountDTO account;

	@Valid
    private Date date;
	
	@Valid
    private String operationType;

	@Valid
    private double value;
	
	@Valid
    private double amount;


}
