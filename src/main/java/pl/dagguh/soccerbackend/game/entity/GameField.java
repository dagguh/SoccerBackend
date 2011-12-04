package pl.dagguh.soccerbackend.game.entity;

import java.awt.Point;
import org.apache.log4j.Logger;
import pl.dagguh.soccerbackend.game.control.MoveDirection;
import pl.dagguh.soccerbackend.game.control.UnexpectedMoveDirection;

/**
 * @author Maciej Kwidziński <maciek.kwidzinski@gmail.com>
 */
@Entity
@XmlRootElement
public class GameField {

	private static final int fieldWidth = 9;
	private static final int fieldHeight = 11;
	private int[][] bitMasks;
	private int ballX;
	private int ballY;
	private static final int emptyBitMask = 0;
	private static Logger log = Logger.getLogger(GameField.class);

	public GameField() {
		clear();
	}

	public GameField(int[][] bitMasks, int ballX, int ballY) {
		this.bitMasks = bitMasks;
		this.ballX = ballX;
		this.ballY = ballY;
	}

	public GameField(int[][] bitMasks, Point ball) {
		GameField(bitMasks, ball.x, ball.y);
	}

	public final void clear() {
		bitMasks = new int[fieldHeight][fieldWidth];
		for (int y = 0; y < fieldHeight; y++) {
			for (int x = 0; x < fieldWidth; x++) {
				bitMasks[y][x] = emptyBitMask;
			}
		}
	}

	public int getBallX() {
		return ballX;
	}

	public void setBallX(int ballX) {
		this.ballX = ballX;
	}

	public int getBallY() {
		return ballY;
	}

	public void setBallY(int ballY) {
		this.ballY = ballY;
	}

	public int[][] getBitMasks() {
		return bitMasks;
	}

	public void setBitMasks(int[][] bitMasks) {
		this.bitMasks = bitMasks;
	}

	public boolean isMoveDirectionLegal(MoveDirection moveDirection) {
		return (moveDirection.getMask() & getBitMaskAtBall()) == emptyBitMask;
	}

	private int getBitMaskAtBall() {
		return bitMasks[ballY][ballX];
	}

	public boolean makeMove(MoveDirection moveDirection) {
		if (!isMoveDirectionLegal(moveDirection)) {
			return false;
		}

		try {
			leaveTrail(moveDirection);
			moveBall(moveDirection);
			leaveTrail(moveDirection);
			return true;
		} catch (UnexpectedMoveDirection e) {
			return false;
		}
	}

	private void moveBall(MoveDirection moveDirection) throws UnexpectedMoveDirection {
		switch (moveDirection) {
			case N:
				moveNorth();
				break;
			case NE:
				moveNorth();
				moveEast();
				break;
			case E:
				moveEast();
				break;
			case SE:
				moveSouth();
				moveEast();
				break;
			case S:
				moveSouth();
				break;
			case SW:
				moveSouth();
				moveWest();
				break;
			case W:
				moveWest();
				break;
			case NW:
				moveNorth();
				moveWest();
				break;
			default:
				log.error("Unexpected move direction " + moveDirection);
				throw new UnexpectedMoveDirection();
		}
	}

	private void moveNorth() {
		ballY++;
	}

	private void moveSouth() {
		ballY--;
	}

	private void moveEast() {
		ballX++;
	}

	private void moveWest() {
		ballX--;
	}

	private void leaveTrail(MoveDirection moveDirection) {
		bitMasks[ballY][ballX] |= moveDirection.getMask();
	}
}
