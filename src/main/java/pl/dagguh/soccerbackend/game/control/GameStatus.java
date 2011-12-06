package pl.dagguh.soccerbackend.game.control;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Maciej Kwidziński <maciek.kwidzinski@gmail.com>
 */
@XmlRootElement
public enum GameStatus {

	WAITING_FOR_OPPONENT,
	RED_PLAYER_TURN,
	BLUE_PLAYER_TURN,
	RED_WINS,
	BLUE_WINS
}
