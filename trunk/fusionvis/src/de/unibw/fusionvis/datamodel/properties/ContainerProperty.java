package de.unibw.fusionvis.datamodel.properties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;

import de.unibw.fusionvis.datamodel.Type;

/**
 * Klasse zur Speicherung von  Eigenschaften
 * @author stzschoppe
 *
 */
public class ContainerProperty extends AbstractProperty {
	/**
	 * Inhalt des Containers als <code>HashMap&lt;String,AbstractProperty></code>
	 */
	private HashMap<String,AbstractProperty> value;

	/**
	 * Konstruktor
	 * @param id Bezeichnung der Eigenschaft
	 */
	public ContainerProperty(String id) {
		super(id, Type.TContainer);
		this.value = new HashMap<String, AbstractProperty>();
	}
	
	/**
	 * Konstruktor
	 * @param id Bezeichnung der Eigenschaft
	 * @param value Collection von Eigenschaften
	 */
	public ContainerProperty(String id, Collection<AbstractProperty> value) {
		super(id, Type.TContainer);
		this.value = new HashMap<String, AbstractProperty>();
		for (Iterator<AbstractProperty> iterator = value.iterator(); iterator.hasNext();) {
			AbstractProperty component =  iterator.next();
			this.value.put(component.getId(), component);
		}
	}

	@Override
	public boolean getValueAsBoolean() {
		throw new UnsupportedOperationException("Zugriff auf ContainerProperty als boolean-Wert");
	}

	@Override
	public char getValueAsChar() {
		throw new UnsupportedOperationException("Zugriff auf ContainerProperty als char-Wert");
	}

	@Override
	public ContainerProperty getValueAsContainerProperty() {
		return this;
	}

	@Override
	public GregorianCalendar getValueAsDate() {
		throw new UnsupportedOperationException("Zugriff auf ContainerProperty als Date");
	}

	@Override
	public int getValueAsInt() {
		throw new UnsupportedOperationException("Zugriff auf ContainerProperty als int-Wert");
	}

	@Override
	public String getValueAsString() {
		throw new UnsupportedOperationException("Zugriff auf ContainerProperty als String");
	}

	@Override
	public void setValueFromBoolean(boolean value) {
		throw new UnsupportedOperationException("Setzen einer ContainerProperty mit einem boolean-Wert");
	}

	@Override
	public void setValueFromChar(char value) {
		throw new UnsupportedOperationException("Setzen einer ContainerProperty mit einem char-Wert");

	}

	@Override
	public void setValueFromDate(GregorianCalendar value) {
		throw new UnsupportedOperationException("Setzen einer ContainerProperty mit einem Date");
	}

	@Override
	public void setValueFromInt(int value) {
		throw new UnsupportedOperationException("Setzen einer ContainerProperty mit einem int-Wert");

	}

	@Override
	public void setValueFromString(String value) {
		throw new UnsupportedOperationException("Setzen einer ContainerProperty mit einem String");
	}

	@Override
	public float getValueAsFloat() {
		throw new UnsupportedOperationException("Zugriff auf ContainerProperty als float-Wert");

	}

	@Override
	public void setValueFromFloat(float value) {
		throw new UnsupportedOperationException("Setzen einer ContainerProperty mit einem float-Wert");		
	}
	
	/**
	 * @param components Die zu setzenden Komponenten
	 */
	public void setComponents(HashMap<String,AbstractProperty> components){
		value = components;
	}
	
	/**
	 * @return Die Komponenten als <code>ArrayList</code>
	 * 
	 */
	public ArrayList<AbstractProperty> getComponents() {
		return new ArrayList<AbstractProperty>( value.values());
	}
	
	/**
	 * Liefert eine spezielle Komponente des Containers unter Angabe ihres Bezeichners,
	 * oder wirft eine <code>IllegalArgumentException</code> falls der Bezeichner ung�ltig ist.
	 * @param id Bezeicher der Komponente
	 * @return Die Komponente
	 */
	public AbstractProperty getComponent(String id){
		if (value.containsKey(id)) {
			return value.get(id);
		} else {
			throw new IllegalArgumentException("Keine Komponente mit diesem Namen vorhandem");
		}
	}
	
	/**
	 * F�gt eine Komponente hinzu. existiert sie bereits, wird sie �berschrieben.
	 * @param component Die hinzuzuf�gende Eigenschaft.
	 */
	public void addComponent(AbstractProperty component) {
		value.put(component.getId(), component);
	}
	
	/**
	 * Entfernt die zum Bezeicher geh�rende Komponente
	 * @param id Bezeichner der entfernenden Komponente
	 * @return die entfernte Komponente oder null, wenn sie nicht vorhanden war
	 */
	public AbstractProperty removeComponent(String id){
		return value.remove(id);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String result = id + ":\n";
		for (Iterator<AbstractProperty> iterator = value.values().iterator(); iterator.hasNext();) {
			AbstractProperty component =  iterator.next();
			result += "-->" + component.toString();
		}
		return result;
	}

}