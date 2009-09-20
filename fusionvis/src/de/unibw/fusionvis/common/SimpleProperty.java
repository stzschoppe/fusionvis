/**
 * 
 */
package de.unibw.fusionvis.common;

/**
 * <p>
 * Eigenschaftscontainer, eine Eigenschaft mit ihrer ID, ihrem Typ und ihrem
 * Wert speichert.
 * </p>
 * 
 * Gespeichert werden die Werte als String.
 * 
 * @author stzschoppe
 * 
 */
public class SimpleProperty {
	/**
	 * ID der Eigenschaft.
	 */
	private String id;

	/**
	 * Typ der Eigenschaft.
	 * 
	 * @see de.unibw.fusionvis.common.Type
	 */
	private Type type;

	/**
	 * Wert der Eigenschaft, gespeichert in einem String Objekt. Der zugewiesene
	 * Wert muss in den angegeben Typ überführbar sein.
	 */
	private String value;

	/**
	 * Konstruktor einer Eigenschaft unter Angabe der erforderlichen Werte.
	 * 
	 * @param id
	 *            Bezeicher
	 * @param type
	 *            Typ
	 * @param value
	 *            Wert
	 */
	public SimpleProperty(String id, Type type, String value) {
		this.id = id;
		this.type = type;
		if (Type.valueCorrect(value, type)) {
			this.value = value;
		} else
			this.value = Type.getNeutral(type);

	}

	/**
	 * Konstruktor einer Eigenschaft aus einer bestehenden Eigenschaft.
	 * 
	 * @param sp
	 *            Die zu kopierende Eigenschaft
	 */
	public SimpleProperty(SimpleProperty sp) {
		this(sp.getId(), sp.getType(), sp.getStringValue());
	}
	
	/**
	 * Konstruktor einer Eigenschaft unter Angabe der erforderlichen Werte.
	 * 
	 * @param id
	 *            Bezeicher
	 * @param value
	 *            Wert
	 */
	public SimpleProperty(String id, int value){
		this.id = id;
		this.type = Type.TInt;
		this.value = String.valueOf(value);
	}
	
	/**
	 * Konstruktor einer Eigenschaft unter Angabe der erforderlichen Werte.
	 * 
	 * @param id
	 *            Bezeicher
	 * @param value
	 *            Wert
	 */
	public SimpleProperty(String id, float value){
		this.id = id;
		this.type = Type.TFloat;
		this.value = String.valueOf(value);
	}
	
	/**
	 * Konstruktor einer Eigenschaft unter Angabe der erforderlichen Werte.
	 * 
	 * @param id
	 *            Bezeicher
	 * @param value
	 *            Wert
	 */
	public SimpleProperty(String id, char value){
		this.id = id;
		this.type = Type.TChar;
		this.value = String.valueOf(value);
	}
	
	/**
	 * Konstruktor einer Eigenschaft unter Angabe der erforderlichen Werte.
	 * 
	 * @param id
	 *            Bezeicher
	 * @param value
	 *            Wert
	 */
	public SimpleProperty(String id, boolean value){
		this.id = id;
		this.type = Type.TBool;
		this.value = String.valueOf(value);
	}
	
	/**
	 * Konstruktor einer Eigenschaft unter Angabe der erforderlichen Werte.
	 * 
	 * @param id
	 *            Bezeicher
	 * @param value
	 *            Wert
	 */
	public SimpleProperty(String id, String value){
		this.id = id;
		this.type = Type.TString;
		this.value = String.valueOf(value);
	}

	/**
	 * @return Bezeichner
	 */
	public String getId() {
		return id;
	}

	/**
	 *@see de.unibw.fusionvis.common.Type
	 * @return Typ
	 */
	public Type getType() {
		return type;
	}

	/**
	 * @return Wert
	 * @deprecated
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Setz die Eigenschaft auf einen neuen Wert. Wenn der Wert nicht zum Typ
	 * der Eigenschaft passt, wird die Zuweisung ignoriert.
	 * 
	 * @param value
	 *            the value to set
	 * @deprecated
	 */
	public void setValue(String value) {
		if (Type.valueCorrect(value, type)) {
			this.value = value;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof SimpleProperty))
			return false;
		SimpleProperty other = (SimpleProperty) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	/**
	 * Gibt eine Integerrepräsentation der Eigenschaft wieder.
	 * 
	 * @return Integerwert der Eigenschaft, oder 0, falls kein gültiger Wert.
	 */
	public int getIntValue() {
		if (getType() == Type.TInt) {
			return Integer.valueOf(value);
		} else {
			return Integer.valueOf(Type.getNeutral(Type.TInt));
		}
	}

	/**
	 * Gibt eine Floatrepräsentation der Eigenschaft wieder.
	 * 
	 * @return Floatwert der Eigenschaft, oder 0.0, falls kein gültiger Wert.
	 */
	public float getFloatValue() {
		if (getType() == Type.TFloat) {
			return Float.valueOf(value);
		} else {
			return Float.valueOf(Type.getNeutral(Type.TFloat));
		}
	}

	/**
	 * Gibt eine Charrepräsentation der Eigenschaft wieder.
	 * 
	 * @return Charwert der Eigenschaft, oder "\0", falls kein gültiger Wert.
	 */
	public char getCharValue() {
		if (getType() == Type.TChar) {
			return value.charAt(0);
		} else {
			return Type.getNeutral(Type.TChar).charAt(0);
		}
	}

	/**
	 * Gibt eine Boolrepräsentation der Eigenschaft wieder.
	 * 
	 * @return Boolwert der Eigenschaft, oder <code>false</code>, falls kein
	 *         gültiger Wert.
	 */
	public boolean getBoolValue() {
		if (getType() == Type.TBool) {
			return Boolean.valueOf(value);
		} else {
			return Boolean.valueOf(Type.getNeutral(Type.TBool));
		}
	}

	/**
	 * Gibt eine Stringrepräsentation der Eigenschaft wieder.
	 * 
	 * @return Stringwert der Eigenschaft.
	 */
	public String getStringValue() {
		return value;
	}
	
	/**
	 * Setzt die Eigenschaft mit einem Integerwert. Fals der Typ nicht mit dem Typ der
	 * Eigenschaft übereinstimmt, geschiet keine Änderung.
	 * @param newValue Der neue Wert.
	 */
	public void set_Value(int newValue) {
		if (getType() == Type.TInt) 
			value = String.valueOf(newValue);
	}
	
	/**
	 * Setzt die Eigenschaft mit einem Floatwert. Fals der Typ nicht mit dem Typ der
	 * Eigenschaft übereinstimmt, geschiet keine Änderung.
	 * @param newValue Der neue Wert.
	 */
	public void set_Value(float newValue) {
		if (getType() == Type.TFloat) 
			value = String.valueOf(newValue);
	}
	
	/**
	 * Setzt die Eigenschaft mit einem Charwert. Fals der Typ nicht mit dem Typ der
	 * Eigenschaft übereinstimmt, geschiet keine Änderung.
	 * @param newValue Der neue Wert.
	 */
	public void set_Value(char newValue) {
		if (getType() == Type.TChar) 
			value = String.valueOf(newValue);
	}
	
	/**
	 * Setzt die Eigenschaft mit einem Boolwert. Fals der Typ nicht mit dem Typ der
	 * Eigenschaft übereinstimmt, geschiet keine Änderung.
	 * @param newValue Der neue Wert.
	 */
	public void set_Value(boolean newValue) {
		if (getType() == Type.TBool) 
			value = String.valueOf(newValue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return id + " [type=" + type + ", value=" + value + "]";
	}

}
