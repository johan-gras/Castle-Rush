package core.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

import core.TowerDefense;
import core.controller.WorldController;
import core.model.World;
import core.view.WorldRenderer;

public class GameScreen implements Screen, InputProcessor {
	
	private TowerDefense tf;
	
	private World world;
	private WorldRenderer renderer;
	private WorldController controller;
	
	private int width, height;
	
	public GameScreen(TowerDefense tf){
		
		this.tf = tf;
	}
	
	/********SCREEN METHODS*************/
	
	public void dispose() {
		Gdx.input.setInputProcessor(null);
		
	}

	public void hide() {
		Gdx.input.setInputProcessor(null);
		
	}

	public void pause() {
		// TODO Auto-generated method stub
		//world.getInfoGame().pauseGame();
	}

	public void render(float delta) {
		if (world.getInfoGame().isGameRunning()){
			world.update(delta);
			controller.update(delta);
		}
		//else
			//Gdx.input.setInputProcessor(null);
		
		Gdx.gl.glClearColor(1, 1, 1, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		renderer.render(delta);
		
	}

	public void resize(int width, int height) {
		this.width = width;
		this.height = height;
		controller.setSize(width, height);
		renderer.setSize(width, height);
	}

	public void resume() {
		// TODO Auto-generated method stub
		world.getInfoGame().resumeGame();
	}

	public void show() {
		world = new World(40, 28, tf.reseau);
		renderer = new WorldRenderer(world);
		controller = new WorldController(world, renderer.getControllerRenderer());
		Gdx.input.setInputProcessor(this);
	}
	
	/********INPUT PROCESSOR METHODS***********/
	
	/**
	 * Méthode qui contient les différents raccourcis pour poser une tour
	 */
	public boolean keyDown(int keyCode) {
		  if (keyCode == Keys.T)
		   controller.TowerPressed();
		  else if (keyCode == Keys.M)
		   controller.MonsterPressed();
		  else if (keyCode == Keys.NUMPAD_1 || keyCode == Keys.NUM_1)
		   controller.send(0);
		  else if (keyCode == Keys.NUMPAD_2 || keyCode == Keys.NUM_2)
		   controller.send(1);
		  else if (keyCode == Keys.NUMPAD_3 || keyCode == Keys.NUM_3)
		   controller.send(2);
		  else if (keyCode == Keys.NUMPAD_4 || keyCode == Keys.NUM_4)
		   controller.send(3);
		  else if (keyCode == Keys.NUMPAD_5 || keyCode == Keys.NUM_5)
		   controller.send(4);
		  else if (keyCode == Keys.NUMPAD_6 || keyCode == Keys.NUM_6)
		   controller.send(5); 
		  else if (keyCode == Keys.NUMPAD_7 || keyCode == Keys.NUM_7)
		   controller.send(6); 
		  else if (keyCode == Keys.ESCAPE)
		   controller.escapePressed();
		  else if (keyCode == Keys.SHIFT_LEFT)
		   controller.shiftPressed();
		  
		  return true;
		 }

	public boolean keyTyped(char arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean keyUp(int keyCode) {
		if (keyCode == Keys.SHIFT_LEFT)
			controller.shiftUp();
		
		return true;
	}

	public boolean mouseMoved(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean scrolled(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean touchDown(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean touchDragged(int x, int y, int arg2) {
		return false;
	}

	public boolean touchUp(int x, int y, int arg2, int arg3) {
		controller.mouseClick(x, y);
		return true;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
