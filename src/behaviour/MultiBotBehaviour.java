package basis;
/*
 * class of actions for handling with other bots
 */
public class MultiBotStrategy {
	
	private boolean botCheck =  false;
	public boolean crashedIntoBot = false;
	
	public enum STRATEGY {Defense, Aggressive, Cautious};
	STRATEGY strategy;
	 
	
	public MultiBotStrategy() {
	}
	
	public void setCrashedIntoBot() {
		crashedIntoBot =  true;
	}
	
	
	/**
	 * policy for Multi Bot Enviroments
	 * 
	 * Strategy Defense: If Bot hits another Bot, it will first check via US, if way isn't blocked anymore
	 * Strategy Aggressive: Bot quickly speeds up
	 * Strategy Cautious: Bot slowly approaches bot and look for itself, if bot is still there.
	 * @param r
	 */
	public void policy(RobotState r) {
	
	if (crashedIntoBot) {
		r.halt();
		
		switch(strategy) {
		case Defense: defenseStrategy(r); break;
		case Aggressive: aggressiveStrategy(r); break;
		case Cautious: cautiousStrategy(r); break;
			}
		}
	}
	
	private void defenseStrategy(RobotState r) {
		r.backwardBlocking(100, 500);
		r.rotate(-90);
		while (r.getUltraSonic() < 255) {
			
		}
		r.rotate(90);
		r.forward(30);
	}
	
	private void aggressiveStrategy(RobotState r) {
		r.rotate(360);
		r.forwardBlocking(100, 2000);
	}
	
	private void cautiousStrategy(RobotState r) {
		r.backwardBlocking(10, 1000);
		r.forward(10);
		if (r.crashedIntoWall()) {
			cautiousStrategy(r);
		}
	}
	//1 is defensive, 2 is Aggressive and 3 is Cautious
	public void setStrategy(int strategy) {
		switch(strategy) {
		case(1): this.strategy = STRATEGY.Defense; break;
		case(2): this.strategy = STRATEGY.Aggressive; break;
		case(3): this.strategy = STRATEGY.Cautious; break;
		}
	}
	
}
