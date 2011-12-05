package pl.dagguh.soccerbackend.game.control;

/**
 * @author Maciej Kwidziński <maciek.kwidzinski@gmail.com>
 */
public enum MoveStatus {

	ACCEPTED_FINISH_TURN,
	ACCEPTED_CONTINUE_TURN,
	ACCEPTED_RED_SHOOTS_GOAL,
	ACCEPTED_BLUE_SHOOTS_GOAL,
	ACCEPTED_BLOCKED,
	REJECTED
}
