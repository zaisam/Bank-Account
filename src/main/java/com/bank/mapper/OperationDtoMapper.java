package com.bank.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.bank.account.model.Client;
import com.bank.account.model.Operation;
import com.bank.dto.ClientDTO;
import com.bank.dto.HistoryDTO;
import com.bank.dto.OperationDTO;
import com.mysql.fabric.xmlrpc.base.Array;

@Service
public class OperationDtoMapper {
	private static final ModelMapper modelMapper = new ModelMapper();
	/**
	 * Convert List of Operation to List of HistoryDTO
	 * @param operations list operation to convert
	 * @return List<HistoryDTO>
	 */
	public List<HistoryDTO> convertToDTO(List<Operation> operations) {
		List<HistoryDTO> historyDto = new ArrayList<>();
		operations.forEach(operation -> historyDto.add(modelMapper.map(operation, HistoryDTO.class)));
		historyDto.forEach(history -> history.setAmount(operations.get(0).getAccount().getAmount()));
		return historyDto;
	}
	/**
	 * Convert List of HistoryDTO to List of Operation
	 * @param operationDTO list OperationDTO to convert
	 * @return List<Operation>
	 */
	public List<Operation> convertToEntity(List<OperationDTO> operationDTO) {

		List<Operation> operations  = new ArrayList<>();
		operationDTO.forEach(operationDto -> operations.add(modelMapper.map(operationDto, Operation.class)));
		return operations;

	}
	/**
	 * Convert operation to OperationDTO
	 * @param operation the Operation to convert
	 * @return OperationDTO
	 */
	public OperationDTO convertToDTO(Operation operation) {
		return modelMapper.map(operation, OperationDTO.class);
	}
	/**
	 * Convert OperationDTO to Operation
	 * @param OperationDTO the OperationDTO to convert
	 * @return Operation
	 */
	public Operation convertToEntity(OperationDTO operationDTO) {

		return modelMapper.map(operationDTO, Operation.class);

	}
}
