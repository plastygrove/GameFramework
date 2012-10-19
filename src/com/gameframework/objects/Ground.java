package com.gameframework.objects;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import com.gameframework.Util;

public class Ground extends GameObject {

	public Ground(World world, Vec2 position, float width, float height) {
		this.position = position;
		this.boundingWidth = width;
		this.boundingHeight = height;

		this.body = world.createBody(getBodyDef());
		this.body.createFixture(getFixtureDef());
		// PolygonShape ps = new PolygonShape();
		// ps.setAsEdge(new Vec2(-40.0f, 500.0f), new Vec2(840.0f, 500.0f));
		// this.body.createFixture(ps, 0.0f);

	}

	@Override
	public void render(Graphics g) {
		Util util = Util.getInstance();
		Vector2f pos = util.rectBoxToSlick(position, boundingWidth, boundingHeight);
		Shape shape = new Rectangle(pos.x, pos.y, util.metresToPixels(boundingWidth), util.metresToPixels(boundingHeight));
//		GradientFill fill = new GradientFill(pos.x-10, pos.y, Color.green, pos.x, pos.y, Color.green, false);
//		g.fill(shape, fill);
		g.draw(shape);
	}

	@Override
	protected BodyDef getBodyDef() {
		BodyDef bd = new BodyDef();
		bd.position = position;
		bd.type = BodyType.STATIC;
		return bd;
	}

	@Override
	protected FixtureDef getFixtureDef() {
		FixtureDef fd = new FixtureDef();
		PolygonShape ps = new PolygonShape();
		// ps.setAsEdge(new Vec2(-40.0f, 500.0f), new Vec2(40.0f, 500.0f));
		ps.setAsBox(boundingWidth / 2, boundingHeight / 2);

		fd.shape = ps;

		fd.density = 0.5f;
		fd.friction = 0.3f;
		fd.restitution = 0.5f;

		return fd;
	}

}
