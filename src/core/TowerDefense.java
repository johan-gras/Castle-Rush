package core;

import com.badlogic.gdx.Game;

import core.model.Reseau;
import core.screen.GameScreen;
import core.screen.MainMenu;

public class TowerDefense extends Game {
	
	//Permet de switcher du menu principal au jeu
	public MainMenu menu;
	public GameScreen game;
	
	//Pour établir le réseau
	public Reseau reseau = null;
	
	/**
	 * Constructeur
	 */
	public void create() {
		menu = new MainMenu(this);
		game = new GameScreen(this);
		setScreen(menu);
	}

}
