package engine;

import java.util.List;
import org.springframework.data.repository.CrudRepository;


public interface CardRepository extends CrudRepository<Card, String> {

    List<Card> findAllBy(String heroName);
    List<Card> findByName(String name);
}