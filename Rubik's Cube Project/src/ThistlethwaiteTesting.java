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

public class ThistlethwaiteTesting {
	private Cube c;
	private temp2 ci;
	private temp3 tmp;
	private temp5 tmp5;
	private temp6 tmp6;
	
	public ThistlethwaiteTesting(Cube c, temp2 ci, temp3 tmp, temp5 tmp5, temp6 tmp6) {
		this.c = c;
		this.ci = ci;
		this.tmp = tmp;
		this.tmp5 = tmp5;
		this.tmp6 = tmp6;
	}
	
	public LinkedList<String> solve() throws FileNotFoundException, IOException, ClassNotFoundException {
		LinkedList<String> solve = new LinkedList<String>();
		
		Gson gson = new Gson();
		
		String[][] data1 = gson.fromJson(new FileReader("thistlethwaiteG0-G1.json"), String[][].class);
		String[] phase1Solve = data1[this.ci.getEdgeOrientation()];
		for (String move : phase1Solve) {
			c.doMove(move);
			ci.doMove(move);
			tmp.doMove(move);
			tmp5.doMove(move);
			tmp6.doMove(move);
			solve.add(move);
		}
		
		//System.out.println(solve.toString());
		
		String[][] data2 = gson.fromJson(new FileReader("thistlethwaiteG1-G2.json"), String[][].class);
		String[] phase2Solve = data2[this.tmp.getIndex()];
		
		for (String move : phase2Solve) {
			c.doMove(move);
			ci.doMove(move);
			tmp.doMove(move);
			tmp5.doMove(move);
			tmp6.doMove(move);
			solve.add(move);
		}
		
		//System.out.println(solve.toString());
		
		String[][] data3 = gson.fromJson(new FileReader("thistlethwaiteG2-G3.json"), String[][].class);
		String[] phase3Solve = data3[this.tmp5.getIndex()];
		
		for (String move : phase3Solve) {
			c.doMove(move);
			tmp6.doMove(move);
			solve.add(move);
		}
		
		// System.out.println(solve.toString());
		
		String[][] data4 = gson.fromJson(new FileReader("thistlethwaiteG3-G4.json"), String[][].class);
		String[] phase4Solve = data4[this.tmp6.getIndex()];
		
		for (String move : phase4Solve) {
			solve.add(move);
		}
		
		
		
		System.out.println(solve.toString());
		System.out.println(solve.size());
		System.out.println();
		
		return solve;
	}
}
