
package GUI;

import Model.Example;

/**
* A Class to set up 
* @author Jobin Mathew
*/
public class CreateFeatureGUI extends javax.swing.JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Example featureExample;
	private String featureName;
	
	/**
	* Creates new form CreateFeature
	* @param featureExample: Example
	*/
	public CreateFeatureGUI(Example featureExample) {
		this.featureExample = featureExample;
		initComponents();  
	}
	/**
	* Original Constructor
	*/
	public CreateFeatureGUI() {
		initComponents();  
	}

	/**
	* This method is called from within the constructor to initialize the form.
	*/    
	private void initComponents() {

		continueButton = new javax.swing.JButton();
		nameTextField = new javax.swing.JTextField();
		jLabel3 = new javax.swing.JLabel();
		numberRadioButton = new javax.swing.JRadioButton();
		textRadioButton = new javax.swing.JRadioButton();
		customRadioButton = new javax.swing.JRadioButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("CREATE FEATURE");
		setResizable(false);

		continueButton.setText("Continue");
		continueButton.setEnabled(false);
		continueButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				continueButtonActionPerformed(evt);
			}
		});

		jLabel3.setText("Name of the Feature: ");

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

		customRadioButton.setText("Custom Feature");
		customRadioButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				customRadioButtonActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
		layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
		.addGap(82, 82, 82)
		.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
		.addComponent(continueButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		.addGroup(layout.createSequentialGroup()
		.addComponent(jLabel3)
		.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		.addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
		.addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
		.addGap(42, 42, 42)
		.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		.addComponent(numberRadioButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		.addComponent(textRadioButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		.addGroup(layout.createSequentialGroup()
		.addComponent(customRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
		.addGap(0, 0, Short.MAX_VALUE)))))
		.addGap(107, 107, 107))
		);
		layout.setVerticalGroup(
		layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
		.addGap(91, 91, 91)
		.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
		.addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		.addComponent(jLabel3))
		.addGap(30, 30, 30)
		.addComponent(numberRadioButton)
		.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		.addComponent(textRadioButton)
		.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		.addComponent(customRadioButton)
		.addGap(18, 18, 18)
		.addComponent(continueButton)
		.addContainerGap(87, Short.MAX_VALUE))
		);

		pack();
		setLocationRelativeTo(null);
	}

	private void numberRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {
		textRadioButton.setSelected(false);
		customRadioButton.setSelected(false);
		if(numberRadioButton.isSelected())
		continueButton.setEnabled(true);
		else continueButton.setEnabled(false);
	}

	private void textRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {
		numberRadioButton.setSelected(false);
		customRadioButton.setSelected(false);
		if(textRadioButton.isSelected())
		continueButton.setEnabled(true);
		else continueButton.setEnabled(false);
	}
	
	private void customRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {
		numberRadioButton.setSelected(false);
		textRadioButton.setSelected(false);
		if(customRadioButton.isSelected())
		continueButton.setEnabled(true);
		else continueButton.setEnabled(false);
	}

	@SuppressWarnings("deprecation")
	private void continueButtonActionPerformed(java.awt.event.ActionEvent evt) {
		if(nameTextField.getText().length()>0){
			if(nameTextField.getText().contains(" ")){
				Popup.infoBox("You cannot have spaces in the Name.", "INPUT ERROR");
			}
			else if(numberRadioButton.isSelected()) {
				featureName = nameTextField.getText();
				NumberFeatureGUI num = new NumberFeatureGUI(featureExample, featureName);
				num.show();
				dispose();
			}
			else if(textRadioButton.isSelected()){
				TextFeatureGUI txt = new TextFeatureGUI(featureExample, featureName);
				txt.show();
				dispose();
			}
			else if(customRadioButton.isSelected()){
				//CustomFeatureGUI cus = new CustomFeatureGUI(featureExample, featureName);
				//cus.show();
				dispose();
			}
			else{
				Popup.infoBox("Please Enter a Name.", "INPUT ERROR");
			}
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
			java.util.logging.Logger.getLogger(CreateFeatureGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(CreateFeatureGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(CreateFeatureGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(CreateFeatureGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new CreateFeatureGUI().setVisible(true);
			}
		});
	}

	// Variables declaration
	private javax.swing.JButton continueButton;
	private javax.swing.JRadioButton customRadioButton;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JTextField nameTextField;
	private javax.swing.JRadioButton numberRadioButton;
	private javax.swing.JRadioButton textRadioButton;
	// End of variables declaration
}