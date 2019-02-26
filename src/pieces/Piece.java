package pieces;

import java.util.ArrayList;
import java.util.List;

import things.Board;
import things.Moveset;
import things.Moveset.Move;
import things.Player;
import things.Position;
import things.Util;

public abstract class Piece implements IPiece {
	
	Player player;
	Board board;
	Position position;
	Moveset moveset;
	boolean inHand;
	int moveCount;
	
	protected Piece(Board b, Player p) {
		this.board = b;
		this.player = p;
		this.inHand = true;
		this.moveCount = 0;
		this.moveset = setMoveset();
	}
	
	protected abstract Moveset setMoveset();
	protected abstract Piece newInstance();
	
	@Override
	public IPiece clone(Board b) {
		Piece piece = this.newInstance();

		piece.board = b;
		piece.position = this.position;
		piece.moveset = this.moveset;
		piece.inHand = this.inHand;
		piece.moveCount = this.moveCount;
		
		return piece;
	}

	@Override
	public Position getPosition() {
		return this.position;
	}
	
	@Override
	public Player getPlayer() {
		return this.player;
	}

	@Override
	public boolean isFriendly(IPiece piece) {
		return this.getPlayer().is(piece.getPlayer());
	}
	
	@Override
	public boolean isInHand() {
		return this.inHand;
	}
	
	@Override
	public boolean isAlive() {
		return position != null;
	}
	
	@Override
	public boolean hasntMovedYet() {
		return this.moveCount == 0;
	}
	
	@Override
	public void drop(Position p) {
		this.position = p;
		this.inHand = false;
	}
	
	@Override
	public void move(Position p) {
		this.position = p;
		this.moveCount++;
	}
	
	@Override
	public void kill() {
		this.position = null;
	}

	@Override
	public List<Position> getValidMoves() {
		List<Position> list = new ArrayList<>();
		List<Move> moves = this.moveset.getMoves(getMovementTier());

		//System.out.printf("Calcualting valid moves for %s piece at %s, acting tier %d.%n", this.toString(), this.getPosition().toString(), getMovementTier());
		
		for (int i = 0; i < moves.size(); i++) {
			Move move = moves.get(i);
			
			//System.out.printf("-parent move R%02d, F%02d%n", move.forward(), move.right());
			
			List<Move> passing = move.passingTiles(this.getPosition(), this.player.isReversed());
			//System.out.println("x");
					
			switch (moves.get(i).type()) {
				case INITONLY_BLOCKABLE: {
					if (
							!hasntMovedYet() ||
							this.getPosition().tier() != 1 ||
							(this.player.isReversed() ? Board.MAX_RANKS - this.getPosition().rank() + 1 : this.getPosition().rank()) != 3
						) {
						break;
					}
					// continue
				}
				case TELEPORTABLE: case BLOCKABLE: {
					for (int j = 0; j < passing.size(); j++) {
						Move pMove = passing.get(j);
						
						//System.out.printf("--passing move R%02d, F%02d%n", move.forward(), move.right());
						
						Position passingSquare = this.getPosition().shift(pMove.forward(), pMove.right(), 0, this.player.isReversed());
						List<IPiece> stack = this.board.getStack(passingSquare);
						
						//System.out.printf(String.format("---checking %s", passingSquare.toString()));
						
						if (stack.size() == 0) {
							Position shiftedPosition = passingSquare.shift(0, 0, 1 - this.getPosition().tier(), this.player.isReversed()); 
							list.add(shiftedPosition);
							
							//System.out.printf(", yes! to %s%n", shiftedPosition.toString());
						} else if (stack.size() < Board.MAX_TIER) {
							if (!(stack.get(stack.size() - 1) instanceof Marshal)) {
								Position shiftedPosition = passingSquare.shift(0, 0, 1 + stack.size() - this.getPosition().tier(), this.player.isReversed());
								list.add(shiftedPosition);
								
								//System.out.printf(", yes! to %s%n", shiftedPosition.toString());
							} else {
								//System.out.println(", no");
							}
							break;
						} else {
							//System.out.println(", no");
							break;
						}
					}
					break;
				}
				case ATTACK_HOP: {
					break;
				}
			 }
		}
		
		return list;
	}

	@Override
	public List<Position> getValidAttacks() {
		List<Position> list = new ArrayList<>();
		List<Move> moves = this.moveset.getMoves(getMovementTier());

		//System.out.printf("Calcualting valid attacks for %s piece at %s, acting tier %d.%n", this.toString(), this.getPosition().toString(), getMovementTier());
		
		for (int i = 0; i < moves.size(); i++) {
			Move move = moves.get(i);
			
			//System.out.printf("-parent move R%02d, F%02d%n", move.forward(), move.right());
			
			List<Move> passing = move.passingTiles(this.getPosition(), this.player.isReversed());
			//System.out.println("x");
					
			switch (moves.get(i).type()) {
				case INITONLY_BLOCKABLE: {
					if (
							!hasntMovedYet() ||
							this.getPosition().tier() != 1 ||
							(this.player.isReversed() ? Board.MAX_RANKS - this.getPosition().rank() + 1 : this.getPosition().rank()) != 3
						) {
						break;
					}
					// continue
				}
				case TELEPORTABLE: case BLOCKABLE: {
					if (this instanceof Cannon) {
						//System.out.println(", no");
						break;
					}
					
					for (int j = 0; j < passing.size(); j++) {
						Move pMove = passing.get(j);
						
						//System.out.printf("--passing move R%02d, F%02d%n", move.forward(), move.right());
						
						Position passingSquare = this.getPosition().shift(pMove.forward(), pMove.right(), 0, this.player.isReversed());
						List<IPiece> stack = this.board.getStack(passingSquare);
						
						//System.out.printf(String.format("---checking %s", passingSquare.toString()));
						
						if (stack.size() != 0) {
							if (!stack.get(stack.size() - 1).getPlayer().is(this.player)) {
								Position shiftedPosition = passingSquare.shift(0, 0, stack.size() - this.getPosition().tier(), this.player.isReversed()); 
								if (shiftedPosition == null) {
									System.out.println("NULL");
								}
								list.add(shiftedPosition);
								//System.out.printf(", yes! to %s%n", shiftedPosition.toString());
							} else {
								//System.out.println(", no");
							}
							break;
						} else {
							//System.out.println(", no");
						}
					}
					break;
				}
				case ATTACK_HOP: {
					boolean seenPiece = false;
					for (int j = 0; j < passing.size(); j++) {
						Move pMove = passing.get(j);
						Position passingSquare = this.getPosition().shift(pMove.forward(), pMove.right(), 0, this.player.isReversed());
						List<IPiece> stack = this.board.getStack(passingSquare);
						
						//System.out.printf(String.format("---checking %s", passingSquare.toString()));
						
						if (seenPiece && stack.size() != 0) {
							if (!stack.get(stack.size() - 1).getPlayer().is(this.player)) {
								Position shiftedPosition = passingSquare.shift(0, 0, stack.size() - this.getPosition().tier(), this.player.isReversed()); 
								list.add(shiftedPosition);
								
								//System.out.printf(", yes! to %s%n", shiftedPosition.toString());
								break;
							} else {
								//System.out.println(", no");
							}
						} else if (stack.size() != 0) {
							seenPiece = true;
							//System.out.println(", no");
						} else {
							//System.out.println(", no");
						}
					}
					break;
				}
			 }
		}
		
		return list;
	}

	@Override
	public List<Position> getValidDrops(boolean limit) {
		List<Position> list = new ArrayList<>();
		
		for (int i = 0; i < (limit ? 3 : Board.MAX_RANKS); i++) {
			int rank = this.player.isReversed() ? Board.MAX_RANKS - i : i + 1;
			
			for (int file = 1; file <= Board.MAX_FILES; file++) {
				List<IPiece> stack = this.board.getStack(new Position(rank, file, 0));
				int tier = stack.size(); 
				
				if (tier < Board.MAX_TIER && !(tier > 0 && stack.get(tier - 1) instanceof Marshal)) {
					list.add(new Position(rank, file, tier + 1));
				}
			}
		}
		
		return list;
	}
	
	// return 0 for all tiers
	private int getMovementTier() {
		int tier = this.getPosition().tier();
		int myRank = this.getPosition().rank(), myFile = this.getPosition().file();
		
		for (int rank = (myRank - 1 < 1 ? 1 : myRank - 1); rank <= (myRank + 1 > Board.MAX_RANKS ? Board.MAX_RANKS : myRank + 1); rank++) {
			for (int file = (myFile - 1 < 1 ? 1 : myFile - 1); file <= (myFile + 1 > Board.MAX_FILES ? Board.MAX_FILES : myFile + 1); file++) {
				List<IPiece> stack = board.getStack(new Position(rank, file, 1));
				if (stack.size() > 0) {
					IPiece piece = stack.get(stack.size() - 1);
					
					if (piece.getPlayer().is(this.getPlayer()) && piece instanceof Fortress) {
						int fortTier = piece.getPosition().tier();
						tier = fortTier + 1 > tier ? (fortTier >= 3 ? 3 : fortTier + 1) : tier;
					}
				}
			}
		}
		
		for (int i = 0; i < Util.directions.length; i++) {
			for (int j = 1; j < Board.MAX_RANKS; j++) {
				Position checkSpace = this.getPosition().shift(j * Util.directions[i][0], j * Util.directions[i][1], 0, false);
				
				if (checkSpace == null) {
					break;
				}
				
				List<IPiece> stack = this.board.getStack(checkSpace);
				
				if (stack.size() > 0) {
					IPiece top = stack.get(stack.size() - 1);
					
					if (top.getPlayer().is(this.getPlayer()) && top instanceof LieutenantGeneral) {
						return 0;
					}
					break;
				}
			}
		}
		
		return tier;
	}
}