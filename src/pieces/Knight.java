package pieces;

import things.Board;
import things.Moveset;
import things.Player;

public class Knight extends Piece {
	
	public Knight(Board b, Player p) {
		super(b, p);
	}
	
	@Override
	public String toString() {
		return "K";
	}
	
	protected Piece newInstance() {
		return new Knight(this.board, this.player);
	}
	
	protected Moveset setMoveset() {
		Moveset moveset = new Moveset();

		moveset.addMove(1, 2, -1, Moveset.Type.TELEPORTABLE);
		moveset.addMove(1, 2, 1, Moveset.Type.TELEPORTABLE);
		moveset.addMove(1, 1, 1, Moveset.Type.TELEPORTABLE);
		moveset.addMove(1, 0, -1, Moveset.Type.TELEPORTABLE);
		moveset.addMove(1, 0, 1, Moveset.Type.TELEPORTABLE);

		moveset.addMove(2, 2, -1, Moveset.Type.TELEPORTABLE);
		moveset.addMove(2, 2, 1, Moveset.Type.TELEPORTABLE);
		moveset.addMove(2, 1, -2, Moveset.Type.TELEPORTABLE);
		moveset.addMove(2, 1, -1, Moveset.Type.TELEPORTABLE);
		moveset.addMove(2, 1, 1, Moveset.Type.TELEPORTABLE);
		moveset.addMove(2, 1, 2, Moveset.Type.TELEPORTABLE);
		moveset.addMove(2, -1, -1, Moveset.Type.TELEPORTABLE);
		moveset.addMove(2, -1, 1, Moveset.Type.TELEPORTABLE);

		moveset.addMove(3, 2, -1, Moveset.Type.TELEPORTABLE);
		moveset.addMove(3, 2, 1, Moveset.Type.TELEPORTABLE);
		moveset.addMove(3, 1, -2, Moveset.Type.TELEPORTABLE);
		moveset.addMove(3, 1, 2, Moveset.Type.TELEPORTABLE);
		moveset.addMove(3, -1, -2, Moveset.Type.TELEPORTABLE);
		moveset.addMove(3, -1, 2, Moveset.Type.TELEPORTABLE);
		moveset.addMove(3, -2, -1, Moveset.Type.TELEPORTABLE);
		moveset.addMove(3, -2, 1, Moveset.Type.TELEPORTABLE);
		
		return moveset;
	}
}