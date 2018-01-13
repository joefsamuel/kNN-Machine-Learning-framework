package Model;

/**
 * This class implements DistanceMetric interface and calculates distance based on Euclidean distance
 * @author Dhrubomoy Das Gupta
 *
 */
public class EuclideanDistanceMetric implements DistanceMetric {
	
	/**
	 * Returns the distance between two feature based on the sum of Euclidean distance between them.
	 * @param f1	the first feature
	 * @param f2	the second feature
	 * @return		distance between them
	 */
	public double getDistanceMetric(Feature f1, Feature f2) {
		double sum = 0;
		for(int i=0; i<f1.getChildFeatures().size(); i++) {
			double diff = f1.getChildFeatures().get(i).getValue() - f2.getChildFeatures().get(i).getValue();
			sum += Math.pow(diff, 2);
		}
		return Math.sqrt(sum);
	}
	
	public String toString() {
		return "Euclidean";
	}
	
}
