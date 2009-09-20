/**
 * 
 */
package de.unibw.fusionvis.importer;

import de.unibw.fusionvis.common.DataSet;

/**
 * <p>Baut einen DOM-Baum aus einem XML-Datensatz und wandelt diesen in 
 * eine Collection von einzelnen Dateneinträgen um.</p>
 * @author stzschoppe
 */
public abstract class Importer {
	/**Ort der XML-Datei, aus der die Informationen zu lesen sind */
	protected final String xmlDataLocation;
	
	/**Datensatz*/
	protected DataSet dataSet = null;
	
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
	 * DOM Struktur des Datensatzes. Ist verantwortlich die Instanzvariable 
	 * <code>dataSet</code> zu initialisieren.
	 * @return Satz von Daten
	 */
	public abstract DataSet getDataSet();
	


}
