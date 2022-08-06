
public class Cubie {
	
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
