package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Feature is an abstract class that has a distanceMetric we can associate it with\
 * 
 * @author Dhrubomoy Das Gupta
 *
 */
public abstract class Feature {
	private String name;
	private DistanceMetric distanceMetric;
	
	/**
	 * Constructor for Feature
	 * @param name	Name of the Feature
	 */
	public Feature(String name) {
		this.name = name;
	}
	
	/**
	 * Sets name of the feature
	 * @param n	name
	 */
	public void setName(String n) {
		this.name = n;
	}
	
	/**
	 * Returns name of the feature
	 * @return	Name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the distance metric
	 * @return	Distance Metric associated with the feature
	 */
	public DistanceMetric getDistanceMetric() {
		return this.distanceMetric;
	}
	public void setDistanceMetric(DistanceMetric dm) {
		this.distanceMetric = dm;
	}
	
	/**
	 * Returns the distance from another feature
	 * @param f	Feature we want to get the distance from
	 * @return	Distance
	 */
	public double getDistance(Feature f) {
		return this.distanceMetric.getDistanceMetric(this, f);
	}
	
	/**
	 * Dummy method. This method is overridden in SimpleFeature class.
	 * ComplexFeature doesn't have a value. It only has child features (either complex or simple)
	 * @return
	 */
	public double getValue() {
		return -1;
	}
	
	public void setValue(double val) {}
	public void setStringValue(String str) {}
	
	/**
	 * Dummy method. Overridden in Complex feature.
	 * @param f
	 */
	public void addChildFeature(Feature f) {}
	
	/**
	 * Dummy method. Overridden in Simple feature.
	 * @param f
	 */
	public String getType() {
		return "";
	}
	
	/**
	 * Dummy method. Overridden in Simple feature.
	 * @param f
	 */
	public String getStringValue() {
		return null;
	}
	
	/**
	 * Dummy method. Overridden in Complex feature.
	 * @return child features
	 */
	public List<Feature> getChildFeatures() {
		return null;
	}
	
	public void setChildFeatures(ArrayList<Feature> cf) {}
	
	
	/**
	 * Returns the name of the feature
	 * @return Name of the feature
	 */
	public abstract String toString();

	public abstract String toXML();
}
