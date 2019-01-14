package servlet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import engine.EngineInterface;
import engine.Game;
import engine.util.GameListener;

@Controller
public class WebSocketController implements GameListener {
	
	private final SimpMessagingTemplate template;
	
	@Autowired
	WebSocketController(SimpMessagingTemplate template){
		this.template = template;
	}
	
	@MessageMapping("/send/message")
	public void onReceivedMessage(String message) {
		//this.template.convertAndSend("/chat", new SimpleDateFormat("HH:mm:ss").format(new Date())+"- "+message);
		this.template.convertAndSend("/chat", EngineInterface.getAllHeroDescription());
	}
	
	@MessageMapping("/send/iWantPlay")
	public void onReceivedIWantPlay (@Payload AllMessages message) {
		try {
			String rtrn = new ObjectMapper().writeValueAsString(new Object() {
		        @JsonProperty private UUID rtrnmess = EngineInterface.wantPlay(message.getlvl(),message.getPseudo(),message.getHero(),WebSocketController.this);
		    });
			this.template.convertAndSend("/chat/"+message.getPseudo(),rtrn);
			
			System.out.println("------------------");
			System.out.println(message.getPseudo());
			System.out.println(message.getHero());
			System.out.println(message.getlvl());
			//System.out.println(EngineInterface.wantPlay(message.getlvl(),message.getPseudo(),message.getHero(), null));
			System.out.println("------------------");
		}
		
		catch(Exception e) {
			System.out.print(e.getMessage());
		}
	}
	
	// notifie les players quand une game commence
	public void notify(Game game) {
		// Ici init a été fait
		System.out.println("couccou CONNARD lol");
		try {
			System.out.println("---Pas Exception");
			if (game.getGameID() == null) {
				System.out.println(">null");
			}
			else System.out.println(">notnull");
			String rtrn = new ObjectMapper().writeValueAsString(new Object() {
		        @JsonProperty private String rtrnmess = "game is ready(!!)" + game.getGameID().toString();
		    });
			System.out.println("---Pas ExceptionA");
			this.template.convertAndSend("/chat/"+game.getPlayer1().getName(),rtrn);
			this.template.convertAndSend("/chat/"+game.getPlayer2().getName(),rtrn );
			System.out.println("---Pas Exception2");
		}
		
		catch(Exception e) {
			System.out.println("---Exception");
			System.out.print(e.getMessage());
		}
	}
	
	/*
	 * recevoir la main du joueur
	 */
	@MessageMapping("/send/myHand")
	public void onReceivedIMyHand (String message) {
		try {
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			String[] msg = message.split("!!");
			String pseudo = msg[0];
			System.out.println("pseudo:"+pseudo);
			UUID gameUUID = UUID.fromString(msg[1]);
			System.out.println(gameUUID);
			UUID playerUUID = UUID.fromString(msg[2]);
			System.out.println(playerUUID);
			
			String[] hnd = EngineInterface.getMyHand(gameUUID, playerUUID);
			System.out.println("step  1");
			System.out.println(hnd[0]);
			System.out.println("step  2");
			System.out.println(hnd.length);
			for(int i=0; i< hnd.length; i++ ) {
				System.out.println(hnd[i]);
			}
			
			String supp = "this is your hand(!!)";
			for(int i=0; i< hnd.length; i++ ) {
				supp = supp + hnd[i];
				if(i != hnd.length-1) {supp = supp + "(CardDesc)";}
			}
			System.out.println(supp);
			String mm =supp;
			String rtrn = new ObjectMapper().writeValueAsString(new Object() {
		        @JsonProperty private String rtrnmess = mm;
		    });
			this.template.convertAndSend("/chat/"+pseudo,rtrn);
		}
		
		catch(Exception e) {
			System.out.print(e.getMessage());
		}
	}
	
	/*
	 * recevoir le board du joueur
	 */
	@MessageMapping("/send/myBoard")
	public void onReceivedIMyBoard (String message) {
		try {
			System.out.println("BBBBBBBB");
			String[] msg = message.split("!!");
			String pseudo = msg[0];
			System.out.println(pseudo);
			UUID gameUUID = UUID.fromString(msg[1]);
			System.out.println(gameUUID);
			UUID playerUUID = UUID.fromString(msg[2]);
			System.out.println(playerUUID);
			
			String[] hnd = EngineInterface.getMyBoard(gameUUID, playerUUID);
			System.out.println("step  1");
			System.out.println(hnd[0]);
			System.out.println("step  2");
			System.out.println(hnd.length);
			for(int i=0; i< hnd.length; i++ ) {
				System.out.println(hnd[i]);
			}
			
			String supp = "this is your board(!!)";
			for(int i=0; i< hnd.length; i++ ) {
				supp = supp + hnd[i];
				if(i != hnd.length-1) {supp = supp + "(CardDesc)";}
			}
			System.out.println(supp);
			String mm =supp;
			String rtrn = new ObjectMapper().writeValueAsString(new Object() {
		        @JsonProperty private String rtrnmess = mm;
		    });
			this.template.convertAndSend("/chat/"+pseudo,rtrn);
		}
		
		catch(Exception e) {
			System.out.print(e.getMessage());
		}
	}
	
	/*
	 * recevoir le board du joueur adverse
	 */
	@MessageMapping("/send/myOppBoard")
	public void onReceivedOppMyBoard (String message) {
		try {
			System.out.println("opopopopopopopopopopopopop");
			String[] msg = message.split("!!");
			String pseudo = msg[0];
			System.out.println(pseudo);
			UUID gameUUID = UUID.fromString(msg[1]);
			System.out.println(gameUUID);
			UUID playerUUID = UUID.fromString(msg[2]);
			System.out.println(playerUUID);
			
			String[] hnd = EngineInterface.getOpponantBoard(gameUUID, playerUUID);
			System.out.println("step  1");
			System.out.println(hnd[0]);
			System.out.println("step  2");
			System.out.println(hnd.length);
			for(int i=0; i< hnd.length; i++ ) {
				System.out.println(hnd[i]);
			}
			
			String supp = "this is your opponent board(!!)";
			for(int i=0; i< hnd.length; i++ ) {
				supp = supp + hnd[i];
				if(i != hnd.length-1) {supp = supp + "(CardDesc)";}
			}
			System.out.println(supp);
			String mm =supp;
			String rtrn = new ObjectMapper().writeValueAsString(new Object() {
		        @JsonProperty private String rtrnmess = mm;
		    });
			this.template.convertAndSend("/chat/"+pseudo,rtrn);
		}
		
		catch(Exception e) {
			System.out.print(e.getMessage());
		}
	}
	
	/*
	 * passer son tour
	 */
	@MessageMapping("/send/passTurn")
	public void onReceivedPassMyTurn (String message) {
		try {
			System.out.println("-----------------");
			System.out.println("Pass Turn");
			String[] msg = message.split("!!");
			String pseudo = msg[0];
			System.out.println(pseudo);
			UUID gameUUID = UUID.fromString(msg[1]);
			System.out.println(gameUUID);
			UUID playerUUID = UUID.fromString(msg[2]);
			System.out.println(playerUUID);
			
			Boolean succes = EngineInterface.passTurn(gameUUID, playerUUID );
			
			if (succes) {
				System.out.println("je suis dans le if");
				String rtrn = new ObjectMapper().writeValueAsString(new Object() {
			        @JsonProperty private String rtrnmess = "Current turn(!!)"+"Vous etes le joueur courant";
			    });
				this.template.convertAndSend("/chat/"+EngineInterface.getGameFromUUID(gameUUID).getCurrentPlayer().getName(),rtrn);
				String rtrn2 = new ObjectMapper().writeValueAsString(new Object() {
			        @JsonProperty private String rtrnmess = "Current turn(!!)"+"Vous n'etes plus le joueur courant";
			    });
				this.template.convertAndSend("/chat/"+EngineInterface.getGameFromUUID(gameUUID).getNotCurrentPlayer().getName(),rtrn2);
			}
			
		}
		catch(Exception e) {
			System.out.print(e.getMessage());
		}
	}
	
	/*
	 * passer son tour
	 */
	@MessageMapping("/send/invock")
	public void onReceivedInvock (String message) {
		try {
			System.out.println("-----------------");
			System.out.println("I want invock");
			String[] msg = message.split("!!");
			String pseudo = msg[0];
			System.out.println(pseudo);
			UUID gameUUID = UUID.fromString(msg[1]);
			System.out.println(gameUUID);
			UUID playerUUID = UUID.fromString(msg[2]);
			System.out.println(playerUUID);
			UUID cardUUID = UUID.fromString(msg[3]);
			System.out.println(cardUUID);
			
			EngineInterface.invock(gameUUID, playerUUID, cardUUID);
			
		}
		catch(Exception e) {
			System.out.print(e.getMessage());
		}
	}
	
	/*
	 * info sur mon hero
	 */
	@MessageMapping("/send/MyHeroInfo")
	public void onReceivedMyHero (String message) {
		try {
			System.out.println("-----------------");
			System.out.println("Info on your hero");
			String[] msg = message.split("!!");
			String pseudo = msg[0];
			System.out.println(pseudo);
			UUID gameUUID = UUID.fromString(msg[1]);
			System.out.println(gameUUID);
			UUID playerUUID = UUID.fromString(msg[2]);
			System.out.println(playerUUID);
			
			String rep = "Info on your hero(!!)"+playerUUID+"(!!)"+pseudo+"(!!)";
			int mana;
			int life;
			int armor;
			
			if(EngineInterface.getGameFromUUID(gameUUID).getPlayer1().getPlayerID().equals(playerUUID)) {
				mana = EngineInterface.getGameFromUUID(gameUUID).getPlayer1().getHero().getMana();
				life = EngineInterface.getGameFromUUID(gameUUID).getPlayer1().getHero().getHealth();
				armor = EngineInterface.getGameFromUUID(gameUUID).getPlayer1().getHero().getArmor();
			}
			else {
				mana = EngineInterface.getGameFromUUID(gameUUID).getPlayer2().getHero().getMana();
				life = EngineInterface.getGameFromUUID(gameUUID).getPlayer2().getHero().getHealth();
				armor = EngineInterface.getGameFromUUID(gameUUID).getPlayer2().getHero().getArmor();
			}
			rep = rep + mana+"(!!)" + life +"(!!)" + armor + "(!!)";
			
			if (EngineInterface.getGameFromUUID(gameUUID).getCurrentPlayer().getPlayerID().equals(playerUUID)) {
				rep = rep + 1;
			}
			else {
				rep = rep + 0;
			}
			
			String mm = rep;
			String rtrn = new ObjectMapper().writeValueAsString(new Object() {
		        @JsonProperty private String rtrnmess = mm;
		    });
			System.out.println(mm);
			this.template.convertAndSend("/chat/"+pseudo,rtrn);
		}
		
		catch(Exception e) {
			System.out.print(e.getMessage());
		}
	}
	
	/*
	 * info sur mon opp
	 */
	@MessageMapping("/send/MyOppInfo")
	public void onReceivedMyOpp (String message) {
		try {
			System.out.println("-----------------");
			System.out.println("Info on your opponent");
			String[] msg = message.split("!!");
			String pseudo = msg[0];
			System.out.println(pseudo);
			UUID gameUUID = UUID.fromString(msg[1]);
			System.out.println(gameUUID);
			UUID playerUUID = UUID.fromString(msg[2]);
			System.out.println(playerUUID);
			
			UUID pl = UUID.randomUUID();
			String ps = "";
			if(EngineInterface.getGameFromUUID(gameUUID).getPlayer1().getPlayerID().equals(playerUUID)) {
				pl = EngineInterface.getGameFromUUID(gameUUID).getPlayer2().getPlayerID();
				pl.toString();
				ps = EngineInterface.getGameFromUUID(gameUUID).getPlayer2().getName();
			}
			else {
				pl = EngineInterface.getGameFromUUID(gameUUID).getPlayer1().getPlayerID();
				pl.toString();
				ps = EngineInterface.getGameFromUUID(gameUUID).getPlayer1().getName();
			}
			
			String rep = "Info on your opponent(!!)"+pl+"(!!)"+ps+"(!!)";
			int mana;
			int life;
			int armor;
			
			if(EngineInterface.getGameFromUUID(gameUUID).getPlayer1().getPlayerID().equals(playerUUID)) {
				mana = EngineInterface.getGameFromUUID(gameUUID).getPlayer2().getHero().getMana();
				life = EngineInterface.getGameFromUUID(gameUUID).getPlayer2().getHero().getHealth();
				armor = EngineInterface.getGameFromUUID(gameUUID).getPlayer2().getHero().getArmor();
			}
			else {
				mana = EngineInterface.getGameFromUUID(gameUUID).getPlayer1().getHero().getMana();
				life = EngineInterface.getGameFromUUID(gameUUID).getPlayer1().getHero().getHealth();
				armor = EngineInterface.getGameFromUUID(gameUUID).getPlayer1().getHero().getArmor();
			}
			rep = rep + mana+"(!!)" + life +"(!!)" + armor + "(!!)";
			
			if (EngineInterface.getGameFromUUID(gameUUID).getCurrentPlayer().getPlayerID().equals(playerUUID)) {
				rep = rep + 0;
			}
			else {
				rep = rep + 1;
			}
			
			String mm = rep;
			String rtrn = new ObjectMapper().writeValueAsString(new Object() {
		        @JsonProperty private String rtrnmess = mm;
		    });
			System.out.println(mm);
			this.template.convertAndSend("/chat/"+pseudo,rtrn);
		}
		
		catch(Exception e) {
			System.out.print(e.getMessage());
		}
	}
	
	/*
	 * attaquer une créature avec une autre
	 */
	@MessageMapping("/send/IAttack")
	public void onReceivedIAttack (String message) {
		try {
			System.out.println("-----------------");
			System.out.println("I attack");
			String[] msg = message.split("!!");
			String pseudo = msg[0];
			System.out.println(pseudo);
			UUID gameUUID = UUID.fromString(msg[1]);
			System.out.println(gameUUID);
			UUID playerUUID = UUID.fromString(msg[2]);
			System.out.println(playerUUID);
			UUID myMinonUUID = UUID.fromString(msg[3]);
			System.out.println(playerUUID);
			UUID yourMinionUUID = UUID.fromString(msg[4]);
			System.out.println(playerUUID);
			
			EngineInterface.attack(gameUUID, playerUUID, myMinonUUID, yourMinionUUID);
			
		}
		
		catch(Exception e) {
			System.out.print(e.getMessage());
		}
	}
}
