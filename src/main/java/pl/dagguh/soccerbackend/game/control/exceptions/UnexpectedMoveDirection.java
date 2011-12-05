package pl.dagguh.soccerbackend.game.control.exceptions;

import org.apache.log4j.Logger;
import pl.dagguh.soccerbackend.game.control.MoveDirection;

/**
 *
 * @author Maciej Kwidziński <maciek.kwidzinski@gmail.com>
 */
public class UnexpectedMoveDirection extends Exception {

	private static Logger log = Logger.getLogger(UnexpectedMoveDirection.class);
	public UnexpectedMoveDirection(MoveDirection direction) {
		log.info("Unexpected direction " + direction);
	}
	
}
