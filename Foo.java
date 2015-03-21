import java.util.*;

class Foo {
    private Quagent q;
    private Events events;

    public static void main(String[] args) throws Exception {
		Foo rw = new Foo();
		rw.run();
    }

    void run() throws Exception {
		try {
			// connect to a new quagent
			q = new Quagent();
			while(true) {
				events = q.events();
				String[] eventStrings = new String[events.size()];
				for (int i = 0; i < events.size(); i++) {
					eventStrings[i] = events.eventAt(i);
				}
				EventHandler eh = new EventHandler(eventStrings);
				q.walk(30);
				eh = null;
			}
		}	 
		catch (QDiedException e) { // the quagent died -- catch that exception
			System.out.println("bot died!");
			q.close();
			
			// since our bot died try to run another one
			run();
		}
		catch (Exception e) { // something else went wrong???
			System.out.println("system failure: "+e);
			System.exit(0);
		}
    }

    class EventHandler {
		double[] objectDistance = new double[3];
		Boolean stopped = false;

		public EventHandler(String[] events) {
			try {
		    	// printEvents(events);
		    	for (int i = 0; i < events.length; i++) {
					String current = events[i];
		    		System.out.println(current);
		    		if (current.indexOf("rays") >= 0) {
		    			objectDistance = parseRays(current);
		    		}
		    		else if (current.indexOf("STOPPED") >= 0) {
		    			stopped = true;
		    		}
		    		else if (current.indexOf("foo") >= 0) {
		    			//do something
		    		}
		    	}
		    }
		    catch (Exception e) {
		    	System.out.println("thanks for holding my hand java");
		    }
		}

	    public double[] parseRays(String rayResponse) throws Exception {
			//Variables to store the distances
			double distanceStraight = 0.0;
			double distanceRight = 0.0;
			double distanceLeft = 0.0;
			//Split message up into tokens
			String[] tokens = rayResponse.split("[()\\s]+");
			//Get distance straight ahead
			double xstraight = Double.parseDouble(tokens[6]);
			double ystraight = Double.parseDouble(tokens[7]);
			distanceStraight = Math.sqrt(xstraight*xstraight + ystraight*ystraight);
			//Get distance to the left
			double xleft = Double.parseDouble(tokens[11]);
			double yleft = Double.parseDouble(tokens[12]);
			distanceLeft = Math.sqrt(xleft*xleft + yleft*yleft);
			//Get distance to the right
			double xright = Double.parseDouble(tokens[21]);
			double yright = Double.parseDouble(tokens[22]);
			distanceRight = Math.sqrt(xright*xright + yright*yright);
			//For distance testing:
			// System.out.println("Distance Straight: " + distanceStraight);
			// System.out.println("Distance Right: " + distanceRight);
			// System.out.println("Distance Left: " + distanceLeft + "\n\n");
			double[] distanceArr = {distanceStraight, distanceLeft, distanceRight};
			return distanceArr;
		}
	}
}

