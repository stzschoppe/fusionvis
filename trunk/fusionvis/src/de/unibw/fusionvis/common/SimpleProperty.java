/**
 * 
 */
package de.unibw.fusionvis.common;

/**
 * <p>Eigenschaftscontainer, der Eigenschaften mit ihrer ID, ihrem Typ und ihrem Wert speichert.
 * </p>
 * 
 * Gespeichert werden die Werte als String.
 * 
 * @see de.unibw.fusionvis.common.Type
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
	
}
