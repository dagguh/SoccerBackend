package pl.dagguh.soccerbackend.game.boundary;

import java.util.List;
import pl.dagguh.soccerbackend.player.boundary.PlayerService;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import org.apache.log4j.Logger;
import pl.dagguh.soccerbackend.game.control.GameNotFoundException;
import pl.dagguh.soccerbackend.player.control.PlayerNotFoundException;
import pl.dagguh.soccerbackend.player.control.TicketMismatchException;
import pl.dagguh.soccerbackend.game.entity.Game;
import pl.dagguh.soccerbackend.game.entity.Player;

/**
 * @author Maciej Kwidziński <maciek.kwidzinski@gmail.com>
 */
@Stateless
@Path("/game")
@Table(name = GameService.tableName)
public class GameService {

	public static final String tableName = "GAME";
	private static Logger log = Logger.getLogger(GameService.class);
	@PersistenceContext
	private EntityManager em;
	@EJB
	private PlayerService playerService;

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String createWithStringTicket(Player firstPlayer) {
		return Long.toString(create(firstPlayer));
	}
	
	public long create(Player firstPlayer) {
		try {
			log.info("Creating new game with first player: " + firstPlayer);
			playerService.validateTicket(firstPlayer);
			Game game = new Game();
			game.setRedPlayerNick(firstPlayer.getNick());
			Game newGame = em.merge(game);
			log.info("New game created " + newGame);
			return newGame.getId();
		} catch (PlayerNotFoundException e) {
			return -1;
		} catch (TicketMismatchException e) {
			return -1;
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/{gameId}")
	public String join(Player player, @PathParam("gameId") long gameId) {
		try {
			Game game = find(gameId);
//			if (game.)
			return "Dołączono";
		} catch (GameNotFoundException e) {
			return "Gra nie istnieje";
		}
	}

	private Game find(long gameId) throws GameNotFoundException {
		Game game = em.find(Game.class, gameId);
		if (null == game) {
			throw new GameNotFoundException();
		}
		return game;
	}

//	private List<Game> findOpenGames() {
//		TypedQuery<Game> query = em.createQuery("SELECT * FROM ", Game.class);
//		TypedQuery<Game> query2 = em.createQuery(new CriteriaQuery<Game>())
//		CriteriaQuery<Game> query = em.getCriteriaBuilder().createQuery(Game.class);
//		query.select(query.from(Game.class)).where(null);
//		return query.getResultList();
//	}
}
