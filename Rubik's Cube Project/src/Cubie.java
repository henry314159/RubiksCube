
public class Cubie {
	// A custom data structure, not unlike Tuple2, however,
	// Cubie is required because using general case custom data structures
	// caused implementation issues to do with Java's tolerance for generalisation.
	
	private byte position;
	private byte orientation;
	
	public Cubie(byte position, byte orientation) {
		this.position = position;
		this.orientation = orientation;
	}
	
	public byte getPos() {
		return position;
	}
	
	public byte getOrient() {
		return orientation;
	}
	
	public void setPos(byte position) {
		this.position = position;
	}
	
	public void setOrient(byte orientation) {
		this.orientation = orientation;
	}
	
}
