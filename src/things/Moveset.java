package things;

import java.util.ArrayList;
import java.util.List;

public class Moveset {
	
	public class Move {
		
		final int forward, right;
		final Type type;
		
		Move(int f, int r, Type t) {
			this.forward = f;
			this.right = r;
			this.type = t;
		}
		
		public int forward() {
			return this.forward;
		}
		
		public int right() {
			return this.right;
		}
		
		public Type type() {
			return this.type;
		}
		
		public List<Move> passingTiles(Position from, boolean invert) {
			//System.out.print(from.toString() + ">");
			
			List<Move> passingMoves = new ArrayList<>();
			int myForward = this.forward();
			int myRight = this.right();
			
			// if this move is off the board, shrink it inwards until it is
			while (from.shift(myForward, myRight, 0, invert) == null) {
				//System.out.print("-");
				if (type == Type.TELEPORTABLE) {
					return passingMoves;
				}
				
				myForward = Util.shrink(myForward);
				myRight = Util.shrink(myRight);
			}
			
			
			if (!from.equals(from.shift(myForward, myRight, 0, invert))) {
				Move farthest = new Move(myForward, myRight, this.type);
				passingMoves.add(farthest);
				
				if (type != Type.TELEPORTABLE) {
					farthest.calcPassTiles(passingMoves);
				}
			}
			return passingMoves;
		}
		
		private void calcPassTiles(List<Move> list) {
			//System.out.print(".");
			if (forward() <= 1 && forward() >= -1 && right() <= 1 && right() >= -1) {
				return;
			}
			
			Move inside = new Move(Util.shrink(forward()), Util.shrink(right()), this.type);
			list.add(0, inside);
			inside.calcPassTiles(list);
		}
	}
	
	public enum Type {
		BLOCKABLE,
		TELEPORTABLE,
		INITONLY_BLOCKABLE, // pawn initial 2-step
		ATTACK_HOP // cannon attack
	}
	
	List<Move>[] moves;
	
	@SuppressWarnings("unchecked")
	public Moveset() {
		this.moves = new List[Board.MAX_TIER];
		for (int i = 0; i < this.moves.length; i++) {
			this.moves[i] = new ArrayList<Move>();
		}
	}
	
	public void addMove(int tier, int forward, int right, Type type) {
		assert tier >= 1 && tier <= Board.MAX_TIER;
		
		this.moves[tier - 1].add(new Move(forward, right, type));
	}
	
	public List<Move> getMoves(int tier) {
		assert tier >= 0 && tier <= Board.MAX_TIER;
		
		if (tier == 0) {
			List<Move> allTiers = new ArrayList<>();
			allTiers.addAll(moves[0]);
			allTiers.addAll(moves[1]);
			allTiers.addAll(moves[2]);
			
			return allTiers;
		} else {
			return moves[tier - 1];
		}
	}
	
}