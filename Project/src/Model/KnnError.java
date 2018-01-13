package Model;

import java.util.ArrayList;

import javax.swing.DefaultListModel;

/**
 * This class is responsible for calculating error in order to get the quality of the KNN algorithm.
 * If prediction was made using average the error will be sum of errors. If prediction was made using vote, 
 * then the error will be the percentage that it predicted the correct label.
 * 
 * @author Dhrubomoy Das Gupta
 *
 */
public class KnnError {
	private ArrayList<TrainingExample> trainExamples;
	private int k;
	private PredictionType predictionType;

	/**
	 * Constructor for KnnError class.
	 * @param trainExamples		ArrayList of training example
	 * @param k					Value of k
	 * @param predictionType	Prediction Type
	 */
	public KnnError(ArrayList<TrainingExample> trainExamples, int k, PredictionType predictionType) {
		this.trainExamples = trainExamples;
		this.k = k;
		this.predictionType = predictionType;
	}

	private double getErrorPercentage() {
		double numOfCorrectPrediction = 0;
		for(int i=0; i<trainExamples.size(); i++) {
			DefaultListModel<TrainingExample> tempTrainExamples = new DefaultListModel<>();
			for(int y = 0;y<trainExamples.size();y++){ tempTrainExamples.addElement(trainExamples.get(y)); }
			//ArrayList<TrainingExample> tempTrainExamples = new ArrayList<>(trainExamples);
			TestingExample tstExmpl = new TestingExample(trainExamples.get(i).getName(), tempTrainExamples.get(i).getInput());
			DefaultListModel<TestingExample> tstExmpls = new DefaultListModel<>();
			tstExmpls.addElement(tstExmpl);
			//ArrayList<TestingExample> tstExmpls = new ArrayList<>();
			//tstExmpls.add(tstExmpl);
			tempTrainExamples.remove(i);
			if(k>tempTrainExamples.size()) {
				k = tempTrainExamples.size();
			}
			KNN knn = new KNN(tempTrainExamples, tstExmpls, PredictionType.VOTING, k);
			knn.makePrediction();
			if(trainExamples.get(i).getOutput().getValue() == tstExmpls.get(0).getPredictedOutput().getValue()) {
				numOfCorrectPrediction++;
			}
		}
		return (numOfCorrectPrediction/trainExamples.size())*100;
	}

	private double getSumOfErrors() {
		double sum = 0;
		for(int i=0; i<trainExamples.size(); i++) {
			DefaultListModel<TrainingExample> tempTrainExamples = new DefaultListModel<>();
			for(int y = 0;y<trainExamples.size();y++){ tempTrainExamples.addElement(trainExamples.get(y)); }
			//ArrayList<TrainingExample> tempTrainExamples = new ArrayList<>(trainExamples);
			TestingExample tstExmpl = new TestingExample(trainExamples.get(i).getName(), tempTrainExamples.get(i).getInput());
			DefaultListModel<TestingExample> tstExmpls = new DefaultListModel<>();
			tstExmpls.addElement(tstExmpl);
			//ArrayList<TestingExample> tstExmpls = new ArrayList<>();
			//tstExmpls.add(tstExmpl);
			tempTrainExamples.remove(i);
			if(k>tempTrainExamples.size()) {
				k = tempTrainExamples.size();
			}
			KNN knn = new KNN(tempTrainExamples, tstExmpls, PredictionType.AVERAGE, k);
			knn.makePrediction();
			sum += Math.abs(trainExamples.get(i).getOutput().getValue() - tstExmpls.get(0).getPredictedOutput().getValue());
		}
		return sum;
	}
	
	/**
	 * Returns a string describing the error.
	 * 
	 * @param none
	 * @return String result
	 */
	public String toString() {
		String result = "";
		if(predictionType == PredictionType.AVERAGE) {
			result += "Sum of Errors: " + getSumOfErrors();
		} else if(predictionType == PredictionType.VOTING) {
			result += "Correct label prediction: " + getErrorPercentage() + "%";
		}
		return result;
	}

}
