package Model;

/**
 * DistanceMetric is an interface class that other distance metric classes can implement 
 * in order to allowing user to set a distance metric to a feature. (makes use of the strategy pattern)
 * 
 * @author Dhrubomoy Das Gupta
 *
 */
public interface DistanceMetric {

	/**
	 * Returns the distance between two feature
	 * @param f1	the first feature
	 * @param f2	the second feature
	 * @return		distance between them
	 */
	public double getDistanceMetric(Feature f1, Feature f2);

}
