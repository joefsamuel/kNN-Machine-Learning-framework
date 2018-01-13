package GUI;

import Model.Example;
import Model.Feature;
import Model.SimpleFeature;

/**
 * The NumberFeatureGUI is the Graphical User Interface when the number feature is selected and used for input. 
 * 
 * @author Joe Samuel, Jobin Mathew
 *
 */
public class NumberFeatureGUI extends javax.swing.JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Example featureExample;
	private String featureName;
   
	/**
	 * Constructor 
	 * @param featureExample The example to which the feature is to be added.
	 * @param featureName	Name of the feature in the example.
	 */
    public NumberFeatureGUI(Example featureExample, String featureName) {
    this.featureExample = featureExample;    
    this.featureName = featureName;
	initComponents();
    }

    /**
	 * Constructor - To Initialize GUI interface
	 */
    public NumberFeatureGUI() {
    	initComponents();
	}

    private void initComponents() {

        addNumber = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        addFeatureButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("ENTER THE VALUE OF THIS FEATURE");


        jLabel1.setText("Enter Number:");

        addFeatureButton.setText("Add Feature");
        addFeatureButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addFeatureButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(119, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(addFeatureButton, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(116, 116, 116))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(93, 93, 93)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(53, 53, 53)
                .addComponent(addFeatureButton)
                .addContainerGap(111, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }

    private void addFeatureButtonActionPerformed(java.awt.event.ActionEvent evt) {
    	try {
			double doubleValue = Double.parseDouble(addNumber.getText());
			Feature newFeatureForExample = new SimpleFeature(featureName, doubleValue);
			System.out.println(doubleValue);
			featureExample.getInput().addFeature(newFeatureForExample);
			Popup.infoBox("Feature has succesfully been added", "Success!");
			dispose();
        }
        catch(NumberFormatException ex)
        {
            System.out.println("Exception : "+ ex);
            dispose();
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
            java.util.logging.Logger.getLogger(NumberFeatureGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NumberFeatureGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NumberFeatureGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NumberFeatureGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NumberFeatureGUI().setVisible(true);
            }
        });
    }

    // Variables declaration
    private javax.swing.JButton addFeatureButton;
    private javax.swing.JTextField addNumber;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration
}