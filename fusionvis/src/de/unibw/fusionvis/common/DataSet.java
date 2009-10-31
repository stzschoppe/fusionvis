/**
 * 
 */
package de.unibw.fusionvis.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import de.unibw.fusionvis.common.properties.AbstractProperty;


/**
 * Datenstruktur zur Aufnahme von Daten, die der Importer ausliest. Wichtigste
 * Eigenschaft neben dem Bezeicher (<code>id</code>) ist eine Liste Daten.
 * @author stzschoppe
 */
public class DataSet {
	/** Liste von Daten */
	private ArrayList<Data> data;
	
	/** Einfache Eigenschaften*/
	private HashMap<String, AbstractProperty> simpleProperties;

	/** Bezeichner des Satzes von Daten*/
	private String id;
	
	/**
	 * Konstruktor für einen leeren Satz von Daten
	 * @param id Bezeicher des Datensatzes.
	 */
	public DataSet(String id) {
		this.id = id;
		this.simpleProperties = new HashMap<String, AbstractProperty>();
		this.data = new ArrayList<Data>();
	}
	
	/**
	 * Liefert den Bezeichner des Satzes von Daten.
	 * @return Den Bezeicher
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Fügt eine einfache Eigenschaft hinzu
	 * @param property Hinzuzufügende Eigenschaft
	 */
	public void addSimpleProperty(AbstractProperty property) {
		simpleProperties.put(property.getId(), property);
	}
	
	/**
	 * Liefert eine einfache Eigenschaft unter Angabe ihres Bezeichners,
	 * oder <code>null</code> falls der Bezeichner ungültig ist.
	 * @param id Bezeicher der Eigenschaft
	 * @return Die Eigenschaft
	 */
	public AbstractProperty getSimpleProperty(String id) {
		return simpleProperties.get(id);
	}
	
	/**
	 * Liefert die Liste der gespeicherten Daten.
	 * @return Datenliste
	 */
	public ArrayList<Data> getData() {
		return data;
	}
	
	/**
	 * Fügt einen neuen Datensatz ein.
	 * @param data Einzufügender Datensatz
	 */
	public void addData(Data data){
		this.data.add(data);
	}
	
	/**
	 * Liefert den Satz von Daten, der in den einfachen Eigenschaften das angegebene 
	 * id-value Paar enthält.
	 * @param id Bezeicher der zur filterung verwendeten Eigenschaft
	 * @param value Wert nachdem gefiltert wird
	 * @return Die reduzierte Datenstruktur
	 */
	@SuppressWarnings("unchecked")
	public DataSet filterBy(String id, String value){
		DataSet result = new DataSet(String.valueOf(this.id));
		result.simpleProperties = (HashMap<String, AbstractProperty>) this.simpleProperties.clone();
		for (Iterator<Data> iterator = data.iterator(); iterator.hasNext();) {
			Data tmpData =  iterator.next();
			if (tmpData.getAbstractProperty(id).getValueAsString().equals(value)) {
				result.data.add(tmpData);
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String result="";
		result += "DataSet id=" + id + "\n\ndata:\n";
		result += data;
//		for (Iterator<Data> iterator = data.iterator(); iterator.hasNext();) {
//			Data component =  iterator.next();
//			result += component.toString() + "\n";
//		}
		result += "\nEinfache Eigenschaften:\n";
		for (Iterator<AbstractProperty> iterator = simpleProperties.values().iterator(); iterator.hasNext();) {
			AbstractProperty component =  iterator.next();
			result += "->" + component.toString() + "\n";
		}
		return result;
	}
	
	
	
}
