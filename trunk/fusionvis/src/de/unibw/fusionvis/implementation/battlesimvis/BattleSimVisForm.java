package de.unibw.fusionvis.implementation.battlesimvis;

import java.util.logging.Level;
import java.util.logging.Logger;

import de.unibw.fusionvis.FusionVis;
import de.unibw.fusionvis.FusionVisForm;
import de.unibw.fusionvis.importer.ImporterPanel;

public class BattleSimVisForm extends FusionVisForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8764514617523001648L;

	public BattleSimVisForm(FusionVis model, ImporterPanel importerPanel) {
		super(model, importerPanel);
	}

	public static void main(String[] args) throws Exception {
		final FusionVis model = new BattleSimVis(
				new BattleSimImporter(BattleSimVis.getLogger()),
				null, null);
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				Logger logger = BattleSimVis.getLogger();
				try {
					// Logger Konfiguration
					logger.setLevel(Level.ALL);
					logger.log(Level.INFO, "starte BattleVis..\n");	
					
					// Shell starten
					// Shell.getInstance().run();
					new BattleSimVisForm(model, new ImporterPanel(model.importer)).setVisible(true);
					
					// Beenden
					// logger.log(Level.INFO, "beende BattleVis");
				} catch (Exception e) {
					logger.log(Level.SEVERE, e.getMessage());
					logger.log(Level.INFO, "beende BattleVis");
					logger.log(Level.SEVERE,
							e.getLocalizedMessage());
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
