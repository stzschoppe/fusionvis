package de.unibw.fusionvis.datamodel.properties;

import java.util.GregorianCalendar;

import de.unibw.fusionvis.datamodel.Type;

/**
 * Klasse zur Speicherung von String Eigenschaften
 * @author stzschoppe
 *
 */
public class StringProperty extends AbstractProperty{
	/**
	 * Wert der Eigenschaft als <code>String</code>
	 */
	private String value;

	/**
	 * Konstruktor
	 * @param id Bezeichnung der Eigenschaft
	 * @param value Wert der Eigenschaft
	 */
	public StringProperty(String id, String value) {
		super(id, Type.TString);
		this.value = value;
	}

	@Override
	public boolean getValueAsBoolean() {
		throw new UnsupportedOperationException("Zugriff auf String als boolean-Wert");
	}

	@Override
	public char getValueAsChar() {
		throw new UnsupportedOperationException("Zugriff auf String als char-Wert");
	}

	@Override
	public ContainerProperty getValueAsContainerProperty() {
		throw new UnsupportedOperationException("Zugriff auf String als ContainerProperty");
	}

	@Override
	public GregorianCalendar getValueAsDate() {
		throw new UnsupportedOperationException("Zugriff auf String als Date");
	}

	@Override
	public int getValueAsInt() {
		throw new UnsupportedOperationException("Zugriff auf String als int-Wert");
	}

	@Override
	public String getValueAsString() {
		return value;
	}

	@Override
	public void setValueFromBoolean(boolean value) {
		this.value = String.valueOf(value);
		
	}

	@Override
	public void setValueFromChar(char value) {
		this.value = String.valueOf(value);
	}

	@Override
	public void setValueFromDate(GregorianCalendar value) {
		this.value = value.toString();
	}

	@Override
	public void setValueFromInt(int value) {
		this.value = String.valueOf(value);
	}

	@Override
	public void setValueFromString(String value) {
		this.value = String.valueOf(value);
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
		throw new UnsupportedOperationException("Zugriff auf String als float-Wert");
	}

	@Override
	public void setValueFromFloat(float value) {
		this.value = Float.toString(value);
	}
}
