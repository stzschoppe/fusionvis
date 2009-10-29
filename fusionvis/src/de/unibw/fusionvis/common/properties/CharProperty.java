package de.unibw.fusionvis.common.properties;

import java.util.GregorianCalendar;

import de.unibw.fusionvis.common.Type;

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
	 * @param id Bezeichnung der Eigenschaft
	 * @param type Type der Eigenschaft
	 */
	public CharProperty(String id, Type type) {
		super(id, Type.TChar);
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
		throw new UnsupportedOperationException("Setzen eines char-Wertes mit einem char-Wert");

	}

	@Override
	public void setValueFromContainerProperty(ContainerProperty value) {
		throw new UnsupportedOperationException("Setzen eines char-Wertes mit einer Containerproperty");

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
		throw new UnsupportedOperationException("Setzen eines char-Wertes mit einem String");

	}

}
