package com.barbenheimer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class BarbenheimerApplication {

    
    /** 
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(BarbenheimerApplication.class, args);
    }

}
