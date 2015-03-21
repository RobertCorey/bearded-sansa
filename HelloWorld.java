public class HelloWorld {

    public static void main(String[] args) {
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

}
