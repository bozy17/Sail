
public class Level {
	
	public Boat boat;
	public Pirate pirate1;
	public Pirate pirate2;
	public Pirate pirate3;
	public LevelGate levelgate;
	public Wind wind;
	public Land land;
	public Treasure treasure;
	public float ellapsedTime;
	public int COURT_WIDTH;
	public int COURT_HEIGHT;


	public Level (Boat boat, Pirate pirate1, Pirate pirate2, Pirate pirate3,
			LevelGate levelgate, Wind wind, Land land, Treasure treasure, 
			float ellapsedTime, int COURT_WIDTH, int COURT_HEIGHT) {
		this.boat = boat;
		this.pirate1 = pirate1;
		this.pirate2 = pirate2;
		this.pirate3 = pirate3;
		this.levelgate = levelgate;
		this.wind = wind;
		this.land = land;
		this.treasure = treasure;
		this.ellapsedTime = ellapsedTime;
		this.COURT_WIDTH = COURT_WIDTH;
		this.COURT_HEIGHT = COURT_HEIGHT;
	}
}
