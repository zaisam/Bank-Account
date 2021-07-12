package helper;

import com.bank.dto.AccountDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ValidatorHelper {

	private ValidatorHelper() {

	}
	
	public static void validateAccount(AccountDTO accountDto) {
		String errorMessage = "AccountDTO cannot be null";
		if (accountDto ==  null) {
			throw new IllegalArgumentException(errorMessage);
		}
	}
}
