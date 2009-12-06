/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ImporterPanel.java
 *
 * Created on 02.11.2009, 22:14:15
 */

package de.unibw.fusionvis.frontend;

import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import de.unibw.fusionvis.FusionVisForm;
import de.unibw.fusionvis.backend.Importer;
import de.unibw.fusionvis.datamodel.Data;
import de.unibw.fusionvis.datamodel.DataSet;
import de.unibw.fusionvis.datamodel.properties.AbstractProperty;

/**
 * 
 * @author stzschoppe
 */
public class ImporterPanel extends javax.swing.JPanel implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5038004658178727397L;
	/** Creates new form ImporterPanel */
	public ImporterPanel(Importer importer) {
		initComponents();
		// importer.addObserver(this); //XXX Was habe ich mir dabei gedacht
		this.importer = importer;
		observableSupportForFilter = new ObservableSupport();
		observableSupportForSelection = new ObservableSupport();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents() {

		importerOverviewPanel = new javax.swing.JPanel();
		importerDataSetIdLabel = new javax.swing.JLabel();
		importerListingPanel = new javax.swing.JPanel();
		importerFilterPanel = new javax.swing.JPanel();
		importerFilterTextPanel = new javax.swing.JPanel();
		importerFilterKeyLabel = new javax.swing.JLabel();
		importerFilterValueLabel = new javax.swing.JLabel();
		importerFilterKeyText = new javax.swing.JTextField();
		importerFilterValueText = new javax.swing.JTextField();
		importerFilterButton = new javax.swing.JButton();
		jSeparator1 = new javax.swing.JSeparator();
		importerFilterViewPanel = new javax.swing.JPanel();
		importerFilterViewLabel = new javax.swing.JLabel();
		importerFilterViewComboBox = new javax.swing.JComboBox();
		importerListScrollPanel = new javax.swing.JScrollPane();
		importerDetailTree = new javax.swing.JTree();

		setMinimumSize(new java.awt.Dimension(200, 600));
		setLayout(new java.awt.BorderLayout());

		// importerSplitPane.setDividerLocation(150);

		importerOverviewPanel.setLayout(new java.awt.BorderLayout());

		importerDataSetIdLabel
				.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		importerDataSetIdLabel.setText("DataSetID");
		importerOverviewPanel.add(importerDataSetIdLabel,
				java.awt.BorderLayout.PAGE_START);

		importerListingPanel.setLayout(new java.awt.BorderLayout());

		importerFilterPanel.setLayout(new java.awt.BorderLayout());

		importerFilterKeyLabel
				.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		importerFilterKeyLabel.setLabelFor(importerFilterKeyText);
		importerFilterKeyLabel.setText("Key");
		importerFilterKeyLabel
				.setVerticalAlignment(javax.swing.SwingConstants.TOP);

		importerFilterValueLabel.setLabelFor(importerFilterValueText);
		importerFilterValueLabel.setText("Value");

		importerFilterButton.setText("Filter");
		importerFilterButton
				.addMouseListener(new java.awt.event.MouseAdapter() {
					public void mouseClicked(java.awt.event.MouseEvent evt) {
						importerFilterButtonMouseClicked(evt);
					}
				});

		javax.swing.GroupLayout importerFilterTextPanelLayout = new javax.swing.GroupLayout(
				importerFilterTextPanel);
		importerFilterTextPanel.setLayout(importerFilterTextPanelLayout);
		importerFilterTextPanelLayout
				.setHorizontalGroup(importerFilterTextPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								importerFilterTextPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												importerFilterTextPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																jSeparator1,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																133,
																Short.MAX_VALUE)
														.addGroup(
																javax.swing.GroupLayout.Alignment.LEADING,
																importerFilterTextPanelLayout
																		.createSequentialGroup()
																		.addGroup(
																				importerFilterTextPanelLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.TRAILING,
																								false)
																						.addComponent(
																								importerFilterValueLabel,
																								javax.swing.GroupLayout.Alignment.LEADING,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addComponent(
																								importerFilterKeyLabel,
																								javax.swing.GroupLayout.Alignment.LEADING,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								48,
																								Short.MAX_VALUE))
																		.addGap(
																				18,
																				18,
																				18)
																		.addGroup(
																				importerFilterTextPanelLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addGroup(
																								importerFilterTextPanelLayout
																										.createSequentialGroup()
																										.addGap(
																												10,
																												10,
																												10)
																										.addComponent(
																												importerFilterButton,
																												javax.swing.GroupLayout.DEFAULT_SIZE,
																												javax.swing.GroupLayout.DEFAULT_SIZE,
																												Short.MAX_VALUE))
																						.addComponent(
																								importerFilterValueText,
																								javax.swing.GroupLayout.Alignment.TRAILING,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								67,
																								Short.MAX_VALUE)
																						.addComponent(
																								importerFilterKeyText,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								67,
																								Short.MAX_VALUE))))
										.addContainerGap()));
		importerFilterTextPanelLayout
				.setVerticalGroup(importerFilterTextPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								importerFilterTextPanelLayout
										.createSequentialGroup()
										.addComponent(
												jSeparator1,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												13,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(1, 1, 1)
										.addGroup(
												importerFilterTextPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																importerFilterKeyText,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																importerFilterKeyLabel))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												importerFilterTextPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																importerFilterValueLabel)
														.addComponent(
																importerFilterValueText,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(importerFilterButton)));

		importerFilterPanel.add(importerFilterTextPanel,
				java.awt.BorderLayout.PAGE_START);

		importerFilterViewLabel.setLabelFor(importerFilterViewComboBox);
		importerFilterViewLabel.setText("Sicht");

		importerFilterViewComboBox
				.setModel(new javax.swing.DefaultComboBoxModel(new String[] {
						"Alles", "Blau", "Rot", "Benutzerdefiniert" }));
		importerFilterViewComboBox
				.addItemListener(new java.awt.event.ItemListener() {
					public void itemStateChanged(java.awt.event.ItemEvent evt) {
						importerFilterViewComboBoxItemStateChanged(evt);
					}
				});
		itemSelected = importerFilterViewComboBox.getSelectedItem();

		javax.swing.GroupLayout importerFilterViewPanelLayout = new javax.swing.GroupLayout(
				importerFilterViewPanel);
		importerFilterViewPanel.setLayout(importerFilterViewPanelLayout);
		importerFilterViewPanelLayout
				.setHorizontalGroup(importerFilterViewPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								importerFilterViewPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(importerFilterViewLabel)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(
												importerFilterViewComboBox, 0,
												101, Short.MAX_VALUE)
										.addContainerGap()));
		importerFilterViewPanelLayout
				.setVerticalGroup(importerFilterViewPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								importerFilterViewPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												importerFilterViewPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																importerFilterViewLabel)
														.addComponent(
																importerFilterViewComboBox,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));

		importerFilterPanel.add(importerFilterViewPanel,
				java.awt.BorderLayout.CENTER);

		importerListingPanel.add(importerFilterPanel,
				java.awt.BorderLayout.PAGE_START);

		importerDetailTree.getSelectionModel().addTreeSelectionListener(
				new javax.swing.event.TreeSelectionListener() {

					@Override
					public void valueChanged(TreeSelectionEvent evt) {
						DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                        importerDetailTree.getLastSelectedPathComponent();
						if (node != null)
								importerTreeValueChanged(node);
					}
				});

		importerListScrollPanel.setViewportView(importerDetailTree);

		importerListingPanel.add(importerListScrollPanel,
				java.awt.BorderLayout.CENTER);

		importerOverviewPanel.add(importerListingPanel,
				java.awt.BorderLayout.CENTER);

		javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode(
				"root");
		importerDetailTree.setModel(new javax.swing.tree.DefaultTreeModel(
				treeNode1));

		add(importerOverviewPanel, java.awt.BorderLayout.CENTER);
	}// </editor-fold>//GEN-END:initComponents

	private void importerFilterButtonMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_importerFilterButtonMouseClicked
		if (importerFilterKeyText.getText().equals("")) {
			// Nichts tun
		} else {
			DefaultMutableTreeNode root;
			switch (importerFilterViewComboBox.getSelectedIndex()) {
			case 0: // Alles
				modelUserDefined = modelAll;
				break;

			case 1: // Blau
				modelUserDefined = modelBlue;
				break;

			case 2: // Rot
				modelUserDefined = modelRed;
				break;

			case 3: // Benutzerdefiniert
				// nichts zu machen
				break;

			default:
				break;
			}
			
			modelUserDefined = modelUserDefined.filterBy(importerFilterKeyText
					.getText(), importerFilterValueText.getText());
			
			root = new DefaultMutableTreeNode(modelUserDefined.getId());
			for (Data data : modelUserDefined.getData()) {
				root.add(createJListNode(data));
			}
			

			FusionVisForm.getLogger().log(
					Level.INFO,
					modelUserDefined.getData().size()
							+ " Datensätze mit angegebener Eigenschaft");
			
			importerFilterViewComboBox.setSelectedIndex(3);
			importerDetailTree.setModel(new DefaultTreeModel(root));
			// XXX kann wohl weg importer.setDataSet(modelUserDefined);
			observableSupportForFilter.markAndNotify(modelUserDefined);
		}
	}// GEN-LAST:event_importerFilterButtonMouseClicked

	private void importerFilterViewComboBoxItemStateChanged(
			java.awt.event.ItemEvent evt) {// GEN-FIRST:event_importerFilterViewComboBoxItemStateChanged
		// TODO Erzeugen der Bäume beim Start einmalig zum Performance Gewinn
		if (!evt.getItem().equals(itemSelected)) {
			itemSelected = evt.getItem();
			DefaultMutableTreeNode root;
			switch (importerFilterViewComboBox.getSelectedIndex()) {
			case 0: // Alles
				root = new DefaultMutableTreeNode(modelAll.getId());
				for (Data data : modelAll.getData()) {
					root.add(createJListNode(data));
				}
				importerDetailTree.setModel(new DefaultTreeModel(root));
				observableSupportForFilter.markAndNotify(modelAll);
				break;

			case 1: // Blau
				root = new DefaultMutableTreeNode(modelBlue.getId());
				for (Data data : modelBlue.getData()) {
					root.add(createJListNode(data));
				}
				importerDetailTree.setModel(new DefaultTreeModel(root));
				observableSupportForFilter.markAndNotify(modelBlue);
				break;

			case 2: // Rot
				root = new DefaultMutableTreeNode(modelRed.getId());
				for (Data data : modelRed.getData()) {
					root.add(createJListNode(data));
				}
				importerDetailTree.setModel(new DefaultTreeModel(root));
				observableSupportForFilter.markAndNotify(modelRed);
				break;

			case 3: // Benutzerdefiniert
				root = new DefaultMutableTreeNode(modelUserDefined.getId());
				for (Data data : modelUserDefined.getData()) {
					root.add(createJListNode(data));
				}
				importerDetailTree.setModel(new DefaultTreeModel(root));
				observableSupportForFilter.markAndNotify(modelUserDefined);
				break;

			default:
				break;
			}
		}

	}// GEN-LAST:event_importerFilterViewComboBoxItemStateChanged

	private void importerTreeValueChanged(DefaultMutableTreeNode node) {// GEN-FIRST:event_importerListValueChanged
		observableSupportForSelection.markAndNotify(node.toString());
		//TODO
	}// GEN-LAST:event_importerListValueChanged

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JLabel importerDataSetIdLabel;
	// private javax.swing.JPanel importerDetailPanel;
	// private javax.swing.JScrollPane importerDetailScrollPanel;
	// private javax.swing.JLabel importerDetailTaxonomy;
	private javax.swing.JTree importerDetailTree;
	private javax.swing.JButton importerFilterButton;
	private javax.swing.JLabel importerFilterKeyLabel;
	private javax.swing.JTextField importerFilterKeyText;
	private javax.swing.JPanel importerFilterPanel;
	private javax.swing.JPanel importerFilterTextPanel;
	private javax.swing.JLabel importerFilterValueLabel;
	private javax.swing.JTextField importerFilterValueText;
	private javax.swing.JComboBox importerFilterViewComboBox;
	private javax.swing.JLabel importerFilterViewLabel;
	private javax.swing.JPanel importerFilterViewPanel;
	// private javax.swing.JList importerList;
	private javax.swing.JScrollPane importerListScrollPanel;
	private javax.swing.JPanel importerListingPanel;
	private javax.swing.JPanel importerOverviewPanel;
	// private javax.swing.JSplitPane importerSplitPane;
	private javax.swing.JSeparator jSeparator1;
	public DataSet modelAll;
	protected DataSet modelRed;
	protected DataSet modelBlue;
	protected DataSet modelUserDefined;
	protected Importer importer;
	protected Object itemSelected;
	public ObservableSupport observableSupportForFilter;

	public ObservableSupport observableSupportForSelection;

	// End of variables declaration//GEN-END:variables
	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof DataSet) {
			DataSet dataSet = (DataSet) arg1;
			modelAll = dataSet;
			// Alle echten Blauen und die gesichteten Roten
			modelBlue = modelAll.filterBy("HostilityCode", "FR").filterBy(
					"Certainty", "Real");
			modelBlue.addDataSet(modelAll.filterBy("HostilityCode", "HO")
					.filterBy("Certainty", "Perceived"));
			// Alle echten Roten und die gesichteten Roten
			modelRed = modelAll.filterBy("HostilityCode", "HO").filterBy(
					"Certainty", "Real");
			modelRed.addDataSet(modelAll.filterBy("HostilityCode", "FR")
					.filterBy("Certainty", "Perceived"));
			// Benutzerdefinierte sicht, die mit den Textfeldern gefiltert werden
			// kann
			modelUserDefined = dataSet;
			importerDataSetIdLabel.setText(dataSet.getId());
			DefaultMutableTreeNode root = new DefaultMutableTreeNode(dataSet
					.getId());
			for (Data data : dataSet.getData()) {
				root.add(createJListNode(data));
			}
			importerDetailTree.setModel(new DefaultTreeModel(root));
			// Viewer informieren
			observableSupportForFilter.markAndNotify(modelAll);
		} else if (arg1 instanceof String) {
			String id = (String)arg1;
			
			// Alle Knoten einklappen
			//expandAll(importerDetailTree, false);
			
			// Pfad für die zu expandierende Node heraussuchen
			DefaultMutableTreeNode root = ((DefaultMutableTreeNode)importerDetailTree.getModel().getRoot());
			DefaultMutableTreeNode toExpand = null;
			for (int i = 0; i < root.getChildCount(); i++) {
				if (root.getChildAt(i).toString().equals(id)) {
					toExpand = (DefaultMutableTreeNode)root.getChildAt(i);
					TreeNode[] path = toExpand.getPath();
					TreePath treePath = new TreePath(path);
					importerDetailTree.expandPath(treePath);
					importerDetailTree.setSelectionPath(treePath);
					importerDetailTree.scrollPathToVisible(treePath);
					break;
				}
			}
		}
	}

	private DefaultMutableTreeNode createJListNode(Data data) {
		AbstractProperty property;

		// Wurzel mit Namen des Data Objekts //XXX NullPointerException
		DefaultMutableTreeNode root = null;

		try {
			root = new DefaultMutableTreeNode(data.getId());

			// Position
			DefaultMutableTreeNode position = new DefaultMutableTreeNode(
					"Position");
			property = data.getPosition();
			for (AbstractProperty component : property
					.getValueAsContainerProperty().getComponents()) {
				position.add(extractProperty(component));
			}

			// Einfache Eigenschaften
			DefaultMutableTreeNode simpleProperties = new DefaultMutableTreeNode(
					"Eigenschaften");
			for (AbstractProperty component : data.getSimpleProperties()) {
				simpleProperties.add(extractProperty(component));
			}

			// Zusammengesetzte Eigenschaften
			DefaultMutableTreeNode containerProperties = new DefaultMutableTreeNode(
					"Vektoren");
			for (AbstractProperty component : data.getContainerProperties()) {
				DefaultMutableTreeNode vector = new DefaultMutableTreeNode(
						component.getId());
				for (AbstractProperty vectorComponent : component
						.getValueAsContainerProperty().getComponents()) {
					vector.add(extractProperty(vectorComponent));
				}
				containerProperties.add(vector);
			}

			// Taxonomien
			DefaultMutableTreeNode taxonimies = new DefaultMutableTreeNode(
					"Taxonomien");
			for (AbstractProperty component : data.getTaxonomies()) {
				taxonimies.add(extractProperty(component));
			}

			// Baum zusammenfügen
			root.add(position);
			root.add(simpleProperties);
			root.add(containerProperties);
			root.add(taxonimies);
		} catch (Exception e) {

		}
		return root;
	}
	

	/**
	 * Extrahiert eine Eigenschaft in Baumformat. Wurzel ist der Name der
	 * Eigenschaft. Blätter sind Typ und Wert mit der jeweiligen
	 * Stringrepräsentation
	 * 
	 * @param property
	 *            Zu bearbeitende Eigenschaft
	 * @return Wurzel des Extrahierten Baums
	 */
	private DefaultMutableTreeNode extractProperty(AbstractProperty property) {
		DefaultMutableTreeNode propertyId = new DefaultMutableTreeNode(property
				.getId());

//		// Typknoten mit Typ als Kind
//		DefaultMutableTreeNode type = new DefaultMutableTreeNode("Typ");
//		type.add(new DefaultMutableTreeNode(Type.toString(property.getType())));
//
//		// Wertknoten mit Wert als Kind
//		DefaultMutableTreeNode value = new DefaultMutableTreeNode("Wert");
//		value.add(new DefaultMutableTreeNode(property.getValueAsString()));
//
//		propertyId.add(type);
//		propertyId.add(value);
		propertyId.add(new DefaultMutableTreeNode(property.getValueAsString()));
		return propertyId;
	}
	
	public class ObservableSupport extends Observable {
		public ObservableSupport() {
		}
		public void markAndNotify(Object o){
			setChanged();
			notifyObservers(o);
		}
	}

	/**
	 * @return the importer
	 */
	public Importer getImporter() {
		return importer;
	}

}
