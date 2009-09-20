/**
 * 
 */
package de.unibw.fusionvis.common;

import java.util.HashMap;

/**
 * Datenstruktur zur Aufnahme von Eigenschaften, die der Importer ausliest. Wichtigste
 * Eigenschaft neben dem Bezeicher (<code>id</code>) ist ein 3D/4D-Vektor 
 * (<code>position</code>), der eine Art Lage des Datums angibt. <br />
 * Weiterhin k�nnen zus�tzliche Eigenschaften (Einfache Daten und Vektoren) in
 * Form von Listen gespeichert werden.
 * @author stzschoppe
 *
 */
public class Data { //TODO toString() implementieren.
	
	/** Bezeichner des Datums*/
	private String id;
	
	/** "Lage"-Vektor des Datums*/
	private VectorProperty position;
	
	/** Einfache Eigenschaften*/
	private HashMap<String, SimpleProperty> simpleProperties;
	
	/** Vektorielle Eigenschaften*/
	private HashMap<String, VectorProperty> vectorProperties;
	
	/** Taxonomien*/
	private HashMap<String, SimpleProperty> taxonomies;

	/**
	 * Konstruktor eines leeren Datums.
	 * @param id Bezeicher des Datums
	 */
	public Data(String id){
		this.id = id;
		simpleProperties = new HashMap<String, SimpleProperty>();
		vectorProperties = new HashMap<String, VectorProperty>();
		taxonomies = new HashMap<String, SimpleProperty>();
	}

	/**
	 * Gibt die "Lage" des Datums wieder.
	 * @return Vektor mit Lageinformationen.
	 */
	public VectorProperty getPosition() {
		return position;
	}

	/**
	 * Setzt die "Lage" des Datums.
	 * @param position Die zu setzende Lage
	 */
	public void setPosition(VectorProperty position) {
		this.position = position;
	}

	/**
	 * Liefert den Bezeichner des Datums.
	 * @return Den Bezeicher
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * F�gt eine einfache Eigenschaft hinzu
	 * @param property Hinzuzuf�gende Eigenschaft
	 */
	public void addSimpleProperty(SimpleProperty property) {
		simpleProperties.put(property.getId(), property);
	}
	
	/**
	 *F�gt eine vektorielle Eigenschaft hinzu
	 * @param property Hinzuzuf�gende Eigenschaft
	 */
	public void addVectorProperty(VectorProperty property){
		vectorProperties.put(property.getId(), property);
	}
	
	/**
	 * F�gt eine Taxonomie hinzu
	 * @param taxonomy Hinzuzuf�gende Taxonomie
	 */
	public void addTaxonomie(SimpleProperty taxonomy) {
		taxonomies.put(taxonomy.getId(), taxonomy);
	}
}
