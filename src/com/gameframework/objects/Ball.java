package com.gameframework.objects;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Vector2f;

import com.gameframework.Util;

public class Ball extends GameObject {
	private float radius;
	private Vec2 gravity = new Vec2(0, 0);

	public Ball(World world, Vec2 position, float radius) {
		this.position = position;
		this.radius = radius;
		this.boundingHeight = this.boundingWidth = 2 * radius;

		body = world.createBody(getBodyDef());
		body.createFixture(getFixtureDef());
	}

	@Override
	public void render(Graphics g, Vec2 offset) {
		Util util = Util.getInstance();
		Vec2 offsetPos = new Vec2(position.x + offset.x, position.y + offset.y);
		Vector2f slickPosition = util.boxToSlick(offsetPos);
		float slickRadius = util.metresToPixels(radius);
		org.newdawn.slick.geom.Shape shape = new Circle(slickPosition.x, slickPosition.y, slickRadius);
		GradientFill fill = new GradientFill(slickPosition.x - slickRadius, slickPosition.y, Color.blue, slickPosition.x + slickRadius, slickPosition.y, Color.red);
		g.fill(shape, fill);
	}

	@Override
	protected BodyDef getBodyDef() {
		BodyDef bd = new BodyDef();
		bd.position = position;
		bd.type = BodyType.DYNAMIC;
		return bd;
	}

	@Override
	protected FixtureDef getFixtureDef() {
		FixtureDef fd = new FixtureDef();
		Shape shape = new CircleShape();
		shape.m_radius = radius;

		fd.shape = shape;
		fd.density = 1f;
		fd.friction = 0.3f;
		fd.restitution = 0.8f;

		return fd;
	}

	@Override
	public void update() {
		super.update();
		body.applyForce(gravity.mul(body.m_mass), position);// Force = mass*acceleration
	}

	public void setGravity(Vec2 acceleration) {
		this.gravity = acceleration;
	}

	public void applyImpulse(Vec2 force) {
		this.body.applyLinearImpulse(force, position);
	}

}
