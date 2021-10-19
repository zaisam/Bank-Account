package com.bank.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.bank.account.model.Operation;
import com.bank.dto.HistoryDTO;

@Mapper
public interface OperationDtoMapper {
	OperationDtoMapper INSTANCE = Mappers.getMapper(OperationDtoMapper.class);
	
	List<HistoryDTO> toHistoryDto(List<Operation> operations);
	
}