package pieces;

import things.Board;
import things.Moveset;
import things.Player;

public class Marshal extends Piece {
	
	public Marshal(Board b, Player p) {
		super(b, p);
	}
	
	@Override
	public String toString() {
		return "M";
	}
	
	protected Piece newInstance() {
		return new Marshal(this.board, this.player);
	}
	
	protected Moveset setMoveset() {
		Moveset moveset = new Moveset();
		
		moveset.addMove(1, 1, -1, Moveset.Type.BLOCKABLE);
		moveset.addMove(1, 1, 0, Moveset.Type.BLOCKABLE);
		moveset.addMove(1, 1, 1, Moveset.Type.BLOCKABLE);
		moveset.addMove(1, 0, -1, Moveset.Type.BLOCKABLE);
		moveset.addMove(1, 0, 1, Moveset.Type.BLOCKABLE);
		moveset.addMove(1, -1, -1, Moveset.Type.BLOCKABLE);
		moveset.addMove(1, -1, 0, Moveset.Type.BLOCKABLE);
		moveset.addMove(1, -1, 1, Moveset.Type.BLOCKABLE);
		
		moveset.addMove(2, 1, -1, Moveset.Type.BLOCKABLE);
		moveset.addMove(2, 1, 0, Moveset.Type.BLOCKABLE);
		moveset.addMove(2, 1, 1, Moveset.Type.BLOCKABLE);
		moveset.addMove(2, 0, -1, Moveset.Type.BLOCKABLE);
		moveset.addMove(2, 0, 1, Moveset.Type.BLOCKABLE);
		moveset.addMove(2, -1, -1, Moveset.Type.BLOCKABLE);
		moveset.addMove(2, -1, 0, Moveset.Type.BLOCKABLE);
		moveset.addMove(2, -1, 1, Moveset.Type.BLOCKABLE);
		
		moveset.addMove(3, 1, -1, Moveset.Type.BLOCKABLE);
		moveset.addMove(3, 1, 0, Moveset.Type.BLOCKABLE);
		moveset.addMove(3, 1, 1, Moveset.Type.BLOCKABLE);
		moveset.addMove(3, 0, -1, Moveset.Type.BLOCKABLE);
		moveset.addMove(3, 0, 1, Moveset.Type.BLOCKABLE);
		moveset.addMove(3, -1, -1, Moveset.Type.BLOCKABLE);
		moveset.addMove(3, -1, 0, Moveset.Type.BLOCKABLE);
		moveset.addMove(3, -1, 1, Moveset.Type.BLOCKABLE);
		
		return moveset;
	}
}