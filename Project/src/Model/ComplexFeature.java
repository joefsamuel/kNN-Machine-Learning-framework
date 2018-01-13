package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * ComplexFeature is a Feature that doesn't have a value but it has a list of child features
 * 
 * @author Dhrubomoy Das Gupta
 *
 */
public class ComplexFeature extends Feature {
	private List<Feature> childFeatures;
	private static DistanceMetric DEFAULT_DISTANCE_METRIC = new EuclideanDistanceMetric();
	private String type;

	/**
	 * Constructor for ComplexFeature
	 * @param name	Name of the complex feature
	 */
	public ComplexFeature(String name) {
		// Complex feature won't have a value.
		// it will only have a list of child features.
		super(name);
		this.setDistanceMetric(DEFAULT_DISTANCE_METRIC);
		this.childFeatures = new ArrayList<Feature>();
		this.type = "Complex";
	}
	
	public void setChildFeatures(ArrayList<Feature> cf) {
		this.childFeatures = cf;
	}
	
	/**
	 * Constructor for ComplexFeature
	 * @param name	Name of the complex feature
	 * @param distancemetric to be used for calculation
	 */
	public ComplexFeature(String name, DistanceMetric metric) {
		// Complex feature won't have a value.
		// it will only have a list of child features.
		super(name);
		this.setDistanceMetric(metric);
		this.childFeatures = new ArrayList<Feature>();
		this.type = "Complex";
	}

	/**
	 * Adds a feature to the list of child feature
	 * @param	A feature we want to add as a child
	 */
	public void addChildFeature(Feature f) {
		childFeatures.add(f);
	}
	
	/**
	 * returns the type of feature
	 * @return String type of feature
	 */
	public String getType() {
		return type;
	}

	/**
	 * Returns the list of child features
	 * @return	ArrayList of child feature
	 */
	public List<Feature> getChildFeatures() {
		return this.childFeatures;
	}
	
	public String toXML() {
		String xml = "<ComplexFeature name=\"" + this.getName() + "\" distanceMetric=\"" + this.getDistanceMetric().toString() + "\">";
		for(Feature f: this.childFeatures) {
			xml += f.toXML();
		}
		xml += "</ComplexFeature>";
		return xml;
	}
	
	public String toString() {
		String str = this.getName() + ": [";
		for(Feature f: childFeatures) {
			str += f.toString() + " ";
		}
		return str + "]";
	}

}
