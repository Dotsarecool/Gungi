package things;
import java.util.ArrayList;
import java.util.List;

import pieces.IPiece;
import pieces.Marshal;

public class Board {
	
	public static int MAX_RANKS = 9, MAX_FILES = 9, MAX_TIER = 3;
	
	Player [] players;
	List<IPiece>[][] pieces;
	
	@SuppressWarnings("unchecked")
	public Board() {
		this.pieces = new List[MAX_RANKS][MAX_FILES];
		for (int i = 0; i < this.pieces.length; i++) {
			for (int j = 0; j < this.pieces[i].length; j++) {
				this.pieces[i][j] = new ArrayList<IPiece>();
			}
		}
		this.players = new Player [] {
			new Player(this, Player.Color.BLACK),
			new Player(this, Player.Color.WHITE)
		};
	}
	
	public Player getPlayer(int i) {
		return this.players[i];
	}
	
	// 0 = nothing, 1 = check, 2 = checkmate; for each p1 and p2
	public int[] getCheckStatus(boolean checkmate) {
		int [] check = new int[2];
		
		for (int i = 0; i < check.length; i++) {
			List<IPiece> topPieces = getPlayersTopPieces(getPlayer(i));
			
			checkCheck:
			for (int j = 0; j < topPieces.size(); j++) {
				List<Position> attacks = topPieces.get(j).getValidAttacks();
				
				for (Position p : attacks) {
					IPiece target = this.getStack(p).get(p.tier() - 1);
					
					if (target instanceof Marshal && !target.getPlayer().is(getPlayer(i))) {
						check[i ^ 1] = 1;
						break checkCheck;
					}
				}
			}
		}
		
		// for checkmate, recursively check each move for check
		if (checkmate) {
			play:
			for (int i = 0; i < check.length; i++) {
				List<String> allValidMoves = new ArrayList<>();
				
				if (check[i] == 1) {
					List<IPiece> topPieces = getPlayersTopPieces(getPlayer(i));
					
					for (int j = 0; j < topPieces.size(); j++) {
						List<Position> moves = topPieces.get(j).getValidMoves();
						
						for (int k = 0; k < moves.size(); k++) {
							Board b = this.clone();
							b.movePiece(topPieces.get(j).getPosition(), moves.get(k));
							
							if (b.getCheckStatus(false)[i] == 0) {
								allValidMoves.add(String.format("Piece %s moves from %s to %s.", topPieces.get(j), topPieces.get(j).getPosition(), moves.get(k)));
								//continue play;
							}
						}
						
						List<Position> attacks = topPieces.get(j).getValidAttacks();
						
						for (int k = 0; k < attacks.size(); k++) {
							Board b = this.clone();
							b.removePiece(attacks.get(k));
							b.movePiece(topPieces.get(j).getPosition(), attacks.get(k));
							
							if (b.getCheckStatus(false)[i] == 0) {
								allValidMoves.add(String.format("Piece %s attacks from %s to %s.", topPieces.get(j), topPieces.get(j).getPosition(), attacks.get(k)));
								//continue play;
							}
						}
					}
					
					List<IPiece> hand = getPlayer(i).getHand();
					
					for (int j = 0; j < hand.size(); j++) {
						List<Position> drops = hand.get(j).getValidDrops(false);
						
						for (int k = 0; k < drops.size(); k++) {
							Board b = this.clone();
							IPiece p = hand.get(j).clone(b);
							b.addPiece(p, drops.get(k));

							if (b.getCheckStatus(false)[i] == 0) {
								allValidMoves.add(String.format("Piece %s drops to %s.", p, drops.get(k)));
								//continue play;
							}
						}
					}
					
							if (allValidMoves.isEmpty()) {
								check[i] = 2;
							} else {
								System.out.println("All valid moves for player " + i);
								for (String x : allValidMoves) {
									System.out.println(x);
								}
							}
					
				}
			}
		}
		
		return check;
	}
	
	public List<IPiece> getStack(Position p) {
		return this.pieces[p.rank() - 1][p.file() - 1];
	}
	
	public List<IPiece> getPlayersTopPieces(Player player) {
		List<IPiece> topPieces = new ArrayList<>();
		
		for (int i = 0; i < this.pieces.length; i++) {
			for (int j = 0; j < this.pieces[i].length; j++) {
				int tier = this.pieces[i][j].size();
				
				if (tier > 0 && this.pieces[i][j].get(tier - 1).getPlayer().is(player)) {
					topPieces.add(this.pieces[i][j].get(tier - 1));
				}
			}
		}
		return topPieces;
	}
	
	public void addPiece(IPiece piece, Position to) {
		assert getStack(to).size() + 1 == to.tier();
		
		piece.drop(to);
		getStack(to).add(piece);
	}
	
	public void movePiece(Position from, Position to) {
		assert
			getStack(from).size() == from.tier() &&
			getStack(to).size() + 1 == to.tier();
		
		IPiece p = getStack(from).remove(from.tier() - 1);
		p.move(to);
		getStack(to).add(p);
	}
	
	public void removePiece(Position from) {
		assert getStack(from).size() == from.tier();

		IPiece p = getStack(from).remove(from.tier() - 1);
		p.kill();
	}
	
	public Board clone() {
		Board b = new Board();
		b.players = new Player[this.players.length];
		for (int i = 0; i < b.players.length; i++) {
			b.players[i] = this.players[i].clone(b);
		}
		
		for (int i = 0; i < this.pieces.length; i++) {
			for (int j = 0; j < this.pieces[i].length; j++) {
				for (int k = 0; k < this.pieces[i][j].size(); k++) {
					b.pieces[i][j].add(this.pieces[i][j].get(k).clone(b));
				}
			}
		}
		
		return b;
	}
	
	public void print(Position p, List<Position> highlight) {
		System.out.printf("  ");
		for (int i = 0; i < MAX_FILES; i++) {
			System.out.printf("  %2d", i+1);
		}
		System.out.println();
		
		for (int i = MAX_RANKS - 1; i >= 0; i--) {
			System.out.print("   +");
			for (int j = 0; j < pieces[i].length; j++) {
				boolean has = false, pick = p != null && p.rank() == i + 1 && p.file() == j + 1;
				if (highlight != null) {
					for (Position x : highlight) {
						if (x.rank() == i+1 && x.file() == j+1) {
							has = true;
							break;
						}
					}
				}
				System.out.print(
						(pick && p.tier() == 1 ? "O" : "-") +
						(has ? "v" : (pick && p.tier() == 2 ? "O" : "-")) +
						(pick && p.tier() == 3 ? "O" : "-") +
						"+");
			}
			
			if (2 * (MAX_RANKS - 1 - i) < Util.LEGEND.length) {
				System.out.printf("   %s%n", Util.LEGEND[2*(MAX_RANKS - 1 - i)]);
			} else {
				System.out.println();
			}
			
			System.out.printf("%2d |", i + 1);
			for (int j = 0; j < pieces[i].length; j++) {
				for (int k = 0; k < 3; k++) {
					if (k < pieces[i][j].size()) {
						String s = pieces[i][j].get(k).toString();
						System.out.print(pieces[i][j].get(k).getPlayer().isReversed() ? s.toLowerCase() : s.toUpperCase());
					} else {
						System.out.print(" ");
					}
				}
				System.out.print("|");
			}
			
			if (2 * (MAX_RANKS - 1 - i) + 1 < Util.LEGEND.length) {
				System.out.printf("   %s%n", Util.LEGEND[2*(MAX_RANKS - 1 - i) + 1]);
			} else {
				System.out.println();
			}
		}

		System.out.print("   +");
		for (int i = 0; i < MAX_FILES; i++) {
			System.out.print("---+");
		}
		System.out.println();
	}
	
}