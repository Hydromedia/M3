package parosenb.engine;

import java.awt.Graphics2D;
import java.util.ArrayList;

import parosenb.engine.Spriter.SpriteLoader;
import cs1971.Vec2f;
import cs1971.Vec2i;

public class Animator {
	private float counter;
	private float animationTime;
	
	public void setAnimationTime(float animationTime){
		this.animationTime = animationTime;
	}
	
	public Animator(float animationTime){
		counter = 0;
		this.animationTime = animationTime;
	}
	
	public void onTick(long nanosSincePreviousTick){
		float milliSecondsSincePreviousTick = nanosSincePreviousTick / 1000000;
		counter = counter + ( (int) milliSecondsSincePreviousTick);
		if (counter >= animationTime) {
			counter = 0;
		}
	}
	public void startCounter() {
		counter = 0;
	}
	public void onDraw(Graphics2D g, Vec2f destPosition, Vec2f destSize, ArrayList<Vec2i> spritePositions, int spriteSheetsIndex){
		//int casting always rounds down, so we don't need Math.floor
		SpriteLoader.drawImage(g, destPosition, destSize, spritePositions.get((int) ((counter / animationTime) * spritePositions.size())), spriteSheetsIndex);
	}
}
