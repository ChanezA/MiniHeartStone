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
            /*repository.save(new Minion("Chef de raid","bonjour je suis un chef de  raid",3,2,2,null,true,false,false));
            repository.save(new Minion(3, 3,2,"Chevaucheur de loup","bonjour je suis un chevaucher de loup",null,true, false, true));
            repository.save(new Minion(4, 2,2,"Sanglier brocheroc","groink",null,false, false, true));
            repository.save(new Minion(1, 2,2,"Soldat du compté-de-l'or","bonjour je suis un soldat du compté-de-l'or",null,false, false, false));
            repository.save(new Minion(5, 3,3,"Yéti noroit","cc",null,false, false, true));
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