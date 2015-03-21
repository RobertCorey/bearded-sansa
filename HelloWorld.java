public class HelloWorld {

    public static void main(String[] args) {
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
			return double distanceArr = {distanceStraight, distanceLeft, distanceRight};
		}
    }

}
