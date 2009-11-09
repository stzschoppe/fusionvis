/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FusionVisForm.java
 *
 * Created on 03.11.2009, 14:42:00
 */

package de.unibw.fusionvis;

import java.awt.Canvas;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.lwjgl.util.Dimension;

import com.jme.system.DisplaySystem;
import com.jme.system.canvas.JMECanvas;
import com.jme.util.GameTaskQueue;
import com.jme.util.GameTaskQueueManager;
import com.jmex.awt.lwjgl.LWJGLAWTCanvasConstructor;

import de.unibw.fusionvis.importer.ImporterPanel;
import de.unibw.fusionvis.viewer.ViewerPanel;

/**
 *
 * @author stzschoppe
 */
public class FusionVisForm extends javax.swing.JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5567019550663889694L;
	/** Creates new form FusionVisForm */
    public FusionVisForm(FusionVis model, ImporterPanel importerPanel, ViewerPanel viewerPanel) {
    	this.model = model;
    	initComponents(importerPanel, viewerPanel);
    	model.importer.addObserver(importerPanel);
    	model.importer.addObserver(viewerPanel);
    	logger = FusionVis.getLogger();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     * @param importerPanel 
     * @param viewerPanel 
     */
    private void initComponents(ImporterPanel importerPanel, ViewerPanel viewerPanel) {

        fusionvisFileChooser = new javax.swing.JFileChooser();
        if (importerPanel==null) {
			fusionvisImporterPanel = new ImporterPanel(model.importer);
		}
        else {
			fusionvisImporterPanel = importerPanel;
		}
        if (viewerPanel==null) {
			fusionvisViewerPanel = new ViewerPanel(FusionVis.getLogger(), model.importer);
		}
        else {
        	fusionvisViewerPanel = viewerPanel;
		}
        
		fusionvisMenu = new javax.swing.JMenuBar();
        fusionvisFileMenu = new javax.swing.JMenu();
        fusionvisImportMenuItem = new javax.swing.JMenuItem();
        fusionvisCloseMenuItem = new javax.swing.JMenuItem();
        fusionvisHelpMenu = new javax.swing.JMenu();
        fusionvisInfoMenuItem = new javax.swing.JMenuItem();

        fusionvisFileChooser.setApproveButtonText("Importieren");
        fusionvisFileChooser.setCurrentDirectory(new java.io.File("C:\\Users\\stephan\\workspace_bachelor\\FusionVis\\res"));
        fusionvisFileChooser.setDialogTitle("XML-Datensatz importieren");
        fusionvisFileChooser.setFileFilter(new FileNameExtensionFilter("XML-Dateien", "xml")

        );
        //fusionvisFileChooser.setSelectedFile(new java.io.File("C:\\Program Files\\NetBeans 6.7.1\\*.xml"));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMinimumSize(new java.awt.Dimension(800, 600));

        getContentPane().add(fusionvisImporterPanel, java.awt.BorderLayout.WEST);
        
        getContentPane().add(fusionvisViewerPanel, java.awt.BorderLayout.CENTER);
        fusionvisViewerPanel.setVisible(true);
        
        fusionvisFileMenu.setText("Datei");

        fusionvisImportMenuItem.setText("Datensatz importieren..");
        fusionvisImportMenuItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fusionvisImportMenuItemMouseClicked(evt);
            }
        });
        fusionvisImportMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fusionvisImportMenuItemActionPerformed(evt);
            }
        });
        fusionvisFileMenu.add(fusionvisImportMenuItem);

        fusionvisCloseMenuItem.setText("Beenden");
        fusionvisFileMenu.add(fusionvisCloseMenuItem);

        fusionvisMenu.add(fusionvisFileMenu);

        fusionvisHelpMenu.setText("Hilfe");

        fusionvisInfoMenuItem.setText("Info..");
        fusionvisInfoMenuItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fusionvisInfoMenuItemMouseClicked(evt);
            }
        });
        fusionvisHelpMenu.add(fusionvisInfoMenuItem);

        fusionvisMenu.add(fusionvisHelpMenu);

        setJMenuBar(fusionvisMenu);

        setTitle("FusionVis");
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void fusionvisImportMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fusionvisImportMenuItemActionPerformed
		int returnVal = fusionvisFileChooser.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			fusionvisImporterPanel.setVisible(true);
			model.commandImportXML(fusionvisFileChooser.getSelectedFile().getAbsolutePath());
    }

    }//GEN-LAST:event_fusionvisImportMenuItemActionPerformed

    private void fusionvisImportMenuItemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fusionvisImportMenuItemMouseClicked
        fusionvisImportMenuItemActionPerformed(null);
    }//GEN-LAST:event_fusionvisImportMenuItemMouseClicked

    private void fusionvisInfoMenuItemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fusionvisInfoMenuItemMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_fusionvisInfoMenuItemMouseClicked

//    /**
//    * @param args the command line arguments
//    */
//    public static void main(String args[]) throws Exception {
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new FusionVisForm(null, null).setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JMenuItem fusionvisCloseMenuItem;
    protected javax.swing.JFileChooser fusionvisFileChooser;
    protected javax.swing.JMenu fusionvisFileMenu;
    protected javax.swing.JMenu fusionvisHelpMenu;
    protected javax.swing.JMenuItem fusionvisImportMenuItem;
    protected ImporterPanel fusionvisImporterPanel;
    protected ViewerPanel fusionvisViewerPanel;
    protected javax.swing.JMenuItem fusionvisInfoMenuItem;
    protected javax.swing.JMenuBar fusionvisMenu;
    protected FusionVis model;
    protected static Logger logger;
    // End of variables declaration//GEN-END:variables


}
