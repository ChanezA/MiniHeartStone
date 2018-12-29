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
            // save a couple of minions

            repository.save(new Minion("Sanglier de brocheroc","je suis n1",1,1,1,false,false,false,null));
            repository.save(new Minion("Chevaucheur de loup", "je suis n2",3, 1, 3, false,false, false, null));
            repository.save(new Minion("Chef de raid", "je suis fort",3, 1, 2, false,false, true, null));
            repository.save(new Minion("Yéti noroit", "je suis n4",4, 1, 2, false,true, false, null));
            repository.save(new Minion("Soldat du compté d'or", "je suis n4",1, 1, 2, false,true, false, null));
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