package pl.dagguh.soccerbackend.entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Maciej Kwidziński <maciek.kwidzinski@gmail.com>
 */
@Entity
@XmlRootElement
public class Account implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@OneToOne(cascade = CascadeType.ALL, optional = false)
	private Player player;
	private String password;
	private String ticket;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	@Override
	public String toString() {
		return "Account{" + "player=" + player + ", ticket=" + ticket + '}';
	}
}
