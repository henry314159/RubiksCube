
public class Cube {
	
	long up = 72340172838076673L;
	long down = 0L;
	long right = 217020518514230019L;
	long left = 144680345676153346L;
	long front = 289360691352306692L;
	long back = 361700864190383365L;
	
	Long[] cubeNums = {
			down,
			up,
			left,
			right,
			front,
			back
		};

	String[] cube = {
			"YYYYYYYY", // down 0
			"WWWWWWWW", // up 1
			"OOOOOOOO", // left 2
			"RRRRRRRR", // right 3
			"GGGGGGGG", // front 4
			"BBBBBBBB"  // back 5
		};
	
	public void doMove(String move) {
		switch (move) {
		case "R":
			R(false);
			break;
		case "L":
			L(false);
			break;
		case "U":
			U(false);
			break;
		case "D":
			D(false);
			break;
		case "F":
			F(false);
			break;
		case "B":
			B(false);
			break;
		case "R'":
			R(true);
			break;
		case "L'":
			L(true);
			break;
		case "U'":
			U(true);
			break;
		case "D'":
			D(true);
			break;
		case "F'":
			F(true);
			break;
		case "B'":
			B(true);
			break;
		case "R2":
			R(true);
			R(true);
			break;
		case "L2":
			L(true);
			L(true);
			break;
		case "U2":
			U(true);
			U(true);
			break;
		case "D2":
			D(true);
			D(true);
			break;
		case "F2":
			F(true);
			F(true);
			break;
		case "B2":
			B(true);
			B(true);
			break;
		}
	}
	
	public Cube doMoveReturn(String move) {
		Cube c = new Cube();
		c.up = this.up;
		c.down = this.down;
		c.right = this.right;
		c.left = this.left;
		c.front = this.front;
		c.back = this.back;
		switch (move) {
		case "R":
			c.R(false);
			break;
		case "L":
			c.L(false);
			break;
		case "U":
			c.U(false);
			break;
		case "D":
			c.D(false);
			break;
		case "F":
			c.F(false);
			break;
		case "B":
			c.B(false);
			break;
		case "R'":
			c.R(true);
			break;
		case "L'":
			c.L(true);
			break;
		case "U'":
			c.U(true);
			break;
		case "D'":
			c.D(true);
			break;
		case "F'":
			c.F(true);
			break;
		case "B'":
			c.B(true);
			break;
		case "R2":
			c.R(true);
			c.R(true);
			break;
		case "L2":
			c.L(true);
			c.L(true);
			break;
		case "U2":
			c.U(true);
			c.U(true);
			break;
		case "D2":
			c.D(true);
			c.D(true);
			break;
		case "F2":
			c.F(true);
			c.F(true);
			break;
		case "B2":
			c.B(true);
			c.B(true);
			break;
		}
		return c;
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
		Long face1byte1 = getByteGivenPositionInLong(face1, i);
		Long face2byte2 = getByteGivenPositionInLong(face2, j);
		
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
		
		int[] x = new int[] {4, 5, 6};
		
		if (prime) {
			down = Long.rotateRight(down, 16);
			
			newLeft = changes(newLeft, front, x, x);
			newFront = changes(newFront, right, x, x);
			newRight = changes(newRight, back, x, x);
			newBack = changes(newBack, left, x, x);
		} else {
			down = Long.rotateLeft(down, 16);
			
			newLeft = changes(newLeft, back, x, x);
			newFront = changes(newFront, left, x, x);
			newRight = changes(newRight, front, x, x);
			newBack = changes(newBack, right, x, x);
		}
		
		left = newLeft;
		front = newFront;
		right = newRight;
		back = newBack;
	}

}
