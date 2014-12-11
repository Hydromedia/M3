package parosenb.m.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;

import cs1971.Vec2f;
import cs1971.Vec2i;
import parosenb.engine.PhysicsEntity;
import parosenb.engine.Viewport;
import parosenb.engine.World;
import parosenb.engine.Entity;
import parosenb.engine.collision.AAB;
import parosenb.engine.collision.Polygon;
import parosenb.engine.collision.Ray;
import parosenb.m.game.WinScreen;
import parosenb.m.game.CircleChar;

public class GameWorld extends World {
	public PlayerUnit playerUnit;
	public ArrayList<GameEntity> gameEntities = new ArrayList<GameEntity>();
	public ArrayList<GameEntity> gameToAdd = new ArrayList<GameEntity>();
	public ArrayList<GameEntity> gameToRemove = new ArrayList<GameEntity>();
	private GameScreen screen;
	private boolean goingLeft = false;
	private boolean goingRight = false;
	private Ray ray = null;
	public GameWorld(Vec2f worldBounds, GameScreen screen) {
		this.screen = screen;
		this.worldBounds = worldBounds;
		this.entities = new ArrayList<Entity>();
		this.physicsEntities = new ArrayList<PhysicsEntity>();
		this.playerUnit = new PlayerUnit(new Vec2f(worldBounds.x/2, worldBounds.y/3), worldBounds, this);
		CircleChar e1 = new CircleChar(30, new Vec2f(worldBounds.x/4, worldBounds.y/2), Color.BLACK, this);
		e1.setMass(4f);
		CircleChar e2 = new CircleChar(20, new Vec2f(3*worldBounds.x/4, worldBounds.y/2), Color.BLACK, this);
		e2.setMass(1f);
		
		AABChar e3 = new AABChar(new Vec2f(20, 40), new Vec2f(6*worldBounds.x/7, worldBounds.y/2), this);
		
		DiamondChar e4 = new DiamondChar(new Vec2f(1*worldBounds.x/7, worldBounds.y/2), 20, Color.orange, this);
		new Wall(new Vec2f(300, 300),  new Vec2f(100, 50), this, 1);
		new Wall(new Vec2f(-500, -500),  new Vec2f(500, worldBounds.y + 1000), this, 0);
		new Wall(new Vec2f(-500, -500),  new Vec2f(worldBounds.x + 1000, 500), this, 0);
		new Wall(new Vec2f(worldBounds.x, -500),  new Vec2f(500, worldBounds.y + 1000), this, 0);
		new Wall(new Vec2f(-500, worldBounds.y),  new Vec2f(worldBounds.x + 1000, 500), this, 0);
	}
	
	@Override
	public void onKeyTyped(KeyEvent e, Viewport gameView) {
		
	}
	public void onDraw(Vec2i topLeft, Graphics2D g) {
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, (int) worldBounds.x, (int) worldBounds.y);
		for(Entity e: entities){
			e.onDraw(g);
		} for (Ray r : rays){
			g.setColor(Color.green);
			g.drawLine((int) r.position.x, (int) r.position.y, (int) (r.position.x+r.direction.x*1000), (int) (r.position.y+r.direction.y*1000));
		}
	}
	
	public void playerWin(){
		this.screen.getApp().delScreen();
		this.screen.getApp().addScreen(new WinScreen(this.screen.getApp(), screen.screenVector, "The Player"));
	}
	
	public void enemyWin(){
		this.screen.getApp().delScreen();
		this.screen.getApp().addScreen(new WinScreen(this.screen.getApp(), screen.screenVector, "The Enemy"));
	}
	
	@Override
	public void onTick(long nanosSincePreviousTick) {
		super.onTick(nanosSincePreviousTick);
		Vec2f view = (new Vec2f(-this.playerUnit.position.x,  -this.playerUnit.position.y));
		view = view.plus(new Vec2f(viewport.viewSize.x, viewport.viewSize.y).sdiv(2f));
		viewport.amountToTranslate = new Vec2i((int)view.x, (int)view.y);
		//viewport.addTranslation(new Vec2i((int) this.playerUnit.position.x, (int)this.playerUnit.position.y));
		for (PhysicsEntity e : physicsEntities){
			Vec2f g = new Vec2f(0, .0098f);
			e.applyForce(g.smult(e.getMass()));
		}
		for (GameEntity e: gameToAdd) {
			gameEntities.add(e);
		}
		for (GameEntity e: gameToRemove) {
			gameEntities.remove(e);
		}
		
		if (playerUnit != null && goingRight) {
			playerUnit.applyForce((((new Vec2f(.2f, playerUnit.velocity.y)).minus(playerUnit.velocity)).smult(.1f)));
		} else if (playerUnit != null && goingLeft) {
			playerUnit.applyForce((((new Vec2f(-.2f, playerUnit.velocity.y)).minus(playerUnit.velocity)).smult(.1f)));
		} else if (playerUnit != null) {
			playerUnit.applyForce((((new Vec2f(0f, playerUnit.velocity.y)).minus(playerUnit.velocity)).smult(.1f)));
		}
	}
	
	@Override
	public void onKeyPressed(KeyEvent e, Viewport gameView) {
		if (playerUnit != null) {
			if (e.getKeyChar() == 'w') {
				if(playerUnit.getLastMTV() != null && playerUnit.getLastMTV().y <= 0){
					playerUnit.applyImpulse(new Vec2f(0, -1.4f));
				}
			} else if (e.getKeyChar() == 'a'){
				goingLeft = true;
			} else if (e.getKeyChar() == 's'){
				
			} else if (e.getKeyChar() == 'd'){
				goingRight = true;
			}
		}
	}
	
	@Override
	public void onKeyReleased(KeyEvent e, Viewport gameView) {
		if (playerUnit != null) {
			if (e.getKeyChar() == 'w') {
				
			} else if (e.getKeyChar() == 'a'){
				goingLeft = false;
			} else if (e.getKeyChar() == 's'){
				
			} else if (e.getKeyChar() == 'd'){
				goingRight = false;
			}
		}
	}
	
	@Override
	public void onMouseClicked(MouseEvent e, Viewport gameView) {
		
	}
	
	@Override
	public void onMousePressed(MouseEvent e, Viewport gameView) {
		if (playerUnit != null){
			if (e.getButton() == MouseEvent.BUTTON1){
				//System.out.println("not null");
				Point point = Viewport.screenToView(gameView.viewSize,
						gameView.amountToTranslate,
						gameView.amountToZoom,
						e.getPoint(),
						gameView.topLeft);
				Vec2f p = new Vec2f(point.x, point.y);
				Vec2f dir = new Vec2f(1, 0);
				ray = new Ray(playerUnit.position, dir.normalized(), 2, 2, playerUnit);
				rays.add(ray);
			} else if (e.getButton() == MouseEvent.BUTTON2) {
				Vec2f dir = new Vec2f(-.2f, -.2f);
				new Grenade(10, playerUnit.position, Color.BLACK, this, dir);
			}else if (e.getButton() == MouseEvent.BUTTON3) {
				Vec2f dir = new Vec2f(.2f, -.2f);
				new Grenade(10, playerUnit.position, Color.BLACK, this, dir);
			}
		}
	}

	@Override
	public void onMouseReleased(MouseEvent e, Viewport gameView) {
		raysToRemove.add(ray);
		//System.out.println("did");
	}

	@Override
	public void onMouseDragged(MouseEvent e, Viewport gameView) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMouseMoved(MouseEvent e, Viewport gameView) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMouseWheelMoved(MouseWheelEvent e, Viewport gameView) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public <T extends PhysicsEntity> void onCollision(T s, T e) {
		if (s instanceof Grenade) {
			Grenade g = (Grenade) s;
			g.explode();
		} if (e instanceof Grenade){
			Grenade g = (Grenade) e;
			g.explode();
		}
	}

	@Override
	public <T extends PhysicsEntity> void onRayCollision(Ray r, T s, T e, Vec2f closestPoint) {
		if (s instanceof Grenade) {
			Grenade g = (Grenade) s;
			if (r.position.dist(e.position) <= g.range){
				e.applyImpulse(r.direction);
			}
			this.raysToRemove.add(r);
			
		} else {
			e.applyImpulse(r.direction.smult(.11f));
		}
	}
}
