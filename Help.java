import java.util.*;
import java.lang.Math.*;

class Help extends Quagent {
	
	//Private Global variables
    private Quagent q;
    private Events events;
	
    public static void main(String[] args) throws Exception {
		new Help();
    }

    Help () throws Exception {
		super();
		String stage = "wallhugger";
		int ticks = 0;

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
					case "wallhugger":
						wallhugger(eh);
						break;
				}
				// Thread.currentThread().sleep(200);
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
	private void wallhugger(EventHandler eh) throws Exception {
		if (eh.dRight > 100) {
			this.turn(-90);
			this.walk(30);
		}
		if (eh.stopped == true) {
			this.turn(90);
			this.walk(30);
		} else {
			this.walk(30);
		}
	}

	//Private function for death of quagent once all objects are found
	private void die() throws Exception {
		
		//Kill the quagent
		this.close();
	} 

    class EventHandler {
		double dStraight, dLeft, dRight, dBehind, dArea;
		Boolean stopped = false;

		public EventHandler(String[] events) {
			try {
		    	// printEvents(events);
		    	for (int i = 0; i < events.length; i++) {
					String current = events[i];
		    		System.out.println(current);
		    		if (current.indexOf("rays") >= 0) {
		    			parseRays(current);
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

	    public void parseRays(String rayResponse) throws Exception {
			//Split message up into tokens
			String[] tokens = rayResponse.split("[()\\s]+");
			//Get distance straight ahead
			double xstraight = Double.parseDouble(tokens[6]);
			double ystraight = Double.parseDouble(tokens[7]);
			this.dStraight = Math.sqrt(xstraight*xstraight + ystraight*ystraight);
			// Get distance behind
			double xbehind = Double.parseDouble(tokens[17]);
			double ybehind = Double.parseDouble(tokens[18]);
			this.dBehind = Math.sqrt(xbehind*xbehind + ybehind*ybehind);
			//Get distance to the left
			double xleft = Double.parseDouble(tokens[11]);
			double yleft = Double.parseDouble(tokens[12]);
			this.dLeft = Math.sqrt(xleft*xleft + yleft*yleft);
			//Get distance to the right
			double xright = Double.parseDouble(tokens[21]);
			double yright = Double.parseDouble(tokens[22]);
			this.dRight = Math.sqrt(xright*xright + yright*yright);
			this.dArea = this.dStraight + this.dLeft + this.dRight + this.dBehind;
		}
	}
}
