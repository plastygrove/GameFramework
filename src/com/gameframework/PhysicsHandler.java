package com.gameframework;

import java.util.List;

import org.jbox2d.dynamics.World;

import com.gameframework.objects.GameObject;

public class PhysicsHandler {
	public void simulate(World world, List<GameObject> allObjects, float time){
		world.step(time, 6, 3);
		for (GameObject gameObject : allObjects) {
			gameObject.update();
		}
	}
}
