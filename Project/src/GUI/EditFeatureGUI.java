package GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import Model.Example;
import Model.TestingExample;
import Model.TrainingExample;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;

@SuppressWarnings("unused")
public class EditFeatureGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DefaultListModel<TrainingExample> featuresListTraining;// 1
	private DefaultListModel<TestingExample> featuresListTesting;// 2
	private DefaultListModel<Example> featuresList;// 3
	private javax.swing.JList<TrainingExample> list1;
	private javax.swing.JList<TestingExample> list2;
	private javax.swing.JList<Example> list;
	
	private int listType;

	public EditFeatureGUI() {
		setResizable(false);
		makeFrame();
	}

	public EditFeatureGUI(DefaultListModel<Example> defaultListModel, Example example) {
		this.featuresList = new DefaultListModel<Example>();
		this.featuresList = defaultListModel;
		this.listType = 3;
		makeFrame();
	}

	public EditFeatureGUI(DefaultListModel<TestingExample> testExamples, TestingExample selectedValue) {
		this.featuresListTesting = new DefaultListModel<TestingExample>();
		this.featuresListTesting = testExamples;
		this.listType = 2;
		makeFrame();
	}

	public EditFeatureGUI(DefaultListModel<TrainingExample> trainingExamples, TrainingExample selectedValue) {
		this.featuresListTraining = new DefaultListModel<TrainingExample>();
		this.featuresListTraining = trainingExamples;
		this.listType = 1;
		makeFrame();
	}

	/**
	 * Create the frame.
	 */
	private void makeFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setSize(408, 381);

		JLabel lblSelectTheFeature = new JLabel("Select the feature you want to edit:");
		lblSelectTheFeature.setHorizontalAlignment(SwingConstants.CENTER);

		JButton btnRemoveFeatureName = new JButton("Remove Feature");
		btnRemoveFeatureName.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				removeButtonActionPerformed(evt);
			}

			private void removeButtonActionPerformed(ActionEvent evt) {
				if (!list.isSelectionEmpty()) {
					// example.getInput().removeFeature(list.getSelectedValue());
					refreshList();
				} else {
					Popup.infoBox("Please make a selection", "Error. Try again.");

				}
			}
		});
		btnRemoveFeatureName.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				finishButtonActionPerformed(evt);
			}
		});

		JButton btnEditFeatureName = new JButton("Edit Feature");
		btnEditFeatureName.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				editButtonActionPerformed(evt);
			}
		});

		JLabel label = new JLabel("");

		JButton finish = new JButton("Done");

		// pack();
		setContentPane(contentPane);

		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(Alignment.LEADING,
										gl_contentPane.createSequentialGroup().addContainerGap().addComponent(label,
												GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE))
								.addGroup(Alignment.LEADING,
										gl_contentPane.createSequentialGroup().addContainerGap().addComponent(
												lblSelectTheFeature, GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE))
								.addGroup(gl_contentPane.createSequentialGroup().addContainerGap().addComponent(
										btnEditFeatureName, GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE))
								.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
										.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE))
								.addGroup(gl_contentPane.createSequentialGroup().addGap(10).addComponent(
										btnRemoveFeatureName, GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)))
						.addGap(10))
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
						.addComponent(finish, GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE).addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(lblSelectTheFeature, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
						.addGap(2).addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(label, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE).addGap(4)
						.addComponent(btnEditFeatureName, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(btnRemoveFeatureName, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(finish, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(27, Short.MAX_VALUE)));

		list = new JList<>();
		scrollPane.setViewportView(list);
		if(this.listType == 1){
			//list.setModel(featuresListTraining);
		}
		else if (this.listType == 2){
			//list.setModel(featuresListTesting);
		}
		else{
			list.setModel(featuresList);
		}
		
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		contentPane.setLayout(gl_contentPane);
		setLocationRelativeTo(null);
	}

	public void finishButtonActionPerformed(ActionEvent evt) {
		this.dispose();
	}

	public void editButtonActionPerformed(ActionEvent evt) {
		/*
		 * if(!list.isSelectionEmpty()) {
		 * if(list.getSelectedValue().getType().equals("Num")) { double newValue
		 * = Double.parseDouble(JOptionPane.showInputDialog(this,
		 * "Please enter the new value"));
		 * list.getSelectedValue().setValue(newValue); } else
		 * if(list.getSelectedValue().getType().equals("String")) { String
		 * newValue = JOptionPane.showInputDialog(this,
		 * "Please enter the new text");
		 * list.getSelectedValue().setStringValue(newValue); } else
		 * if(list.getSelectedValue().getType().equals("Custom")) {
		 * JOptionPane.showMessageDialog(this,
		 * "Unable to change complex feature at the moment"); } else {
		 * JOptionPane.showMessageDialog(this,
		 * "Sorry your choice wasn't recognized. Please try again."); }
		 * refreshList(); }else { JOptionPane.showMessageDialog(this,
		 * "Please make a selection"); }
		 */
	}

	private void refreshList() {
		list.setModel(featuresList);
		list.setFocusable(true);
		list.grabFocus();
	}
}
