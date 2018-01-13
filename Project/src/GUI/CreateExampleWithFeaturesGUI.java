
package GUI;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;

import Model.ComplexFeature;
import Model.Example;
import Model.Feature;
import Model.SimpleFeature;
import Model.TrainingExample;

/**
* This is the GUI Class to show all the created features.
* @author Jobin Mathew and Joe Samuel
*/
public class CreateExampleWithFeaturesGUI extends javax.swing.JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DefaultListModel<Feature> featuresList;
	private Example trainExample;
	private String string = "";
	
	/**
	* Creates new form CreateExampleWithFeaturesGUI (Constructor)
	* @param featuresList: DefaultListModel<Feature>
	*/
	public CreateExampleWithFeaturesGUI(DefaultListModel<Feature> featuresList) {
		this.featuresList = featuresList;    
		initComponents();
		setLocationRelativeTo(null);
	}
	
	/**
	* Creates new form CreateExampleWithFeaturesGUI (Alternate Constructor)
	* @param featuresList: DefaultListModel<Feature>
	* @param exampleType: String
	* @param te: Example
	*/
	public CreateExampleWithFeaturesGUI(DefaultListModel<Feature> featuresList, String exampleType, Example te) {
		this.featuresList = featuresList;
		this.trainExample = te;
		this.string = exampleType;
		initComponents();
		setLocationRelativeTo(null);
	}
	
	/**
	 * Original Constructor
	 */
	public CreateExampleWithFeaturesGUI() {   
		initComponents();
		setLocationRelativeTo(null);
	}

	/**
	* This method is called from within the constructor to initialize the form.
	*/
	// <editor-fold defaultstate="collapsed" desc="Generated Code">                          
	private void initComponents() {

		jScrollPane1 = new javax.swing.JScrollPane();
		featureJList = new javax.swing.JList<>();
		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		ValueTextField = new javax.swing.JTextField();
		AssignValueButton = new javax.swing.JButton();
		SetAsOutputButton = new javax.swing.JButton();
		FinishButton = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		featureJList.setModel(featuresList);
		featureJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jScrollPane1.setViewportView(featureJList);

		jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel1.setText("Choose Feature");

		jLabel2.setText("Value: ");

		AssignValueButton.setText("Assign Value");
		AssignValueButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				AssignValueButtonActionPerformed(evt);
			}
		});

		SetAsOutputButton.setText("Set as Output");
		SetAsOutputButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				SetAsOutputButtonActionPerformed(evt);
			}
		});

		FinishButton.setText("Finish");
		FinishButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				FinishButtonActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
		layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		.addGroup(layout.createSequentialGroup()
		.addContainerGap()
		.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
		.addGap(0, 0, Short.MAX_VALUE)
		.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
		.addComponent(jScrollPane1)
		.addGroup(layout.createSequentialGroup()
		.addComponent(jLabel2)
		.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		.addComponent(ValueTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
		.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		.addComponent(AssignValueButton)
		.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		.addComponent(SetAsOutputButton))
		.addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)))
		.addComponent(FinishButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		.addContainerGap())
		);
		layout.setVerticalGroup(
		layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		.addGroup(layout.createSequentialGroup()
		.addContainerGap()
		.addComponent(jLabel1)
		.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
		.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
		.addComponent(jLabel2)
		.addComponent(ValueTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		.addComponent(AssignValueButton)
		.addComponent(SetAsOutputButton))
		.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		.addComponent(FinishButton)
		.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		
		if(this.string.equals("testing"))SetAsOutputButton.setEnabled(false);
		else SetAsOutputButton.setEnabled(true);
		pack();
	}                  

	private void AssignValueButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                
		if(ValueTextField.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "Please assign a value");
		}
		else{
			createFeature();
		}
	}   
	
	@SuppressWarnings("deprecation")
	private void createFeature() {
		if(featureJList.getSelectedValue().getType().equals("Num")) {
			String name	= featureJList.getSelectedValue().getName();
			double newValue = Double.parseDouble(ValueTextField.getText());
			Feature newFeature = new SimpleFeature(name, newValue);
			trainExample.getInput().addFeature(newFeature);
		}
		else if(featureJList.getSelectedValue().getType().equals("String")) {
			String name	= featureJList.getSelectedValue().getName();
			String newValue = (String)ValueTextField.getText();
			Feature newFeature = new SimpleFeature(name, newValue);
			trainExample.getInput().addFeature(newFeature);
		}
		else if(featureJList.getSelectedValue().getType().equals("Complex")) {
			String name	= featureJList.getSelectedValue().getName();
			ComplexFeature f = new ComplexFeature(name);
			ComplexFeatureGUI c = new ComplexFeatureGUI(1,f);
			c.show();
			trainExample.getInput().addFeature(f);
		}
		else {
			JOptionPane.showMessageDialog(this, "Please Try again!");
		}
	}
	
	private void SetAsOutputButtonActionPerformed(java.awt.event.ActionEvent evt) {
		Feature outputSelection = new SimpleFeature("","");
		if(ValueTextField.getText().equals("")) { 
			JOptionPane.showMessageDialog(this, "Please enter a value.");
		}
		else {
			if(featureJList.getSelectedValue().getType().equals("Num")) {
				double newValue = Double.parseDouble(ValueTextField.getText());
				outputSelection = new SimpleFeature(featureJList.getSelectedValue().getName(), newValue);
			} else if(featureJList.getSelectedValue().getType().equals("String")) {
				outputSelection = new SimpleFeature(featureJList.getSelectedValue().getName(), ValueTextField.getText());
			}
			TrainingExample te = (TrainingExample) trainExample;
			te.setOutput(outputSelection);
			JOptionPane.showMessageDialog(this, "Your output feature to be predicted has been set.");
		}
	}                                                 

	private void FinishButtonActionPerformed(java.awt.event.ActionEvent evt) {
		dispose();
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
			java.util.logging.Logger.getLogger(CreateExampleWithFeaturesGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(CreateExampleWithFeaturesGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(CreateExampleWithFeaturesGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(CreateExampleWithFeaturesGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new CreateExampleWithFeaturesGUI().setVisible(true);
			}
		});
	}

	// Variables declaration
	private javax.swing.JButton AssignValueButton;
	private javax.swing.JButton FinishButton;
	private javax.swing.JButton SetAsOutputButton;
	private javax.swing.JTextField ValueTextField;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JList<Feature> featureJList;
	private javax.swing.JScrollPane jScrollPane1;
	// End of variables declaration                   
}
