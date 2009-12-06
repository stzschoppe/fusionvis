package de.unibw.battlesimvis;

import java.util.logging.Level;

import de.unibw.battlesimvis.backend.BattleSimImporter;
import de.unibw.fusionvis.FusionVisForm;
import de.unibw.fusionvis.backend.Importer;
import de.unibw.fusionvis.frontend.ImporterPanel;
import de.unibw.fusionvis.frontend.ViewerPanel;

/**
 * Form des BattleSimulation Visualisierung
 * @author stzschoppe
 *
 */
public class BattleSimVisForm extends FusionVisForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8764514617523001648L;

	/**
	 * Konstruktor
	 * @param importerPanel ImporterPanel
	 * @param viewerPanel ImporterPanel
	 */
	public BattleSimVisForm(ImporterPanel importerPanel,
			ViewerPanel viewerPanel) {
		super(importerPanel, viewerPanel);
		setTitle("BattleSimVis");
	}

	/**
	 * Hauptprogramm
	 * @param args
	 * @throws Exception
	 */
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
