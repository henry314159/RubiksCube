public class Cube {
	
	long up = 72340172838076673L;
	long down = 0L;
	long right = 217020518514230019L;
	long left = 144680345676153346L;
	long front = 289360691352306692L;
	long back = 361700864190383365L;
	
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