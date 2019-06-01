package core.controller;

import java.awt.Button;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import core.model.World;
import core.model.units.Monster;
import core.model.units.Tower;
import core.view.ControllerRendering;

public class WorldController {
	enum State {
		NOTHING, BUILDING
	}
	
	private World world;
	private ControllerRendering rendererC;
	
	private Tower towerSelect = null;
	
	private State state = State.NOTHING;
	private boolean shift = false;
	private int width, height;
	private float ppux, ppuy; //Pixel Par Unite

	private boolean Tpressed = false;
	private boolean Mpressed = false;
	
	//Informations pour caractéristiques sur les tours
	public static String towerSelectName;
	public static String dmg;
	public static String range;
	public static String as;
	public static String prix;
	public static boolean shopTower = false;
	
	//Informations pour caractéristiques sur les monstres
	public static String monsterName;
	public static String income;
	public static String destructionGain;
	public static String hp;
	public static String prixMonster;
	public static String speed;
	public static boolean shopMonster = false;
	
	//Play ou pause
	public static boolean pause = false;
	
	/**
	 * Constructeur
	 * @param world
	 * @param rendererC
	 */
	public WorldController(World world, ControllerRendering rendererC) {
		this.world = world;
		this.rendererC = rendererC;
	}
	
	public void setSize(int w, int h){
		width = 380*2;
		height = 300*2;
		ppux = (float)width/(float)world.getX();
		ppuy = (float)height/(float)world.getY();
	}
	
	public void basicTowerPressed() {
		state = State.BUILDING;
		towerSelect = world.getTower(0);
	}
	
	public void escapePressed(){
		state = State.NOTHING;
		Tpressed = false;
		Mpressed = false;
		rendererC.normalMode();
	}
	
	/**
	 * Utilisé pour gérer l'achat de tour ou de monstre dans le shop quand on clique sur une tour ou un monstre
	 * @param xM
	 * @param yM
	 */
	public void mouseClick(int xM, int yM){
		if (state == State.BUILDING){
			Vector2 vectCase = new Vector2((int)(xM/ppux), (int)((height-yM)/ppuy));
			
			if (! (world.isFree(vectCase, towerSelect.getSize()))){
				rendererC.warningMode("Vous ne pouvez pas poser votre tour ici");
				state = State.NOTHING;
			}
			else if (world.getInfoGame().getPlayerGold() < towerSelect.getPrice()){
				rendererC.warningMode("Vous n'avez pas assez d'argent");
				state = State.NOTHING;
			}
			else{
				world.buildTower(towerSelect.create(vectCase));
				rendererC.normalMode();
				state = State.NOTHING;
			}
			if (shift)
				state = State.BUILDING;
		}
		//Pour ajouter des tours à l'aide de la souris
		if(xM<820 && xM>791 && yM<101 && yM>67){
			buildTower(0);
			
			towerSelectName=towerSelect.getName();
			dmg=Integer.toString(towerSelect.getDmg());
			range=Integer.toString(towerSelect.getRange());
			prix=Integer.toString(towerSelect.getPrice());
			as = Float.toString(towerSelect.getAs());
			
			shopTower=true;
			shopMonster= false;
		}
		if(xM<874 && xM>840 && yM<100 && yM>69){
			buildTower(1);
			
			towerSelectName=towerSelect.getName();
			dmg=Integer.toString(towerSelect.getDmg());
			range=Integer.toString(towerSelect.getRange());
			prix=Integer.toString(towerSelect.getPrice());
			as = Float.toString(towerSelect.getAs());
			
			shopTower=true;
			shopMonster= false;
		}
		if(xM<925 && xM>891 && yM<100 && yM>69){
			buildTower(2);
			
			towerSelectName=towerSelect.getName();
			dmg=Integer.toString(towerSelect.getDmg());
			range=Integer.toString(towerSelect.getRange());
			prix=Integer.toString(towerSelect.getPrice());
			as = Float.toString(towerSelect.getAs());
			
			shopTower=true;
			shopMonster= false;
		}
		if(xM<820 && xM>791 && yM<158 && yM>127){
			buildTower(3);
			
			towerSelectName=towerSelect.getName();
			dmg=Integer.toString(towerSelect.getDmg());
			range=Integer.toString(towerSelect.getRange());
			prix=Integer.toString(towerSelect.getPrice());
			as = Float.toString(towerSelect.getAs());
			
			shopTower=true;
			shopMonster= false;
		}
		if(xM<874 && xM>840 && yM<158 && yM>127){
			buildTower(4);
			
			towerSelectName=towerSelect.getName();
			dmg=Integer.toString(towerSelect.getDmg());
			range=Integer.toString(towerSelect.getRange());
			prix=Integer.toString(towerSelect.getPrice());
			as = Float.toString(towerSelect.getAs());
			
			shopTower=true;
			shopMonster= false;
		}
		if(xM<925 && xM>891 && yM<158 && yM>127){
			buildTower(5);
			
			towerSelectName=towerSelect.getName();
			dmg=Integer.toString(towerSelect.getDmg());
			range=Integer.toString(towerSelect.getRange());
			prix=Integer.toString(towerSelect.getPrice());
			as = Float.toString(towerSelect.getAs());
			
			shopTower=true;
			shopMonster= false;
		}
		if(xM<820 && xM>791 && yM<224 && yM>193){
			buildTower(6);
			
			towerSelectName=towerSelect.getName();
			dmg=Integer.toString(towerSelect.getDmg());
			range=Integer.toString(towerSelect.getRange());
			prix=Integer.toString(towerSelect.getPrice());
			as = Float.toString(towerSelect.getAs());
			
			shopTower=true;
			shopMonster= false;
		}
		//Monstre de base
		if(xM < 826 && xM > 795 && yM < 337 && yM > 301){
			Monster m = this.world.getMonsters().get(0);
			sendMonster(0);
			
			monsterName = m.getName();
			income = Integer.toString(m.getIncome());
			destructionGain = Integer.toString(m.getDestructionGain());
			hp = Integer.toString(m.getHp());
			prixMonster = Integer.toString(m.getPrice());
			speed = Float.toString(m.getSpeed());
			
			shopTower=false;
			shopMonster= true;
		}
		
		//Enerve
		if(xM < 875 && xM > 838 && yM < 336 && yM > 302){
			Monster m = this.world.getMonsters().get(6);
			sendMonster(6);
			
			monsterName = m.getName();
			income = Integer.toString(m.getIncome());
			destructionGain = Integer.toString(m.getDestructionGain());
			hp = Integer.toString(m.getHp());
			prixMonster = Integer.toString(m.getPrice());
			speed = Float.toString(m.getSpeed());
			
			shopTower=false;
			shopMonster= true;
		}
		
		//Invisible
		if(xM < 919 && xM > 887 && yM < 333 && yM > 303){
			Monster m = this.world.getMonsters().get(1);
			sendMonster(1);
			
			monsterName = m.getName();
			income = Integer.toString(m.getIncome());
			destructionGain = Integer.toString(m.getDestructionGain());
			hp = Integer.toString(m.getHp());
			prixMonster = Integer.toString(m.getPrice());
			speed = Float.toString(m.getSpeed());
			
			shopTower=false;
			shopMonster= true;
		}
		
		//Passe tourelle
		if(xM < 832 && xM > 791 && yM < 396 && yM > 368){
			Monster m = this.world.getMonsters().get(2);
			sendMonster(2);
			
			monsterName = m.getName();
			income = Integer.toString(m.getIncome());
			destructionGain = Integer.toString(m.getDestructionGain());
			hp = Integer.toString(m.getHp());
			prixMonster = Integer.toString(m.getPrice());
			speed = Float.toString(m.getSpeed());
			
			shopTower=false;
			shopMonster= true;
		}
		
		//Rapide
		if(xM < 875 && xM > 841 && yM < 402 && yM > 368){
			Monster m = this.world.getMonsters().get(3);
			sendMonster(3);
			
			monsterName = m.getName();
			income = Integer.toString(m.getIncome());
			destructionGain = Integer.toString(m.getDestructionGain());
			hp = Integer.toString(m.getHp());
			prixMonster = Integer.toString(m.getPrice());
			speed = Float.toString(m.getSpeed());
			
			shopTower=false;
			shopMonster= true;
		}
		
		//Resistant
		if(xM < 924 && xM > 888 && yM < 403 && yM > 368){
			Monster m = this.world.getMonsters().get(4);
			sendMonster(4);

			monsterName = m.getName();
			income = Integer.toString(m.getIncome());
			destructionGain = Integer.toString(m.getDestructionGain());
			hp = Integer.toString(m.getHp());
			prixMonster = Integer.toString(m.getPrice());
			speed = Float.toString(m.getSpeed());
			
			shopTower=false;
			shopMonster= true;
		}
		
		//Volant
		if(xM < 833 && xM > 789 && yM < 449 && yM > 427){
			Monster m = this.world.getMonsters().get(5);
			sendMonster(5);
			
			monsterName = m.getName();
			income = Integer.toString(m.getIncome());
			destructionGain = Integer.toString(m.getDestructionGain());
			hp = Integer.toString(m.getHp());
			prixMonster = Integer.toString(m.getPrice());
			speed = Float.toString(m.getSpeed());
			
			shopTower=false;
			shopMonster= true;
		}
		
		//Play/Pause
		if(xM < 48 && xM > 7 && yM < 32 && yM > 4){
			
			if(!pause){
				pause = true;
				world.getInfoGame().pauseGame();
			}
			else{
				pause = false;
				world.getInfoGame().resumeGame();
			}
		}
		
		//System.out.println("x : " + xM +" et y : "+ yM);
		
	}
	
	public void shiftPressed(){
		shift = true;
	}
	
	public void shiftUp(){
		shift = false;
	}
	
	public void update(float delta){
		processInput();
	}

	private void processInput() {
		if (state == State.BUILDING){
			Vector2 vectCase = new Vector2((int)(Gdx.input.getX()/ppux), (int)((height-Gdx.input.getY())/ppuy));
			rendererC.chooseCaseMode(vectCase, towerSelect, world.isFree(vectCase, towerSelect.getSize()));
		}
	}
	
	public void TowerPressed() {
		Tpressed = true;
		Mpressed = false;
		state = State.BUILDING;
		if (towerSelect == null)
			towerSelect = world.getTower(0);
	}
	
	public void MonsterPressed(){
		Mpressed = true;
		Tpressed = false;
	}
	
	public void buildTower(int n){
		state = State.BUILDING;
		towerSelect = world.getTower(n);
	}
	
	public void send(int n){
		if(Tpressed){
			buildTower(n);
		}
		if(Mpressed){
			sendMonster(n);
		}
	}
	
	public void sendMonster(int n){
		if (world.isOnline()){
			if (world.getInfoGame().getPlayerGold() < world.getMonsters().get(n).getPrice()){
				rendererC.warningMode("Vous n'avez pas assez d'argent");
			}
			else{
				world.getReseau().sendMonster(n);
				world.getInfoGame().buySomething(world.getMonsters().get(n).getPrice());
				world.getInfoGame().addIncome(world.getMonsters().get(n).getIncome());
			}
		}
	}

}
