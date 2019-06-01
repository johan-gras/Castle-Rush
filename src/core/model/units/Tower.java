package core.model.units;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import core.model.Unit;
import core.model.units.Monster.Capacity;

public class Tower extends Unit {
	private enum State {
		BUILDING, WAITING, FIRING
	}
	public enum Capacity {
		ANTIINVISIBLE, ANTIPASSTOWER, FREEZING
	}

	private final static float TIMEBUILDING = 5f;
	private final static Texture TEXTUREBUILDING = new Texture("images/towers/building.png");

	//---Les différentes stats de la tour
	private String name;
	private int price;
	private int evoPrice;
	private int range;
	private int dmg; //Damage
	private float as; //Attack Speed
	private float size;
	private State state = State.BUILDING;
	private float timer = 0f;
	private Texture textureNormal;
	private Monster target;
	private HashMap<Capacity,Boolean> capacities = new HashMap<Capacity,Boolean>();

	/**
	 * Constructeur
	 * @param name
	 * @param price
	 * @param range
	 * @param dmg
	 * @param as
	 * @param size
	 * @param texture
	 */
	public Tower(String name, int price, int range, int dmg, float as, float size, String texture) {
		this.name = name;
		this.textureNormal = new Texture("images/towers/" + texture);
		this.texture = TEXTUREBUILDING;
		this.price = price;
		this.evoPrice = (int)(price*0.1);
		this.range = range;
		this.dmg = dmg;
		this.as = as;
		this.size = size;
		for (Capacity c : Capacity.values())
			capacities.put(c, false);
	}

	/**
	 * Autre constructeur
	 * @param name
	 * @param price
	 * @param range
	 * @param dmg
	 * @param as
	 * @param size
	 * @param textureNormal
	 * @param capacities
	 */
	public Tower(String name, int price, int range, int dmg, float as, float size, Texture textureNormal, HashMap<Capacity,Boolean> capacities) {
		this.name = name;
		this.price = price;
		this.evoPrice = (int)(price*0.1);
		this.range = range;
		this.dmg = dmg;
		this.as = as;
		this.size = size;
		this.texture = TEXTUREBUILDING;
		this.textureNormal = textureNormal;
		this.capacities = capacities;
	}

	/**
	 * Permet d'affecter des coordonnées à une our lorsqu'on l'a pose
	 * @param position
	 * @return
	 */
	public Tower create(Vector2 position){
		Tower copy = new Tower(name, price, range, dmg, as, size, textureNormal, capacities);
		copy.setPosition(position);
		return copy;
	}

	// ///////////////////IA TOUR/////////////////////

	// Fonction qui fait l'update de l'IA des tours
	public void update (float delta){
		if (state == State.BUILDING){
			timer += delta;
			if (timer >= TIMEBUILDING){
				state = State.WAITING;
				texture = textureNormal;
			}
		}

		else if (state == State.WAITING){
			target = target(world.getMap());
			if (target != null){
				if(capacities.get(Capacity.ANTIINVISIBLE) && target.getCapacity(Monster.Capacity.INVISIBLE))
					;
				if (target.isVisible()){
					state = State.FIRING;
					timer = as; //On tire direct
				}
			}
		}

		else if (state == State.FIRING)
			attack(delta);
	}



	/**
	 * Méthode pour attaquer les sbires qui sont à portée
	 * @param delta
	 */
	public void attack(float delta) {

		if (checkTarget()) {
			timer += delta;
			if (timer >= 1/as) {
				timer -= 1/as;
				target.retirerHp(dmg);

				if(capacities.get(Capacity.FREEZING))
					target.slowTower();
			}
		}
		else
			state = State.WAITING;
	}

	/**
	 * Méthode qui défini le monstre à attaquer
	 * @param map
	 * @return
	 */
	public Monster target(HashMap<Vector2, Unit> map) {
		int x = (int) (position.x - range);
		int y = (int) (position.y - range);
		Monster m = null;
		while (m == null && x <= position.x + range + 2 ) {
			y = (int) (position.y - range);
			while (m == null && y <= position.y + range + 1 ) {

				if (world.getMapM().containsKey(new Vector2(x, y))) {
					m = world.getMapM().get(new Vector2(x, y));
				}
				y++;
			}
			x++;
		}
		return m;
	}

	public boolean horsdeportee() {
		return (target.getPosition().x < position.x - range || target.getPosition().x > position.x + range + 2 || target.getPosition().y < position.y - range || target.getPosition().y > position.y + range + 1);
	}

	/**
	 * Permet de vérifier si a cible est morte ou hors de portée
	 * @return
	 */
	public boolean checkTarget() {
		return !(!target.isAlive() || horsdeportee());
	}

	//-------GETTERS ET SETTERS--------
	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}

	public int getEvoPrice() {
		return evoPrice;
	}

	public int getRange() {
		return range;
	}

	public int getDmg() {
		return dmg;
	}

	public float getAs() {
		return as;
	}

	public float getSize() {
		return size;
	}

	public Texture getTextureNormal() {
		return textureNormal;
	}

	public void setCapacity(Capacity capacity){
		capacities.put(capacity, true);
	}

	public boolean getCapacity(Capacity capacity){
		return capacities.get(capacity);
	}

	}
