/**
 * 
 */
package de.unibw.fusionvis.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
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
	 * Komponenten in Form einer <code>HashMap&lt;String, SimpleProperty>
	 */
	private HashMap<String, SimpleProperty> components;
		
	
	/**
	 * Konstruktor für einen leeren Vektor.
	 * @param id Bezeichner des Vektors.
	 */
	public VectorProperty(String id) {
		this.id = id;
		this.components = new HashMap<String, SimpleProperty>();
	}
	
	/**
	 * Konstruktor für einen Vektor mit angegebenen Inhalt.
	 * @param id Bezeichner des Vektors 
	 * @param components Komponenten des Vektors
	 */
	public VectorProperty(String id, Collection<SimpleProperty> components) {
		this.id = id;
		this.components = new HashMap<String, SimpleProperty>();
		for (Iterator<SimpleProperty> iterator = components.iterator(); iterator.hasNext();) {
			SimpleProperty component =  iterator.next();
			this.components.put(component.getId(), component);
		}
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
	}

	/**
	 * @return Die Komponenten als <code>ArrayList</code>
	 * 
	 */
	public ArrayList<SimpleProperty> getComponents() {
		return new ArrayList<SimpleProperty>(components.values());
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
		return components.size();
	}
	
	/**
	 * Fügt eine Komponente hinzu. existiert sie bereits, wird sie überschrieben.
	 * @param component Die hinzuzufügende Eigenschaft.
	 */
	public void addComponent(SimpleProperty component) {
		components.put(component.getId(), component);
	}
	
	/**
	 * Entfernt die zum Bezeicher gehörende Komponente
	 * @param id Bezeichner der entfernenden Komponente
	 * @return die entfernte Komponente oder null, wenn sie nicht vorhanden war
	 */
	public SimpleProperty removeComponent(String id){
		return components.remove(id);
	}
	
	/**
	 * Gibt eine Liste von Bezeichern der Komponenten des Vektors.
	 * @return Liste von Bezeichern
	 */
	public Set<String> getComponentIds() {
		return components.keySet();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String result = id + ":\n";
		for (Iterator<SimpleProperty> iterator = components.values().iterator(); iterator.hasNext();) {
			SimpleProperty component =  iterator.next();
			result += "->" + component.toString() + "\n";
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((components == null) ? 0 : components.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (!(obj instanceof VectorProperty))
			return false;
		VectorProperty other = (VectorProperty) obj;
		if (components == null) {
			if (other.components != null)
				return false;
		} else if (!components.equals(other.components))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
