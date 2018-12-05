package engine;



/*

A FAIRE : FAIRE UNE VERIFICATION DU PLAYER, DE LA CARTE, ... ULTRA IMPORTANT (PLUS UTILISER METHODE POSER CARTE, ...
POUR L'UTILISATION DU MANA




 */
public abstract class Effect {
    protected Card carte;
    protected String description;
    protected String nom;
    protected Effect(Card carte) {
        this.carte = carte;
    }
    public String getDescription() {
        return description;
    }
    public String getNom() {
        return nom;
    }
    /**
     * Les Game en paramètre seront les game courantes.
     */
    /**
     * Effect activé lorsqu'un tour commence.
     * Pré-condition : Un tour débute et la carte est posée sur le terrain
     * Post-condition : Si la carte a un effet en début de tour, cet effet sera executé.
     */
    void turnStartEffect(Game game) {};
    /**
     * Effet activé lorsqu'un tour termine
     * Pré-condition : Un tour se termine et la carte est posée sur le terrain
     * Post-condition : Si la carte a un effet en fin de tour et est posée sur le terrain, cet effet sera executé
     */
    void turnEndEffect(Game game) {};
    /**
     * Effet activé lorsqu'une carte est posé sur le terrain.
     * Pré-condition : Carte posée sur le terrain
     * Post-condition : Si la carte a un effet lorsque posée, cet effet sera executé.
     */
    void putOnBoardEffect(Game game) {};
    void putOnBoardEffect(Game game, Player player) {};
    void putOnBoardEffect(Game game, Player player, Card card) {};
    /**
     * Effet activé lorsque la carte attaque, en général un serviteur.
     * Pré-condition : Le serviteur attaque un autre serviteur ou le héro adverse.
     * Post-condition : L'effet sera executé avant que l'action attaque soit terminée
     */
    void attackEffect(Game game) {};
    /**
     * Effet activé lorqu'une carte meurt, est retirée du plateau
     * Pré-condition : Le serviteur est retiré du terrain parce qu'il à ces points de vie <= 0.
     * Post-condition : L'effet s'active et la carte sera retirée du terrain
     */
    void deathEffect(Game game) {};
    void deathEffect(Game game, Player player) {};
}
class ProvocationEffect extends Effect {
    public ProvocationEffect(Minion carte) {
        super(carte);
        this.nom = "Provocation";
        this.description = "This card will be targeted first";
    }
    /**
     * Ajoute la carte à la liste des cartes ayant provocation dans un tableau propre au joueur
     */
    public void putOnBoardEffect(Game game, Player player) {
        //game.addToProvocList(player, this.card);
    }
    /**
     * Retire la carte à la liste
     */
    public void deathEffect(Game game, Player player) {
        //game.removeToProvocList(player, this.card);
    }
}
class ChargeEffect extends Effect {
    public ChargeEffect(Minion carte) {
        super(carte);
        this.nom = "Charge";
        this.description = "Le serviteur peut attaquer lorsqu'il est posé";
    }
    public void putOnBoardEffect(Game game) {
        //this.carte.setHasAttacked(false);
    }
}
class VolDeVieEffect extends Effect {
    public VolDeVieEffect(Minion carte) {
        super(carte);
        this.nom = "Vol de vie";
        this.description = "Les dégâts infligés par cette carte soigneront du même montant votre héros";
    }
    public void attackEffect(Game game, Player player) {
        player.getHero().setHealth(player.getHero().getHealth() + this.carte.getAttack());
    }
}
class ChefDeRaidEffect extends Effect {
    public ChefDeRaidEffect(Minion carte) {
        super(carte);
        this.nom = "Chef de raid";
        this.description = "Tous les serviteurs aliés du plateau gagnent +1 d'attaque";
    }
    /**
     * A FAIRE !!!!!!!!!!!!!!! A FAIRE !!!!!!! A FAIRE !!!!!!!
     */
}

class ImageMiroirEffect extends Effect {
    public ImageMiroirEffect(Spell carte) {
        super(carte);
        this.nom = "Image Miroir" ;
        this.description = "Invoque deux serviteurs 0/2 avec provocation";
    }
    public void putOnBoardEffect(Game game, Player player) {
        //Minion02 est un minion ayant provocation, 0 d'atk et 2 pv
        //game.getBoard(player).add(new Minion02());
        //game.getBoard(player).add(new Minion02());
        //Ajouter la destruction de la carte
    }
}

class ExplosionDesArcanesEffect extends Effect {
    public ExplosionDesArcanesEffect(Spell carte) {
        super(carte);
        this.nom = "Explosion des arcanes";
        this.description = "Inflige 1 point de dégats à tous les serviteurs adverses";
    }

    public void putOnBoardEffect(Game game, Player player) {
        for (Card card : game.getBoard(player)) {
            if (card instanceof Minion) {
                ((Minion) card).setLife(((Minion) card).getLife()-1);
            }
        }
    }
}

class MetamorphoseEffect extends Effect {
    public MetamorphoseEffect(Spell carte) {
        super(carte);
        this.nom = "Metamorphose";
        this.description = "Transforme un serviteur en serviteur 1/1 sans effet spécial";
    }

    public void putOnBoardEffect(Game game, Player player, Card card) {
        for (Card card1 : game.getBoard(player)) {
            if (card1.equals(card)) {
                game.getBoard(player).remove(card);
                game.getBoard(player).add(new Sheep());
            }
        }
    }
}

class BenedictionDePuissanceEffect extends Effect {
    public BenedictionDePuissanceEffect(Spell card) {
        super(card);
        this.nom = "Benediction de puissance";
        this.description = "Confère +3 d'attaque à un serviteur";
    }

    public void putOnBoardEffect(Game game, Player player, Card card) {

    }
}