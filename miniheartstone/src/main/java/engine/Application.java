package engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public CommandLineRunner demo(CardRepository repository) {
        return (args) -> {
            // save a couple of Cards
            repository.save(new Minion("Yéti norois", "cc je suis le yéti",
                    4, 3, 4, null, null));
            repository.save(new Minion("Sanglier brocheroc", "groink",
                    4, 3, 3, null, null));

            // fetch all cards
            log.info("Cards found with findAll():");
            log.info("-------------------------------");
            for (Card card : repository.findAll()) {
                log.info(card.toString());
            }
            log.info("");

            // fetch an individual card by ID
            repository.findById("Yéti norois")
                    .ifPresent(card -> {
                        log.info("Card found with findById(\"Yéti norois\"):");
                        log.info("--------------------------------");
                        log.info(card.toString());
                        log.info("");
                    });
/*
            // fetch customers by last name
            log.info("Customer found with findByLastName('Bauer'):");
            log.info("--------------------------------------------");
            repository.findByName("Bauer").forEach(bauer -> {
                log.info(bauer.toString());
            });
            // for (Customer bauer : repository.findByLastName("Bauer")) {
            // 	log.info(bauer.toString());
            // }
            log.info("");*/
        };
    }

}