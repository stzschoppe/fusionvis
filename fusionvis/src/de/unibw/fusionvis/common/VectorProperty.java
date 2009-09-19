/**
 * 
 */
package de.unibw.fusionvis.common;

import java.util.HashMap;
//XXX evtl SimpleProperty und VectorProperty auf ein Interface zurückführen, damit man sie schachteln kann
/**
 * Eigenschaftscontainer, der mehrere Eigenschaften (<code>SimpleProperty</Code>) mit ihreren ID, 
 * ihren Typen und ihren Werten speichert.
 * @author stzschoppe
 */
public class VectorProperty {
	/**
	 * ID der Eigenschaft.
	 */
	private String id;
	
	/**
	 * Komponenten in Form einer <code>ArrayList&lt;SimpleProperty>
	 */
	private HashMap<String, SimpleProperty> components;
		
	/**
	 * Dimension des Vektors.
	 */
	private int dimension;
	
	/**
	 * Konstruktor für einen leeren Vektor.
	 * @param id Bezeichner des Vektors.
	 */
	public VectorProperty(String id) {
		this.id = id;
		this.dimension = 0;
		this.components = new HashMap<String, SimpleProperty>();
	}
	
	/**
	 * Konstruktor für einen Vektor mit angegebenen Inhalt.
	 * @param id Bezeichner des Vektors
	 * @param componets Komponenten des Vektors
	 */
	public VectorProperty(String id, HashMap<String, SimpleProperty> componets) {
		this.id = id;
		this.components = componets;
		this.dimension = componets.size();
	}
	
	


	/**
	 * @return Bezeichner
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param components Die zu setzenden Komponenten in Form einer <code>HashMap&lt;SimpleProperty></code>
	 */
	public void setComponents(HashMap<String, SimpleProperty> components) {
		this.components = components;
		dimension = components.size();
	}

	/**
	 * @return Die Komponenten, <code>HashMap</code> von Bezeicher der Komponente (Key) 
	 * und <code>SimpleProperty</code> (Value)
	 */
	public HashMap<String, SimpleProperty> getComponents() {
		return components;
	}
	
	/**
	 * Liefert eine spezielle Komponente des Vektors unter Angabe seines Bezeichners,
	 * oder <code>null</code> falls der Bezeichner ungültig ist.
	 * @param id Bezeicher der Komponente
	 * @return Die Komponente
	 */
	public SimpleProperty getComponent(String id){ //TODO Testen
		if (components.containsKey(id)) {
			return components.get(id);
		} else {
			//XXX Exception?
			return null;
		}
	}

	/**
	 * @return Die Dimension des gespeicherten Vektors von <code>SimpleProperty</code> Objekten.
	 */
	public int getDimension() {
		return dimension;
	}
	
	/**
	 * @param property Die hinzuzufügende Eigenschaft.
	 */
	public void addProperty(SimpleProperty property) {
		components.put(property.getId(), property);
	}
	
}
