package todolist;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * The entry point of the Spring Boot application.
 *
 * @SpringBootApplication enables:
 * 1. Component Scanning (@ComponentScan) - Finds your Controllers, Services, and Repositories.
 * 2. Auto-Configuration (@EnableAutoConfiguration) - Automatically configures JPA, H2, and Web setups based on your pom.xml.
 * 3. Configuration Class (@Configuration) - Allows you to register extra beans.
 */
@SpringBootApplication
@EnableJpaAuditing // Enables automatic timestamp tracking
public class TodoListApplication {

    public static void main(String[] args) {
        // Launches the embedded Tomcat server and initializes the Spring application context
        SpringApplication.run(TodoListApplication.class, args);
    }
}

