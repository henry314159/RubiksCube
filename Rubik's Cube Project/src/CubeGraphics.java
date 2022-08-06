import processing.core.*;

import java.io.IOException;
import java.util.LinkedList;
import peasy.*;

public class CubeGraphics extends PApplet {
	PeasyCam cam;
	Cubie[] cube = new Cubie[27];
	
	boolean solving = false;
	
	int shift = 1;
	boolean tab = false;

	Move m = null;
	
	LinkedList<String> movesDone = new LinkedList<String>();
	LinkedList<String> movesToDo = new LinkedList<String>();
	
	public static void main(String[] args) {
		PApplet.main("CubeGraphics");
	}
	
	public void settings() {
		this.size(600, 600, P3D);
	}
	
	public void setup() {
		cam = new PeasyCam(this, 600);
		
		int index = 0;
		for (int x=-1; x<2; x++) {
			for (int y=-1; y<2; y++) {
				for (int z=-1; z<2; z++) {
					PMatrix3D matrix = new PMatrix3D();
					matrix.translate(x, y, z);
					cube[index] = new Cubie(matrix, x, y ,z);
					index++;
				}
			}
		}
	}
	
	void turnZ(int index, int dir) {
		for (int i=0; i<cube.length; i++) {
			if (cube[i].z == index) {
				PMatrix2D matrix = new PMatrix2D();
				matrix.rotate(HALF_PI*dir);
				matrix.translate(cube[i].x, cube[i].y);
				cube[i].update(round(matrix.m02), round(matrix.m12), cube[i].z);
				cube[i].turnFacesZ(dir);
			}
		}
	}
	
	void turnY(int index, int dir) {
		for (int i=0; i<cube.length; i++) {
			if (cube[i].y == index) {
				PMatrix2D matrix = new PMatrix2D();
				matrix.rotate(HALF_PI*dir);
				matrix.translate(cube[i].x, cube[i].z);
				cube[i].update(round(matrix.m02), cube[i].y, round(matrix.m12));
				cube[i].turnFacesY(dir);
			}
		}
	}
	
	void turnX(int index, int dir) {
		for (int i=0; i<cube.length; i++) {
			if (cube[i].x == index) {
				PMatrix2D matrix = new PMatrix2D();
				matrix.rotate(HALF_PI*dir);
				matrix.translate(cube[i].y, cube[i].z);
				cube[i].update(cube[i].x, round(matrix.m02), round(matrix.m12));
				cube[i].turnFacesX(dir);
			}
		}
	}
	
	public void draw() {
		if (m != null)
			m.update();
		if (solving && !m.animating) {
			if (!movesToDo.isEmpty()) {
				String move = movesToDo.removeFirst();
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
				}
			} else
				solving = false;
		}
		
		background(200);
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
		if ((m == null || !m.animating) && !solving) {
			if (key == CODED) {
				if (keyCode == SHIFT)
					shift *= -1;
			} else {
				String moveString = "";
				
				switch (key) {
				case ' ':
					Cube c = new Cube();
					CubeIndexModel ci = new CubeIndexModel();
					temp3 tmp = new temp3();
					for (String move : movesDone) {
						c.doMove(move);
						ci.doMove(move);
						tmp.doMove(move);
					}
					tab = false;
					Thistlethwaite t = new Thistlethwaite(c, ci, tmp);
					try {
						movesToDo = t.solve();
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}
					movesDone = new LinkedList<String>();
					solving = true;
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
	
	class Cubie {
		PMatrix3D matrix;
		int x = 0;
		int y = 0;
		int z = 0;
		Face[] faces = new Face[6];
		
		Cubie(PMatrix3D m, int x, int y, int z) {
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
		
		void turnFacesX(int dir) {
			for (Face f : faces) {
				f.turnX(HALF_PI*dir);
			}
		}
		
		void turnFacesY(int dir) {
			for (Face f : faces) {
				f.turnY(HALF_PI*dir);
			}
		}
		
		void turnFacesZ(int dir) {
			for (Face f : faces) {
				f.turnZ(HALF_PI*dir);
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
		PVector normal;
		int[] c;
		
		Face(PVector normal, int[] c) {
			this.normal = normal;
			this.c = c;
		}
		
		void turnZ(float angle) {
			PVector v2 = new PVector();
			v2.x = round(normal.x * cos(angle) - normal.y * sin(angle));
			v2.y = round(normal.x * sin(angle) + normal.y * cos(angle));
			v2.z = round(normal.z);
			normal = v2;
		}
		
		void turnY(float angle) {
			PVector v2 = new PVector();
			v2.x = round(normal.x * cos(angle) - normal.z * sin(angle));
			v2.z = round(normal.x * sin(angle) + normal.z * cos(angle));
			v2.y = round(normal.y);
			normal = v2;
		}
		
		void turnX(float angle) {
			PVector v2 = new PVector();
			v2.y = round(normal.y * cos(angle) - normal.z * sin(angle));
			v2.z = round(normal.y * sin(angle) + normal.z * cos(angle));
			v2.x = round(normal.x);
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
							turnZ(0, shiftCoeff*shift);
						} else {
							turnZ(z, shiftCoeff*shift);
							if (tab)
								turnZ(0, shiftCoeff*shift);
						}
					} else if (abs(x) > 0) {
						if (abs(x) > 1) {
							turnX(0, shiftCoeff*shift);
						} else {
							turnX(x, shiftCoeff*shift);
							if (tab)
								turnX(0, shiftCoeff*shift);
						}
					} else {
						if (abs(y) > 1) {
							turnY(0, shiftCoeff*shift);
						} else {
							turnY(y, shiftCoeff*shift);
							if (tab)
								turnY(0, shiftCoeff*shift);
						}
					}
				}
			}
		}
	}

}
