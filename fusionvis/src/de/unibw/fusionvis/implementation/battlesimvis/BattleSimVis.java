package de.unibw.fusionvis.implementation.battlesimvis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;

import de.unibw.fusionvis.FusionVis;
import de.unibw.fusionvis.FusionVisForm;
import de.unibw.fusionvis.datamodel.Data;
import de.unibw.fusionvis.importer.Importer;
import de.unibw.fusionvis.mapper.Mapper;
import de.unibw.fusionvis.viewer.Viewer;

import shell.Command;
import shell.CommandHandler;
import shell.Parameter;
import shell.Shell;
import shell.StringParameter;

public class BattleSimVis extends FusionVis {
	private CommandHandler commandHandler = new CommandHandler() {
		
		@Override
		public void execute(Command command) {
			if (command.getName().equals("latlon")) {
				commandLatLon();
			}
			else if (command.getName().equals("print")) {
				commandPrintById(command.getParameterByName("id").getValueAsString());
			} 
		}
	};
	
	public BattleSimVis(Importer importer, Mapper mapper, Viewer viewer) {
		super(importer, mapper, viewer);
		
		// Erzeugen des "latlon" Commands
		Shell.getInstance().addCommand(new Command("latlon", "gibt den Wertebereich f�r Latitude und Longitude aus.", commandHandler));
		
		// Erzeugen des "print" Commands
		Parameter id = new StringParameter("id", "Bezeicher der Eigenschaft.");
		Command print = new Command("print", "Gibt eine Unit mit angegebenen Namen aus", commandHandler);
		print.addParameter(id);
		Shell.getInstance().addCommand(print);
	}

	/**
	 * Main-Methode
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		BattleSimVis battleSimVis = new BattleSimVis(new BattleSimImporter(getLogger()), null, null);
		try {
			// Logger Konfiguration
			getLogger().setLevel(Level.ALL);
			getLogger().log(Level.INFO, "starte FusionVis..\n");	
			
			// Shell starten
			Shell.getInstance().run();
			
//XXX	        java.awt.EventQueue.invokeLater(new Runnable() {
//	            public void run() {
//	                new FusionVisForm().setVisible(true);
//	            }
//	        });
			
			// Beenden
			getLogger().log(Level.INFO, "beende FusionVis");
		} catch (Exception e) {
			getLogger().log(Level.SEVERE, e.getMessage());
			getLogger().log(Level.INFO, "beende FusionVis");
			throw e;
		}
	}
	/**
	 * Bestimmung des Minimums/Maximums f�r Lat und Lon
	 */
	private void commandLatLon(){
		float latMin = 90f;
		float latMax =  -90f;
		float lonMin = 180f;
		float lonMax =  -180f;
		ArrayList<Data> dataSet = importer.getDataSet().getData();
		
		for (Iterator<Data> iterator = dataSet.iterator(); iterator.hasNext();) {
			Data data = iterator.next();	
			
			if (data.getPosition().getComponent("Lat").getValueAsFloat()<latMin) {
				latMin=data.getPosition().getComponent("Lat").getValueAsFloat();
			}
			else if (data.getPosition().getComponent("Lat").getValueAsFloat()>latMax) {
				latMax=data.getPosition().getComponent("Lat").getValueAsFloat();
			}
			if (data.getPosition().getComponent("Lon").getValueAsFloat()<lonMin) {
				lonMin=data.getPosition().getComponent("Lon").getValueAsFloat();
			}
			else if (data.getPosition().getComponent("Lon").getValueAsFloat()>lonMax) {
				lonMax=data.getPosition().getComponent("Lon").getValueAsFloat();
			}
			
		}
		getLogger().log(Level.INFO, "Latitude: ["+latMin+"|"+latMax+"]" + "\n");
		getLogger().log(Level.INFO, "Longitude: ["+lonMin+"|"+lonMax+"]" + "\n");
	}
	
	private void commandPrintById(String id){
		ArrayList<Data> dataSet = importer.getDataSet().getData();
		
		for (Iterator<Data> iterator = dataSet.iterator(); iterator.hasNext();) {
			Data data = iterator.next();	
			if (data.getId().equals(id)) {
				getLogger().log(Level.INFO, data.toString() + "\n");
				return;
			} 
		}
		getLogger().log(Level.INFO, "Keine Unit mit diesem Namen vorhanden." + "\n");
	}
}
