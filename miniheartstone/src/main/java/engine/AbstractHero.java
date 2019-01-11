package engine;

import java.util.ArrayList;
import java.util.UUID;

import exception.MiniHeartStoneException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * <b>Classe abstraite représentant les différents héros de notre jeu.</b>
 *
 * @author Amri Chanez, Baron Alan, Pineau Alexis, Questel Louis.
 * @version 1.0
 */
@Entity
public abstract class AbstractHero {

    @Column(name = "description")
    protected String description;
    @Id
    @Column(name = "heroName")
    protected String heroName;

    //public static final int MANA_MAX= 10;
    public static final int VIE_MAX = 30;
    @Transient
    protected int mana;
    @Transient
    protected UUID heroUUID;
    @Transient
    protected int health;
    @Transient
    protected int armor;
    /*liste toutes les cartes que le heros peut avoir
    à recuperer dans la base de données.*/
    @Transient
    protected ArrayList<AbstractCard> deck = new ArrayList<AbstractCard>();
    @Transient
    protected ArrayList<AbstractCard> hand = new ArrayList<AbstractCard>();
    @Transient
    protected ArrayList<Minion> board = new ArrayList<Minion>();

    @Transient
    protected boolean looser = false;
    @Transient
    protected boolean winner = false;

    /**
     * Instantiates the hero with the given parameters, without mana and armor,
     * and with a random UUID.
     * @param hname The name of the hero
     * @param vie The hero's health
     * @param desc The mana cost of the card
     */
    protected AbstractHero(final String hname, final int vie,
                           final String desc) {
        this.heroName = hname;
        this.health = vie;
        this.description = desc;
        this.mana = 0;
        this.armor = 0;
        this.heroUUID = UUID.randomUUID();

        // Retrieving card with Spring
        CardRepository repo = Application.repo;
        // Cards available only for this hero
        for (AbstractCard abstractCard : repo.findByHeroName(this.heroName)) {
            this.deck.add(abstractCard);
        }
        // Cards available for all heros
        for (AbstractCard abstractCard : repo.findByHeroName(null)) {
            this.deck.add(abstractCard);
        }
    }
    /**
     * Used when the hero use its heroic power.
     */
    public abstract void power();

    /**
     * Allow the player to draw a card from the card pool.
     * @return Card crd
     */

    public AbstractCard draw() {
        int random = (int) (Math.random() * (deck.size()));
        AbstractCard crd = null;
        try {
            crd = deck.get(random).cloneCard();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        hand.add(crd);
        return crd;
    }
    /**
     * Permet d'invoquer une créature ou un spell selon son id.
     * @param cardID L'UUID de la carte que l'on veut invoquer
     * @throws MiniHeartStoneException when the card isn't on the player's hand
     * or the player doesn't have enough mana
     */
    public void invock(final UUID cardID)
            throws MiniHeartStoneException {
        try {
            if (this.isOnMyHand(cardID)) {
                // si le joueur à le mana suffisant
                if (this.getCardFromHandByUUID(cardID).getManaCost() <= this.getMana()) {
                    // on retire le mana au joueur
                    this.setMana(this.getMana() - this.getCardFromHandByUUID(cardID).getManaCost());
                    // si c'est un minion
                    if (this.getCardFromHandByUUID(cardID) instanceof Minion) {
                        //System.out.println("est-ce que je passe ici");
                        AbstractCard abstractCard = this.getCardFromHandByUUID(cardID);
                        hand.remove(abstractCard);
                        // si ma carte est un chef de raid +1 att a toutes les cretures du board
                        if (abstractCard.getName().equals("Chef de raid")) {
                            for (int i = 0; i < board.size(); i++) {
                                AbstractCard miniminion = this.getBoard().get(i);
                                ((Minion) miniminion).setAttack(((Minion) miniminion).getAttack() + 1);
                            }
                        }
                        // ajout des pts d'attack en fonction du nombre de chef de raids presents sur le plateau alli�
                        ((Minion) abstractCard).setAttack(((Minion) abstractCard).getAttack() + this.howManyChefDeRaidInMyBoard());
                        board.add((Minion)abstractCard);
                        // si la carte a charge
                        if (((Minion) abstractCard).getHasCharge()) {
                            ((Minion) abstractCard).setReadyToAttack(true);
                        }
                        //si c'est un spell
                    } else {
                        Spell spell = (Spell) (this.getCardFromHandByUUID(cardID));
                        // si c'est image miroir
                        if (spell.getName().equals("Image miroir")) {
                            AbstractCard one = new Minion("Soldat", "je suis n4", 1, 0, 2, true, false, false, null, null);
                            ((Minion) one).setAttack(((Minion) one).getAttack() + this.howManyChefDeRaidInMyBoard());
                            AbstractCard two = new Minion("Soldat", "je suis n4", 1, 0, 2, true, false, false, null, null);
                            ((Minion) two).setAttack(((Minion) two).getAttack() + this.howManyChefDeRaidInMyBoard());
                            this.getBoard().add((Minion)one);
                            this.getBoard().add((Minion)two);

                            this.getHand().remove(this.getCardFromHandByUUID(cardID));
                        }
                        // si c'est maitrise du blocage
                        if (spell.getName().equals("Maîtrise du blocage")) {
                            this.getHand().remove(this.getCardFromHandByUUID(cardID));
                            this.setArmor(this.getArmor() + 5);
                            this.draw();
                        }
                    }
                } else {
                // si le joueur n'a pas le mana suffisant
                    throw new MiniHeartStoneException("Vous n'avez pas le mana suffisant");
                }
            } else {
            // si la carte n'est pas en main
                throw new MiniHeartStoneException("Vous n'avez pas cette carte en main");
            }
        } catch (MiniHeartStoneException e) {
            System.out.println(e.toString());
        }
    }

    /**
     * When the hero is under attack, this method is used by the engine.
     * @param degats Damages put on the hero by its opponent.
     */
    public void myHeroHasBeenAttack(final int degats) {
        // si j'ai de l'armure
        if (this.getArmor() > 0) {
            int diff = this.getArmor() - degats;
            // si les degats son supp�rieur � l'armure
            if (diff < 0) {
                this.setArmor(0);
                // je fais + car diff est ici n�gatif
                this.setHealth(this.getHealth() + diff);
            // si l'armure suffit
            } else {
                this.setArmor(diff);
            }
        } else {
        // si je n'ai pas d'armure
            this.setHealth(this.getHealth() - degats);
        }
        // je verifie que le hero n'est pas mort
        if (this.getHealth() <= 0) {
            looser = true;
        }
    }

    /**
     * Used when a card is under attack.
     * @param carteAttaqueeUUID UUID of the card under attack
     * @param degats Damages put on the card
     */
    public void hasBeenAttack(final UUID carteAttaqueeUUID, final int degats) {
        try {
            // si la créature est bien sur mon board
            if (this.isOnMyBoard(carteAttaqueeUUID)) {
                Minion min = (Minion) (this.getCardFromBoardByUUID(carteAttaqueeUUID));
                min.setLife(min.getLife() - degats);

                if (min.getLife() <= 0) {
                    this.getBoard().remove(min);
                    if (min.getName().equals("Chef de raid")) {
                        for (int i = 0; i < board.size(); i++) {
                            AbstractCard miniminion = this.getBoard().get(i);
                            ((Minion) miniminion).setAttack(((Minion) miniminion).getAttack() - 1);
                        }
                    }
                }
            } else {
            // si la créature n'est pas sur mon board
                throw new MiniHeartStoneException("Cette créature n'est pas sur le borad adverse");
            }
        } catch (MiniHeartStoneException e) {
            System.out.println(e.toString());
        }
    }

    /**
     * Vérifie si la carte donnée est dans la main de notre héros.
     * @param cardID UUID de la carte qu'on veut tester
     * @return true si la carte est présente dans la main, false sinon
     */
    public boolean isOnMyHand(final UUID cardID) {
        boolean present = false;
        for (AbstractCard abstractCard : hand) {
            if (abstractCard.getCardUUID() == cardID) {
                present = true;
                break;
            }
        }
        return present;
    }

    /**
     * Vérifie si la carte donnée est sur le plateau de notre héros.
     * @param cardID UUID de la carte qu'on veut tester
     * @return true si la carte est présente sur le board, false sinon
     */
    public boolean isOnMyBoard(final UUID cardID) {
        boolean present = false;
        for (AbstractCard abstractCard : board) {
            if (abstractCard.getCardUUID() == cardID) {
                present = true;
                break;
            }
        }
        return present;
    }

    /**
     * Retrourne la carte de la main en fonction de l'UUID donné (si existe).
     * @param cardID UUID de la carte que l'on veut avoir
     * @return la carte demandée si elle existe dans la main, rien sinon
     */
    public AbstractCard getCardFromHandByUUID(final UUID cardID) {
        for (AbstractCard abstractCard : hand) {
            if (abstractCard.getCardUUID() == cardID) {
                return abstractCard;
            }
        }
        return null;
    }

    /**
     * Retrourne la carte du board en fonction de l'UUID donné (si existe).
     * @param cardID UUID de la carte que l'on veut avoir
     * @return la carte demandée si elle existe sur le board, rien sinon
     */
    public AbstractCard getCardFromBoardByUUID(final UUID cardID) {
        for (AbstractCard abstractCard : board) {
            if (abstractCard.getCardUUID() == cardID) {
                return abstractCard;
            }
        }
        return null;
    }

    /**
     * Calcul le nombre de Minions "Chef de Raid" pour augmenter l'attaque des Minions sur le board en conséquence.
     * @return le nombre de Chef de Raid sur le board
     */
    public int howManyChefDeRaidInMyBoard() {
        int count = 0;
        for (int i = 0; i < board.size(); i++) {
            if (this.getBoard().get(i).getName().equals("Chef de raid")) {
                count++;
            }
        }
        return count;
    }

    /**
     * Utilisé pour savoir si une carte avec provocation existe sur mon board.
     * @return true si il en existe au moins une, false sinon
     */
    public boolean aCardWithProvocationInMyBorad() {
        for (int i = 0; i < board.size(); i++) {
            if (((Minion) this.getBoard().get(i)).getHasTaunt()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retourne le nom du héros.
     * @return this.heroName
     */
    public String getHeroName() {
        return this.heroName;
    }

    /**
     * Retourne la vie du héros.
     * @return this.health
     */
    public int getHealth() {
        return this.health;
    }

    /**
     * Retourne les cartes que le héros peut posséder.
     * @return this.deck
     */
    public ArrayList<AbstractCard> getDeck() {
        return this.deck;
    }

    /**
     * Retourne la main du héros.
     * @return this.hand
     */
    public ArrayList<AbstractCard> getHand() {
        return this.hand;
    }

    /**
     * Retourne le board du héros.
     * @return this.board
     */
    public ArrayList<Minion> getBoard() {
        return this.board;
    }

    /**
     * Permet de mettre la vie de notre héros à "vie".
     * @param vie Vie que l'on va donner à notre héros
     */
    public void setHealth(final int vie) {
        if (vie >= VIE_MAX) {
            this.health = VIE_MAX;
        } else {
            this.health = vie;
        }
    }

    /**
     * Retourne la valeur de looser.
     * @return this.looser
     */
    public boolean isLooser() {
        return this.looser;
    }

    /**
     * Retourne la valeur de mana du héros.
     * @return this.mana
     */
    public int getMana() {
        return this.mana;
    }

    /**
     * Permet de changer la valeur du mana de notre héros.
     * @param newMana Mana que l'on veut donner à notre héros
     */
    public void setMana(final int newMana) {
        this.mana = newMana;
    }

    /**
     * Retourne l'armure de notre héros.
     * @return this.armor
     */
    public int getArmor() {
        return this.armor;
    }

    /**
     * Permet de changer la valeur de l'armure de notre héros.
     * @param newArmor Armure que l'on veut donner à notre héros.
     */
    public void setArmor(final int newArmor) {
        this.armor = newArmor;
    }

    /**
     * Affiche notre héros sous forme de String (surtout pour le debogage).
     * @return notre héros sous forme de String.
     */
    public String toString() {
        String affHand = "\n" + "\n" + "------------- affichage de la main courant ------------- \n";
        for (int i = 0; i < this.hand.size(); i++) {
            affHand = affHand + "------------- affichage de la main courant ------------- \n";
            affHand = affHand + "\n element à l'index " + i + " \n " + hand.get(i).toString() + " \n ";
        }

        return "mana : " + this.mana + "\n"
                + "heroUUID : " + this.heroUUID + "\n"
                + "description :" + this.description + "\n"
                + "heroName : " + this.heroName + "\n"
                + "health : " + this.health + "\n"
                + "armor : " + this.armor + affHand;

    }

    /**
     * Affiche comme un board (utilisé pour le débogage).
     * @return notre board sous forme de String
     */
    public String superToString() {
        String aff =
                "                |  PV : " + this.getHealth() + "  AR : " + this.getArmor() + "  MN : " + this.getMana() + "  |					\n"
                        + "					------------------------------					\n";

        aff = aff + "Cartes en main : \n";
        for (int i = 0; i < hand.size(); i++) {
            aff = aff + "|||" + hand.get(i).getName() + " Mana Cost : " + hand.get(i).getManaCost() + "|||	";

        }
        aff = aff + "\n";

        aff = aff + "Cartes en du board : \n";
        for (int i = 0; i < board.size(); i++) {
            aff = aff + "|||" + board.get(i).getName() + "	lf : " + ((Minion) board.get(i)).getLife() + " Att : " + ((Minion) board.get(i)).getAttack() + "|||	";

        }

        aff = aff + "\n";
        return aff;
    }

    /**
     * Permet de tirer une carte dans le deck et de la placer dans notre main.
     * @param cardName le nom de la carte
     * @return la carte que nous avons tirée.
     */
    public AbstractCard draw(final String cardName) {
        AbstractCard min = null;
        /*if (cardName.equals("Sanglier brocheroc")) {
            min = new Minion("Sanglier de brocheroc", "je suis n1", 1, 1, 1, false, false, false, null, null);
        } else if (cardName.equals("Soldat du comté-de-l'or")) {
            min = new Minion("Soldat du compté d'or", "je suis n4", 1, 1, 2, false, true, false, null, null);
        } else if (cardName.equals("Chevaucheur de loup")) {
            min = new Minion("Chevaucheur de loup", "je suis n2", 3, 1, 3, false, false, false, null, null);
        } else if (cardName.equals("Chef de raid")) {
            min = new Minion("Chef de raid", "je suis fort", 3, 2, 2, false, false, true, null, null);
        } else if (cardName.equals("Yéti noroit")) {
            min = new Minion("Yéti noroit", "je suis n4", 4, 4, 5, false, true, false, null, null);
        // Spell(int manaCost, String name, String description, String pictureURL)
        } else if (cardName.equals("Image miroir")) {
            min = new Spell("Image miroir", "description", 1, null, "img");
        // Spell(int manaCost, String name, String description, String pictureURL)
        } else if (cardName.equals("Explosion des arcanes")) {
            min = new Spell("Explosion des arcanes", "description", 1, null, "img");
        // Spell(int manaCost, String name, String description, String pictureURL)
        } else if (cardName.equals("Métamorphose")) {
            min = new Spell("Métamorphose", "description", 4, null, "img");
        } else if (cardName.equals("Champion frisselame")) {
            min = new Minion("Champion frisselame", "description", 4, 2, 8, false, false, true, null, null);
        } else if (cardName.equals("Bénédiction de puissance")) {
            min = new Spell("Bénédiction de puissance", "description", 1, null, "img");
        } else if (cardName.equals("Consécration")) {
            min = new Spell("Consécration", "description", 4, null, "img");
        } else if (cardName.equals("Tourbillon")) {
            min = new Spell("Tourbillon", "description", 4, null, "img");
        } else if (cardName.equals("Avocat commis d'office")) {
            min = new Minion("Avocat commis d'office", "description", 2, 0, 7, false, true, false, null, null);
        } else if (cardName.equals("Maîtrise du blocage")) {
            min = new Spell("Maîtrise du blocage", "description", 3, null, "img");
        }

        if (min != null) {
            hand.add(min);
        }*/
        return min;
    }
}
