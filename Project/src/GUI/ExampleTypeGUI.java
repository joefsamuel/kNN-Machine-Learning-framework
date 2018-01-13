package GUI;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import Model.Example;
import Model.Feature;
import Model.Input;
import Model.KNN;
import Model.SimpleFeature;
import Model.TestingExample;
import Model.TrainingExample;

/**
 * The ExampleTypeGUI is the Graphical User Interface when an Example is to be created after a problem has been defined. 
 * 
 * @author Joe Samuel, Jobin Mathew
 *
 */
public class ExampleTypeGUI extends javax.swing.JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private KNN tempKnn;
	private Example featureExample;
	private DefaultListModel<Feature> featuresList;
	/**
	 * Constructor - To Initialize GUI interface
	 */
    public ExampleTypeGUI() {
        initComponents();
    }
    
	/**
	 * Constructor  with Example input
	 * @param featureExample The example to which the feature is to be added.
	 * @param featureName	Name of the feature in the example.
	 */
    public ExampleTypeGUI(Example featureExample) {
    		this.featureExample = featureExample;
        initComponents();
    }
    
	/**
	 * Constructor  with Example input, KNN associated and the list of features.
	 * @param featureExample The example to which the feature is to be added.
	 * @param tempKnn	KNN associated with the training and testing list of examples.
	 * @param featuresList List of features to be used for a given problem
	 */
    public ExampleTypeGUI(Example featureExample, KNN tempKnn, DefaultListModel<Feature> featuresList) {
    		this.featureExample = featureExample;
    		this.tempKnn = tempKnn;
    		this.featuresList = featuresList;
        initComponents();
    }
    
    private void initComponents() {

        nextButton = new javax.swing.JButton();
        trainingRadioButton = new javax.swing.JRadioButton();
        testingRadioButton = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SELECT THE TYPE OF EXAMPLE");
        nextButton.setText("Next");
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });

        trainingRadioButton.setText("Training Example");
        trainingRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                traningRadioButtonActionPerformed(evt);
            }
        });

        testingRadioButton.setText("Testing Example");
        testingRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testingRadioButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(144, 144, 144)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(trainingRadioButton)
                        .addComponent(testingRadioButton, javax.swing.GroupLayout.Alignment.LEADING))
                    .addComponent(nextButton, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(151, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(92, Short.MAX_VALUE)
                .addComponent(trainingRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(testingRadioButton)
                .addGap(32, 32, 32)
                .addComponent(nextButton)
                .addGap(107, 107, 107))
        );

        pack();
        setLocationRelativeTo(null);
    }                      

    private void traningRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {
        testingRadioButton.setSelected(false);
        if(trainingRadioButton.isSelected())
        nextButton.setEnabled(true);
        else nextButton.setEnabled(false);
    }                                                  

    private void testingRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {
        trainingRadioButton.setSelected(false);
        if(testingRadioButton.isSelected())
        nextButton.setEnabled(true);
        else nextButton.setEnabled(false);
    }                                                  

    @SuppressWarnings("deprecation")
	private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {  
    		featureExample = null;
        String nameExample = JOptionPane.showInputDialog("Please Enter Name for this example");
        Feature newOutputFeature = new SimpleFeature("Output", -1);
        if(nameExample.length()>0){        
			if(trainingRadioButton.isSelected()) {
				//changed (nameExample, new Input(new ArrayList<>(1)), newOutputFeature)
	        	featureExample = new TrainingExample(nameExample, new Input(new DefaultListModel<>()), newOutputFeature);	
	        	tempKnn.getTrainingExamples().addElement((TrainingExample) featureExample);
	        	CreateExampleWithFeaturesGUI newExampleWithFeature = new CreateExampleWithFeaturesGUI(featuresList, "training", featureExample);	
	        	newExampleWithFeature.show();
	        	dispose();
	        }
	        else if(testingRadioButton.isSelected()){
	        	featureExample = new TestingExample(nameExample, new Input(new DefaultListModel<>()));
	        	tempKnn.getTestExamples().addElement((TestingExample) featureExample);
	        	CreateExampleWithFeaturesGUI newExampleWithFeature = new CreateExampleWithFeaturesGUI(featuresList, "testing", featureExample);
	        	newExampleWithFeature.show();
	        	dispose();
	        }
        }
        else{
        	Popup.infoBox("Please Enter a Name for the Example.", "INPUT ERROR");
        }
    }                                          

    /**
	 * Layout configuration for the panel and redundancy measures in place.
	 * @param args[]	Default parameters for main
	 * 
	 * @return	void
	 */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ExampleTypeGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ExampleTypeGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ExampleTypeGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ExampleTypeGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ExampleTypeGUI().setVisible(true);
            }
        });
    }

    // Variables declaration                  
    private javax.swing.JButton nextButton;
    private javax.swing.JRadioButton testingRadioButton;
    private javax.swing.JRadioButton trainingRadioButton;
    // End of variables declaration                   
}
