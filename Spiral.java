import java.util.*;

class Spiral {
    private Quagent q;
    private Events events;

    public static void main(String[] args) throws Exception {
		Spiral rw = new Spiral();
		rw.run();
    }

    void run() throws Exception {
		try {
			// connect to a new quagent
			q = new Quagent();
			ArrayList<String> al = new ArrayList<String>();
			al.add("w");
			al.add("w");
			al.add("w");
			al.add("t");

			int tick = 0;

			while(true) {
				events = q.events();

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

    public void handleEvents(Events events) {
    	printEvents(events);
    	for (int i = 0; i < events.size(); i++) {
    		String current = events.eventAt(i);
    		if (current.indexOf("rays") >= 0) {
    				
    		}
    	}
    }

    public void printEvents(Events events) {
		System.out.println("List of Events:");
		for (int ix = 0; ix < events.size(); ix++) {
			System.out.println(events.eventAt(ix));
		}
    }

    public double[] parseRays(String rayResponse) throws Exception {
		double distanceStraight = 0.0;
		double distanceRight = 0.0;
		double distanceLeft = 0.0;
		String[] tokens = rayResponse.split("[()\\s]+");

		double xstraight = Double.parseDouble(tokens[6]);
		double ystraight = Double.parseDouble(tokens[7]);
		distanceStraight = Math.sqrt(xstraight*xstraight + ystraight*ystraight);
		double xleft = Double.parseDouble(tokens[11]);
		double yleft = Double.parseDouble(tokens[12]);
		distanceLeft = Math.sqrt(xleft*xleft + yleft*yleft);
		double xright = Double.parseDouble(tokens[21]);
		double yright = Double.parseDouble(tokens[22]);
		distanceRight = Math.sqrt(xright*xright + yright*yright);
		
		return double distanceArr = {distanceStraight, distanceLeft, distanceRight};
	}
}

