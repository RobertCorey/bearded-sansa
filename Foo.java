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
			ArrayList<String> al = new ArrayList<String>();
			al.add("w");
			al.add("w");
			al.add("w");
			al.add("t");

			int tick = 0;

			while(true) {
				events = q.events();
				if (tick == al.size()) {
					tick = 0;
				}
				if (al.get(tick) == "w") {
					q.walk(30);
				} else {
					q.turn(90);
					al.add("w");
				}
				q.pickup("tofu");
				System.out.println(al);
				printEvents(events);
				tick += 1;
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

    public void printEvents(Events events) {
		System.out.println("List of Events:");
		for (int ix = 0; ix < events.size(); ix++) {
			System.out.println(events.eventAt(ix));
		}
    }
}


