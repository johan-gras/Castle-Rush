package core.model;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Unit {
	protected static World world;

	protected Vector2 position;
	protected Texture texture;

	/**
	 * Constructeur
	 */
	public Unit(){

	}

	public Unit(Vector2 position) {
		this.position = position;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public abstract void update(float delta);

	public abstract float getSize();

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public static void setWorld(World world) {
		Unit.world = world;
	}

}
