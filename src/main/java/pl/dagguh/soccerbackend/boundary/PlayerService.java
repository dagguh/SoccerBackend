package pl.dagguh.soccerbackend.boundary;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import pl.dagguh.soccerbackend.entity.Player;

/**
 *
 * @author Maciej Kwidziński <maciek.kwidzinski@gmail.com>
 */
@Stateless
@Path("/player")
public class PlayerService {
	
	@PersistenceContext
	private EntityManager em;

	public Player createOrUpdate(Player player) {
		return em.merge(player);
	}

	public void remove(Player player) {
		em.remove(em.merge(player));
	}

	@GET
	@Path("/{nick}")
	@Produces(MediaType.APPLICATION_XML)
	public Player find(@PathParam("nick") String nick) {
		return em.find(Player.class, nick);
	}
}
