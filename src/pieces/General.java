package pieces;

import things.Board;
import things.Moveset;
import things.Player;

public class General extends Piece {
	
	public General(Board b, Player p) {
		super(b, p);
	}
	
	@Override
	public String toString() {
		return "G";
	}
	
	protected Piece newInstance() {
		return new General(this.board, this.player);
	}
	
	protected Moveset setMoveset() {
		Moveset moveset = new Moveset();

		moveset.addMove(1, 1, -1, Moveset.Type.BLOCKABLE);
		moveset.addMove(1, 1, 1, Moveset.Type.BLOCKABLE);

		moveset.addMove(2, 1, -1, Moveset.Type.BLOCKABLE);
		moveset.addMove(2, 1, 0, Moveset.Type.BLOCKABLE);
		moveset.addMove(2, 1, 1, Moveset.Type.BLOCKABLE);
		moveset.addMove(2, -1, -1, Moveset.Type.BLOCKABLE);
		moveset.addMove(2, -1, 1, Moveset.Type.BLOCKABLE);

		moveset.addMove(3, 1, -1, Moveset.Type.BLOCKABLE);
		moveset.addMove(3, 1, 0, Moveset.Type.BLOCKABLE);
		moveset.addMove(3, 1, 1, Moveset.Type.BLOCKABLE);
		moveset.addMove(3, 0, -1, Moveset.Type.BLOCKABLE);
		moveset.addMove(3, 0, 1, Moveset.Type.BLOCKABLE);
		moveset.addMove(3, -1, 0, Moveset.Type.BLOCKABLE);
		
		return moveset;
	}
}