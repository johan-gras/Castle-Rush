package core.model.units;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import core.model.Unit;

public class Base extends Unit {
	public enum State {
		ALIVE, DIED
	}
	//---Stats---
	private float size;
	private int hp;
	private State state = State.ALIVE;

	/**
	 * Constructeur
	 * @param position
	 * @param hp
	 * @param size
	 */
	public Base(Vector2 position, int hp, float size) {
		super(position);
		this.hp = hp;
		this.size = size;
		this.texture = new Texture("images/base.png");
	}
	
	/**
	 * Permet de mettre à jour la base
	 */
	@Override
	public void update(float delta) {
		if (hp <= 0){
			state = State.DIED;
			world.getMap().remove(position);
			world.getMap().remove(new Vector2(position.x + 1, position.y));
			world.getMap().remove(new Vector2(position.x, position.y + 1));
			world.getMap().remove(new Vector2(position.x + 1, position.y + 1));
		}
	}
	
	//--------GETTERS ET SETTERS----------
	@Override
	public float getSize() {
		return size;
	}

	public int getHp() {
		return hp;
	}
	
	public void takeDmg(){
		hp--;
	}
	
	public boolean isAlive(){
		return !(state == State.DIED);
	}

}
