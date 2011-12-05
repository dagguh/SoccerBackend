package pl.dagguh.soccerbackend.game.control;

import pl.dagguh.soccerbackend.game.control.exceptions.UnexpectedMoveDirection;

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
	public static final int emptyBitMask = 0;
	public static final int fullBitMask = N.mask | S.mask | W.mask | E.mask | NW.mask | NE.mask | SW.mask | SE.mask;

	private MoveDirection(int flagIndex) {
		this.mask = flagIndex;
	}

	public int getMask() {
		return mask;
	}

	public MoveDirection getOpposite() throws UnexpectedMoveDirection {
		switch (this) {
			case N:
				return S;
			case NE:
				return SW;
			case E:
				return W;
			case SE:
				return NW;
			case S:
				return N;
			case SW:
				return NE;
			case W:
				return E;
			case NW:
				return SE;
			default:
				throw new UnexpectedMoveDirection(this);
		}
	}
}
