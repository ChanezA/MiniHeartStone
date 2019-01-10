package test;

import engine.*;
import org.springframework.boot.SpringApplication;

import java.util.Scanner;

public class CommandLineLauncher {

    public static void main(String[] args) {
        System.out.println("___  ____       _ _   _                 _   _____ _                 ");
        System.out.println("|  \\/  (_)     (_) | | |               | | /  ___| |                  ");
        System.out.println("| .  . |_ _ __  _| |_| | ___  __ _ _ __| |_\\ `--.| |_ ___  _ __   ___");
        System.out.println("| |\\/| | | '_ \\| |  _  |/ _ \\/ _` | '__| __|`--. \\ __/ _ \\| '_ \\ / _ \\");
        System.out.println("| |  | | | | | | | | | |  __/ (_| | |  | |_/\\__/ / || (_) | | | |  __/");
        System.out.println("\\_|  |_/_|_| |_|_\\_| |_/\\___|\\__,_|_|   \\__\\____/ \\__\\___/|_| |_|\\___|");
        System.out.println("\nJeu développé par Alan, Alexis, Chanez et Louis\n");

        SpringApplication.run(Application.class, args);

        Player player1 = getPlayer(1);
        Player player2 = getPlayer(2);

        Game game = new Game(player1, player2);

        System.out.println("\n\noO Début de la partie Oo");
        System.out.println("\n\nNot current player : "+ game.getNotCurrentPlayer().getName());
        System.out.println("\n" + game.getNotCurrentPlayer().getHero().toString());
        System.out.println("\nCurrent Player : " + game.getCurrentPlayer().getName());
        System.out.println("\n" + game.getCurrentPlayer().getHero().toString());
        System.out.println("*** Board ***");
        for (AbstractCard card : game.getCurrentPlayer().getHero().getBoard()) {
            System.out.println(card.toString());
        }
        System.out.println("*** Hand ***");
        for (AbstractCard card : game.getCurrentPlayer().getHero().getHand()) {
            System.out.println(card.toString());
        }

    }

    private static Player getPlayer(int playerNum) {
        System.out.println("\n\nEntrez le pseudo du joueur " + playerNum + " :");
        Scanner scanner = new Scanner(System.in);
        String input1 = scanner.nextLine();
        int input2;
        while (true) {
            try {
                System.out.println("Entrez le héro que jouera " + input1);
                System.out.println("1 : Mage, 2 : Paladin, 3 : Guerrier :");
                input2 = scanner.nextInt();
                if (input2 < 1 || input2 > 3) {
                    System.out.println("Veuillez entrer un entier correct svp");
                }
                else break;
            }
            catch (Exception e) {
                System.out.println("Veuillez entrer un entier correct svp");
            }
        }

        AbstractHero hero = getHero(playerNum, input2);

        return new Player(input1, hero, 1);
    }

    private static AbstractHero getHero(int numero, int input) {
        AbstractHero ret = null;
        switch (input) {
            case 1 :
                ret = new Magus();
                break;
            case 2 :
                ret = new Paladin();
                break;
            case 3 :
                ret = new Warrior();
                break;
            default:
                break;
        }

        return ret;
    }

}
