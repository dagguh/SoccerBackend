package pl.dagguh.soccerbackend.game.control.exceptions;

import org.apache.log4j.Logger;

/**
 *
 * @author Maciej Kwidziński <maciek.kwidzinski@gmail.com>
 */
public class BallOutOfField extends Exception {

	private static Logger log = Logger.getLogger(BallOutOfField.class);

	public BallOutOfField(int ballX, int ballY) {
		log.warn("Ball fell out " + ballX + " " + ballY);
	}
}
