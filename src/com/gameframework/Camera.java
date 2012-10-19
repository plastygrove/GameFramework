package com.gameframework;

import java.util.List;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import com.gameframework.objects.GameObject;

public class Camera {
	private Vector2f position;
	private float viewWidth;
	private float viewHeight;

	public Camera(Vector2f position, float viewWidth, float viewHeight) {
		this.position = position;
		this.viewHeight = viewHeight;
		this.viewWidth = viewWidth;
	}

	public void render(Graphics g, List<GameObject> allObjects) {
		for (GameObject obj : allObjects) {
			if (shouldDisplay(obj))
				obj.render(g);
		}
	}

	private boolean isInView(float x, float y) {
		float cameraLeft = position.x;
		float cameraRight = position.x + viewWidth;
		float cameraTop = position.y;
		float cameraBottom = position.y + viewHeight;

		if (x > cameraLeft && x < cameraRight && y > cameraTop && y < cameraBottom)
			return true;
		else
			return false;
	}

	private boolean shouldDisplay(GameObject obj) {
		boolean bDisp = false;
		Vector2f objPos = Util.getInstance().boxToSlick(obj.getPosition());

		float objLeft = objPos.x;
		float objRight = objPos.x + Util.getInstance().metresToPixels(obj.boundingWidth);
		float objTop = objPos.y;
		float objBottom = objPos.y + Util.getInstance().metresToPixels(obj.boundingHeight);

		if (isInView(objLeft, objTop) || isInView(objRight, objTop) || isInView(objLeft, objBottom) || isInView(objRight, objBottom))
			bDisp = true;

		return bDisp;
	}

}
