package de.unibw.fusionvis.mapper;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;

import com.jme.bounding.BoundingSphere;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.shape.Sphere;

import de.unibw.fusionvis.datamodel.Data;
import de.unibw.fusionvis.datamodel.DataSet;

public class Mapper {
	private Node dataRoot = null;
	private DataSet dataSet;
	private Vector3f maximalDimenVector3f;

	public Mapper(DataSet dataSet,
			Vector3f maximalDimenVector3f) {
		this.dataSet = dataSet;
		this.maximalDimenVector3f = maximalDimenVector3f;
	}

	// Abstract
	public Node getDataRoot(DataSet dataSet) {
		dataRoot  = new Node("dataRoot");
		
		Vector3f[] transform = getCoefficient();
		Vector3f factor = transform[0];
		Vector3f offset = transform[1];

		for (Data data : dataSet.getData()) {
			Sphere sphere = new Sphere(data.getId(), new Vector3f(getPosition(
					data, transform).x, getPosition(data, transform).y,
					getPosition(data, transform).z), 50, 50, 5f);

			sphere.setModelBound(new BoundingSphere());
			sphere.updateModelBound();
			dataRoot.attachChild(sphere);
		}

		return dataRoot;
	}

	/**
	 * Berechnet Faktoren um die Dimension zu skalieren und den auf einen
	 * Bereich zwischen 0 und einem angegebenen Maximum zu verschieben.
	 * 
	 * @return Array, an Position 0 stehen die Faktoren, an 1 die Offsets.
	 */
	// Abstract
	private Vector3f[] getCoefficient() {
		// Koeffizienten der einzelnen Dimensionen
		float x = 1, y = 1, z = 1;

		// Spannweite in die Projiziert werden soll
		float xWidth = maximalDimenVector3f.x;
		float yWidth = maximalDimenVector3f.y;
		float zWidth = maximalDimenVector3f.z;

		// Bestimmung der Oberen und unteren Grenzen der Dimensionen.
		float latMin = 90f;
		float latMax = -90f;

		float lonMin = 180f;
		float lonMax = -180f;

		GregorianCalendar timeMin = new GregorianCalendar(3000, 11, 31); // 31.Dezember.3000
		GregorianCalendar timeMax = new GregorianCalendar(0, 0, 1); // 1.Januar
		// im Jahre
		// 0

		ArrayList<Data> dataList = dataSet.getData();

		for (Iterator<Data> iterator = dataList.iterator(); iterator.hasNext();) {
			Data data = iterator.next();

			if (data.getPosition().getComponent("Lat").getValueAsFloat() < latMin) {
				latMin = data.getPosition().getComponent("Lat")
						.getValueAsFloat();
			} else if (data.getPosition().getComponent("Lat").getValueAsFloat() > latMax) {
				latMax = data.getPosition().getComponent("Lat")
						.getValueAsFloat();
			}
			if (data.getPosition().getComponent("Lon").getValueAsFloat() < lonMin) {
				lonMin = data.getPosition().getComponent("Lon")
						.getValueAsFloat();
			} else if (data.getPosition().getComponent("Lon").getValueAsFloat() > lonMax) {
				lonMax = data.getPosition().getComponent("Lon")
						.getValueAsFloat();
			}
			if (data.getPosition().getComponent("LastModified")
					.getValueAsDate().before(timeMin)) {
				timeMin = data.getPosition().getComponent("LastModified")
						.getValueAsDate();
			} else if (data.getPosition().getComponent("LastModified")
					.getValueAsDate().after(timeMax)) {
				timeMax = data.getPosition().getComponent("LastModified")
						.getValueAsDate();
			}
		}

		if (lonMax != lonMin) {
			x = xWidth / (lonMax - lonMin);
		} else {
			x = 1;
		}
		if (latMax != latMin) {
			z = zWidth / (latMax - latMin);
		} else {
			z = 1;
		}
		if (timeMax.getTimeInMillis() != timeMin.getTimeInMillis()) {
			y = yWidth
					/ (timeMax.getTimeInMillis() - timeMin.getTimeInMillis());
		} else {
			y = 1;
		}

		float xOffset = 0 - lonMax;
		float yOffset = 0 - timeMax.getTimeInMillis();
		float zOffset = 0 - latMax;

		return new Vector3f[] { new Vector3f(x, y, z),
				new Vector3f(xOffset, yOffset, zOffset) };
	}

	private Vector3f getPosition(Data data, Vector3f[] transform) {
		float x = (data.getPosition().getComponent("Lon").getValueAsFloat() + transform[1].x)
				/ transform[0].x;
		float z = (data.getPosition().getComponent("Lat").getValueAsFloat() + transform[1].z)
				/ transform[0].z;
		float y = (data.getPosition().getComponent("LastModified")
				.getValueAsFloat() + transform[1].y)
				/ transform[0].y;
		return new Vector3f(x, y, z);
	}
	
	public Node getDataRoot(){
		if (dataRoot == null) {
			return getDataRoot(dataSet);
		} else {
			return dataRoot;
		}
	}
}
