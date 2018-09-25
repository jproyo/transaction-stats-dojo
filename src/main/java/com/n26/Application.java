package com.n26;

import com.n26.config.Config;
import com.n26.persistence.Storage;
import com.n26.persistence.mem.MemoryStorage;
import com.n26.service.StatisticsService;
import com.n26.service.TransactionService;
import com.n26.service.impl.StatisticsServiceImpl;
import com.n26.service.impl.TransactionServiceImpl;
import com.n26.service.validator.TransactionValidator;
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
    public Config config(){
        return new Config();
    }

    @Bean
    public TransactionValidator validator(){
        return new TransactionValidator();
    }

    @Bean
    public TransactionService transactionService(){
        return new TransactionServiceImpl();
    }

    @Bean
    public StatisticsService statisticsService(){
        return new StatisticsServiceImpl();
    }

    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }

}
