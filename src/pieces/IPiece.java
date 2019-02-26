package pieces;

import java.util.List;

import things.Board;
import things.Player;
import things.Position;

public interface IPiece {
	Position getPosition();
	Player getPlayer();
	List<Position> getValidMoves();
	List<Position> getValidAttacks();
	List<Position> getValidDrops(boolean limit);
	boolean isFriendly(IPiece piece);
	boolean isInHand();
	boolean isAlive();
	boolean hasntMovedYet();
	void drop(Position p);
	void move(Position p);
	void kill();
	IPiece clone(Board b);
}