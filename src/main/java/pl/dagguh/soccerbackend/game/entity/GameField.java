package pl.dagguh.soccerbackend.game.entity;

import java.awt.Point;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.log4j.Logger;
import pl.dagguh.soccerbackend.game.control.MoveDirection;
import pl.dagguh.soccerbackend.game.control.MoveStatus;
import pl.dagguh.soccerbackend.game.control.exceptions.UnexpectedMoveDirection;
import pl.dagguh.soccerbackend.game.control.exceptions.BallOutOfField;

/**
 * @author Maciej Kwidziński <maciek.kwidzinski@gmail.com>
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class GameField implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private long id;
	private static final int fieldWidth = 9;
	private static final int fieldHeight = 11;
	private int[][] bitMasks;
	private int ballX;
	private int ballY;
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
		this(bitMasks, ball.x, ball.y);
	}

	public final void clear() {
		bitMasks = new int[fieldHeight][fieldWidth];
		for (int i = 0; i < fieldHeight; i++) {
			for (int j = 0; j < fieldWidth; j++) {
				if (j == 0) {
					bitMasks[i][j] = 241;
				} else if (j == 8) {
					bitMasks[i][j] = 31;
				}
				if (i == 0) {
					if (j == 0) {
						bitMasks[i][j] = 247;
					} else if (j < 3) {
						bitMasks[i][j] = 199;
					} else if (j == 3) {
						bitMasks[i][j] = 193;
					} else if (j == 4) {
						bitMasks[i][j] = 0;
					} else if (j == 5) {
						bitMasks[i][j] = 7;
					} else if (j < 8) {
						bitMasks[i][j] = 199;
					} else if (j == 8) {
						bitMasks[i][j] = 223;
					}
				} else if (i == 10) {
					if (j == 0) {
						bitMasks[i][j] = 253;
					} else if (j < 3) {
						bitMasks[i][j] = 124;
					} else if (j == 3) {
						bitMasks[i][j] = 112;
					} else if (j == 4) {
						bitMasks[i][j] = 0;
					} else if (j == 5) {
						bitMasks[i][j] = 28;
					} else if (j < 8) {
						bitMasks[i][j] = 124;
					} else if (j == 8) {
						bitMasks[i][j] = 127;
					}
				} else if (j != 0 && j != 8) {
					bitMasks[i][j] = 0;
				}
			}
		}
		ballX = 4;
		ballY = 5;
	}

	public long getId() {
		return id;
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

	@Override
	public String toString() {
		return "GameField{" + "ballX=" + ballX + ", ballY=" + ballY + '}';
	}

	public boolean isMoveDirectionLegal(MoveDirection moveDirection) {
		return (moveDirection.getMask() & getBitMaskAtBall()) == MoveDirection.emptyBitMask;
	}

	private int getBitMaskAtBall() {
		return bitMasks[ballY][ballX];
	}

	public MoveStatus makeMove(MoveDirection moveDirection) {
		if (!isMoveDirectionLegal(moveDirection)) {
			return MoveStatus.REJECTED;
		}

		try {
			leaveTrail(moveDirection);
			moveBall(moveDirection);
			boolean isTurnFinished = isBallFinishingTurn();
			leaveTrail(moveDirection.getOpposite());
			if (didRedShootGoal()) {
				return MoveStatus.ACCEPTED_RED_SHOOTS_GOAL;
			} else if (didBlueShootGoal()) {
				return MoveStatus.ACCEPTED_BLUE_SHOOTS_GOAL;
			} else if (isBallBlocked()) {
				return MoveStatus.ACCEPTED_BLOCKED;
			} else if (isTurnFinished) {
				return MoveStatus.ACCEPTED_FINISH_TURN;
			} else {
				return MoveStatus.ACCEPTED_CONTINUE_TURN;
			}
		} catch (UnexpectedMoveDirection ex) {
			return MoveStatus.REJECTED;
		} catch (BallOutOfField ex) {
			return MoveStatus.REJECTED;
		}
	}

	private void moveBall(MoveDirection moveDirection) throws UnexpectedMoveDirection, BallOutOfField {
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
				throw new UnexpectedMoveDirection(moveDirection);
		}
		validateBallOnField();
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

	private void validateBallOnField() throws BallOutOfField {
		if (ballX < 0 || ballX >= fieldWidth || ballY < 0 || ballY >= fieldHeight) {
			throw new BallOutOfField(ballX, ballY);
		}
	}

	private void leaveTrail(MoveDirection moveDirection) {
		log.info("Leaving trail from [" + ballY + "][" + ballX + "] to direction " + moveDirection + " with mask " + moveDirection.getMask());
		bitMasks[ballY][ballX] |= moveDirection.getMask();
	}

	private boolean isBallFinishingTurn() {
		return getBitMaskAtBall() == MoveDirection.emptyBitMask;
	}

	private boolean isBallBlocked() {
		return getBitMaskAtBall() == MoveDirection.fullBitMask;
	}

	private boolean didRedShootGoal() {
		return getBallY() > fieldHeight;
	}

	private boolean didBlueShootGoal() {
		return getBallY() < 0;
	}
}
