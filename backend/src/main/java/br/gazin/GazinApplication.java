package br.gazin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"br.gazin.application.*"})
public class GazinApplication {
    public static void main(String[] args) {
        SpringApplication.run(GazinApplication.class, args);
    }
}
