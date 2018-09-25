package com.n26.service.validator;

import com.n26.model.Transaction;

public interface Validator {

    boolean valid(Transaction transaction);

}
