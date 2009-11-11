package de.unibw.fusionvis;
import java.util.logging.Level;
import java.util.logging.Logger;

import shell.Command;
import shell.CommandHandler;
import shell.Shell;
import shell.StringParameter;
import shell.tools.HaltCommand;
import de.unibw.fusionvis.importer.Importer;
import de.unibw.fusionvis.mapper.Mapper;
import de.unibw.fusionvis.viewer.ViewerPanel;


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
	public Importer importer;
	
	public FusionVis(Importer importer) {
		this.importer = importer;
		
		// Initialieren der Shell
		createCommands();
	}
	
	/**
	 * Getter fuer den globalen Logger
	 * @return the logger
	 */
	public static Logger getLogger() {
		return logger;
	}

	/**
     * Erstellt die commands für die shell. Ausgelagert aus dem Konstruktor auf Gruenden der Uebersicht.
     * Weiterfuehrende Erlaeuterungen zu finden in der shell/package.html.
     */
    private void createCommands(){ 	
    	//Erstellt ein Kommando ohne Parameter
    	Shell.getInstance().addCommand(new Command("printAll", "Gibt alle eingelesenen Datensätze aus", new CommandHandler(){
    		
			@Override
			public void execute(Command command) {
				commandPrintAll();
			}}));
    	
    	
    	//Erstellt ein Kommando zum Beenden von FusionVis
    	Shell.getInstance().addCommand(new HaltCommand("exit", "beendet FusionVis"));
    	
    	
    	//Erstellt ein Kommando zum Importieren von XML-Dateien
    	Command importXML = (new Command("import", "Läd eine angegebene xml-Datei.", new CommandHandler(){
    		
			@Override
			public void execute(Command command) {
				commandImportXML(command.getParameterByName("file").getValueAsString());				
			}}));
    	//Fügt dem Kommando einen Parameter hinzu
    	importXML.addParameter(new StringParameter("file", "Dateiname der zu importierenden XML-Datei."));	
    	Shell.getInstance().addCommand(importXML);
    	
    	
    	//Erstellt ein Kommando zum Importieren von XML-Dateien
    	Command filter = (new Command("filter", "Filtert den eingelesenen Datensatz", new CommandHandler(){
    		
			@Override
			public void execute(Command command) {
				commandFilter(command.getParameterByName("key").getValueAsString(), command.getParameterByName("value").getValueAsString());				
			}}));
    	//Fügt dem Kommando zwei Parameter hinzu
    	filter.addParameter(new StringParameter("key", "Schlüssel, nach dessen Wert zu filtern ist"));
    	filter.addParameter(new StringParameter("value", "Wert, nachdem gefiltert werden soll"));
    	Shell.getInstance().addCommand(filter);
    }

    public void commandFilter(String key, String value) {
		importer.setDataSet(importer.getDataSet().filterBy(key, value));
		logger.log(Level.INFO, ""
				+ this.importer.getDataSet().getData().size()
				+ " Datensätze vorhanden.\n");
	}
	
	public void commandImportXML(String file) {
		// Initialisierung des Importers
		logger.log(Level.INFO, "Initialisierung des Importers mit " + file + "\n");
		importer.runImport(file);
		logger.log(Level.INFO, "\n"
				+ this.importer.getDataSet().getData().size()
				+ " Datensätze importiert.\n");
	}
	
	/**
	 * Gibt alle Datensätze über den Logger aus
	 */
	public void commandPrintAll() {
		logger.log(Level.INFO, this.importer.getDataSet() + "\n");
		logger.log(Level.INFO, ""
				+ this.importer.getDataSet().getData().size()
				+ " Datensätze vorhanden.\n");
	}
}
