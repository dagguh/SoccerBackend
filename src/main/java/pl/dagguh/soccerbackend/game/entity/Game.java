package pl.dagguh.soccerbackend.game.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
public class Game implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private long id;
	private String redPlayerNick;
	private String bluePlayerNick;
	private boolean isRedPlayerReady = false;
	private boolean isBluePlayerReady = false;

	public long getId() {
		return id;
	}

	public String getBluePlayerNick() {
		return bluePlayerNick;
	}

	public void setBluePlayerNick(String bluePlayerNick) {
		this.bluePlayerNick = bluePlayerNick;
	}

	public boolean isIsBluePlayerReady() {
		return isBluePlayerReady;
	}

	public void setIsBluePlayerReady(boolean isBluePlayerReady) {
		this.isBluePlayerReady = isBluePlayerReady;
	}

	public boolean isIsRedPlayerReady() {
		return isRedPlayerReady;
	}

	public void setIsRedPlayerReady(boolean isRedPlayerReady) {
		this.isRedPlayerReady = isRedPlayerReady;
	}

	public String getRedPlayerNick() {
		return redPlayerNick;
	}

	public void setRedPlayerNick(String redPlayerNick) {
		this.redPlayerNick = redPlayerNick;
	}

	@Override
	public String toString() {
		return "Game{" + "id=" + id + ", redPlayerNick=" + redPlayerNick + ", bluePlayerNick=" + bluePlayerNick + ", isRedPlayerReady=" + isRedPlayerReady + ", isBluePlayerReady=" + isBluePlayerReady + '}';
	}
}
