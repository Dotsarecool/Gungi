package pieces;

import things.Board;
import things.Moveset;
import things.Player;

public class Archer extends Piece {
	
	public Archer(Board b, Player p) {
		super(b, p);
	}
	
	@Override
	public String toString() {
		return "A";
	}
	
	protected Piece newInstance() {
		return new Archer(this.board, this.player);
	}
	
	protected Moveset setMoveset() {
		Moveset moveset = new Moveset();

		moveset.addMove(1, 1, -1, Moveset.Type.TELEPORTABLE);
		moveset.addMove(1, 1, 0, Moveset.Type.TELEPORTABLE);
		moveset.addMove(1, 1, 1, Moveset.Type.TELEPORTABLE);
		moveset.addMove(1, 0, -1, Moveset.Type.TELEPORTABLE);
		moveset.addMove(1, 0, 1, Moveset.Type.TELEPORTABLE);
		moveset.addMove(1, -1, -1, Moveset.Type.TELEPORTABLE);
		moveset.addMove(1, -1, 0, Moveset.Type.TELEPORTABLE);
		moveset.addMove(1, -1, 1, Moveset.Type.TELEPORTABLE);

		moveset.addMove(2, 2, -2, Moveset.Type.TELEPORTABLE);
		moveset.addMove(2, 2, -1, Moveset.Type.TELEPORTABLE);
		moveset.addMove(2, 2, 0, Moveset.Type.TELEPORTABLE);
		moveset.addMove(2, 2, 1, Moveset.Type.TELEPORTABLE);
		moveset.addMove(2, 2, 2, Moveset.Type.TELEPORTABLE);
		moveset.addMove(2, 1, -2, Moveset.Type.TELEPORTABLE);
		moveset.addMove(2, 1, 2, Moveset.Type.TELEPORTABLE);
		moveset.addMove(2, 0, -2, Moveset.Type.TELEPORTABLE);
		moveset.addMove(2, 0, 2, Moveset.Type.TELEPORTABLE);
		moveset.addMove(2, -1, -2, Moveset.Type.TELEPORTABLE);
		moveset.addMove(2, -1, 2, Moveset.Type.TELEPORTABLE);
		moveset.addMove(2, -2, -2, Moveset.Type.TELEPORTABLE);
		moveset.addMove(2, -2, -1, Moveset.Type.TELEPORTABLE);
		moveset.addMove(2, -2, 0, Moveset.Type.TELEPORTABLE);
		moveset.addMove(2, -2, 1, Moveset.Type.TELEPORTABLE);
		moveset.addMove(2, -2, 2, Moveset.Type.TELEPORTABLE);

		moveset.addMove(3, 3, -3, Moveset.Type.TELEPORTABLE);
		moveset.addMove(3, 3, -2, Moveset.Type.TELEPORTABLE);
		moveset.addMove(3, 3, -1, Moveset.Type.TELEPORTABLE);
		moveset.addMove(3, 3, 0, Moveset.Type.TELEPORTABLE);
		moveset.addMove(3, 3, 1, Moveset.Type.TELEPORTABLE);
		moveset.addMove(3, 3, 2, Moveset.Type.TELEPORTABLE);
		moveset.addMove(3, 3, 3, Moveset.Type.TELEPORTABLE);
		moveset.addMove(3, 2, -3, Moveset.Type.TELEPORTABLE);
		moveset.addMove(3, 2, 3, Moveset.Type.TELEPORTABLE);
		moveset.addMove(3, 1, -3, Moveset.Type.TELEPORTABLE);
		moveset.addMove(3, 1, 3, Moveset.Type.TELEPORTABLE);
		moveset.addMove(3, 0, -3, Moveset.Type.TELEPORTABLE);
		moveset.addMove(3, 0, 3, Moveset.Type.TELEPORTABLE);
		moveset.addMove(3, -1, -3, Moveset.Type.TELEPORTABLE);
		moveset.addMove(3, -1, 3, Moveset.Type.TELEPORTABLE);
		moveset.addMove(3, -2, -3, Moveset.Type.TELEPORTABLE);
		moveset.addMove(3, -2, 3, Moveset.Type.TELEPORTABLE);
		moveset.addMove(3, -3, -3, Moveset.Type.TELEPORTABLE);
		moveset.addMove(3, -3, -2, Moveset.Type.TELEPORTABLE);
		moveset.addMove(3, -3, -1, Moveset.Type.TELEPORTABLE);
		moveset.addMove(3, -3, 0, Moveset.Type.TELEPORTABLE);
		moveset.addMove(3, -3, 1, Moveset.Type.TELEPORTABLE);
		moveset.addMove(3, -3, 2, Moveset.Type.TELEPORTABLE);
		moveset.addMove(3, -3, 3, Moveset.Type.TELEPORTABLE);
		
		return moveset;
	}
}