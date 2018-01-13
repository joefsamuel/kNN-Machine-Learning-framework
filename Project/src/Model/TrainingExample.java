package Model;

/**
 * Training Example is a type of example that also has an output (a feature).
 * Output is a feature whose value we use in order to predict the output of a testing example
 * 
 * @author Dhrubomoy Das Gupta
 *
 */
public class TrainingExample extends Example implements Comparable<TrainingExample> {
	private Feature output;
	// sum of distances for each feature from the corresponding feature of a test example
	private double sumOfDistances;

	/**
	 * Constructor for class TrainingExample
	 * @param name		Name of the training example object
	 * @param input		Inputs of the example
	 * @param output	Output feature of the example
	 */
	public TrainingExample(String name, Input input, Feature output) {
		super(name, input);
		this.output = output;
	}
	
	public TrainingExample(String name) {
		this(name, null, null);
	}
	
	/**
	 * Sets the output feature
	 * @param output	The output feature
	 */
	public void setOutput(Feature output) {
		this.output = output;
	}
	//test
	/**
	 * Returns the output
	 * @return	Output feature
	 */
	public Feature getOutput() {
		return output;
	}
	
	/**
	 * Returns the sum of distances of the input features from the corresponding input features of another example.
	 * @return	Sum of distances 
	 */
	public double getSumOfDistances() {
		return sumOfDistances;
	}

	/**
	 * Sets the sumOfDistance attribute
	 * @param sumOfDistances	Sum of distances
	 */
	public void setSumOfDistances(double sumOfDistances) {
		this.sumOfDistances = sumOfDistances;
	}

	/**
	 * Compares this training example  with the specified training example for order.
	 * @return	a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object
	 */
	@Override
	public int compareTo(TrainingExample trainExample) {
		if(this.sumOfDistances < trainExample.getSumOfDistances()) {
			return -1;
		} else if(this.sumOfDistances == trainExample.getSumOfDistances()) {
			return 0;
		}
		return 1;
	}
	
	/**
	 * ToString method to concatenate result string.
	 * @return	String  of the input data
	 */
	public String toString() {		
		return getName() + getInput().getFeatures().toString() + " " + getOutput().toString();
	}

	public String toXML() {
		String xml = "<TrainingExample name=\"" + this.getName() + "\">";
		xml += this.getInput().toXML();
		xml += "<Output>";
		xml += this.output.toXML();
		xml += "</Output>";
		xml += "</TrainingExample>";
		return xml;
	}

}
