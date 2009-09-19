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
public class DataSet {
	private String id;
	
	private VectorProperty position;
	
	private HashMap<String, SimpleProperty> simpleProperties;
	
	private HashMap<String, VectorProperty> vectorProperties;
	
	private HashMap<String, String> taxonomies;

}
