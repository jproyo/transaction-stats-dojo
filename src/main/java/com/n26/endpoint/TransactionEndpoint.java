package com.n26.endpoint;

import com.n26.model.Statistics;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionEndpoint {

    @RequestMapping(value = "/transactions", method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void save(@RequestBody TransactionRequest transactionRaw){

    }

    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    @ResponseBody
    public Statistics get(){
        return Statistics.empty();
    }


    @RequestMapping(value = "/transactions", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(){

    }
}
