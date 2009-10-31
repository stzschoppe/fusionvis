package de.unibw.fusionvis.common.properties;

import java.util.GregorianCalendar;

import de.unibw.fusionvis.common.Type;
/**
 * Klasse zur Speicherung von float-Eigenschaften
 * @author stzschoppe
 *
 */
public class FloatProperty extends AbstractProperty {
	/**
	 * Wert der Eigenschaft als <code>float</code>
	 */
	private float value;

	/**
	 * Konstruktor
	 * @param id Bezeichnung der Eigenschaft
	 * @param value Wert der Eigenschaft
	 */
	public FloatProperty(String id, float value) {
		super(id, Type.TInt);
		this.value = value;
	}

	@Override
	public boolean getValueAsBoolean() {
		throw new UnsupportedOperationException("Zugriff auf float als boolean-Wert");
	}

	@Override
	public char getValueAsChar() {
		throw new UnsupportedOperationException("Zugriff auf float als char-Wert");
	}

	@Override
	public GregorianCalendar getValueAsDate() {
		throw new UnsupportedOperationException("Zugriff auf float als Date");
	}

	@Override
	public int getValueAsInt() {
		return (int)value;
	}

	@Override
	public String getValueAsString() {
		return Float.toString(value);
	}

	@Override
	public void setValueFromBoolean(boolean value) {
		throw new UnsupportedOperationException("Setzen eines float-Wertes mit einem boolean-Wert");

	}

	@Override
	public void setValueFromChar(char value) {
		this.value = value;

	}

	@Override
	public void setValueFromDate(GregorianCalendar value) {
		throw new UnsupportedOperationException("Setzen eines float-Wertes mit einem Date");

	}

	@Override
	public void setValueFromInt(int value) {
		this.value = value;

	}

	@Override
	public void setValueFromString(String value) {
		try {
			this.value = Float.parseFloat(value);
		} catch (Exception e) {
			throw new UnsupportedOperationException("Setzen eines float-Wertes mit einem ungültigen String");
		}

	}

	@Override
	public ContainerProperty getValueAsContainerProperty() {
		throw new UnsupportedOperationException("Zugriff auf float als ContainerProperty");
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
		return value;
	}

	@Override
	public void setValueFromFloat(float value) {
		this.value = value;
		
	}
}
