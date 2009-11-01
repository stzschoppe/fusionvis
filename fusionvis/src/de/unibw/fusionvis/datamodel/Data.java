/**
 * 
 */
package de.unibw.fusionvis.datamodel;

import java.util.HashMap;
import java.util.Iterator;

import de.unibw.fusionvis.datamodel.properties.AbstractProperty;
import de.unibw.fusionvis.datamodel.properties.ContainerProperty;


/**
 * Datenstruktur zur Aufnahme von Eigenschaften, die der Importer ausliest. Wichtigste
 * Eigenschaft neben dem Bezeicher (<code>id</code>) ist ein 3D/4D-Vektor 
 * (<code>position</code>), der eine Art Lage des Datums angibt. <br />
 * Weiterhin können zusätzliche Eigenschaften (Einfache Daten und Vektoren) in
 * Form von Listen gespeichert werden.
 * @author stzschoppe
 *
 */
public class Data {
	
	/** Bezeichner des Datums*/
	private String id;
	
	/** "Lage"-Vektor des Datums*/
	private ContainerProperty position;
	
	/** Einfache Eigenschaften*/
	private HashMap<String, AbstractProperty> simpleProperties;
	
	/** Vektorielle Eigenschaften*/
	private HashMap<String, ContainerProperty> vectorProperties;
	
	/** Taxonomien*/
	private HashMap<String, AbstractProperty> taxonomies;

	/**
	 * Konstruktor eines leeren Datums.
	 * @param id Bezeicher des Datums
	 */
	public Data(String id){
		this.id = id;
		simpleProperties = new HashMap<String, AbstractProperty>();
		vectorProperties = new HashMap<String, ContainerProperty>();
		taxonomies = new HashMap<String, AbstractProperty>();
	}

	/**
	 * Gibt die "Lage" des Datums wieder.
	 * @return Vektor mit Lageinformationen.
	 */
	public ContainerProperty getPosition() {
		return position;
	}

	/**
	 * Setzt die "Lage" des Datums.
	 * @param position Die zu setzende Lage
	 */
	public void setPosition(ContainerProperty position) {
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
	 * Fügt eine einfache Eigenschaft hinzu
	 * @param property Hinzuzufügende Eigenschaft
	 */
	public void addAbstractProperty(AbstractProperty property) {
		simpleProperties.put(property.getId(), property);
	}
	
	/**
	 *Fügt eine vektorielle Eigenschaft hinzu
	 * @param property Hinzuzufügende Eigenschaft
	 */
	public void addContainerProperty(ContainerProperty property){
		vectorProperties.put(property.getId(), property);
	}
	
	/**
	 * Fügt eine Taxonomie hinzu
	 * @param taxonomy Hinzuzufügende Taxonomie
	 */
	public void addTaxonomie(AbstractProperty taxonomy) {
		taxonomies.put(taxonomy.getId(), taxonomy);
	}
	
	/**
	 * Liefert eine einfache Eigenschaft unter Angabe ihres Bezeichners,
	 * oder <code>null</code> falls der Bezeichner ungültig ist.
	 * @param id Bezeicher der Eigenschaft
	 * @return Die Eigenschaft
	 */
	public AbstractProperty getAbstractProperty(String id) {
		return simpleProperties.get(id);
	}
	
	/**
	 * Liefert eine vektorielle Eigenschaft unter Angabe ihres Bezeichners,
	 * oder <code>null</code> falls der Bezeichner ungültig ist.
	 * @param id Bezeicher der Eigenschaft
	 * @return Die Eigenschaft
	 */
	public ContainerProperty getContainerProperty(String id) {
		return vectorProperties.get(id);
	}
	
	/**
	 * Liefert eine Taxonomie unter Angabe ihres Bezeichners,
	 * oder <code>null</code> falls der Bezeichner ungültig ist.
	 * @param id Bezeicher der Taxonomie
	 * @return Die Taxonomie
	 */
	public AbstractProperty getTaxonomy(String id) {
		return taxonomies.get(id);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String result = "\n";
		
		result += id + "\n";
		result += "===========\n\n";
		
		result += position + "\n";
		result += "-----------\n\n";
		
		result += "Einfache Eigenschaften:\n";
		for (Iterator<AbstractProperty> iterator = simpleProperties.values().iterator(); iterator.hasNext();) {
			AbstractProperty component =  iterator.next();
			result += "->" + component.toString();
		}
		result += "-----------\n\n";
		
		result += "Vektorielle Eigenschaften:\n";
		for (Iterator<ContainerProperty> iterator = vectorProperties.values().iterator(); iterator.hasNext();) {
			ContainerProperty component =  iterator.next();
			result += "->" + component.toString() + "\n";
		}
		result += "-----------\n\n";
		
		result += "Taxonomien:\n";
		for (Iterator<AbstractProperty> iterator = taxonomies.values().iterator(); iterator.hasNext();) {
			AbstractProperty component =  iterator.next();
			result += "->" + component.toString() + "\n";
		}
		result += "===========\n";
		result += "===========\n\n";
		
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((position == null) ? 0 : position.hashCode());
		result = prime
				* result
				+ ((simpleProperties == null) ? 0 : simpleProperties.hashCode());
		result = prime * result
				+ ((taxonomies == null) ? 0 : taxonomies.hashCode());
		result = prime
				* result
				+ ((vectorProperties == null) ? 0 : vectorProperties.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Data))
			return false;
		Data other = (Data) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (simpleProperties == null) {
			if (other.simpleProperties != null)
				return false;
		} else if (!simpleProperties.equals(other.simpleProperties))
			return false;
		if (taxonomies == null) {
			if (other.taxonomies != null)
				return false;
		} else if (!taxonomies.equals(other.taxonomies))
			return false;
		if (vectorProperties == null) {
			if (other.vectorProperties != null)
				return false;
		} else if (!vectorProperties.equals(other.vectorProperties))
			return false;
		return true;
	}
	
	
}
