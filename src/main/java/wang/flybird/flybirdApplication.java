package wang.flybird;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class flybirdApplication {


    public static void main(String[] args) {
    	
        SpringApplication.run(flybirdApplication.class, args);
        
    }
}
