package com.bank.dto;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HistoryDTO {
	
	@Valid
    private Instant date;
	
	@Valid
	private String operationType;

	@Valid
	private double amount;
	
}
