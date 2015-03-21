/*************************************************************************
* ObjectFinder Midterm
*************************************************************************/

// NOTE: This quagent assumes a rectangular room with no obstacles.

import java.util.*;
import java.lang.Math.*;

class ObjectFinderMidterm extends Quagent {
	
	//Private Global variables
    private Quagent q;
    private Events events;
	public static int DIST = 500;
	private static int CORNER_DISTANCE = 20;
	private static int DISTANCE_STRAIGHT_TO_WALL = 80;
	private int turnAngle = 0;
	private Boolean corner = false;
	private int numOfObjects = 8;
	private int objectsFound = 0;
	private Boolean direction = false;
	
    public static void main(String[] args) throws Exception {
		new ObjectFinderMidterm();
    }

    ObjectFinderMidterm () throws Exception {
		
		// run the constructor of the super class
		super();

		try {
		
			//Start walking
			//this.walk(DIST);
		
			// loop forever
			while(true) {
				while (objectsFound != numOfObjects){
				
					//Start walking
					this.walk(DIST);
					
					//Shoot out rays
					this.rays(4);
					
					//Shoot out a radius
					// this.radius(60);
					
					//Handle Stopped
					//handleStopped(this.events());
					
					//Handle Radius
					// handleRadius(this.events());
					
					//Handle Rays
					// handleRays(this.events());
					this.pickup("tofu");
					handleEvents(this.events());
					//Give the engine time to think
					Thread.currentThread().sleep(200);
				}
				
				//Quagent must die
				die();
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
	
    public void handleEvents(Events events) throws Exception {
		for (int ix = 0; ix < events.size(); ix++) {
			String e = events.eventAt(ix);
			if (e.indexOf("rays") >= 0) {
				handleRays(e);
			} else if (e.indexOf("OK  (do pickup tofu)") >= 0) {
				System.out.println("Yummy Yummy in my tummy!!");
				objectsFound++;
			}
		}
    }
	//Public function to handle the stopped event
    public void handleStopped(Events events) throws Exception {
	
		// get the individual events from the event object
		for (int ix = 0; ix < events.size(); ix++) {
			
			//Events object
			String e = events.eventAt(ix);
		
			//Once a stopped event is detected
			if (e.indexOf("STOPPED") >= 0) {
				if(direction){
					
					//Turn 90
					this.turn(90);
				
					//Walk a small distance
					this.walk(50);

					//Sleep to let the quagent walk
					Thread.currentThread().sleep(800);
					
					//Turn another 90
					this.turn(90);
					
					//Set direction to false
					direction = false;
					
				}
				else{
				
					//Turn 90
					this.turn(90);
				
					//Walk a small distance
					this.walk(50);
					
					//Sleep to let the quagent walk
					Thread.currentThread().sleep(800);
					
					//Turn another 90
					this.turn(90);
					
					//Set direction to true
					direction = true;
				
				}
			}
		}
    }
	
	//Public function to handle the stopped event
    public void handleRadius(Events events) throws Exception {
	
		// get the individual events from the event object
		for (int ix = 0; ix < events.size(); ix++) {
			
			//Events object
			String e = events.eventAt(ix);
		
			//Once a stopped event is detected
			if (e.indexOf("radius") >= 0) {
				
				String[] tokens = e.split("[()\\s]+");
				
				int numOfObjects = Integer.parseInt(tokens[4]);
				
				if (numOfObjects > 0){
				
					this.stand();
				
					//Pick up the object
					this.pickup("tofu");
					
					//Pause the quagent
					Thread.currentThread().sleep(500);
					
					//Add one to objects picked up
					objectsFound++;
					
					System.out.println("Object " + objectsFound + " found!");
				
				}
				
			}
		}
    }
	
	//Public function to handle the rays events
	public void handleRays(String e) throws Exception {
		
		//Variables to store the distances
		double distanceStraight = 0.0;
		double distanceRight = 0.0;
		double distanceLeft = 0.0;
	
		//Split message up into tokens
		String[] tokens = e.split("[()\\s]+");

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
		System.out.println("Distance Straight: " + distanceStraight);
		System.out.println("Distance Right: " + distanceRight);
		System.out.println("Distance Left: " + distanceLeft + "\n\n");
		
		
		//Determine navigation for quagent
		if(distanceStraight < DISTANCE_STRAIGHT_TO_WALL){
			if(distanceRight < CORNER_DISTANCE){
				
				if (!corner){
				
					//Turn 90 degrees and continue walking
					this.turn(90);
					this.stand();
					
					//Add 1 to corner variable
					corner = true;
					
				}
				else{
				
					//Continue walking in new direction
					this.turn(90);
					this.stand();
					this.walk(10);
					Thread.currentThread().sleep(800);
					this.turn(90);
					this.stand();
					
					//Reset corner to zero
					corner = false;
					
				}
			}
			else if (distanceLeft < DISTANCE_STRAIGHT_TO_WALL){
			
				if (!corner){
				
					//Turn 90 degrees and continue walking
					this.turn(-90);
					this.stand();
					
					//Add 1 to corner variable
					corner = true;
					
				}
				else{
				
					//Continue walking in new direction
					this.turn(-90);
					this.stand();
					this.walk(10);
					Thread.currentThread().sleep(800);
					this.turn(-90);
					this.stand();
					
					//Reset corner to zero
					corner = false;
					
				}
			}
			else{
			
				if(direction){
				
					//Turn 90
					this.turn(90);
				
					//Walk a small distance
					this.walk(10);
					
					//Sleep to let the quagent walk
					Thread.currentThread().sleep(800);
					
					//Turn another 90
					this.turn(90);
					
					//Set direction to false
					direction = false;
				
				}
				else{
			
					//Turn 90
					this.turn(-90);
				
					//Walk a small distance
					this.walk(8);
					
					//Sleep to let the quagent walk
					Thread.currentThread().sleep(800);
					
					//Turn another 90
					this.turn(-90);
					
					//Set direction to true
					direction = true;
			
				}
			}
		}
	}
	
	private void findAngle(double x, double y){
		
		Double angle = 0.0;
		
		if (turnAngle == 0){
		
			//Calculate the angle 
			angle = Math.toDegrees(Math.atan2(y,x));
			
			turnAngle = angle.intValue();
			
			System.out.println("Angle: " + turnAngle);
		
		}
	}
	
	public double findDistance (double x, double y){
		return Math.sqrt(x*x + y*y);
	}
	
	
	//Private function to turn
	private void turn() throws Exception {
					
		//Local variable for turn radius
		int turn = (int)(Math.random()*180.0 - 90.0);
			
		//Turn
		this.turn(turn);
		
	}
	
	//Private function for death of quagent once all objects are found
	private void die() throws Exception {
		
		//Kill the quagent
		this.close();
	} 

	//Function to print out events (for testing)
    public void printEvents(Events events) {
		System.out.println("List of Events:");
		for (int ix = 0; ix < events.size(); ix++) {
			System.out.println(events.eventAt(ix));
		}
    }
}
