package pl.dagguh.soccerbackend.game.control;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Maciej Kwidziński <maciek.kwidzinski@gmail.com>
 */
@XmlRootElement
public enum MoveStatus {

	ACCEPTED_FINISH_TURN,
	ACCEPTED_CONTINUE_TURN,
	ACCEPTED_RED_SHOOTS_GOAL,
	ACCEPTED_BLUE_SHOOTS_GOAL,
	ACCEPTED_BLOCKED,
	REJECTED
}
