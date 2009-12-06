package de.unibw.fusionvis.backend;

import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jme.scene.Node;

import de.unibw.fusionvis.datamodel.DataSet;

/**
 * Die Mapperklasse wandelt ein Datenmodell in eine jme-Szene um.
 * @author stzschoppe
 *
 */
public abstract class Mapper {
	/**
	 * Wurzelknoten der Szene.
	 */
	protected Node dataNode = null;
	/**
	 * Datenmodell
	 */
	protected DataSet dataSet;
	/**
	 * Maximale Ausmaße der Szene in Unit.
	 */
	protected Vector3f maximalDimenVector3f;
	/**
	 * Größe der darzustellenden Dateneinheiten (Kreisradius).
	 */
	protected float unitSize;

	/**
	 * Konstruktor
	 * @param maximalDimenVector3f Maximale Ausmaße der Szene in Unit.
	 * @param unitSize Größe der darzustellenden Dateneinheiten (Kreisradius).
	 */
	public Mapper(Vector3f maximalDimenVector3f,  float unitSize) {
		this.dataSet = null;
		this.maximalDimenVector3f = maximalDimenVector3f;
		this.unitSize = unitSize;
	}
	
	/**
	 * Konstruktor
	 * @param dataSet Datenmodell
	 * @param maximalDimenVector3f Maximale Ausmaße der Szene in Unit.
	 * @param unitSize Größe der darzustellenden Dateneinheiten (Kreisradius).
	 */
	public Mapper(DataSet dataSet, Vector3f maximalDimenVector3f, float unitSize) {
		this.dataSet = dataSet;
		this.maximalDimenVector3f = maximalDimenVector3f;
		this.unitSize = unitSize;
	}

	/**
	 * Liefert den Wurzelknoten des Szenenbaums, der die darszustellenden Objekte
	 * enthält.
	 * @param dataSet Datenmodell, das in eine jme-Szene umgewandelt werden soll.
	 * @return Wurzelknoten der Szene.
	 */
	public abstract Node getDataRoot(DataSet dataSet); 

	/**
	 * Berechnet Faktoren um die Dimensionen zu skalieren. 
	 * 
	 * @return Faktoren, der einzelnen Dimensionen.
	 */
	protected abstract Vector3f getCoefficient(); 
	
	/**
	 * Berechnet einen Vector mit Länge und Breite der benutzen Ebene.
	 * @return Vector vom Format (Länge|Breite)
	 */
	public abstract Vector2f getSize();

	/**
	 * Liefert den Wurzelknoten des Szenenbaums, der die darszustellenden Objekte
	 * enthält.
	 * @return Wurzelknoten der Szene.
	 */
	public Node getDataRoot() {
		if (dataSet == null) {
			throw new UnsupportedOperationException(
					"Mapper nicht initialisiert, getter mir Argument aufrufen.");
		} else {
			dataNode = getDataRoot(dataSet);
			texture(dataNode);
			return dataNode;
		}
	}


	/**
	 * Texturierung der Dateneinheiten des angegebenen DataNodes.
	 * @param dataNode Wurzelknoten der Dateneinheiten.
	 */
	public abstract void texture(Node dataNode);	
}
