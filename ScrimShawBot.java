import java.util.*;
import java.lang.Math.*;

class ScrimShawBot extends Quagent {
	
	//Private Global variables
    private Quagent q;
    private Events events;
    //event trackers
	private int ticks = 0;
	private int wallhuggerState = 0;
	private int consecutiveStops = 0;

    public static void main(String[] args) throws Exception {
		new ScrimShawBot();
    }

    ScrimShawBot () throws Exception {
		super();
		String stage = "grabperimeter";
		EventHandler oldEh = null;
		
		try {
			while(true) {
				ticks += 1;
				//get events and convert to eventHandler object
				events = this.events();
				String[] eventStrings = new String[events.size()];
				for (int i = 0; i < events.size(); i++) {
					eventStrings[i] = events.eventAt(i);
				}
				EventHandler eh = new EventHandler(eventStrings);

				//limit operations that clog queue
				if (ticks % 4 == 0) {
					this.pickup("tofu");
					this.rays(4);
				}

				//execute chosen strategy
				switch (stage) {
					case "grabperimeter":
						stage = grabperimeter(eh);
						break;
					case "wallhugger":
						stage = wallhugger(eh);
						break;
					case "dartout":
						stage = dartout(eh);
						break;
					case "dartin":
						stage = dartin(eh);
						break;
				}
				System.out.println(stage);
				oldEh = eh;
				Thread.currentThread().sleep(200);
			}
		}	 
		catch (QDiedException e) { // the quagent died -- catch that exception
			System.out.println("bot died!");
			this.close();
		}
		catch (Exception e) { // something else went wrong???
			System.out.println("system failure: "+e);
			System.exit(0);
		}
    }

	/*Private functions for states and other functions
	 * Each case has their own private function to take care
	 * of the function of the state.	
	*/
	private String wallhugger(EventHandler eh) throws Exception {
		wallhuggerState += 1;
		if (eh.dRight > 200) {
			this.turn(-90);
			this.walk(30);
			Thread.currentThread().sleep(200);
			return "wallhugger";
		}
		if (eh.stopped == true) {
			this.turn(90);
			this.walk(30);
			Thread.currentThread().sleep(200);
			return "wallhugger";
		} 
		if (wallhuggerState >= 10 && eh.dStraight >= 300) {
			System.out.println("DARTY @" + eh.dStraight);
			this.walk(30);
			this.turn(90);
			wallhuggerState = 0;
			Thread.currentThread().sleep(200);
			return "dartout";	
		}
		else {
			this.walk(30);
			return "wallhugger";
		}
	}
	private String dartout(EventHandler eh) throws Exception {
		if (eh.stopped) {
			this.turn(180);
			Thread.currentThread().sleep(200);
			return "dartin";
		} else {
			this.walk(30);
			Thread.currentThread().sleep(200);
			return "dartout";
		}
	}

	private String dartin(EventHandler eh) throws Exception {
		if (eh.stopped) {
			this.turn(90);
			Thread.currentThread().sleep(200);
			return "wallhugger";
		} else {
			this.walk(30);
			Thread.currentThread().sleep(200);
			return "dartin";
		}
	}

	private String grabperimeter(EventHandler eh) throws Exception {
		this.walk(30);
		this.turn(90);
		this.walk(30);
		this.turn(-90);
		if (eh.stopped) {
			consecutiveStops += 1;
			System.out.println(consecutiveStops);
		} else {
			consecutiveStops = 0;
		}
		if (consecutiveStops >= 10) {
			return "wallhugger";
		}
		return "grabperimeter";
	}

	//Private function for death of quagent once all objects are found
	private void die() throws Exception {
		
		//Kill the quagent
		this.close();
	} 
	//Abstraction of event handler object
    class EventHandler {
		double dStraight, dLeft, dRight, dBehind, dArea;
		Boolean stopped = false;
		Boolean rayResponse = false;

		public EventHandler(String[] events) {
			try {
		    	// printEvents(events);
		    	for (int i = 0; i < events.length; i++) {
					String current = events[i];
		    		if (current.indexOf("rays") >= 0) {
		    			parseRays(current);
		    			rayResponse = true;
		    		}
		    		else if (current.indexOf("TELL STOPPED 0.00") >= 0) {
		    			stopped = true;
		    		}
		    		else if (current.indexOf("foo") >= 0) {
		    			//do something
		    		}
		    	}
		    }
		    catch (Exception e) {
		    	System.out.println("foo");
		    }
		}
		//cherry pick ray coordinates from string
	    public void parseRays(String rayResponse) throws Exception {
			//Split message up into tokens
			String[] tokens = rayResponse.split("[()\\s]+");

			this.dStraight = getRayDistance(tokens[6], tokens[7]);
			this.dLeft = getRayDistance(tokens[11], tokens[12]);
			this.dBehind = getRayDistance(tokens[17], tokens[18]);
			this.dRight = getRayDistance(tokens[21], tokens[22]);

			this.dArea = this.dStraight + this.dLeft + this.dRight + this.dBehind;
		}
		//takes x y in string returns distance
		private double getRayDistance(String x, String y) throws Exception {
			double xstraight = Double.parseDouble(x);
			double ystraight = Double.parseDouble(y);
			return Math.sqrt(xstraight*xstraight + ystraight*ystraight);
		}
	}
}
