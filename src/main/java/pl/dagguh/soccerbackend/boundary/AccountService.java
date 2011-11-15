package pl.dagguh.soccerbackend.boundary;

import java.util.UUID;
import javax.ejb.Stateless;
import org.apache.log4j.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import pl.dagguh.soccerbackend.entity.Account;
import pl.dagguh.soccerbackend.entity.Player;

/**
 * @author Maciej Kwidziński <maciek.kwidzinski@gmail.com>
 */
@Stateless
@Path("/account")
public class AccountService {

	private static Logger log = Logger.getLogger(AccountService.class);
	@PersistenceContext
	private EntityManager em;

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String create(Account account) {
		log.info("Incoming account " + account);
		Player player = account.getPlayer();
		if (exists(player)) {
			return "Gracz " + player.getNick() + " już posiada konto";
		}
		player.setScore(1200);
		account.setPlayer(player);
		em.merge(account);
		return "Konto dla gracza " + player.getNick() + " zosało założone";
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String update(Account account) {
		if (exists(account.getPlayer())) {
			em.merge(account);
			return "Zmiany zostały zapisane";
		} else {
			return "Gracz nie istnieje";
		}
	}

	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	public void remove(Account account) {
		em.remove(em.merge(account));
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/authenticate")
	public String authenticate(Account inputAccount) {
		log.info("Authenticating: " + inputAccount);
		Account matchingAccount = find(inputAccount.getPlayer());
		if (null == matchingAccount) {
			log.info("No account matches " + matchingAccount);
		} else {
			log.info("Found account matching " + matchingAccount);
			if (matchingAccount.getPassword().equals(inputAccount.getPassword())) {
				String ticket = UUID.randomUUID().toString();
				matchingAccount.setTicket(ticket);
				em.merge(matchingAccount);
				return ticket;
			}
		}
		return "";
	}

	public Account find(Player player) {
		return em.find(Account.class, player.getNick());
	}

	public boolean exists(Player player) {
		try {
			return null != find(player);
		} catch (IllegalArgumentException e) {
			return false;
		}
	}
}
