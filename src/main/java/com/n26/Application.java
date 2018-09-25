package com.n26;

import com.n26.persistence.Storage;
import com.n26.persistence.mem.MemoryStorage;
import com.n26.service.StatisticsService;
import com.n26.service.TransactionService;
import com.n26.service.impl.StatisticsServiceImpl;
import com.n26.service.impl.TransactionServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    @Bean
    public Storage storage(){
        return new MemoryStorage();
    }

    @Bean
    public TransactionService transactionService(){
        return TransactionServiceImpl.create().storage(storage()).build();
    }

    @Bean
    public StatisticsService statisticsService(){
        return StatisticsServiceImpl.create().storage(storage()).build();
    }

    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }

}
