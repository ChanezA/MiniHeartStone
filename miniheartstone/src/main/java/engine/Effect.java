package engine;
/*

A FAIRE : FAIRE UNE VERIFICATION DU PLAYER, DE LA CARTE, ... ULTRA IMPORTANT. Il est bon de noter que player sera toujours
currentplayer

 */
public abstract class Effect {
    protected Card carte;
    protected String description;
    protected String nom;
    protected boolean hasTarget;

    protected Effect(Card carte) {
        this.carte = carte;
        hasTarget = false;
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
    void putOnBoardEffect(Game game, Card card) {};
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
    public void putOnBoardEffect(Game game) {
        //game.addToProvocList(game.getCurrentPlayer(), this.card);
    }
    /**
     * Retire la carte à la liste
     */
    public void deathEffect(Game game) {
        //game.removeToProvocList(game.getCurrentPlayer(), this.card);
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
    public void attackEffect(Game game) {
        game.getCurrentPlayer().getHero().setHealth(game.getCurrentPlayer().getHero().getHealth() + this.carte.getAttack());
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
    public void putOnBoardEffect(Game game) {
        //Minion02 est un minion ayant provocation, 0 d'atk et 2 pv
        //game.getBoard(game.getCurrentPlayer()).add(new Minion02());
        //game.getBoard(game.getCurrentPlayer()).add(new Minion02());
    }
}

class ExplosionDesArcanesEffect extends Effect {
    public ExplosionDesArcanesEffect(Spell carte) {
        super(carte);
        this.nom = "Explosion des arcanes";
        this.description = "Inflige 1 point de dégats à tous les serviteurs adverses";
    }

    public void putOnBoardEffect(Game game) {
        for (Card card : game.getBoard(game.getOpponentPlayer(game.getCurrentPlayer()))) {
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
        this.hasTarget = true;
    }

    public void putOnBoardEffect(Game game, Card card) {
        for (Card card1 : game.getBoard(game.getCurrentPlayer())) {
            if (card1.equals(card)) {
                game.getBoard(game.getCurrentPlayer()).remove(card);
                //game.getBoard(game.getCurrentPlayer()).add(new Sheep());
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

    public void putOnBoardEffect(Game game, Card card) {

    }
}