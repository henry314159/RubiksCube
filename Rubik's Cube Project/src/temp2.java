import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.LinkedList;

public class temp2{
	
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
	
	byte[] edges = new byte[12];
	// 0  1  2  3  4  5  6  7  8  9  10 11
	// UB UR UF UL FR FL BL BR DF DL DB DR
	// 0 - oriented, 1 - flipped
	
	public temp2() {
		
	}
	
	public temp2(byte[] edges) {
		for (int i = 0; i < 12; i++) {
			this.edges[i] = edges[i];
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
		temp2 t = new temp2();
		
		String[] allowedMoves = {"R", "R'", "L", "L'", "F", "F'", "B", "B'", "U", "U'", "D", "D'"};
		
		LinkedList<Tuple2<temp2, LinkedList<String>>> queue = new LinkedList<Tuple2<temp2, LinkedList<String>>>();
		boolean[] explored = new boolean[2048];
		explored[0] = true;
		
		String[][] distances = new String[2048][];
		
		queue.add(new Tuple2<temp2, LinkedList<String>>(t, new LinkedList<String>()));
		distances[0] = new String[] {""};
		
		while (!queue.isEmpty()) {
			Tuple2<temp2, LinkedList<String>> dequeued = queue.removeFirst();
			
			for (String move : allowedMoves) {
				temp2 w = new temp2(dequeued.first.edges);
				w.doMove(move);
				int code = w.getEdgeOrientation();
				if (!explored[code]) {
					explored[code] = true;
					LinkedList<String> wll = new LinkedList<String>();
					wll.addAll(dequeued.second);
					wll.add(move);
					queue.add(new Tuple2<temp2, LinkedList<String>>(w, wll));
					distances[code] = reverseAndClean(wll);
				}
			}
		}
//		try(FileOutputStream f = new FileOutputStream("thistlethwaiteG1-G2.txt");
//			    ObjectOutput s = new ObjectOutputStream(f)) {
//			    s.writeObject(distances);
//			    s.close();
//			    f.close();
//			}
//		String[][] distances;
//		try(FileInputStream in = new FileInputStream("thistlethwaiteG1-G2.txt");
//				ObjectInputStream s = new ObjectInputStream(in)) {
//				distances = (String[][]) s.readObject();
//				s.close();
//				in.close();
//		}
		int oh = 0;
		for (String[] s : distances) {
			if (s != null) {
				
			}else {
				oh++;
			}
		}
		System.out.println(oh);
	}
	
	public final static String[] reverseAndClean(LinkedList<String> lls_) {
		LinkedList<String> lls = new LinkedList<String>();
		lls.addAll(lls_);
		String[] out = new String[lls.size()];
		int index = 0;
		while (!lls.isEmpty()) {
			String temp = lls.removeLast();
			switch(temp) {
			case "R":
				temp = "R'";
				break;
			case "L":
				temp = "L'";
				break;
			case "U":
				temp = "U'";
				break;
			case "D":
				temp = "D'";
				break;
			case "F":
				temp = "F'";
				break;
			case "B":
				temp = "B'";
				break;
			case "R'":
				temp = "R";
				break;
			case "L'":
				temp = "L";
				break;
			case "U'":
				temp = "U";
				break;
			case "D'":
				temp = "D";
				break;
			case "F'":
				temp = "F";
				break;
			case "B'":
				temp = "B";
				break;
			case "R2":
				temp = "R2";
				break;
			case "L2":
				temp = "L2";
				break;
			case "U2":
				temp = "U2";
				break;
			case "D2":
				temp = "D2";
				break;
			case "F2":
				temp = "F2";
				break;
			case "B2":
				temp = "B2";
				break;
			}
			out[index] = temp;
			index++;
		}
		return out;
	}
	
	public final void doMove(String m) {
		switch (m) {
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

	public final int getEdgeOrientation() {
		int orient = 0;
		int powerOfTwo = 1;
		for (int i = 0; i < 11; i++) {
			orient += edges[i] * powerOfTwo;
			powerOfTwo *= 2;
		}
		return orient;
	}
	
	private final void updateEdgeOrientation(byte index) {
		edges[index] ^= 1;
	}
	
	private static final byte[] swap(byte[] a, byte index1, byte index2) {
		byte temp = a[index1];
		a[index1] = a[index2];
		a[index2] = temp;
		
		return a;
	}
	
	public final void U() {
		byte temp = edges[UL];
		edges[UL] = edges[UF];
		edges[UF] = edges[UR];
		edges[UR] = edges[UB];
		edges[UB] = temp;
	}
	
	public final void UPrime() {
		byte temp = edges[UB];
		edges[UB] = edges[UR];
		edges[UR] = edges[UF];
		edges[UF] = edges[UL];
		edges[UL] = temp;
	}
	
	public final void U2() {
		edges = swap(edges, UB, UF);
		edges = swap(edges, UR, UL);
	}
	
	public final void u() {
		
	}
	
	public final void uPrime() {
		
	}
	
	public final void L() {
		byte temp = edges[BL];
		edges[BL] = edges[DL];
		edges[DL] = edges[FL];
		edges[FL] = edges[UL];
		edges[UL] = temp;

	}
	
	public final void LPrime() {
		byte temp = edges[BL];
		edges[BL] = edges[UL];
		edges[UL] = edges[FL];
		edges[FL] = edges[DL];
		edges[DL] = temp;

	}
	
	public final void L2() {

		edges = swap(edges, BL, FL);
		edges = swap(edges, UL, DL);
	}
	
	public final void l() {
		
	}
	
	public final void lPrime() {
		
	}
	
	public final void F() {
		byte temp = edges[UF];
		edges[UF] = edges[FL];
		edges[FL] = edges[DF];
		edges[DF] = edges[FR];
		edges[FR] = temp;
		
		updateEdgeOrientation(UF);
		updateEdgeOrientation(FL);
		updateEdgeOrientation(DF);
		updateEdgeOrientation(FR);
	}
	
	public final void FPrime() {
		byte temp = edges[UF];
		edges[UF] = edges[FR];
		edges[FR] = edges[DF];
		edges[DF] = edges[FL];
		edges[FL] = temp;
	
		updateEdgeOrientation(UF);
		updateEdgeOrientation(FL);
		updateEdgeOrientation(DF);
		updateEdgeOrientation(FR);
	}
	
	public final void F2() {

		edges = swap(edges, UF, DF);
		edges = swap(edges, FL, FR);
	}
	
	public final void f() {
		
	}
	
	public final void fPrime() {
		
	}
	
	public final void R() {
		byte temp = edges[BR];
		edges[BR] = edges[UR];
		edges[UR] = edges[FR];
		edges[FR] = edges[DR];
		edges[DR] = temp;

	}
	
	public final void RPrime() {
		byte temp = edges[BR];
		edges[BR] = edges[DR];
		edges[DR] = edges[FR];
		edges[FR] = edges[UR];
		edges[UR] = temp;
		
	}
	
	public final void R2() {

		edges = swap(edges, BR, FR);
		edges = swap(edges, UR, DR);
	}
	
	public final void r() {
		
	}
	
	public final void rPrime() {
		
	}
	
	public final void B() {
		byte temp = edges[UB];
		edges[UB] = edges[BR];
		edges[BR] = edges[DB];
		edges[DB] = edges[BL];
		edges[BL] = temp;
		
		updateEdgeOrientation(UB);
		updateEdgeOrientation(BL);
		updateEdgeOrientation(DB);
		updateEdgeOrientation(BR);
	}
	
	public final void BPrime() {
		byte temp = edges[UB];
		edges[UB] = edges[BL];
		edges[BL] = edges[DB];
		edges[DB] = edges[BR];
		edges[BR] = temp;
		
		updateEdgeOrientation(UB);
		updateEdgeOrientation(BL);
		updateEdgeOrientation(DB);
		updateEdgeOrientation(BR);
	}
	
	public final void B2() {

		edges = swap(edges, UB, DB);
		edges = swap(edges, BL, BR);
	}
	
	public final void b() {
		
	}
	
	public final void bPrime() {
		
	}
	
	public final void D() {
		byte temp = edges[DB];
		edges[DB] = edges[DR];
		edges[DR] = edges[DF];
		edges[DF] = edges[DL];
		edges[DL] = temp;
	}
	
	public final void DPrime() {
		byte temp = edges[DL];
		edges[DL] = edges[DF];
		edges[DF] = edges[DR];
		edges[DR] = edges[DB];
		edges[DB] = temp;
	}
	
	public final void D2() {
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
