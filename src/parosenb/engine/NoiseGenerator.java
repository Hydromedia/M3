package parosenb.engine;

import java.util.Random;

import cs1971.Vec2f;
import cs1971.Vec2i;

public class NoiseGenerator {
	 int _baseSeed;
	 int _currentSeed;
	 
	 public NoiseGenerator (int seed) {
		 this._baseSeed = seed;
	 }
	 
	 
	 Random _rand = new Random();
	 // feel free to make your own noise function
	 private float noise(Vec2i vec) {
		 _rand.setSeed(_currentSeed + vec.hashCode());
		 for (int i = 0; i < (int) (_rand.nextFloat()*10); i++){
			 _rand.nextFloat();
		 }
		 return _rand.nextFloat();
	 }
	 
	 private float interpolate(float a, float b, float x) {
		 return a * (1 - smooth(x)) + (b * smooth(x));
	 }
	 
	 private float smooth(float x) {
		 return (float) ((1 - Math.cos(x*Math.PI)) * .5);
	 }
	 
	 // returns a weighted average of the 9 points around the Vec2i v
	 float smoothNoise(Vec2i vec) {
		// four corners, each multiplied by 1/16
		float corners = (noise(new Vec2i(vec.x-1, vec.y-1)) + noise(new Vec2i(vec.x+1, vec.y-1))
						+ noise(new Vec2i(vec.x-1, vec.y+1)) + noise(new Vec2i(vec.x+1, vec.y+1))) / 16;
		// four sides, each multiplied by 1/8
		float sides = (noise(new Vec2i(vec.x-1, vec.y)) + noise(new Vec2i(vec.x+1, vec.y))
					+ noise(new Vec2i(vec.x, vec.y-1)) + noise(new Vec2i(vec.x, vec.y+1))) / 8;
		// center, multiplied by 1/4
		float center = noise(new Vec2i(vec.x, vec.y)) / 4;
		return center + sides + corners;
	}
	 
	// returns an value interpolated between the four corners surrounding the Vec2f v
	 float interpolatedNoise(Vec2f vec){
		  int integer_x = (int) Math.floor(vec.x);
		  float fractional_x = vec.x - integer_x;
		  int integer_y = (int) Math.floor(vec.y);
		  float fractional_y = vec.y - integer_y;
		  // the four integer corners surrounding the float (x,y) pair
		  float v1 = smoothNoise(new Vec2i(integer_x, integer_y));
		  float v2 = smoothNoise(new Vec2i(integer_x + 1, integer_y));
		  float v3 = smoothNoise(new Vec2i(integer_x, integer_y + 1));
		  float v4 = smoothNoise(new Vec2i(integer_x + 1, integer_y + 1));
		  float i1 = interpolate(v1, v2, fractional_x);
		  float i2 = interpolate(v3, v4, fractional_x);
		  return interpolate(i1, i2, fractional_y);
	 }
	 
	// returns a value between 0 and 1
	// freq is the initial frequency of the largest “hill”
	// persistence is between 0 and .5, determining how large each amplitude will be in relation to the previous one
	public float valueNoise(Vec2f vec, float freq, float persistence, int num_octaves) {
		float total = 0;
		float amp = (float) .5;
		for(int i = 0; i < num_octaves; i++) {
			_currentSeed = _baseSeed + i; // so we use a modified seed for each octave
			total = total + interpolatedNoise(new Vec2f(vec.x * freq, vec.y * freq)) * amp;
			amp = amp * persistence;
			freq = freq * 2;
		}
		return total;
	}
	
}

