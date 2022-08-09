import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class temp4 {

	byte ULB = 0;
	byte URB = 1;
	byte URF = 2;
	byte ULF = 3;
	byte DLF = 4;
	byte DLB = 5;
	byte DRB = 6;
	byte DRF = 7;
	
	byte[] corners = {0, 1, 2, 3, 4, 5, 6, 7};
	// 0   1   2   3   4   5   6   7
	// ULB URB URF ULF DLF DLB DRB DRF
	
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
	
	byte[] edges = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
	// 0  1  2  3  4  5  6  7  8  9  10 11
	// UB UR UF UL FR FL BL BR DF DL DB DR
	
	int[] choose2 = {0, 0, 1, 3, 6, 10, 15, 21, 28, 36, 45, 55};
	int[] choose3 = {0, 0, 0, 1, 4, 10, 20, 35, 56, 84, 120, 165};
	int[] choose4 = {0, 0, 0, 0, 1, 5, 15, 35, 70, 126, 210, 330};
	
	public temp4() {
	}
	
	public temp4(byte[] corners, byte[] edges) {
		for (int i = 0; i < 8; i++) {
			this.corners[i] = corners[i];
		}
		for (int i = 0; i < 12; i++) {
			this.edges[i] = edges[i];
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {

		String[] allowedMoves = {"R", "R'", "L", "L'", "R2", "U2", "L2", "D2", "F2", "B2"};
		
		LinkedList<Tuple2<temp4, LinkedList<String>>> queue = new LinkedList<Tuple2<temp4, LinkedList<String>>>();
		boolean[] explored = new boolean[29400];
		explored[8522] = true;
		
		String[][] distances = new String[29400][];
		
		temp4 t = new temp4();
		System.out.println(t.getIndex());
		
		queue.add(new Tuple2<temp4, LinkedList<String>>(t, new LinkedList<String>()));
		distances[8522] = new String[] {};
		
		while (!queue.isEmpty()) {
			Tuple2<temp4, LinkedList<String>> dequeued = queue.removeFirst();
			
			for (String move : allowedMoves) {
				temp4 w = new temp4(dequeued.first.corners, dequeued.first.edges);
				w.doMove(move);
				int code = w.getIndex();
				if (!explored[code] || distances[code].length > dequeued.second.size() + 1) {
					explored[code] = true;
					LinkedList<String> wll = new LinkedList<String>();
					wll.addAll(dequeued.second);
					wll.add(move);
					queue.add(new Tuple2<temp4, LinkedList<String>>(w, wll));
					distances[code] = reverseAndClean(wll);
				}
			}
		}
		int x = 0;
		int index = 0;
		for (String[] s : distances) {
			if (s != null) {
			} else {
				System.out.println(index);
				x++;
			}
			index++;
		}
		System.out.println(x);
		
		temp4 test = new temp4();
		test.L2();
		test.R2();
		test.R();
		test.F2();
		test.RPrime();
		test.U2();
		test.LPrime();
		test.B2();
		test.R2();
		test.L();
		test.D2();
		test.L();
		System.out.print(test.getIndex());
		System.out.print(" ");
		System.out.println(Arrays.toString(distances[test.getIndex()]));
		
//		Gson gson = new GsonBuilder().create();
//		String json = gson.toJson(distances);		
//		FileWriter file = new FileWriter("thistlethwaiteG2-G3.json");
//		BufferedWriter buffer = new BufferedWriter(file);
//		buffer.write(json);
//		buffer.close();
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
	
	private final int getEdgeIndexL() {
		int[] edgeMap = new int[12];
		
		edgeMap[UL] = 0;
		edgeMap[UR] = 1;
		edgeMap[FL] = 2;
		edgeMap[FR] = 3;
		edgeMap[BL] = 4;
		edgeMap[BR] = 5;
		edgeMap[DL] = 6;
		edgeMap[DR] = 7;
		
		int[] edgeCombo = new int[4];
		int edgeComboInd = 0;
		
		for (int i = 0; i < 12 && edgeComboInd < 4; i++) {
			if (edges[i] == UL ||
				edges[i] == FL ||
				edges[i] == DL ||
				edges[i] == BL) {
				edgeCombo[edgeComboInd++] = edgeMap[i];
			}
		}
		Arrays.sort(edgeCombo);
		return edgeCombo[0]+choose2[edgeCombo[1]]+choose3[edgeCombo[2]]+choose4[edgeCombo[3]];
	}
	
	private final int getTetradPermIndex() {
		int[] cornerCombo = new int[4];
		int cornerComboInd = 0;
		
		for (int i = 0; i < 8 && cornerComboInd < 4; i++) {
			if (corners[i] == ULB ||
				corners[i] == URF ||
				corners[i] == DLF ||
				corners[i] == DRB) {
				cornerCombo[cornerComboInd++] = i;
			}
		}
		Arrays.sort(cornerCombo);
		return cornerCombo[0]+choose2[cornerCombo[1]]+choose3[cornerCombo[2]]+choose4[cornerCombo[3]];
	}
	
	public final int getCornerIndex(int corner) {
		for (int i = 0; i < 8; i ++) {
			if  (corners[i] - corner == 0)
				return i;
		}
		return -1;
	}
	
	private final int getTetradTwistAndParity() {
		byte[] rightTetrad = new byte[4];
		byte[] leftTetrad = new byte[4];
		
		int rightTetradIndex = 0;
		int leftTetradIndex = 0;
		for (int i = 0; i < 8; i++) {
			if (corners[i]==URF||corners[i]==ULB||corners[i]==DLF||corners[i]==DRB) {
				leftTetrad[leftTetradIndex] = (byte)corners[i];
				leftTetradIndex++;
			} else {
				rightTetrad[rightTetradIndex] = (byte)corners[i];
				rightTetradIndex++;
			}
		}
		byte[] newCorners = {
				leftTetrad[0],
				rightTetrad[0],
				leftTetrad[1],
				rightTetrad[1],
				leftTetrad[2],
				rightTetrad[2],
				leftTetrad[3],
				rightTetrad[3],
			};

		temp4 newTemp4 = new temp4(newCorners, this.edges);
		
		int drb = newTemp4.getCornerIndex(DRB);
		switch(drb) {
		case 0:
			newTemp4.B2();
			break;
		case 2:
			newTemp4.R2();
			break;
		case 4:
			newTemp4.D2();
			break;
		}
		
		int dlf = newTemp4.getCornerIndex(DLF);
		switch(dlf) {
		case 0:
			newTemp4.L2();
			break;
		case 2:
			newTemp4.F2();
		}
		
		int ulb = newTemp4.getCornerIndex(ULB);
		if (ulb == 2) {
			newTemp4.U2();
		}
		
		int dlb = newTemp4.getCornerIndex(DLB);
		switch(dlb) {
		case 7:
			newTemp4.F2();
			newTemp4.L2();
			newTemp4.F2();
			newTemp4.U2();
			break;
		case 3:
			newTemp4.U2();
			newTemp4.F2();
			newTemp4.U2();
			newTemp4.L2();
			break;
		case 1:
			newTemp4.L2();
			newTemp4.U2();
			newTemp4.L2();
			newTemp4.F2();
			break;
		}
				
		int ulf = newTemp4.corners[ULF];
		int urb = newTemp4.corners[URB];
		int drf = newTemp4.corners[DRF];
		int[] map = {0, 0, 0, 1, 0, 0, 0, 2};
		
		ulf = map[ulf];
		urb = map[urb];
		drf = map[drf];
		
		int[] perm = {ulf, urb, drf};
		int[] lehmer = new int[3];
		for (int i = 0; i < 3; i++) {
			lehmer[i] = perm[i];
		}
		for (int i = 1; i < 3; ++i) {
			int j = i;
			while (j-- > 0) {
				if (perm[j] < perm[i])
					lehmer[i] -= 1;
			}
		}
		return lehmer[0]*2+lehmer[1]+lehmer[2];
		
	}
	
	public final int getIndex() {
//		System.out.print(getEdgeIndexL());
//		System.out.print(" ");
//		System.out.print(getTetradPermIndex());
//		System.out.print(" ");
//		System.out.print(getTetradTwistAndParity());
//		System.out.print(" ");
//		System.out.print(((getEdgeIndexL()*70)+getTetradPermIndex())*6+getTetradTwistAndParity());
//		System.out.print(" ");
//		System.out.print(Arrays.toString(corners));
//		System.out.print(" ");
//		System.out.println(Arrays.toString(edges));
		return ((getEdgeIndexL()*70)+getTetradPermIndex())*6+getTetradTwistAndParity();
	}
	
	private static final byte[] swap(byte[] corners2, byte index1, byte index2) {
		byte temp = corners2[index1];
		corners2[index1] = corners2[index2];
		corners2[index2] = temp;
		
		return corners2;
	}
	
	public final void U() {
		byte temp = corners[ULF];
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
		byte temp = corners[ULB];
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
		byte temp = corners[DLB];
		corners[DLB] = corners[DLF];
		corners[DLF] = corners[ULF];
		corners[ULF] = corners[ULB];
		corners[ULB] = temp;
		
		temp = edges[BL];
		edges[BL] = edges[DL];
		edges[DL] = edges[FL];
		edges[FL] = edges[UL];
		edges[UL] = temp;
	
	}
	
	public final void LPrime() {
		byte temp = corners[DLB];
		corners[DLB] = corners[ULB];
		corners[ULB] = corners[ULF];
		corners[ULF] = corners[DLF];
		corners[DLF] = temp;
		
		temp = edges[BL];
		edges[BL] = edges[UL];
		edges[UL] = edges[FL];
		edges[FL] = edges[DL];
		edges[DL] = temp;
		
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
		byte temp = corners[ULF];
		corners[ULF] = corners[DLF];
		corners[DLF] = corners[DRF];
		corners[DRF] = corners[URF];
		corners[URF] = temp;
		
		temp = edges[UF];
		edges[UF] = edges[FL];
		edges[FL] = edges[DF];
		edges[DF] = edges[FR];
		edges[FR] = temp;
		
	}
	
	public final void FPrime() {
		byte temp = corners[ULF];
		corners[ULF] = corners[URF];
		corners[URF] = corners[DRF];
		corners[DRF] = corners[DLF];
		corners[DLF] = temp;
		
		temp = edges[UF];
		edges[UF] = edges[FR];
		edges[FR] = edges[DF];
		edges[DF] = edges[FL];
		edges[FL] = temp;
	
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
		byte temp = corners[DRB];
		corners[DRB] = corners[URB];
		corners[URB] = corners[URF];
		corners[URF] = corners[DRF];
		corners[DRF] = temp;
		
		temp = edges[BR];
		edges[BR] = edges[UR];
		edges[UR] = edges[FR];
		edges[FR] = edges[DR];
		edges[DR] = temp;
	}
	
	public final void RPrime() {
		byte temp = corners[DRB];
		corners[DRB] = corners[DRF];
		corners[DRF] = corners[URF];
		corners[URF] = corners[URB];
		corners[URB] = temp;
		
		temp = edges[BR];
		edges[BR] = edges[DR];
		edges[DR] = edges[FR];
		edges[FR] = edges[UR];
		edges[UR] = temp;
		
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
		byte temp = corners[ULB];
		corners[ULB] = corners[URB];
		corners[URB] = corners[DRB];
		corners[DRB] = corners[DLB];
		corners[DLB] = temp;
		
		temp = edges[UB];
		edges[UB] = edges[BR];
		edges[BR] = edges[DB];
		edges[DB] = edges[BL];
		edges[BL] = temp;
		
	}
	
	public final void BPrime() {
		byte temp = corners[ULB];
		corners[ULB] = corners[DLB];
		corners[DLB] = corners[DRB];
		corners[DRB] = corners[URB];
		corners[URB] = temp;
		
		temp = edges[UB];
		edges[UB] = edges[BL];
		edges[BL] = edges[DB];
		edges[DB] = edges[BR];
		edges[BR] = temp;

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
		byte temp = corners[DLB];
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
		byte temp = corners[DLF];
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
}
