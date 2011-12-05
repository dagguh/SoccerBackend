package pl.dagguh.soccerbackend.player.control;

import org.apache.log4j.Logger;

/**
 *
 * @author Maciej Kwidziński <maciek.kwidzinski@gmail.com>
 */
public class TicketMismatchException extends Exception {

	private static Logger log = Logger.getLogger(TicketMismatchException.class);

	public TicketMismatchException(PlayerToken playerToken) {
		log.warn("Player " + playerToken.getNick() + " with mismatched ticket " + playerToken.getTicket());
	}
}
