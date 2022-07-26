import java.util.LinkedList;

public class IDDFS {
	private static Goal goal;
	public LinkedList<String> result;
	
	public IDDFS(Cube cube, int goalState) {
		goal = new Goal(goalState);
		result = find(cube, goal.allowedMoves[goalState-1]);
	}

	private static LinkedList<String> find(Cube root, String[] allowedMoves) {
		LinkedList<String> movesSoFar = new LinkedList<String>();
		for (int i=0; i<100; i++) {
			Tuple3<Cube, Boolean, LinkedList<String>> t = dls(root, i, allowedMoves, movesSoFar);
			if (!(t.get0() == null)) {
				return t.get2();
			} else if (!t.get1()) {
				return null;
			}
		}
		return null;
	}
	
	private static Tuple3<Cube, Boolean, LinkedList<String>> dls(Cube cube, int depth, String[] allowedMoves, LinkedList<String> movesSoFar) {
		if (depth == 0) {
			if (goal.isSatisfied(cube)) {
				return new Tuple3<Cube, Boolean, LinkedList<String>>(cube, true, movesSoFar);
			} else {
				return new Tuple3<Cube, Boolean, LinkedList<String>>(null, true, movesSoFar);
			}
		} else if (depth > 0) {
			boolean anyRemaining = false;
			for (String move : allowedMoves) {
				Cube child = cube.doMoveReturn(move);
				movesSoFar.add(move);
				Tuple3<Cube, Boolean, LinkedList<String>> t = dls(child, depth-1, allowedMoves, movesSoFar);
				if (!(t.get0() == null)) {
					return new Tuple3<Cube, Boolean, LinkedList<String>>(t.get0(), true, movesSoFar);
				} else {
					movesSoFar.removeLast();
				}
				if (t.get1()) {
					anyRemaining = true;
				}
			}
			return new Tuple3<Cube, Boolean, LinkedList<String>>(null, anyRemaining, movesSoFar);
		}
		return null;
	}
}
