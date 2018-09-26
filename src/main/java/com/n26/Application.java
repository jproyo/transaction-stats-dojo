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

/**
 * Spring Boot main entry point application
 */
@SpringBootApplication
public class Application {

    /**
     * Storage storage.
     *
     * @return the storage
     */
    @Bean
    public Storage storage(){
        return new MemoryStorage();
    }

    /**
     * Config config.
     *
     * @return the config
     */
    @Bean
    public Config config(){
        return new Config();
    }

    /**
     * Validator transaction validator.
     *
     * @return the transaction validator
     */
    @Bean
    public TransactionValidator validator(){
        return new TransactionValidator();
    }

    /**
     * Transaction service transaction service.
     *
     * @return the transaction service
     */
    @Bean
    public TransactionService transactionService(){
        return new TransactionServiceImpl();
    }

    /**
     * Statistics service statistics service.
     *
     * @return the statistics service
     */
    @Bean
    public StatisticsService statisticsService(){
        return new StatisticsServiceImpl();
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }

}
