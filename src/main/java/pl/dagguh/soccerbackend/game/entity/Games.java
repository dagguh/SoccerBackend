package pl.dagguh.soccerbackend.game.entity;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Maciej Kwidziński <maciek.kwidzinski@gmail.com>
 */
@XmlRootElement
public class Games extends ArrayList<Game> {

	private List<Game> games;

	public Games() {
	}

	public Games(List<Game> games) {
		this.games = games;
	}

	public List<Game> getGames() {
		return games;
	}

	public void setGames(List<Game> games) {
		this.games = games;
	}

	@Override
	public String toString() {
		return "Games{" + "games=" + games + '}';
	}
}
