package de.unibw.fusionvis.datamodel.properties;

import java.util.GregorianCalendar;

import de.unibw.fusionvis.datamodel.Type;

/**
 * Klasse zur Speicherung von char-Eigenschaften
 * @author stzschoppe
 *
 */
public class CharProperty extends AbstractProperty {
	/**
	 * Wert der Eigenschaft als <code>char</code>
	 */
	private char value;
	
	/**
	 * Konstruktor
	 * @param id Bezeichnung der Eigenschaft
	 * @param value Wert der Eigenschaft
	 */
	public CharProperty(String id, char value) {
		super(id, Type.TChar);
		this.value = value;
	}

	@Override
	public boolean getValueAsBoolean() {
		throw new UnsupportedOperationException("Zugriff auf char als boolean-Wert");
	}

	@Override
	public char getValueAsChar() {
		return value;
	}

	@Override
	public ContainerProperty getValueAsContainerProperty() {
		throw new UnsupportedOperationException("Zugriff auf char als ContainerProperty");
	}

	@Override
	public GregorianCalendar getValueAsDate() {
		throw new UnsupportedOperationException("Zugriff auf char als Date");
	}

	@Override
	public int getValueAsInt() {
		throw new UnsupportedOperationException("Zugriff auf char als int-Wert");
	}

	@Override
	public String getValueAsString() {
		return String.valueOf(value);
	}

	@Override
	public void setValueFromBoolean(boolean value) {
		throw new UnsupportedOperationException("Setzen eines char-Wertes mit einem boolean-Wert");

	}

	@Override
	public void setValueFromChar(char value) {
		this.value = value;

	}

	@Override
	public void setValueFromDate(GregorianCalendar value) {
		throw new UnsupportedOperationException("Setzen eines char-Wertes mit einem Date");

	}

	@Override
	public void setValueFromInt(int value) {
		throw new UnsupportedOperationException("Setzen eines char-Wertes mit einem int-Wert");

	}

	@Override
	public void setValueFromString(String value) {
		try {
			this.value = value.charAt(0);
		} catch (Exception e) {
			throw new UnsupportedOperationException("Setzen eines char-Wertes mit einem ungültigen String");
		}

	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return id + ": type=" + type + ", value=" + value + "\n";
	}

	@Override
	public float getValueAsFloat() {
		throw new UnsupportedOperationException("Zugriff auf char als float-Wert");
	}

	@Override
	public void setValueFromFloat(float value) {
		throw new UnsupportedOperationException("Setzen eines char-Wertes mit einem float-Wert");
		
	}

}
