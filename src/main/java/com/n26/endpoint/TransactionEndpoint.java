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

/**
 * Endpoint REST API Controller
 */
@RestController
public class TransactionEndpoint {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private StatisticsService statisticsService;

    /**
     * Save response entity.
     *
     * @param toStore the to store
     * @return the response entity
     */
    @RequestMapping(value = "/transactions", method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity save(@RequestBody Transaction toStore){
        StoreResult store = transactionService.store(toStore);
        return new ResponseEntity(HttpStatus.resolve(store.getStatus()));
    }

    /**
     * Get statistics.
     *
     * @return the statistics
     */
    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    @ResponseBody
    public Statistics get(){
        return statisticsService.get();
    }


    /**
     * Delete.
     */
    @RequestMapping(value = "/transactions", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(){
        transactionService.deleteAll();
    }

    /**
     * Handle bad request response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity handleBadRequest(
            HttpMessageNotReadableException ex) {
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle error parsing json response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler(TransactionParseException.class)
    protected ResponseEntity handleErrorParsingJson(
            TransactionParseException ex) {
        return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
