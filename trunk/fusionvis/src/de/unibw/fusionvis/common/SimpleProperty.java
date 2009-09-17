/**
 * 
 */
package de.unibw.fusionvis.common;

/**
 * <p>Eigenschaftscontainer, der Eigenschaften mit ihrer ID, ihrem Typ und ihrem Wert speichert.
 * </p>
 * <p>Zulässige Typen sind:
 * <ul>
 *  <li><code>int</code></li>
 *  <li><code>float</code></li>
 *  <li><code>char</code></li>
 *  <li><code>Date</code></li>
 *  <li><code>String</code></li>
 * </ul>
 * </p>
 * Gespeichert werden die Werte als String.
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
	 * <p>
	 * Zulässige Typen sind:
	 * <ul>
	 * <li><code>int</code></li>
	 * <li><code>float</code></li>
	 * <li><code>char</code></li>
	 * <li><code>Date</code></li>
	 * <li><code>String</code></li>
	 * </ul>
	 * </p>
	 */
	private String type;
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
	public SimpleProperty(String id, String type, String value) {
		this.id = id;
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Konstruktor einer Eigenschaft aus einer bestehenden Eigenschaft.
	 * @param sp
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
	 * <p>
	 * Zulässige Typen sind:
	 * <ul>
	 * <li><code>int</code></li>
	 * <li><code>float</code></li>
	 * <li><code>char</code></li>
	 * <li><code>Date</code></li>
	 * <li><code>String</code></li>
	 * </ul>
	 * </p>
	 * @return Typ
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return Wert
	 */
	public String getValue() {
		return value;
	}
	
}
