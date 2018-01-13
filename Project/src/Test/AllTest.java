package Test;

import junit.framework.*;

import java.util.ArrayList;

import javax.swing.DefaultListModel;

import Model.AbsoluteDistanceMetric;
import Model.ComplexFeature;
import Model.DistanceCalculation;
import Model.DistanceMetric;
import Model.EuclideanDistanceMetric;
import Model.Feature;
import Model.Input;
import Model.KNN;
import Model.KnnError;
import Model.PolarCoordinateDistanceMetric;
import Model.PredictionType;
import Model.SimpleFeature;
import Model.TestingExample;
import Model.TrainingExample;

/**
 * JUnit tests for all classes except KNN, KnnError and DistanceCalculation
 * 
 * @author Eric Bedard
 * @version Milestone2
 * 
 */
public class AllTest extends TestCase {
	private Feature sf1, sf2, cf1;
	private Feature pr1, pr2, pt1, pt2, p1, p2;		// To test PolarCoordinateDistanceMetric
	private Input i;
	private TestingExample e1;
	private TrainingExample te;
	
	/**
	 * Layout configuration for the panel and redundancy measures in place.
	 * @param args[]	Default parameters for main
	 * 
	 * @return	void
	 */
	public static void main(String[] args) {
		junit.textui.TestRunner.run(AllTest.class);
	}
	
	
	protected void setUp() {
		sf1 = new SimpleFeature("sf1",1);
		sf2 = new SimpleFeature("sf2",2);
		cf1 = new ComplexFeature("cf1");
		cf1.addChildFeature(sf1);
		DefaultListModel<Feature> l = new DefaultListModel<>();
		l.addElement(sf1);
		i = new Input(l);
		te = new TrainingExample("te",i,sf2);
		e1 = new TestingExample("e1",i);
		
		DistanceMetric pcdm = new PolarCoordinateDistanceMetric();
		p1 = new ComplexFeature("p1");		p2 = new ComplexFeature("p2");
		pr1 = new SimpleFeature("pr1", 3);	pr2 = new SimpleFeature("pr2", 5);
		pt1 = new SimpleFeature("pt1", 60);	pt2 = new SimpleFeature("pt2", 145);
		p1.addChildFeature(pr1); 			p1.addChildFeature(pt1);
		p2.addChildFeature(pr2); 			p2.addChildFeature(pt2);
		p1.setDistanceMetric(pcdm); 		p2.setDistanceMetric(pcdm);
	}
	
	/**
	 * Tests the getName feature
	 * @param none
	 * 
	 * @return	void
	 */
	public void testGetNameFeature() { assertEquals("sf1",sf1.getName()); }
	
	/**
	 * Tests the setName feature
	 * @param none
	 * 
	 * @return	void
	 */
	public void testSetNameFeature() {
		sf1.setName("new");
		assertEquals("new", sf1.getName());
	}
	
	/**
	 * Tests the setMetric feature
	 * @param none
	 * 
	 * @return	void
	 */
	public void testSetMetricFeature() {
		DistanceMetric e = new EuclideanDistanceMetric();
		sf1.setDistanceMetric(e);
		assertEquals(e, sf1.getDistanceMetric());
	}
	
	/**
	 * Tests the getDistance feature
	 * @param none
	 * 
	 * @return	void
	 */
	public void testGetDistanceFeature() { assertEquals(1.0, sf2.getDistance(sf1)); }
	
	//SimpleFeature tests
	/**
	 * Tests the getString feature
	 * @param none
	 * 
	 * @return	void
	 */
	public void testGetString() {
		SimpleFeature sf = new SimpleFeature("sf","test");
		assertEquals("test",sf.getStringValue());
	}
	
	/**
	 * Tests the getValue feature
	 * @param none
	 * 
	 * @return	void
	 */
	public void testGetValue() { assertEquals(1.0,sf1.getValue()); }
	
	//Complex Feature Tests
	/**
	 * Tests the child features within a complex feature
	 * @param none
	 * 
	 * @return	void
	 */
	public void testGetChildFeatureComplex() { assertEquals(sf1, cf1.getChildFeatures().get(0)); }
	
	/**
	 * Tests the additon of a child feature within a complex feature
	 * @param none
	 * 
	 * @return	void
	 */
	public void testAddChildFeature() {
		cf1.addChildFeature(sf2);
		assertEquals(sf2,cf1.getChildFeatures().get(1));
	}
	
	//Input Tests
	/**
	 * Tests the getFeatures method from Input
	 * @param none
	 * 
	 * @return	void
	 */
	public void testGetFeatureComplex() { assertEquals(sf1,i.getFeatures().getElementAt(0)); }
	
	/**
	 * Tests the addFeatures method from Input
	 * @param none
	 * 
	 * @return	void
	 */
	public void testAddFeatureInput() {
		i.addFeature(sf2);
		assertEquals(sf2, i.getFeatures().get(1));
	}
	
	//Example Tests
	/**
	 * Tests the getName method for type Example
	 * @param none
	 * 
	 * @return	void
	 */
	public void testGetNameExample() { assertEquals("e1",e1.getName()); }
	
	/**
	 * Tests the getInput method for type Example
	 * @param none
	 * 
	 * @return	void
	 */
	public void testGetInputExample() { assertEquals(i,e1.getInput()); }
	
	/**
	 * Tests the setName method for type Example
	 * @param none
	 * 
	 * @return	void
	 */
	public void testSetNameExample() {
		e1.setName("new");
		assertEquals("new",e1.getName());
	}
	
	/**
	 * Tests the sum of distances method for type Example
	 * @param none
	 * 
	 * @return	void
	 */
	public void testSumOfDistance() {
			TestingExample e2 = new TestingExample("e2",i);
			assertEquals(0.0, e1.getSumOfDistances(e2));
		}
	
	//TestingExamples Tests
	/**
	 * Tests the set predicted output testing method for type TestingExample
	 * @param none
	 * 
	 * @return	void
	 */
	public void testSetPredictedOutputTestingExamples() { 
		e1.setPredictedOutput(sf1);
		assertEquals(sf1,e1.getPredictedOutput());
	}
	
	/**
	 * Tests the toString() method for type TestingExample
	 * @param none
	 * 
	 * @return	void
	 */
	public void testToString() {
		e1.setPredictedOutput(sf2);
		assertEquals("e1[sf1: 1.0]",e1.toString());
	}
	
	//trainingExample tests
	/**
	 * Tests the sumofDistances method for type TrainingExample
	 * @param none
	 * 
	 * @return	void
	 */
	public void testSetSumOfDistances() {
		te.setSumOfDistances(1.0);
		assertEquals(1.0,te.getSumOfDistances());
	}
	
	/**
	 * Tests the getoutput method for type TrainingExample
	 * @param none
	 * 
	 * @return	void
	 */
	public void testGetOutput() { assertEquals(sf2,te.getOutput()); }
	
	/**
	 * Tests the setoutput method for type TrainingExample
	 * @param none
	 * 
	 * @return	void
	 */
	public void testSetOutput() {
		te.setOutput(sf1);
		assertEquals(sf1,te.getOutput());
	}
	
	/**
	 * Tests the Compare to method for type TrainingExample
	 * @param none
	 * 
	 * @return	void
	 */
	public void testCompareTo() {
		TrainingExample temp = new TrainingExample("temp",i,sf2);
		assertEquals(0,te.compareTo(temp));
	}
	
	//AbsoluteDistanceMetric tests
	/**
	 * Tests the absolute distance metric method 
	 * @param none
	 * 
	 * @return	void
	 */
	public void testAbsoluteDistanceMetric() {
		AbsoluteDistanceMetric a = new AbsoluteDistanceMetric();
		assertEquals(1.0,a.getDistanceMetric(sf1, sf2));
	}
	
	//EuclideanDistanceMetric tests
	/**
	 * Tests the Euclidean distance metric method 
	 * @param none
	 * 
	 * @return	void
	 */
	public void testEuclideanDistanceMetric() {
		EuclideanDistanceMetric e = new EuclideanDistanceMetric();
		ComplexFeature cf2 = new ComplexFeature("cf2");
		cf2.addChildFeature(sf1);
		cf2.addChildFeature(sf1);
		cf2.addChildFeature(sf1);
		cf1.addChildFeature(sf1);
		cf1.addChildFeature(sf1);
		assertEquals(0.0,e.getDistanceMetric(cf1, cf2));
	}
	

	//DistanceCalculation tests

	/**
	 * Tests the distance calculation method 
	 * @param none
	 * 
	 * @return	void
	 */

	public void testDistanceCalculation() {
		ArrayList<TrainingExample> tempTrain = new ArrayList<>();
		tempTrain.add(te);
		DistanceCalculation d = new DistanceCalculation(tempTrain,e1);
		d.associateDistancesWithTrainExamples();
		assertEquals(true, Double.isNaN(te.getSumOfDistances()));
	}
	

	//KNN tests
/**
 * Tests the knn results 
 * @param none
 * 
 * @return	void
 */
	public void testKNN() {
		 DefaultListModel<TrainingExample> trainExamples = new  DefaultListModel<>();
		 trainExamples.addElement(te);
		 DefaultListModel<Feature> l = new DefaultListModel<>();
		 l.addElement(sf2);
		 Input y = new Input(l);
		 TrainingExample wrong = new TrainingExample("wrong",y,sf1);
		 trainExamples.addElement(wrong);
		 DefaultListModel<TestingExample> testExamples = new DefaultListModel<>();
		 testExamples.addElement(e1);
		 KNN knn = new KNN(trainExamples,testExamples,PredictionType.AVERAGE,1);
		 knn.makePrediction();
		 assertEquals(2.0,e1.getPredictedOutput().getValue());
	}
	
	/**
	 * Tests the knn error margins 
	 * @param none
	 * 
	 * @return	void
	 */
	public void testKnnError() {
		ArrayList<TrainingExample> train = new ArrayList<>();
		train.add(te);
		DefaultListModel<Feature> l = new DefaultListModel<>();
		l.addElement(sf2);
		Input y = new Input(l);
		TrainingExample wrong = new TrainingExample("wrong",y,sf1);
		train.add(wrong);
		KnnError test = new KnnError(train,1,PredictionType.AVERAGE);
		test.toString();
		assertEquals("Sum of Errors: 2.0",test.toString());
	}
	
	public void testPolarCoordinateDistanceMetric() {
		double roundedDistance = Math.round(p1.getDistance(p2)*100.0)/100.0;
		assertEquals(5.6, roundedDistance);
	}
	
}
