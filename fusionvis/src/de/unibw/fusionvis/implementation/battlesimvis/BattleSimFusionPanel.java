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

import java.util.Observable;
import java.util.Observer;

import de.unibw.fusionvis.viewer.ViewerPanel;

/**
 * Panel zum Einblenden von Vektoren und Bewegungskegel
 * @author stephan
 */
public class BattleSimFusionPanel extends javax.swing.JPanel implements Observer {

    /**
	 * 
	 */
	private static final long serialVersionUID = -406213533343117138L;
	/** Konstruktor 
	 * @param viewerPanel �bergeordnetes ViewerPanel
     * und ihre Eigenschaften speichert
     */
    public BattleSimFusionPanel(ViewerPanel viewerPanel) {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {

        fusionButtonGroup = new javax.swing.ButtonGroup();
        fusionPanel = new javax.swing.JPanel();
        fusionCamViewComboBox = new javax.swing.JComboBox();
        fuisonCamViewLabel = new javax.swing.JLabel();
        westPanel = new javax.swing.JPanel();
        fusionVectorCheckBox = new javax.swing.JCheckBox();
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

        fusionCamViewComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sicht 1", "Sicht 2", "Sicht 3" }));
        fusionCamViewComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                fusionCamViewComboBoxItemStateChanged(evt);
            }
        });
        fusionCamViewComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fusionCamViewComboBoxActionPerformed(evt);
            }
        });

        fuisonCamViewLabel.setText("Kamerasicht");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(fusionPanel);
        fusionPanel.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fusionCamViewComboBox, 0, 78, Short.MAX_VALUE)
                    .addComponent(fuisonCamViewLabel))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fuisonCamViewLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fusionCamViewComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        add(fusionPanel, java.awt.BorderLayout.EAST);

        fusionVectorCheckBox.setText("Vector");
        fusionVectorCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                fusionVectorCheckBoxItemStateChanged(evt);
            }
        });
        fusionVectorCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fusionVectorCheckBoxActionPerformed(evt);
            }
        });

        fusionOrientationCheckBox.setText("Orientation");
        fusionOrientationCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                fusionOrientationCheckBoxItemStateChanged(evt);
            }
        });

        fusionSeparator.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout westPanelLayout = new javax.swing.GroupLayout(westPanel);
        westPanel.setLayout(westPanelLayout);
        westPanelLayout.setHorizontalGroup(
            westPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, westPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(westPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fusionOrientationCheckBox)
                    .addComponent(fusionVectorCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fusionSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        westPanelLayout.setVerticalGroup(
            westPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fusionSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
            .addGroup(westPanelLayout.createSequentialGroup()
                .addComponent(fusionOrientationCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(fusionVectorCheckBox)
                .addContainerGap())
        );

        add(westPanel, java.awt.BorderLayout.WEST);

        centerPanel.setMinimumSize(new java.awt.Dimension(70, 50));
        centerPanel.setPreferredSize(new java.awt.Dimension(360, 79));

        fusionCandidateList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                fusionCandidateListValueChanged(evt);
            }
        });
        fusionCandidateScrollPane.setViewportView(fusionCandidateList);

        fusionHeightLabel.setLabelFor(fusionHeightTextField);
        fusionHeightLabel.setText("Höhe");

        fusionRadiusLabel.setLabelFor(fusionRadiusTextField);
        fusionRadiusLabel.setText("Radius");

        fusionHeightTextField.setText("100");
        fusionHeightTextField.addActionListener(new java.awt.event.ActionListener() {
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
        fusionRadiusTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fusionRadiusTextFieldActionPerformed(evt);
            }
        });
        fusionRadiusTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                fusionRadiusTextFieldKeyTyped(evt);
            }
        });

        fusionButtonGroup.add(fusionFutureRadioButton);
        fusionFutureRadioButton.setText("zukünftig");
        fusionFutureRadioButton.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                fusionFutureRadioButtonItemStateChanged(evt);
            }
        });

        fusionButtonGroup.add(fusionPastRadioButton);
        fusionPastRadioButton.setText("vergangen");
        fusionPastRadioButton.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                fusionPastRadioButtonItemStateChanged(evt);
            }
        });

        fusionButtonGroup.add(fusionInvisibleRadioButton);
        fusionInvisibleRadioButton.setSelected(true);
        fusionInvisibleRadioButton.setText("ausgeblendet");
        fusionInvisibleRadioButton.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                fusionInvisibleRadioButtonItemStateChanged(evt);
            }
        });

        fusionSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout centerPanelLayout = new javax.swing.GroupLayout(centerPanel);
        centerPanel.setLayout(centerPanelLayout);
        centerPanelLayout.setHorizontalGroup(
            centerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, centerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(centerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(centerPanelLayout.createSequentialGroup()
                        .addComponent(fusionRadiusLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fusionRadiusTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(centerPanelLayout.createSequentialGroup()
                        .addComponent(fusionHeightLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fusionHeightTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(centerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fusionInvisibleRadioButton)
                    .addComponent(fusionFutureRadioButton)
                    .addComponent(fusionPastRadioButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(fusionCandidateScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fusionSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        centerPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {fusionHeightTextField, fusionRadiusTextField});

        centerPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {fusionFutureRadioButton, fusionInvisibleRadioButton, fusionPastRadioButton});

        centerPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {fusionHeightLabel, fusionRadiusLabel});

        centerPanelLayout.setVerticalGroup(
            centerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(centerPanelLayout.createSequentialGroup()
                .addGroup(centerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(centerPanelLayout.createSequentialGroup()
                        .addGroup(centerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(fusionHeightLabel)
                            .addComponent(fusionHeightTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(centerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(fusionRadiusLabel)
                            .addComponent(fusionRadiusTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(centerPanelLayout.createSequentialGroup()
                        .addComponent(fusionInvisibleRadioButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fusionFutureRadioButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fusionPastRadioButton)))
                .addContainerGap(13, Short.MAX_VALUE))
            .addComponent(fusionCandidateScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
            .addComponent(fusionSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
        );

        add(centerPanel, java.awt.BorderLayout.CENTER);

        northLabel.setMinimumSize(new java.awt.Dimension(5, 5));

        fusionIdLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        fusionIdLabel.setText("Id");

        javax.swing.GroupLayout northLabelLayout = new javax.swing.GroupLayout(northLabel);
        northLabel.setLayout(northLabelLayout);
        northLabelLayout.setHorizontalGroup(
            northLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(northLabelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(fusionIdLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(362, Short.MAX_VALUE))
            .addComponent(fusionSeparator3, javax.swing.GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE)
        );
        northLabelLayout.setVerticalGroup(
            northLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(northLabelLayout.createSequentialGroup()
                .addComponent(fusionIdLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fusionSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        add(northLabel, java.awt.BorderLayout.NORTH);
    }// </editor-fold>//GEN-END:initComponents

    private void fusionVectorCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fusionVectorCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fusionVectorCheckBoxActionPerformed

    private void fusionHeightTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fusionHeightTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fusionHeightTextFieldActionPerformed

    private void fusionRadiusTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fusionRadiusTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fusionRadiusTextFieldActionPerformed

    private void fusionCamViewComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fusionCamViewComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fusionCamViewComboBoxActionPerformed

    private void fusionOrientationCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_fusionOrientationCheckBoxItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_fusionOrientationCheckBoxItemStateChanged

    private void fusionVectorCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_fusionVectorCheckBoxItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_fusionVectorCheckBoxItemStateChanged

    private void fusionHeightTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fusionHeightTextFieldKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_fusionHeightTextFieldKeyTyped

    private void fusionRadiusTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fusionRadiusTextFieldKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_fusionRadiusTextFieldKeyTyped

    private void fusionInvisibleRadioButtonItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_fusionInvisibleRadioButtonItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_fusionInvisibleRadioButtonItemStateChanged

    private void fusionFutureRadioButtonItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_fusionFutureRadioButtonItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_fusionFutureRadioButtonItemStateChanged

    private void fusionPastRadioButtonItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_fusionPastRadioButtonItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_fusionPastRadioButtonItemStateChanged

    private void fusionCandidateListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_fusionCandidateListValueChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_fusionCandidateListValueChanged

    private void fusionCamViewComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_fusionCamViewComboBoxItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_fusionCamViewComboBoxItemStateChanged


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
    private javax.swing.JCheckBox fusionVectorCheckBox;
    private javax.swing.JPanel fusionPanel;
    private javax.swing.JPanel northLabel;
    private javax.swing.JPanel westPanel;
    // End of variables declaration//GEN-END:variables
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
