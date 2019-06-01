package core.model;

public class InfoGame {
	public enum State {
		RUNNING, GAMEOVER, PAUSE
	}

	//----Stats de la partie-----
	private String playerName;
	private int playerGold;
	private int income; //Per seconds
	private float secondTime = 0f;
	private float time = 0f;
	private int goldCollect;
	private int monstersKill = 0;
	private int towersSet = 0;
	private State state = State.RUNNING;

	/**
	 * Constructeur
	 * @param playerName
	 * @param playerGold
	 * @param income
	 */
	public InfoGame(String playerName, int playerGold, int income) {
		this.playerName = playerName;
		this.playerGold = playerGold;
		this.income = income;
		this.goldCollect = playerGold;
	}

	/**
	 * Permet d'acutaliser les infos du jeu
	 * @param delta
	 */
	public void udpate(float delta){
		time += delta;
		secondTime += delta;
		if (secondTime >= 4){
			secondTime--;
			playerGold += income;
			goldCollect += income;
		}
	}

	public String getPlayerName() {
		return playerName;
	}

	public int getPlayerGold() {
		return playerGold;
	}

	public int getIncome() {
		return income;
	}

	public void setIncome(int income) {
		this.income = income;
	}

	public void buySomething(int gold){
		playerGold -= gold;
	}

	public void monsterKill(int gold){
		playerGold += gold;
		goldCollect += gold;
		monstersKill++;
	}

	public void towerSet(){
		towersSet++;
	}

	public void gameOver(){
		state = State.GAMEOVER;
	}
	
	public boolean isGameOver(){
		return (state == State.GAMEOVER);
	}

	public boolean isGameRunning(){
		return (state == State.RUNNING);
	}
	
	public boolean isGamePaused(){
		return (state == State.PAUSE);
	}
	
	public void pauseGame(){
		state = State.PAUSE;
	}
	
	public void resumeGame(){
		
		state = State.RUNNING;
	}

	public Time getTime() {
		return new Time(this.time);
	}

	public int getGoldCollect() {
		return goldCollect;
	}

	public int getMonstersKill() {
		return monstersKill;
	}

	public int getTowersSet() {
		return towersSet;
	}

	@Override
	public String toString() {
		return "Pseudo : " + playerName + " | Temps : " + this.getTime().getHeures() + " Heures |" + this.getTime().getMinutes() + " Min | " + this.getTime().getSecondes() + " Sec";
	}
	
	public void addIncome(int income){
		this.income += income;
	}
	
	public void lessIncome(int income){
		this.income -= income;
	}

}