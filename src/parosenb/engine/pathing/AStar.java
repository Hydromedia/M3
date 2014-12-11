package parosenb.engine.pathing;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;

import cs1971.Vec2i;

public class AStar <T extends Node<T>> {
	private Comparator<T> comparator;
	private int worldSize;
	
	public AStar(int worldSize){
		this.worldSize = worldSize;
	}
	

	
	public ArrayList<T> movementPath(T start, T goal){
		this.comparator = new Heuristic<T>(goal);
		start.distance = 0; 
		start.previous = null;
		PriorityQueue<T> pq = new PriorityQueue<T>(11, comparator);
		pq.add(start);
		
		T curr;
		HashSet<T> visitedSet = new HashSet<T>();
		while((!pq.isEmpty()) && (pq.peek() != goal)){
		   curr = pq.remove();
		   visitedSet.add(curr);
		   int count = 0;
		   for(T n : curr.getNeighbors()){
			   visitedSet.add(n);
			   count++;
			   int cost = 1;
			   if ((curr.distance + cost) < n.distance) {
				   n.distance = curr.distance + cost;
				   n.previous = curr;
				   if (n == goal) {
					   //System.out.println("GOT TO THE GOAL");
				   }
				   pq.remove(n);
				   pq.add(n);
			   }
		   }
	   }
		
		ArrayList<T> ret = new ArrayList<T>();
		T node = goal;
		while (node.previous != null) {
			ret.add(node);
			node = node.previous;
			if (worldSize < ret.size()){
				node.previous = null;
			}
		}
		Iterator<T> itr = visitedSet.iterator();
		while(itr.hasNext()) {
	         T n = itr.next();
	         n.distance = Integer.MAX_VALUE;
	         n.previous = null;
	      }
		
		return ret;
	}
	public int heuristic (Vec2i curr, Vec2i goal){
		return Math.abs((curr.x + curr.y) - (goal.x + goal.y));
	}
	
}
