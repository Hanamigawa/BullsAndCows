package ztq.guessnumber2;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author tianqi.zhu
 */
@SpringBootApplication
public class App {
    public static void main(String[] args) {
        System.out.println("Started");
        SpringApplication.run(App.class, args);
        System.out.println("Finished");
    }
}
