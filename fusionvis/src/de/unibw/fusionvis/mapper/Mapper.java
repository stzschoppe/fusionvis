package de.unibw.fusionvis.mapper;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;

import com.jme.bounding.BoundingSphere;
import com.jme.image.Texture;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.scene.shape.Sphere;
import com.jme.scene.state.TextureState;
import com.jme.system.DisplaySystem;
import com.jme.util.TextureManager;

import de.unibw.fusionvis.datamodel.Data;
import de.unibw.fusionvis.datamodel.DataSet;
import de.unibw.fusionvis.viewer.TestViewer;

public abstract class Mapper {
	protected Node dataNode = null;
	protected DataSet dataSet;
	protected Vector3f maximalDimenVector3f;

	public Mapper(Vector3f maximalDimenVector3f) {
		this.dataSet = null;
		this.maximalDimenVector3f = maximalDimenVector3f;
	}
	
	public Mapper(DataSet dataSet, Vector3f maximalDimenVector3f) {
		this.dataSet = dataSet;
		this.maximalDimenVector3f = maximalDimenVector3f;
	}

	// Abstract
	public abstract Node getDataRoot(DataSet dataSet); 

	/**
	 * Berechnet Faktoren um die Dimension zu skalieren und den auf einen
	 * Bereich zwischen 0 und einem angegebenen Maximum zu verschieben.
	 * 
	 * @return Array, an Position 0 stehen die Faktoren, an 1 die Offsets.
	 */
	// Abstract
	protected abstract Vector3f[] getCoefficient(); 

	public Node getDataRoot() {
		if (dataNode == null) {
			throw new UnsupportedOperationException(
					"Mapper nicht initialisiert, getter mir Argument aufrufen.");
		} else {
			texture(dataNode, DisplaySystem.getDisplaySystem());
			return dataNode;
		}
	}


	public abstract void texture(Node dataNode, DisplaySystem display);	
}
