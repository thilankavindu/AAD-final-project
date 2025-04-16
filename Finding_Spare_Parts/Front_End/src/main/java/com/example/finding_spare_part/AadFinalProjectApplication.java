package com.example.finding_spare_part;

import com.example.finding_spare_part.entity.User;
import com.example.finding_spare_part.repo.UserRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.UUID;
import java.util.logging.Logger;

@SpringBootApplication
public class AadFinalProjectApplication {
    private static final Logger logger = Logger.getLogger(AadFinalProjectApplication.class.getName());

    @PersistenceContext
    private EntityManager entityManager;

    public static void main(String[] args) {
        SpringApplication.run(AadFinalProjectApplication.class, args);
    }

    /**
     * Initialize default admin user if one doesn't exist
     */
    @Bean
    public CommandLineRunner initializeAdminUser(UserRepo userRepo, BCryptPasswordEncoder passwordEncoder, TransactionTemplate transactionTemplate) {
        return args -> {
            boolean adminExists = transactionTemplate.execute(status ->
                    userRepo.existsByEmail("admin@spareparts.com")
            );

            if (!adminExists) {
                logger.info("No admin user found. Creating default admin user...");

                transactionTemplate.execute(status -> {
                    try {
                        // Use direct SQL or native query approach to avoid JPA optimistic locking issues
                        String email = "admin@spareparts.com";
                        String password = passwordEncoder.encode("Admin@123");
                        String role = "ADMIN";
                        String name = "Admin";

                        // Use UUID generation in the database
                        UUID userId = UUID.randomUUID();

                        // Create user directly using EntityManager to avoid potential JPA caching issues
                        User adminUser = new User();
                        adminUser.setUserid(userId);
                        adminUser.setName(name);
                        adminUser.setEmail(email);
                        adminUser.setPassword(password);
                        adminUser.setRole(role);

                        entityManager.persist(adminUser);
                        entityManager.flush();

                        logger.info("Default admin user created successfully.");
                        logger.info("Email: admin@spareparts.com");
                        logger.info("Password: Admin@123");

                        return true;
                    } catch (Exception e) {
                        logger.severe("Failed to create admin user: " + e.getMessage());
                        status.setRollbackOnly();
                        return false;
                    }
                });
            } else {
                logger.info("Admin user already exists. Skipping creation.");
            }
        };
    }
}