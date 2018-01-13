package Model;

/**
 * This class implements DistanceMetric interface and calculates distance when polar coordinates
 * (distance and angle in degrees) are given.
 * @author Dhrubomoy Das Gupta
 *
 */
public class PolarCoordinateDistanceMetric implements DistanceMetric {

	@Override
	public double getDistanceMetric(Feature f1, Feature f2) {
		double result = 0;
		double r1 = f1.getChildFeatures().get(0).getValue();
		double r2 = f2.getChildFeatures().get(0).getValue();
		double theta1 = f1.getChildFeatures().get(1).getValue();
		double theta2 = f2.getChildFeatures().get(1).getValue();
		double theta_diff = Math.toRadians(theta1) - Math.toRadians(theta2);
		result = Math.pow(r1, 2) + Math.pow(r2, 2) - (2 * r1 * r2 * Math.cos(theta_diff));
		return Math.sqrt(result);
	}
	
	public String toString() {
		return "Polar";
	}
	
}
