import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import pieces.IPiece;
import pieces.Marshal;
import pieces.Pawn;
import things.Board;
import things.Player;
import things.Position;

public class Gungi {
	
	static int TURN_P1 = 0, TURN_P2 = 1;
	
	static int turn = TURN_P1;
	static Scanner in = new Scanner(System.in);
	static long gameId = System.currentTimeMillis();
	static Random rand = new Random();
	
	public static void main(String[] args) {
		Board board = new Board();
		
		board.print(null, null);
		
		int dropTurns = 0;
		boolean [] pass = new boolean[] {false, true};
		int [] inCheck = new int[2];
		
		while (dropTurns < 26 && !(pass[0] && pass[1] && !(turn == 0 && inCheck[0] > 0))) {
			write(board.getPlayer(turn).toString() + "'s turn.");
			boolean drop = true;
			
			if (board.getPlayer(turn).getHand().size() <= 20 && !(turn == 0 && inCheck[0] > 0 && pass[1])) {
				int choice = promptChoice("Drop Piece or pass?", new String[] {"Drop","Pass"});
				
				if (choice == 1) {
					write("You chose to pass.");
					drop = false;
					pass[turn] = true;
				}
			}
			
			if (drop) {
				doDrop(board, board.getPlayer(turn), new int[2], true, dropTurns == 0);
				pass[turn] = false;
			}
			
			nextTurn(board);
			
			if (turn == 0) {
				dropTurns++;
			}
			
			inCheck = board.getCheckStatus(false);
		}
		
		write("Let's begin.");
		board.print(null, null);
		inCheck = board.getCheckStatus(true);
		
		turn = TURN_P2;
		while (inCheck[0] != 2 && inCheck[1] != 2) {
			write(board.getPlayer(turn).toString() + "'s turn." + (inCheck[turn] == 1 ? " They are in CHECK." : ""));
			
			int turnOption = board.getPlayer(turn).getHand().size() > 0 ? promptChoice("Drop piece or move?", new String[] {"Drop", "Move"}) : 1;
			
			if (turnOption == 1) {
				doMove(board, board.getPlayer(turn), inCheck);
			} else {
				doDrop(board, board.getPlayer(turn), inCheck, false, false);
			}
			
			nextTurn(board);
		}
		
		write(board.getPlayer(turn) + " HAS BEEN CHECKMATED! " + board.getPlayer(turn ^ 1) + " WINS!");
	}
	
	public static void doMove(Board b, Player p, int[] check) {
		List<IPiece> topPieces = b.getPlayersTopPieces(p);

		List<Position> locations = new ArrayList<>();
		for (IPiece pc : topPieces) {
			locations.add(pc.getPosition());
		}
		
		b.print(null, locations);
		
		boolean goodPosition = false;
		Position from = null;
		List<Position> moves = null, attacks = null;
		do {
			from = promptPosition("Pick a position to move from.");
			
			for (IPiece pc : topPieces) {
				moves = pc.getValidMoves();
				attacks = pc.getValidAttacks();
				
				if (pc.getPosition().equals(from) && (moves.size() > 0 || attacks.size() > 0)) {
					goodPosition = true;
					break;
				}
			}
		} while (!goodPosition);
		
		List<Position> allOptions = new ArrayList<>();
		allOptions.addAll(moves);
		allOptions.addAll(attacks);
		b.print(from, allOptions);
		
				//for (Position x : moves) write("M " + x.toString());
				//for (Position x : attacks) write("A " + x.toString());
		
		Position to = null;
		do {
			to = promptPosition("Pick a position to move to.");
		} while ((!moves.contains(to) && !attacks.contains(to)) || !validateCheckMove(b, null, from, to, check));
		
		if (moves.contains(to)) {
			b.movePiece(from, to);
			write("The piece was moved.");
		} else {
			b.removePiece(to);
			b.movePiece(from, to);
			write("The piece attacked!");
		}
	}
	
	public static void doDrop(Board b, Player p, int[] check, boolean placementPhase, boolean forceMarshall) {
		int pidx = -1;
		if (forceMarshall) {
			for (int i = 0; i < p.getHand().size(); i++) {
				if (p.getHand().get(i) instanceof Marshal) {
					pidx = i;
					break;
				}
			}
		} else {
			pidx = promptChoice("Choose a piece to drop.",p.getHand());
		}
		IPiece piece = p.getHand().get(pidx);
		
		List<Position> list = piece.getValidDrops(placementPhase);
		
		b.print(null, list);
		
				//for (Position x : list) write(x.toString());
		
		Position pos = null;
		do {
			pos = promptPosition("Where to drop this " + piece.toString() + " piece?");
		} while (!list.contains(pos) || (!placementPhase && !validateCheckMove(b, piece, null, pos, check)));
		
		p.drop(piece, pos);
		
		write("Placed the piece.");
	}
	
	// true = move is valid, check array is updated (1 = check, 2 = checkmate)
	// false = move is invalid, nothing is changed
	public static boolean validateCheckMove(Board b, IPiece p, Position from, Position to, int[] check) {
		// we can assume we aren't in checkmate first
		Board tempBoard = b.clone();
		IPiece tempPiece = p == null ? null : p.clone(tempBoard);
		
		if (from == null) {
			tempBoard.addPiece(tempPiece, to);
		} else {
			if (tempBoard.getStack(to).size() == to.tier()) {
				tempBoard.removePiece(to);
			}
			tempBoard.movePiece(from, to);
		}
		
		// if in check, must be not in check after move, otherwise invalidate
		// then, check for check/mate for other player and update check array
		int[] newCheck = tempBoard.getCheckStatus(true);
		
		if (newCheck[turn] > 0) {
			return false;
		}
		
		// if in check/mate, but this was a dropped pawn, invalidate and revert check array
		if (newCheck[turn ^ 1] != 0 && tempPiece instanceof Pawn) {
			List<Position> pawnAttacks = tempPiece.getValidAttacks();
			
			for (Position w : pawnAttacks) {
				List<IPiece> stack = tempBoard.getStack(w);
				IPiece top = stack.get(stack.size() - 1);
				
				if (top instanceof Marshal && top.getPlayer().is(tempPiece.getPlayer())) {
					return false;
				}
			}
			return false;
		}
		
		check[0] = newCheck[0];
		check[1] = newCheck[1];
		return true;
	}
	
	public static void nextTurn(Board b) {
		turn ^= 1;
		b.print(null, null);
	}
	
	public static void write(String s) {
		System.out.println(s);
	}
	
	public static String read() {
		String line = in.nextLine();
		try (FileWriter fw = new FileWriter(new File("res/game_" + gameId + ".txt"), true)){
			fw.write(String.format("%s%n", line));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return line;
	}
	
	public static Position promptPosition(String message) {
		write(message);
		int r = -1, f = -1, t = -1;
		
		do {
			System.out.print(">> ");
			String line = read();
			
			String[] opts = line.trim().split("[-:,\\s]+");
			try {
				r = Integer.parseInt(opts[0]);
				f = Integer.parseInt(opts[1]);
				t = Integer.parseInt(opts[2]);
			} catch (Exception e) {
				r = -1;
				
				if (line.equals(".")) {
					r = 1 + rand.nextInt(3) + (turn == 1 ? 0 : 6);
					f = 1 + rand.nextInt(Board.MAX_FILES);
					t = 1 + rand.nextInt(Board.MAX_TIER);
				}
				
				write("Try again.");
			}
		} while (!(r > 0 && r <= Board.MAX_RANKS && f > 0 && f <= Board.MAX_FILES && t > 0 && t <= Board.MAX_TIER));
		
		return new Position(r, f, t);
	}
	
	@SuppressWarnings("rawtypes")
	public static int promptChoice(String message, List options) {
		return promptChoice(message, options.toArray());
	}
	
	public static int promptChoice(String message, Object[] options) {
		write(message);
		
		if (options.length > 10) {
			for (int i = 0; i < 1 + options.length / 4; i++) {
				String a = (4*i+0 < options.length) ? String.format(" [%02d] %-6s", 4*i+0, options[4*i+0]) : "";
				String b = (4*i+1 < options.length) ? String.format(" [%02d] %-6s", 4*i+1, options[4*i+1]) : "";
				String c = (4*i+2 < options.length) ? String.format(" [%02d] %-6s", 4*i+2, options[4*i+2]) : "";
				String d = (4*i+3 < options.length) ? String.format(" [%02d] %-6s", 4*i+3, options[4*i+3]) : "";
				write(String.format("%12s%12s%12s%12s", a, b, c, d));
			}
		} else {
			for (int i = 0; i < options.length; i++) {
				write(String.format(" [%02d] %s", i, options[i]));
			}
		}
		
		int result = -1;
		
		do {
			System.out.print(">> ");
			String line = read();
			
			try {
				result = Integer.parseInt(line);
			} catch (Exception e) {
				result = -1;
				
				if (line.equals(".")) {
					result = rand.nextInt(options.length);
				}
				
				write("Try again.");
			}
		} while (result < 0 || result >= options.length);
		
		return result;
	}
	
}