package core.model.units;

import com.badlogic.gdx.math.Vector2;

import core.model.Unit;

public class BigUnit extends Unit {
	private static final float SIZE = 1f;
	
	private Unit unitLink;
	
	/**
	 * Constructeur
	 * @param position
	 * @param unitLink
	 */
	public BigUnit(Vector2 position, Unit unitLink) {
		super(position);
		this.unitLink = unitLink;
	}
	
	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	}

	public float getSize() {
		return SIZE;
	}

	public Unit getUnitLink() {
		return unitLink;
	}

	public void setUnitLink(Unit unitLink) {
		this.unitLink = unitLink;
	}

}
