
// NB: my dead implementation takes forever, will try to make pruning tables

public class Thistlethwaite {
	public Thistlethwaite(Cube c) {
		IDDFS phase1 = new IDDFS(c, 1);
		System.out.println(phase1.result.toString());
		
		for (String move : phase1.result) {
			c.doMove(move);
		}
		
		IDDFS phase2 = new IDDFS(c, 2);
		System.out.println(phase2.result.toString());
		
		for (String move : phase2.result) {
			c.doMove(move);
		}
		
		IDDFS phase3 = new IDDFS(c, 3);
		System.out.println(phase3.result.toString());
		
		for (String move : phase3.result) {
			c.doMove(move);
		}
		
		IDDFS phase4 = new IDDFS(c, 4);
		System.out.println(phase4.result.toString());
		
		for (String move : phase4.result) {
			c.doMove(move);
		}
		
		IDDFS phase5 = new IDDFS(c, 5);
		System.out.println(phase5.result.toString());
		
		System.out.println(phase1.result.size()+phase2.result.size()+phase3.result.size()+phase4.result.size()+phase5.result.size());
	}
}
