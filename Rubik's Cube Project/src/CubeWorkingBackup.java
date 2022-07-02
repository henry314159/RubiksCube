import java.util.HashMap;
import java.util.Map;

public class CubeWorkingBackup {
	
	long down = 0L;
	long up = 0L;
	long left = 0L;
	long right = 0L;
	long front = 0L;
	long back = 0L;
	
	Long[] cubeNums = {
		down,
		up,
		left,
		right,
		front,
		back
	};
	
	String[] cube = {
			"YYYYYYYY", // down
			"WWWWWWWW", // up
			"OOOOOOOO", // left
			"RRRRRRRR", // right
			"GGGGGGGG", // front
			"BBBBBBBB"  // back
	};
	
	Map<Character, String> colourToCode = new HashMap<Character, String>();
	
	public CubeWorkingBackup() {
		this.colourToCode.put('Y', "00000000");
		this.colourToCode.put('W', "00000001");
		this.colourToCode.put('O', "00000010");
		this.colourToCode.put('R', "00000011");
		this.colourToCode.put('G', "00000100");
		this.colourToCode.put('B', "00000101");
		
		this.down = faceToBitboard(cube[0]);
		this.up = faceToBitboard(cube[1]);
		this.left = faceToBitboard(cube[2]);
		this.right = faceToBitboard(cube[3]);
		this.front = faceToBitboard(cube[4]);
		this.back = faceToBitboard(cube[5]);
	}
	
	public static void main(String[] args) {
		CubeWorkingBackup c = new CubeWorkingBackup();
		
		c.R(false);
		c.U(false);
		c.R(true);
		c.U(true);
		
		c.printFace(c.down);
		System.out.println();
		c.printFace(c.up);
		System.out.println();
		c.printFace(c.left);
		System.out.println();
		c.printFace(c.right);
		System.out.println();
		c.printFace(c.front);
		System.out.println();
		c.printFace(c.back);
		System.out.println();
	}
	
	public void printFace(Long face) {
		String out = bitboardToFace(face);
		System.out.println(out.charAt(7) + " " + out.charAt(6) + " " + out.charAt(5));
		System.out.println(out.charAt(0) + "   " + out.charAt(4));
		System.out.println(out.charAt(1) + " " + out.charAt(2) + " " + out.charAt(3));
	}
	
	public void printBytes(Long face) {
		for (int i = 0; i < 8; i++) {
			System.out.println(getByteGivenPositionInLong(face, i)>>i*8);
		}
	}
	
	public Long faceToBitboard(String face) {
		String binary = "";
		for (int i = 0; i < 8; i++) {
			binary += colourToCode.get(face.charAt(i));
			}
		return Long.parseLong(binary, 2);
	}
	
	public String bitboardToFace(Long bitboard) {
		String face = "";
		for (int i = 7; i > -1; i--) {
			if (((bitboard>>i*8)&1)==1) {
				if (((bitboard>>i*8)&11)==3)
					face += "R";
				else if (((bitboard>>i*8)&101)==5)
					face += "B";
				else
					face += "W";
			} else {
				if (((bitboard>>i*8)&10)==2)
					face += "O";
				else if (((bitboard>>i*8)&100)==4)
					face += "G";
				else
					face += "Y";
			}
		}
		return face;
	}
	
	public Long getByteGivenPositionInLong(Long l, int i) {
		return ((l<<(7-i)*8)>>(i*8+(7-i)*8))<<i*8;
	}
	
	public Long changeFirstByteIntoSecondByte(Long face1, Long face2, int i, int j) {
		Long face1byte1 = (Long) getByteGivenPositionInLong(face1, i);
		Long face2byte2 = (Long) getByteGivenPositionInLong(face2, j);
		
		face1 -= face1byte1;

		if (i > j) {
			face1 += (face2byte2<<((i-j)*8));
		} else {
			face1 += (face2byte2>>((j-i)*8));
		}
		
		return face1;
	}
	
	public Long changes(Long face1, Long face2, int[] i, int[] j) {
		for (int x = 0; x < i.length; x++) {
			face1 = changeFirstByteIntoSecondByte(face1, face2, i[x], j[x]);
		}
		return face1;
	}
	
	public void F(boolean prime) {
		Long newUp = up;
		Long newRight = right;
		Long newDown = down;
		Long newLeft = left;
		
		if (prime) {
			front = Long.rotateRight(front, 16);
			
			newUp = changes(newUp, right, new int[] {6, 5, 4}, new int[] {0, 7, 6});
			newRight = changes(newRight, down, new int[] {0, 7, 6}, new int[] {2, 1, 0});
			newDown = changes(newDown, left, new int[] {0, 1, 2}, new int[] {2, 3, 4});
			newLeft = changes(newLeft, up, new int[] {2, 3, 4}, new int[] {4, 5, 6});
		} else {
			front = Long.rotateLeft(front, 16);
			
			newUp = changes(newUp, left, new int[] {6, 5, 4}, new int[] {4, 3, 2});
			newRight = changes(newRight, up, new int[] {0, 7, 6}, new int[] {6, 5, 4});
			newDown = changes(newDown, right, new int[] {0, 1, 2}, new int[] {6, 7, 0});
			newLeft = changes(newLeft, down, new int[] {2, 3, 4}, new int[] {0, 1, 2});
		}
		
		up = newUp;
		right = newRight;
		down = newDown;
		left = newLeft;
	}
	
	public void B(boolean prime) {
		Long newRight = right;
		Long newUp = up;
		Long newDown = down;
		Long newLeft = left;
		
		if (prime) {
			back = Long.rotateRight(back, 16);
			
			newUp = changes(newUp, left, new int[] {0, 1, 2}, new int[] {6, 7, 0});
			newRight = changes(newRight, up, new int[] {2, 3, 4}, new int[] {0, 1, 2});
			newDown = changes(newDown, right, new int[] {4, 5, 6}, new int[] {2, 3, 4});
			newLeft = changes(newLeft, down, new int[] {0, 7, 6}, new int[] {6, 5, 4});
		} else {
			back = Long.rotateLeft(back, 16);
			
			newUp = changes(newUp, right, new int[] {0, 1, 2}, new int[] {2, 3, 4});
			newRight = changes(newRight, down, new int[] {2, 3, 4}, new int[] {4, 5, 6});
			newDown = changes(newDown, left, new int[] {4, 5, 6}, new int[] {6, 7, 0});
			newLeft = changes(newLeft, up, new int[] {0, 7, 6}, new int[] {2, 1, 0});
		}
		
		up = newUp;
		right = newRight;
		down = newDown;
		left = newLeft;
	}
	
	public void R(boolean prime) {
		Long newUp = up;
		Long newBack = back;
		Long newDown = down;
		Long newFront = front;
		
		if (prime) {
			right = Long.rotateRight(right, 16);
			
			newUp = changes(newUp, back, new int[] {2, 3, 4}, new int[] {6, 7, 0});
			newBack = changes(newBack, down, new int[] {6, 7, 0}, new int[] {2, 3, 4});
			newDown = changes(newDown, front, new int[] {2, 3, 4}, new int[] {2, 3, 4});
			newFront = changes(newFront, up, new int[] {2, 3, 4}, new int[] {2, 3, 4});
		} else {
			right = Long.rotateLeft(right, 16);
			
			newUp = changes(newUp, front, new int[] {2, 3, 4}, new int[] {2, 3, 4});
			newBack = changes(newBack, up, new int[] {0, 7, 6}, new int[] {4, 3, 2});
			newDown = changes(newDown, back, new int[] {2, 3, 4}, new int[] {6, 7, 0});
			newFront = changes(newFront, down, new int[] {2, 3, 4}, new int[] {2, 3, 4});
		}
		
		up = newUp;
		back = newBack;
		down = newDown;
		front = newFront;
	}
	
	public void L(boolean prime) {
		Long newUp = up;
		Long newFront = front;
		Long newDown = down;
		Long newBack = back;
		
		if (prime) {
			left = Long.rotateRight(left, 16);
			
			newUp = changes(newUp, front, new int[] {0, 7, 6}, new int[] {0, 7, 6});
			newFront = changes(newFront, down, new int[] {0, 7, 6}, new int[] {0, 7, 6});
			newDown = changes(newDown, back, new int[] {6, 7, 0}, new int[] {2, 3, 4});
			newBack = changes(newBack, up, new int[] {2, 3, 4}, new int[] {6, 7, 0});
		} else {
			left = Long.rotateLeft(left, 16);
			
			newUp = changes(newUp, back, new int[] {0, 7, 6}, new int[] {4, 3, 2});
			newFront = changes(newFront, up, new int[] {0, 7, 6}, new int[] {0, 7, 6});
			newDown = changes(newDown, front, new int[] {0, 7, 6}, new int[] {0, 7, 6});
			newBack = changes(newBack, down, new int[] {2, 3, 4}, new int[] {6, 7, 0});
		}
		
		up = newUp;
		front = newFront;
		down = newDown;
		back = newBack;
	}
	
	public void U(boolean prime) {
		Long newLeft = left;
		Long newFront = front;
		Long newRight = right;
		Long newBack = back;
		
		int[] x = new int[] {0, 1, 2};
		
		if (prime) {
			up = Long.rotateRight(up, 16);
			
			newLeft = changes(newLeft, back, x, x);
			newFront = changes(newFront, left, x, x);
			newRight = changes(newRight, front, x, x);
			newBack = changes(newBack, right, x, x);
		} else {
			up = Long.rotateLeft(up, 16);
			
			newLeft = changes(newLeft, front, x, x);
			newFront = changes(newFront, right, x, x);
			newRight = changes(newRight, back, x, x);
			newBack = changes(newBack, left, x, x);
		}
		
		left = newLeft;
		front = newFront;
		right = newRight;
		back = newBack;
	}
	
	public void D(boolean prime) {
		Long newLeft = left;
		Long newFront = front;
		Long newRight = right;
		Long newBack = back;
		
		if (prime) {
			down = Long.rotateRight(down, 16);
			
			newLeft = changes(newLeft, front, new int[] {4, 5, 6}, new int[] {4, 5, 6});
			newFront = changes(newFront, right, new int[] {4, 5, 6}, new int[] {4, 5, 6});
			newRight = changes(newRight, back, new int[] {4, 5, 6}, new int[] {4, 5, 6});
			newBack = changes(newBack, left, new int[] {4, 5, 6}, new int[] {4, 5, 6});
		} else {
			down = Long.rotateLeft(down, 16);
			
			newLeft = changes(newLeft, back, new int[] {4, 5, 6}, new int[] {4, 5, 6});
			newFront = changes(newFront, left, new int[] {4, 5, 6}, new int[] {4, 5, 6});
			newRight = changes(newRight, front, new int[] {4, 5, 6}, new int[] {4, 5, 6});
			newBack = changes(newBack, right, new int[] {4, 5, 6}, new int[] {4, 5, 6});
		}
		
		left = newLeft;
		front = newFront;
		right = newRight;
		back = newBack;
	}

}