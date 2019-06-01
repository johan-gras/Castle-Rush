package core.model;

public class Time {

	private int secondes = 0;
	private int minutes = 0;
	private int heures = 0;
	
	/**
	 * Constructeur
	 * @param time
	 */
	public Time(float time){
		
		for(int i = 0; i < (int)time; i++){
			
			this.secondes ++;
			
			if(this.secondes > 60){
				this.secondes = 0;
				this.minutes += 1;
			}
			
			if(this.minutes > 60){
				this.minutes = 0;
				this.heures += 1;
			}
		}
	}

	public int getSecondes() {
		return secondes;
	}

	public void setSecondes(int secondes) {
		this.secondes = secondes;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public int getHeures() {
		return heures;
	}

	public void setHeures(int heures) {
		this.heures = heures;
	}

	@Override
	public String toString() {
		return "" + this.getHeures() + " Heures |" + this.getMinutes() + " Min | " + this.getSecondes() + " Sec";
	}
	
	
}
