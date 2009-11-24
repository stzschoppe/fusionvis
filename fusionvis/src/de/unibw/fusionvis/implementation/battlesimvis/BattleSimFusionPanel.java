/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * BattleSimFusionPanel.java
 *
 * Created on 15.11.2009, 23:10:16
 */

package de.unibw.fusionvis.implementation.battlesimvis;

import java.awt.event.ItemEvent;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;

import com.jme.bounding.BoundingBox;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.scene.shape.Cylinder;
import com.jme.scene.state.BlendState;
import com.jme.scene.state.MaterialState;
import com.jme.system.DisplaySystem;

import de.unibw.fusionvis.datamodel.Data;
import de.unibw.fusionvis.datamodel.DataSet;
import de.unibw.fusionvis.viewer.ViewerPanel;

/**
 * Panel zum Einblenden von Vektoren und Bewegungskegel
 * 
 * @author stephan
 */
public class BattleSimFusionPanel extends javax.swing.JPanel implements
		Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -406213533343117138L;

	/**
	 * Konstruktor
	 * 
	 * @param viewerPanel
	 *            �bergeordnetes ViewerPanel und ihre Eigenschaften speichert
	 */
	public BattleSimFusionPanel(ViewerPanel viewerPanel) {
		this.viewerPanel = viewerPanel;
		maxConeHeight = ((BattleSimMapper) viewerPanel.mapper).getTimeSpan()
				/ viewerPanel.getMaximalDimenVector3f().y;
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents() {

		fusionButtonGroup = new javax.swing.ButtonGroup();
		fusionPanel = new javax.swing.JPanel();
		fusionCamViewComboBox = new javax.swing.JComboBox();
		fuisonCamViewLabel = new javax.swing.JLabel();
		westPanel = new javax.swing.JPanel();
		fusionVelocityCheckBox = new javax.swing.JCheckBox();
		fusionOrientationCheckBox = new javax.swing.JCheckBox();
		fusionSeparator = new javax.swing.JSeparator();
		centerPanel = new javax.swing.JPanel();
		fusionCandidateScrollPane = new javax.swing.JScrollPane();
		fusionCandidateList = new javax.swing.JList();
		fusionHeightLabel = new javax.swing.JLabel();
		fusionRadiusLabel = new javax.swing.JLabel();
		fusionHeightTextField = new javax.swing.JTextField();
		fusionRadiusTextField = new javax.swing.JTextField();
		fusionFutureRadioButton = new javax.swing.JRadioButton();
		fusionPastRadioButton = new javax.swing.JRadioButton();
		fusionInvisibleRadioButton = new javax.swing.JRadioButton();
		fusionSeparator2 = new javax.swing.JSeparator();
		northLabel = new javax.swing.JPanel();
		fusionIdLabel = new javax.swing.JLabel();
		fusionSeparator3 = new javax.swing.JSeparator();

		setMinimumSize(new java.awt.Dimension(400, 60));
		setLayout(new java.awt.BorderLayout());

		fusionCamViewComboBox.setModel(new javax.swing.DefaultComboBoxModel(
				new String[] { "Sicht 1", "Sicht 2", "Sicht 3" }));
		fusionCamViewComboBox
				.addItemListener(new java.awt.event.ItemListener() {
					public void itemStateChanged(java.awt.event.ItemEvent evt) {
						fusionCamViewComboBoxItemStateChanged(evt);
					}
				});
		fusionCamViewComboBox
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						fusionCamViewComboBoxActionPerformed(evt);
					}
				});

		fuisonCamViewLabel.setText("Kamerasicht");

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(
				fusionPanel);
		fusionPanel.setLayout(jPanel1Layout);
		jPanel1Layout
				.setHorizontalGroup(jPanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																fusionCamViewComboBox,
																0, 78,
																Short.MAX_VALUE)
														.addComponent(
																fuisonCamViewLabel))
										.addContainerGap()));
		jPanel1Layout
				.setVerticalGroup(jPanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(fuisonCamViewLabel)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												fusionCamViewComboBox,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap(31, Short.MAX_VALUE)));

		add(fusionPanel, java.awt.BorderLayout.EAST);

		fusionVelocityCheckBox.setText("Vector");
		fusionVelocityCheckBox
				.addItemListener(new java.awt.event.ItemListener() {
					public void itemStateChanged(java.awt.event.ItemEvent evt) {
						fusionVectorCheckBoxItemStateChanged(evt);
					}
				});
		fusionVelocityCheckBox
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						fusionVectorCheckBoxActionPerformed(evt);
					}
				});

		fusionOrientationCheckBox.setText("Orientation");
		fusionOrientationCheckBox
				.addItemListener(new java.awt.event.ItemListener() {
					public void itemStateChanged(java.awt.event.ItemEvent evt) {
						fusionOrientationCheckBoxItemStateChanged(evt);
					}
				});

		fusionSeparator.setOrientation(javax.swing.SwingConstants.VERTICAL);

		javax.swing.GroupLayout westPanelLayout = new javax.swing.GroupLayout(
				westPanel);
		westPanel.setLayout(westPanelLayout);
		westPanelLayout
				.setHorizontalGroup(westPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								westPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												westPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																fusionOrientationCheckBox)
														.addComponent(
																fusionVelocityCheckBox,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																79,
																Short.MAX_VALUE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												fusionSeparator,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)));
		westPanelLayout
				.setVerticalGroup(westPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(fusionSeparator,
								javax.swing.GroupLayout.DEFAULT_SIZE, 82,
								Short.MAX_VALUE)
						.addGroup(
								westPanelLayout
										.createSequentialGroup()
										.addComponent(fusionOrientationCheckBox)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(fusionVelocityCheckBox)
										.addContainerGap()));

		add(westPanel, java.awt.BorderLayout.WEST);
		westPanel.setVisible(false);

		centerPanel.setMinimumSize(new java.awt.Dimension(70, 50));
		centerPanel.setPreferredSize(new java.awt.Dimension(360, 79));

		fusionCandidateList
				.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
					public void valueChanged(
							javax.swing.event.ListSelectionEvent evt) {
						fusionCandidateListValueChanged(evt);
					}
				});
		fusionCandidateScrollPane.setViewportView(fusionCandidateList);

		fusionHeightLabel.setLabelFor(fusionHeightTextField);
		fusionHeightLabel.setText("H�he");

		fusionRadiusLabel.setLabelFor(fusionRadiusTextField);
		fusionRadiusLabel.setText("Radius");

		fusionHeightTextField.setText("100");
		fusionHeightTextField
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						fusionHeightTextFieldActionPerformed(evt);
					}
				});
		fusionHeightTextField.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent evt) {
				fusionHeightTextFieldKeyTyped(evt);
			}
		});

		fusionRadiusTextField.setText("50");
		fusionRadiusTextField
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						fusionRadiusTextFieldActionPerformed(evt);
					}
				});

		fusionButtonGroup.add(fusionFutureRadioButton);
		fusionFutureRadioButton.setText("zuk�nftig");
		fusionFutureRadioButton
				.addItemListener(new java.awt.event.ItemListener() {
					public void itemStateChanged(java.awt.event.ItemEvent evt) {
						fusionFutureRadioButtonItemStateChanged(evt);
					}
				});

		fusionButtonGroup.add(fusionPastRadioButton);
		fusionPastRadioButton.setText("vergangen");
		fusionPastRadioButton
				.addItemListener(new java.awt.event.ItemListener() {
					public void itemStateChanged(java.awt.event.ItemEvent evt) {
						fusionPastRadioButtonItemStateChanged(evt);
					}
				});

		fusionButtonGroup.add(fusionInvisibleRadioButton);
		fusionInvisibleRadioButton.setSelected(true);
		fusionInvisibleRadioButton.setText("ausgeblendet");
		fusionInvisibleRadioButton
				.addItemListener(new java.awt.event.ItemListener() {
					public void itemStateChanged(java.awt.event.ItemEvent evt) {
						fusionInvisibleRadioButtonItemStateChanged(evt);
					}
				});

		fusionSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

		javax.swing.GroupLayout centerPanelLayout = new javax.swing.GroupLayout(
				centerPanel);
		centerPanel.setLayout(centerPanelLayout);
		centerPanelLayout
				.setHorizontalGroup(centerPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								centerPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												centerPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																centerPanelLayout
																		.createSequentialGroup()
																		.addComponent(
																				fusionRadiusLabel)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				fusionRadiusTextField,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				56,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGroup(
																centerPanelLayout
																		.createSequentialGroup()
																		.addComponent(
																				fusionHeightLabel)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				fusionHeightTextField,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				51,
																				javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addGap(18, 18, 18)
										.addGroup(
												centerPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																fusionInvisibleRadioButton)
														.addComponent(
																fusionFutureRadioButton)
														.addComponent(
																fusionPastRadioButton))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(
												fusionCandidateScrollPane,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												96, Short.MAX_VALUE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												fusionSeparator2,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)));

		centerPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL,
				new java.awt.Component[] { fusionHeightTextField,
						fusionRadiusTextField });

		centerPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL,
				new java.awt.Component[] { fusionFutureRadioButton,
						fusionInvisibleRadioButton, fusionPastRadioButton });

		centerPanelLayout
				.linkSize(javax.swing.SwingConstants.HORIZONTAL,
						new java.awt.Component[] { fusionHeightLabel,
								fusionRadiusLabel });

		centerPanelLayout
				.setVerticalGroup(centerPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								centerPanelLayout
										.createSequentialGroup()
										.addGroup(
												centerPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																centerPanelLayout
																		.createSequentialGroup()
																		.addGroup(
																				centerPanelLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								fusionHeightLabel)
																						.addComponent(
																								fusionHeightTextField,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																		.addGroup(
																				centerPanelLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								fusionRadiusLabel)
																						.addComponent(
																								fusionRadiusTextField,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.PREFERRED_SIZE)))
														.addGroup(
																centerPanelLayout
																		.createSequentialGroup()
																		.addComponent(
																				fusionInvisibleRadioButton)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				fusionFutureRadioButton)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				fusionPastRadioButton)))
										.addContainerGap(13, Short.MAX_VALUE))
						.addComponent(fusionCandidateScrollPane,
								javax.swing.GroupLayout.DEFAULT_SIZE, 82,
								Short.MAX_VALUE).addComponent(fusionSeparator2,
								javax.swing.GroupLayout.DEFAULT_SIZE, 82,
								Short.MAX_VALUE));

		add(centerPanel, java.awt.BorderLayout.CENTER);

		northLabel.setMinimumSize(new java.awt.Dimension(5, 5));

		fusionIdLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
		fusionIdLabel.setText("Id");

		javax.swing.GroupLayout northLabelLayout = new javax.swing.GroupLayout(
				northLabel);
		northLabel.setLayout(northLabelLayout);
		northLabelLayout.setHorizontalGroup(northLabelLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						northLabelLayout.createSequentialGroup().addGap(10, 10,
								10).addComponent(fusionIdLabel,
								javax.swing.GroupLayout.PREFERRED_SIZE, 136,
								javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(362, Short.MAX_VALUE))
				.addComponent(fusionSeparator3,
						javax.swing.GroupLayout.DEFAULT_SIZE, 508,
						Short.MAX_VALUE));
		northLabelLayout
				.setVerticalGroup(northLabelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								northLabelLayout
										.createSequentialGroup()
										.addComponent(
												fusionIdLabel,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												29,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												fusionSeparator3,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												2,
												javax.swing.GroupLayout.PREFERRED_SIZE)));

		add(northLabel, java.awt.BorderLayout.NORTH);
	}// </editor-fold>//GEN-END:initComponents

	private void fusionVectorCheckBoxActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_fusionVectorCheckBoxActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_fusionVectorCheckBoxActionPerformed

	private void fusionHeightTextFieldActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_fusionHeightTextFieldActionPerformed
	// TODO
	}// GEN-LAST:event_fusionHeightTextFieldActionPerformed

	private void fusionRadiusTextFieldActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_fusionRadiusTextFieldActionPerformed
	// TODO
	}// GEN-LAST:event_fusionRadiusTextFieldActionPerformed

	private void fusionCamViewComboBoxActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_fusionCamViewComboBoxActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_fusionCamViewComboBoxActionPerformed

	private void fusionOrientationCheckBoxItemStateChanged(
			java.awt.event.ItemEvent evt) {// GEN-FIRST:event_fusionOrientationCheckBoxItemStateChanged
		// TODO add your handling code here:
	}// GEN-LAST:event_fusionOrientationCheckBoxItemStateChanged

	private void fusionVectorCheckBoxItemStateChanged(
			java.awt.event.ItemEvent evt) {// GEN-FIRST:event_fusionVectorCheckBoxItemStateChanged
		// TODO add your handling code here:
	}// GEN-LAST:event_fusionVectorCheckBoxItemStateChanged

	private void fusionHeightTextFieldKeyTyped(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_fusionHeightTextFieldKeyTyped
		float tempHeight = maxConeHeight;
		try {
			maxConeHeight = Float.parseFloat(fusionHeightTextField.getText());
			if (!fusionInvisibleRadioButton.isSelected()) {
				if (fusionFutureRadioButton.isSelected()) {
					updateFutureCone();
				} else if (fusionPastRadioButton.isSelected()) {
					updatePastCone();
				}
			}
		} catch (Exception e) {
			maxConeHeight = tempHeight;
		}
	}// GEN-LAST:event_fusionHeightTextFieldKeyTyped

	private void fusionInvisibleRadioButtonItemStateChanged(
			java.awt.event.ItemEvent evt) {// GEN-FIRST:event_fusionInvisibleRadioButtonItemStateChanged
		if (viewerPanel.selectionId != null) {
			Node node = (Node) viewerPanel.dataNode
					.getChild(viewerPanel.selectionId);
			Node futureConeNode = (Node) (node.getChild("futureConeNode"));
			Node pastConeNode = (Node) (node.getChild("pastConeNode"));
			futureConeNode.detachAllChildren();
			pastConeNode.detachAllChildren();
			pastCones.remove(viewerPanel.selectionId);
			futureCones.remove(viewerPanel.selectionId);
		}
	}// GEN-LAST:event_fusionInvisibleRadioButtonItemStateChanged

	private void fusionFutureRadioButtonItemStateChanged(
			java.awt.event.ItemEvent evt) {// GEN-FIRST:event_fusionFutureRadioButtonItemStateChanged
		updateFutureCone();
	}// GEN-LAST:event_fusionFutureRadioButtonItemStateChanged

	private void updateFutureCone() {
		maxConeHeight = ((BattleSimMapper) viewerPanel.mapper).getTimeSpan()
				/ viewerPanel.getMaximalDimenVector3f().y;
		if (viewerPanel.selectionId != null) {
			Node node = (Node) viewerPanel.dataNode
					.getChild(viewerPanel.selectionId);
			Node futureConeNode = (Node) (node.getChild("futureConeNode"));
			Node pastConeNode = (Node) (node.getChild("pastConeNode"));
			futureConeNode.detachAllChildren();
			pastConeNode.detachAllChildren();
			pastCones.remove(viewerPanel.selectionId);
			futureCones.add(viewerPanel.selectionId);
			futureConeNode.attachChild(createCone(true));
		}
	}

	private void fusionPastRadioButtonItemStateChanged(
			java.awt.event.ItemEvent evt) {// GEN-FIRST:event_fusionPastRadioButtonItemStateChanged
		updatePastCone();
	}// GEN-LAST:event_fusionPastRadioButtonItemStateChanged

	private void updatePastCone() {
		maxConeHeight = ((BattleSimMapper) viewerPanel.mapper).getTimeSpan()
				/ viewerPanel.getMaximalDimenVector3f().y;
		if (viewerPanel.selectionId != null) {
			Node node = (Node) viewerPanel.dataNode
					.getChild(viewerPanel.selectionId);
			Node futureConeNode = (Node) (node.getChild("futureConeNode"));
			Node pastConeNode = (Node) (node.getChild("pastConeNode"));
			futureConeNode.detachAllChildren();
			pastConeNode.detachAllChildren();

			futureCones.remove(viewerPanel.selectionId);
			pastCones.add(viewerPanel.selectionId);

			pastConeNode.attachChild(createCone(false));
		}
	}

	private void fusionCandidateListValueChanged(
			javax.swing.event.ListSelectionEvent evt) {// GEN-FIRST:event_fusionCandidateListValueChanged
		// TODO add your handling code here:
	}// GEN-LAST:event_fusionCandidateListValueChanged

	private void fusionCamViewComboBoxItemStateChanged(
			java.awt.event.ItemEvent evt) {// GEN-FIRST:event_fusionCamViewComboBoxItemStateChanged
		switch (fusionCamViewComboBox.getSelectedIndex()) {
		case 0:
			viewerPanel.cam.lookAt(new Vector3f(1, -1, 1).normalizeLocal()
					.multLocal(1000), Vector3f.UNIT_Y);
			viewerPanel.cam.getLocation().y = 20;
			viewerPanel.cam.getLocation().x = -15;
			viewerPanel.cam.getLocation().z = -15;
			viewerPanel.cam.update();
			break;

		case 1:
			viewerPanel.cam.getLocation().y = 50;
			viewerPanel.cam.getLocation().x = 18;
			viewerPanel.cam.getLocation().z = 18;
			viewerPanel.cam.lookAt(new Vector3f(0, -1, 0).normalizeLocal()
					.multLocal(1000), Vector3f.UNIT_Y);
			viewerPanel.cam.update();
			break;
		case 2:
			viewerPanel.cam.getLocation().y = 20;
			viewerPanel.cam.getLocation().x = 45;
			viewerPanel.cam.getLocation().z = 45;
			viewerPanel.cam.lookAt(new Vector3f(-1, -1, -1).normalizeLocal()
					.multLocal(1000), Vector3f.UNIT_Y);
			viewerPanel.cam.update();
			break;

		default:
			break;
		}
	}// GEN-LAST:event_fusionCamViewComboBoxItemStateChanged

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JPanel centerPanel;
	private javax.swing.JLabel fuisonCamViewLabel;
	private javax.swing.ButtonGroup fusionButtonGroup;
	private javax.swing.JComboBox fusionCamViewComboBox;
	private javax.swing.JList fusionCandidateList;
	private javax.swing.JScrollPane fusionCandidateScrollPane;
	private javax.swing.JRadioButton fusionFutureRadioButton;
	private javax.swing.JLabel fusionHeightLabel;
	private javax.swing.JTextField fusionHeightTextField;
	private javax.swing.JLabel fusionIdLabel;
	private javax.swing.JRadioButton fusionInvisibleRadioButton;
	private javax.swing.JCheckBox fusionOrientationCheckBox;
	private javax.swing.JRadioButton fusionPastRadioButton;
	private javax.swing.JLabel fusionRadiusLabel;
	private javax.swing.JTextField fusionRadiusTextField;
	private javax.swing.JSeparator fusionSeparator;
	private javax.swing.JSeparator fusionSeparator2;
	private javax.swing.JSeparator fusionSeparator3;
	private javax.swing.JCheckBox fusionVelocityCheckBox;
	private javax.swing.JPanel fusionPanel;
	private javax.swing.JPanel northLabel;
	private javax.swing.JPanel westPanel;
	// End of variables declaration//GEN-END:variables

	private ViewerPanel viewerPanel;
	private float maxConeHeight;

	private HashSet<String> futureCones = new HashSet<String>();
	private HashSet<String> pastCones = new HashSet<String>();

	@Override
	public void update(Observable o, Object arg) {

		if (arg instanceof DataSet) { // Datenimport
			fusionIdLabel.setText("Id");
			fusionVelocityCheckBox.setSelected(false);
			fusionOrientationCheckBox.setSelected(false);
			fusionInvisibleRadioButton.setSelected(true);
			fusionHeightTextField.setText("100");
			fusionRadiusTextField.setText("50");
			fusionCandidateList.setModel(new DefaultListModel());
		} else if (arg instanceof String) {
			String id = (String) arg;
			fusionIdLabel.setText(id);
			Node node = (Node) viewerPanel.dataNode.getChild(id);
			Node velocityNode = (Node) (node.getChild("velocityNode"));

			Node orientationNode = (Node) (node.getChild("orientationNode"));
			Node futureConeNode = (Node) (node.getChild("futureConeNode"));
			Node pastConeNode = (Node) (node.getChild("pastConeNode"));

			fusionVelocityCheckBox.setSelected(velocityNode.getChildren()
					.size() != 0);
			fusionOrientationCheckBox.setSelected(orientationNode.getChildren()
					.size() != 0);

			if (futureCones.contains(id))
				fusionFutureRadioButton.setSelected(true);
			else if (pastCones.contains(id))
				fusionPastRadioButton.setSelected(true);
			else
				fusionInvisibleRadioButton.setSelected(true);

			// TODO fusionCandidateList bef�llen

			fusionHeightTextField.setText(Float.toString(maxConeHeight)); // TODO
																			// aus
																			// den
																			// Werten
			// auslesen
			fusionCandidateList.setModel(new DefaultListModel());
		} else {

		}
	}

	/**
	 * Erzeugt einen Kegel von angegebener H�he und Radius.
	 * 
	 * @param intoFuture
	 *            Gibt an, ob der Kegel nach oben (<code>true</code>) oder nach
	 *            unten (<code>false</code>) ge�ffnet ist.
	 * @return
	 */
	private Spatial createCone(boolean intoFuture) {
		String id = viewerPanel.selectionId;
		float velocity = 0;

		// Geschwindigkeit in km/h auslesen
		Data data = viewerPanel.importerPanel.modelAll.getDataById(id);
		if (data != null) {
			velocity = data.getContainerProperty("Velocity")
					.getValueAsContainerProperty().getComponent("XVelocity")
					.getValueAsFloat();
		}

		final BlendState alphaState = DisplaySystem.getDisplaySystem()
				.getRenderer().createBlendState();
		alphaState.setBlendEnabled(true);
		alphaState.setSourceFunction(BlendState.SourceFunction.SourceAlpha);
		alphaState
				.setDestinationFunction(BlendState.DestinationFunction.OneMinusSourceAlpha);
		alphaState.setTestEnabled(true);
		alphaState.setTestFunction(BlendState.TestFunction.GreaterThan);
		alphaState.setEnabled(true);

		// the sphere material that will be modified to make the sphere
		// look opaque then transparent then opaque and so on
		MaterialState materialState = DisplaySystem.getDisplaySystem()
				.getRenderer().createMaterialState();
		float opacityAmount = 0.2f;
		materialState
				.setAmbient(new ColorRGBA(0.0f, 0.0f, 0.0f, opacityAmount));
		materialState
				.setDiffuse(new ColorRGBA(0.1f, 0.5f, 0.8f, opacityAmount));
		materialState
				.setSpecular(new ColorRGBA(1.0f, 1.0f, 1.0f, opacityAmount));
		materialState.setShininess(128.0f);
		materialState
				.setEmissive(new ColorRGBA(0.0f, 0.0f, 0.0f, opacityAmount));
		materialState.setEnabled(true);

		// IMPORTANT: this is used to handle the internal sphere faces when
		// setting them to transparent, try commenting this line to see what
		// happens
		materialState.setMaterialFace(MaterialState.MaterialFace.FrontAndBack);

		Cylinder cone = new Cylinder("cone " + viewerPanel.selectionId, 50, 50,
				2, -4, false);

		Spatial spatial = ((Node) viewerPanel.dataNode.getChild(id))
				.getChild(id);

		float coneHeigth;
		if (intoFuture) {
			coneHeigth = maxConeHeight - spatial.getLocalTranslation().y;
		} else {
			coneHeigth = maxConeHeight
					- (maxConeHeight - spatial.getLocalTranslation().y);
		}
		float coneRadius = (float) (coneHeigth
				* viewerPanel.getMaximalDimenVector3f().y * velocity / 3.6)
				/ viewerPanel.getMaximalDimenVector3f().x;
		cone.updateGeometry(50, 50, 0, coneRadius, coneHeigth, false, false);

		// Drehung des Kegels berechnen
		float[] rot1 = { (float) Math.PI / 2, (float) Math.PI / 2, 0 };
		float[] rot2 = { 3 * (float) Math.PI / 2, (float) Math.PI / 2, 0 };
		if (intoFuture) {
			cone.setLocalRotation(new Quaternion(rot1));
			cone.setLocalTranslation(spatial.getLocalTranslation().x, spatial
					.getLocalTranslation().y
					+ coneHeigth / 2, spatial.getLocalTranslation().z);
		} else {
			cone.setLocalRotation(new Quaternion(rot2));
			cone.setLocalTranslation(spatial.getLocalTranslation().x, spatial
					.getLocalTranslation().y
					- coneHeigth / 2, spatial.getLocalTranslation().z);

		}

		cone.setRenderState(materialState);
		cone.updateRenderState();

		cone.setRenderState(alphaState);
		cone.updateRenderState();
		cone.setRenderQueueMode(Renderer.QUEUE_TRANSPARENT);

		cone.setModelBound(new BoundingBox());
		cone.updateModelBound();

		return cone;
	}

}
