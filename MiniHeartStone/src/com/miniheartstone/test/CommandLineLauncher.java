package com.miniheartstone.test;

import com.miniheartstone.engine.*;

public class CommandLineLauncher {

    public static void main(String args) {
        Hero hero1 = new Magus("Gandalf", 100, 10);
        Hero hero2 = new Warrior("Boromir", 3000, 2, 3);
        Player player1 = new Player("Gerson", hero1, 3);
        Player player2 = new Player("Pascal", hero2, 1);
        Game game = new Game();
    }

}
