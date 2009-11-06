package de.unibw.fusionvis.mapper;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;

import com.jme.bounding.BoundingSphere;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Node;
import com.jme.scene.Spatial.LightCombineMode;
import com.jme.scene.shape.Box;
import com.jme.scene.shape.Sphere;

import de.unibw.fusionvis.datamodel.Data;
import de.unibw.fusionvis.datamodel.DataSet;

public class Mapper {
	private Node dataNode = null;
	private DataSet dataSet;
	private Vector3f maximalDimenVector3f;

	public Mapper(Vector3f maximalDimenVector3f) {
		this.dataSet = dataSet;
		this.maximalDimenVector3f = maximalDimenVector3f;
	}

	// Abstract
	public Node getDataRoot(DataSet dataSet) {
		this.dataSet = dataSet;
		dataNode = new Node("dataRoot");
		dataNode.setLightCombineMode(LightCombineMode.Off);// eliminiere jeglichen Lichteinfluss

		Vector3f[] transform = getCoefficient();
		Vector3f factor = transform[0];
		Vector3f offset = transform[1];
		

		for (Data data : dataSet.getData()) {
			Box box = new Box(data.getId(), getPosition(
					data, transform), 5, 5, 5);

			box.setSolidColor(ColorRGBA.black);
			box.setModelBound(new BoundingSphere());
			box.updateModelBound();
			System.out.println(box.center);
			dataNode.attachChild(box);
		}

		return dataNode;
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

		Vector3f offset = new Vector3f(- lonMin, - timeMin.getTimeInMillis(), -latMin);
		Vector3f factor = new Vector3f(x, y, z);
		Vector3f[] result = {factor, offset};

//		System.out.println("Faktor " + factor);
//		System.out.println("Offset " + offset);
		return result;
	}

	private Vector3f getPosition(Data data, Vector3f[] transform) {
		float x = (data.getPosition().getComponent("Lon").getValueAsFloat() + transform[1].x)
				* transform[0].x;
		float z = (data.getPosition().getComponent("Lat").getValueAsFloat() + transform[1].z)
				* transform[0].z;
		float y = (data.getPosition().getComponent("LastModified")
				.getValueAsFloat() + transform[1].y)
				* transform[0].y;
//		System.out.println(new Vector3f(x, y, z));
		return new Vector3f(x, y, z);
		
	}

	public Node getDataRoot() {
		if (dataNode == null) {
			throw new UnsupportedOperationException(
					"Mapper nicht initialisiert, getter mir Argument aufrufen.");
		} else {
			return dataNode;
		}
	}
}
