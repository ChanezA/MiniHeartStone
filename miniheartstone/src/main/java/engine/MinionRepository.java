package engine;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface MinionRepository extends CrudRepository<Minion, String> {

    List<AbstractCard> findByName(String name);
    List<AbstractCard> findByHeroName(String heroName);
}