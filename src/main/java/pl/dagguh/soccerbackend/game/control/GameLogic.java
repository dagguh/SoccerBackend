package pl.dagguh.soccerbackend.game.control;

import javax.ejb.Stateless;
import org.apache.log4j.Logger;
import pl.dagguh.soccerbackend.game.entity.Game;
import pl.dagguh.soccerbackend.game.entity.GameField;

/**
 * @author Maciej Kwidziński <maciek.kwidzinski@gmail.com>
 */
@Stateless
public class GameLogic {

	private static Logger log = Logger.getLogger(GameLogic.class);

	public Game createNewGame(String redPlayerNick) {
		Game game = new Game();
		game.setRedPlayerNick(redPlayerNick);
		game.setBluePlayerNick("Oczekiwanie na przeciwnika...");
		game.setGameStatus(GameStatus.WAITING_FOR_OPPONENT);
		game.setGameField(new GameField());
		return game;
	}

	public Game joinToGame(Game game, String nick) {
		game.setBluePlayerNick(nick);
		game.setGameStatus(GameStatus.RED_PLAYER_TURN);
		return game;
	}

	public MoveStatus makeMove(MoveDirection direction, Game game) {
		if (!isReadyToMove(game)) {
			log.info("Moving when not ready to move " + game.getGameStatus());
			return MoveStatus.REJECTED;
		}
		GameField gameField = game.getGameField();
		return gameField.makeMove(direction);

	}

	private boolean isReadyToMove(Game game) {
		GameStatus status = game.getGameStatus();
		return (status == GameStatus.BLUE_PLAYER_TURN || status == GameStatus.RED_PLAYER_TURN);
	}

	public GameStatus interpretMoveStatus(MoveStatus moveStatus, GameStatus gameStatus) {
		switch (moveStatus) {
			case ACCEPTED_FINISH_TURN:
				return finishCurrentTurn(gameStatus);
			case ACCEPTED_RED_SHOOTS_GOAL:
				return GameStatus.RED_WINS;
			case ACCEPTED_BLUE_SHOOTS_GOAL:
				return GameStatus.BLUE_WINS;
			case ACCEPTED_BLOCKED:
				return gameOverForCurrentPlayer(gameStatus);
			default:
				return gameStatus;
		}
	}

	private GameStatus finishCurrentTurn(GameStatus status) {
		if (status == GameStatus.RED_PLAYER_TURN) {
			return GameStatus.BLUE_PLAYER_TURN;
		} else {
			return GameStatus.RED_PLAYER_TURN;
		}
	}

	private GameStatus gameOverForCurrentPlayer(GameStatus status) {
		if (status == GameStatus.RED_PLAYER_TURN) {
			return GameStatus.BLUE_WINS;
		} else {
			return GameStatus.RED_WINS;
		}
	}

}
