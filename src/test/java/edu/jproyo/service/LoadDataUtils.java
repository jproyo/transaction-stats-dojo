package edu.jproyo.service;

import edu.jproyo.model.Transaction;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.stream.IntStream;

public class LoadDataUtils {

    public static void loadTransaction(TransactionService transactionService, double amount, long timestamp){
        transactionService.store(Transaction.create()
                .timestamp(timestamp)
                .amount(new BigDecimal(amount))
                .build());
    }

    public static void loadTransactionsWithoutOldResults(TransactionService transactionService) {
        IntStream.range(1, 11)
                .forEach(i -> loadTransaction(transactionService, 21.32*i, System.currentTimeMillis()-61000));

        Arrays.asList(
                Transaction.create()
                        .timestamp(System.currentTimeMillis()-1000)
                        .amount(new BigDecimal(21.38))
                        .build()
                , Transaction.create()
                        .timestamp(System.currentTimeMillis()-1000)
                        .amount(new BigDecimal(14.6546456))
                        .build()
                , Transaction.create()
                        .timestamp(System.currentTimeMillis()-1000)
                        .amount(new BigDecimal(31.578))
                        .build()
                , Transaction.create()
                        .timestamp(System.currentTimeMillis()-1000)
                        .amount(new BigDecimal(1223221.35558))
                        .build()
                , Transaction.create()
                        .timestamp(System.currentTimeMillis()-1000)
                        .amount(new BigDecimal(757547.354358))
                        .build()
                , Transaction.create()
                        .timestamp(System.currentTimeMillis()-1000)
                        .amount(new BigDecimal(266661.378888))
                        .build()
                , Transaction.create()
                        .timestamp(System.currentTimeMillis()-1000)
                        .amount(new BigDecimal(2232421.12231238))
                        .build()
        ).stream().forEach(transactionService::store);
    }

    public static void loadTransactions(TransactionService transactionService) {
        IntStream.range(1, 201)
                .forEach(i -> loadTransaction(transactionService, 23.98*i, System.currentTimeMillis()-1000));
    }


}
