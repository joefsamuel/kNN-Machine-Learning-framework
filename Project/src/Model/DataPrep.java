package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import javax.swing.DefaultListModel;

public class DataPrep {
	private DefaultListModel<TrainingExample> trainExamples;
	private DefaultListModel<TestingExample> testExamples;
	private HashMap<String, Integer> outputStringValueMap;
	private KNN knn;

	public DataPrep(DefaultListModel<TrainingExample> trainExamples, DefaultListModel<TestingExample> testExamples, KNN knn) {
		this.trainExamples = trainExamples;
		this.testExamples = testExamples;
		this.knn = knn;
		prepareInputs();
		setDoubleValueToTrainingExampleOutput();
	}
	
	public HashMap<String, Integer> getOutputStringValueMap() {
		return this.outputStringValueMap;
	}
	
	// Set double value for input features that have string values!
	private void prepareInputs() {
		for(int idx: getStringIndexes()) {
			setDoubleValueToAllRow(idx);
		}
	}
	
	private void setDoubleValueToTrainingExampleOutput() {
		// check if output of training example has string value
		if(this.trainExamples.get(0).getOutput().getStringValue() != null) {
			knn.setPredictionType(PredictionType.VOTING);
			HashSet<String> uniqueStrings = new HashSet<>();
			for(int i=0; i<this.trainExamples.size(); i++) {
				uniqueStrings.add(this.trainExamples.get(i).getOutput().getStringValue());
			}
			outputStringValueMap = getStringValueMap(uniqueStrings);
			for(int i=0; i<this.trainExamples.size(); i++) {
				setDoubleValue(this.trainExamples.get(i).getOutput(), outputStringValueMap);
			}	
		}
	}

	private ArrayList<Integer> getStringIndexes() {
		ArrayList<Integer> stringIndexes = new ArrayList<>();
		DefaultListModel<Feature> inputFeatures = trainExamples.get(0).getInput().getFeatures();
		for(int i=0; i<inputFeatures.size(); i++) {
			// Check if it has a string value
			if(inputFeatures.get(i).getStringValue() != null) {
				// we have found our index
				stringIndexes.add(i);
			}
		}
		return stringIndexes;
	}
	
	// Sets a double value for each row for a given column (by index)
	private void setDoubleValueToAllRow(int index) {
		HashSet<String> uniqueStrings = new HashSet<>();
		for(int i=0; i<this.trainExamples.size(); i++) {
			uniqueStrings.add(this.trainExamples.get(i).getInput().getFeatures().get(index).getStringValue());
		}
		HashMap<String, Integer> stringValueMap = this.getStringValueMap(uniqueStrings);
		setDoubleValueToTrainingInput(stringValueMap, index);
		setDoubleValueToTestingInput(stringValueMap, index);
	}
	
	private void setDoubleValueToTestingInput(HashMap<String, Integer> stringValueMap, int index) {
		for(int i=0; i<this.testExamples.size(); i++) {
			Feature ftr = this.testExamples.get(i).getInput().getFeatures().get(index);
			setDoubleValue(ftr, stringValueMap);
		}
	}

	// loops through the column of trainExample and set double value of each string
	private void setDoubleValueToTrainingInput(HashMap<String, Integer> stringValueMap, int index) {
		for(int i=0; i<this.trainExamples.size(); i++) {
			Feature ftr = this.trainExamples.get(i).getInput().getFeatures().get(index);
			setDoubleValue(ftr, stringValueMap);
		}
	}
	
	private void setDoubleValue(Feature ftr, HashMap<String, Integer> stringValueMap) {
		String strVal = ftr.getStringValue();
		for(Map.Entry<String, Integer> entry : stringValueMap.entrySet()) {
			if(strVal.equals(entry.getKey())) {
				ftr.setValue(entry.getValue());
			}
		}
	}
	
	private HashMap<String, Integer> getStringValueMap(HashSet<String> uniqueStrings) {
		HashMap<String, Integer> stringValueMap = new HashMap<>();
		ArrayList<String> uniqueStringsList = new ArrayList<String>(uniqueStrings);
		for(int i=0; i<uniqueStringsList.size(); i++) {
			stringValueMap.put(uniqueStringsList.get(i), i);
		}
		return stringValueMap;
	}

}
