package Model;

/**
 * This class implements DistanceMetric in order to calculate distance based on AbsoluteDifference between two simple features
 * @author dhrubomoydasgupta
 *
 */
public class AbsoluteDistanceMetric implements DistanceMetric {

	/**
	 * Returns the distance between two feature based on the absolute difference between them.
	 * @param f1	the first feature
	 * @param f2	the second feature
	 * @return		distance between them
	 */
	public double getDistanceMetric(Feature f1, Feature f2) {
		return Math.abs(f1.getValue() - f2.getValue());
	}
	
	public String toString() {
		return "Absolute";
	}
	
}
