package pieces;

import things.Board;
import things.Moveset;
import things.Player;

public class Musketeer extends Piece {
	
	public Musketeer(Board b, Player p) {
		super(b, p);
	}
	
	@Override
	public String toString() {
		return "R";
	}
	
	protected Piece newInstance() {
		return new Musketeer(this.board, this.player);
	}
	
	protected Moveset setMoveset() {
		Moveset moveset = new Moveset();

		moveset.addMove(1, 1, -1, Moveset.Type.BLOCKABLE);
		moveset.addMove(1, 1, 1, Moveset.Type.BLOCKABLE);
		moveset.addMove(1, -1, 0, Moveset.Type.BLOCKABLE);

		moveset.addMove(2, 3, 0, Moveset.Type.BLOCKABLE);
		
		moveset.addMove(3, Board.MAX_RANKS, 0, Moveset.Type.BLOCKABLE);
		
		return moveset;
	}
}