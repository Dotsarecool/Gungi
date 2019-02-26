package pieces;

import things.Board;
import things.Moveset;
import things.Player;

public class Spy extends Piece {
	
	public Spy(Board b, Player p) {
		super(b, p);
	}
	
	@Override
	public String toString() {
		return "Y";
	}
	
	protected Piece newInstance() {
		return new Spy(this.board, this.player);
	}
	
	protected Moveset setMoveset() {
		Moveset moveset = new Moveset();

		moveset.addMove(1, 1, 0, Moveset.Type.BLOCKABLE);
		moveset.addMove(1, -1, -1, Moveset.Type.BLOCKABLE);
		moveset.addMove(1, -1, 0, Moveset.Type.BLOCKABLE);
		moveset.addMove(1, -1, 1, Moveset.Type.BLOCKABLE);

		moveset.addMove(2, 2, -2, Moveset.Type.BLOCKABLE);
		moveset.addMove(2, 2, 0, Moveset.Type.BLOCKABLE);
		moveset.addMove(2, 2, 2, Moveset.Type.BLOCKABLE);
		moveset.addMove(2, 0, -2, Moveset.Type.BLOCKABLE);
		moveset.addMove(2, 0, 2, Moveset.Type.BLOCKABLE);
		moveset.addMove(1, -1, 0, Moveset.Type.BLOCKABLE);

		moveset.addMove(3, Board.MAX_RANKS, -Board.MAX_FILES, Moveset.Type.BLOCKABLE);
		moveset.addMove(3, Board.MAX_RANKS, 0, Moveset.Type.BLOCKABLE);
		moveset.addMove(3, Board.MAX_RANKS, Board.MAX_FILES, Moveset.Type.BLOCKABLE);
		moveset.addMove(3, 0, Board.MAX_FILES, Moveset.Type.BLOCKABLE);
		moveset.addMove(3, 0, -Board.MAX_FILES, Moveset.Type.BLOCKABLE);
		moveset.addMove(3, -Board.MAX_RANKS, 0, Moveset.Type.BLOCKABLE);
		
		return moveset;
	}
}