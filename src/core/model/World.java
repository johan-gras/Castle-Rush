package core.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import core.model.units.Base;
import core.model.units.BigUnit;
import core.model.units.Monster;
import core.model.units.Monster.Capacity;
import core.model.units.Monster.Direction;
import core.model.units.Obstacle;
import core.model.units.Spawn;
import core.model.units.Tower;

public class World {
	private int x;
	private int y;

	private InfoGame infoGame = new InfoGame("Palijoyo", 150, 5);
	private HashMap<Vector2,Unit> map = new HashMap<Vector2,Unit>();
	private HashMap<Vector2,Monster> mapM = new HashMap<Vector2,Monster>();
	private Base base;
	private Spawn spawn;
	private ArrayList<Tower> towers = new ArrayList<Tower>();
	private ArrayList<Monster> monsters = new ArrayList<Monster>();
	private HashMap<String,Obstacle> obstacles = new HashMap<String,Obstacle>();
	private Pathfinding pathfinding = new Pathfinding(this);

	//--Pour la génération auto des vagues de monstres
	private float tpsDebutSpawn = 20f;
	private int nbMobMax = 10;
	private int nbMobMin = 3;
	private Script script = new Script(this);
	
	//Reseau
	private Reseau reseau;
	private boolean online;

	/**
	 * Constructeur
	 * @param x
	 * @param y
	 */
	public World(int x, int y, Reseau r) {
		this.x = x;
		this.y = y;
		Unit.setWorld(this);
		loadUnits();

		createDemoWorld();
		
		pathfinding.update();
		
		if (r == null)
			online = false;
		else{
			online = true;
			this.reseau = r;
			this.reseau.setWorld(this);
		}
	}

	/**
	 * Méthode qui permet d'actualiser la position des monstres etc...
	 * @param delta
	 */
	public void update(float delta){
		if (online)
			reseau.update();
		else{
			spawnMob();
			script.update(delta);
		}
		
		if(!online)
			spawnMob();
		infoGame.udpate(delta);
		script.update(delta); //Exécution du script pour les vagues de monstres

		//Mise à jour des monstres
		int i = 0, size = mapM.values().size();
		Object[] units = mapM.values().toArray();
		while (i < size) {
			((Monster) units[i]).update(delta);
			i++;
		}
		
		//Mise à jour des units
		i = 0;
		size = map.values().size();
		units = map.values().toArray();
		while (i < size) {
			((Unit) units[i]).update(delta);
			i++;
		}

		//Si la base est morte, le jeu passe en mode game over
		if (!base.isAlive()){
			infoGame.gameOver();
			if (online)
				reseau.haveLost();
		}
	}

	/**
	 * Méthode qui permet de charger dans le monde les différents type de monstres et de tourelles
	 */
	private void loadUnits(){
		Tower t;
		towers.add(new Tower("Basic Tower", 20, 3, 5, 1f, 2f, "basicTower.png"));
		t = new Tower("Anti Invisible", 35, 3, 5, 1f, 2f, "antiInvisible.png");
		t.setCapacity(Tower.Capacity.ANTIINVISIBLE);
		towers.add(t);
		t = new Tower("Anti Vol", 25, 3, 4, 1f, 2f, "antiVol.png");
		t.setCapacity(Tower.Capacity.ANTIPASSTOWER);
		towers.add(t);
		towers.add(new Tower("Impenetrable", 30, 3, 4, 1f, 2f, "impenetrable.png"));
		towers.add(new Tower("Grande Portee", 40, 6, 5, 1f, 2f, "portee.png"));
		t = new Tower("Freezing", 30, 3, 4, 1f, 2f, "ralentissante.png");
		t.setCapacity(Tower.Capacity.FREEZING);
		towers.add(t);
		towers.add(new Tower("Fast Shooter", 40, 4, 4, 2f, 2f, "rapide.png"));

		Monster m;
		monsters.add(new Monster(0, "Basic Monster", 20, 2, 10, 70, 1.5f, 1f, "basicMonster.png"));
		m = new Monster(1, "invisible", 50, 5, 25, 50, 1.5f, 1f, "invisible.png");
		m.setCapacity(Monster.Capacity.INVISIBLE);
		monsters.add(m);
		m = new Monster(2, "passeTourelle", 40, 4, 20, 70, 1.5f, 1f, "passeTourelle.png");
		m.setCapacity(Monster.Capacity.PASSTOWER);
		monsters.add(m);
		monsters.add(new Monster(3, "rapide", 30, 3, 15, 50, 3.0f, 1f, "rapide.png"));
		monsters.add(new Monster(4, "resistant", 30, 3, 15, 140, 1.5f, 1f, "resistant.png"));
		monsters.add(new Monster(5, "volant", 40, 4, 20, 60, 1.5f, 1f, "volant.png"));
		monsters.add(new Monster(6, "enerve", 60, 6, 30, 120, 2.5f, 1f, "enerve.png"));

		obstacles.put("Lave", new Obstacle("Lave", 1f, "lava.png"));
		obstacles.put("Pierre", new Obstacle("Pierre", 1f, "rock.png"));
		obstacles.put("Eau", new Obstacle("Eau", 1f, "water.png"));
		obstacles.put("Glace", new Obstacle("Glace", 1f, "freeze.png"));
	}

	/**
	 * Méthode qui génère le carte avec le spawn, la map etc...
	 */
	private void createDemoWorld() {
		//BASE
		base = new Base(new Vector2(30, 17), 10 , 2f);
		createUnit(base);

		//SPAWN
		spawn = new Spawn(new Vector2(5, 17), 2f);
		createUnit(spawn);

		//Obstacles
		generateObstacles();
	}

	private void spawnMob(){
		
		if(this.getInfoGame().isGameRunning() && !this.getSpawn().haveMonster()){
			
			Random n = new Random();
			
			for(int i = 0; i < 10; i++){
				
				int numMob = (int)( Math.random()*( 6 - 0 + 1 ) ) + 0; //Va de 0 à 6
				
				nbMobMax += this.getInfoGame().getTime().getMinutes(); //Augmente au fur et à mesure du temps
				nbMobMin += this.getInfoGame().getTime().getMinutes();
				int nbMob = (int)( Math.random()*( nbMobMax - nbMobMin + 1 ) ) + nbMobMin;
				
				script.addWave(monsters.get(numMob), nbMob, tpsDebutSpawn);
				
				tpsDebutSpawn +=10f;
			}
		}
	}

	/**
	 * Méthode qui permet de générer des obstacles
	 */
	private void generateObstacles(){
		Random n = new Random();
		Vector2 temp;
		int nbBloc = 0, x, y;

		while (nbBloc<140){
			String element = Obstacle.getRandomName();
			boolean free;
			int i, j;

			do {
				ArrayList<Vector2> vectorsToAdd = new ArrayList<Vector2>();
				x = n.nextInt(n.nextInt(3)+1)+2;
				y = n.nextInt(n.nextInt(3)+1)+2;
				temp = new Vector2(n.nextInt(this.x-x), n.nextInt(this.y-y));
				i = (int) temp.x;
				free = true;

				do{
					j = (int) temp.y;
					do{
						free = !map.containsKey(new Vector2(i, j));
						vectorsToAdd.add(new Vector2(i, j));
						j++;
					} while(j < temp.y + y && free);
					i++;
				} while(i < temp.x + x && free);
				
				if (free){
					for (Vector2 v : vectorsToAdd)
						map.put(v, new BigUnit(v, null));
					pathfinding.update();
					free = (pathfinding.getDirection(spawn.getPosition()) != Direction.NONE);
					for (Vector2 v : vectorsToAdd)
						map.remove(v);
				}				
			} while(!free);

			for (i=0 ; i<x ; i++){
				for (j=0 ; j<y ; j++){
					Vector2 temp2 = new Vector2(temp.x+i, temp.y+j);
					map.put(temp2, obstacles.get(element).create(temp2));
					nbBloc++;
				}
			}
		}
	}

	/**
	 * Méthode qui permet de créer des unités
	 * @param u
	 */
	private void createUnit(Unit u){
		//EXCEPTIONS A TRAITER

		Vector2 vect = u.getPosition();
		map.put(vect, u);

		if (u.getSize() > 1){
			Vector2 temp;

			for (int i = 0 ; i < u.getSize() ; i++){
				for (int j = 0 ; j < u.getSize() ; j++){

					if (!(i == 0 && j == 0)){
						temp = new Vector2(vect.x+i, vect.y+j);
						map.put(temp, new BigUnit(temp, u));
					}
				}
			}
		}
	}

	/**
	 * Méthode qui permet de créer un tour et d'actualiser l'IA des monstres lorsqu'on l'a pose
	 * @param tower
	 */
	public void buildTower(Tower tower){
		infoGame.buySomething(tower.getPrice());
		infoGame.towerSet();
		createUnit(tower);
		pathfinding.update();
	}

	/**
	 * Méthode qui permet de savoir si l'unité peut être dans la case du jeu (à cause d'un obstacle ou d'une tour par exmeple)
	 * @param destination
	 * @return
	 */
	public boolean isWalkable(Vector2 destination){
		if (((!map.containsKey(destination))
				|| (map.get(destination) instanceof BigUnit && ((BigUnit) map.get(destination)).getUnitLink() instanceof Spawn)
				|| (map.get(destination) instanceof Base)
				|| (map.get(destination) instanceof BigUnit && ((BigUnit) map.get(destination)).getUnitLink() instanceof Base)
				|| (map.get(destination) instanceof Obstacle && ((Obstacle) map.get(destination)).getType() != "Pierre")
				) && (destination.x >= 0 && destination.y >= 0 && destination.x < this.x && destination.y < this.y))
			return true;

		return false;
	}

	/**
	 * Méthode qui permet de vérifier si la case est libre
	 * @param vect
	 * @param size
	 * @return
	 */
	public boolean isFree(Vector2 vect, float size){
		//EXCEPTIONS

		boolean rep = true;
		Vector2 temp;
		ArrayList<Vector2> vectorsToAdd = new ArrayList<Vector2>();
		int i = 0, j = 0;

		do {
			j = 0;
			do {
				temp = new Vector2(vect.x+i, vect.y+j);
				if (temp.x >= this.x || temp.y >= this.y || temp.x < 0 || temp.y < 0)
					return false;
				rep = !map.containsKey(temp);
				if (rep)
					rep = !mapM.containsKey(temp);
				vectorsToAdd.add(temp);
				j++;
			} while(j<size && rep);
			i++;
		} while(i<size && rep);
		
		if (rep){
			for (Vector2 v : vectorsToAdd)
				map.put(v, new BigUnit(v, null));
			pathfinding.update();
			rep = (pathfinding.getDirection(spawn.getPosition()) != Direction.NONE);
			for (Vector2 v : vectorsToAdd)
				map.remove(v);
			pathfinding.update();
		}
		return rep;
	}

	//----------------GETTERS ET SETTERS-----------------
	public HashMap<Vector2, Unit> getMap() {
		return map;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public InfoGame getInfoGame() {
		return infoGame;
	}

	public Tower getTower(int key){
		return towers.get(key);
	}

	public Base getBase() {
		return base;
	}

	public Spawn getSpawn() {
		return spawn;
	}

	public ArrayList<Tower> getTowers() {
		return towers;
	}

	public void setTowers(ArrayList<Tower> towers) {
		this.towers = towers;
	}

	public ArrayList<Monster> getMonsters() {
		return monsters;
	}

	public void setMonsters(ArrayList<Monster> monsters) {
		this.monsters = monsters;
	}

	public Pathfinding getPathfinding() {
		return pathfinding;
	}

	public HashMap<Vector2, Monster> getMapM() {
		return mapM;
	}

	public Reseau getReseau() {
		return reseau;
	}

	public void setReseau(Reseau reseau) {
		this.reseau = reseau;
	}
	
	public boolean isOnline(){
		return online;
	}
	
}
