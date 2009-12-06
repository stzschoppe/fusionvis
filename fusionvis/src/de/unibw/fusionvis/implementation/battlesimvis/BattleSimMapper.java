package de.unibw.fusionvis.implementation.battlesimvis;

import static java.lang.Math.PI;
import static java.lang.Math.asin;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;

import com.jme.bounding.BoundingSphere;
import com.jme.image.Texture;
import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.scene.shape.Sphere;
import com.jme.scene.state.TextureState;
import com.jme.system.DisplaySystem;
import com.jme.util.TextureManager;

import de.unibw.fusionvis.datamodel.Data;
import de.unibw.fusionvis.datamodel.DataSet;
import de.unibw.fusionvis.mapper.Mapper;
import de.unibw.fusionvis.viewer.TestViewer;

public class BattleSimMapper extends Mapper {
	
	private float latMin = 90f;
	private float latMax = -90f;

	private float lonMin = 180f;
	private float lonMax = -180f;

	private GregorianCalendar timeMin = new GregorianCalendar(3000, 11, 31); // 31.Dezember.3000
	private GregorianCalendar timeMax = new GregorianCalendar(0, 0, 1); // 1.Januar
	// im Jahre
	// 0

	public BattleSimMapper(Vector3f maximalDimenVector3f, float unitSize) {
		super(maximalDimenVector3f, unitSize);
	}

	public BattleSimMapper(DataSet dataSet, Vector3f maximalDimenVector3f, float unitSize) {
		super(dataSet, maximalDimenVector3f, unitSize);
	}

	@Override
	protected Vector3f getCoefficient() {
		// Koeffizienten der einzelnen Dimensionen
		float x = 1, y = 1, z = 1;

		Vector2f basePane = getSize();
		
		// Spannweite in die Projiziert werden soll
		float xWidth = basePane.x/maximalDimenVector3f.x;
		float zWidth = basePane.y/maximalDimenVector3f.z;

		// Bestimmung der Oberen und unteren Grenzen der Dimensionen.
		latMin = 90f;
		latMax = -90f;

		lonMin = 180f;
		lonMax = -180f;

		timeMin = new GregorianCalendar(3000, 11, 31); // 31.Dezember.3000
		timeMax = new GregorianCalendar(0, 0, 1); // 1.Januar
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
		float yWidth = (timeMax.getTimeInMillis()-timeMin.getTimeInMillis()) / (maximalDimenVector3f.y*1000);
		if (timeMax.getTimeInMillis() != timeMin.getTimeInMillis()) {
			y = yWidth
					/ (timeMax.getTimeInMillis() - timeMin.getTimeInMillis());
		} else {
			y = 1;
		}
		
		Vector3f factor = new Vector3f(x, y, z);

//		 System.out.println("Latitude: " + distanceonEarth(new Vector2f(lonMin, latMin), new Vector2f(lonMin, latMax)));
//		 System.out.println("Longitude: " + distanceonEarth(new Vector2f(lonMin, latMin), new Vector2f(lonMax, latMin)));
//		 System.out.println(yWidth +" Offset " + y);
		return factor;
	}

	@Override
	public Node getDataRoot(DataSet dataSet) {
		this.dataSet = dataSet;
		dataNode = new Node("dataRoot");
		// dataNode.setLightCombineMode(LightCombineMode.Off);// eliminiere
		// jeglichen Lichteinfluss

		Vector3f factor = getCoefficient();

		for (Data data : dataSet.getData()) {
			Sphere sphere = new Sphere(data.getId(), new Vector3f(0, 0, 0), 15,
					15, unitSize);
			sphere.setLocalTranslation(getPosition(data, factor));
			sphere.updateGeometricState(0, false);
			sphere.setModelBound(new BoundingSphere());
			sphere.updateModelBound();
			
			Node sphereNode = new Node(data.getId());
			Node orientationNode = initializeNode(new Node("orientationNode"));
			Node velocityNode = initializeNode(new Node("velocityNode"));
			Node futureConeNode = initializeNode(new Node("futureConeNode"));
			Node pastConeNode = initializeNode(new Node("pastConeNode"));
			
			sphereNode.attachChild(sphere);
			sphereNode.attachChild(orientationNode);
			sphereNode.attachChild(velocityNode);
			sphereNode.attachChild(futureConeNode);
			sphereNode.attachChild(pastConeNode);

			dataNode.attachChild(sphereNode);
			dataNode.updateGeometricState(0, false);
			dataNode.updateRenderState();
		}

		texture(dataNode);
		return dataNode;
	}

	@Override
	public void texture(Node dataNode) {
		if (dataNode.getChildren() == null) {
			return;
		}
		DisplaySystem display = DisplaySystem.getDisplaySystem();
		URL fr = TestViewer.class.getClassLoader().getResource(
				"de/unibw/fusionvis/img/fr.gif");
		URL ho = TestViewer.class.getClassLoader().getResource(
				"de/unibw/fusionvis/img/ho.gif");
		URL frperceived = TestViewer.class.getClassLoader().getResource(
				"de/unibw/fusionvis/img/frperceived.gif");
		URL hoperceived = TestViewer.class.getClassLoader().getResource(
				"de/unibw/fusionvis/img/hoperceived.gif");

		Texture texture1 = TextureManager.loadTexture(fr,
				Texture.MinificationFilter.Trilinear,
				Texture.MagnificationFilter.Bilinear);
		TextureState friendly = display.getRenderer().createTextureState();
		friendly.setEnabled(true);
		friendly.setTexture(texture1);

		Texture texture2 = TextureManager.loadTexture(frperceived,
				Texture.MinificationFilter.Trilinear,
				Texture.MagnificationFilter.Bilinear);
		TextureState friendlyPerceived = display.getRenderer()
				.createTextureState();
		friendlyPerceived.setEnabled(true);
		friendlyPerceived.setTexture(texture2);

		Texture texture3 = TextureManager.loadTexture(hoperceived,
				Texture.MinificationFilter.Trilinear,
				Texture.MagnificationFilter.Bilinear);
		TextureState hostilePerceived = display.getRenderer()
				.createTextureState();
		hostilePerceived.setEnabled(true);
		hostilePerceived.setTexture(texture3);

		Texture texture4 = TextureManager.loadTexture(ho,
				Texture.MinificationFilter.Trilinear,
				Texture.MagnificationFilter.Bilinear);
		TextureState hostile = display.getRenderer().createTextureState();
		hostile.setEnabled(true);
		hostile.setTexture(texture4);
		Collection<Spatial> spartials = dataNode.getChildren();

		for (Spatial spatial : spartials) {
			String certainty = dataSet.getDataById(spatial.getName())
					.getAbstractProperty("Certainty").getValueAsString();
			String hostitlityCode = dataSet.getDataById(spatial.getName())
					.getAbstractProperty("HostilityCode").getValueAsString();
			if (hostitlityCode.equals("HO")) {
				if (certainty.equals("Real")) {
					spatial.setRenderState(hostile);
				} else if (certainty.equals("Perceived")) {
					spatial.setRenderState(hostilePerceived);
				}
			} else if (hostitlityCode.equals("FR")) {
				if (certainty.equals("Real")) {
					spatial.setRenderState(friendly);
				} else if (certainty.equals("Perceived")) {
					spatial.setRenderState(friendlyPerceived);
				}
			} else {
				// Nichts tun
			}
			spatial.updateRenderState();
		}
	}

	private Vector3f getPosition(Data data, Vector3f transform) {
		float x = (data.getPosition().getComponent("Lon").getValueAsFloat() -lonMin)
				* transform.x;
		float z = (data.getPosition().getComponent("Lat").getValueAsFloat() - latMin)
				* transform.z;
		float y = (data.getPosition().getComponent("LastModified")
				.getValueAsDate().getTimeInMillis() - timeMin.getTimeInMillis())
				* transform.y;
		// System.out.println(new Vector3f(x, y, z));
		return new Vector3f(x, y, z);

	}
	
	/**
	 * Berechnet den Abstand zweier Punkte auf der Erde unter der
	 * Annahme, die Erde sei eine Kugel. Die Koordinaten sind in der 
	 * Reihenfolge Longitude, Latitude anzugeben. Benutzt wird
	 * die Haversine-Formel.
	 * @param point1 Koordinaten des ersten Punkts in Grad
	 * @param point2 Koordinaten des zweiten Punkts in Grad
	 * @return Ergebnis der Berechnung in Meter
	 */
	public float distanceonEarth(Vector2f point1, Vector2f point2){
		double R = 6371000.785;
		
		double lat1 = point1.y * PI/180;
		double lat2 = point2.y * PI/180;
		double deltaLon = (point1.x - point2.x) * PI/180;
		
		double x = sin((lat1-lat2)/2); x*=x;
		double y = sin((deltaLon)/2); y*=y; y*=cos(lat1)*cos(lat2);
		double z = sqrt(x+y);
		
		double distance = 2 * R * asin(z);
		
		return (float) distance;
	}
	
	/**
	 * Sorgt dafür, dass die Liste von Kindern nach dem initialisieren des
	 * Knotens nicht null, sondern eine leere Liste ist.
	 * @param node Zu initialisierender Knoten.
	 * @return Initialisierter Knoten.
	 */
	protected Node initializeNode(Node node) {
		node.attachChild(new Node("Dummy"));
		node.detachAllChildren();
		return node;
	}
	
	/**
	 * Berechnet einen Vector mit Länge und Breite der benutzen Ebene 
	 * in km.
	 * @return Vector vom Format (Länge|Breite)
	 */
	public Vector2f getSize(){
		// Bestimmung der Oberen und unteren Grenzen der Dimensionen.
		float latMin = 90f;
		float latMax = -90f;

		float lonMin = 180f;
		float lonMax = -180f;

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
		}
		
		
		Vector2f pointUL = new Vector2f(lonMin,latMin); // unten links 
		Vector2f pointOL = new Vector2f(lonMin,latMax); // oben links
		Vector2f pointUR = new Vector2f(lonMax,latMin); // unten rechts
		
		return new Vector2f(distanceonEarth(pointUL, pointUR), distanceonEarth(pointOL, pointUL));
	}

	public float getLatMin() {
		return latMin;
	}

	public float getLatMax() {
		return latMax;
	}

	public float getLonMin() {
		return lonMin;
	}

	public float getLonMax() {
		return lonMax;
	}

	public GregorianCalendar getTimeMin() {
		return timeMin;
	}

	public GregorianCalendar getTimeMax() {
		return timeMax;
	}
	
	public float getTimeSpan() {
		return (float) ((timeMax.getTimeInMillis()-timeMin.getTimeInMillis()) * 0.001);
	}

}
