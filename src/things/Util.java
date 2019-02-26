package things;

public class Util {
	
	public static final String[] LEGEND = {
			"",
			"",
			"",
			"Ll = Lieutenant General",
			"Jj = Major General",
			"Gg = General",
			"Ff = Fortress",
			"Uu = Counsel",
			"Cc = Cannon",
			"Rr = Musketeer",
			"Ss = Samurai",
			"Kk = Knight",
			"Yy = Spy",
			"Aa = Archer",
			"Pp = Pawn",
			"Mm = Marshal",
			"",
			""
	};
	
	public static final int[][] directions = {
			{1, -1}, {1, 0}, {1, 1}, {0, -1}, {0, 1}, {-1, -1}, {-1, 0}, {-1, 1}	
	};
	
	// decrement towards zero, zero -> zero
	public static int shrink(int x) {
		if (x < 0) {
			return x + 1;
		} else if (x > 0) {
			return x - 1;
		}
		return 0;
	}
	
}