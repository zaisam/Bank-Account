package helper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.bank.account.controller.AccountController;
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
	
	public static <T> void validate(T o) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<T>> violations = validator.validate(o);

		if (!violations.isEmpty()) {
			List<String> messages = violations.stream().map(ConstraintViolation::getMessage)
					.collect(Collectors.toList());
			String message = String.join(", ", messages);
			ValidatorHelper.log.error("message: " + message);
			throw new IllegalArgumentException();
		}
	}
}
