package core.model.units;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import core.model.Unit;

public class Spawn extends Unit {
	public enum State {
		WAITING, SUMMONING
	}
	
	private float size;
	private State state = State.WAITING;
	
	private ArrayList<Monster> monstersInvoc = new ArrayList<Monster>();
	
	/**
	 * Constructeur
	 * @param position
	 * @param size
	 */
	public Spawn(Vector2 position, float size) {
		super(position);
		this.size = size;
		this.texture = new Texture("images/spawn.png");
	}
	
	/**
	 * Permet d'actualiser l'état de la tour
	 */
	public void update(float delta){
		if (state == State.SUMMONING && !world.getMapM().containsKey(position))
			state = State.WAITING;
		
		if (state == State.WAITING && !monstersInvoc.isEmpty()){
			world.getMapM().put(position, monstersInvoc.remove(0).create(position.cpy()));
			state = State.SUMMONING;
		}
	}
	
	//------GETTERS ET SETTERS-------
	public void addMonster(Monster monster){
		monstersInvoc.add(monster);
	}

	@Override
	public float getSize() {
		return size;
	}
	
	public boolean haveMonster(){
		return (state == State.SUMMONING);
	}

}
