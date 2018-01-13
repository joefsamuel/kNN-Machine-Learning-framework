/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package GUI;

import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;

import Model.AbsoluteDistanceMetric;
import Model.ComplexFeature;
import Model.DistanceMetric;
import Model.EuclideanDistanceMetric;
import Model.Feature;
import Model.PolarCoordinateDistanceMetric;
import Model.SimpleFeature;

import javax.swing.JLabel;


/**
* Creating a GUI to ask for the type of features.
* @author Jobin Mathew and Joe Samuel
*/
public class CreateProblemFeatureGUI extends javax.swing.JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private ArrayList<Feature> typeFeature = new ArrayList<>();
	private String featureName;
	private DefaultListModel<Feature> featuresList;
	private boolean firstEntry = false;
	private DistanceMetric distanceMetric;
	
	/**
	* Creates new form CreateFeature
	*/
	public CreateProblemFeatureGUI(DefaultListModel<Feature> featuresList) {
		this.featuresList = featuresList; 
		initComponents();
	}
	
	/**
	* Creates new form CreateFeature
	*/
	public CreateProblemFeatureGUI() {
		initComponents();
	}

	/**
	* This method is called from within the constructor to initialize the form.
	*/                        
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initComponents() {

		nameInputTextBox = new javax.swing.JTextField();
		featureNameLabel = new javax.swing.JLabel();
		numberRadioButton = new javax.swing.JRadioButton();
		textRadioButton = new javax.swing.JRadioButton();
		customRadioButton = new javax.swing.JRadioButton();
		addAnotherButton = new javax.swing.JButton();
		finishButton = new javax.swing.JButton();
		lblDistanceMetric = new javax.swing.JLabel();
		String[] distanceMetrics = {"Absolute", "Euclidean","Polar"};
		comboBox = new javax.swing.JComboBox(distanceMetrics);
		comboBox.setSelectedIndex(0);
		comboBox.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				comboBoxActionPerformed(evt);
			}
		});
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("CREATE FEATURE(s)");

		nameInputTextBox.setEditable(true);
		nameInputTextBox.setEnabled(true);
		
		featureNameLabel.setText("Enter the name of the Feature: ");

		numberRadioButton.setText("Number");
		numberRadioButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				numberRadioButtonActionPerformed(evt);
			}
		});

		textRadioButton.setText("Text");
		textRadioButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				textRadioButtonActionPerformed(evt);
			}
		});

		customRadioButton.setText("Custom");
		customRadioButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				customRadioButtonActionPerformed(evt);
			}
		});

		addAnotherButton.setText("Add Another Feature");
		addAnotherButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addAnotherButtonActionPerformed(evt);
			}
		});

		finishButton.setText("Finish");
		finishButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				finishButtonActionPerformed(evt);
			}
		});
		finishButton.setEnabled(false);
		lblDistanceMetric = new JLabel("Distance Metric: ");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		layout.setHorizontalGroup(
			layout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, layout.createSequentialGroup()
					.addGap(58)
					.addGroup(layout.createParallelGroup(Alignment.TRAILING)
						.addGroup(layout.createSequentialGroup()
							.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addComponent(customRadioButton)
								.addComponent(textRadioButton)
								.addComponent(numberRadioButton))
							.addGap(188))
						.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
							.addGroup(layout.createSequentialGroup()
								.addComponent(addAnotherButton)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(finishButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGroup(layout.createSequentialGroup()
								.addComponent(featureNameLabel)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(nameInputTextBox, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)))
						.addGroup(layout.createSequentialGroup()
							.addComponent(lblDistanceMetric)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
							.addGap(11)))
					.addContainerGap(66, Short.MAX_VALUE))
		);
		layout.setVerticalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addGap(53)
					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(nameInputTextBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(featureNameLabel))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(numberRadioButton)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(textRadioButton)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(customRadioButton)
					.addGap(18)
					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDistanceMetric)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(addAnotherButton)
						.addComponent(finishButton))
					.addContainerGap(66, Short.MAX_VALUE))
		);
		getContentPane().setLayout(layout);

		pack();
		setResizable(false);
		setLocationRelativeTo(null);
	}        
	
	private void comboBoxActionPerformed(java.awt.event.ActionEvent evt){
	
	}

	private void numberRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {      

		textRadioButton.setSelected(false);
		customRadioButton.setSelected(false);
		if(numberRadioButton.isSelected() && firstEntry)
		finishButton.setEnabled(true);
		else finishButton.setEnabled(false);
	}                                                 

	private void textRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                
		numberRadioButton.setSelected(false);
		customRadioButton.setSelected(false);
		if(textRadioButton.isSelected() && firstEntry)
		finishButton.setEnabled(true);
		else finishButton.setEnabled(false);
	}                                               

	private void customRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                  
		numberRadioButton.setSelected(false);
		textRadioButton.setSelected(false);
		if(customRadioButton.isSelected() && firstEntry)
		finishButton.setEnabled(true);
		else finishButton.setEnabled(false);
	}                                                 

	@SuppressWarnings("deprecation")
	private void addAnotherButtonActionPerformed(java.awt.event.ActionEvent evt) {
		createFeature();
		this.show();
		nameInputTextBox.setText("");
		numberRadioButton.setSelected(false);
		textRadioButton.setSelected(false);
		customRadioButton.setSelected(false);
		firstEntry = true;
		finishButton.setEnabled(true);
	}                                                

	private void finishButtonActionPerformed(java.awt.event.ActionEvent evt) {                                             
		createFeature();
	}                                      
	
	private void createFeature(){
		if(nameInputTextBox.getText().length()>0 && (numberRadioButton.isSelected()|| textRadioButton.isSelected()|| customRadioButton.isSelected())){
			featureName = nameInputTextBox.getText();
			
			if(nameInputTextBox.getText().contains(" ")){
				Popup.infoBox("You cannot have spaces in the Name.", "INPUT ERROR");
			}
			else if(numberRadioButton.isSelected()) {
				//featureName = nameInputTextBox.getText();
				Feature newFeatureForExample = new SimpleFeature(featureName, -1, "Num",distanceMetric);
				featuresList.addElement(newFeatureForExample);
				Popup.infoBox("Feature has succesfully been created", "Success!");
				dispose();
			}
			else if(textRadioButton.isSelected()){
				//featureName = nameInputTextBox.getText();
				Feature newFeatureForExample = new SimpleFeature(featureName, "", "String",distanceMetric);
				featuresList.addElement(newFeatureForExample);
				Popup.infoBox("Feature has succesfully been created", "Success!");
				dispose();
			}
			else if(customRadioButton.isSelected()){
				//featureName = nameInputTextBox.getText();
				ComplexFeature cus = new ComplexFeature(featureName,distanceMetric);
				featuresList.addElement(cus);
				Popup.infoBox("Feature has succesfully been created", "Success!");
				dispose();
			}
			
			//Setting the type of distanceMetric
			String selectedDistanceMetric = comboBox.getSelectedItem().toString();
			if(selectedDistanceMetric.equals("Absolute")) distanceMetric = new AbsoluteDistanceMetric();
			else if(selectedDistanceMetric.equals("Euclidean")) distanceMetric = new EuclideanDistanceMetric();
			else if(selectedDistanceMetric.equals("Polar")) distanceMetric = new PolarCoordinateDistanceMetric();
			//Add new distanceMetrics here...
		}
		else{
			Popup.infoBox("Please Enter a Name and/or Select a Type.", "INPUT ERROR");
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
			java.util.logging.Logger.getLogger(CreateProblemFeatureGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(CreateProblemFeatureGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(CreateProblemFeatureGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(CreateProblemFeatureGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new CreateProblemFeatureGUI().setVisible(true);
			}
		});
	}

	// Variables declaration                  
	private javax.swing.JButton addAnotherButton;
	private javax.swing.JRadioButton customRadioButton;
	private javax.swing.JButton finishButton;
	private javax.swing.JLabel featureNameLabel;
	private javax.swing.JTextField nameInputTextBox;
	private javax.swing.JRadioButton numberRadioButton;
	private javax.swing.JRadioButton textRadioButton;
	@SuppressWarnings("rawtypes")
	private javax.swing.JComboBox comboBox;	
	private javax.swing.JLabel lblDistanceMetric;
}
