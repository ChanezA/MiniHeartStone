package engine;

public abstract class Spell extends Card {
    public Spell(int manaCost, String name, String pictureURL, String description, Effect effect) {
        super(manaCost, name, pictureURL, description, effect);
    }
}
