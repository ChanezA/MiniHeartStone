package engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);
    public static CardRepository repo = null;

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public CommandLineRunner demo(CardRepository repository) {
        return (args) -> {
            // save minions
            repository.save(new Minion("Sanglier de brocheroc","je suis n1",1,1,1,false,false,false,null,null));
            repository.save(new Minion("Chevaucheur de loup", "je suis n2",3, 1, 3, false,false, false,null, null));
            repository.save(new Minion("Chef de raid", "je suis fort",3, 1, 2, false,false, true,null, null));
            repository.save(new Minion("Yéti noroit", "je suis n4",4, 1, 2, false,true, false,null, null));
            repository.save(new Minion("Soldat du compté d'or", "je suis n4",1, 1, 2, false,true, false,null, null));
            // save spells
            repository.save(new Spell(1, "Image miroir", "je suis un spell qui invoque 2 0/2 provoc",null, null));
            repository.save(new Spell(3, "Maîtrise du blocage", "je suis un spell qui pioche",null, null));
            Application.repo = repository;

            // fetch all customers
            LOG.info("Customers found with findAll():");
            LOG.info("-------------------------------");
            for (AbstractCard abstractCard : repository.findAll()) {
                LOG.info(abstractCard.toString());
            }
            LOG.info("");

            // fetch an individual customer by ID
            /*repository.findById(1L)
                    .ifPresent(customer -> {
                        LOG.info("Customer found with findById(1L):");
                        LOG.info("--------------------------------");
                        LOG.info(customer.toString());
                        LOG.info("");
                    });
*/
            // fetch customers by last name
            LOG.info("Customer found with findByLastName('Bauer'):");
            LOG.info("--------------------------------------------");
            repository.findByName("Sanglier brocheroc").forEach(sanglier -> {
                LOG.info(sanglier.toString());
            });
            // for (Customer bauer : repository.findByLastName("Bauer")) {
            // 	LOG.info(bauer.toString());
            // }
            LOG.info("");
        };
    }

}