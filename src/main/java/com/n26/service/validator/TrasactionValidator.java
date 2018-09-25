package com.n26.service.validator;

import com.n26.model.Transaction;

public class TrasactionValidator implements Validator{

    public boolean valid(Transaction transaction){
        
    }

    public boolean isOld(){
        long sixtySecsBefore = now - 60000;
        return getTimestamp() < sixtySecsBefore;
    }

    public boolean isFuture(){
        return getTimestamp() > now;
    }

    public boolean noContent() {
        return getAmount() == null || getTimestamp() == null;
    }

    private boolean validTime() {
        return !isOld() && !isFuture();
    }

    public boolean valid() {
        return !noContent() && validTime();
    }

}
