
public class Goal {
	int state;
	String[][] allowedMoves;
	
	byte[][] corner180Perms = {
			{0, 1, 1, 0, 2, 2, 2, 2, 3, 3, 3, 3, 1, 0, 0, 1},
			{1, 0, 0, 1, 2, 2, 2, 2, 3, 3, 3, 3, 0, 1, 1, 0},
			{1, 1, 0, 0, 2, 3, 3, 2, 2, 3, 3, 2, 1, 1, 0, 0},
			{0, 0, 1, 1, 3, 2, 2, 3, 3, 2, 2, 3, 0, 0, 1, 1},
			{1, 1, 1, 1, 3, 3, 2, 2, 2, 2, 3, 3, 0, 0, 0, 0},
			{1, 1, 1, 1, 2, 2, 3, 3, 3, 3, 2, 2, 0, 0, 0, 0},
			{1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 0, 0, 0, 0},
			{0, 0, 0, 0, 2, 2, 2, 2, 3, 3, 3, 3, 1, 1, 1, 1},
			{0, 1, 1, 0, 2, 3, 3, 2, 2, 3, 3, 2, 1, 0, 0, 1},
			{0, 1, 1, 0, 3, 2, 2, 3, 3, 2, 2, 3, 1, 0, 0, 1},
			{1, 0, 0, 1, 3, 3, 2, 2, 2, 2, 3, 3, 1, 0, 0, 1},
			{0, 1, 1, 0, 2, 2, 3, 3, 3, 3, 2, 2, 0, 1, 1, 0},
			{1, 0, 0, 1, 2, 3, 3, 2, 2, 3, 3, 2, 0, 1, 1, 0},
			{1, 0, 0, 1, 3, 2, 2, 3, 3, 2, 2, 3, 0, 1, 1, 0},
			{0, 1, 1, 0, 3, 3, 2, 2, 2, 2, 3, 3, 0, 1, 1, 0},
			{1, 0, 0, 1, 2, 2, 3, 3, 3, 3, 2, 2, 1, 0, 0, 1},
			{1, 1, 0, 0, 3, 2, 2, 3, 2, 3, 3, 2, 1, 1, 0, 0},
			{1, 1, 0, 0, 2, 3, 3, 2, 3, 2, 2, 3, 1, 1, 0, 0},
			{0, 0, 0, 0, 3, 3, 3, 3, 2, 2, 2, 2, 1, 1, 1, 1},
			{0, 0, 1, 1, 2, 3, 3, 2, 2, 3, 3, 2, 1, 1, 0, 0},
			{1, 1, 0, 0, 2, 3, 3, 2, 2, 3, 3, 2, 0, 0, 1, 1},
			{0, 0, 1, 1, 2, 3, 3, 2, 3, 2, 2, 3, 0, 0, 1, 1},
			{0, 0, 1, 1, 3, 2, 2, 3, 2, 3, 3, 2, 0, 0, 1, 1},
			{1, 1, 0, 0, 3, 2, 2, 3, 3, 2, 2, 3, 0, 0, 1, 1},
			{0, 0, 1, 1, 3, 2, 2, 3, 3, 2, 2, 3, 1, 1, 0, 0},
			{0, 1, 1, 0, 2, 2, 3, 3, 2, 2, 3, 3, 1, 0, 0, 1},
			{1, 0, 0, 1, 3, 3, 2, 2, 3, 3, 2, 2, 0, 1, 1, 0},
			{1, 1, 0, 0, 3, 3, 2, 2, 2, 2, 3, 3, 1, 1, 0, 0},
			{0, 0, 1, 1, 3, 3, 2, 2, 2, 2, 3, 3, 0, 0, 1, 1},
			{1, 1, 1, 1, 3, 3, 3, 3, 2, 2, 2, 2, 0, 0, 0, 0},
			{0, 1, 1, 0, 3, 3, 2, 2, 3, 3, 2, 2, 1, 0, 0, 1},
			{1, 0, 0, 1, 2, 2, 3, 3, 2, 2, 3, 3, 0, 1, 1, 0},
			{1, 1, 0, 0, 2, 2, 3, 3, 3, 3, 2, 2, 1, 1, 0, 0},
			{0, 0, 1, 1, 2, 2, 3, 3, 3, 3, 2, 2, 0, 0, 1, 1},
			{0, 0, 1, 1, 2, 3, 3, 2, 2, 3, 3, 2, 0, 0, 1, 1},
			{1, 1, 0, 0, 3, 2, 2, 3, 3, 2, 2, 3, 1, 1, 0, 0},
			{0, 0, 0, 0, 3, 3, 2, 2, 2, 2, 3, 3, 1, 1, 1, 1},
			{0, 0, 0, 0, 2, 2, 3, 3, 3, 3, 2, 2, 1, 1, 1, 1},
			{1, 1, 1, 1, 3, 2, 2, 3, 2, 3, 3, 2, 0, 0, 0, 0},
			{0, 0, 0, 0, 2, 3, 3, 2, 3, 2, 2, 3, 1, 1, 1, 1},
			{0, 1, 1, 0, 3, 3, 3, 3, 2, 2, 2, 2, 1, 0, 0, 1},
			{1, 0, 0, 1, 2, 3, 3, 2, 2, 3, 3, 2, 1, 0, 0, 1},
			{0, 1, 1, 0, 2, 3, 3, 2, 2, 3, 3, 2, 0, 1, 1, 0},
			{1, 1, 1, 1, 2, 3, 3, 2, 3, 2, 2, 3, 0, 0, 0, 0},
			{0, 0, 0, 0, 3, 2, 2, 3, 2, 3, 3, 2, 1, 1, 1, 1},
			{1, 0, 0, 1, 3, 2, 2, 3, 3, 2, 2, 3, 1, 0, 0, 1},
			{0, 1, 1, 0, 3, 2, 2, 3, 3, 2, 2, 3, 0, 1, 1, 0},
			{1, 0, 0, 1, 2, 2, 3, 3, 2, 2, 3, 3, 1, 0, 0, 1},
			{1, 0, 0, 1, 3, 3, 2, 2, 3, 3, 2, 2, 1, 0, 0, 1},
			{1, 0, 1, 0, 3, 3, 2, 2, 2, 2, 3, 3, 0, 1, 0, 1},
			{0, 1, 0, 1, 3, 3, 2, 2, 2, 2, 3, 3, 1, 0, 1, 0},
			{1, 0, 0, 1, 3, 3, 3, 3, 2, 2, 2, 2, 0, 1, 1, 0},
			{0, 1, 1, 0, 3, 3, 2, 2, 3, 3, 2, 2, 0, 1, 1, 0},
			{0, 1, 1, 0, 2, 2, 3, 3, 2, 2, 3, 3, 0, 1, 1, 0},
			{0, 1, 0, 1, 2, 2, 3, 3, 3, 3, 2, 2, 1, 0, 1, 0},
			{1, 0, 1, 0, 2, 2, 3, 3, 3, 3, 2, 2, 0, 1, 0, 1},
			{0, 0, 1, 1, 2, 3, 2, 3, 3, 2, 3, 2, 1, 1, 0, 0},
			{1, 1, 0, 0, 3, 2, 3, 2, 2, 3, 2, 3, 0, 0, 1, 1},
			{0, 0, 1, 1, 3, 2, 3, 2, 2, 3, 2, 3, 1, 1, 0, 0},
			{1, 1, 0, 0, 2, 3, 2, 3, 3, 2, 3, 2, 0, 0, 1, 1},
			{1, 0, 1, 0, 3, 2, 2, 3, 2, 3, 3, 2, 0, 1, 0, 1},
			{0, 1, 0, 1, 2, 3, 3, 2, 3, 2, 2, 3, 1, 0, 1, 0},
			{0, 0, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 1, 1, 0, 0},
			{0, 0, 1, 1, 3, 3, 3, 3, 2, 2, 2, 2, 1, 1, 0, 0},
			{0, 1, 0, 1, 3, 2, 2, 3, 2, 3, 3, 2, 1, 0, 1, 0},
			{1, 0, 1, 0, 2, 3, 3, 2, 3, 2, 2, 3, 0, 1, 0, 1},
			{1, 1, 0, 0, 2, 2, 2, 2, 3, 3, 3, 3, 0, 0, 1, 1},
			{1, 1, 0, 0, 3, 3, 3, 3, 2, 2, 2, 2, 0, 0, 1, 1},
			{0, 1, 1, 0, 2, 3, 2, 3, 3, 2, 3, 2, 1, 0, 0, 1},
			{0, 1, 1, 0, 3, 2, 3, 2, 2, 3, 2, 3, 1, 0, 0, 1},
			{1, 0, 0, 1, 3, 2, 3, 2, 2, 3, 2, 3, 0, 1, 1, 0},
			{1, 0, 0, 1, 2, 3, 2, 3, 3, 2, 3, 2, 0, 1, 1, 0},
			{1, 1, 0, 0, 2, 2, 3, 3, 2, 2, 3, 3, 1, 1, 0, 0},
			{1, 1, 0, 0, 3, 3, 2, 2, 3, 3, 2, 2, 1, 1, 0, 0},
			{0, 0, 1, 1, 2, 2, 3, 3, 2, 2, 3, 3, 0, 0, 1, 1},
			{0, 0, 1, 1, 3, 3, 2, 2, 3, 3, 2, 2, 0, 0, 1, 1},
			{1, 1, 1, 1, 2, 3, 2, 3, 3, 2, 3, 2, 0, 0, 0, 0},
			{1, 1, 1, 1, 3, 2, 3, 2, 2, 3, 2, 3, 0, 0, 0, 0},
			{0, 0, 0, 0, 3, 2, 3, 2, 2, 3, 2, 3, 1, 1, 1, 1},
			{0, 0, 0, 0, 2, 3, 2, 3, 3, 2, 3, 2, 1, 1, 1, 1},
			{1, 0, 0, 1, 3, 2, 2, 3, 2, 3, 3, 2, 1, 0, 0, 1},
			{1, 0, 0, 1, 2, 3, 3, 2, 3, 2, 2, 3, 1, 0, 0, 1},
			{1, 0, 1, 0, 2, 2, 2, 2, 3, 3, 3, 3, 0, 1, 0, 1},
			{0, 1, 0, 1, 3, 3, 3, 3, 2, 2, 2, 2, 1, 0, 1, 0},
			{0, 1, 1, 0, 3, 2, 2, 3, 2, 3, 3, 2, 0, 1, 1, 0},
			{0, 1, 1, 0, 2, 3, 3, 2, 3, 2, 2, 3, 0, 1, 1, 0},
			{0, 1, 0, 1, 2, 2, 2, 2, 3, 3, 3, 3, 1, 0, 1, 0},
			{1, 0, 1, 0, 3, 3, 3, 3, 2, 2, 2, 2, 0, 1, 0, 1},
			{1, 0, 1, 0, 2, 3, 2, 3, 3, 2, 3, 2, 0, 1, 0, 1},
			{0, 1, 0, 1, 3, 2, 3, 2, 2, 3, 2, 3, 1, 0, 1, 0},
			{1, 0, 1, 0, 3, 2, 3, 2, 2, 3, 2, 3, 0, 1, 0, 1},
			{0, 1, 0, 1, 2, 3, 2, 3, 3, 2, 3, 2, 1, 0, 1, 0},
			{0, 0, 1, 1, 2, 2, 3, 3, 2, 2, 3, 3, 1, 1, 0, 0},
			{1, 1, 0, 0, 3, 3, 2, 2, 3, 3, 2, 2, 0, 0, 1, 1},
			{1, 1, 0, 0, 2, 2, 3, 3, 2, 2, 3, 3, 0, 0, 1, 1},
			{0, 0, 1, 1, 3, 3, 2, 2, 3, 3, 2, 2, 1, 1, 0, 0},
	};
	
	public Goal(int state) {
		this.state = state;
		allowedMoves = new String[][] {
			{"L", "R", "F", "B", "U", "D", "L'", "R'", "F'", "B'", "U'", "D'"},
			{"L", "R", "F", "B", "L'", "R'", "F'", "B'", "U2", "D2", "L2", "R2", "F2", "B2"},
			{"L", "R", "L'", "R'", "F2", "B2", "U2", "D2", "L2", "R2"},
			{"L", "R", "L'", "R'", "F2", "B2", "U2", "D2", "L2", "R2"},
			{"L2", "R2", "F2", "B2", "U2", "D2"}
		};
		
	}
		
	public Boolean isSatisfied(Cube cube) {
		switch (state) {
		case 1:
			return GoalG0_G1(cube);
		case 2:
			return GoalG1_G2(cube);
		case 3:
			return GoalG2_G3a(cube);
		case 4:
			return GoalG2_G3b(cube);
		case 5:
			return GoalG3_G4(cube);
		}
		return false;
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
	
	private Boolean GoalG0_G1(Cube cube) {
		byte UB = (byte) (cube.getByteGivenPositionInLong(cube.up, 1)>>(1*8));
		byte UL = (byte) (cube.getByteGivenPositionInLong(cube.up, 7)>>7*8);
		byte UR = (byte) (cube.getByteGivenPositionInLong(cube.up, 3)>>(3*8));
		byte UF = (byte) (cube.getByteGivenPositionInLong(cube.up, 5)>>(5*8));
		
		byte LU = (byte) (cube.getByteGivenPositionInLong(cube.left, 1)>>(1*8));
		byte LB = (byte) (cube.getByteGivenPositionInLong(cube.left, 7)>>7*8);
		byte LF = (byte) (cube.getByteGivenPositionInLong(cube.left, 3)>>(3*8));
		byte LD = (byte) (cube.getByteGivenPositionInLong(cube.left, 5)>>(5*8));
		
		byte FU = (byte) (cube.getByteGivenPositionInLong(cube.front, 1)>>(1*8));
		byte FL = (byte) (cube.getByteGivenPositionInLong(cube.front, 7)>>7*8);
		byte FR = (byte) (cube.getByteGivenPositionInLong(cube.front, 3)>>(3*8));
		byte FD = (byte) (cube.getByteGivenPositionInLong(cube.front, 5)>>(5*8));
		
		byte RU = (byte) (cube.getByteGivenPositionInLong(cube.right, 1)>>(1*8));
		byte RF = (byte) (cube.getByteGivenPositionInLong(cube.right, 7)>>7*8);
		byte RB = (byte) (cube.getByteGivenPositionInLong(cube.right, 3)>>(3*8));
		byte RD = (byte) (cube.getByteGivenPositionInLong(cube.right, 5)>>(5*8));
		
		byte BU = (byte) (cube.getByteGivenPositionInLong(cube.back, 1)>>(1*8));
		byte BL = (byte) (cube.getByteGivenPositionInLong(cube.back, 7)>>7*8);
		byte BR = (byte) (cube.getByteGivenPositionInLong(cube.back, 3)>>(3*8));
		byte BD = (byte) (cube.getByteGivenPositionInLong(cube.back, 5)>>(5*8));
		
		byte DF = (byte) (cube.getByteGivenPositionInLong(cube.down, 1)>>(1*8));
		byte DL = (byte) (cube.getByteGivenPositionInLong(cube.down, 7)>>7*8);
		byte DR = (byte) (cube.getByteGivenPositionInLong(cube.down, 3)>>(3*8));
		byte DB = (byte) (cube.getByteGivenPositionInLong(cube.down, 5)>>(5*8));
		
		return (UF == 3  || UF == 2 || FU == 5 || FU == 4) &&
			   (UB == 3  || UB == 2 || BU == 5 || BU == 4) &&
	           (DF == 3  || DF == 2 || FD == 5 || FD == 4) &&
	           (DB == 3  || DB == 2 || BD == 5 || BD == 4) &&
	           (LU == 3  || LU == 2 || UL == 5 || UL == 4) &&
	           (LD == 3  || LD == 2 || DL == 5 || DL == 4) &&
	           (RU == 3  || RU == 2 || UR == 5 || UR == 4) &&
	           (RD == 3  || RD == 2 || DR == 5 || DR == 4) &&
	           (LF == 3  || LF == 2 || FL == 5 || FL == 4) &&
	           (LB == 3  || LB == 2 || BL == 5 || BL == 4) &&
	           (RF == 3  || RF == 2 || FR == 5 || FR == 4) &&
	           (RB == 3  || RB == 2 || BR == 5 || BR == 4);
	}
	
	private Boolean GoalG1_G2(Cube cube) {
		byte LUB = (byte) (cube.getByteGivenPositionInLong(cube.left, 0)>>(0*8));
		byte LUF = (byte) (cube.getByteGivenPositionInLong(cube.left, 2)>>(2*8));
		byte LDB = (byte) (cube.getByteGivenPositionInLong(cube.left, 6)>>(6*8));
		byte LDF = (byte) (cube.getByteGivenPositionInLong(cube.left, 4)>>(4*8));
		
		byte RUB = (byte) (cube.getByteGivenPositionInLong(cube.right, 2)>>(2*8));
		byte RUF = (byte) (cube.getByteGivenPositionInLong(cube.right, 0)>>(0*8));
		byte RDB = (byte) (cube.getByteGivenPositionInLong(cube.right, 4)>>(4*8));
		byte RDF = (byte) (cube.getByteGivenPositionInLong(cube.right, 6)>>(6*8));
		
		byte UF = (byte) (cube.getByteGivenPositionInLong(cube.up, 5)>>(5*8));
		byte FU = (byte) (cube.getByteGivenPositionInLong(cube.front, 1)>>(1*8));
		byte UB = (byte) (cube.getByteGivenPositionInLong(cube.up, 1)>>(1*8));
		byte BU = (byte) (cube.getByteGivenPositionInLong(cube.back, 1)>>(1*8));
		byte DF = (byte) (cube.getByteGivenPositionInLong(cube.down, 1)>>(1*8));
		byte FD = (byte) (cube.getByteGivenPositionInLong(cube.front, 5)>>(5*8));
		byte DB = (byte) (cube.getByteGivenPositionInLong(cube.down, 5)>>(5*8));
		byte BD = (byte) (cube.getByteGivenPositionInLong(cube.back, 5)>>(5*8));

		return (LUB == 2 || LUB == 3) &&
			   (LUF == 2 || LUF == 3) &&
			   (LDB == 2 || LDB == 3) &&
			   (LDF == 2 || LDF == 3) &&
			   (RUB == 2 || RUB == 3) &&
			   (RUF == 2 || RUF == 3) &&
			   (RDB == 2 || RDB == 3) &&
			   (RDF == 2 || RDF == 3) &&

			   (UF == 1   || UF == 0)  &&
			   (FU == 4 || FU == 5)  &&

			   (UB == 1   || UB == 0)  &&
			   (BU == 4 || BU == 5)  &&

			   (DF == 1   || DF == 0)  &&
			   (FD == 4 || FD == 5)  &&

			   (DB == 1   || DB == 0)  &&
			   (BD == 4 || BD == 5);
	}
	
	private Boolean GoalG2_G3a(Cube cube) {
		return (count(corner180Perms, cubeToByteArray(cube)) == 1);
	}
	
	private Boolean GoalG2_G3b(Cube cube) {
		byte UL = (byte) (cube.getByteGivenPositionInLong(cube.up, 7)>>7*8);
		byte UR = (byte) (cube.getByteGivenPositionInLong(cube.up, 3)>>(3*8));
		byte LU = (byte) (cube.getByteGivenPositionInLong(cube.left, 1)>>(1*8));
		byte LB = (byte) (cube.getByteGivenPositionInLong(cube.left, 7)>>7*8);
		byte LF = (byte) (cube.getByteGivenPositionInLong(cube.left, 3)>>(3*8));
		byte LD = (byte) (cube.getByteGivenPositionInLong(cube.left, 5)>>(5*8));
		byte RU = (byte) (cube.getByteGivenPositionInLong(cube.right, 1)>>(1*8));
		byte RF = (byte) (cube.getByteGivenPositionInLong(cube.right, 7)>>7*8);
		byte RB = (byte) (cube.getByteGivenPositionInLong(cube.right, 3)>>(3*8));
		byte RD = (byte) (cube.getByteGivenPositionInLong(cube.right, 5)>>(5*8));
		byte DL = (byte) (cube.getByteGivenPositionInLong(cube.down, 7)>>7*8);
		byte DR = (byte) (cube.getByteGivenPositionInLong(cube.down, 3)>>(3*8));
		byte BL = (byte) (cube.getByteGivenPositionInLong(cube.back, 7)>>7*8);
		byte BR = (byte) (cube.getByteGivenPositionInLong(cube.back, 3)>>(3*8));
		byte FL = (byte) (cube.getByteGivenPositionInLong(cube.front, 7)>>7*8);
		byte FR = (byte) (cube.getByteGivenPositionInLong(cube.front, 3)>>(3*8));
		
		return (count(corner180Perms, cubeToByteArray(cube)) == 1) &&
		      (UL == 1 || UL == 0) && (LU == 2 || LU == 3)  &&
		      (UR == 1 || UR == 0) && (RU == 2 || RU == 3)  &&
		      (DL == 1 || DL == 0) && (LD == 2 || LD == 3)  &&
		      (DR == 1 || DR == 0) && (RD == 2 || RD == 3)  &&
		      (BL == 4 || BL == 5) && (LB == 2 || LB == 3)  &&
		      (FL == 4 || FL == 5) && (LF == 2 || LF == 3)  &&
		      (BR == 4 || BR == 5) && (RB == 2 || RB == 3)  &&
		      (FR == 4 || FR == 5) && (RF == 2 || RF == 3);
	}
	
	private Boolean GoalG3_G4(Cube cube) {
		Cube solved = new Cube();
		
		return (cube.up == solved.up) &&
				(cube.down == solved.down) &&
				(cube.left == solved.left) &&
				(cube.right == solved.right) &&
				(cube.front == solved.front) &&
				(cube.back == solved.back);
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
	
}
