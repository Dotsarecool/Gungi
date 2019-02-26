package things;
import java.util.ArrayList;
import java.util.List;
import pieces.*;

public class Player {
	
	public enum Color {
		BLACK, WHITE
	}
	
	Board board;
	Color color;
	List<IPiece> hand;
	
	public Player(Board b, Color c) {
		this.board = b;
		this.color = c;
		this.hand = getStartingPieces();
	}
	
	@Override
	public String toString() {
		return this.color.toString();
	}
	
	public Player clone(Board b) {
		Player p = new Player(b, this.color);
		p.hand = new ArrayList<IPiece>();
		
		for (int i = 0; i < this.hand.size(); i++) {
			p.hand.add(this.hand.get(i).clone(b));
		}
		
		return p;
	}
	
	public boolean is(Player p) {
		return this.getColor() == p.getColor();
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public boolean isReversed() {
		return this.color == Color.BLACK;
	}
	
	public List<IPiece> getHand() {
		return this.hand;
	}
	
	public void drop(IPiece piece, Position position) {
		this.hand.remove(piece);
		this.board.addPiece(piece, position);
	}
	
	private List<IPiece> getStartingPieces() {
		List<IPiece> list = new ArrayList<IPiece>();
		list.add(new LieutenantGeneral(this.board, this));
		list.add(new LieutenantGeneral(this.board, this));
		list.add(new MajorGeneral(this.board, this));
		list.add(new MajorGeneral(this.board, this));
		list.add(new MajorGeneral(this.board, this));
		list.add(new MajorGeneral(this.board, this));
		list.add(new General(this.board, this));
		list.add(new General(this.board, this));
		list.add(new General(this.board, this));
		list.add(new General(this.board, this));
		list.add(new General(this.board, this));
		list.add(new General(this.board, this));
		list.add(new Fortress(this.board, this));
		list.add(new Fortress(this.board, this));
		list.add(new Counsel(this.board, this));
		list.add(new Counsel(this.board, this));
		list.add(new Cannon(this.board, this));
		list.add(new Cannon(this.board, this));
		list.add(new Musketeer(this.board, this));
		list.add(new Musketeer(this.board, this));
		list.add(new Samurai(this.board, this));
		list.add(new Samurai(this.board, this));
		list.add(new Knight(this.board, this));
		list.add(new Knight(this.board, this));
		list.add(new Spy(this.board, this));
		list.add(new Spy(this.board, this));
		list.add(new Archer(this.board, this));
		list.add(new Archer(this.board, this));
		list.add(new Pawn(this.board, this));
		list.add(new Pawn(this.board, this));
		list.add(new Pawn(this.board, this));
		list.add(new Pawn(this.board, this));
		list.add(new Pawn(this.board, this));
		list.add(new Pawn(this.board, this));
		list.add(new Pawn(this.board, this));
		list.add(new Pawn(this.board, this));
		list.add(new Pawn(this.board, this));
		list.add(new Marshal(this.board, this));
		return list;
	}
}