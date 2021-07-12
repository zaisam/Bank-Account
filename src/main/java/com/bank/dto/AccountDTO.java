package com.bank.dto;

import java.util.Currency;
import java.util.Locale;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Builder;
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
	private double amount;

}
