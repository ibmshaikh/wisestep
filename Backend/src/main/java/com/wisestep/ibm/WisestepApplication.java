package com.wisestep.ibm;

import com.wisestep.ibm.repository.user.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = UserRepository.class)
public class WisestepApplication {

    public static void main(String[] args) {
        SpringApplication.run(WisestepApplication.class, args);
    }

}
