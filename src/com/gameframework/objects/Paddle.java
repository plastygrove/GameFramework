package com.gameframework.objects;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import com.gameframework.Util;

public class Paddle extends GameObject {

	public Paddle(World world, Vec2 position, float width, float height){
		this.position = position;
		this.boundingWidth = width;
		this.boundingHeight = height;
		this.body = world.createBody(getBodyDef());
		this.body.createFixture(getFixtureDef());
	}
	@Override
	public void render(Graphics g, Vec2 offset) {
		Util util = Util.getInstance();
		Vec2 offsetPos = new Vec2(position.x+offset.x, position.y+offset.y);
		Vector2f slickPosition = util.rectBoxToSlick(offsetPos, boundingWidth, boundingHeight);
		Shape shape = new Rectangle(slickPosition.x, slickPosition.y, util.metresToPixels(boundingWidth), util.metresToPixels(boundingHeight));
		g.draw(shape);
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
		PolygonShape ps = new PolygonShape();
		// ps.setAsEdge(new Vec2(-40.0f, 500.0f), new Vec2(40.0f, 500.0f));
		ps.setAsBox(boundingWidth / 2, boundingHeight / 2);

		fd.shape = ps;

		fd.density = 5.5f;
		fd.friction = 0.3f;
		fd.restitution = 0.5f;

		return fd;
	}

}
