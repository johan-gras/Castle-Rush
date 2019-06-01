package core.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import core.model.units.Tower;

public class ControllerRendering {
	enum Mode {
		NORMAL, CASECHOISSING, WARNING
	}
	
	private Mode mode = Mode.NORMAL;
	private SpriteBatch batch;
	private int width, height;
	private float ppux, ppuy; //Pixel Par Unite
	
	/** for case choissing **/
	private Vector2 vectCase;
	private Tower towerChoose;
	private boolean availableCase;
	
	/** for warning mode **/
	private String textWarn;
	private BitmapFont font = new BitmapFont();
	private float timer;
	
	public ControllerRendering(SpriteBatch batch) {
		this.batch = batch;
		
		font.setColor(new Color(1f, 0f, 0f, 0.6f));
		font.setScale(1f, 1f);
	}
	
	public void setSize(int w, int h, float ppux, float ppuy){
		this.width = 380*2;
		this.height = 300*2;
		this.ppux = ppux;
		this.ppuy = ppuy;
	}
	
	/**
	 * Permet d'afficher le rendu des messages d'erreur par exemple
	 * @param delta
	 */
	public void render(float delta){
		if (mode == Mode.CASECHOISSING){
			if (availableCase)
				batch.draw(towerChoose.getTextureNormal(), vectCase.x * ppux, vectCase.y * ppuy, towerChoose.getSize() * ppux, towerChoose.getSize() * ppuy);
			else{
				batch.setColor(Color.RED);
				batch.draw(towerChoose.getTextureNormal(), vectCase.x * ppux, vectCase.y * ppuy, towerChoose.getSize() * ppux, towerChoose.getSize() * ppuy);
				batch.setColor(Color.WHITE);
			}
		}
		
		else if (mode == Mode.WARNING){
			font.draw(batch, textWarn, 200, 500);
			timer += delta;
			if (timer >= 3)
				mode = Mode.NORMAL;
		}
	}
	
	public void normalMode(){
		mode = Mode.NORMAL;
	}
	
	public void chooseCaseMode(Vector2 vectCase, Tower towerChoose, boolean available){
		mode = Mode.CASECHOISSING;
		this.vectCase = vectCase;
		this.towerChoose = towerChoose;
		this.availableCase = available;
	}
	
	public void warningMode(String text){
		mode = Mode.WARNING;
		textWarn = text;
		timer = 0f;
	}

}
