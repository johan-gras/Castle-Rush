package core.model;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.math.Vector2;

import core.model.units.Monster;
import core.model.units.Monster.Direction;
import core.model.units.Spawn;

public class Pathfinding {
	private final static Vector2 POS_UP = new Vector2(0, 1);
	private final static Vector2 POS_DOWN = new Vector2(0, -1);
	private final static Vector2 POS_LEFT = new Vector2(-1, 0);
	private final static Vector2 POS_RIGHT = new Vector2(1, 0);
	
	private World world;
	private HashMap<Vector2,Direction> noeuds;
	
	/**
	 * Constructeur
	 * @param world
	 */
	public Pathfinding(World world) {
		this.world = world;
	}
	
	/**
	 * Permet de mettre à jour le chemin à prendre
	 */
	public void update(){
		calculateNoeuds();
	}
	
	/**
	 * Permet de trouver le chemin le plus court en se basant sur l'algorithme "Breadth First Search"
	 */
	private void calculateNoeuds(){
		noeuds = new HashMap<Vector2,Direction>();
		ArrayList<Vector2> queue = new ArrayList<Vector2>();
		Vector2 current;
		
		noeuds.put(world.getBase().getPosition(), Direction.NONE);
		queue.add(world.getBase().getPosition());
		
		while (!queue.isEmpty()){
			current = queue.remove(0);
			for (Vector2 neighbor : getNeighbors(current)){
				if (!noeuds.containsKey(neighbor)){
					queue.add(neighbor);
					
					if (POS_UP.x == current.x - neighbor.x && POS_UP.y == current.y - neighbor.y)
						noeuds.put(neighbor, Direction.UP);
					else if (POS_DOWN.x == current.x - neighbor.x && POS_DOWN.y == current.y - neighbor.y)
						noeuds.put(neighbor, Direction.DOWN);
					else if (POS_RIGHT.x == current.x - neighbor.x && POS_RIGHT.y == current.y - neighbor.y)
						noeuds.put(neighbor, Direction.RIGHT);
					else if (POS_LEFT.x == current.x - neighbor.x && POS_LEFT.y == current.y - neighbor.y)
						noeuds.put(neighbor, Direction.LEFT);
				}
			}
		}
	}
	
	//FOR DEBUGING
	public void affich(){
		Vector2 temp = null;
		for (int i=world.getY() ; i>=0 ; i--){
		for (int j=0 ; j<world.getX() ; j++){
			temp = new Vector2(j, i);
			if (noeuds.containsKey(temp)){
				if (noeuds.get(temp) == Direction.RIGHT)
					System.out.print("R ");
				if (noeuds.get(temp) == Direction.LEFT)
					System.out.print("L ");
				if (noeuds.get(temp) == Direction.UP)
					System.out.print("U ");
				if (noeuds.get(temp) == Direction.DOWN)
					System.out.print("D ");
			}
			else
				System.out.print("N ");
		}
		System.out.println();
	}
	System.out.println(temp);System.out.println();System.out.println();
	}

	private ArrayList<Vector2> getNeighbors(Vector2 position) {
		ArrayList<Vector2> neightbors = new ArrayList<Vector2>();
		Vector2 temp = new Vector2(position.x + 1, position.y);
		if (world.isWalkable(temp) || world.getMap().get(temp) instanceof Spawn)
			neightbors.add(temp);
		temp = new Vector2(position.x - 1, position.y);
		if (world.isWalkable(temp) || world.getMap().get(temp) instanceof Spawn)
			neightbors.add(temp);
		temp = new Vector2(position.x, position.y + 1);
		if (world.isWalkable(temp) || world.getMap().get(temp) instanceof Spawn)
			neightbors.add(temp);
		temp = new Vector2(position.x, position.y - 1);
		if (world.isWalkable(temp) || world.getMap().get(temp) instanceof Spawn)
			neightbors.add(temp);
		
		return neightbors;
	}
	
	public Direction getDirection(Vector2 position){
		if (!noeuds.containsKey(position))
			return Direction.NONE;
		return noeuds.get(position);
	}

}
