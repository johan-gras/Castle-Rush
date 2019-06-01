package core.view;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

import core.controller.WorldController;
import core.model.InfoGame;
import core.model.Unit;
import core.model.World;
import core.model.units.BigUnit;
import core.model.units.Monster;
import core.model.units.Spawn;

public class WorldRenderer {
	
	//-----Game over-----
	
	private Sprite gameovermenu;
	//------Menu logo------
	
	private Sprite play;
	private Sprite stop;
	
	private Sprite fondmenugamefull; //quand le joueur a assez de vie
	private Sprite fondmenugamelow; //Quand le joueur a perdu
	private Sprite fondhaut;
	
	private Sprite fondinfo1;
	private Sprite fondinfo2;
	private Sprite fondinfo3;
	private Sprite fondinfo4;
	private Sprite fondinfo5;
	private Sprite fondinfo6;
	private Sprite fondinfo7;
	
	//---Static
	public static InfoGame bestScore[] = new InfoGame[5];
	
	//---Images pour les Caractéristiques---
	private Sprite imageCaracteristique;
	
	private Sprite dmg;
	private Sprite range;
	private Sprite price;
	private Sprite as;
	
	private Sprite hp;
	private Sprite income;
	private Sprite gain;
	private Sprite vitesse;
	private Sprite priceMonster;
	
	//--------------
	
	private Texture herbe;
	
	private float largeur_ecran;
	private float hauteur_ecran;
	//------------------
	
	//---Images tour---
	
	private Sprite basicTower;
	private Sprite antiInvisible;
	private Sprite antiVol;
	private Sprite impenetrable;
	private Sprite portee;
	private Sprite ralentissante;
	private Sprite fastShooter;
	
	//---Images Monstres---
	
	private Sprite basicMonster;
	private Sprite enerve;
	private Sprite invisible;
	private Sprite passeTourelle;
	private Sprite rapide;
	private Sprite resistant;
	private Sprite volant;
	
	//----------------
	
	private World world;
	private ControllerRendering controllerRenderer;
	
	private OrthographicCamera cam;
	private SpriteBatch batch = new SpriteBatch();
	
	//---Polices----
	private BitmapFont font;
	private BitmapFont gameover;
	private BitmapFont menu;
	private BitmapFont prix;
	private BitmapFont description;
	
	//---Dimensions---
	private int width, height;
	private float ppux, ppuy; //Pixel Par Unite
	
	/**
	 * Constructeur qui sert également à initialiser tous les composants de la classe comme les images, etc...
	 * @param world
	 */
	public WorldRenderer(World world) {
		largeur_ecran = Gdx.graphics.getWidth();
		hauteur_ecran = Gdx.graphics.getHeight();
		
		this.world = world;
		this.cam = new OrthographicCamera(world.getX(), world.getY());
		this.cam.position.set(world.getX()/2, world.getY()/2, 0);
		this.cam.update();
		controllerRenderer = new ControllerRendering(batch);
		
		//----Floor----
		
		herbe = new Texture("images/floor/grass.png");
		
		//----Fond du menu du jeu---
		
		play = new Sprite(new Texture(Gdx.files.internal("images/menu/play.png"))) ;
		play.scale(-0.35f);
		play.setPosition(5, 597);
		
		stop = new Sprite(new Texture(Gdx.files.internal("images/menu/pause.png"))) ;
		stop.scale(-0.35f);
		stop.setPosition(5, 597);
		
		gameovermenu = new Sprite(new Texture(Gdx.files.internal("images/menu/gameover.png"))) ;
		gameovermenu.setPosition(180, 200);
		
		fondmenugamefull = new Sprite(new Texture(Gdx.files.internal("images/menu/fondmenugamefull.png"))) ;
		fondmenugamefull.setPosition(380*2, 0);
		
		fondmenugamelow = new Sprite(new Texture(Gdx.files.internal("images/menu/fondmenugamelow.png"))) ;
		fondmenugamelow.setPosition(380*2, 0);

		fondhaut = new Sprite(new Texture(Gdx.files.internal("images/menu/fondhaut.png"))) ;
		fondhaut.setPosition(0, 600);
		
		fondinfo1 = new Sprite(new Texture(Gdx.files.internal("images/menu/fondinfo.png"))) ;
		fondinfo1.setPosition(50, 597);
		
		fondinfo2 = new Sprite(new Texture(Gdx.files.internal("images/menu/fondinfo.png"))) ;
		fondinfo2.setPosition(200, 597);
		
		fondinfo3 = new Sprite(new Texture(Gdx.files.internal("images/menu/fondinfo.png"))) ;
		fondinfo3.setPosition(350, 597);
		
		fondinfo4 = new Sprite(new Texture(Gdx.files.internal("images/menu/fondinfomenu.png"))) ;
		fondinfo4.setPosition(780, 590);
		
		fondinfo5 = new Sprite(new Texture(Gdx.files.internal("images/menu/fondinfomenu.png"))) ;
		fondinfo5.setPosition(780, 365);
		
		fondinfo6 = new Sprite(new Texture(Gdx.files.internal("images/menu/fondinfomenu2.png"))) ;
		fondinfo6.setPosition(760, 140);
		
		fondinfo7 = new Sprite(new Texture(Gdx.files.internal("images/menu/fondinfo2.png"))) ;
		fondinfo7.setPosition(500, 597);
		
		//--Infos tourelles---
		dmg = new Sprite(new Texture(Gdx.files.internal("images/menu/sword.png"))) ;
		dmg.setPosition(760, 60);
		
		range = new Sprite(new Texture(Gdx.files.internal("images/menu/target.png"))) ;
		range.setPosition(760, 40);
		
		price = new Sprite(new Texture(Gdx.files.internal("images/menu/coins.png"))) ;
		price.setPosition(760, 20);
		
		as = new Sprite(new Texture(Gdx.files.internal("images/menu/as.png"))) ;
		as.setPosition(760, 0);
		
		//--Infos monstres---
		hp = new Sprite(new Texture(Gdx.files.internal("images/menu/hp.png"))) ;
		hp.setPosition(760, 83);
		
		income = new Sprite(new Texture(Gdx.files.internal("images/menu/income.png"))) ;
		income.setPosition(760, 60);
		
		gain = new Sprite(new Texture(Gdx.files.internal("images/menu/gain.png"))) ;
		gain.setPosition(760, 40);
		
		priceMonster = new Sprite(new Texture(Gdx.files.internal("images/menu/coins.png"))) ;
		priceMonster.setPosition(760, 20);
		
		vitesse = new Sprite(new Texture(Gdx.files.internal("images/menu/speed.png"))) ;
		vitesse.setPosition(760, 0);

		//----Images des tourelles pour le shop
		
		basicTower = new Sprite(new Texture(Gdx.files.internal("images/towers/basicTower.png"))) ;
		basicTower.setPosition(790, 540);
		
		antiInvisible = new Sprite(new Texture(Gdx.files.internal("images/towers/antiInvisible.png"))) ;
		antiInvisible.scale(0.8f);
		antiInvisible.setPosition(848, 547);
		
		antiVol = new Sprite(new Texture(Gdx.files.internal("images/towers/antiVol.png"))) ;
		antiVol.scale(0.7f);
		antiVol.setPosition(900, 548);
		
		impenetrable = new Sprite(new Texture(Gdx.files.internal("images/towers/impenetrable.png"))) ;
		impenetrable.scale(0.7f);
		impenetrable.setPosition(797, 485);
		
		portee = new Sprite(new Texture(Gdx.files.internal("images/towers/portee.png"))) ;
		portee.scale(0.7f);
		portee.setPosition(852, 485);
		
		ralentissante = new Sprite(new Texture(Gdx.files.internal("images/towers/ralentissante.png"))) ;
		ralentissante.scale(0.7f);
		ralentissante.setPosition(898, 485);
		
		fastShooter = new Sprite(new Texture(Gdx.files.internal("images/towers/rapide.png"))) ;
		fastShooter.scale(0.7f);
		fastShooter.setPosition(797, 425);
		
		//--Images des monstres
		
		basicMonster = new Sprite(new Texture(Gdx.files.internal("images/monsters/basicMonster.png"))) ;
		basicMonster.setPosition(795, 302);
		
		enerve = new Sprite(new Texture(Gdx.files.internal("images/monsters/enerve.png"))) ;
		enerve.scale(0.3f);
		enerve.setPosition(842, 305);
		
		invisible = new Sprite(new Texture(Gdx.files.internal("images/monsters/invisible.png"))) ;
		invisible.scale(1.0f);
		invisible.setPosition(900, 312);
		
		passeTourelle = new Sprite(new Texture(Gdx.files.internal("images/monsters/passeTourelle.png"))) ;
		passeTourelle.scale(0.5f);
		passeTourelle.setPosition(800, 250);
		
		rapide = new Sprite(new Texture(Gdx.files.internal("images/monsters/rapide.png"))) ;
		rapide.scale(0.8f);
		rapide.setPosition(853, 245);
		
		resistant = new Sprite(new Texture(Gdx.files.internal("images/monsters/resistant.png"))) ;
		resistant.scale(0.9f);
		resistant.setPosition(897, 245);
		
		volant = new Sprite(new Texture(Gdx.files.internal("images/monsters/volant.png"))) ;
		volant.scale(1.0f);
		volant.setPosition(800, 195);
		
		
		//----Police d'écriture---
		font = new BitmapFont(Gdx.files.internal("images/polices/myfont.fnt"), false);
		font.setScale(xUnite(0.5f), yUnite(0.5f)); 
		
		gameover = new BitmapFont(Gdx.files.internal("images/polices/myfont.fnt"), false);
		gameover.setScale(1.5f);
		
		menu = new BitmapFont(Gdx.files.internal("images/polices/myfont.fnt"), false);
		menu.setScale(xUnite(0.5f), yUnite(0.5f));
		
		prix = new BitmapFont(Gdx.files.internal("images/polices/myfont.fnt"), false);
		
		description = new BitmapFont(Gdx.files.internal("images/polices/myfont.fnt"), false);
		description.scale(-0.2f);

	}
	
	/**
	 * Est appelé pour modéliser les différentes fenêtres du jeu
	 */
	public void afficherMenuJeu(){

		if(world.getInfoGame().isGameOver()){
			
			//Permet de remplir un tableau des meilleurs score et vérifie s'il n'est pas meilleur
			for(int i = 0; i < this.bestScore.length; i++ ){
				
				if(this.bestScore[i] == null || this.bestScore[i].getTime().getMinutes() < world.getInfoGame().getTime().getMinutes() && this.bestScore[i].getTime().getSecondes() < world.getInfoGame().getTime().getSecondes() && this.bestScore[i].getTime().getHeures() < world.getInfoGame().getTime().getHeures()){
					
					this.bestScore[i] = world.getInfoGame();
				}
			}
			
			fondmenugamelow.draw(batch);
		}
		else
			fondmenugamefull.draw(batch);
		
		//---Afficher des mobs---
		basicMonster.draw(batch);
		enerve.draw(batch);
		invisible.draw(batch);
		passeTourelle.draw(batch);
		rapide.draw(batch);
		resistant.draw(batch);
		volant.draw(batch);
		
		//--Affichage des tourelles--
		fastShooter.draw(batch);
		ralentissante.draw(batch);
		portee.draw(batch);
		impenetrable.draw(batch);
		antiVol.draw(batch);
		antiInvisible.draw(batch);
		basicTower.draw(batch);
		
		//---Affichages des fenetres
		
		fondhaut.draw(batch);
		
		if(WorldController.pause == false){
			play.draw(batch); 
		}
		else
			stop.draw(batch);
		
		fondinfo2.draw(batch);
		fondinfo3.draw(batch);
		fondinfo1.draw(batch);
		fondinfo4.draw(batch);
		fondinfo5.draw(batch);
		fondinfo6.draw(batch);
		fondinfo6.draw(batch);
		fondinfo7.draw(batch);

	}
	
	public void setSize(int w, int h){
		width = 380*2;
		height = 300*2;
		ppux = (float)width/(float)world.getX();
		ppuy = (float)height/(float)world.getY();
		controllerRenderer.setSize(w, h, ppux, ppuy);
	}
	
	/**
	 * Le render qui se charge d'afficher tout ce beau monde
	 * @param delta
	 */
	public void render(float delta) {
		
		batch.begin();
			afficherMenuJeu();	
			drawFloor();
			drawUnits();
			drawGameOver();
			drawPause();
			controllerRenderer.render(delta);
			drawInfo();
		batch.end();
	}
	
	/**
	 * Permet de modéliser les unités
	 */
	private void drawUnits(){
		//Units
		for (Unit u : world.getMap().values()) {
			if (!(u instanceof BigUnit)){
				batch.draw(u.getTexture(), u.getPosition().x * ppux, u.getPosition().y * ppuy, u.getSize() * ppux, u.getSize() * ppuy);
			}
		}
		
		//Monsters
		for (Monster m : world.getMapM().values())
			batch.draw(m.getTexture(), m.getPositionDraw().x * ppux, m.getPositionDraw().y * ppuy, m.getSize() * ppux, m.getSize() * ppuy);
	}
	
	/**
	 * Méthode qui permet d'afficher les informations en temps réel sur les prix et statistiques des unités, ainsi que le temps, l'argent etc...
	 */
	private void drawInfo(){
		
		//-----Info en haut du jeu----
		
		//Argent
		if(world.getInfoGame().getPlayerGold() <= 999)
			font.draw(batch, "Gold : " + world.getInfoGame().getPlayerGold(), 80, 630);
		else if(world.getInfoGame().getPlayerGold() > 999 && world.getInfoGame().getPlayerGold() <= 9999)
			font.draw(batch, "Gold : " + world.getInfoGame().getPlayerGold(), 77, 630);
		else
			font.draw(batch, "Gold : " + world.getInfoGame().getPlayerGold(), 74, 630);
		
		//Income
		if(world.getInfoGame().getIncome() < 10)
			font.draw(batch, "Income : " + world.getInfoGame().getIncome() + "/s",220, 630);
		else
			font.draw(batch, "Income : " + world.getInfoGame().getIncome() + "/s",215, 630);
		
		//HP
		font.draw(batch, world.getBase().getHp() + " HP", 405, 630);
		
		font.draw(batch, world.getInfoGame().getTime().toString(), 515, 630);
		
		//--------------------Info sur le shop des tours----------
		menu.draw(batch, "Tours", 830, 625);
		
		//Prix de la tourelle de base = 0
		int prix0 = world.getTower(0).getPrice();
		String prixS0 = String.valueOf(prix0);
		prix.draw(batch, prixS0, 792, 595);
		
		//Prix de la tourelle anti invisible = 1
		int prix1 = world.getTower(1).getPrice();
		String prixS1 = String.valueOf(prix1);
		prix.draw(batch, prixS1, 843, 595);
		
		//Prix de la tourelle anti vol= 2
		int prix2 = world.getTower(2).getPrice();
		String prixS2 = String.valueOf(prix2);
		prix.draw(batch, prixS2, 896, 595);
		
		//Prix de la tourelle impenetrable = 3
		int prix3 = world.getTower(3).getPrice();
		String prixS3 = String.valueOf(prix3);
		prix.draw(batch, prixS3, 792, 532);
		
		//Prix de la tourelle grande portee = 4
		int prix4 = world.getTower(4).getPrice();
		String prixS4 = String.valueOf(prix4);
		prix.draw(batch, prixS4, 843, 532);
		
		//Prix de la tourelle freeze = 5
		int prix5 = world.getTower(5).getPrice();
		String prixS5 = String.valueOf(prix5);
		prix.draw(batch, prixS5, 896, 532);
		
		//Prix de la tourelle fast shooter = 6
		int prix6 = world.getTower(6).getPrice();
		String prixS6 = String.valueOf(prix6);
		prix.draw(batch, prixS6, 793, 470);
		
		//-----------------Info sur le shop des monstres----------
		menu.draw(batch, "Monstres", 815, 400);
		
		//Prix du monstre basique = 0
		int prix7 = world.getMonsters().get(0).getPrice();
		String prixS7 = String.valueOf(prix7);
		prix.draw(batch, prixS7, 797, 365);
		
		//Prix du monstre enerve = 6
		int prix13 = world.getMonsters().get(6).getPrice();
		String prixS13 = String.valueOf(prix13);
		prix.draw(batch, prixS13, 845, 365);
		
		//Prix du monstre invisible = 1
		int prix8 = world.getMonsters().get(1).getPrice();
		String prixS8 = String.valueOf(prix8);
		prix.draw(batch, prixS8, 890, 365);
		
		//Prix du monstre passe tourelle = 2
		int prix9 = world.getMonsters().get(2).getPrice();
		String prixS9 = String.valueOf(prix9);
		prix.draw(batch, prixS9, 797, 295);
		
		//Prix du monstre rapide = 3
		int prix10 = world.getMonsters().get(3).getPrice();
		String prixS10 = String.valueOf(prix10);
		prix.draw(batch, prixS10, 845, 295);
		
		//Prix du monstre resistant = 4
		int prix11 = world.getMonsters().get(4).getPrice();
		String prixS11 = String.valueOf(prix11);
		prix.draw(batch, prixS11, 890, 295);
		
		//Prix du monstre volant = 5
		int prix12 = world.getMonsters().get(5).getPrice();
		String prixS12 = String.valueOf(prix12);
		prix.draw(batch, prixS12, 797, 235);
		
		//-------------------Caractéristiques--------------------	
		menu.draw(batch, "Caracteristiques", 770, 175);
		
		if(WorldController.shopTower == true){
		
			if(WorldController.towerSelectName == "Basic Tower"){
				imageCaracteristique = new Sprite(new Texture(Gdx.files.internal("images/towers/basicTower.png"))) ;
				imageCaracteristique.setPosition(770, 110); 
				imageCaracteristique.draw(batch);
				description.draw(batch, "Tourelle basique", 810, 130);
			}
			
			if(WorldController.towerSelectName == "Anti Invisible"){
				imageCaracteristique = new Sprite(new Texture(Gdx.files.internal("images/towers/antiInvisible.png"))) ;
				imageCaracteristique.scale(0.8f);
				imageCaracteristique.setPosition(780, 120); 
				imageCaracteristique.draw(batch);
				description.draw(batch, "Anti-invisible", 810, 130);
			}
			
			if(WorldController.towerSelectName == "Anti Vol"){
				imageCaracteristique = new Sprite(new Texture(Gdx.files.internal("images/towers/antiVol.png"))) ;
				imageCaracteristique.scale(0.8f);
				imageCaracteristique.setPosition(780, 120); 
				imageCaracteristique.draw(batch);
				description.draw(batch, "Anti-aerienne", 810, 130);
			}
				
			if(WorldController.towerSelectName == "Impenetrable"){
				imageCaracteristique = new Sprite(new Texture(Gdx.files.internal("images/towers/impenetrable.png"))) ;
				imageCaracteristique.scale(0.8f);
				imageCaracteristique.setPosition(780, 120); 
				imageCaracteristique.draw(batch);
				description.draw(batch, "Impenetrable", 810, 130);
			}
				
			if(WorldController.towerSelectName == "Grande Portee"){
				imageCaracteristique = new Sprite(new Texture(Gdx.files.internal("images/towers/portee.png"))) ;
				imageCaracteristique.scale(0.8f);
				imageCaracteristique.setPosition(780, 110); 
				imageCaracteristique.draw(batch);
				description.draw(batch, "Grande portee", 810, 120);
			}
					
			if(WorldController.towerSelectName == "Freezing"){
				imageCaracteristique = new Sprite(new Texture(Gdx.files.internal("images/towers/ralentissante.png"))) ;
				imageCaracteristique.scale(0.8f);
				imageCaracteristique.setPosition(780, 110); 
				imageCaracteristique.draw(batch);
				description.draw(batch, "Ralentissante", 815, 120);
			}
				
			if(WorldController.towerSelectName == "Fast Shooter"){
				imageCaracteristique = new Sprite(new Texture(Gdx.files.internal("images/towers/rapide.png"))) ;
				imageCaracteristique.scale(0.8f);
				imageCaracteristique.setPosition(780, 115); 
				imageCaracteristique.draw(batch);
				description.draw(batch, "Tir rapide", 810, 130);
			}
			
			if(WorldController.dmg != null){
				dmg.draw(batch);
				description.draw(batch, WorldController.dmg + " de dommage", 785, 80);
			}
			
			if(WorldController.range != null){
				range.draw(batch);
				description.draw(batch, WorldController.range + " de portee", 785, 60);
			}
			
			if(WorldController.prix != null){
				price.draw(batch);
				description.draw(batch, WorldController.prix + " de gold requis", 785, 40);
			}
			
			if(WorldController.as != null){
				as.draw(batch);
				description.draw(batch, WorldController.as + " de vitesse d'atq.", 785, 20);
			}
		}
		
		if(WorldController.shopMonster == true){
			
			if(WorldController.monsterName == "Basic Monster"){
				imageCaracteristique = new Sprite(new Texture(Gdx.files.internal("images/monsters/basicMonster.png"))) ;
				imageCaracteristique.setPosition(770, 110); 
				imageCaracteristique.draw(batch);
				description.draw(batch, "Monstre basique", 810, 130);
			}
			
			if(WorldController.monsterName == "invisible"){
				imageCaracteristique = new Sprite(new Texture(Gdx.files.internal("images/monsters/invisible.png"))) ;
				imageCaracteristique.scale(0.8f);
				imageCaracteristique.setPosition(780, 120); 
				imageCaracteristique.draw(batch);
				description.draw(batch, "Monstre invisible", 810, 130);
			}
			
			if(WorldController.monsterName == "passeTourelle"){
				imageCaracteristique = new Sprite(new Texture(Gdx.files.internal("images/monsters/passeTourelle.png"))) ;
				imageCaracteristique.scale(0.6f);
				imageCaracteristique.setPosition(775, 120); 
				imageCaracteristique.draw(batch);
				description.draw(batch, "Passe-tourelle", 810, 130);
			}
				
			if(WorldController.monsterName == "rapide"){
				imageCaracteristique = new Sprite(new Texture(Gdx.files.internal("images/monsters/rapide.png"))) ;
				imageCaracteristique.scale(0.8f);
				imageCaracteristique.setPosition(780, 120); 
				imageCaracteristique.draw(batch);
				description.draw(batch, "Monstre rapide", 810, 130);
			}
				
			if(WorldController.monsterName == "resistant"){
				imageCaracteristique = new Sprite(new Texture(Gdx.files.internal("images/monsters/resistant.png"))) ;
				imageCaracteristique.scale(0.8f);
				imageCaracteristique.setPosition(780, 120); 
				imageCaracteristique.draw(batch);
				description.draw(batch, "Monstre resistant", 810, 130);
			}
					
			if(WorldController.monsterName == "volant"){
				imageCaracteristique = new Sprite(new Texture(Gdx.files.internal("images/monsters/volant.png"))) ;
				imageCaracteristique.scale(0.8f);
				imageCaracteristique.setPosition(780, 120); 
				imageCaracteristique.draw(batch);
				description.draw(batch, "Monstre volant", 815, 130);
			}
				
			if(WorldController.monsterName == "enerve"){
				imageCaracteristique = new Sprite(new Texture(Gdx.files.internal("images/monsters/enerve.png"))) ;
				imageCaracteristique.scale(0.4f);
				imageCaracteristique.setPosition(775, 115); 
				imageCaracteristique.draw(batch);
				description.draw(batch, "Monstre enerve", 810, 130);
			}
			
			if(WorldController.hp != null){
				hp.draw(batch);
				description.draw(batch, WorldController.hp + " de vie", 785, 100);
			}
			
			if(WorldController.income != null){
				income.draw(batch);
				description.draw(batch, WorldController.income + " d'income", 785, 80);
			}
			
			if(WorldController.destructionGain != null){
				gain.draw(batch);
				description.draw(batch, WorldController.destructionGain + " de gold acquis", 785, 60);
			}
			
			if(WorldController.prixMonster != null){
				priceMonster.draw(batch);
				description.draw(batch, WorldController.prixMonster + " de gold requis", 785, 40);
			}
			
			if(WorldController.speed != null){
				vitesse.draw(batch);
				description.draw(batch, WorldController.speed + " de vitesse de depl.", 785, 20);
			}
		}
		
	}
	
	/**
	 * Permet d'afficher la fênetre Game Over lorsque le joueur a perdu
	 */
	private void drawGameOver(){
		
		//--------------------Game over------
		if (world.getInfoGame().isGameOver()){
			gameovermenu.draw(batch);
			gameover.draw(batch, "Game Over", 300, 485);
			
			menu.draw(batch, "Pseudo : " + world.getInfoGame().getPlayerName(), 200, 440);
			menu.draw(batch, "Argent total : " + world.getInfoGame().getGoldCollect(), 200, 400);
			menu.draw(batch, "Monstres tues : " + world.getInfoGame().getMonstersKill(), 200, 360);
			menu.draw(batch, "Tours posees : " + world.getInfoGame().getTowersSet(), 200, 320);
			
			menu.draw(batch, "Temps : " + world.getInfoGame().getTime().getHeures() + " Heures |" + world.getInfoGame().getTime().getMinutes() + " Min | " + world.getInfoGame().getTime().getSecondes() + " Sec", 200, 280);
		}
	}
	
	/**
	 * Permet de modéliser la fênetre pause ainsi que diverses informations
	 */
	private void drawPause(){
		
		//--Pause----
		
		if(world.getInfoGame().isGamePaused()){
			
			gameovermenu.draw(batch);
			gameover.draw(batch, "Pause", 325, 485);
			
			menu.draw(batch, "Pseudo : " + world.getInfoGame().getPlayerName(), 200, 440);
			menu.draw(batch, "Argent total : " + world.getInfoGame().getGoldCollect(), 200, 400);
			menu.draw(batch, "Monstres tues : " + world.getInfoGame().getMonstersKill(), 200, 360);
			menu.draw(batch, "Tours posees : " + world.getInfoGame().getTowersSet(), 200, 320);
			
			menu.draw(batch, "Temps : " + world.getInfoGame().getTime().getHeures() + " Heures |" + world.getInfoGame().getTime().getMinutes() + " Min | " + world.getInfoGame().getTime().getSecondes() + " Sec", 200, 280);
		}
	}
	
	/**
	 * Permet de modéliser le sol de la map
	 */
	private void drawFloor() {
		
		for(int i=0;i<world.getX();i++){
			
			for(int j=0;j<world.getY();j++){
				
				batch.draw(herbe, i * ppux, j* ppuy, 1F * ppux, 1F * ppuy);
			}
		}

	}

	public ControllerRendering getControllerRenderer() {
		return controllerRenderer;
	}
	
	//-------------Utils --------------------
	
	// Fonction qui maintien le rapport entre les positions Y
		// vis-à-vis de la taille de l'Ecran
		private float xUnite(float x)
		{
			return x*largeur_ecran/480f;
		}

		// Fonction qui maintien le rapport entre les positions Y
		// vis-�-vis de la taille de l'Ecran
		private float yUnite(float y)
		{
			return y*hauteur_ecran/320;
		}

}
