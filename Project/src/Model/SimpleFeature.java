package Model;

/**
 * SimpleFeature inherits from Feature. It has a value and distance metric associated with it.
 * 
 * @author Dhrubomoy Das Gupta
 *
 */
public class SimpleFeature extends Feature {
	private double value;
	private String stringValue = null;
	private static DistanceMetric DEFAULT_DISTANCE_METRIC = new AbsoluteDistanceMetric();
	private String type;
	
	/**
	 * Constructor for class SimpleFeature
	 * @param name	Name of the simple feature
	 * @param value	Value of the simple feature
	 */
	public SimpleFeature(String name, double value) {
		super(name);
		this.value = value;
		this.setDistanceMetric(DEFAULT_DISTANCE_METRIC);
	}
	
	public SimpleFeature(String name) {
		this(name, -1);
	}
	
	/**
	 * Constructor for class SimpleFeature
	 * @param name	Name of the simple feature
	 * @param value	Value of the simple feature
	 * @param type Type of the simple feature
	 * @param distanceMetric Type of distance metric to use to compute
	 */
	public SimpleFeature(String name, double value, String type, DistanceMetric distanceMetric) {
		super(name);
		this.value = value;
		this.setDistanceMetric(distanceMetric);
		this.type = type;
	}
	
	/**
	 * Constructor for class SimpleFeature
	 * @param name	Name of the simple feature
	 * @param value	String of the simple feature
	 */
	public SimpleFeature(String name, String inputString) {
		super(name);
		this.value = -1;
		this.stringValue = inputString;
		this.setDistanceMetric(DEFAULT_DISTANCE_METRIC);
	}
	
	/**
	 * Constructor for class SimpleFeature
	 * @param name	Name of the simple feature
	 * @param value	String of the simple feature
	 * @param type String of the simple feature
	 * @param distanceMetric Type of distance metric to use to compute
	 */
	public SimpleFeature(String name, String stringValue, String type, DistanceMetric distanceMetric) {
		super(name);
		this.value = -1;
		this.stringValue = stringValue;
		this.setDistanceMetric(distanceMetric);
		this.type = type;
	}
	
	/**
	 * Returns the type of the simple feature
	 * @return type of the feature
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Returns the value of the simple feature
	 * @return Value of the simple feature
	 */
	public double getValue() {
		return value;
	}
	
	public void setValue(double val) {
		this.value = val;
	}
	
	public void setStringValue(String str) {
		this.stringValue = str;
	}
	
	/**
	 * Returns the String for input
	 * @return String of the simple feature
	 */
	public String getStringValue() {
		return stringValue;
	}

	public String toXML() {
		String xml = "<SimpleFeature name=\"" + this.getName() + "\" distanceMetric=\"" + this.getDistanceMetric().toString() + "\"";
		xml += " stringValue=\"" + this.stringValue + "\"";
		xml += " value=\"" + this.value + "\">";
		xml += "</SimpleFeature>";
		return xml;
	}
	
	public String toString() {
		String str = getName()+": ";
		if(this.getStringValue() != null) {
			str += this.getStringValue();
		} else {
			str += this.getValue();
		}
		return str;
	}

}