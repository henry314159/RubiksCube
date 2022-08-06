import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

// NB: my dead implementation takes forever, will try to make pruning tables

public class Thistlethwaite {
	private Cube c;
	private CubeIndexModel ci;
	private temp3 tmp;
	
	public Thistlethwaite(Cube c, CubeIndexModel ci, temp3 tmp) {
		this.c = c;
		this.ci = ci;
		this.tmp = tmp;
	}
	
	public LinkedList<String> solve() throws FileNotFoundException, IOException, ClassNotFoundException {
		LinkedList<String> solve = new LinkedList<String>();
		
		String[][] moves;
		try(FileInputStream in = new FileInputStream("thistlethwaiteG0-G1.txt");
				ObjectInputStream s = new ObjectInputStream(in)) {
				moves = (String[][]) s.readObject();
				s.close();
				in.close();
		}
		
		String[] phase1Solve = moves[this.ci.getEdgeOrientation()];
		System.out.println(Arrays.toString(phase1Solve));
		System.out.println(ci.getEdgeOrientation());
		
		for (String move : phase1Solve) {
			c.doMove(move);
			ci.doMove(move);
			tmp.doMove(move);
			solve.add(move);
		}
		
		System.out.println(ci.getEdgeOrientation());
		
		Gson gson = new Gson();
		String[][] data2 = gson.fromJson(new FileReader("thistlethwaiteG1-G2.json"), String[][].class);
		String[] phase2Solve = data2[this.tmp.getIndex()];
		for (String move : phase2Solve) {
			c.doMove(move);
			ci.doMove(move);
			tmp.doMove(move);
			solve.add(move);
		}
		
		IDDFS phase3 = new IDDFS(c, 3);
		
		for (String move : phase3.result) {
			c.doMove(move);
			solve.add(move);
		}
		
		IDDFS phase4 = new IDDFS(c, 4);
		
		for (String move : phase4.result) {
			c.doMove(move);
			solve.add(move);
		}
		
		IDDFS phase5 = new IDDFS(c, 5);
		
		for (String move : phase5.result) {
			solve.add(move);
		}
		
		return solve;
	}
}
