/**
 * 
 */
package de.unibw.fusionvis.importer;

import org.w3c.dom.Document;

/**
 * <p>Baut einen DOM-Baum aus einem XML-Datensatz und wandelt diesen in 
 * eine Collection von einzelnen Dateneinträgen um.</p>
 * @author stzschoppe
 */
public abstract class Importer {
	/**Ort der XML-Datei, aus der die Informationen zu lesen sind */
	protected final String xmlDataLocation;
	
	/**DOM Struktur des Datensatzes*/
	protected Document document = null;
	
	/**
	 * Konstruktor eines Importers unter Angabe der zu importierenden XML-Datei
	 * @param xmlDataLocation Pfad zur XML-Datei
	 */
	public Importer(String xmlDataLocation){
		this.xmlDataLocation = xmlDataLocation;
	}
	
	/**
	 * <p>Ort der XML-Datei, aus der die Informationen zu lesen sind</p>
	 * @return xmlDataLocation
	 */
	public String getXmlDataLocation() {
		return xmlDataLocation;
	}

	/**
	 * DOM Struktur des Datensatzes
	 * @return document
	 */
	public abstract Document getDocument();
	


}
