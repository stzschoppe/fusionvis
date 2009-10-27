package de.unibw.fusionvis.common.properties;

import java.util.GregorianCalendar;

import de.unibw.fusionvis.common.Type;

/**
 * Klasse zur Speicherung von bool'schen Eigenschaften
 * @author stzschoppe
 *
 */
public class BooleanProperty extends AbstractProperty {
	/**
	 * Wert der Eigenschaft als <code>boolean</code>
	 */
	private boolean value;

	/**
	 * Konstruktor
	 * @param id Bezeichnung der Eigenschaft
	 */
	public BooleanProperty(String id) {
		super(id, Type.TBool);
	}

	@Override
	public boolean getValueAsBoolean() {
		
		return value;
	}

	@Override
	public char getValueAsChar() {
		throw new UnsupportedOperationException("Zugriff auf boolean als char-Wert");
	}

	@Override
	public ContainerProperty getValueAsContainerProperty() {
		throw new UnsupportedOperationException("Zugriff auf boolean als ContainerProperty");
	}

	@Override
	public GregorianCalendar getValueAsDate() {
		throw new UnsupportedOperationException("Zugriff auf boolean als Date");
	}

	@Override
	public int getValueAsInt() {
		throw new UnsupportedOperationException("Zugriff auf boolean als int-Wert");
	}

	@Override
	public String getValueAsString() {
		return Boolean.toString(value);
	}

	@Override
	public void setValueFromBoolean(boolean value) {
		this.value = value;

	}

	@Override
	public void setValueFromChar(char value) {
		throw new UnsupportedOperationException("Setzen eines boolean-Wertes mit einem char-Wert");

	}

	@Override
	public void setValueFromContainerProperty(ContainerProperty value) {
		throw new UnsupportedOperationException("Setzen eines boolean-Wertes mit einer Containerproperty");

	}

	@Override
	public void setValueFromDate(GregorianCalendar value) {
		throw new UnsupportedOperationException("Setzen eines boolean-Wertes mit einem Date");

	}

	@Override
	public void setValueFromInt(int value) {
		throw new UnsupportedOperationException("Setzen eines boolean-Wertes mit einem int-Wert");

	}

	@Override
	public void setValueFromString(String value) {
		throw new UnsupportedOperationException("Setzen eines boolean-Wertes mit einem String");

	}

}
