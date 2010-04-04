package de.unibw.fusionvis.datamodel.properties;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.GregorianCalendar;

import de.unibw.fusionvis.datamodel.Type;

/**
 * Klasse zur Speicherung von Date Eigenschaften
 * @author stzschoppe
 *
 */
public class DateProperty extends AbstractProperty {
	/**
	 * Wert der Eigenschaft als <code>GregorianCalendar</code>
	 */
	private GregorianCalendar value;

	/**
	 * Konstruktor aus einem zu parsenden String
	 * @param id Bezeicher der Eigenschaft
	 * @param value Zu setzender Wert als String
	 */
	public DateProperty(String id, String value) {
		super(id, Type.TDate);
		this.value = new GregorianCalendar();
		try {
			this.value.setTime(DateFormat.getInstance().parse(value));
		} catch (ParseException e) {
			throw new IllegalArgumentException("Falsches Format des, in ein Date zu parsenden Strings.");
		}
	}
	
	/**
	 * Konstruktor aus einem Date
	 * @param id Bezeicher der Eigenschaft
	 * @param value Zu setzender Wert als Date
	 */
	public DateProperty(String id, Date value) {
		super(id, Type.TDate);
		this.value = new GregorianCalendar();
			this.value.setTime(value);
	}

	@Override
	public boolean getValueAsBoolean() {
		throw new UnsupportedOperationException("Zugriff auf Date als boolean-Wert");
	}

	@Override
	public char getValueAsChar() {
		throw new UnsupportedOperationException("Zugriff auf Date als char-Wert");
	}

	@Override
	public ContainerProperty getValueAsContainerProperty() {
		throw new UnsupportedOperationException("Zugriff auf Date als ContainerProperty");
	}

	@Override
	public GregorianCalendar getValueAsDate() {
		return value;
	}

	@Override
	@Deprecated
	public int getValueAsInt() {
		return (int)value.getTimeInMillis();
	}

	@Override
	public String getValueAsString() {
		return DateFormat.getInstance().format(value.getTime());
	}

	@Override
	public void setValueFromBoolean(boolean value) {
		throw new UnsupportedOperationException("Setzen eines Dates mit einem boolean-Wert");
	}

	@Override
	public void setValueFromChar(char value) {
		throw new UnsupportedOperationException("Setzen eines Dates mit einem char-Wert");

	}

	@Override
	public void setValueFromDate(GregorianCalendar value) {
		this.value = value;

	}

	/**
	 * Setzt die Zeit, indem der übergebene int in ein Date umgewandelt wird.
	 * @param value Zeit in ms
	 */
	@Override
	public void setValueFromInt(int value) {
		this.value.setTime(new Date(value));

	}

	/**
	 * Setzt die Zeit, indem der übergebene String in ein Date geparst wird.
	 * @param value zu parsender String.
	 */
	@Override
	public void setValueFromString(String value) {
		try {
			this.value.setTime(DateFormat.getInstance().parse(value));
		} catch (ParseException e) {
			throw new IllegalArgumentException("Falsches Format des, in ein Date zu parsenden Strings.");
		}

	}

	@Override
	@Deprecated
	public float getValueAsFloat() {
		return value.getTimeInMillis();
	}

	@Override
	public void setValueFromFloat(float value) {
		throw new UnsupportedOperationException("Setzen eines Dates mit einem float-Wert");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return id + ": type=" + type + ", value=" + DateFormat.getInstance().format(value.getTime()) + "\n";
	}
}
