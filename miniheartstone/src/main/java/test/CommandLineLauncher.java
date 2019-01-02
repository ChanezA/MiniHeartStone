package test;

import engine.*;

import java.util.Scanner;

public class CommandLineLauncher {

    public static void main(String[] args) {
        System.out.println(" /----------------------\\");
        System.out.println("  --- MiniHeartStone ---");
        System.out.println(" \\----------------------/\n");
        System.out.println("Jeu développé par Alan, Alexis, Chanez et Louis\n");

        Player player1 = getPlayer(1);
        Player player2 = getPlayer(2);

        Game game = new Game(player1, player2);

        System.out.println("\n\noO Début de la partie Oo");
        System.out.println("\n\n" + game.getNotCurrentPlayer().getHero().toString());
        //System.out.println("\n" + game.getNotCurrentPlayer().getHand().toString());
        System.out.println("\n" + game.getCurrentPlayer().getHero().toString());
        System.out.println("\n* Tour de : " + game.getCurrentPlayer().getName());
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
                /*if (numero == 1) ret = new Magus("Gandalf", 2000, 10);
                else ret = new Magus("Dumbledore", 100, 300);
                break;
            case 2 :
                if (numero == 1) ret = new Paladin("Lancelot", 20, 2);
                else ret = new Magus("Perceval", 80, 3);
                break;
            case 3 :
                if (numero == 1) ret = new Warrior("Boromir", 3000, 4, 10);
                else ret = new Warrior("Gimli", 3000, 400, 20);*/
                break;
            default:
                break;
        }

        return ret;
    }

}
