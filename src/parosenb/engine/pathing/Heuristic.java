package parosenb.engine.pathing;

import java.util.Comparator;

public class Heuristic <T extends Node<T>> implements Comparator<T> {
	T goal;
	public Heuristic(T goal){
		this.goal = goal;
	}
	public int compare(T arg0, T arg1) {
		if (arg0.distance + arg0.getHeuristic(goal) < arg1.distance + arg1.getHeuristic(goal) ){
			return -1;
		}
		if (arg0.distance + arg0.getHeuristic(goal) > arg1.distance + arg1.getHeuristic(goal) ){
			return 1;
		} else {
			return 0;
		}
	}
}