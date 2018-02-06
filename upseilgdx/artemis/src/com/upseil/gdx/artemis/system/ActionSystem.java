package com.upseil.gdx.artemis.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.upseil.gdx.artemis.component.ActingComponent;

public class ActionSystem extends IteratingSystem {
	
	private ComponentMapper<ActingComponent> mapper;

	public ActionSystem() {
		super(Aspect.all(ActingComponent.class));
	}

	@Override
	protected void process(int entityId) {
		mapper.get(entityId).act(world.delta);
	}

}
