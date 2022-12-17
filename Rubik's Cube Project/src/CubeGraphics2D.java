// make so tells u if u put it in wrong and no solve is found
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.LinkedList;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import processing.core.*;

public class CubeGraphics2D extends PApplet {
	
	Face[] faceArray = new Face[6];
	
	int squareSideLength = 50;
	
	int[][] colours = {
			{255, 255, 0}, // left
			{255, 165, 0}, // back
			{0, 255, 0}, // up
			{255, 0, 0}, // front
			{255, 255, 255}, // right
			{0, 0, 255} // down
	};
	
	int[][] orderedCorners = new int[][] {
		{0, 1, 2},
		{1, 2, 4},
		{2, 3, 4},
		{0, 2, 3},
		{0, 3, 5},
		{0, 1, 5},
		{1, 4, 5},
		{3, 4, 5}
	};
	
	int[][] orderedEdges = new int[][] {
		{1, 2},
		{2, 4},
		{2, 3},
		{0, 2},
		{3, 4},
		{0, 3},
		{0, 1},
		{1, 4},
		{3, 5},
		{0, 5},
		{1, 5},
		{4, 5}
	};
	
	public static void main(String[] args) {
		PApplet.main("CubeGraphics2D");
	}
	
	public void settings() {
		this.size(600, 450);
	}
	
	public void setup() {
		Square[] s0 = new Square[9];
		Square[] s1 = new Square[9];
		Square[] s2 = new Square[9];
		Square[] s3 = new Square[9];
		Square[] s4 = new Square[9];
		Square[] s5 = new Square[9];
		
		boolean centre = false;
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (i == 1 && j == 1)
					centre = true;
				s0[i*3+j] = new Square(j*squareSideLength, i*squareSideLength, squareSideLength, 0, centre);
				s1[i*3+j] = new Square(j*squareSideLength, i*squareSideLength, squareSideLength, 1, centre);
				s2[i*3+j] = new Square(j*squareSideLength, i*squareSideLength, squareSideLength, 2, centre);
				s3[i*3+j] = new Square(j*squareSideLength, i*squareSideLength, squareSideLength, 3, centre);
				s4[i*3+j] = new Square(j*squareSideLength, i*squareSideLength, squareSideLength, 4, centre);
				s5[i*3+j] = new Square(j*squareSideLength, i*squareSideLength, squareSideLength, 5, centre);
				centre = false;
			}
		}
		
		faceArray[0] = new Face(0, squareSideLength*3, s0);
		faceArray[1] = new Face(squareSideLength*3, 0, s1);
		faceArray[2] = new Face(squareSideLength*3, squareSideLength*3, s2);
		faceArray[3] = new Face(squareSideLength*3, squareSideLength*6, s3);
		faceArray[4] = new Face(squareSideLength*6, squareSideLength*3, s4);
		faceArray[5] = new Face(squareSideLength*9, squareSideLength*3, s5);
	}
	
	public void draw() {
		for (int i = 0; i < 6; i++)
			faceArray[i].show();
	}
	
	public void mouseClicked() {
		for (Face f : faceArray) {
			for (Square s : f.squareArray) {
				int squareX = s.x + f.x;
				int squareY = s.y + f.y;
				
				if (((squareX < mouseX) && (mouseX < squareX + squareSideLength)) &&
					((squareY < mouseY) && (mouseY < squareY + squareSideLength)) &&
					!s.centre) {
					s.colour = (s.colour + 1) % 6;
				}
			}
		}
	}
	
	public void keyPressed() {
		
		Gson gson = new Gson();
		Cubie[][] cube = getCube();
		CubeIndexModel ci = new CubeIndexModel(cube[0], cube[1]);
		
		try {
			String[][] data1 = gson.fromJson(new FileReader("thistlethwaiteG0-G1.json"), String[][].class);
			String[][] data2 = gson.fromJson(new FileReader("thistlethwaiteG1-G2.json"), String[][].class);
			String[][] data3 = gson.fromJson(new FileReader("thistlethwaiteG2-G3.json"), String[][].class);
			String[][] data4 = gson.fromJson(new FileReader("thistlethwaiteG3-G4.json"), String[][].class);
			LinkedList<String> solve = new LinkedList<String>();
			
			String[] phase1Solve = data1[ci.getIndex0()];
			
			for (String move : phase1Solve) {
				ci.doMove(move);
				solve.add(move);
			}
			
			String[] phase2Solve = data2[ci.getIndex1()];
			
			for (String move : phase2Solve) {
				ci.doMove(move);
				solve.add(move);
			}
			
			String[] phase3Solve = data3[ci.getIndex2()];
			
			for (String move : phase3Solve) {
				ci.doMove(move);
				solve.add(move);
			}

			String[] phase4Solve = data4[ci.getIndex3()];
			
			for (String move : phase4Solve) {
				solve.add(move);
			}
			
			solve = cleanSolve(solve);
						
			System.out.println(solve.toString());
			System.out.println(solve.size());
			System.out.println();
			
		} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private static LinkedList<String> cleanSolve(LinkedList<String> solve) {
		if (solve.size() == 0) {
			return solve;
		}
		LinkedList<String> clean = new LinkedList<String>();
		String lastItem = solve.removeFirst();
		clean.add(lastItem);
		for (String item : solve) {
			String cleanItem = item;
			if (item.startsWith(""+lastItem.charAt(0))) {
				if (item.length() == lastItem.length()) {
					if (item.length() == 1) {
						cleanItem = item.charAt(0) + "2";
					} else {
						if (item.endsWith("'") && lastItem.endsWith("'")) {
							cleanItem = item.charAt(0) + "2";
						} else if ((item.endsWith("'") && lastItem.endsWith("2")) || (item.endsWith("2") && lastItem.endsWith("'"))) {
							cleanItem = ""+item.charAt(0);
						} else {
							cleanItem = null;
						}
					}
				} else {
					if (item.length() < lastItem.length()) {
						if (lastItem.endsWith("2")) {
							cleanItem = item.charAt(0) + "'";
						} else {
							cleanItem = null;
						}
					} else {
						if (item.endsWith("2")) {
							cleanItem = item.charAt(0) + "'";
						} else {
							cleanItem = null;
						}
					}
				}
			}
			lastItem = cleanItem;
			if (cleanItem == item) {
				clean.add(cleanItem);
			} else {
				clean.removeLast();
				if (cleanItem != null) {
					clean.add(cleanItem);
				} else {
					if (clean.size() != 0) {
						lastItem = clean.getLast();
					} else {
						lastItem = " ";
					}
				}
			}
		}
		return clean;
	}
	
	public Cubie[][] getCube() {
		Cubie[][] cube = new Cubie[2][];
		cube[0] = new Cubie[8];
		cube[1] = new Cubie[12];
		
		int[][] cornerColours = new int[8][3];
		cornerColours[0] = new int[] {faceArray[2].squareArray[0].colour, faceArray[0].squareArray[2].colour, faceArray[1].squareArray[6].colour}; // ULB
		cornerColours[1] = new int[] {faceArray[2].squareArray[2].colour, faceArray[4].squareArray[0].colour, faceArray[1].squareArray[8].colour}; // URB
		cornerColours[2] = new int[] {faceArray[2].squareArray[8].colour, faceArray[4].squareArray[6].colour, faceArray[3].squareArray[2].colour}; // URF
		cornerColours[3] = new int[] {faceArray[2].squareArray[6].colour, faceArray[0].squareArray[8].colour, faceArray[3].squareArray[0].colour}; // ULF
		cornerColours[4] = new int[] {faceArray[5].squareArray[8].colour, faceArray[0].squareArray[6].colour, faceArray[3].squareArray[6].colour}; // DLF
		cornerColours[5] = new int[] {faceArray[5].squareArray[2].colour, faceArray[0].squareArray[0].colour, faceArray[1].squareArray[0].colour}; // DLB
		cornerColours[6] = new int[] {faceArray[5].squareArray[0].colour, faceArray[4].squareArray[2].colour, faceArray[1].squareArray[2].colour}; // DRB
		cornerColours[7] = new int[] {faceArray[5].squareArray[6].colour, faceArray[4].squareArray[8].colour, faceArray[3].squareArray[8].colour}; // DRF
		
		int i = 0;
		for (int[] corner : cornerColours) {
			cube[0][i] = new Cubie(getCornerPosition(Arrays.copyOf(corner, corner.length)), getCornerOrientation(Arrays.copyOf(corner, corner.length), i));
			i ++;
		}
		
		int[][] edgeColours = new int[12][2];
		edgeColours[0] = new int[] {faceArray[2].squareArray[1].colour, faceArray[1].squareArray[7].colour}; // UB
		edgeColours[1] = new int[] {faceArray[2].squareArray[5].colour, faceArray[4].squareArray[3].colour}; // UR
		edgeColours[2] = new int[] {faceArray[2].squareArray[7].colour, faceArray[3].squareArray[1].colour}; // UF
		edgeColours[3] = new int[] {faceArray[2].squareArray[3].colour, faceArray[0].squareArray[5].colour}; // UL
		edgeColours[4] = new int[] {faceArray[3].squareArray[5].colour, faceArray[4].squareArray[7].colour}; // FR
		edgeColours[5] = new int[] {faceArray[3].squareArray[3].colour, faceArray[0].squareArray[7].colour}; // FL
		edgeColours[6] = new int[] {faceArray[1].squareArray[3].colour, faceArray[0].squareArray[1].colour}; // BL
		edgeColours[7] = new int[] {faceArray[1].squareArray[5].colour, faceArray[4].squareArray[1].colour}; // BR
		edgeColours[8] = new int[] {faceArray[5].squareArray[7].colour, faceArray[3].squareArray[7].colour}; // DF
		edgeColours[9] = new int[] {faceArray[5].squareArray[5].colour, faceArray[0].squareArray[3].colour}; // DL
		edgeColours[10] = new int[] {faceArray[5].squareArray[1].colour, faceArray[1].squareArray[1].colour}; // DB
		edgeColours[11] = new int[] {faceArray[5].squareArray[3].colour, faceArray[4].squareArray[5].colour}; // DR
		
		i = 0;
		for (int[] edge : edgeColours) {
			cube[1][i] = new Cubie(getEdgePosition(Arrays.copyOf(edge, edge.length)), getEdgeOrientation(Arrays.copyOf(edge, edge.length), i));
			i ++;
		}
		
		return cube;
	}
	
	private byte getCornerPosition(int[] corner) {
		Arrays.sort(corner);
		for (byte i = 0; i < 8; i++) {
			if (Arrays.equals(corner, orderedCorners[i])) {
				return i;
			}
		}
		
		return 9;
	}
	
	private byte getCornerOrientation(int[] corner, int pos) {
		if (corner[0] == 2 || corner[0] == 5) {
			return 0;
		}
				
		int homePos = getCornerPosition(Arrays.copyOf(corner, 3));
		
		if (pos < 4) {
			if (homePos == 0) {
				if (corner[0] == 1) {
					return 1;
				} else {
					return 2;
				}
			} else if (homePos == 1) {
				if (corner[0] == 1) {
					return 2;
				} else {
					return 1;
				}
			} else if (homePos == 2) {
				if (corner[0] == 3) {
					return 1;
				} else {
					return 2;
				}
			} else if (homePos == 3) {
				if (corner[0] == 3) {
					return 2;
				} else {
					return 1;
				}
			} else if (homePos == 4) {
				if (corner[0] == 0) {
					return 2;
				} else {
					return 1;
				}
			} else if (homePos == 5) {
				if (corner[0] == 1) {
					return 2;
				} else {
					return 1;
				}
			} else if (homePos == 6) {
				if (corner[0] == 1) {
					return 1;
				} else {
					return 2;
				}
			} else {
				if (corner[0] == 3) {
					return 2;
				} else {
					return 1;
				}
			}
		} else {
			if (homePos == 0) {
				if (corner[0] == 1) {
					return 1;
				} else {
					return 2;
				}
			} else if (homePos == 1) {
				if (corner[0] == 1) {
					return 2;
				} else {
					return 1;
				}
			} else if (homePos == 2) {
				if (corner[0] == 3) {
					return 1;
				} else {
					return 2;
				}
			} else if (homePos == 3) {
				if (corner[0] == 3) {
					return 2;
				} else {
					return 1;
				}
			} else if (homePos == 4) {
				if (corner[0] == 3) {
					return 1;
				} else {
					return 2;
				}
			} else if (homePos == 5) {
				if (corner[0] == 1) {
					return 2;
				} else {
					return 1;
				}
			} else if (homePos == 6) {
				if (corner[0] == 1) {
					return 1;
				} else {
					return 2;
				}
			} else {
				if (corner[0] == 3) {
					return 2;
				} else {
					return 1;
				}
			}
		}
		
	}
	
	private byte getEdgePosition(int[] edge) {
		Arrays.sort(edge);
		for (byte i = 0; i < 12; i++) {
			if (Arrays.equals(edge, orderedEdges[i])) {
				return i;
			}
		}
		
		return 12;
	}
	
	private byte getEdgeOrientation(int[] edge, int pos) {
		int homePos = getEdgePosition(Arrays.copyOf(edge, 2));
		
		if (homePos == 0) {
			if (edge[0] == 1) {
				return 1;
			} else {
				return 0;
			}
		} else if (homePos == 1) {
			if (edge[0] == 4) {
				return 1;
			} else {
				return 0;
			}
		} else if (homePos == 2) {
			if (edge[0] == 3) {
				return 1;
			} else {
				return 0;
			}
		} else if (homePos == 3) {
			if (edge[0] == 0) {
				return 1;
			} else {
				return 0;
			}
		} else if (homePos == 4) {
			if (edge[0] == 4) {
				return 1;
			} else {
				return 0;
			}
		} else if (homePos == 5) {
			if (edge[0] == 0) {
				return 1;
			} else {
				return 0;
			}
		} else if (homePos == 6) {
			if (edge[0] == 0) {
				return 1;
			} else {
				return 0;
			}
		} else if (homePos == 7) {
			if (edge[0] == 4) {
				return 1;
			} else {
				return 0;
			}
		} else if (homePos == 8) {
			if (edge[0] == 3) {
				return 1;
			} else {
				return 0;
			}
		} else if (homePos == 9) {
			if (edge[0] == 0) {
				return 1;
			} else {
				return 0;
			}
		} else if (homePos == 10) {
			if (edge[0] == 1) {
				return 1;
			} else {
				return 0;
			}
		} else {
			if (edge[0] == 4) {
				return 1;
			} else {
				return 0;
			}
		}
	}
	
	class Square {
		int x;
		int y;
		int sideLength;
		int colour;
		boolean centre;
		
		Square(int x, int y, int sideLength, int colour, boolean centre) {
			this.x = x;
			this.y = y;
			this.sideLength = sideLength;
			this.colour = colour;
			this.centre = centre;
		}
		
		void show() {
			fill(colours[colour][0], colours[colour][1], colours[colour][2]);
			push();
			translate(x, y);
			square(0, 0, sideLength);
			pop();
		}
	}
	
	class Face {
		int x;
		int y;
		Square[] squareArray;
		
		Face(int x, int y, Square[] squareArray) {
			this.x = x;
			this.y = y;
			this.squareArray = squareArray;
		}
		
		void show() {
			for (Square s : squareArray) {
				push();
				translate(x, y);
				s.show();
				pop();
			}
		}
	}

}
