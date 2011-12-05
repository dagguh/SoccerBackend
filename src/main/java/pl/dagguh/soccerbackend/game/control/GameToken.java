package pl.dagguh.soccerbackend.game.control;

import javax.xml.bind.annotation.XmlRootElement;
import pl.dagguh.soccerbackend.player.control.PlayerToken;

/**
 * @author Maciej Kwidziński <maciek.kwidzinski@gmail.com>
 */
@XmlRootElement
public class GameToken {

	private PlayerToken playerToken;
	private String gameId;

	public GameToken() {
	}

	public GameToken(PlayerToken playerToken, String gameId) {
		this.playerToken = playerToken;
		this.gameId = gameId;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public PlayerToken getPlayerToken() {
		return playerToken;
	}

	public void setPlayerToken(PlayerToken playerToken) {
		this.playerToken = playerToken;
	}

	@Override
	public String toString() {
		return "GameToken{" + "playerToken=" + playerToken + ", gameId=" + gameId + '}';
	}
}
