package de.unibw.fusionvis.common.properties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;

import de.unibw.fusionvis.common.SimpleProperty;
import de.unibw.fusionvis.common.Type;

/**
 * Klasse zur Speicherung von  Eigenschaften
 * @author stzschoppe
 *
 */
public class ContainerProperty extends AbstractProperty {

	private HashMap<String,AbstractProperty> value;

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
	public ContainerProperty getValueAsContainerProperty() { //XXX
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
	 * oder wirft eine <code>IllegalArgumentException</code> falls der Bezeichner ungültig ist.
	 * @param id Bezeicher der Komponente
	 * @return Die Komponente
	 */
	public AbstractProperty getComponent(String id){ //TODO Testen
		if (value.containsKey(id)) {
			return value.get(id);
		} else {
			throw new IllegalArgumentException("Keine Komponente mit diesem Namen vorhandem");
		}
	}
	
	/**
	 * Fügt eine Komponente hinzu. existiert sie bereits, wird sie überschrieben.
	 * @param component Die hinzuzufügende Eigenschaft.
	 */
	public void addComponent(AbstractProperty component) {
		value.put(component.getId(), component);
	}
	
	/**
	 * Entfernt die zum Bezeicher gehörende Komponente
	 * @param id Bezeichner der entfernenden Komponente
	 * @return die entfernte Komponente oder null, wenn sie nicht vorhanden war
	 */
	public AbstractProperty removeComponent(String id){
		return value.remove(id);
	}

}
