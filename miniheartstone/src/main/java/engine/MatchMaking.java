package engine;

import java.util.*;

public class MatchMaking {
    private Queue<Player> noobMatchMaking;
    private Queue<Player> collaboratorMatchMaking;
    private Queue<Player> onlyBGMatchMaking;

    MatchMaking() {
        noobMatchMaking = new PriorityQueue<Player>();
        collaboratorMatchMaking = new PriorityQueue<Player>();
        onlyBGMatchMaking = new PriorityQueue<Player>();
    }

    public void addOnMatchMaking(Player player, HashMap<UUID, Game> allCurrentGame, int level) {
        if (level == 1) {
            if (noobMatchMaking.peek() != null) {
                Player player2 = noobMatchMaking.poll();
                Game game = new Game(player, player2);
                allCurrentGame.put(game.getGameID(), game);
                // envoyer l'UUID de la game aux deux players

            } else {
                noobMatchMaking.add(player);
            }
        }
        if (level == 2) {
            if (collaboratorMatchMaking.peek() != null) {
                Player player2 = collaboratorMatchMaking.poll();
                Game game = new Game(player, player2);
                allCurrentGame.put(game.getGameID(), game);
                // envoyer l'UUID de la game aux deux players

            } else {
                collaboratorMatchMaking.add(player);
            }
        }
        if (level == 3) {
            if (onlyBGMatchMaking.peek() != null) {
                Player player2 = onlyBGMatchMaking.poll();
                Game game = new Game(player, player2);
                allCurrentGame.put(game.getGameID(), game);
                // envoyer l'UUID de la game aux deux players

            } else {
                onlyBGMatchMaking.add(player);
            }
        }
    }
}