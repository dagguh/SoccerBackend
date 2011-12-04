package pl.dagguh.soccerbackend.player.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Maciej Kwidziński <maciek.kwidzinski@gmail.com>
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Player implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final int initialScore = 1200;
	@Id
	private String nick;
	private String password;
	private int score = initialScore;
	private String email;
	private String ticket;

	public String getNick() {
		return nick;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Player{" + "nick=" + nick + ", password=" + password + ", score=" + score + ", email=" + email + ", ticket=" + ticket + '}';
	}
}
