package com.n26.endpoint;

import com.n26.endpoint.serialization.TransactionParseException;
import com.n26.model.Statistics;
import com.n26.model.StoreResult;
import com.n26.model.Transaction;
import com.n26.service.StatisticsService;
import com.n26.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionEndpoint {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private StatisticsService statisticsService;

    @RequestMapping(value = "/transactions", method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity save(@RequestBody Transaction toStore){
        StoreResult store = transactionService.store(toStore);
        return new ResponseEntity(HttpStatus.resolve(store.getStatus()));
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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity handleBadRequest(
            HttpMessageNotReadableException ex) {
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TransactionParseException.class)
    protected ResponseEntity handleErrorParsingJson(
            TransactionParseException ex) {
        return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
