package com.gameframework.objects;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.newdawn.slick.Image;


public abstract class GameObject implements Displayable, Physical {
	protected Vec2 position;
	protected Image image;
	protected Body body;
	public float boundingWidth;
	public float boundingHeight;

	protected abstract BodyDef getBodyDef();

	protected abstract FixtureDef getFixtureDef();
	
	@Override
	public void setPosition(Vec2 position){
		this.position = position;
		body.setTransform(position, body.getAngle());
	}
	
	@Override
	public Vec2 getPosition(){
		return  position;
	}
	
	@Override
	public void update(){
		this.position = body.getPosition().clone();
	}
	
	public void applyLinearVelocity(Vec2 velocity){
		body.setLinearVelocity(velocity);
	}

}
