package pl.dagguh.soccerbackend.player.control;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Maciej Kwidziński <maciek.kwidzinski@gmail.com>
 */
@XmlRootElement
public class PlayerToken {

	private String nick;
	private String ticket;

	public PlayerToken() {
	}

	public PlayerToken(String nick, String ticket) {
		this.nick = nick;
		this.ticket = ticket;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	@Override
	public String toString() {
		return "PlayerToken{" + "nick=" + nick + ", ticket=" + ticket + '}';
	}
}
