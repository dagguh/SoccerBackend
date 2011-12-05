package pl.dagguh.soccerbackend.game.control.exceptions;

import org.apache.log4j.Logger;
import pl.dagguh.soccerbackend.game.control.GameToken;

/**
 *
 * @author Maciej Kwidziński <maciek.kwidzinski@gmail.com>
 */
public class InvalidGameTokenException extends Exception {

	private static Logger log = Logger.getLogger(InvalidGameTokenException.class);

	public InvalidGameTokenException(GameToken token) {
		log.info(token + " is invalid");
	}
}
