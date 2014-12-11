package parosenb.engine.pathing;


public interface Pathable<T> {
	public int getDistance();
	public void setDistance(int distance);
	public int getEstimate();
	public int getHeuristic();
	public void setEstimate(int estimate);
	public T getPrevious();
	public void setPrevious(T node);
	public boolean getPassable();
}
