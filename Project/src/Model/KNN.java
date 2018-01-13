package Model;

import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.DefaultListModel;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * KNN class is responsible for comparing the distances of training examples from testing example
 * and making prediction either by taking average (if output feature is continuous) or by performing
 * vote (if output is discrete).
 * 
 * @author Dhrubomoy Das Gupta
 * @version Milestone2
 * 
 */
@SuppressWarnings("unused")
public class KNN{
	private DefaultListModel<TrainingExample> trainExamples;
	private DefaultListModel<TestingExample> testExamples;
	private PredictionType predictionType;
	private int k;
	private static String DEFAULT_OUTPUT_FEATURE_NAME = "output_feature";
	private DataPrep dp;
	
	/**
	 * Creates KNN object based on the parameters
	 * @param trainExamples		An DefaultListModel of training examples
	 * @param testExamples		An DefaultListModel of testing examples
	 * @param k					value of k
	 */
	public KNN(DefaultListModel<TrainingExample> trainExamples, DefaultListModel<TestingExample> testExamples, int k) {

		this.predictionType = PredictionType.AVERAGE;	//default
		this.trainExamples = trainExamples;
		this.testExamples = testExamples;
		this.k = k;
	}
	
	/**
	 * Creates KNN object based on the parameters
	 * @param trainExamples		An DefaultListModel of training examples
	 * @param testExamples		An DefaultListModel of testing examples
	 * @param predictionType	PredictionType decides whether to predict based on average (if continuous output) or by voting (if discrete output)
	 * @param k					value of k
	 */
	public KNN(DefaultListModel<TrainingExample> trainExamples, DefaultListModel<TestingExample> testExamples, PredictionType predictionType, int k) {
		this(trainExamples, testExamples, k);
		this.predictionType = predictionType;
	}
	
	public void setPredictionType(PredictionType pt) {
		this.predictionType = pt;
	}

	/**
	 * Returns a list of TrainingExamples used by the KNN
	 * @return 	DefaultListModel of TrainingExamples
	 */
	public DefaultListModel<TrainingExample> getTrainingExamples(){
		return trainExamples;
	}
	
	/**
	 * Returns a list of TestingExamples used by the KNN
	 * @return 	DefaultListModel of TestingExamples
	 */
	public DefaultListModel<TestingExample> getTestExamples(){
		return testExamples;
	}
	
	/**
	 * Returns value of k for the KNN object
	 * @return 	Value of k
	 */
	public int getK() {
		return this.k;
	}
	
	/**
	 * Sets value of attribute k
	 * @param k
	 */
	public void setK(int k) {
		this.k = k;
	}
	
	/**
	 * Makes prediction with a new value of k
	 * @param k
	 */
	public void makePrediction(int k) {
		setK(k);
		makePrediction();
	}
	
	/**
	 * Makes prediction for the output feature of the testing examples. First it loops through each testing example,
	 * finds distances between each training example and the testing example, compares the training examples,
	 * and then makes prediction based on the training examples that have the minimum distances!
	 * 
	 * After creating a KNN object this method <b>must be</b> called first. It sets the output feature of each testing
	 * example to the prediction value.
	 */
	public void makePrediction() {
		dp = new DataPrep(trainExamples, testExamples, this);
		if(k > trainExamples.size()) {
			System.err.println("k has to be smaller than or equal number of training examples!");
		}
		for(int i=0; i<testExamples.size(); i++) {
			ArrayList<TrainingExample> trainExamplesArray = new ArrayList<>();
			for(int y = 0;y<trainExamples.getSize();y++){ trainExamplesArray.add(trainExamples.get(y)); }
			DistanceCalculation dc = new DistanceCalculation(trainExamplesArray, testExamples.get(i));
			dc.associateDistancesWithTrainExamples();
			Collections.sort(trainExamplesArray);
			trainExamples = new DefaultListModel<>();
			for(int z = 0;z<trainExamplesArray.size();z++) {
				trainExamples.addElement(trainExamplesArray.get(z));
			}
			
			// If k == 1 then it doesn't matter if output feature is discrete or continuous.
			// the training example that has the minimum distance will be chosen. So we do not worry about
			// whether to calculate average or perform vote!
			if(k==1) {
				double output = trainExamples.get(0).getOutput().getValue();
				Feature outputFeature = new SimpleFeature(DEFAULT_OUTPUT_FEATURE_NAME, output);
				testExamples.get(i).setPredictedOutput(outputFeature);
			} else {
				if(predictionType == PredictionType.AVERAGE) {
					predictByCalculatingAvarage(i);
				} else if(predictionType == PredictionType.VOTING) {
					predictByPerformingVote(i);
				}
			}
		}
	}
	
	// Set the string value predicted output of testing example
	private void setStringOutputForTestingExample() {
		// check if output of training example has string value
		if(this.trainExamples.get(0).getOutput().getStringValue() != null) {
			HashMap<String, Integer> outputStringValueMap = dp.getOutputStringValueMap();
			for(int i=0; i<this.testExamples.size(); i++) {
				Feature predictedOutput = this.testExamples.get(i).getPredictedOutput();
				for(Map.Entry<String, Integer> entry : outputStringValueMap.entrySet()) {
					if(predictedOutput.getValue() == entry.getValue()) {
						predictedOutput.setStringValue(entry.getKey());
					}
				}
			}
		}
	}
	
	/**
	 * This method predicts by performing vote (if k>1). It loops through the output value of sorted training examples,
	 * and checks which of the examples occurs the most, then chooses that value as prediction.
	 * @param index		index of the testing example
	 */
	private void predictByPerformingVote(int index) {
		ArrayList<TrainingExample> trainExamplesArray = new ArrayList<>();
		for(int y = 0;y<trainExamples.getSize();y++){ trainExamplesArray.add(trainExamples.get(y)); }
		ArrayList<Double> minTrainExampleOutputs = (ArrayList<Double>) trainExamplesArray.stream()
				.limit(k)
				.map(m -> m.getOutput().getValue())
				.collect(Collectors.toList());
		int occurrences = 0;
		double output = minTrainExampleOutputs.get(0);
		for(int i=0; i<minTrainExampleOutputs.size(); i++) {
			int newOccurrences = Collections.frequency(minTrainExampleOutputs, minTrainExampleOutputs.get(i));
			if(newOccurrences >= occurrences) {
				occurrences = newOccurrences;
				output = minTrainExampleOutputs.get(i);
			}
		}
		Feature outputFeature = new SimpleFeature(DEFAULT_OUTPUT_FEATURE_NAME, output);
		testExamples.get(index).setPredictedOutput(outputFeature);
	}

	/**
	 * This method predicts by calculating the average
	 * @param index
	 */
	private void predictByCalculatingAvarage(int index) {
		double sum = 0;
		for(int i=0; i<k; i++) {
			sum += trainExamples.get(i).getOutput().getValue();
		}
		testExamples.get(index).setPredictedOutput(new SimpleFeature("", sum/k));
	}
	
	/**
	 * this method returns information about knn object in XML format
	 * @return	info about knn object in xml format
	 */
	public String toXML() {
		String xml = "<knn" + " k=\"" + k + "\">";
		for(int i=0; i<this.trainExamples.size(); i++) {
			xml += this.trainExamples.get(i).toXML();
		}
		for(int i=0; i<this.testExamples.size(); i++) {
			xml += this.testExamples.get(i).toXML();
		}
		xml += "</knn>";
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + indentXMLString(xml);
	}
	
	private String indentXMLString(String input, int indent) {
	    try {
	        Source xmlInput = new StreamSource(new StringReader(input));
	        StringWriter stringWriter = new StringWriter();
	        StreamResult xmlOutput = new StreamResult(stringWriter);
	        TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        transformerFactory.setAttribute("indent-number", indent);
	        Transformer transformer = transformerFactory.newTransformer(); 
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
	        transformer.transform(xmlInput, xmlOutput);
	        return xmlOutput.getWriter().toString();
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}

	private String indentXMLString(String input) {
	    return indentXMLString(input, 4);
	}
	
	/**
	 * Returns a short description of the prediction and its quality (error)
	 * One <b>must</b> call makePrediction() method first in order to get the prediction!
	 * @return		string describing the prediction and error
	 */
	public String toString() {
		String result = "";
		if(!this.testExamples.isEmpty()) {
			setStringOutputForTestingExample();
			ArrayList<TrainingExample> trainExamplesArray = new ArrayList<>();
			for(int y = 0;y<trainExamples.getSize();y++){ trainExamplesArray.add(trainExamples.get(y)); }
			ArrayList<TestingExample> testExamplesArray = new ArrayList<>();
			for(int y = 0;y<testExamples.getSize();y++){ testExamplesArray.add(testExamples.get(y)); }
			result = "Prediction: (knn = "+this.getK()+")\n";
			for(TestingExample tstExmpl : testExamplesArray) {
				result += "  "+tstExmpl.getPredictionString() + "\n";
			}
			KnnError kErr = new KnnError(trainExamplesArray, k, predictionType);
			result += kErr.toString();
		} else {
			result = "No testing example found! Nothing to predict!";
		}
		return result;
	}
	
	// A method to make the initialization of the housing example easier! 
	private static Example initializeHouseExample(String houseName, int xVal, int yVal, int sqftVal, String ageVal, int priceVal) {
		Feature coord = new ComplexFeature("coord");
		DistanceMetric edm = new EuclideanDistanceMetric();		// Set distance Metric for coordinate
		coord.setDistanceMetric(edm);
		Feature xCoord = new SimpleFeature("xCoord", xVal); Feature yCoord = new SimpleFeature("yCoord", yVal);
		coord.addChildFeature(xCoord); coord.addChildFeature(yCoord);
		
		Feature sqft = new SimpleFeature("sqft", sqftVal);
		Feature age = new SimpleFeature("age", ageVal);
		
		DefaultListModel<Feature> features = new DefaultListModel<>();
		features.addElement(coord); features.addElement(sqft); features.addElement(age);	// Add the three features
		Input input = new Input(features);
		Feature output = new SimpleFeature("output", priceVal);
		Example houseExample;
		
		if(priceVal == -1) {
			houseExample = new TestingExample(houseName, input);
		} else {
			houseExample = new TrainingExample(houseName, input, output);
		}
		return houseExample;
	}

	public static void main(String[] args) {
		// Initialize Training Examples
		Example house1 = initializeHouseExample("h1", 12, 25, 1200, "new", 500000);
		Example house2 = initializeHouseExample("h2", 10, 50, 1000, "old", 300000);
		Example house3 = initializeHouseExample("h3", 30, 100, 800, "new", 400000);
		DefaultListModel<TrainingExample> trainExamples = new DefaultListModel<>();
		trainExamples.addElement((TrainingExample) house1);
		trainExamples.addElement((TrainingExample) house2);
		trainExamples.addElement((TrainingExample) house3); 
		
		// Initialize Training Examples
		Example house4 = initializeHouseExample("h4", 15, 20, 1000, "new", -1);
		Example house5 = initializeHouseExample("h5", 32, 110, 820, "old", -1);
		DefaultListModel<TestingExample> testExamples = new DefaultListModel<>();
		testExamples.addElement((TestingExample) house4); 
		testExamples.addElement((TestingExample) house5);
		
		KNN knn = new KNN(trainExamples, testExamples, PredictionType.AVERAGE, 1);
		System.out.println(knn.toXML());
	}

}
