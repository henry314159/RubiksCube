import java.util.Arrays;
import java.util.LinkedList;

public class temp {
	
	byte[][] unique;
	int index;
	
	public static void main(String[] args) {
		temp t = new temp();
	}
	
	public temp() {
		Cube c = new Cube();
		String[] allowedMoves = {"L2", "R2", "F2", "B2", "U2", "D2"};
		
		LinkedList<Cube> queue = new LinkedList<Cube>();
		queue.add(c);
		
		unique = new byte[96][];
		index = 0;
		
		//find(c, allowedMoves);
		
		LinkedList<Cube> explored = new LinkedList<Cube>();
		
		explored.add(c);
		while (index != 96) {
			Cube v = queue.removeFirst();
			for (String move : allowedMoves) {
				if (index == 96)
					break;
				Cube w = v.doMoveReturn(move);
				if (!explored.contains(w)) {
					explored.add(w);
					queue.add(w);
					if (count(unique, cubeToByteArray(w)) == 0) {
						unique[index] = cubeToByteArray(w);
						index ++;
					}
				}
			}
		}
	
		for (byte[] item : unique) {
			System.out.print(Arrays.toString(item));
			if (count(unique, item) > 1)
				System.out.print(count(unique, item));
			System.out.println();
		}
	}
		
	private static byte count(byte[][] array, byte[] item) {
		byte count = 0;
		for (int i = 0; i < array.length; i++) {
			boolean equals = true;
			try {
				for (int j = 0; j < item.length; j++) {
					if (!(item[j] == array[i][j]))
						equals = false;
				}
			} catch(NullPointerException e) {
				return count;
			}
			if (equals) 
				count++;
		}
		return count;
	}
	
	private static byte[] cubeToByteArray(Cube c) {
		return new byte[] {
				(byte) (c.getByteGivenPositionInLong(c.up, 0)>>0*8),
				(byte) (c.getByteGivenPositionInLong(c.up, 2)>>2*8),
				(byte) (c.getByteGivenPositionInLong(c.up, 4)>>4*8),
				(byte) (c.getByteGivenPositionInLong(c.up, 6)>>6*8),
				
				(byte) (c.getByteGivenPositionInLong(c.left, 0)>>0*8),
				(byte) (c.getByteGivenPositionInLong(c.left, 2)>>2*8),
				(byte) (c.getByteGivenPositionInLong(c.left, 4)>>4*8),
				(byte) (c.getByteGivenPositionInLong(c.left, 6)>>6*8),
				
				(byte) (c.getByteGivenPositionInLong(c.right, 0)>>0*8),
				(byte) (c.getByteGivenPositionInLong(c.right, 2)>>2*8),
				(byte) (c.getByteGivenPositionInLong(c.right, 4)>>4*8),
				(byte) (c.getByteGivenPositionInLong(c.right, 6)>>6*8),
				
				(byte) (c.getByteGivenPositionInLong(c.down, 0)>>0*8),
				(byte) (c.getByteGivenPositionInLong(c.down, 2)>>2*8),
				(byte) (c.getByteGivenPositionInLong(c.down, 4)>>4*8),
				(byte) (c.getByteGivenPositionInLong(c.down, 6)>>6*8),
				
		};
	}
	
}
