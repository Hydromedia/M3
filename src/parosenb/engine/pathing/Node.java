package parosenb.engine.pathing;

import java.util.ArrayList;

public abstract class Node <N extends Node<N>> {
	int distance = Integer.MAX_VALUE;
	N previous = null;
	
	public abstract int getHeuristic(N goal);
	public abstract ArrayList<N> getNeighbors();
}