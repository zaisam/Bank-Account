package com.bank.dto;

import java.time.Instant;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class AccountDTO {

	@Valid
	private String name;

	@Valid
	private ClientDTO client;

	@Valid
	private double balance;
	
	@Valid
	private double amount;
	

    @JsonIgnore
    private Instant date = Instant.now();
    
    @JsonIgnore
    private double allowNegativeAmount = -500;
	
	

}
