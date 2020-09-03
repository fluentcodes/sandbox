package fluentcodes.sandbox;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.annotation.PostConstruct;

/**
 * Created by werner.diwischek on 11.12.17.
 */

//https://stackoverflow.com/questions/24661289/spring-boot-not-serving-static-content
@SpringBootApplication
public class Start {
    public static void main(String[] args) {
        SpringApplication.run(fluentcodes.sandbox.Start.class, args);
    }

    @PostConstruct
    public void init() {
        System.out.println("Init Something");
    }
}

