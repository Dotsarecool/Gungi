package things;
public class Position {
	
	final int rank, file, tier;
	
	public Position(int r, int f, int t) {
		assert
			r >= 1 && r <= Board.MAX_RANKS &&
			f >= 1 && f <= Board.MAX_FILES &&
			t >= 1 && t <= Board.MAX_TIER;
		
		this.rank = r;
		this.file = f;
		this.tier = t;
	}
	
	@Override
	public String toString() {
		return String.format("%d-%d-%d", this.rank, this.file, this.tier);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (o == this) return true;
		if (!(o instanceof Position)) return false;
		Position p = (Position)o;
		return p.rank() == this.rank && p.file() == this.file && p.tier() == this.tier;
	}
	
	public int rank() {
		return this.rank;
	}
	
	public int file() {
		return this.file;
	}
	
	public int tier() {
		return this.tier;
	}
	
	public Position shift(int forward, int right, int up, boolean invert) {
		if (invert) {
			forward = -forward;
			right = -right;
		}
		int rank = this.rank + forward;
		int file = this.file + right;
		int tier = this.tier + up;
		if (
				rank < 1 || rank > Board.MAX_RANKS ||
				file < 1 || file > Board.MAX_FILES ||
				tier < 1 || tier > Board.MAX_TIER
			) {
			return null;
		}
		return new Position(rank, file, tier);
	}
	
}