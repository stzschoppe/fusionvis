package de.unibw.fusionvis;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.unibw.fusionvis.importer.Importer;

/**
 * 
 */

/**
 * 
 * Visualization of data fusion models based on jME 2.0 engine
 * (main class) 
 * @author stzschoppe
 */
public class FusionVis {
	/** globaler Logger*/
	private static Logger logger = Logger.getLogger("FusionVis");
	private Importer importer;
	
	
	/**
	 * Getter fuer den globalen Logger
	 * @return the logger
	 */
	public static Logger getLogger() {
		return logger;
	}

	public static void main(String[] args) {
	    FusionVis fusionVis = new FusionVis();
		try {
	      // Logger Konfiguration 
	      logger.setLevel(Level.ALL);
	      logger.log(Level.INFO, "starte FusionVis..\n");
	      
	      // Initialisierung des Importers
	      logger.log(Level.INFO, "Initialisierung des Importers\n");
	      fusionVis.importer = new Importer("\\res\\testdaten.xml"); //TODO hook für die GUI
	      
	      
	      logger.log(Level.INFO, "beende FusionVis");
	    } catch (SecurityException e) {
	      e.printStackTrace();
	  
	  }
	}
}
