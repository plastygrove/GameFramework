package com.gameframework.states;

import java.util.ArrayList;
import java.util.List;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.gameframework.Camera;
import com.gameframework.PhysicsHandler;
import com.gameframework.objects.Ball;
import com.gameframework.objects.GameObject;
import com.gameframework.objects.Ground;

public class GamePlayState extends BasicGameState {

	private int id;
	private Camera camera;
	private PhysicsHandler handler;
	private List<GameObject> allObjects;
	private World world;

	public GamePlayState(int id) {
		super();
		this.id = id;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		allObjects = new ArrayList<>();
		handler = new PhysicsHandler();
		world = new World(new Vec2(0.0f, 0.0f), true);
		camera = new Camera(new Vector2f(0, 0), 800, 600);

		// All measurements in MKS units
		// Ball ball = new Ball(world, new Vec2(0, 2.5f), 1f);
		// Ball ball2 = new Ball(world, new Vec2(-1.5f, 2f), 0.2f);
		// ball.applyImpulse(new Vec2(0, 1));
		// ball2.applyImpulse(new Vec2(0, 1));
		// ball.setGravity(new Vec2(0, -10));
		// ball2.setGravity(new Vec2(0, -10));
		// allObjects.add(ball);
		// allObjects.add(ball2);

		Ground ground = new Ground(world, new Vec2(0, -2), 14, 1);
		allObjects.add(ground);

		for (int i = 0; i < 10; i++) {
			Ball ball = new Ball(world, new Vec2(i - 6 + (.05f + .025f * i) * i, 5), .2f + 0.05f * i);
			ball.setGravity(new Vec2(0, -10));
			allObjects.add(ball);
		}

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		camera.render(g, allObjects);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		handler.simulate(world, allObjects, delta * .001f);
	}

	@Override
	public void keyPressed(int key, char c) {
		super.keyPressed(key, c);
		// handler.simulate(world, allObjects, .01f);
	}

	@Override
	public void keyReleased(int key, char c) {
		super.keyReleased(key, c);
	}

	@Override
	public int getID() {
		return id;
	}

}
