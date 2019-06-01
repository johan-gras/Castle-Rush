package core.model.units;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import core.model.Unit;

public class Obstacle extends Unit{

	private float size;
	
	/**
	 * Constructeur
	 * @param type
	 * @param size
	 * @param texture
	 */
	public Obstacle(String type, float size, String texture){
		this.type=type;
		this.size = size;
		this.texture = new Texture("images/floor/" + texture);		
	}
	
	/**
	 * Constructeur
	 * @param type
	 * @param size
	 * @param texture
	 */
	public Obstacle(String type, float size, Texture texture){
		this.type=type;
		this.size = size;
		this.texture = texture;		
	}
	
	/**
	 * Permet d'affecter les coordonnées à un obstacle
	 * @param position
	 * @return
	 */
	public Obstacle create(Vector2 position){
		Obstacle copy = new Obstacle(type, size, texture);
		copy.setPosition(position);
		return copy;
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Permet de générer aléatoirement un type au décor
	 * @return
	 */
	public static String getRandomName(){
		Random random = new Random();
		int n = random.nextInt(4 - 1 + 1) + 1;
		if(n==1)
			return "Lave";
		if(n==2)
			return "Pierre";
		if(n==3)
			return "Eau";
		else
			return "Glace";
	}

	@Override
	public float getSize() {
		// TODO Auto-generated method stub
		return size;
	}

	String type;
	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


}
