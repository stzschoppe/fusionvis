package de.unibw.fusionvis.common.properties;

import java.util.GregorianCalendar;

import de.unibw.fusionvis.common.Type;
/**
 * Klasse zur Speicherung von int-Eigenschaften
 * @author stzschoppe
 *
 */
public class IntProperty extends AbstractProperty {
	/**
	 * Wert der Eigenschaft als <code>int</code>
	 */
	private int value;

	/**
	 * Konstruktor
	 * @param id Bezeichnung der Eigenschaft
	 * @param value Wert der Eigenschaft
	 */
	public IntProperty(String id, int value) {
		super(id, Type.TInt);
		this.value = value;
	}

	@Override
	public boolean getValueAsBoolean() {
		throw new UnsupportedOperationException("Zugriff auf int als boolean-Wert");
	}

	@Override
	public char getValueAsChar() {
		throw new UnsupportedOperationException("Zugriff auf int als char-Wert");
	}

	@Override
	public GregorianCalendar getValueAsDate() {
		throw new UnsupportedOperationException("Zugriff auf int als Date");
	}

	@Override
	public int getValueAsInt() {
		return value;
	}

	@Override
	public String getValueAsString() {
		return Integer.toString(value);
	}

	@Override
	public void setValueFromBoolean(boolean value) {
		throw new UnsupportedOperationException("Setzen eines int-Wertes mit einem boolean-Wert");

	}

	@Override
	public void setValueFromChar(char value) {
		this.value = value;

	}

	@Override
	public void setValueFromDate(GregorianCalendar value) {
		throw new UnsupportedOperationException("Setzen eines int-Wertes mit einem Date");

	}

	@Override
	public void setValueFromInt(int value) {
		this.value = value;

	}

	@Override
	public void setValueFromString(String value) {
		try {
			this.value = Integer.parseInt(value);
		} catch (Exception e) {
			throw new UnsupportedOperationException("Setzen eines int-Wertes mit einem ungültigen String");
		}

	}

	@Override
	public ContainerProperty getValueAsContainerProperty() {
		throw new UnsupportedOperationException("Zugriff auf int als ContainerProperty");
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
		throw new UnsupportedOperationException("Zugriff auf int als float-Wert");
	}

	@Override
	public void setValueFromFloat(float value) {
		throw new UnsupportedOperationException("Setzen eines int-Wertes mit einem float-Wert");
	}
}
