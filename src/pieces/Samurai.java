package pieces;

import things.Board;
import things.Moveset;
import things.Player;

public class Samurai extends Piece {
	
	public Samurai(Board b, Player p) {
		super(b, p);
	}
	
	@Override
	public String toString() {
		return "S";
	}
	
	protected Piece newInstance() {
		return new Samurai(this.board, this.player);
	}
	
	protected Moveset setMoveset() {
		Moveset moveset = new Moveset();

		moveset.addMove(1, 1, -1, Moveset.Type.BLOCKABLE);
		moveset.addMove(1, 1, 1, Moveset.Type.BLOCKABLE);
		moveset.addMove(1, -1, -1, Moveset.Type.BLOCKABLE);
		moveset.addMove(1, -1, 1, Moveset.Type.BLOCKABLE);

		moveset.addMove(2, 2, -2, Moveset.Type.BLOCKABLE);
		moveset.addMove(2, 2, 2, Moveset.Type.BLOCKABLE);
		moveset.addMove(2, -2, -2, Moveset.Type.BLOCKABLE);
		moveset.addMove(2, -2, 2, Moveset.Type.BLOCKABLE);

		moveset.addMove(3, Board.MAX_RANKS, -Board.MAX_FILES, Moveset.Type.BLOCKABLE);
		moveset.addMove(3, Board.MAX_RANKS, Board.MAX_FILES, Moveset.Type.BLOCKABLE);
		moveset.addMove(3, -Board.MAX_RANKS, -Board.MAX_FILES, Moveset.Type.BLOCKABLE);
		moveset.addMove(3, -Board.MAX_RANKS, Board.MAX_FILES, Moveset.Type.BLOCKABLE);
		
		return moveset;
	}
}