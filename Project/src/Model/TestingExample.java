package Model;

/**
 * A testing example is a type of example that has values for Input, but its
 * output is what we want to predict. Output is described by the predctiedOutput attribute
 * 
 * @author dhrubomoydasgupta
 *
 */
public class TestingExample extends Example {
	private Feature predictedOutput;
	
	/**
	 * Constructor for class TestingExample
	 * @param name	Name of the TestingExample object
	 * @param input	Input
	 */
	public TestingExample(String name, Input input) {
		super(name, input);
	}
	
	public TestingExample(String name) {
		this(name, null);
	}
	
	/**
	 * Sets the predictedOutput for the TestingExample object
	 * @param output	Output feature
	 */
	public void setPredictedOutput(Feature output) {
		this.predictedOutput = output;
	}
	
	/**
	 * Returns the predictedOutput
	 * @return	Predicted Output
	 */
	public Feature getPredictedOutput() {
		return this.predictedOutput;
	}
	
	/**
	 * If prediction was made then this method returns the a short description of the prediction
	 * 
	 * @return String result
	 */
	public String getPredictionString() {
		String result = "";
		if(predictedOutput != null) {
			result += "Prediction for Testing Example '"+getName()+"' is: ";
			if(predictedOutput.getStringValue() != null) {
				result += getPredictedOutput().getStringValue();
			} else {
				result += getPredictedOutput().getValue();
			}
			
		} else {
			result += "Testing Example '"+getName()+"' has not been predicted yet!";
		}
		return result;
	}
	
	/**
	 * Returns the value of the simple feature
	 * @return Value of the simple feature
	 */
	public String toString() {		
		return getName() + getInput().getFeatures().toString();
	}

	public String toXML() {
		String xml = "<TestingExample name=\"" + this.getName() + "\">";
		xml += this.getInput().toXML();
		xml += "</TestingExample>";
		return xml;
	}

}
