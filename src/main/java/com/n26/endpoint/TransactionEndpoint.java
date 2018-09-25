package com.n26.endpoint;

import com.n26.model.Statistics;
import com.n26.model.StoreResult;
import com.n26.model.Transaction;
import com.n26.service.StatisticsService;
import com.n26.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionEndpoint {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private StatisticsService statisticsService;

    @RequestMapping(value = "/transactions", method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity save(@RequestBody TransactionRequest transactionRaw){
        Transaction toStore = Transaction.create()
                .amount(transactionRaw.getAmount())
                .timestamp(transactionRaw.getTimestamp().getTime()).build();
        StoreResult store = transactionService.store(toStore);
        switch (store){
            case NO_CONTENT:
                return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
            case INVALID_TRANSACTION:
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            case OK:
                return new ResponseEntity(HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    @ResponseBody
    public Statistics get(){
        return statisticsService.get();
    }


    @RequestMapping(value = "/transactions", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(){
        transactionService.deleteAll();
    }
}
