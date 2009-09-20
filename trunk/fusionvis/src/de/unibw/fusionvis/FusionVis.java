package de.unibw.fusionvis;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.unibw.fusionvis.common.DataSet;
import de.unibw.fusionvis.importer.BattleSimImporter;
import de.unibw.fusionvis.importer.Importer;


/**
 * 
 * Visualization of data fusion models based on jME 2.0 engine
 * (main class) 
 * @author stzschoppe
 */
public class FusionVis {
	/** globaler Logger*/
	private static Logger logger = Logger.getLogger("FusionVis");
	
	/** Importer zum Erstellen der Datenstruktur*/
	private Importer importer;
	
	
	/**
	 * Getter fuer den globalen Logger
	 * @return the logger
	 */
	public static Logger getLogger() {
		return logger;
	}

	/**
	 * Main-Methode
	 * @param args
	 */
	public static void main(String[] args) {
		FusionVis fusionVis = new FusionVis();
		try {
			// Logger Konfiguration
			logger.setLevel(Level.ALL);
			logger.log(Level.INFO, "starte FusionVis..\n");

			// Initialisierung des Importers
			logger.log(Level.INFO, "Initialisierung des Importers" + "\n");
			fusionVis.importer = new BattleSimImporter("\\res\\testdaten.xml"); 
			
			DataSet dataSet = fusionVis.importer.getDataSet().filterBy("Certainty", "Perceived");
			dataSet = dataSet.filterBy("HostilityCode", "HO");
			logger.log(Level.INFO, "\n"
					+ fusionVis.importer.getDataSet().getData().size()
					+ " Datensätze Importiert.\n");
			logger.log(Level.INFO, dataSet + "\n");
			logger.log(Level.INFO, ""
					+ dataSet.getData().size()
					+ " Datensätze gefiltert.\n");
			
			// Beenden
			logger.log(Level.INFO, "beende FusionVis");
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
			logger.log(Level.INFO, "beende FusionVis");
		}
	}
}
