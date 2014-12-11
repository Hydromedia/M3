package parosenb.engine.collision;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import cs1971.Vec2f;

public abstract class Shape extends CollisionObject {
	public abstract boolean contains(Vec2f point);
	public abstract ArrayList<java.awt.Shape> getShape(Vec2f position);
	public abstract void updatePosition(Vec2f position);
}