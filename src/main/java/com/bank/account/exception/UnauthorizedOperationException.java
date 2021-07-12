package com.bank.account.exception;

import com.bank.account.model.Account;
import com.bank.account.model.Operation;

public class UnauthorizedOperationException extends RuntimeException {

    private static final String MESSAGE = "Unauthorized operation value %s on account %s authorized overdraft is %s";

    public UnauthorizedOperationException(Account account, Operation operation) {
        super(String.format(MESSAGE, operation.getValue(), account.getName(), account.getAllowNegativeAmount()));
    }

}
