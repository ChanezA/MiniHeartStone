package engine;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CardRepository extends CrudRepository<AbstractCard, String> {

    List<AbstractCard> findByName(String name);
    List<AbstractCard> findByHeroName(String heroName);
}