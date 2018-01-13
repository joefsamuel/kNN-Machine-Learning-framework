package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JWindow;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import Model.Example;
import Model.Feature;
import Model.KNN;
import Model.KnnXMLHandler;
import Model.PredictionType;
import Model.TestingExample;
import Model.TrainingExample;

/**
 * The main View Class.
 *
 * @author Jobin Mathew and Joe Samuel
 */
@SuppressWarnings("unused")
public class View {

	/** The frame. */
	private JFrame frame;
	
	/** The menu bar. */
	private JMenuBar menuBar;
	
	/** The Example. */
	private JMenu Feature, Example;
	
	/** The remove feature. */
	private JMenuItem createFeature, editFeature, removeFeature;
	
	/** The remove example. */
	private JMenuItem createExample, editExample, removeExample;
	
	/** The create problem. */
	private JButton calculateOutput, Reset, createProblem;
	
	/** The training example. */
	private JList<TrainingExample> trainingExample;
	
	/** The testing example. */
	private JList<TestingExample> testingExample;
	
	/** The training example label. */
	private javax.swing.JLabel trainingExampleLabel;
	
	/** The testing example label. */
	private javax.swing.JLabel testingExampleLabel;

	/** The features list. */
	// List for features
	private DefaultListModel<Feature> featuresList;
	
	/** The new example. */
	private Example newExample;
	
	/** The knn. */
	private KNN knn;
	
	/** The mn data. */
	private JMenu mnData;
	
	/** The mntm import. */
	private JMenuItem mntmImport;
	
	/** The mntm export. */
	private JMenuItem mntmExport;

	/** The file chooser. */
	private JFileChooser fileChooser;

	/** The knn created. */
	private boolean knnCreated = false;

	/**
	 * Constructor.
	 *
	 * @param knn            TThe knn responsible for testing and training lists
	 */
	public View(KNN knn) {
		trainingExample = new JList<>();
		testingExample = new JList<>();
		featuresList = new DefaultListModel<Feature>();
		this.knn = knn;
		makeFrame();
	}

	/**
	 * Method to show the splash screen.
	 *
	 * @return void
	 */
	public void splashScreen() {
		// Splash screen
		JWindow window = new JWindow();
		window.getContentPane()
				.add(new JLabel("Welcome to AI-Dev's Machine Learning Framework", SwingConstants.CENTER));
		window.setBounds(500, 500, 500, 300);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		window.setVisible(false);
		window.dispose();
	}

	/**
	 * Method to initialize the frame.
	 *
	 * @return void
	 */
	public void makeFrame() {
		// Splash screen
		splashScreen();

		// Creating new Frame
		frame = new JFrame("AI DEV MILESTONE 4");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 400);
		frame.setLocationRelativeTo(null);

		// Creating new Menu bar
		menuBar = new JMenuBar();

		// Action Listener
		addActionListener menuButton = new addActionListener();

		// Create Problem
		createProblem = new JButton("Create problem");
		createProblem.addActionListener(menuButton);
		menuBar.add(createProblem);

		// File I/O
		mnData = new JMenu("File");
		menuBar.add(mnData);

		mntmImport = new JMenuItem("Import");

		mntmImport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

				FileNameExtensionFilter filter = new FileNameExtensionFilter("XML Files", "xml");
				fileChooser.setFileFilter(filter);

				int result = fileChooser.showOpenDialog(frame);

				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					String s = getFileExtension(selectedFile.getName());
					if (s.equals("xml")) {
						try {
							@SuppressWarnings("resource")
							BufferedReader br = new BufferedReader(new FileReader(selectedFile));
							if (br.readLine() == null) {
								Popup.infoBox("The selected file is empty!", "NO DATA");
							} else
								readFile(selectedFile);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					} else
						Popup.infoBox("Please select an .xml file!", "INVALID FILE TYPE");
				}
			}
		});
		mnData.add(mntmImport);

		mntmExport = new JMenuItem("Save as XML");
		mntmExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				fileChooser.setFileFilter(new FileNameExtensionFilter("XML file", "xml"));
				int result = fileChooser.showSaveDialog(frame);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					try {
						writeFile(selectedFile);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		mnData.add(mntmExport);
		mntmExport.setEnabled(false);
		createProblem.setEnabled(true);

		// File Chooser initalization
		fileChooser = new JFileChooser();

		// Main Menu
		Feature = new JMenu("Feature");
		Feature.setEnabled(false);
		Example = new JMenu("Example");
		menuBar.add(Example);
		menuBar.add(Feature);
		frame.setJMenuBar(menuBar);

		// Labels
		trainingExampleLabel = new javax.swing.JLabel();
		testingExampleLabel = new javax.swing.JLabel();

		// Menu Items
		// Example based menu items
		createExample = new JMenuItem("Create Example");
		createExample.setAccelerator(
				javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK));
		createExample.addActionListener(menuButton);
		Example.setEnabled(false);
		Example.add(createExample);

		editExample = new JMenuItem("Edit Example");
		editExample.setAccelerator(
				javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
		editExample.addActionListener(menuButton);
		editExample.setEnabled(false);
		Example.add(editExample);

		removeExample = new JMenuItem("Delete Example");
		removeExample.setAccelerator(
				javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
		removeExample.addActionListener(menuButton);
		removeExample.setEnabled(false);
		Example.add(removeExample);

		editFeature = new JMenuItem("Edit/Remove Feature");
		editFeature.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E,
				java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
		editFeature.addActionListener(menuButton);
		editFeature.setEnabled(false);
		Feature.add(editFeature);

		// Buttons
		// Reset button
		Reset = new JButton("Reset program");
		Reset.addActionListener(menuButton);
		Reset.setEnabled(false);
		menuBar.add(Reset);

		// Calculate Output button
		calculateOutput = new JButton("Calculate Output");
		calculateOutput.setEnabled(false);
		calculateOutput.addActionListener(menuButton);
		menuBar.add(calculateOutput);

		// JList creation
		trainingExample.setModel(knn.getTrainingExamples());
		trainingExample.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		testingExample.setModel(knn.getTestExamples());
		testingExample.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane jScrollPaneTraining = new JScrollPane(trainingExample);
		JScrollPane jScrollPaneTesting = new JScrollPane(testingExample);
		frame.getContentPane().add(jScrollPaneTraining);
		frame.getContentPane().add(jScrollPaneTesting);

		// Layout
		jScrollPaneTraining.setViewportView(trainingExample);
		jScrollPaneTesting.setViewportView(testingExample);

		trainingExampleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		trainingExampleLabel.setText("Training Example - Current Dataset");
		testingExampleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		testingExampleLabel.setText("Testing Example - Dataset to predict the output");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(frame.getContentPane());
		frame.getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
								.addComponent(trainingExampleLabel, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(jScrollPaneTraining))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jScrollPaneTesting).addComponent(testingExampleLabel,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE))
						.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(trainingExampleLabel).addComponent(testingExampleLabel))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jScrollPaneTraining, javax.swing.GroupLayout.Alignment.TRAILING,
										javax.swing.GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE)
								.addComponent(jScrollPaneTesting, javax.swing.GroupLayout.Alignment.TRAILING))
						.addContainerGap()));

		jScrollPaneTraining.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		jScrollPaneTraining.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		jScrollPaneTesting.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		jScrollPaneTesting.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		// Frame attributes
		frame.setResizable(false);
		frame.setVisible(true);
		frame.pack();
	}

	/**
	 * Gets the calculate output.
	 *
	 * @return the calculate output
	 */
	public JButton getCalculateOutput() {
		return calculateOutput;
	}

	/**
	 * Sets the calculate output.
	 *
	 * @param calculateOutput the new calculate output
	 */
	public void setCalculateOutput(JButton calculateOutput) {
		this.calculateOutput = calculateOutput;
	}

	/**
	 * The listener interface for receiving addAction events.
	 * The class that is interested in processing a addAction
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addaddActionListener<code> method. When
	 * the addAction event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see addActionEvent
	 */
	/*
	 * Action Listener Class
	 */
	private class addActionListener implements ActionListener {
		
		/**
		 * Creates the feature.
		 */
		@SuppressWarnings({ "deprecation" })
		private void createFeature() {
			if (trainingExample.isSelectionEmpty() && !testingExample.isSelectionEmpty()) {
				CreateFeatureGUI newFeatureExample = new CreateFeatureGUI(testingExample.getSelectedValue());
				newFeatureExample.show();
			} else if (!trainingExample.isSelectionEmpty() && testingExample.isSelectionEmpty()) {
				CreateFeatureGUI newFeatureExample = new CreateFeatureGUI(trainingExample.getSelectedValue());
				newFeatureExample.show();
			} else {
				JOptionPane.showMessageDialog(frame, "Please choose only example from one list.");
			}

		}

		/**
		 * Feature edit.
		 */
		private void featureEdit() {
			if (trainingExample.isSelectionEmpty() && !testingExample.isSelectionEmpty()) {
				EditFeatureGUI editFeature = new EditFeatureGUI(knn.getTestExamples(),
						testingExample.getSelectedValue());
				editFeature.setVisible(true);
				// editFeature.getFeaturesList();
			} else if (!trainingExample.isSelectionEmpty() && testingExample.isSelectionEmpty()) {
				EditFeatureGUI editFeature = new EditFeatureGUI(knn.getTrainingExamples(),
						trainingExample.getSelectedValue());
				editFeature.setVisible(true);
				// editFeature.getFeaturesList();
			} else {
				JOptionPane.showMessageDialog(frame, "Please choose only example from one list.");
			}
			refreshList();
		}

		/**
		 * Feature remove.
		 */
		private void featureRemove() {
			refreshList();
		}

		/**
		 * Example edit.
		 */
		private void exampleEdit() {
			if (trainingExample.isSelectionEmpty() && !testingExample.isSelectionEmpty()) {
				String newName = JOptionPane.showInputDialog("Please enter new name for example");
				if (newName.isEmpty()) {
					Popup.infoBox("Example Name not modified!", "WARNING");
				} else {
					testingExample.getSelectedValue().setName(newName);
					JOptionPane.showMessageDialog(frame, "Example has been renamed.");
				}
			} else if (!trainingExample.isSelectionEmpty() && testingExample.isSelectionEmpty()) {
				String newName = JOptionPane.showInputDialog("Please enter new name for example");
				if (newName.isEmpty()) {
					Popup.infoBox("Example Name not modified!", "WARNING");
				} else {
					trainingExample.getSelectedValue().setName(newName);
					JOptionPane.showMessageDialog(frame, "Example has been renamed.");
				}
			} else {
				JOptionPane.showMessageDialog(frame, "Please choose only example from one list.");
			}
		}

		/**
		 * Example remove.
		 */
		private void exampleRemove() {
			if (!trainingExample.isSelectionEmpty() && !testingExample.isSelectionEmpty()) {
				knn.getTrainingExamples().removeElement(trainingExample.getSelectedValue());
				knn.getTestExamples().removeElement(testingExample.getSelectedValue());
				JOptionPane.showMessageDialog(frame, "Successfully removed items.");
			} else if (trainingExample.isSelectionEmpty() && !testingExample.isSelectionEmpty()) {
				knn.getTestExamples().removeElement(testingExample.getSelectedValue());
				JOptionPane.showMessageDialog(frame, "Successfully removed items.");
			} else if (!trainingExample.isSelectionEmpty() && testingExample.isSelectionEmpty()) {
				knn.getTrainingExamples().removeElement(trainingExample.getSelectedValue());
				JOptionPane.showMessageDialog(frame, "Successfully removed items.");
			} else {
				JOptionPane.showMessageDialog(frame, "Please select an example from either or both of the lists.");
			}
		}

		/**
		 * Confirm result.
		 */
		private void confirmResult() {
			int userResult = JOptionPane.showConfirmDialog(null,
					"Would you like to save the result as a training example?", "Confirmation of result",
					JOptionPane.YES_NO_OPTION);
			if (userResult == JOptionPane.YES_OPTION) {
				TestingExample temp = knn.getTestExamples().get(0);
				temp.getPredictedOutput().setName(knn.getTrainingExamples().get(0).getOutput().getName());
				TrainingExample newTrainingExample = new TrainingExample(temp.getName(), temp.getInput(),
						temp.getPredictedOutput());
				knn.getTrainingExamples().addElement(newTrainingExample);
				knn.getTestExamples().removeElement(temp);
			} else {
				JOptionPane.showMessageDialog(frame, "Thank you for using AI-Dev's Machine Learning Framework.");
			}

		}

		/**
		 * Refresh list.
		 */
		private void refreshList() {
			trainingExample.setModel(knn.getTrainingExamples());
			trainingExample.setFocusable(true);
			trainingExample.grabFocus();
			testingExample.setModel(knn.getTestExamples());
			testingExample.setFocusable(true);
			testingExample.grabFocus();
		}

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent e) {

			switch (e.getActionCommand()) {
			case "Create problem":
				CreateProblemFeatureGUI newProblem = new CreateProblemFeatureGUI(featuresList);
				newProblem.show();
				createProblem.setEnabled(false);
				Example.setEnabled(true);
				Reset.setEnabled(true);
				mntmExport.setEnabled(true);
				knnCreated = true;
				refreshList();
				break;

			case "Create Example":
				ExampleTypeGUI newFeatureType = new ExampleTypeGUI(newExample, knn, featuresList);
				newFeatureType.show();
				Feature.setEnabled(true);
				editExample.setEnabled(true);
				removeExample.setEnabled(true);
				Reset.setEnabled(true);
				calculateOutput.setEnabled(true);
				editFeature.setEnabled(true);
				refreshList();
				break;

			case "Edit Example":
				exampleEdit();
				refreshList();
				break;

			case "Delete Example":
				exampleRemove();
				refreshList();
				break;

			case "Edit/Remove Feature":
				featureEdit();
				refreshList();
				break;

			case "Reset program":
				newExample = null;
				featuresList.removeAllElements();
				createProblem.setEnabled(true);
				Example.setEnabled(false);
				knn.getTestExamples().removeAllElements();
				knn.getTrainingExamples().removeAllElements();
				Feature.setEnabled(false);
				calculateOutput.setEnabled(false);
				refreshList();
				JOptionPane.showMessageDialog(frame, "Program Successfully reset.");
				Reset.setEnabled(false);
				knnCreated = false;
				mntmExport.setEnabled(false);
				break;

			case "Calculate Output":
				if (knn.getTrainingExamples().getSize() > 1 && knn.getTestExamples().getSize() > 0) {
					String newK = JOptionPane.showInputDialog("Please enter a value for K");
					int kval = Integer.parseInt(newK);
					if (kval > knn.getTrainingExamples().size()) {
						Popup.infoBox("Value of K must be less than or equal to the number of Training Examples!",
								"INVALID");
					}
					knn.setK(kval);
					knn.makePrediction();
					JOptionPane.showMessageDialog(frame, knn.toString());
					confirmResult();
					refreshList();
				} else {
					if (knn.getTrainingExamples().getSize() < 0) {
						JOptionPane.showMessageDialog(frame,
								"Please add atleast one more training example to predict output");
					} else {
						JOptionPane.showMessageDialog(frame,
								"Please add atleast one more testing example to predict output");
					}
				}

				break;

			default:
				JOptionPane.showMessageDialog(frame, "Unfortunately your command was not available. Please try again.");
				break;
			}
		}
	}

	/**
	 * Read file.
	 *
	 * @param choosenFile the choosen file
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void readFile(File choosenFile) throws FileNotFoundException, IOException {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			KnnXMLHandler knnHandler = new KnnXMLHandler();
			saxParser.parse(choosenFile, knnHandler);
			this.knn = knnHandler.getKNN();
			JOptionPane.showMessageDialog(frame, knnHandler.getKnnPrediction());
		} catch (Exception e) {
			e.printStackTrace();
		}
		knnCreated = true;
		mntmExport.setEnabled(true);
	}

	/**
	 * Write file.
	 *
	 * @param choosenFile the choosen file
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void writeFile(File choosenFile) throws FileNotFoundException, IOException {
		if (knnCreated) {
			FileOutputStream newFile = new FileOutputStream(choosenFile);
			BufferedWriter writer = new BufferedWriter( new OutputStreamWriter( newFile, Charset.forName("UTF-8") ) );
			writer.write(knn.toXML());
			writer.flush();
			newFile.close();
		} else {
			JOptionPane.showMessageDialog(frame, "Please create a problem or import a file.");
		}

	}

	/**
	 * Layout configuration for the panel and redundancy measures in place.
	 *
	 * @param args the arguments
	 * @return void
	 */
	public static void main(String[] args) {
		DefaultListModel<TrainingExample> trainExamples = new DefaultListModel<>();
		DefaultListModel<TestingExample> testExamples = new DefaultListModel<>();
		KNN knn = new KNN(trainExamples, testExamples, PredictionType.VOTING, 1);
		new View(knn);
	}

	/**
	 * Gets the file extension.
	 *
	 * @param fullName the full name
	 * @return the file extension
	 */
	public static String getFileExtension(String fullName) {
		if (fullName.equals(""))
			return "";
		else {
			String fileName = new File(fullName).getName();
			int dotIndex = fileName.lastIndexOf('.');
			return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
		}
	}
}