package com.n26;

import com.n26.persistence.Storage;
import com.n26.persistence.mem.MemoryStorage;
import com.n26.service.TransactionService;
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
    public TransactionService service(){
        return TransactionService.create().storage(storage()).build();
    }

    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }

}
