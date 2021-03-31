package com.example.entrevueSpringBoot;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.SQLException; 
import java.sql.Statement; 

import java.sql.ResultSet; 

@SpringBootApplication
@RestController
public class EntrevueSpringBootApplication {

        DbController dbcontrol = new DbController();

	public static void main(String[] args) {
		SpringApplication.run(EntrevueSpringBootApplication.class, args);
	}
        // Go to that URL first in order to initialize the test database via H2
        @GetMapping("/createDB")
	public String createDB() {
           String bdComplete = "error";
           dbcontrol.dbcreate();
           bdComplete = dbcontrol.dbReadDatabase();
           return bdComplete;
        }

        // get the requested movie via it's id
        @GetMapping("/api/film/{id}")
	public String getMovie(@PathVariable("id") String id) {
           String bdComplete = "error";
           dbcontrol.dbcreate();
           bdComplete = dbcontrol.dbGetMovie(id);
           return bdComplete;
        }

}
