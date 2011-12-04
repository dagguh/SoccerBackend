package pl.dagguh.soccerbackend.game.control;

/**
 *
 * @author Maciej Kwidziński <maciek.kwidzinski@gmail.com>
 */
public enum MoveDirection {

	N(1),
	NE(2),
	E(4),
	SE(8),
	S(16),
	SW(32),
	W(64),
	NW(128);
	private final int mask;

	private MoveDirection(int flagIndex) {
		this.mask = flagIndex;
	}

	public int getMask() {
		return mask;
	}
	
}
