
import processing.core.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import peasy.*;

public class CubeGraphics3D extends PApplet {
	// CubeGraphics3D handles the 3D GUI
	
	PeasyCam cam;
	GraphicalCubie[] cube = new GraphicalCubie[27];
	
	PFont f;
	
	boolean solving = false;
	boolean displayingSolve = false;
	boolean nextMove = false;
	
	int shift = 1;
	boolean tab = false;

	Move m = null;
	
	LinkedList<String> movesDone = new LinkedList<String>();
	LinkedList<String> movesToDo = new LinkedList<String>();
	
	String[][] data1;
	String[][] data2;
	String[][] data3;
	String[][] data4;
	
	public CubeGraphics3D() throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		// Loads the four Thistlethwaite lookup tables, this is done whenever an instance of
		// CubeGraphics3D is created so that it happens only once, which is faster than loading
		// the tables every time the user wants to solve the cube.
		
		Gson gson = new Gson();
		data1 = gson.fromJson(new FileReader("thistlethwaiteG0-G1.json"), String[][].class);
		data2 = gson.fromJson(new FileReader("thistlethwaiteG1-G2.json"), String[][].class);
		data3 = gson.fromJson(new FileReader("thistlethwaiteG2-G3.json"), String[][].class);
		data4 = gson.fromJson(new FileReader("thistlethwaiteG3-G4.json"), String[][].class);
	}
	
	private final static LinkedList<String> reverseAndClean(LinkedList<String> lls_) {
		// The input lls_ is a linked list containing moves written in Rubiks cube notation.
		// The output is the logical inverse of the inputted sequence of moves, i.e., performing
		// the input then the output in succession will leave the cube in its starting state.
		
		LinkedList<String> lls = new LinkedList<String>();
		lls.addAll(lls_);
		LinkedList<String> out = new LinkedList<String>();
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
			case "r":
				temp = "r'";
				break;
			case "l":
				temp = "l'";
				break;
			case "u":
				temp = "u'";
				break;
			case "d":
				temp = "d'";
				break;
			case "f":
				temp = "f'";
				break;
			case "b":
				temp = "b'";
				break;
			case "r'":
				temp = "r";
				break;
			case "l'":
				temp = "l";
				break;
			case "u'":
				temp = "u";
				break;
			case "d'":
				temp = "d";
				break;
			case "f'":
				temp = "f";
				break;
			case "b'":
				temp = "b";
				break;
			case "r2":
				temp = "r2";
				break;
			case "l2":
				temp = "l2";
				break;
			case "u2":
				temp = "u2";
				break;
			case "d2":
				temp = "d2";
				break;
			case "f2":
				temp = "f2";
				break;
			case "b2":
				temp = "b2";
				break;
			case "e":
				temp = "e'";
				break;
			case "e'":
				temp = "e";
				break;
			case "e2":
				temp = "e2";
				break;
			case "m":
				temp = "m'";
				break;
			case "m'":
				temp = "m";
				break;
			case "m2":
				temp = "m2";
				break;
			case "s":
				temp = "s'";
				break;
			case "s'":
				temp = "s";
				break;
			case "s2":
				temp = "s2";
				break;
			}
			out.add(temp);
		}
		return out;
	}
	
	public LinkedList<String> solve(CubeIndexModel ci) {
		// Input is an instance of CubeIndexModel that the user wishes to be solved.
		// The solving works by using the calculated indices to access the lookup tables
		// loaded when CubeGraphics3D is started.
		// Output is a sequence of moves that will return the input to its solved state.
		
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
		
		return solve;
	}
	
	private static LinkedList<String> cleanSolve(LinkedList<String> solve) {
		// Input is a linked list of Rubiks cube notation moves.
		// cleanSolve gets rid of successive moves that negate one another,
		// e.g. "R" and "R'", cleanSolve also adds successive moves that make
		// double moves, e.g. "R" and "R" make "R2".
		
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
	
	public static void main(String[] args) {
		PApplet.main("CubeGraphics3D");
	}
	
	public void settings() {
		this.size(600, 600, P3D);
	}
	
	public void setup() {
		// Initialises the 27 individual cubies to be rendered and stores
		// them in 'cube'.
		
		f = createFont("Arial", 16, true);

		cam = new PeasyCam(this, 600);
		
		int index = 0;
		for (int x=-1; x<2; x++) {
			for (int y=-1; y<2; y++) {
				for (int z=-1; z<2; z++) {
					PMatrix3D matrix = new PMatrix3D();
					matrix.translate(x, y, z);
					cube[index] = new GraphicalCubie(matrix, x, y, z);
					index++;
				}
			}
		}
	}
	
	void turn(int index, int dir, int xyz) {
		// Turns the face at 'index' in the direction 'dir' in the 'xyz' plane
		
		for (int i = 0; i < cube.length; i++) {
			if ((cube[i].x == index && xyz == 0) || (cube[i].y == index && xyz == 1) || (cube[i].z == index && xyz == 2)) {
				PMatrix2D matrix = new PMatrix2D();
				matrix.rotate(HALF_PI*dir);
				switch (xyz) {
				case 0:
					matrix.translate(cube[i].y, cube[i].z);
					cube[i].update(cube[i].x, round(matrix.m02), round(matrix.m12));
					break;
				case 1:
					matrix.translate(cube[i].x, cube[i].z);
					cube[i].update(round(matrix.m02), cube[i].y, round(matrix.m12));
					break;
				case 2:
					matrix.translate(cube[i].x, cube[i].y);
					cube[i].update(round(matrix.m02), round(matrix.m12), cube[i].z);
					break;
				}
				cube[i].turnFaces(dir, xyz);
			}
		}
	}

	public void draw() {
		// Processing runs this function every frame, it handles moves that need to be animated and
		// starts moves in the move queue 'movesToDo' if no other moves are being done at the moment.
		
		background(50);
		if (m != null) {
			m.update();
			if (solving && !m.animating) {
				if (!movesToDo.isEmpty()) {
					String move = movesToDo.getFirst();
					if (displayingSolve) {
						textFont(f, 60);
						fill(150, 0, 0);
						if (move.contains("2"))
							text(move.charAt(0), width/4, height/4);
						else
							text(move, width/4, height/4);
					}
					if ((nextMove && displayingSolve) || !displayingSolve) {
						movesToDo.removeFirst();
						LinkedList<String> temp = new LinkedList<String>();
						
						switch (move) {
						case "R":
							shift = 1;
							m = new Move(1, 0, 0, 1);
							m.start();
							break;
						case "L":
							shift = 1;
							m = new Move(-1, 0, 0, -1);
							m.start();
							break;
						case "F":
							shift = 1;
							m = new Move(0, 0, 1, 1);
							m.start();
							break;
						case "B":
							shift = 1;
							m = new Move(0, 0, -1, -1);
							m.start();
							break;
						case "U":
							shift = 1;
							m = new Move(0, -1, 0, 1);
							m.start();
							break;
						case "D":
							shift = 1;
							m = new Move(0, 1, 0, -1);
							m.start();
							break;
						case "R'":
							shift = -1;
							m = new Move(1, 0, 0, 1);
							m.start();
							break;
						case "L'":
							shift = -1;
							m = new Move(-1, 0, 0, -1);
							m.start();
							break;
						case "F'":
							shift = -1;
							m = new Move(0, 0, 1, 1);
							m.start();
							break;
						case "B'":
							shift = -1;
							m = new Move(0, 0, -1, -1);
							m.start();
							break;
						case "U'":
							shift = -1;
							m = new Move(0, -1, 0, 1);
							m.start();
							break;
						case "D'":
							shift = -1;
							m = new Move(0, 1, 0, -1);
							m.start();
							break;
						case "R2":
							shift = 1;
							m = new Move(1, 0, 0, 1);
							m.start();
							temp.add("R");
							temp.addAll(movesToDo);
							movesToDo = temp;
							break;
						case "L2":
							shift = 1;
							m = new Move(-1, 0, 0, -1);
							m.start();
							temp.add("L");
							temp.addAll(movesToDo);
							movesToDo = temp;
							break;
						case "F2":
							shift = 1;
							m = new Move(0, 0, 1, 1);
							m.start();
							temp.add("F");
							temp.addAll(movesToDo);
							movesToDo = temp;
							break;
						case "B2":
							shift = 1;
							m = new Move(0, 0, -1, -1);
							m.start();
							temp.add("B");
							temp.addAll(movesToDo);
							movesToDo = temp;
							break;
						case "U2":
							shift = 1;
							m = new Move(0, -1, 0, 1);
							m.start();
							temp.add("U");
							temp.addAll(movesToDo);
							movesToDo = temp;
							break;
						case "D2":
							shift = 1;
							m = new Move(0, 1, 0, -1);
							m.start();
							temp.add("D");
							temp.addAll(movesToDo);
							movesToDo = temp;
							break;
						case "r":
							shift = 1;
							tab = true;
							m = new Move(1, 0, 0, 1);
							m.start();
							break;
						case "l":
							shift = 1;
							tab = true;
							m = new Move(-1, 0, 0, -1);
							m.start();
							break;
						case "f":
							shift = 1;
							tab = true;
							m = new Move(0, 0, 1, 1);
							m.start();
							break;
						case "b":
							shift = 1;
							tab = true;
							m = new Move(0, 0, -1, -1);
							m.start();
							break;
						case "u":
							shift = 1;
							tab = true;
							m = new Move(0, -1, 0, 1);
							m.start();
							break;
						case "d":
							shift = 1;
							tab = true;
							m = new Move(0, 1, 0, -1);
							m.start();
							break;
						case "r'":
							shift = -1;
							tab = true;
							m = new Move(1, 0, 0, 1);
							m.start();
							break;
						case "l'":
							shift = -1;
							tab = true;
							m = new Move(-1, 0, 0, -1);
							m.start();
							break;
						case "f'":
							shift = -1;
							tab = true;
							m = new Move(0, 0, 1, 1);
							m.start();
							break;
						case "b'":
							shift = -1;
							tab = true;
							m = new Move(0, 0, -1, -1);
							m.start();
							break;
						case "u'":
							shift = -1;
							tab = true;
							m = new Move(0, -1, 0, 1);
							m.start();
							break;
						case "d'":
							shift = -1;
							tab = true;
							m = new Move(0, 1, 0, -1);
							m.start();
							break;
						case "r2":
							shift = 1;
							tab = true;
							m = new Move(1, 0, 0, 1);
							m.start();
							temp.add("r");
							temp.addAll(movesToDo);
							movesToDo = temp;
							break;
						case "l2":
							shift = 1;
							tab = true;
							m = new Move(-1, 0, 0, -1);
							m.start();
							temp.add("l");
							temp.addAll(movesToDo);
							movesToDo = temp;
							break;
						case "f2":
							shift = 1;
							tab = true;
							m = new Move(0, 0, 1, 1);
							m.start();
							temp.add("f");
							temp.addAll(movesToDo);
							movesToDo = temp;
							break;
						case "b2":
							shift = 1;
							tab = true;
							m = new Move(0, 0, -1, -1);
							m.start();
							temp.add("b");
							temp.addAll(movesToDo);
							movesToDo = temp;
							break;
						case "u2":
							shift = 1;
							tab = true;
							m = new Move(0, -1, 0, 1);
							m.start();
							temp.add("u");
							temp.addAll(movesToDo);
							movesToDo = temp;
							break;
						case "d2":
							shift = 1;
							tab = true;
							m = new Move(0, 1, 0, -1);
							m.start();
							temp.add("d");
							temp.addAll(movesToDo);
							movesToDo = temp;
							break;
						case "e":
							shift = 1;
							m = new Move(0, 2, 0, -1);
							m.start();
							break;
						case "m":
							shift = 1;
							m = new Move(2, 0, 0, -1);
							m.start();
							break;
						case "s":
							shift = 1;
							m = new Move(0, 0, 2, 1);
							m.start();
							break;
						case "e'":
							shift = -1;
							m = new Move(0, 2, 0, -1);
							m.start();
							break;
						case "m'":
							shift = -1;
							m = new Move(2, 0, 0, -1);
							m.start();
							break;
						case "s'":
							shift = -1;
							m = new Move(0, 0, 2, 1);
							m.start();
							break;
						case "e2":
							shift = 1;
							m = new Move(0, 2, 0, -1);
							m.start();
							temp.add("e");
							temp.addAll(movesToDo);
							movesToDo = temp;
							break;
						case "m2":
							shift = 1;
							m = new Move(2, 0, 0, -1);
							m.start();
							temp.add("m");
							temp.addAll(movesToDo);
							movesToDo = temp;
							break;
						case "s2":
							shift = 1;
							m = new Move(0, 0, 2, 1);
							m.start();
							temp.add("s");
							temp.addAll(movesToDo);
							movesToDo = temp;
							break;
						}
						nextMove = false;
					}
				} else {
					solving = false;
					displayingSolve = false;
					nextMove = false;
				}
			}
		}

		scale(60);
		for (int i=0; i<cube.length; i++) {
			push();
			if (m != null) {
				if ((cube[i].z != 0 && cube[i].z == m.z)
						|| (tab && cube[i].z == 0 && m.z != 0)
						|| (cube[i].z == 0 && abs(m.z) == 2)) {
					rotateZ(m.angle);
				} else if ((cube[i].x != 0 && cube[i].x == m.x)
						|| (tab && cube[i].x == 0 && m.x != 0)
						|| (cube[i].x == 0 && abs(m.x) == 2)) {
					rotateX(m.angle);
				} else if ((cube[i].y != 0 && cube[i].y == m.y)
						|| (tab && cube[i].y == 0 && m.y != 0)
						|| (cube[i].y == 0 && abs(m.y) == 2)) {
					rotateY(-m.angle);
				}
			}
			cube[i].show();
			pop();
		}
	}
	
	public void keyPressed() {
		// Runs whenever a key is pressed, checks nothing else is being animated, then addresses the user's key press.
		
		if ((m == null || !m.animating) && (!solving || displayingSolve)) {
			if (key == CODED) {
				if (keyCode == SHIFT)
					shift *= -1;
			} else {
				String moveString = "";
				
				switch (key) {
				case ' ':
					if (displayingSolve) {
						nextMove = true;
						break;
					}
					CubeIndexModel ci = new CubeIndexModel();
					boolean bad = false;
					for (String move : movesDone) {
						if (Character.isLowerCase(move.charAt(0))) {
							System.out.println("Error - solver cannot be used with middle slice moves.");
							bad = true;
							break;
						}
						ci.doMove(move);
					}
					if (!bad) {
						movesDone = cleanSolve(movesDone);
						System.out.println(movesDone.toString());
						System.out.println(movesDone.size());
						tab = false;
						movesToDo = solve(ci);
						movesDone = new LinkedList<String>();
						if (movesToDo.size() > 0) {
							solving = true;
							displayingSolve = true;
							nextMove = true;
						}
					} else {
						movesToDo = reverseAndClean(movesDone);
						solving = true;
						movesDone = new LinkedList<String>();
					}
					break;
				case TAB:
					tab ^= true;
					break;
				case 'f':
					m = new Move(0, 0, 1, 1);
					m.start();
					if (tab)
						moveString += "f";
					else
						moveString += "F";
					break;
				case 'b':
					m = new Move(0, 0, -1, -1);
					m.start();
					if (tab)
						moveString += "b";
					else
						moveString += "B";
					break;
				case 'u':
					m = new Move(0, -1, 0, 1);
					m.start();
					if (tab)
						moveString += "u";
					else
						moveString += "U";
					break;
				case 'd':
					m = new Move(0, 1, 0, -1);
					m.start();
					if (tab)
						moveString += "d";
					else
						moveString += "D";
					break;
				case 'l':
					m = new Move(-1, 0, 0, -1);
					m.start();
					if (tab)
						moveString += "l";
					else
						moveString += "L";
					break;
				case 'r':
					m = new Move(1, 0, 0, 1);
					m.start();
					if (tab)
						moveString += "r";
					else
						moveString += "R";
					break;
				case 'm':
					m = new Move(2, 0, 0, -1);
					m.start();
					moveString += "m";
					break;
				case 's':
					m = new Move(0, 0, 2, 1);
					m.start();
					moveString += "s";
					break;
				case 'e':
					m = new Move(0, 2, 0, -1);
					m.start();
					moveString += "e";
					break;
				}
				if (!moveString.equals("")) {
					if (shift == -1)
						moveString += "'";
					movesDone.add(moveString);
				}
			}
		}
	}
	
	class GraphicalCubie {
		// One of the 27 little cubes that make up the Rubiks cube, each one is coloured on all faces 
		
		PMatrix3D matrix;
		int x = 0;
		int y = 0;
		int z = 0;
		Face[] faces = new Face[6];
		
		GraphicalCubie(PMatrix3D m, int x, int y, int z) {
			this.matrix = m;
			this.x = x;
			this.y = y;
			this.z = z;
			
			int[] back = new int[] {0, 0, 255};
			int[] front = new int[] {0, 255, 0};
			int[] up = new int[] {255, 255, 255};
			int[] down = new int[] {255, 255, 0};
			int[] right = new int[] {255, 165, 0};
			int[] left = new int[] {255, 0, 0};
			
			if (x == -1) {
				right = new int[] {0, 0, 0};
			} else if (x == 0) {
				right = new int[] {0, 0, 0};
				left = new int[] {0, 0, 0};
			} else {
				left = new int[] {0, 0, 0};
			}
			
			if (y == -1) {
				up = new int[] {0, 0, 0};
			} else if (y == 0) {
				down = new int[] {0, 0, 0};
				up = new int[] {0, 0, 0};
			} else {
				down = new int[] {0, 0, 0};
			}
			
			if (z == -1) {
				front = new int[] {0, 0, 0};
			} else if (z == 0) {
				front = new int[] {0, 0, 0};
				back = new int[] {0, 0, 0};
			} else {
				back = new int[] {0, 0, 0};
			}
			
			faces[0] = new Face(new PVector(0, 0, -1), back);
			faces[1] = new Face(new PVector(0, 0, 1), front);
			faces[2] = new Face(new PVector(0, 1, 0), up);
			faces[3] = new Face(new PVector(0, -1, 0), down);
			faces[4] = new Face(new PVector(1, 0, 0), right);
			faces[5] = new Face(new PVector(-1, 0, 0), left);
		}
		
		void update(int x, int y, int z) {
			matrix.reset();
			matrix.translate(x, y, z);
			this.x = x;
			this.y = y;
			this.z = z;
		}
		
		void turnFaces(int dir, int xyz) {
			for (Face f : faces) {
					f.turn(HALF_PI*dir, xyz);
			}
		}
		
		void show() {
			noFill();
			stroke(0);
			strokeWeight((float) 0.1);
			pushMatrix();
			applyMatrix(matrix);
			box(1);
			for (Face f : faces)
				f.show();
			popMatrix();
		}
	}
	
	class Face {
		// An individual face on a cubie
		
		PVector normal;
		int[] c;
		
		Face(PVector normal, int[] c) {
			this.normal = normal;
			this.c = c;
		}
		
		void turn(float angle, int xyz) {
			PVector v2 = new PVector();
			switch (xyz) {
			case 0:
				v2.y = round(normal.y * cos(angle) - normal.z * sin(angle));
				v2.z = round(normal.y * sin(angle) + normal.z * cos(angle));
				v2.x = round(normal.x);
				break;
			case 1:
				v2.x = round(normal.x * cos(angle) - normal.z * sin(angle));
				v2.z = round(normal.x * sin(angle) + normal.z * cos(angle));
				v2.y = round(normal.y);
				break;
			case 2:
				v2.x = round(normal.x * cos(angle) - normal.y * sin(angle));
				v2.y = round(normal.x * sin(angle) + normal.y * cos(angle));
				v2.z = round(normal.z);
				break;
			}
			normal = v2;
		}
		
		void show() {
			fill(c[0], c[1], c[2]);
			rectMode(CENTER);
			noStroke();
			pushMatrix();
			translate((float)0.5*normal.x, (float)0.5*normal.y, (float)0.5*normal.z);
			if (abs(normal.x) > 0) {
				rotateY(HALF_PI);
			} else if (abs(normal.y) > 0) {
				rotateX(HALF_PI);
			}
			square(0, 0, 1);
			popMatrix();
		}
	}
	
	class Move {
		// A move to be animated
		
		float angle = 0;
		int x = 0;
		int y = 0;
		int z = 0;
		boolean animating = false;
		int shiftCoeff;
		
		Move(int x, int y, int z, int shiftCoeff) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.shiftCoeff = shiftCoeff;
		}
		
		void start() {
			animating = true;
		}
		
		void update() {
			if (animating) {
				angle += shiftCoeff * shift * .15;
				if (abs(angle) > HALF_PI) {
					angle = 0;
					animating = false;
					if (abs(z) > 0) {
						if (abs(z) > 1) {
							turn(0, shiftCoeff*shift, 2);
						} else {
							turn(z, shiftCoeff*shift, 2);
							if (tab)
								turn(0, shiftCoeff*shift, 2);
						}
					} else if (abs(x) > 0) {
						if (abs(x) > 1) {
							turn(0, shiftCoeff*shift, 0);
						} else {
							turn(x, shiftCoeff*shift, 0);
							if (tab)
								turn(0, shiftCoeff*shift, 0);
						}
					} else {
						if (abs(y) > 1) {
							turn(0, shiftCoeff*shift, 1);
						} else {
							turn(y, shiftCoeff*shift, 1);
							if (tab)
								turn(0, shiftCoeff*shift, 1);
						}
					}
				}
			}
		}
	}

}
