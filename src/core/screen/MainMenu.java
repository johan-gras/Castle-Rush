package core.screen;


import javax.swing.JOptionPane;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import core.TowerDefense;
import core.model.Reseau;
import core.view.WorldRenderer;


public class MainMenu implements Screen {

	
	private SpriteBatch batch = new SpriteBatch();
	private TowerDefense tf;
	
	//---Musiques----
	private Music music;
	private Music music2;
	private Sound sound;

	//--Les boutons----
	private Sprite boutonSprite;
	private Sprite boutonRetour;
	private Sprite boutonCliqueRetour;
	private Sprite boutonCliqueSprite;
	private Sprite arrierePlanSprite;
	private Sprite titre;
	private Sprite score;

	//---Polices d'écriture---
	private BitmapFont font;
	private BitmapFont fontRetour;

	private float largeur_ecran;
	private float hauteur_ecran;
	
	//----Coordonnées des boutons---

	private float yposBouton1;
	private float xposBouton1;

	private float yposBouton2;
	private float xposBouton2;

	private float yposBouton3;
	private float xposBouton3;

	private float yposBoutonServeur;
	private float xposBoutonServeur;

	private float yposBoutonClient;
	private float xposBoutonClient;

	private float yposBoutonCredits;
	private float xposBoutonCredits;

	private float yposBoutonScorage;
	private float xposBoutonScorage;

	private float yposBoutonRetour;
	private float xposBoutonRetour;

	private float yposTitre;
	private float xposTitre;

	private float xDecalage;
	private float yDecalage;
	
	//---Les textes des boutons----

	private String texteBouton1;
	private String texteBouton2;
	private String texteBouton3;

	private String texteBoutonServeur;
	private String texteBoutonClient;

	private String texteBoutonCredits;
	private String texteBoutonScorage;

	private String texteBoutonRetour;

	//----Gestion interfaces---

		//---> Pour savoir dans quelle page on se situe
	private boolean isMultijoueur = false;
	private boolean isDivers = false;
	private boolean isCredits = false;
	private boolean isSolo = false;
	private boolean isRetour = true;
	private boolean isRejoindre = false;
	private boolean isHeberger = false;
	private boolean isScores = false;
	
		//---->Pour savoir quelle bouton a été cliqué
	private boolean cliqueBouton1;
	private boolean cliqueBouton2;
	private boolean cliqueBouton3;
	
	private int page = 0;
	
	private JOptionPane jop = new JOptionPane();

	/**
	 * Constructeur
	 * @param tf
	 */
	public MainMenu(TowerDefense tf){
		this.tf = tf;
	}

	/**
	 * Méthode qui permet d'initialiser toutes les images, musiques et polices utilisées
	 */
	public void create(){
		//--Musiques--
		music = Gdx.audio.newMusic(Gdx.files.internal("music/menu.mp3"));
		sound = Gdx.audio.newSound(Gdx.files.internal("music/click.mp3"));
		music2 = Gdx.audio.newMusic(Gdx.files.internal("music/combat.mp3"));

		music.setLooping(true);
		music2.setLooping(true);
		music.setVolume(0.2f);
		music2.setVolume(0.2f);
		music.play();
		
		// Obtenir la taille de l'Ecran actuelle
		largeur_ecran = Gdx.graphics.getWidth();
		hauteur_ecran = Gdx.graphics.getHeight();

		// Charger Texture dans Sprite
		boutonSprite =new Sprite(new Texture(Gdx.files.internal("images/menu/bouton.png"))) ;
		boutonRetour =new Sprite(new Texture(Gdx.files.internal("images/menu/bouton.png"))) ;
		boutonCliqueRetour =new Sprite(new Texture(Gdx.files.internal("images/menu/boutonclique.png"))) ;
		boutonCliqueSprite = new Sprite(new Texture(Gdx.files.internal("images/menu/boutonclique.png"))) ;
		arrierePlanSprite = new Sprite(new Texture(Gdx.files.internal("images/menu/arriereplan.png")));
		titre = new Sprite(new Texture(Gdx.files.internal("images/menu/titre.png")));
		score =  new Sprite(new Texture(Gdx.files.internal("images/menu/gameover.png")));

		//---Coordonnées des sprites----
		boutonSprite.setSize(xUnite(128), yUnite(64));
		boutonCliqueSprite.setSize(xUnite(128), yUnite(64));
		arrierePlanSprite.setSize(xUnite(480), yUnite(320));
		titre.setSize(xUnite(200), yUnite(100));

		// La police pour le texte
		font = new BitmapFont(Gdx.files.internal("images/polices/myfont.fnt"), false);
		font.setColor(Color.WHITE);
		font.setScale(xUnite(0.5f), yUnite(0.5f)); // d�finir la taille du texte selon l'�cran
		
		fontRetour = new BitmapFont(Gdx.files.internal("images/polices/myfont.fnt"), false);
		fontRetour.setScale(0.9f);

		xDecalage = xUnite(15); // pour g�rer le d�calage de positionnement entre font et sprite
		yDecalage = yUnite(38);

		// Texte des boutons 1, bouton 2, bouton 3
		texteBouton1 = "      Survival Game";
		texteBouton2 = " Attendre un joueur";
		texteBouton3 = "Rejoindre un joueur";

		xposBouton1 = xUnite(50); // Position du bouton 'StartGame'
		yposBouton1 = yUnite(150);

		xposBouton2 = xUnite(50); // Position du bouton 'Multijoueur'
		yposBouton2 = yUnite(100);

		xposBouton3 = xUnite(50); // Position du bouton 'Cr�dit'
		yposBouton3 = yUnite(50);

		xposBouton3 = xUnite(50); // Position du bouton 'Cr�dit'
		yposBouton3 = yUnite(50);

		xposBoutonServeur = xUnite(50); // Position du bouton 'Serveur'
		yposBoutonServeur = yUnite(70);

		xposBoutonClient = xUnite(50); // Position du bouton 'Client'
		yposBoutonClient = yUnite(130);

		xposBoutonCredits = xUnite(50); // Position du bouton 'Option'
		yposBoutonCredits = yUnite(70);

		xposBoutonScorage = xUnite(50); // Position du bouton 'Scorage'
		yposBoutonScorage = yUnite(130);

		xposBoutonRetour = xUnite(0); // Position du bouton 'Scorage'
		yposBoutonRetour = yUnite(10);

		xposTitre = xUnite(0);  
		yposTitre = yUnite(200);

	}

	/**
	 * Méthode qui controle les évenements liées à la souris
	 */
	public void manipulerMenu()
	{
		Gdx.input.setInputProcessor(new InputProcessor() {

			public boolean touchUp(int x, int y, int pointer, int bouton) {
				if(x>xUnite(57) && x < xUnite(170) && y<yUnite(162) && y> yUnite(113))
				{
					// le bouton 1 (startGame) a été cliqué
					page=1;
				}
				if(x>xUnite(57) && x < xUnite(170) && y>yUnite(165) && y<yUnite(214))
				{
					// le bouton 2 (Options) a été cliqué
					page=2;
				}

				if(x>xUnite(57) && x < xUnite(170) && y>yUnite(217) && y<yUnite(266))
				{
					// le bouton 3 (Crédits) a été cliqué    
					page=3;
				}

				cliqueBouton1 = false;
				cliqueBouton2 = false;
				cliqueBouton3 = false;

				return false;
			}

			public boolean touchDown(int x, int y, int pointer, int bouton) {

				//123 en longueur et 49 en largeur
				if(x>xUnite(57) && x < xUnite(170) && y<yUnite(162) && y> yUnite(113))
				{
					cliqueBouton1=true;
				}
				if(x>xUnite(57) && x < xUnite(170) && y>yUnite(165) && y<yUnite(214))
				{
					cliqueBouton2=true;
				}
				if(x>xUnite(57) && x < xUnite(170) && y>yUnite(217) && y<yUnite(266))
				{
					cliqueBouton3=true;
				}
				return false;
			}

			public boolean touchDragged(int arg0, int arg1, int arg2) {
				return false;
			}

			public boolean scrolled(int arg0) {
				return false;
			}

			public boolean mouseMoved(int arg0, int arg1) {
				return false;
			}

			public boolean keyUp(int arg0) {
				return false;
			}

			public boolean keyTyped(char arg0) {
				return false;
			}

			public boolean keyDown(int arg0) {
				return false;
			}
		});
	}

	/**
	 * Méthode qui permet de dessiner le fond du menu
	 */
	public void dessinerMenu()   // dessiner le menu
	{

		batch.begin();  // obligatoire pour commencer un dessin sur le SpriteBatch

		// arrierePlan
		arrierePlanSprite.draw(batch);
		//----Titre
		titre.setPosition(xposTitre, yposTitre); 
		titre.draw(batch);                           


		// bouton 1
		if(!cliqueBouton1)
		{
			boutonSprite.setPosition(xposBouton1, yposBouton1);// fixer la position
			boutonSprite.draw(batch);                          // puis le dessiner
		}else
		{
			boutonCliqueSprite.setPosition(xposBouton1, yposBouton1);
			boutonCliqueSprite.draw(batch);                         
		}

		// bouton 2
		if(!cliqueBouton2) 
		{
			boutonSprite.setPosition(xposBouton2, yposBouton2);// fixer la position
			boutonSprite.draw(batch);                          // puis le dessiner
		}else
		{
			boutonCliqueSprite.setPosition(xposBouton2, yposBouton2);
			boutonCliqueSprite.draw(batch);
		}

		// bouton 3
		if(!cliqueBouton3)
		{
			boutonSprite.setPosition(xposBouton3, yposBouton3); // fixer la position
			boutonSprite.draw(batch);                           // puis le dessiner
		}else
		{
			boutonCliqueSprite.setPosition(xposBouton3, yposBouton3);
			boutonCliqueSprite.draw(batch);
		}


		// texte du bouton1

		font.draw(batch, ""+texteBouton1, xposBouton1+xDecalage, yposBouton1+yDecalage);

		// texte du bouton2

		font.draw(batch, ""+texteBouton2, xposBouton2+xDecalage, yposBouton2+yDecalage);

		// texte du bouton3

		font.draw(batch, ""+texteBouton3, xposBouton3+xDecalage, yposBouton3+yDecalage);

		batch.end();  // obligatoire pour finir le dessin sur un SpriteBatch

	}

	
	
	/**
	 * Méthode du render
	 */
	public void render (float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(1, 1, 1, 1);

		manipulerMenu();  // gestion des input

		switch(page)  // dans quelle page je me situe ?
		{
		case 0:              // Contenu de la page menu
			dessinerMenu();

			break;
		case 1:   
			music2.play();
			sound.play();
			tf.setScreen(tf.game);
			this.dispose();
			break;

		case 2:
			jop.showMessageDialog(null, "Voici votre IP locale :D " + Reseau.getLocalHostLANAddress().getHostAddress(), "IP du pc locale :D ><", JOptionPane.INFORMATION_MESSAGE);
			music2.play();
			sound.play();
			tf.reseau = new Reseau(1, null);
			tf.setScreen(tf.game);
			this.dispose();
			break;

		case 3:
			String ip = jop.showInputDialog(null, "Rentrer l'IP de l'hote de la partie : ", "IP du m�chant ><", JOptionPane.QUESTION_MESSAGE);
			music2.play();
			sound.play();
			tf.reseau = new Reseau(2, ip);
			tf.setScreen(tf.game);
			this.dispose();
			break;
		}
	}

	public void resize (int width, int height) {
		//stage.setViewport(width, height, false);
	}

	public void dispose() {
		font.dispose();
		batch.dispose();
		music.dispose();

	}

	public void show() {
		// TODO Auto-generated method stub
		this.create();
	}

	public void hide() {
		// TODO Auto-generated method stub

	}

	public void pause() {
		// TODO Auto-generated method stub

	}

	public void resume() {
		// TODO Auto-generated method stub

	}
	
	// Fonction qui maintien le rapport entre les positions Y
	// vis-à-vis de la taille de l'Ecran
	private float xUnite(float x)
	{
		return x*largeur_ecran/480f;
	}

	// Fonction qui maintien le rapport entre les positions Y
	// vis-à-vis de la taille de l'Ecran
	private float yUnite(float y)
	{
		return y*hauteur_ecran/320;
	}
}