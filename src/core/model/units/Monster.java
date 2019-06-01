package core.model.units;

import java.util.HashMap;
import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import core.model.Unit;

public class Monster extends Unit {
	public enum State {
		WAITING, WALKING, DIED
	}
	public enum Direction {
		NONE, RIGHT, LEFT, UP, DOWN
	}
	public enum Capacity {
		INVISIBLE, PASSTOWER
	}
	public enum ObstacleModifier {
		NONE, WATERSLOW, ICESPEED, LAVARANDOM
	}
	
	//---Stats du monstres----
	private String name;
	private int price;
	private int income;
	private int destructionGain;
	private int hp; //Heal Point
	private float speed; //Unit per second
	private float slow;
	private float size;
	private State state = State.WAITING;
	private Vector2 velocity;
	private Vector2 positionDraw;
	private float timer;
	private float timerFreeze;
	private boolean freeze = false;
	private ObstacleModifier obstacleMod = ObstacleModifier.NONE;
	private boolean visible = true;
	private int num;
	private HashMap<Capacity,Boolean> capacities = new HashMap<Capacity,Boolean>();

	/**
	 * Constructeur
	 * @param name
	 * @param price
	 * @param income
	 * @param destructionGain
	 * @param hp
	 * @param speed
	 * @param size
	 * @param texture
	 */
	public Monster(int num, String name, int price, int income, int destructionGain, int hp, float speed, float size, String texture) {
		this.num = num;
		this.name = name;
		this.texture = new Texture("images/monsters/" + texture);
		this.price = price;
		this.income = income;
		this.destructionGain = destructionGain;
		this.hp = hp;
		this.speed = speed;
		this.size = size;
		for (Capacity c : Capacity.values())
			capacities.put(c, false);
	}

	/**
	 * Autre constructeur
	 * @param name
	 * @param price
	 * @param income
	 * @param destructionGain
	 * @param hp
	 * @param speed
	 * @param size
	 * @param texture
	 * @param capacities
	 */
	public Monster(int num, String name, int price, int income, int destructionGain, int hp, float speed, float size, Texture texture, HashMap<Capacity,Boolean> capacities) {
		this.num = num;
		this.name = name;
		this.price = price;
		this.income = income;
		this.destructionGain = destructionGain;
		this.hp = hp;
		this.speed = speed;
		this.size = size;
		this.texture = texture;
		this.capacities = capacities;
	}

	/**
	 * Permet d'affecter des coordonnées à un monstre
	 * @param position
	 * @return
	 */
	public Monster create(Vector2 position){
		Monster copy = new Monster(num, name, price, income, destructionGain, hp, speed, size, texture, capacities);
		copy.setPosition(position);
		copy.setPositionDraw(position.cpy());
		return copy;
	}

	/**
	 * Méthode qui permet d'actualiser les informations concernant le monstre
	 */
	@Override
	public void update(float delta) {
		if (hp <= 0)
			state = State.DIED;
		
		if (freeze){
			timerFreeze += delta;
			if (timerFreeze >= 3f){
				freeze = false;
				speed += slow;
			}
		}

		if (state == State.DIED){
			world.getMapM().remove(position);
			world.getInfoGame().monsterKill(destructionGain);
			if (world.isOnline())
				world.getReseau().deathMonster(num);
		}

		else if (state == State.WALKING){
			timer += delta;
			positionDraw.add(velocity.cpy().scl(delta));
			if (timer >= 1/speed){
				state = State.WAITING;
				positionDraw = position.cpy();
			}
		}

		else if (state == State.WAITING){
			if (obstacleMod == ObstacleModifier.LAVARANDOM)
				walk(randomWalk());
			else
				walk(world.getPathfinding().getDirection(position));
		}
	}

	/**
	 * Méthode qui permet de faire marcher dans une direction un monstre
	 * @param direction
	 * @return
	 */
	private boolean walk(Direction direction){
		Vector2 positionNew;
		if (direction == Direction.LEFT){
			positionNew = new Vector2(position.x - 1, position.y);
			velocity = new Vector2(-speed, 0);
		}
		else if (direction == Direction.RIGHT){
			positionNew = new Vector2(position.x + 1, position.y);
			velocity = new Vector2(speed, 0);
		}
		else if (direction == Direction.UP){
			positionNew = new Vector2(position.x, position.y + 1);
			velocity = new Vector2(0, speed);
		}
		else{
			positionNew = new Vector2(position.x, position.y -1);
			velocity = new Vector2(0, -speed);
		}
		
		//Marcher normal
		if ((!world.getMap().containsKey(positionNew)
				|| (world.getMap().get(positionNew) instanceof BigUnit && ((BigUnit) world.getMap().get(positionNew)).getUnitLink() instanceof Spawn)
				|| (world.getMap().get(positionNew) instanceof Spawn)
				|| (world.getMap().get(positionNew) instanceof Obstacle && ((Obstacle) world.getMap().get(positionNew)).getType() != "Pierre")
				) && (!world.getMapM().containsKey(positionNew))){
			
			world.getMapM().put(positionNew, world.getMapM().remove(position));
			state = State.WALKING;
			positionDraw = position;
			position = positionNew;
			timer = 0f;
		}
		
		//Marcher dans la base
		else if (world.getMap().get(positionNew) instanceof Base){
			((Base) world.getMap().get(positionNew)).takeDmg();
			state = State.DIED;
		}
		else if (world.getMap().get(positionNew) instanceof BigUnit && ((BigUnit) world.getMap().get(positionNew)).getUnitLink() instanceof Base){
			((Base) ((BigUnit) world.getMap().get(positionNew)).getUnitLink()).takeDmg();
			state = State.DIED;
		}
		
		//On peut pas marcher bitch
		else
			return false;
		
		//Types obstacles
		if (obstacleMod == ObstacleModifier.WATERSLOW){
			obstacleMod = ObstacleModifier.NONE;
			speed += 0.5f;
		}
		else if (obstacleMod == ObstacleModifier.ICESPEED){
			obstacleMod = ObstacleModifier.NONE;
			speed -= 0.5f;
		}
		else if (obstacleMod == ObstacleModifier.LAVARANDOM){
			obstacleMod = ObstacleModifier.NONE;
			speed *= 0.5f;
		}
		
		if (world.getMap().get(positionNew) instanceof Obstacle){
			if(((Obstacle) world.getMap().get(positionNew)).getType() == "Eau"){
				obstacleMod = ObstacleModifier.WATERSLOW;
				speed -= 0.5f;
			}
			else if(((Obstacle) world.getMap().get(positionNew)).getType() == "Glace"){
				obstacleMod = ObstacleModifier.ICESPEED;
				speed += 0.5f;
			}
			else if(((Obstacle) world.getMap().get(positionNew)).getType() == "Lave"){
				obstacleMod = ObstacleModifier.LAVARANDOM;
				speed *= 2f;
			}
		}
		
		return true;
	}
	
	/**
	 * Permet de faire marcher dans une direction aléatoire le monstre
	 * @return
	 */
	private Direction randomWalk() {
		Random random = new Random();
		int n = random.nextInt(8 - 1 + 1) + 1;
		if(n==1)
			return Direction.DOWN;
		else if(n==2)
			return Direction.LEFT;
		else if(n==3)
			return Direction.RIGHT;
		else if (n==4)
			return Direction.UP;
		else
			return world.getPathfinding().getDirection(position);
	}
	
	//------GETTERS ET SETTERS----------
	
	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}

	public int getIncome() {
		return income;
	}

	public int getDestructionGain() {
		return destructionGain;
	}

	public int getHp() {
		return hp;
	}

	public float getSpeed() {
		return speed;
	}

	@Override
	public float getSize() {
		return size;
	}

	public boolean isAlive(){
		return !(state == State.DIED);
	}

	public void retirerHp(int dmg) {
		hp -= dmg;		
	}

	public void slowTower(){
		timerFreeze = 0f;
		if (!freeze){
			freeze = true;
			slow = 0.3f*speed;
			speed -= slow;
			System.out.println(speed);
		}
	}

	public void setCapacity(Capacity capacity){
		capacities.put(capacity, true);
	}
	
	public boolean getCapacity(Capacity capacity){
		return capacities.get(capacity);
	}

	public Vector2 getPositionDraw(){
		return positionDraw;
	}

	private void setPositionDraw(Vector2 position) {
		positionDraw = position;
	}

	public boolean isVisible() {
		return (!capacities.get(Capacity.INVISIBLE) || visible);
	}
}
