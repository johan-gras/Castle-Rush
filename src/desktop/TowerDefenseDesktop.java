package desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

import core.TowerDefense;

public class TowerDefenseDesktop {

	public static void main(String[] args) {
		new LwjglApplication(new TowerDefense(), "Castle Rush", 480*2, 320*2);
	}

}
