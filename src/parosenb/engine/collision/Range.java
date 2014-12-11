package parosenb.engine.collision;

public class Range {
	public final float min;
	public final float max;
	public Range (float min, float max){
		this.min = min;
		this.max = max;
	}
	
	@Override
	public String toString(){
		return "Max: " + max + ", Min: "  + min;
	}
	
	public static Float rangeMTV(Range a, Range b){
	 Float aRight = b.max - a.min;
	 Float aLeft = a.max - b.min;
	 if (aLeft < 0 || aRight < 0){
		 return null;
	 }
	 if (aRight < aLeft){
		 return aRight;
	 }
	 else {
		 return -aLeft;
	 }
	}
}
