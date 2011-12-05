package pl.dagguh.soccerbackend.game.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import pl.dagguh.soccerbackend.game.control.GameStatus;

/**
 *
 * @author Maciej Kwidziński <maciek.kwidzinski@gmail.com>
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Game implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private long id;
	private String redPlayerNick;
	private String bluePlayerNick;
	@OneToOne
	private GameField gameField;
	private GameStatus gameStatus;

	public long getId() {
		return id;
	}

	public String getBluePlayerNick() {
		return bluePlayerNick;
	}

	public void setBluePlayerNick(String bluePlayerNick) {
		this.bluePlayerNick = bluePlayerNick;
	}

	public GameField getGameField() {
		return gameField;
	}

	public void setGameField(GameField gameField) {
		this.gameField = gameField;
	}

	public GameStatus getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(GameStatus gameStatus) {
		this.gameStatus = gameStatus;
	}

	public String getRedPlayerNick() {
		return redPlayerNick;
	}

	public void setRedPlayerNick(String redPlayerNick) {
		this.redPlayerNick = redPlayerNick;
	}

	@Override
	public String toString() {
		return "Game{" + "id=" + id + ", redPlayerNick=" + redPlayerNick + ", bluePlayerNick=" + bluePlayerNick + ", gameField=" + gameField + ", gameStatus=" + gameStatus + '}';
	}
}
