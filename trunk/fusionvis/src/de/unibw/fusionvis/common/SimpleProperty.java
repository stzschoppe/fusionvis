/**
 * 
 */
package de.unibw.fusionvis.common;

/**
 * <p>Eigenschaftscontainer, eine Eigenschaft mit ihrer ID, ihrem Typ und ihrem Wert speichert.
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
	 * @see de.unibw.fusionvis.common.Type
	 */
	private Type type;
	
	/**
	 * Wert der Eigenschaft, gespeichert in einem String Objekt. Der zugewiesene Wert
	 * muss in den angegeben Typ überführbar sein.
	 */
	private String value;
	
	/**
	 * Konstruktor einer Eigenschaft unter angebe der erforderlichen Werte.
	 * @param id Bezeicher 
	 * @param type Typ
	 * @param value Wert
	 */
	public SimpleProperty(String id, Type type, String value) {
		this.id = id;
		this.type = type;
		if (Type.valueCorrect(value, type)) {
			this.value = value;
		}
		else this.value = Type.getNeutral(type);
		
	}
	
	/**
	 * Konstruktor einer Eigenschaft aus einer bestehenden Eigenschaft.
	 * @param sp Die zu kopierende Eigenschaft
	 */
	public SimpleProperty(SimpleProperty sp){
		this(sp.getId(), sp.getType(), sp.getValue());
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
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Setz die Eigenschaft auf einen neuen Wert. Wenn der Wert nicht zum Typ
	 * der Eigenschaft passt, wird die Zuweisung ignoriert.
	 * @param value the value to set
	 */
	public void setValue(String value) {
		if (Type.valueCorrect(value, type)) {
			this.value = value;
		}
	}

	/* (non-Javadoc)
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

	/* (non-Javadoc)
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
	
}
