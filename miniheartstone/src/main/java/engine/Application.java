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
    public static CardRepository repo = null;

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public CommandLineRunner demo(CardRepository repository) {
        return (args) -> {
            // save a couple of customers
            /*repository.save(new Minion("Chef de raid", "bonjour je suis un chef de raid",2,2,3,null,null));
            repository.save(new Minion("Chef de raid", "bonjour je suis un chevaucheur de loup",1,3,3,null,null));
            repository.save(new Minion("Sanglier brocheroc", "groink",1,1,1,null,null));
            repository.save(new Minion("Soldat du compté-de-l'or", "bonjour je suis un soldat du compté-de-l'or",2,1,1,null,null));
            repository.save(new Minion("Yéti noroit", "cc",5,4,4,null,null));
*/
            Application.repo = repository;

            // fetch all customers
            log.info("Customers found with findAll():");
            log.info("-------------------------------");
            for (Card card : repository.findAll()) {
                log.info(card.toString());
            }
            log.info("");

            // fetch an individual customer by ID
            /*repository.findById(1L)
                    .ifPresent(customer -> {
                        log.info("Customer found with findById(1L):");
                        log.info("--------------------------------");
                        log.info(customer.toString());
                        log.info("");
                    });
*/
            // fetch customers by last name
            log.info("Customer found with findByLastName('Bauer'):");
            log.info("--------------------------------------------");
            repository.findByName("Sanglier brocheroc").forEach(sanglier -> {
                log.info(sanglier.toString());
            });
            // for (Customer bauer : repository.findByLastName("Bauer")) {
            // 	log.info(bauer.toString());
            // }
            log.info("");
        };
    }

}