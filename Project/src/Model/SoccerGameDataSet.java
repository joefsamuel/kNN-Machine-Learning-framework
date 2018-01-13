package Model;

import javax.swing.DefaultListModel;

public class SoccerGameDataSet {
	private static double nanValue = Double.NaN;
	
	private static Example initializeSoccerExample(double ballDist, double ballDir, double goalDist, double goalDir, double fctDist, double fctDir,
			double fcbDist, double fcbDir, String actionStr, String exampleName) {
		PolarCoordinateDistanceMetric pcdm = new PolarCoordinateDistanceMetric();
		Feature ball = new ComplexFeature("Ball");		ball.setDistanceMetric(pcdm);
		Feature goal = new ComplexFeature("Goal");		goal.setDistanceMetric(pcdm);
		Feature flags = new ComplexFeature("Flags");
		Feature fct = new ComplexFeature("FCT"); 		fct.setDistanceMetric(pcdm);
		Feature fcb = new ComplexFeature("FCB");		fcb.setDistanceMetric(pcdm);
		
		Feature ballDistance = new SimpleFeature("Distance", ballDist); Feature ballDirection = new SimpleFeature("Direction", ballDir);
		Feature goalDistance = new SimpleFeature("Distance", goalDist); Feature goalDirection = new SimpleFeature("Direction", goalDir);
		Feature fctDistance = new SimpleFeature("Distance", fctDist); Feature fctDirection = new SimpleFeature("Direction", fctDir);
		Feature fcbDistance = new SimpleFeature("Distance", fcbDist); Feature fcbDirection = new SimpleFeature("Direction", fcbDir);
		Feature action = new SimpleFeature("Action", actionStr);	//output
		
		ball.addChildFeature(ballDistance); ball.addChildFeature(ballDirection);
		goal.addChildFeature(goalDistance); goal.addChildFeature(goalDirection);
		fct.addChildFeature(fctDistance);   fct.addChildFeature(fctDirection);
		fcb.addChildFeature(fcbDistance);   fcb.addChildFeature(fcbDirection);
		flags.addChildFeature(fct); 		   flags.addChildFeature(fcb);
		
		DefaultListModel<Feature> features = new DefaultListModel<>();
		features.addElement(ball); features.addElement(goal); features.addElement(flags);
		Input input = new Input(features);
		Example exmpl;
		
		if(actionStr == null) {
			exmpl = new TestingExample(exampleName, input);
		} else {
			exmpl = new TrainingExample(exampleName, input, action);
		}
		
		return exmpl;
	}
	
	public DefaultListModel<TrainingExample> getTrainingExamples() {
		TrainingExample ex1 = (TrainingExample)initializeSoccerExample(1.9,-167, 63.8, 31, 39.1, -41, nanValue, nanValue, "Kick", "Training example 1");
		TrainingExample ex2 = (TrainingExample)initializeSoccerExample(1.9, 50, 63.8, 31, 39.1, -41, nanValue, nanValue	, "Kick", "Training example 2");
		TrainingExample ex3 = (TrainingExample)initializeSoccerExample(1.8, 2, 61.9, -4, nanValue, nanValue, nanValue, nanValue, "Kick", "Training example 3");
		TrainingExample ex4 = (TrainingExample)initializeSoccerExample(1.8, -85, 53.5, 17, nanValue, nanValue, nanValue, nanValue, "Kick", "Training example 4");
		TrainingExample ex5 = (TrainingExample)initializeSoccerExample(19.2, 1, 24.6, -17, nanValue, nanValue, nanValue, nanValue, "Dash", "Training example 5");
		TrainingExample ex6 = (TrainingExample)initializeSoccerExample(15.9, 1, 22.3, -18, nanValue, nanValue, nanValue, nanValue, "Dash", "Training example 6");
		TrainingExample ex7 = (TrainingExample)initializeSoccerExample(14.5, 1, 20.7, -20, nanValue, nanValue, nanValue, nanValue, "Dash", "Training example 7");
		TrainingExample ex8 = (TrainingExample)initializeSoccerExample(11, 1, nanValue, nanValue, 44.8	, -5, nanValue, nanValue, "Dash", "Training example 8");
		TrainingExample ex9 = (TrainingExample)initializeSoccerExample(10, 1, 61.3, -31, nanValue, nanValue, 41.4, 43, "Dash", "Training example 9");
		TrainingExample ex10 = (TrainingExample)initializeSoccerExample(45.7, 1, 96.6, 2, 55.6	, -37, 55.6	, 40, "Dash", "Training example 10");
		TrainingExample ex11 = (TrainingExample)initializeSoccerExample(50.4, -1, 101.5, 14, 75.4, -24, 46.2, 40, "Turn", "Training example 11");
		TrainingExample ex12 = (TrainingExample)initializeSoccerExample(41.4, 0, 90.1, 18, 65.1, -27, nanValue, nanValue, "Turn", "Training example 12");
		TrainingExample ex13 = (TrainingExample)initializeSoccerExample(14.5, 15, 60.1, 27, nanValue, nanValue, nanValue, nanValue, "Turn", "Training example 13");
		TrainingExample ex14 = (TrainingExample)initializeSoccerExample(41.4, 3, 94.7, 4, 55.1, -36, 53.5, 43, "Turn", "Training example 14");
		TrainingExample ex15 = (TrainingExample)initializeSoccerExample(23.2, 0, 76.9, 2, nanValue, nanValue, nanValue, nanValue, "Turn", "Training example 15");
		TrainingExample ex16 = (TrainingExample)initializeSoccerExample(12, 24, nanValue, nanValue, nanValue, nanValue, 42.7, -40, "Turn", "Training example 16");
		TrainingExample ex17 = (TrainingExample)initializeSoccerExample(nanValue, nanValue, 26.3, 2, nanValue, nanValue, nanValue, nanValue, "Turn", "Training example 17");
		TrainingExample ex18 = (TrainingExample)initializeSoccerExample(3.5, 1, 56.1, 4, nanValue, nanValue, nanValue, nanValue, "Dash", "Training example 18");
		
		DefaultListModel<TrainingExample> trainExamples = new DefaultListModel<>();
		trainExamples.addElement(ex1); trainExamples.addElement(ex2); trainExamples.addElement(ex3); trainExamples.addElement(ex4); trainExamples.addElement(ex5); 
		trainExamples.addElement(ex6); trainExamples.addElement(ex7); trainExamples.addElement(ex8); trainExamples.addElement(ex9); trainExamples.addElement(ex10); 
		trainExamples.addElement(ex11); trainExamples.addElement(ex12); trainExamples.addElement(ex13); trainExamples.addElement(ex14); trainExamples.addElement(ex15); 
		trainExamples.addElement(ex16); trainExamples.addElement(ex17); trainExamples.addElement(ex18); 
		
		return trainExamples;
	}
	
	public DefaultListModel<TestingExample> getTestingExamples() {
		TestingExample ex1 = (TestingExample) initializeSoccerExample(2, 22, 63.8, -2, nanValue, nanValue, nanValue, nanValue, null, "Testing Example 1");
		TestingExample ex2 = (TestingExample) initializeSoccerExample(2, 22, 63.8, -2, nanValue, nanValue, nanValue, nanValue, null, "Testing Example 2");
		TestingExample ex3 = (TestingExample) initializeSoccerExample(1.9	, -167, 63.8	, 31, 39.1, -41, nanValue, nanValue,  null, "Testing Example 3");
		TestingExample ex4 = (TestingExample) initializeSoccerExample(14.5, 0, 32.8, 5, nanValue, nanValue, nanValue, nanValue,  null, "Testing Example 4");
		TestingExample ex5 = (TestingExample) initializeSoccerExample(nanValue, nanValue, 55.6, 2, nanValue, nanValue, nanValue, nanValue, null, "Testing Example 5");
		TestingExample ex6 = (TestingExample) initializeSoccerExample(41.4, 2, 95.6, 1, 55.1, -38, 55.1, 40, null, "Testing Example 6");
		TestingExample ex7 = (TestingExample) initializeSoccerExample(19.2, 11, 85.8, 12, 47.5	, -34, nanValue, nanValue, null, "Testing Example 7");
		TestingExample ex8 = (TestingExample) initializeSoccerExample(23.2, 1, 42.7, -4, nanValue, nanValue, nanValue, nanValue, null, "Testing Example 8");
		TestingExample ex9 = (TestingExample) initializeSoccerExample(21.1, 1, 41.9, -4, nanValue, nanValue, nanValue, nanValue, null, "Testing Example 9");
		TestingExample ex10 = (TestingExample) initializeSoccerExample(50.4, 1, 104.5, 1, 61.9	, -33, 61.9, 35, null, "Testing Example 10");
		TestingExample ex11 = (TestingExample) initializeSoccerExample(17.4, 0, 91, -3, nanValue, nanValue, 52.9, 39, null, "Testing Example 11");
		TestingExample ex12 = (TestingExample) initializeSoccerExample(10, 1, nanValue, nanValue, 44.4, -9, nanValue, nanValue, null, "Testing Example 12");
		TestingExample ex13 = (TestingExample) initializeSoccerExample(10, 1, nanValue, nanValue, 44.4, -8, nanValue, nanValue, null, "Testing Example 13");

		DefaultListModel<TestingExample> testExamples = new DefaultListModel<>();
		testExamples.addElement(ex1); testExamples.addElement(ex2); testExamples.addElement(ex3); testExamples.addElement(ex4); testExamples.addElement(ex5); 
		testExamples.addElement(ex6); testExamples.addElement(ex7); testExamples.addElement(ex8); testExamples.addElement(ex9); testExamples.addElement(ex10); 
		testExamples.addElement(ex11); testExamples.addElement(ex12); testExamples.addElement(ex13); 
		
		return testExamples;
	}

}
