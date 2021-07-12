package com.bank.dto;

import javax.validation.Valid;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDTO {

	@Valid
	private String firstname;

	@Valid
	private String lastname;
}
