package core.model;

import java.util.ArrayList;

import core.model.units.Monster;

public class Script {
	
	/**
	 * Classe interne
	 * @author Ilies
	 *
	 */
	private class Wave{
		
		public Monster monster;
		public int nombre;
		public float time;

		/**
		 * Constructeur de la vague de monstres
		 * @param monster
		 * @param nombre
		 * @param time
		 */
		public Wave(Monster monster, int nombre, float time) {
			this.monster = monster;
			this.nombre = nombre;
			this.time = time;
		}
	}

	private World world;

	//Liste des vagues de sbires
	private ArrayList<Wave> waves = new ArrayList<Wave>();
	private float timer = 0f;

	/**
	 * Constructeur du script
	 * @param world
	 */
	public Script(World world) {
		this.world = world;
	}

	/**
	 * Permet de mettre à jour les vagues de monstres
	 * @param delta
	 */
	public void update(float delta){
		timer += delta;
		Wave temp = null;

		for (Wave w : waves){
			if (w.time <= timer && temp == null){
				for (int i = 0 ; i < w.nombre ; i++)
					world.getSpawn().addMonster(w.monster);
				temp = w;
			}
		}
		waves.remove(temp);
	}

	/**
	 * Ajoute une nouvelle vague de monstres au script
	 * @param monster
	 * @param nombre
	 * @param time
	 */
	public void addWave(Monster monster, int nombre, float time){
		waves.add(new Wave(monster, nombre, time));
	}

}