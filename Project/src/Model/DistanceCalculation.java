package Model;

import java.util.ArrayList;
import java.util.Collections;
import javax.swing.DefaultListModel;

/**
 * DistanceCalculation class is responsible for calculating distances between each training example and
 * a testing example, normalizes the distances, gets the sum of distances and finally associates them to their
 * corresponding training examples for KNN to compare and make predictions.
 * 
 * @author Dhrubomoy Das Gupta
 *
 */
public class DistanceCalculation {
	private ArrayList<TrainingExample> trainExamples;
	private TestingExample testExample;
	
	/**
	 * Constructor for DistanceCalculation class.
	 * @param trainExamples		List of training examples
	 * @param testExample		A testing example
	 */
	public DistanceCalculation(ArrayList<TrainingExample> trainExamples, TestingExample testExample) {
		this.trainExamples = trainExamples;
		this.testExample = testExample;
	}
	
	/**
	 * Returns an ArrayList of distances between each feature of a training example input and the
	 * corresponding feature of testing example.
	 * @param trainExample
	 * @return
	 */
	private ArrayList<Double> getDistances(TrainingExample trainExample) {
		ArrayList<Double> distances = new ArrayList<>();
		DefaultListModel<Feature> trainExampleFeatures = trainExample.getInput().getFeatures();
		DefaultListModel<Feature> testExampleFeatures = testExample.getInput().getFeatures();
		// loop through each features of trainingExample's input and 
		// get the distance from corresponding feature of testingExample's input
		for(int i=0; i<trainExampleFeatures.size(); i++) {
			Double distance = trainExampleFeatures.get(i).getDistance(testExampleFeatures.get(i));
			if(distance.isNaN()) {
				distance = -1.0;
			}
			distances.add(distance);
		}
		return distances;
	}
	
	/**
	 * Returns the distances for every single training examples looping through the list
	 * and by calling getDistances() method on them.
	 * @return ArrayList of arrayList containing the distances
	 */
	private ArrayList<ArrayList<Double>> getAllDistances() {
		ArrayList<ArrayList<Double>> allDistances = new ArrayList<>();
		for(TrainingExample trainExample : trainExamples) {
			allDistances.add(getDistances(trainExample));
		}
		return allDistances;
	}
	
	/**
	 * Normalizes an ArrayList by dividing each elements by the max element of the ArrayList 
	 * @param column
	 * @return
	 */
	private ArrayList<Double> normalizeColumn(ArrayList<Double> column) {
		ArrayList<Double> normalizedColumn = new ArrayList<>();
		double maxValue = Collections.max(column);
		for(int i=0; i<column.size(); i++) {
			 if(column.get(i).isNaN()) {
				normalizedColumn.add(1.0);
			} else if(maxValue != 0) {
				normalizedColumn.add(column.get(i)/maxValue);
			} else {
				normalizedColumn.add(1.0);
			}
		}
		return normalizedColumn;
	}
	
	/**
	 * Returns normalized distances for each training example
	 * @return
	 */
	private ArrayList<ArrayList<Double>> getNormalizedDistances() {
		ArrayList<ArrayList<Double>> normalizedColumns = new ArrayList<>();
		ArrayList<ArrayList<Double>> allDistances = getAllDistances();
		ArrayList<ArrayList<Double>> normalizedDistances = new ArrayList<>();
		for(int i=0; i<allDistances.get(0).size(); i++) {
			// grab each column, store them in an arraylist, normalize the column
			// by dividing by the max value
			ArrayList<Double> distanceColumn = new ArrayList<>();
			for(int j=0; j<allDistances.size(); j++) {
				distanceColumn.add(allDistances.get(j).get(i));
			}
			ArrayList<Double> normalizedColumn = normalizeColumn(distanceColumn);
			normalizedColumns.add(normalizedColumn);
		}
		for(int i=0; i<normalizedColumns.get(0).size(); i++) {
			normalizedDistances.add(new ArrayList<>());
			for(int j=0; j<normalizedColumns.size(); j++) {
				normalizedDistances.get(i).add(normalizedColumns.get(j).get(i));
			}
		}
		return normalizedDistances;
	}
	
	/**
	 * Returns the sum of distances for each training example
	 * @return
	 */
	private ArrayList<Double> getSumOfDistances() {
		ArrayList<ArrayList<Double>> normalizedDistances = getNormalizedDistances();
		ArrayList<Double> sumOfDistances = new ArrayList<>();
		for(int i=0; i<normalizedDistances.size(); i++) {
			double sum = 0;
			for(int j=0; j<normalizedDistances.get(i).size(); j++) {
				sum += normalizedDistances.get(i).get(j);
			}
			sumOfDistances.add(sum);
		}
		return sumOfDistances;
	}
	
	/**
	 * This method calculates the sum of distances between each training example and testing example
	 * and sets it equal to the sumOfDistances attribute of that training example. KNN class makes use of this method
	 * for further comparison and prediction.
	 */
	public void associateDistancesWithTrainExamples() {
		ArrayList<Double> sumOfDistances = getSumOfDistances();
		for(int i=0; i<sumOfDistances.size(); i++) {
			trainExamples.get(i).setSumOfDistances(sumOfDistances.get(i));
		}
	}

}
