/**
 * 
 */
package de.unibw.fusionvis.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


/**
 * Datenstruktur zur Aufnahme von Daten, die der Importer ausliest. Wichtigste
 * Eigenschaft neben dem Bezeicher (<code>id</code>) ist eine Liste Daten.
 * @author stzschoppe
 */
public class DataSet {
	/** Liste von Daten */
	private ArrayList<Data> data;
	
	/** Einfache Eigenschaften*/
	private HashMap<String, SimpleProperty> simpleProperties;

	/** Bezeichner des Satzes von Daten*/
	private String id;
	
	/**
	 * Konstruktor f�r einen leeren Satz von Daten
	 * @param id Bezeicher des Datensatzes.
	 */
	public DataSet(String id) {
		this.id = id;
		this.simpleProperties = new HashMap<String, SimpleProperty>();
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
	 * F�gt eine einfache Eigenschaft hinzu
	 * @param property Hinzuzuf�gende Eigenschaft
	 */
	public void addSimpleProperty(SimpleProperty property) {
		simpleProperties.put(property.getId(), property);
	}
	
	/**
	 * Liefert eine einfache Eigenschaft unter Angabe ihres Bezeichners,
	 * oder <code>null</code> falls der Bezeichner ung�ltig ist.
	 * @param id Bezeicher der Eigenschaft
	 * @return Die Eigenschaft
	 */
	public SimpleProperty getSimpleProperty(String id) {
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
	 * F�gt einen neuen Datensatz ein.
	 * @param data Einzuf�gender Datensatz
	 */
	public void addData(Data data){
		this.data.add(data);
	}
	
	/**
	 * Liefert den Satz von Daten, der in den einfachen Eigenschaften das angegebene 
	 * id-value Paar enth�lt.
	 * @param id Bezeicher der zur filterung verwendeten Eigenschaft
	 * @param value Wert nachdem gefiltert wird
	 * @return Die reduzierte Datenstruktur
	 */
	@SuppressWarnings("unchecked")
	public DataSet filterBy(String id, String value){
		DataSet result = new DataSet(String.valueOf(this.id));
		result.simpleProperties = (HashMap<String, SimpleProperty>) this.simpleProperties.clone();
		for (Iterator<Data> iterator = data.iterator(); iterator.hasNext();) {
			Data tmpData =  iterator.next();
			if (tmpData.getSimpleProperty(id).getStringValue().equals(value)) {
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
		for (Iterator<SimpleProperty> iterator = simpleProperties.values().iterator(); iterator.hasNext();) {
			SimpleProperty component =  iterator.next();
			result += "->" + component.toString() + "\n";
		}
		return result;
	}
	
	
	
}
