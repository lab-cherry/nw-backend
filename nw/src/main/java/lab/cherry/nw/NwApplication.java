package lab.cherry.nw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@ComponentScan(basePackages = "lab.*")
@Configuration
public class NwApplication {
    public static void main(String[] args) {
        SpringApplication.run(NwApplication.class, args);
    }
}

