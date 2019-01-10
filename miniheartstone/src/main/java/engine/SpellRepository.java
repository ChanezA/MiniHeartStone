package engine;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface SpellRepository extends CrudRepository<Spell, String> {

    List<AbstractCard> findByName(String name);
    List<AbstractCard> findByHeroName(String heroName);
}