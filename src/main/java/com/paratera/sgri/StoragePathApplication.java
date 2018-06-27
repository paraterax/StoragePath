package com.paratera.sgri;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.paratera.sgri.service.DealDataService;


@SpringBootApplication
public class StoragePathApplication implements CommandLineRunner {

    @Autowired
    private DealDataService dealService;

    @Override
    public void run(String... args) throws Exception {
        dealService.timerTask();
    }

    public static void main(String[] args) {
        SpringApplication.run(StoragePathApplication.class, args);
    }

}
