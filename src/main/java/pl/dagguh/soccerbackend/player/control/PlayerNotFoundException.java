package pl.dagguh.soccerbackend.player.control;

import org.apache.log4j.Logger;

/**
 *
 * @author Maciej Kwidziński <maciek.kwidzinski@gmail.com>
 */
public class PlayerNotFoundException extends Exception {
	
	private static Logger log = Logger.getLogger(PlayerNotFoundException.class);

	public PlayerNotFoundException(String nick) {
		log.info("No player with nick " + nick + " found");
	}

	
	
}
