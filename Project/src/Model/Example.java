package Model;

/**
 * Example is an abstract class described by an input (set of features)
 * @author Dhrubomoy Das Gupta
 *
 */
//abstract
public abstract class Example {
	private String name;
	private Input input;
	
	/**
	 * Constructor for Example abstract class.
	 * @param name	Name of the feature
	 * @param input	Input which is a collection of features
	 */
	public Example(String name, Input input) {
		this.name = name;
		this.input = input;
	}

	/**
	 * Returns the name of the example
	 * @return	Name of the example
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of an example
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the input of an example
	 * @return	Input
	 */
	public Input getInput() {
		return input;
	}
	
	public void setInput(Input input) {
		this.input = input;
	}

	/**
	 * Returns the sum of distances of the input features from the corresponding input features of another example.
	 * @param e	Example that we are calculating the sum of distances from
	 * @return	The sum of distances
	 */
	public Double getSumOfDistances(Example e) {
		double sum = 0;
		for(int i=0; i<input.getFeatures().size(); i++) {
			sum += input.getFeatures().get(i).getDistance(e.getInput().getFeatures().get(i));
		}
		return sum;
	}
	
}
