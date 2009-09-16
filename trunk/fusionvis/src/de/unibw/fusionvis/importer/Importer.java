/**
 * 
 */
package de.unibw.fusionvis.importer;

import java.io.IOException;
import java.util.logging.Level;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import de.unibw.fusionvis.FusionVis;

/**
 * Baut einen DOM-Baum aus einem XML-Datensatz und wandelt diesen in 
 * eine Collection von einzelnen Dateneinträgen um.
 * @author stzschoppe
 */
public class Importer {
	/**Ort der XML-Datei, aus der die Informationen zu lesen sind */
	private final String xmlDataLocation;
	
	/**DOM Struktur des Datensatzes*/
	private Document document;
	
	public Importer(String xmlDataLocation){
		this.xmlDataLocation = xmlDataLocation;
		
		// Einlesen des Datensatzes
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
			 builder = factory.newDocumentBuilder();
			 document = builder.parse(xmlDataLocation);
		
		} catch (ParserConfigurationException e) {
			FusionVis.getLogger().log(Level.SEVERE, "Fehler beim Initialisieren des Importers " + e);
		} catch (SAXException e) {
			FusionVis.getLogger().log(Level.SEVERE, "Fehler beim Initialisieren des Importers " + e);
		} catch (IOException e) {
			FusionVis.getLogger().log(Level.SEVERE, "Fehler beim Initialisieren des Importers " + e);
		}
	}
	
	/**
	 * Ort der XML-Datei, aus der die Informationen zu lesen sind
	 * @return xmlDataLocation
	 */
	public String getXmlDataLocation() {
		return xmlDataLocation;
	}

	/**
	 * DOM Struktur des Datensatzes
	 * @return document
	 */
	public Document getDocument() {
		return document;
	}
	


}
