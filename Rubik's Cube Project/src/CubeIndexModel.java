
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
	
	int[] bases = {90, 6, 1}; // {6C2*6C1*6C0, 6C1*6C0, 6C0}
	
	public CubeIndexModel() {
		for (byte i = 0; i < 8; i++) {
			corners[i] = new Cubie(i, (byte) 0);
		}
		
		for (byte i = 0; i < 12; i++) {
			edges[i] = new Cubie(i, (byte) 0);
		}
	}
	
	public CubeIndexModel(Cubie[] corners, Cubie[] edges) {
		for (byte i = 0; i < 8; i++) {
			this.corners[i] = new Cubie(corners[i].getPos(), corners[i].getOrient());
		}
		
		for (byte i = 0; i < 12; i++) {
			this.edges[i] = new Cubie(edges[i].getPos(), edges[i].getOrient());
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
	
	public static final void writeTable(int tableNum) throws IOException {
		int tableSize;
		String filename;
		LinkedList<String> allowedMoves = new LinkedList<String>();
		allowedMoves.add("R2");
		allowedMoves.add("L2");
		allowedMoves.add("F2");
		allowedMoves.add("B2");
		allowedMoves.add("U2");
		allowedMoves.add("D2");
		switch(tableNum) {
		case 0:
			tableSize = 2048;
			filename = "thistlethwaiteG0-G1.json";
			allowedMoves.add("R");
			allowedMoves.add("L");
			allowedMoves.add("F");
			allowedMoves.add("B");
			allowedMoves.add("U");
			allowedMoves.add("D");
			allowedMoves.add("R'");
			allowedMoves.add("L'");
			allowedMoves.add("F'");
			allowedMoves.add("B'");
			allowedMoves.add("U'");
			allowedMoves.add("D'");
			break;
		case 1:
			tableSize = 1082565;
			filename = "thistlethwaiteG1-G2.json";
			allowedMoves.add("R");
			allowedMoves.add("L");
			allowedMoves.add("U");
			allowedMoves.add("D");
			allowedMoves.add("R'");
			allowedMoves.add("L'");
			allowedMoves.add("U'");
			allowedMoves.add("D'");
			break;
		case 2:
			tableSize = 352800;
			filename = "thistlethwaiteG2-G3.json";
			allowedMoves.add("U");
			allowedMoves.add("D");
			allowedMoves.add("U'");
			allowedMoves.add("D'");
			break;
		case 3:
			tableSize = 663552;
			filename = "thistlethwaiteG3-G4.json";
			break;
		default:
			System.out.println("Invalid table number");
			return;
		}
		
		CubeIndexModel c = new CubeIndexModel();
				
		LinkedList<Tuple2<CubeIndexModel, LinkedList<String>>> queue = new LinkedList<Tuple2<CubeIndexModel, LinkedList<String>>>();
		boolean[] explored = new boolean[tableSize];
		explored[c.getIndex(tableNum)] = true;
		
		String[][] distances = new String[tableSize][];
		
		queue.add(new Tuple2<CubeIndexModel, LinkedList<String>>(c, new LinkedList<String>()));
		distances[c.getIndex(tableNum)] = new String[] {};
		
		while (!queue.isEmpty()) {
			Tuple2<CubeIndexModel, LinkedList<String>> dequeued = queue.removeFirst();
			
			for (String move : allowedMoves) {
				CubeIndexModel w = new CubeIndexModel(dequeued.first.corners, dequeued.first.edges);
				w.doMove(move);
				int code = w.getIndex(tableNum);
				if (!explored[code] || distances[code].length > dequeued.second.size() + 1) {
					explored[code] = true;
					LinkedList<String> wll = new LinkedList<String>();
					wll.addAll(dequeued.second);
					wll.add(move);
					queue.add(new Tuple2<CubeIndexModel, LinkedList<String>>(w, wll));
					distances[code] = reverseAndClean(wll);
				}
			}
		}
		Gson gson = new GsonBuilder().create();
		String json = gson.toJson(distances);
		FileWriter file = new FileWriter(filename);
		// had to use buffered write to avoid write errors
		BufferedWriter buffer = new BufferedWriter(file);
		buffer.write(json);
		buffer.close();
	}
	
	private final static String[] reverseAndClean(LinkedList<String> lls_) {
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
	
	private final int getIndex(int tableNum) {
		switch(tableNum) {
		case 0:
			return getIndex0();
		case 1:
			return getIndex1();
		case 2:
			return getIndex2();
		case 3:
			return getIndex3();
		default:
			return -1;
		}
	}
	
	private final int getCornerOrientation() {
		int orient = 0;
		int powerOfThree = 1;
		
		for (int i = 0; i < 7; i++) {
			orient += corners[i].getOrient() * powerOfThree;
			powerOfThree *= 3;
		}
		return orient;
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

	private final int getEdgePartialCombination() {
		byte[] interestingEdges = new byte[4];
		int index = 0;
		for (int i = 0; i < 12 && index < 4; i++) {
			if (edges[i].getPos() == FL || edges[i].getPos() == FR || edges[i].getPos() == BL || edges[i].getPos() == BR) {
				interestingEdges[index] = (byte)i;
				index++;
			}
		}
		Arrays.sort(interestingEdges);
		int rank = interestingEdges[0] + choose2[interestingEdges[1]] + choose3[interestingEdges[2]] + choose4[interestingEdges[3]];
		return rank;
	}
	
	private final int getEdgeIndexM() {
		int[] edgeMap = new int[12];
		
		edgeMap[UB] = 0;
		edgeMap[UR] = 1;
		edgeMap[UF] = 2;
		edgeMap[UL] = 3;
		edgeMap[DF] = 4;
		edgeMap[DL] = 5;
		edgeMap[DB] = 6;
		edgeMap[DR] = 7;
		
		int[] edgeCombo = new int[4];
		int edgeComboInd = 0;
		
		for (int i = 0; i < 12 && edgeComboInd < 4; i++) {
			if (edges[i].getPos() == UB ||
				edges[i].getPos() == UF ||
				edges[i].getPos() == DF ||
				edges[i].getPos() == DB) {
				edgeCombo[edgeComboInd++] = edgeMap[i];
			}
		}
		Arrays.sort(edgeCombo);
		return edgeCombo[0]+choose2[edgeCombo[1]]+choose3[edgeCombo[2]]+choose4[edgeCombo[3]];
	}
	
	private final int getCornerIndex(int corner) {
		for (int i = 0; i < 8; i ++) {
			if  (corners[i].getPos() - corner == 0)
				return i;
		}
		return -1;
	}
	
	private final int getParityIndex() {
		int parity = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = i + 1; j < 8; j++) {
				parity ^= getCornerIndex(i) < getCornerIndex(j) ? 1 : 0;
			}
		}
		return parity;
	}
	
	private final int getTetradPairIndex() {
		int[][] tetradPairs = {
			getTetradPair(ULB, URF),	
			getTetradPair(DLF, DRB),
			getTetradPair(URB, ULF),
			getTetradPair(DLB, DRF),
		};
		
		int rank = 0;
		boolean[] exclude = new boolean[8];
		
		for (int i = 0; i < 3; i++) {
			int pairRank = 0;
			for (int j = 0; j < 28; j++) {
				if (tetradPairs[i][0] == pairs[j][0] &&
					tetradPairs[i][1] == pairs[j][1]) {
					rank += (pairRank) * bases[i];
					break;
				} else if (!exclude[pairs[j][0]]
						&& !exclude[pairs[j][1]]) {
					pairRank++;
				}
			}
			exclude[tetradPairs[i][0]] ^= true;
			exclude[tetradPairs[i][1]] ^= true;
		}
		
		return rank;
	}
	
	private final int[] getTetradPair(int c1, int c2) {
		int[] tetradPair = new int[2];
		int comboInd = 0;
		
		for (int i = 0; i < 8 && comboInd < 2; i++) {
			if (corners[i].getPos() == c1 || corners[i].getPos() == c2) {
				tetradPair[comboInd++] = i;
			}
		}
		
		return tetradPair;
	}
	
	public final int getIndex0() {
		int orient = 0;
		int powerOfTwo = 1;
		for (int i = 0; i < 11; i++) {
			orient += edges[i].getOrient() * powerOfTwo;
			powerOfTwo *= 2;
		}
		return orient;
	}
	
	public final int getIndex1() {
		return getCornerOrientation() + 2187*getEdgePartialCombination();
	}
	
	public final int getIndex2() {
		return ((getEdgeIndexM()*2520)+getTetradPairIndex())*2+getParityIndex();
	}
	
	public final int getIndex3() {
		int[] edgeMap = new int[12];
		
	    edgeMap[UB] = 0;
	    edgeMap[UF] = 1;
	    edgeMap[DF] = 2;
	    edgeMap[DB] = 3;

	    edgeMap[UR] = 0;
	    edgeMap[UL] = 1;
	    edgeMap[DL] = 2;
	    edgeMap[DR] = 3;

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
			if (edges[i].getPos() - x == 0) {
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
	
}
