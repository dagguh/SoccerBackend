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
 * some useless stuff
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
	public void create(Account account) {
		log.info("Creating account " + account);
		em.merge(account);
		Player player = account.getPlayer();
		player.setScore(1200);
		em.merge(player);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_XML)
	public Account update(Account account) {
		if (exists(account.getPlayer())) {
			return em.merge(account);
		}
		throw new IllegalArgumentException("Account doesn't exist");
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
		Account savedAccount = find(inputAccount.getPlayer());
		if (null == savedAccount) {
			log.info("No account matches " + savedAccount);
		} else {
			log.info("Found account matching " + savedAccount);
			if (savedAccount.getPassword().equals(inputAccount.getPassword())) {
				String ticket = UUID.randomUUID().toString();
				savedAccount.setTicket(ticket);
				em.merge(savedAccount);
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
			find(player);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}
}
