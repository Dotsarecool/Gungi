package pieces;

import things.Board;
import things.Moveset;
import things.Player;

public class Counsel extends Piece {
	
	public Counsel(Board b, Player p) {
		super(b, p);
	}
	
	@Override
	public String toString() {
		return "U";
	}
	
	protected Piece newInstance() {
		return new Counsel(this.board, this.player);
	}
	
	protected Moveset setMoveset() {
		Moveset moveset = new Moveset();

		moveset.addMove(1, 3, -3, Moveset.Type.BLOCKABLE);
		moveset.addMove(1, 3, 3, Moveset.Type.BLOCKABLE);
		moveset.addMove(1, 0, -3, Moveset.Type.BLOCKABLE);
		moveset.addMove(1, 0, 3, Moveset.Type.BLOCKABLE);
		moveset.addMove(1, -2, -2, Moveset.Type.BLOCKABLE);
		moveset.addMove(1, -2, 2, Moveset.Type.BLOCKABLE);

		moveset.addMove(2, 3, -3, Moveset.Type.BLOCKABLE);
		moveset.addMove(2, 3, 3, Moveset.Type.BLOCKABLE);
		moveset.addMove(2, 0, -3, Moveset.Type.BLOCKABLE);
		moveset.addMove(2, 0, 3, Moveset.Type.BLOCKABLE);
		moveset.addMove(2, -2, -2, Moveset.Type.BLOCKABLE);
		moveset.addMove(2, -2, 2, Moveset.Type.BLOCKABLE);

		moveset.addMove(3, 3, -3, Moveset.Type.BLOCKABLE);
		moveset.addMove(3, 3, 3, Moveset.Type.BLOCKABLE);
		moveset.addMove(3, 0, -3, Moveset.Type.BLOCKABLE);
		moveset.addMove(3, 0, 3, Moveset.Type.BLOCKABLE);
		moveset.addMove(3, -2, -2, Moveset.Type.BLOCKABLE);
		moveset.addMove(3, -2, 2, Moveset.Type.BLOCKABLE);
		
		return moveset;
	}
}