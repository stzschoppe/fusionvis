package de.unibw.fusionvis.implementation.battlesimvis;

import java.io.ObjectInputStream.GetField;
import java.util.logging.Level;

import de.unibw.fusionvis.FusionVis;
import de.unibw.fusionvis.importer.Importer;
import de.unibw.fusionvis.mapper.Mapper;
import de.unibw.fusionvis.viewer.Viewer;

import shell.Shell;

public class BattleSimVis extends FusionVis {
	public BattleSimVis(Importer importer, Mapper mapper, Viewer viewer) {
		super(importer, mapper, viewer);
	}

	/**
	 * Main-Methode
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		BattleSimVis battleSimVis = new BattleSimVis(new BattleSimImporter("", getLogger()), null, null);
		try {
			// Logger Konfiguration
			getLogger().setLevel(Level.ALL);
			getLogger().log(Level.INFO, "starte FusionVis..\n");	
			
			// Shell starten
			Shell.getInstance().run();
			
			// Beenden
			getLogger().log(Level.INFO, "beende FusionVis");
		} catch (Exception e) {
			getLogger().log(Level.SEVERE, e.getMessage());
			getLogger().log(Level.INFO, "beende FusionVis");
			throw e;
		}
	}
}
