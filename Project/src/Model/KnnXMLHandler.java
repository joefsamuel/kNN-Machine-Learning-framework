package Model;

import java.util.*;

import javax.swing.DefaultListModel;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class KnnXMLHandler extends DefaultHandler {
	
	private Feature simpleFeature;
	private Feature complexFeature;
	private ArrayList<Feature> childFeatures;
	private TrainingExample trainingExample;
	private TestingExample testingExample;
	private Input input;
	private Feature output;
	private DefaultListModel<Feature> inputFeatures;
	
	private int k;
	private DefaultListModel<TrainingExample> trainExamples;
	private DefaultListModel<TestingExample> testExamples;
	private KNN knn;
	
	
	/**
	 * Constructor for KnnXMLHandler class. Simply initializes empty training and testing example.
	 * 
	 */
	public KnnXMLHandler() {
		trainExamples = new DefaultListModel<>();
		testExamples = new DefaultListModel<>();
	}
	
	private DistanceMetric getDistanceMetricFromString(String str) {
		DistanceMetric dm = null;
		if(str.equals("Absolute")) {
			dm = new AbsoluteDistanceMetric();
		} else if(str.equals("Euclidean")) {
			dm = new EuclideanDistanceMetric();
		}
		return dm;
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if(qName.equals("knn")) {
			k = Integer.parseInt(attributes.getValue("k"));
		}
		if(qName.equals("TrainingExample")) {
			trainingExample = new TrainingExample(attributes.getValue("name"));
		}
		if(qName.equals("TestingExample")) {
			testingExample = new TestingExample(attributes.getValue("name"));
		}
		if(qName.equals("Input")) {
			input = new Input();
			inputFeatures = new DefaultListModel<Feature>();
		}
		if(qName.equals("Output")) {
			output = new SimpleFeature("");
		}
		if(qName.equals("ComplexFeature")) {
			String name = attributes.getValue("name");
			String distanceMetric = attributes.getValue("distanceMetric");
			complexFeature = new ComplexFeature(name, this.getDistanceMetricFromString(distanceMetric));
			childFeatures = new ArrayList<>();
		} 
		if(qName.equals("SimpleFeature")) {
			String name = attributes.getValue("name");
			String distanceMetric = attributes.getValue("distanceMetric");
			String stringValue = attributes.getValue("stringValue");
			String value = attributes.getValue("value");

			simpleFeature = new SimpleFeature(name);
			simpleFeature.setDistanceMetric(getDistanceMetricFromString(distanceMetric));
			if(stringValue.equals("null")) {
				simpleFeature.setValue(Double.parseDouble(value));
			} else {
				simpleFeature.setStringValue(stringValue);
			}
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if(qName.equals("knn")) {
			knn = new KNN(trainExamples, testExamples, k);
			knn.makePrediction();
		}
		if(qName.equals("TrainingExample")) {
			trainExamples.addElement(trainingExample);
			trainingExample = null;
		}
		if(qName.equals("TestingExample")) {
			testExamples.addElement(testingExample);
			testingExample = null;
		}
		if(qName.equals("Input")) {
			input.setFeatures(inputFeatures);	
			if(trainingExample != null) {
				trainingExample.setInput(input);
			}
			if(testingExample != null) {
				testingExample.setInput(input);
			}
			input = null;
		}
		if(qName.equals("Output")) {
			if(trainingExample != null) {
				trainingExample.setOutput(output);
			}
		}
		if(qName.equals("ComplexFeature")) {
			complexFeature.setChildFeatures(childFeatures);
			if(input != null) {
				inputFeatures.addElement(complexFeature);
			}
			complexFeature = null;
			childFeatures = null;
		}
		if(qName.equals("SimpleFeature")) {
			if(this.childFeatures != null) {
				childFeatures.add(simpleFeature);
			} else {
				if(input != null) {
					inputFeatures.addElement(simpleFeature);
				} else if(output != null) {
					output = simpleFeature;
				}
			}
			simpleFeature = null;
		}
	}
	
	public KNN getKNN() {
		return knn;
	}
	
	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		
	}
	
	public String getKnnPrediction() {
		return knn.toString();
	}

}
