package pl.dagguh.soccerbackend.player.boundary;

import java.util.UUID;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import org.apache.log4j.Logger;
import pl.dagguh.soccerbackend.player.control.PasswordMismatchException;
import pl.dagguh.soccerbackend.player.control.PlayerNotFoundException;
import pl.dagguh.soccerbackend.player.control.TicketMismatchException;
import pl.dagguh.soccerbackend.game.entity.Player;

/**
 *
 * @author Maciej Kwidziński <maciek.kwidzinski@gmail.com>
 */
@Stateless
@Path("/player")
public class PlayerService {

	private static Logger log = Logger.getLogger(PlayerService.class);
	@PersistenceContext
	private EntityManager em;

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String register(Player player) {
		log.info("Registering " + player);
		String nick = player.getNick();
		if (exists(player)) {
			return "Gracz " + nick + " już posiada konto";
		}
		player.setScore(Player.initialScore);
		em.merge(player);
		return "Konto dla gracza " + nick + " zosało założone";
	}

	private boolean exists(Player player) {
		try {
			find(player.getNick());
			return true;
		} catch (PlayerNotFoundException e) {
			return false;
		}
	}

	public Player find(String nick) throws PlayerNotFoundException {
		Player player = em.find(Player.class, nick);
		if (null == player) {
			log.warn("No player with nick " + nick + " found");
			throw new PlayerNotFoundException();
		}
		return player;
	}

	@GET
	@Path("/{nick}")
	@Produces(MediaType.APPLICATION_XML)
	public Player getInfo(@PathParam("nick") String nick) {
		try {
			Player player = find(nick);
			player.setPassword("****");
			player.setTicket("****");
			return player;
		} catch (PlayerNotFoundException e) {
			return null;
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String update(Player player) {
		if (exists(player)) {
			em.merge(player);
			return "Zmiany zostały zapisane";
		} else {
			return "Gracz nie istnieje";
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/authenticate")
	public String authenticate(Player player) {
		log.info("Authenticating: " + player);
		try {
			validatePassword(player);
			Player matchingPlayer = find(player.getNick());
			String ticket = UUID.randomUUID().toString();
			matchingPlayer.setTicket(ticket);
			em.merge(matchingPlayer);
			return ticket;
		} catch (PlayerNotFoundException e) {
			return "";
		} catch (PasswordMismatchException e) {
			return "";
		}
	}

	public void validatePassword(Player actualPlayer) throws PlayerNotFoundException, PasswordMismatchException {
		Player expectedPlayer = find(actualPlayer.getNick());
		String expectedPassword = expectedPlayer.getPassword();
		String actualPassword = actualPlayer.getPassword();
		if (!expectedPassword.equals(actualPassword)) {
			log.warn(actualPlayer + " password doesn't match " + expectedPassword);
			throw new PasswordMismatchException();
		}
	}

	public void validateTicket(Player actualPlayer) throws PlayerNotFoundException, TicketMismatchException {
		Player expectedPlayer = find(actualPlayer.getNick());
		String expectedTicket = expectedPlayer.getTicket();
		String actualTicket = actualPlayer.getTicket();
		if (!expectedTicket.equals(actualTicket)) {
			log.warn(actualPlayer + " ticket doesn't match " + expectedTicket);
			throw new TicketMismatchException();
		}
	}
}
