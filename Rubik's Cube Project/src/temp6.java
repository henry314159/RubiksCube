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

public class temp6 {

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
	
	int[][] pairs = {
			{0,1},{0,2},{0,3},{0,4},{0,5},{0,6},{0,7},
			{1,2},{1,3},{1,4},{1,5},{1,6},{1,7},
			{2,3},{2,4},{2,5},{2,6},{2,7},
			{3,4},{3,5},{3,6},{3,7},
			{4,5},{4,6},{4,7},
			{5,6},{5,7},
			{6,7}
	};
	
	int[] bases = {90, 6, 1};
	
	public temp6() {
	}
	
	public temp6(byte[] corners, byte[] edges) {
		for (int i = 0; i < 8; i++) {
			this.corners[i] = corners[i];
		}
		for (int i = 0; i < 12; i++) {
			this.edges[i] = edges[i];
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {

		String[] allowedMoves = {"R2", "U2", "L2", "D2", "F2", "B2"};
		
		LinkedList<Tuple2<temp6, LinkedList<String>>> queue = new LinkedList<Tuple2<temp6, LinkedList<String>>>();
		boolean[] explored = new boolean[663552];
		explored[0] = true;
		
		String[][] distances = new String[663552][];
		
		temp6 t = new temp6();
		System.out.println(t.getIndex());
		
		queue.add(new Tuple2<temp6, LinkedList<String>>(t, new LinkedList<String>()));
		distances[0] = new String[] {};
		
		while (!queue.isEmpty()) {
			Tuple2<temp6, LinkedList<String>> dequeued = queue.removeFirst();
			
			for (String move : allowedMoves) {
				temp6 w = new temp6(dequeued.first.corners, dequeued.first.edges);
				w.doMove(move);
				int code = w.getIndex();
				if (!explored[code] || distances[code].length > dequeued.second.size() + 1) {
					explored[code] = true;
					LinkedList<String> wll = new LinkedList<String>();
					wll.addAll(dequeued.second);
					wll.add(move);
					queue.add(new Tuple2<temp6, LinkedList<String>>(w, wll));
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
		
//		temp6 test = new temp6();
//		test.L2();
//		test.U2();
//		test.F2();
//		test.R2();
//		System.out.print(test.getIndex());
//		System.out.print(" ");
//		System.out.println(Arrays.toString(distances[test.getIndex()]));
		
		Gson gson = new GsonBuilder().create();
		String json = gson.toJson(distances);		
		FileWriter file = new FileWriter("thistlethwaiteG3-G4.json");
		BufferedWriter buffer = new BufferedWriter(file);
		buffer.write(json);
		buffer.close();
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
	
	public int getIndex() {
		int[] edgeMap = new int[12];
		
		// M slice.
	    edgeMap[UB] = 0;
	    edgeMap[UF] = 1;
	    edgeMap[DF] = 2;
	    edgeMap[DB] = 3;

	    // S slice.
	    edgeMap[UR] = 0;
	    edgeMap[UL] = 1;
	    edgeMap[DL] = 2;
	    edgeMap[DR] = 3;

	    // E slice.
	    edgeMap[FR] = 0;
	    edgeMap[FL] = 1;
	    edgeMap[BL] = 2;
	    edgeMap[BR] = 3;
	    
	    int[] m = {
	    		edgeMap[getEdgeIndex(UB)],
		    	edgeMap[getEdgeIndex(UF)],
		    	edgeMap[getEdgeIndex(DF)],
		    	edgeMap[getEdgeIndex(DB)]
	    };
	    
	    int[] s = {
	    		edgeMap[getEdgeIndex(UR)],
		    	edgeMap[getEdgeIndex(UL)],
		    	edgeMap[getEdgeIndex(DL)],
		    	edgeMap[getEdgeIndex(DR)]
	    };
	    
	    int[] e = {
	    		edgeMap[getEdgeIndex(FR)],
		    	edgeMap[getEdgeIndex(FL)],
	    };
	    
	    int edgeRank = permIndex(m) * 288 + permIndex(s) * 12 + partialPermIndex(e);
		
	    int[] cornerMap = new int[8];
	    
	    cornerMap[ULB] = 0;
	    cornerMap[URF] = 1;
	    cornerMap[DLF] = 2;
	    cornerMap[DRB] = 3;
	    
	    cornerMap[URB] = 0;
	    cornerMap[ULF] = 1;
	    cornerMap[DLB] = 2;
	    cornerMap[DRF] = 3;
	    
	    int[] tetrad = {
	    		cornerMap[getCornerIndex(ULB)],
	    		cornerMap[getCornerIndex(URF)],
	    		cornerMap[getCornerIndex(DLF)],
	    		cornerMap[getCornerIndex(DRB)],
	    };
	    
	    int urb = cornerMap[getCornerIndex(URB)];
	    
	    int tetradRank = permIndex(tetrad);
	    
	    int cornerRank = tetradRank * 4 + urb;
	    
		return edgeRank * 96 + cornerRank;
	}
	
	private int getEdgeIndex(int x) {
		for (int i = 0; i < 12; i++) {
			if (edges[i] - x == 0) {
				return i;
			}
		}
		return -1;
	}
	
	private int getCornerIndex(int x) {
		for (int i = 0; i < 8; i++) {
			if (corners[i] - x == 0) {
				return i;
			}
		}
		return -1;
	}
	
	private final int permIndex(int[] p) {
		int[] lehmer = new int[4];
		for (int i = 0; i < 4; i++) {
			lehmer[i] = p[i];
		}
		
		for (int i = 1; i < 4; i++) {
			int j = i - 1;
			while (j > -1) {
				if (p[j] < p[i])
					lehmer[i]--;
				j -= 1;
			}
		}
		return 6*lehmer[0]+2*lehmer[1]+lehmer[2]+lehmer[3];
	}
	
	private final int partialPermIndex(int[] pp) {
		int[] lehmer = new int[2];
		for (int i = 0; i < 2; i++) {
			lehmer[i] = pp[i];
		}
		
		for (int i = 1; i < 2; i++) {
			int j = i - 1;
			while (j > -1) {
				if (pp[j] < pp[i])
					lehmer[i]--;
				j -= 1;
			}
		}
		return 3*lehmer[0]+lehmer[1];
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
