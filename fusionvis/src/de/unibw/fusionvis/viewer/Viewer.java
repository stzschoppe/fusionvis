package de.unibw.fusionvis.viewer;

import com.jme.app.SimpleGame;
import com.jme.bounding.BoundingBox;
import com.jme.bounding.BoundingSphere;
import com.jme.light.PointLight;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import com.jme.scene.Line;
import com.jme.scene.Node;
import com.jme.scene.Spatial.LightCombineMode;
import com.jme.scene.shape.Box;
import com.jme.scene.shape.Cylinder;
import com.jme.scene.shape.Sphere;
import com.jme.scene.state.BlendState;
import com.jme.scene.state.MaterialState;
import com.jme.system.DisplaySystem;

public  class Viewer extends SimpleGame {
	private static int numberOfPoints = 0;
	private final boolean cones = true;
	private Node gridNode;
	private Node helperNode;
	private Node root;
	private MaterialState materialState;
    /**
     * The amount of opacity (0 = fully transparent or invisible).
     */
    private float opacityAmount = 0.4f;
 
    public static void main(String[] args) {
		Viewer main = new Viewer();
		main.setConfigShowMode(ConfigShowMode.AlwaysShow);
		main.start();
	}

	protected void simpleInitGame() {
		// set the background color to light gray
        display.getRenderer().setBackgroundColor(ColorRGBA.blue);

        // rootNode einer globalen Knotenvariable zuweisen, um in "nested classes" darauf zuzugreifen
        root = rootNode;
        
        // Knoten "helperNode" erzeugen (fuer Gizmos und Grid)
        helperNode = new Node("helperNode");
        
        createGrid();// Grid erzeugen
        helperNode.attachChild(gridNode);// Grid an "helperNode" anhaengen
        helperNode.setLightCombineMode(LightCombineMode.Off);// eliminiere jeglichen Lichteinfluss
                    
        root.attachChild(helperNode);// "helperNode" an "root" anhaengen
        
        // detach all possible lights, we will setup our own
        lightState.detachAll();
 
        // our light
        final PointLight light = new PointLight();
        light.setDiffuse(ColorRGBA.white);
        light.setSpecular(ColorRGBA.white);
        light.setLocation(new Vector3f(100.0f, 100.0f, 100.0f));
        light.setEnabled(true);
 
        // attach the light to a lightState
        lightState.attach(light);

		for (int i = 0; i < 20; i++) {
			createPoint();
		}
		// rootNode.setLightCombineMode(Spatial.LightCombineMode.Off);

	}

	private void createPoint() {
		numberOfPoints++;
		float upperBound = 200;
		float x = (float) (2 * upperBound * Math.random()) - upperBound;
		float y = (float) (.5*upperBound * Math.random());
		float z = (float) (2 * upperBound * Math.random()) - upperBound;
		float[] rot = { (float) Math.PI / 2, (float) Math.PI / 2, 0 };
		float h = upperBound - y;
		float r = .3f * h;
		Sphere sphere = new Sphere("Point " + numberOfPoints, new Vector3f(x,
				y, z), 50, 50, 5f);

		sphere.setModelBound(new BoundingSphere());
		sphere.updateModelBound();
		if (numberOfPoints % 3 == 1) {
			sphere.setDefaultColor(ColorRGBA.red.clone());
		} else
			sphere.setDefaultColor(ColorRGBA.blue.clone());
		if (cones) {
	        final BlendState alphaState = DisplaySystem.getDisplaySystem().getRenderer().createBlendState();
	        alphaState.setBlendEnabled(true);
	        alphaState.setSourceFunction(BlendState.SourceFunction.SourceAlpha);
	        alphaState.setDestinationFunction(BlendState.DestinationFunction.OneMinusSourceAlpha);
	        alphaState.setTestEnabled(true);
	        alphaState.setTestFunction(BlendState.TestFunction.GreaterThan);
	        alphaState.setEnabled(true);
	        
	        // the sphere material taht will be modified to make the sphere
	        // look opaque then transparent then opaque and so on
	        materialState = display.getRenderer().createMaterialState();
	        materialState.setAmbient(new ColorRGBA(0.0f, 0.0f, 0.0f, opacityAmount));
	        materialState.setDiffuse(new ColorRGBA(0.1f, 0.5f, 0.8f, opacityAmount));
	        materialState.setSpecular(new ColorRGBA(1.0f, 1.0f, 1.0f, opacityAmount));
	        materialState.setShininess(128.0f);
	        materialState.setEmissive(new ColorRGBA(0.0f, 0.0f, 0.0f, opacityAmount));
	        materialState.setEnabled(true);
	 
	        // IMPORTANT: this is used to handle the internal sphere faces when
	        // setting them to transparent, try commenting this line to see what
	        // happens
	        materialState.setMaterialFace(MaterialState.MaterialFace.FrontAndBack);

			
			Cylinder cone = new Cylinder("Movement Cone" + numberOfPoints, 50,
					50, 2, -4, false);
			cone.updateGeometry(20, 20, 0, r, h, false, false);
			cone.setLocalRotation(new Quaternion(rot));
			cone.setLocalTranslation(x, y + h / 2, z);
			cone.setDefaultColor(ColorRGBA.green.clone());
			
			cone.setRenderState(materialState);
			cone.updateRenderState();
			
	        cone.setRenderState(alphaState);
			cone.updateRenderState();
			cone.setRenderQueueMode(Renderer.QUEUE_TRANSPARENT);
			
			cone.setModelBound(new BoundingBox());
			cone.updateModelBound();
			rootNode.attachChild(cone);
		}
		rootNode.attachChild(sphere);

	}
	
    public void createGrid() 
    {
    	gridNode = new Node("gridNode");
    	
    	/* Wichtig beim Erstellen des Grids ist, dass auch dessen Linien ModelBounds erhalten.
         * Wenn das nicht gemacht wird werden die ModelBounds von hinzugefuegten Objekten
         * (z.B. Box) vererbt und Fehler koennen auftreten wie beispielsweise:
         * Grid verschwindet sobald die Box ausserhalb des Sichtfelds ist. 
         */

    	// Gitternetz
        for (int x = -300; x <= 300; x=x+10) {
            Line l = new Line("xLine" + x, new Vector3f[]{new Vector3f((float) x, 0f, -300f), new Vector3f((float) x, 0f, 300f)}, null, null, null);
            l.setSolidColor(ColorRGBA.darkGray);
            l.setModelBound(new BoundingBox());
            l.updateModelBound();
            l.setCastsShadows(false);
            gridNode.attachChild(l);
        }

        for (int z = -300; z <= 300; z=z+10) {
            Line l = new Line("zLine" + z, new Vector3f[]{new Vector3f(-300f, 0f, (float) z), new Vector3f(300f, 0f, (float) z)}, null, null, null);
            l.setSolidColor(ColorRGBA.darkGray);
            l.setModelBound(new BoundingBox());
            l.updateModelBound();
            l.setCastsShadows(false);
            gridNode.attachChild(l);
        }
        
        // rote x-Achse
        final Line xAxis = new Line("xAxis", new Vector3f[]{new Vector3f(-300f, 0f, 0f), new Vector3f(300f, 0f, 0f)}, null, null, null);
        xAxis.setModelBound(new BoundingBox());
        xAxis.updateModelBound();
        xAxis.setSolidColor(ColorRGBA.red);
        xAxis.setCastsShadows(false);
        gridNode.attachChild(xAxis);

        // rote z-Achse
        final Line zAxis = new Line("zAxis", new Vector3f[]{new Vector3f(0f, 0f, -300f), new Vector3f(0f, 0f, 300f)}, null, null, null);
        zAxis.setModelBound(new BoundingBox());
        zAxis.updateModelBound();
        zAxis.setSolidColor(ColorRGBA.red);
        zAxis.setCastsShadows(false);            
        gridNode.attachChild(zAxis);
    }

}