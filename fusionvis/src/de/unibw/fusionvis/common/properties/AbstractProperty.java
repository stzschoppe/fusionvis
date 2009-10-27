/**
 * 
 */
package de.unibw.fusionvis.common.properties;

import java.util.GregorianCalendar;

import de.unibw.fusionvis.common.Type;

/**
 * <p>
 * Abstrakte Eigenschaft, speichert eine Eigenschaft mit ihrer ID, ihrem Typ und bietet
 * eine Schnittstelle für das Setzen und Lesen der gespeicherten Werte
 * </p>
 * 
 * Die Art und Weise der Wertspeicherung legen die erbenden Klassen fest.
 * @author stzschoppe
 *
 */
public abstract class AbstractProperty {
	/**
	 * Bezeichnung der Eigenschaft
	 */
	final private String id;
	
	/**
	 * Typ der Eigenschaft.
	 * 
	 * @see de.unibw.fusionvis.common.Type
	 */
	final private Type type;
	
	/**
	 * Konstruktor
	 * @param id Bezeichnung der Eigenschaft
	 * @param type Typ der Eigenschaft (Enumeration)
	 */
	public AbstractProperty(String id, Type type) {
		this.id = id;
		this.type = type;
	}
	
	/**
	 * @return Typ der Eigenschaft
	 */
	public Type getType() {
		return type;
	}
	
	/**
	 * @return Bezeichner der Eigenschaft
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * @return Wert als <code>int</code>
	 */
	public abstract int getValueAsInt();
	
	/**
	 * @return Wert als <code>char</code>
	 */
	public abstract char getValueAsChar();
	
	/**
	 * @return Wert als <code>boolean</code>
	 */
	public abstract boolean getValueAsBoolean();
	
	/**
	 * @return Wert als <code>String</code>
	 */
	public abstract String getValueAsString();
	
	/**
	 * @return Wert als <code>GregorianCalendar</code>
	 */
	public abstract GregorianCalendar getValueAsDate();
	
	/**
	 * @return Wert als <code>ContainerProperty</code>
	 */
	public abstract ContainerProperty getValueAsContainerProperty();
	
	/**
	 * @param value neuer Wert
	 */
	public abstract void setValueFromInt(int value);
	
	/**
	 * @param value neuer Wert
	 */
	public abstract void setValueFromChar(char  value);
	
	/**
	 * @param value neuer Wert
	 */
	public abstract void setValueFromBoolean(boolean  value);
	
	/**
	 * @param value neuer Wert
	 */
	public abstract void setValueFromString(String  value);
	
	/**
	 * @param value neuer Wert
	 */
	public abstract void setValueFromDate(GregorianCalendar  value);
	
	/**
	 * @param value neuer Wert
	 */
	public abstract void setValueFromContainerProperty(ContainerProperty  value);
}
