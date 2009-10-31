package de.unibw.fusionvis.viewer;

import com.jme.app.SimpleGame;
import com.jme.bounding.BoundingBox;
import com.jme.bounding.BoundingSphere;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.shape.Box;
import com.jme.scene.shape.Cylinder;
import com.jme.scene.shape.Sphere;

public  class Viewer extends SimpleGame {
	private static int numberOfPoints = 0;
	private final boolean cones = true;

	public static void main(String[] args) {
		Viewer main = new Viewer();
		main.setConfigShowMode(ConfigShowMode.AlwaysShow);
		main.start();
	}

	protected void simpleInitGame() {
		Box plane = new Box("Plane", new Vector3f(0, -0.01f, 0), 20, 0.01f, 20);
		plane.setModelBound(new BoundingBox());
		plane.updateModelBound();
		plane.setDefaultColor(ColorRGBA.white.clone());

		for (int i = 0; i < 50; i++) {
			createPoint();
		}
		// rootNode.setLightCombineMode(Spatial.LightCombineMode.Off);
		rootNode.attachChild(plane);
		rootNode.setModelBound(new BoundingBox());
		rootNode.updateModelBound();
	}

	private void createPoint() {
		numberOfPoints++;
		float upperBound = 20;
		float x = (float) (2 * upperBound * Math.random()) - upperBound;
		float y = (float) (upperBound * Math.random());
		float z = (float) (2 * upperBound * Math.random()) - upperBound;
		float[] rot = { (float) Math.PI / 2, (float) Math.PI / 2, 0 };
		float h = upperBound - y;
		float r = .2f * h;
		Sphere sphere = new Sphere("Point " + numberOfPoints, new Vector3f(x,
				y, z), 50, 50, 0.3f);

		sphere.setModelBound(new BoundingSphere());
		sphere.updateModelBound();
		if (numberOfPoints % 3 == 1) {
			sphere.setDefaultColor(ColorRGBA.red.clone());
		} else
			sphere.setDefaultColor(ColorRGBA.blue.clone());
		if (cones) {
			Cylinder cone = new Cylinder("Movement Cone" + numberOfPoints, 50,
					50, 2, -4, false);
			cone.updateGeometry(20, 20, 0, r, h, false, false);
			cone.setLocalRotation(new Quaternion(rot));
			cone.setLocalTranslation(x, y + h / 2, z);
			cone.setDefaultColor(ColorRGBA.green.clone());
			cone.setModelBound(new BoundingBox());
			cone.updateModelBound();
			rootNode.attachChild(cone);
		}
		rootNode.attachChild(sphere);

	}

}