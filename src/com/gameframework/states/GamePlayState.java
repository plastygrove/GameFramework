package com.gameframework.states;

import java.util.ArrayList;
import java.util.List;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.gameframework.Camera;
import com.gameframework.PhysicsHandler;
import com.gameframework.objects.Ball;
import com.gameframework.objects.GameObject;
import com.gameframework.objects.Ground;
import com.gameframework.objects.Paddle;

public class GamePlayState extends BasicGameState {

	private int id;
	private Camera camera;
	private PhysicsHandler handler;
	private List<GameObject> allObjects;
	private World world;
	private int currentKeyPressed;
	private int lastKeyReleased;
	private Paddle paddle;
	private List<Integer> keyList;
	private float paddleSpeed = 5.0f;

	public GamePlayState(int id) {
		super();
		this.id = id;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		keyList = new ArrayList<>();

		allObjects = new ArrayList<>();
		handler = new PhysicsHandler();
		world = new World(new Vec2(0.0f, 0.0f), true);
		camera = new Camera(new Vec2(0, 0), 16, 12);
		camera.setCameraInvert(true);

		// All measurements in MKS units
		// Ball ball = new Ball(world, new Vec2(0, 2.5f), 1f);
		// Ball ball2 = new Ball(world, new Vec2(-1.5f, 2f), 0.2f);
		// ball.applyImpulse(new Vec2(0, 1));
		// ball2.applyImpulse(new Vec2(0, 1));
		// ball.setGravity(new Vec2(0, -10));
		// ball2.setGravity(new Vec2(0, -10));
		// allObjects.add(ball);
		// allObjects.add(ball2);

		for (int i = 0; i < 10; i++) {
			Ball ball = new Ball(world, new Vec2(i - 6 + (.05f + .025f * i) * i, 5), .2f + 0.05f * i);
			ball.setGravity(new Vec2(0, -10));
			allObjects.add(ball);
		}

		for (int i = 0; i < 10; i++) {
			Ball ball = new Ball(world, new Vec2(i - 6 + (.05f + .025f * i) * i + .01f, 8), .2f + 0.05f * i);
			ball.setGravity(new Vec2(0, -10));
			allObjects.add(ball);
		}

		Ground ground = new Ground(world, new Vec2(0, -2), 14, 1);
		allObjects.add(ground);
		paddle = new Paddle(world, new Vec2(0f, -3f), 1f, .5f);
		allObjects.add(paddle);

	}

	// @Override
	// public void mouseMoved(int oldx, int oldy, int newx, int newy) {
	// super.mouseMoved(oldx, oldy, newx, newy);
	// Util util = Util.getInstance();
	// Vec2 boxPos = util.slickToBox(new Vector2f(newx, newy));
	// paddle.setPosition(boxPos);
	// }

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		camera.render(g, allObjects);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		handler.simulate(world, allObjects, delta * .001f);
		switch (currentKeyPressed) {
		case Input.KEY_LEFT:
			camera.move(new Vec2(-0.01f, 0));
			break;
		case Input.KEY_RIGHT:
			camera.move(new Vec2(0.01f, 0));
			break;
		case Input.KEY_UP:
			camera.move(new Vec2(0, 0.01f));
			break;
		case Input.KEY_DOWN:
			camera.move(new Vec2(0, -0.01f));
			break;
		case Input.KEY_W:
			paddle.applyLinearVelocity(new Vec2(0, paddleSpeed));
			break;
		case Input.KEY_S:
			paddle.applyLinearVelocity(new Vec2(0, -paddleSpeed));
			break;
		case Input.KEY_A:
			paddle.applyLinearVelocity(new Vec2(-paddleSpeed, 0));
			break;
		case Input.KEY_D:
			paddle.applyLinearVelocity(new Vec2(paddleSpeed, 0));
			break;
		default:

		}

		switch (lastKeyReleased) {
		case Input.KEY_W:
		case Input.KEY_S:
		case Input.KEY_A:
		case Input.KEY_D:
			paddle.applyLinearVelocity(new Vec2(0, 0));
			lastKeyReleased = -1;
			break;
		default:

		}
	}

	@Override
	public void keyPressed(int key, char c) {
		super.keyPressed(key, c);
		// handler.simulate(world, allObjects, .01f);
		currentKeyPressed = key;
		keyList.add(key);
	}

	@Override
	public void keyReleased(int key, char c) {
		super.keyReleased(key, c);
		keyList.remove(new Integer(key));
		lastKeyReleased = key;
		if (currentKeyPressed == key) {
			if (keyList.isEmpty()) {
				currentKeyPressed = -1;
			} else {
				currentKeyPressed = keyList.get(0);
			}
		}
	}

	@Override
	public int getID() {
		return id;
	}

}
