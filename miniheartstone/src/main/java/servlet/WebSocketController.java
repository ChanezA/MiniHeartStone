package servlet;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import engine.Game;
import engine.util.GameListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import engine.EngineInterface;

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
			//System.out.println(EngineInterface.wantPlay(message.getlvl(),message.getPseudo(),message.getHero()));
			System.out.println(message.getPseudo());
			System.out.println(message.getHero());
			System.out.println(message.getlvl());
			System.out.println("------------------");
		}

		catch(Exception e) {
			System.out.print(e.getMessage());
		}
	}

	/*public static void gameIsRadyToStart(UUID gameUUID,String pseudo) {
		try {
			String rtrn = new ObjectMapper().writeValueAsString(new Object() {
				@JsonProperty private UUID rtrnmess = gameUUID;
			});
			this.template.convertAndSend("/chat/"+pseudo,rtrn);
		}

		catch(Exception e) {
			System.out.print(e.getMessage());
		}
	}*/

	public void notify(Game game) {
		// Ici init a été fait
	}

}