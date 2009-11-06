package de.unibw.fusionvis.viewer;

import java.net.URL;

import jmetest.TutorialGuide.HelloMousePick;
import jmetest.flagrushtut.Lesson1;

import com.jme.app.SimpleGame;
import com.jme.bounding.BoundingBox;
import com.jme.bounding.BoundingSphere;
import com.jme.image.Image;
import com.jme.image.Texture;
import com.jme.light.PointLight;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import com.jme.scene.Line;
import com.jme.scene.Node;
import com.jme.scene.Spatial.LightCombineMode;
import com.jme.scene.shape.Cylinder;
import com.jme.scene.shape.Sphere;
import com.jme.scene.state.BlendState;
import com.jme.scene.state.MaterialState;
import com.jme.scene.state.TextureState;
import com.jme.system.DisplaySystem;
import com.jme.util.TextureManager;

public  class TestViewer extends SimpleGame {
	private static int numberOfPoints = 0;
	private final boolean cones = false;
	private Node gridNode;
	private Node helperNode;
	private Node root;
	private MaterialState materialState;
    /**
     * The amount of opacity (0 = fully transparent or invisible).
     */
    private float opacityAmount = 0.2f;
 
    public static void main(String[] args) {
    	TestViewer main = new TestViewer();
		main.setConfigShowMode(ConfigShowMode.NeverShow);
		main.start();
	}

	protected void simpleInitGame() {
		// set the background color to light gray
        display.getRenderer().setBackgroundColor(ColorRGBA.white);

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
        light.setAttenuate(false);
        light.setDiffuse(ColorRGBA.white);
        light.setSpecular(ColorRGBA.white);
        light.setLocation(new Vector3f(500.0f, 500.0f, 500.0f));
        light.setEnabled(true);
 
        // attach the light to a lightState
        lightState.attach(light);
        
        // our light
        final PointLight light2 = new PointLight();
        light2.setAttenuate(false);
        light2.setDiffuse(ColorRGBA.white);
        light2.setSpecular(ColorRGBA.white);
        light2.setLocation(new Vector3f(-500.0f, -500.0f, -500.0f));
        light2.setEnabled(true);
 
        // attach the light to a lightState
        lightState.attach(light2);
        
        // our light
        final PointLight light3 = new PointLight();
        light3.setAttenuate(false);
        light3.setDiffuse(ColorRGBA.white);
        light3.setSpecular(ColorRGBA.white);
        light3.setLocation(new Vector3f(-500.0f, 500.0f, -500.0f));
        light3.setEnabled(true);
 
        // attach the light to a lightState
        lightState.attach(light3);

		for (int i = 0; i < 27; i++) {
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
		
		URL fr = TestViewer.class.getClassLoader().getResource(
        "de/unibw/fusionvis/img/fr.gif" );
		URL ho = TestViewer.class.getClassLoader().getResource(
        "de/unibw/fusionvis/img/ho.gif" );
		URL frperceived = TestViewer.class.getClassLoader().getResource(
        "de/unibw/fusionvis/img/frperceived.gif" );
		URL hoperceived = TestViewer.class.getClassLoader().getResource(
        "de/unibw/fusionvis/img/hoperceived.gif" );
		
		
		Texture texture1 = TextureManager.loadTexture(fr,
				Texture.MinificationFilter.Trilinear,
				Texture.MagnificationFilter.Bilinear);
		TextureState friendly = display.getRenderer().createTextureState();
		friendly.setEnabled(true);
		friendly.setTexture(texture1);
		
		Texture texture2 = TextureManager.loadTexture(frperceived,
				Texture.MinificationFilter.Trilinear,
				Texture.MagnificationFilter.Bilinear);
		TextureState friendlyPerceived = display.getRenderer().createTextureState();
		friendlyPerceived.setEnabled(true);
		friendlyPerceived.setTexture(texture2);
		
		Texture texture3 = TextureManager.loadTexture(hoperceived,
				Texture.MinificationFilter.Trilinear,
				Texture.MagnificationFilter.Bilinear);
		TextureState hostilePerceived = display.getRenderer().createTextureState();
		hostilePerceived.setEnabled(true);
		hostilePerceived.setTexture(texture3);
		
		Texture texture4 = TextureManager.loadTexture(ho,
				Texture.MinificationFilter.Trilinear,
				Texture.MagnificationFilter.Bilinear);
		TextureState hostile = display.getRenderer().createTextureState();
		hostile.setEnabled(true);
		hostile.setTexture(texture4);
		
		Sphere sphere = new Sphere("Point " + numberOfPoints, new Vector3f(x,
				y, z), 5, 5, 5f);

		sphere.setModelBound(new BoundingSphere());
		sphere.updateModelBound();
		if (numberOfPoints % 4 == 1) {
			sphere.setSolidColor(ColorRGBA.red.clone());
			sphere.setRenderState(hostile);
		} else if (numberOfPoints % 4 == 0) {
			sphere.setSolidColor(ColorRGBA.blue.clone());
			sphere.setRenderState(friendly);
		} else if (numberOfPoints % 4 == 2) {
			sphere.setSolidColor(ColorRGBA.gray.clone());
			sphere.setRenderState(friendlyPerceived);
		} else {
			sphere.setSolidColor(ColorRGBA.orange.clone());
			sphere.setRenderState(hostilePerceived);
		}
		sphere.updateRenderState();
		
		
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