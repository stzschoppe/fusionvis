package de.unibw.fusionvis.implementation.battlesimvis;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.jme.math.Vector3f;

import de.unibw.fusionvis.FusionVis;
import de.unibw.fusionvis.FusionVisForm;
import de.unibw.fusionvis.importer.ImporterPanel;
import de.unibw.fusionvis.mapper.Mapper;
import de.unibw.fusionvis.viewer.ViewerPanel;

public class BattleSimVisForm extends FusionVisForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8764514617523001648L;

	public BattleSimVisForm(FusionVis model, ImporterPanel importerPanel,
			ViewerPanel viewerPanel) {
		super(model, importerPanel, viewerPanel);
		setTitle("BattleSimVis");
	}

	public static void main(String[] args) throws Exception {
		final FusionVis model = new BattleSimVis(new BattleSimImporter());
		final ImporterPanel importerPanel = new ImporterPanel(model.importer);
		
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				Logger logger = BattleSimVis.getLogger();
				try {
					// Logger Konfiguration
					logger.setLevel(Level.ALL);
					logger.log(Level.INFO, "starte BattleVis..\n");

					new BattleSimVisForm(model, importerPanel, new ViewerPanel(importerPanel, 2f))
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
					BattleSimVis.getLogger().log(Level.SEVERE,
							e.getLocalizedMessage());
				}
			}
		});
	}
}
