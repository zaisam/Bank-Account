package com.bank.dto;

import java.util.List;

import javax.validation.Valid;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HistoriesDTO {
	@Valid
    private double balance;
	@Valid
	List<HistoryDTO> operations;
	
}
