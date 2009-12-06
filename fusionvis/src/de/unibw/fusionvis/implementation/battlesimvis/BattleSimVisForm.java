package de.unibw.fusionvis.implementation.battlesimvis;

import java.util.logging.Level;
import java.util.logging.Logger;

import de.unibw.fusionvis.FusionVisForm;
import de.unibw.fusionvis.importer.Importer;
import de.unibw.fusionvis.importer.ImporterPanel;
import de.unibw.fusionvis.viewer.ViewerPanel;

public class BattleSimVisForm extends FusionVisForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8764514617523001648L;

	public BattleSimVisForm(ImporterPanel importerPanel,
			ViewerPanel viewerPanel) {
		super(importerPanel, viewerPanel);
		setTitle("BattleSimVis");
	}

	public static void main(String[] args) throws Exception {
		final Importer importer = new BattleSimImporter();
		final ImporterPanel importerPanel = new ImporterPanel(importer);
		
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// Logger Konfiguration
					logger.setLevel(Level.ALL);
					logger.log(Level.INFO, "starte BattleVis..\n");

					new BattleSimVisForm(importerPanel, new ViewerPanel(importerPanel, 2f))
							.setVisible(true);

					// Beenden
					// logger.log(Level.INFO, "beende BattleVis");
				} catch (Exception e) {
					logger.log(Level.SEVERE, e.getMessage());
					logger.log(Level.INFO, "beende BattleVis");
					logger.log(Level.SEVERE, e.getLocalizedMessage());
				}

				try {

				} catch (Exception e) {
					logger.log(Level.SEVERE,
							e.getLocalizedMessage());
				}
			}
		});
	}
}
