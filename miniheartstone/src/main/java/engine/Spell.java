package engine;

public abstract class Spell extends Card {

    public Spell(String name, String description, int manaCost, String pictureURL, Effect effect) {
        super(name, description, manaCost, pictureURL, effect);
    }

}
