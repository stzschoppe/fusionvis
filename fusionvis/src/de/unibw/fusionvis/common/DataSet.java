/**
 * 
 */
package de.unibw.fusionvis.common;

import java.util.HashMap;

/**
 * Datenstruktur zur Aufnahme von Daten, die der Importer ausliest. Wichtigste
 * Eigenschaft neben dem Bezeicher (<code>id</code>) ist ein 3D/4D-Vektor 
 * (<code>position</code>), der eine Art Lage des Datums angibt. <br />
 * Weiterhin können zusätzliche Eigenschaften (Einfache Daten und Vektoren) in
 * Form von Listen gespeichert werden.
 * @author stzschoppe
 *
 */
public class DataSet { //TODO toString() implementieren.
	private String id;
	
	private VectorProperty position;
	
	private HashMap<String, SimpleProperty> simpleProperties;
	
	private HashMap<String, VectorProperty> vectorProperties;
	
	private HashMap<String, String> taxonomies;

	/**
	 * Konstruktor eines leeren Datensatzes.
	 * @param id Bezeicher des Datensatzes
	 */
	public DataSet(String id){
		this.id = id;
		simpleProperties = new HashMap<String, SimpleProperty>();
		vectorProperties = new HashMap<String, VectorProperty>();
		taxonomies = new HashMap<String, String>();
	}
}
