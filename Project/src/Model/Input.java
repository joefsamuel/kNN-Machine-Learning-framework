package Model;

import javax.swing.DefaultListModel;

/**
 * Input is described by a list of features
 * @author Dhrubomoy Das Gupta
 *
 */
public class Input {
	private DefaultListModel<Feature> features;

	/**
	 * Constructor for Input class
	 * @param features	List of features
	 */
	public Input(DefaultListModel<Feature> features) {
		this.features = features;
	}
	
	public Input() {
		this(null);
	}
	
	public void setFeatures(DefaultListModel<Feature> features) {
		this.features = features;
	}

	/**
	 * Returns the list of features
	 * @return	DefaultListModel of features
	 */
	public DefaultListModel<Feature> getFeatures() {
		return features;
	}
	
	/**
	 * Adds a feature to the current list of features.
	 * @param feature	A feature we want to add
	 */
	public void addFeature(Feature feature) {
		features.addElement(feature);
	}
	
	/**
	 * Adds a feature to the current list of features.
	 * @param feature	A feature we want to add
	 */
	public void removeFeature(Feature feature) {
		features.removeElement(feature);
	}

	public String toXML() {
		String xml = "<Input>";
		for(int i=0; i<this.features.size(); i++) {
			xml += features.get(i).toXML();
		}
		xml += "</Input>";
		return xml;
	}

}
