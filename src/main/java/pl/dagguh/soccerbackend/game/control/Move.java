package pl.dagguh.soccerbackend.game.control;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Maciej Kwidziński <maciek.kwidzinski@gmail.com>
 */
@XmlRootElement
public class Move {

	private GameToken gameToken;
	private MoveDirection moveDirection;

	public Move() {
	}

	public Move(GameToken gameToken, MoveDirection moveDirection) {
		this.gameToken = gameToken;
		this.moveDirection = moveDirection;
	}

	public GameToken getGameToken() {
		return gameToken;
	}

	public void setGameToken(GameToken gameToken) {
		this.gameToken = gameToken;
	}

	public MoveDirection getMoveDirection() {
		return moveDirection;
	}

	public void setMoveDirection(MoveDirection moveDirection) {
		this.moveDirection = moveDirection;
	}

	@Override
	public String toString() {
		return "Move{" + "gameToken=" + gameToken + ", moveDirection=" + moveDirection + '}';
	}
}
