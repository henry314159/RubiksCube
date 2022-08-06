
public class CubeIndexModel {
	
	byte ULB = 0;
	byte URB = 1;
	byte URF = 2;
	byte ULF = 3;
	byte DLF = 4;
	byte DLB = 5;
	byte DRB = 6;
	byte DRF = 7;
	
	Cubie[] corners = new Cubie[8];
	// 0   1   2   3   4   5   6   7
	// ULB URB URF ULF DLF DLB DRB DRF
	// 0 - in position, 1 - twisted clockwise once, 2 - twisted clockwise twice
	
	byte UB = 0;
	byte UR = 1;
	byte UF = 2;
	byte UL = 3;
	byte FR = 4;
	byte FL = 5;
	byte BL = 6;
	byte BR = 7;
	byte DF = 8;
	byte DL = 9;
	byte DB = 10;
	byte DR = 11;
	
	Cubie[] edges = new Cubie[12];
	// 0  1  2  3  4  5  6  7  8  9  10 11
	// UB UR UF UL FR FL BL BR DF DL DB DR
	// 0 - oriented, 1 - flipped
		
	public CubeIndexModel() {
		for (byte i = 0; i < 8; i++) {
			corners[i] = new Cubie(i, (byte) 0);
		}
		
		for (byte i = 0; i < 12; i++) {
			edges[i] = new Cubie(i, (byte) 0);
		}
	}
	
	public final void doMove(String m) {
		switch(m) {
		case "R":
			R();
			break;
		case "L":
			L();
			break;
		case "U":
			U();
			break;
		case "D":
			D();
			break;
		case "F":
			F();
			break;
		case "B":
			B();
			break;
		case "R'":
			RPrime();
			break;
		case "L'":
			LPrime();
			break;
		case "U'":
			UPrime();
			break;
		case "D'":
			DPrime();
			break;
		case "F'":
			FPrime();
			break;
		case "B'":
			BPrime();
			break;
		case "R2":
			R2();
			break;
		case "L2":
			L2();
			break;
		case "U2":
			U2();
			break;
		case "D2":
			D2();
			break;
		case "F2":
			F2();
			break;
		case "B2":
			B2();
			break;
		}
	}
	
	public final byte[] getCornerPermutation() {
		byte[] perms = new byte[8];
		for (int i = 0; i < 8; i++)
			perms[i] = corners[i].getPos();
		return perms;
	}
	
	public final byte[] getCornerOrientation() {
		byte[] orient = new byte[7];
		for (int i = 0; i < 7; i++) {
			if (corners[i].getPos() < 7)
				orient[corners[i].getPos()] = corners[i].getOrient();
		}
		return orient;
	}
	
	public final int getEdgeOrientation() {
		int orient = 0;
		int powerOfTwo = 1;
		for (int i = 0; i < 11; i++) {
			orient += edges[i].getOrient() * powerOfTwo;
			powerOfTwo *= 2;
		}
		return orient;
	}
	
	public final boolean isSolved() {
		for (byte i = 0; i < corners.length; i++) {
			if (corners[i].getPos() != i || corners[i].getOrient() != 0)
				return false;
		}
		
		for (byte i = 0; i < edges.length; i++) {
			if (edges[i].getPos() != i || edges[i].getOrient() != 0)
				return false;
		}
		
		return true;
	}
	
	private final void updateCornerOrientation(byte index, byte amount) {
		corners[index].setOrient((byte) (corners[index].getOrient() + amount));
		
		if (corners[index].getOrient() == 3) {
			corners[index].setOrient((byte) 0);
		} else if (corners[index].getOrient() == 4) {
			corners[index].setOrient((byte) 1);
		}
	}
	
	private final void updateEdgeOrientation(byte index) {
		edges[index].setOrient((byte) (edges[index].getOrient() ^ 1));
	}
	
	private static final Cubie[] swap(Cubie[] a, byte index1, byte index2) {
		Cubie temp = a[index1];
		a[index1] = a[index2];
		a[index2] = temp;
		
		return a;
	}
	
	public final void U() {
		Cubie temp = corners[ULF];
		corners[ULF] = corners[URF];
		corners[URF] = corners[URB];
		corners[URB] = corners[ULB];
		corners[ULB] = temp;
		
		temp = edges[UL];
		edges[UL] = edges[UF];
		edges[UF] = edges[UR];
		edges[UR] = edges[UB];
		edges[UB] = temp;
	}
	
	public final void UPrime() {
		Cubie temp = corners[ULB];
		corners[ULB] = corners[URB];
		corners[URB] = corners[URF];
		corners[URF] = corners[ULF];
		corners[ULF] = temp;
		
		temp = edges[UB];
		edges[UB] = edges[UR];
		edges[UR] = edges[UF];
		edges[UF] = edges[UL];
		edges[UL] = temp;
	}
	
	public final void U2() {
		corners = swap(corners, ULB, URF);
		corners = swap(corners, URB, ULF);
		
		edges = swap(edges, UB, UF);
		edges = swap(edges, UR, UL);
	}
	
	public final void u() {
		
	}
	
	public final void uPrime() {
		
	}
	
	public final void L() {
		Cubie temp = corners[DLB];
		corners[DLB] = corners[DLF];
		corners[DLF] = corners[ULF];
		corners[ULF] = corners[ULB];
		corners[ULB] = temp;
		
		temp = edges[BL];
		edges[BL] = edges[DL];
		edges[DL] = edges[FL];
		edges[FL] = edges[UL];
		edges[UL] = temp;
		
		updateCornerOrientation(DLB, (byte) 1);
		updateCornerOrientation(DLF, (byte) 2);
		updateCornerOrientation(ULF, (byte) 1);
		updateCornerOrientation(ULB, (byte) 2);
	}
	
	public final void LPrime() {
		Cubie temp = corners[DLB];
		corners[DLB] = corners[ULB];
		corners[ULB] = corners[ULF];
		corners[ULF] = corners[DLF];
		corners[DLF] = temp;
		
		temp = edges[BL];
		edges[BL] = edges[UL];
		edges[UL] = edges[FL];
		edges[FL] = edges[DL];
		edges[DL] = temp;
		
		updateCornerOrientation(DLB, (byte) 1);
		updateCornerOrientation(DLF, (byte) 2);
		updateCornerOrientation(ULF, (byte) 1);
		updateCornerOrientation(ULB, (byte) 2);
	}
	
	public final void L2() {
		corners = swap(corners, DLB, ULF);
		corners = swap(corners, ULB, DLF);
		
		edges = swap(edges, BL, FL);
		edges = swap(edges, UL, DL);
	}
	
	public final void l() {
		
	}
	
	public final void lPrime() {
		
	}
	
	public final void F() {
		Cubie temp = corners[ULF];
		corners[ULF] = corners[DLF];
		corners[DLF] = corners[DRF];
		corners[DRF] = corners[URF];
		corners[URF] = temp;
		
		temp = edges[UF];
		edges[UF] = edges[FL];
		edges[FL] = edges[DF];
		edges[DF] = edges[FR];
		edges[FR] = temp;
		
		updateCornerOrientation(ULF, (byte) 2);
		updateCornerOrientation(URF, (byte) 1);
		updateCornerOrientation(DRF, (byte) 2);
		updateCornerOrientation(DLF, (byte) 1);
		
		updateEdgeOrientation(UF);
		updateEdgeOrientation(FL);
		updateEdgeOrientation(DF);
		updateEdgeOrientation(FR);
	}
	
	public final void FPrime() {
		Cubie temp = corners[ULF];
		corners[ULF] = corners[URF];
		corners[URF] = corners[DRF];
		corners[DRF] = corners[DLF];
		corners[DLF] = temp;
		
		temp = edges[UF];
		edges[UF] = edges[FR];
		edges[FR] = edges[DF];
		edges[DF] = edges[FL];
		edges[FL] = temp;
		
		updateCornerOrientation(ULF, (byte) 2);
		updateCornerOrientation(URF, (byte) 1);
		updateCornerOrientation(DRF, (byte) 2);
		updateCornerOrientation(DLF, (byte) 1);
		
		updateEdgeOrientation(UF);
		updateEdgeOrientation(FL);
		updateEdgeOrientation(DF);
		updateEdgeOrientation(FR);
	}
	
	public final void F2() {
		corners = swap(corners, ULF, DRF);
		corners = swap(corners, URF, DLF);
		
		edges = swap(edges, UF, DF);
		edges = swap(edges, FL, FR);
	}
	
	public final void f() {
		
	}
	
	public final void fPrime() {
		
	}
	
	public final void R() {
		Cubie temp = corners[DRB];
		corners[DRB] = corners[URB];
		corners[URB] = corners[URF];
		corners[URF] = corners[DRF];
		corners[DRF] = temp;
		
		temp = edges[BR];
		edges[BR] = edges[UR];
		edges[UR] = edges[FR];
		edges[FR] = edges[DR];
		edges[DR] = temp;
		
		updateCornerOrientation(DRB, (byte) 2);
		updateCornerOrientation(DRF, (byte) 1);
		updateCornerOrientation(URF, (byte) 2);
		updateCornerOrientation(URB, (byte) 1);
	}
	
	public final void RPrime() {
		Cubie temp = corners[DRB];
		corners[DRB] = corners[DRF];
		corners[DRF] = corners[URF];
		corners[URF] = corners[URB];
		corners[URB] = temp;
		
		temp = edges[BR];
		edges[BR] = edges[DR];
		edges[DR] = edges[FR];
		edges[FR] = edges[UR];
		edges[UR] = temp;
		
		updateCornerOrientation(DRB, (byte) 2);
		updateCornerOrientation(DRF, (byte) 1);
		updateCornerOrientation(URF, (byte) 2);
		updateCornerOrientation(URB, (byte) 1);
	}
	
	public final void R2() {
		corners = swap(corners, DRB, URF);
		corners = swap(corners, URB, DRF);
		
		edges = swap(edges, BR, FR);
		edges = swap(edges, UR, DR);
	}
	
	public final void r() {
		
	}
	
	public final void rPrime() {
		
	}
	
	public final void B() {
		Cubie temp = corners[ULB];
		corners[ULB] = corners[URB];
		corners[URB] = corners[DRB];
		corners[DRB] = corners[DLB];
		corners[DLB] = temp;
		
		temp = edges[UB];
		edges[UB] = edges[BR];
		edges[BR] = edges[DB];
		edges[DB] = edges[BL];
		edges[BL] = temp;
		
		updateCornerOrientation(ULB, (byte) 1);
		updateCornerOrientation(URB, (byte) 2);
		updateCornerOrientation(DRB, (byte) 1);
		updateCornerOrientation(DLB, (byte) 2);
		
		updateEdgeOrientation(UB);
		updateEdgeOrientation(BL);
		updateEdgeOrientation(DB);
		updateEdgeOrientation(BR);
	}
	
	public final void BPrime() {
		Cubie temp = corners[ULB];
		corners[ULB] = corners[DLB];
		corners[DLB] = corners[DRB];
		corners[DRB] = corners[URB];
		corners[URB] = temp;
		
		temp = edges[UB];
		edges[UB] = edges[BL];
		edges[BL] = edges[DB];
		edges[DB] = edges[BR];
		edges[BR] = temp;
		
		updateCornerOrientation(ULB, (byte) 1);
		updateCornerOrientation(URB, (byte) 2);
		updateCornerOrientation(DRB, (byte) 1);
		updateCornerOrientation(DLB, (byte) 2);
		
		updateEdgeOrientation(UB);
		updateEdgeOrientation(BL);
		updateEdgeOrientation(DB);
		updateEdgeOrientation(BR);
	}
	
	public final void B2() {
		corners = swap(corners, ULB, DRB);
		corners = swap(corners, URB, DLB);
		
		edges = swap(edges, UB, DB);
		edges = swap(edges, BL, BR);
	}
	
	public final void b() {
		
	}
	
	public final void bPrime() {
		
	}
	
	public final void D() {
		Cubie temp = corners[DLB];
		corners[DLB] = corners[DRB];
		corners[DRB] = corners[DRF];
		corners[DRF] = corners[DLF];
		corners[DLF] = temp;
		
		temp = edges[DB];
		edges[DB] = edges[DR];
		edges[DR] = edges[DF];
		edges[DF] = edges[DL];
		edges[DL] = temp;
	}
	
	public final void DPrime() {
		Cubie temp = corners[DLF];
		corners[DLF] = corners[DRF];
		corners[DRF] = corners[DRB];
		corners[DRB] = corners[DLB];
		corners[DLB] = temp;
		
		temp = edges[DL];
		edges[DL] = edges[DF];
		edges[DF] = edges[DR];
		edges[DR] = edges[DB];
		edges[DB] = temp;
	}
	
	public final void D2() {
		corners = swap(corners, DLB, DRF);
		corners = swap(corners, DRB, DLF);
		
		edges = swap(edges, DB, DF);
		edges = swap(edges, DR, DL);
	}
	
	public final void d() {
		
	}
	
	public final void dPrime() {
		
	}
	
	public final void m() {
		
	}
	
	public final void mPrime() {
		
	}
	
	public final void m2() {
		
	}
	
	public final void e() {
		
	}
	
	public final void ePrime() {
		
	}
	
	public final void e2() {
		
	}
	
	public final void s() {
		
	}
	
	public final void sPrime() {
		
	}
	
	public final void s2() {
		
	}
}
