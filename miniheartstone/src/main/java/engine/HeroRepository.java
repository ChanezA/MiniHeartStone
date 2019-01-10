package engine;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface HeroRepository extends CrudRepository<AbstractHero, String> {

    List<AbstractCard> findByHeroName(String heroName);
}