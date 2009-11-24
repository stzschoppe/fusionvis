package de.unibw.fusionvis.viewer;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Callable;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

import javax.swing.JPanel;

import com.jme.bounding.BoundingBox;
import com.jme.bounding.BoundingSphere;
import com.jme.image.Texture;
import com.jme.input.FirstPersonHandler;
import com.jme.input.InputHandler;
import com.jme.input.InputSystem;
import com.jme.input.KeyBindingManager;
import com.jme.input.KeyInput;
import com.jme.input.KeyboardLookHandler;
import com.jme.input.MouseInput;
import com.jme.input.MouseLookHandler;
import com.jme.input.action.InputAction;
import com.jme.input.action.InputActionEvent;
import com.jme.input.action.KeyStrafeDownAction;
import com.jme.input.action.KeyStrafeUpAction;
import com.jme.intersection.PickResults;
import com.jme.intersection.TrianglePickResults;
import com.jme.light.DirectionalLight;
import com.jme.light.PointLight;
import com.jme.math.Ray;
import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jme.renderer.Camera;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import com.jme.scene.Line;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.scene.Spatial.LightCombineMode;
import com.jme.scene.shape.Box;
import com.jme.scene.shape.Cylinder;
import com.jme.scene.shape.Sphere;
import com.jme.scene.state.BlendState;
import com.jme.scene.state.LightState;
import com.jme.scene.state.MaterialState;
import com.jme.scene.state.TextureState;
import com.jme.system.DisplaySystem;
import com.jme.system.canvas.JMECanvas;
import com.jme.system.canvas.SimpleCanvasImpl;
import com.jme.util.GameTaskQueue;
import com.jme.util.GameTaskQueueManager;
import com.jme.util.TextureManager;
import com.jmex.awt.input.AWTMouseInput;
import com.jmex.awt.lwjgl.LWJGLAWTCanvasConstructor;

import de.unibw.fusionvis.FusionVis;
import de.unibw.fusionvis.datamodel.DataSet;
import de.unibw.fusionvis.implementation.battlesimvis.BattleSimFusionPanel;
import de.unibw.fusionvis.implementation.battlesimvis.BattleSimMapper;
import de.unibw.fusionvis.importer.Importer;
import de.unibw.fusionvis.importer.ImporterPanel;
import de.unibw.fusionvis.mapper.Mapper;

public class ViewerPanel extends JPanel implements Observer{

    final Logger logger; //= Logger.getLogger(ViewerPanel.class.getName());
    static final long serialVersionUID = 1L;
    Preferences prefs = Preferences.userNodeForPackage(ViewerPanel.class);
    
    private ObservableSupport observableSupport;
    
    private int width = 640, height = 480;
    private MyImplementor impl;
    private Canvas glCanvas;
    
    private BattleSimFusionPanel fusionPanel; //XXX zu strikte Spezialisierung
    
    public ImporterPanel importerPanel;
    
    private Node root;
    private Node gridNode;
    private Node helperNode;
	private Node selectionNode;
	public Node dataNode;
    
	private InputHandler input;	
	private MouseLookHandler mouseLook;
	private KeyboardLookHandler keyboardLook;
    
	// private Camera cam;
	private int camSpeed = 25;
	
	public  String selectionId;
	public Camera cam;
	public Mapper mapper;
	private float unitSize;
	/**
	 * x und z Komponente geben an, wieviele Meter pro Unit darzustellen sind.
	 * Die Werte müssen gleich sein, da sonst ungewollte Verzerrungen bei 
	 * Bewegungstrichtern enstehen.
	 * y gibt an wieviele Sekunden pro Unit dargestellt werden.
	 */
	public final Vector3f maximalDimenVector3f = new Vector3f(10,
			2, 10);

    /**
     * Konstruktor
     * @param importerPanel Das ImporterPanel, mit dem beim selektieren zusammengearbeitet
     * werden soll.
     */
    public ViewerPanel(ImporterPanel importerPanel, float unitSize)
    {    
		mapper = new BattleSimMapper(getMaximalDimenVector3f(), unitSize);
    	this.unitSize = unitSize;
		observableSupport = new ObservableSupport();
    	observableSupport.addObserver(importerPanel);
    	this.logger = FusionVis.getLogger();
    	this.importerPanel = importerPanel;
    	importerPanel.observableSupportForFilter.addObserver(this);
    	importerPanel.observableSupportForSelection.addObserver(this);
        try 
        {
            init();	// initialisiere Glidefenster und GUI
            
            setVisible(true);

            // MAKE SURE YOU REPAINT SOMEHOW OR YOU WON'T SEE THE UPDATES...
            new Thread()
            {
                {
                    setDaemon(true);
                }
                public void run()
                {
                    try
                    {
                        while(true)
                        {
                            if(isVisible())
                            	glCanvas.repaint();
                            Thread.sleep(20);// bewirkt, dass die CPU nicht zu 100% belegt ist
                            
                            yield();// verhindert Flackern bei Mouseover von Fensterkomponenten wie "Schliessen-Knopf"
                        }
                    }
                    catch (Exception e) 
                    {
                        logger.throwing(this.getClass().toString(),"run()",e);
                    }
                }
            }.start();
        }
        catch (Exception ex) 
        {
            logger.throwing(this.getClass().toString(),"SceneEditorDemo()",ex);
        }
    }
    
    /**Funktion initialisiert das GUI und den glCanvas */
    public void init() throws Exception 
    {

		setLayout(new BorderLayout());
		add(getGlCanvas(), BorderLayout.CENTER);
		
		
		fusionPanel = new BattleSimFusionPanel(this);
		this.observableSupport.addObserver(fusionPanel);
		importerPanel.observableSupportForFilter.addObserver(fusionPanel);
		add(fusionPanel, BorderLayout.SOUTH);
                
        // Groesse vom Fenster
        setSize(new Dimension(800, 600));
        setMinimumSize(new Dimension(200, 150));

    }
    
    /**Funktion erstellt einen glCanvas, der kompatibel zum Swing GUI ist */
    public Canvas getGlCanvas()
    {
        if (glCanvas == null) 
        {
        	// Erzeugen eines Canvas fuer das 3D Glide Window
        	DisplaySystem display = DisplaySystem.getDisplaySystem();
        	display.registerCanvasConstructor("AWT", LWJGLAWTCanvasConstructor.class);
            glCanvas = (Canvas)display.createCanvas(width, height);
            glCanvas.setMinimumSize(new Dimension(100, 100));

            // Update der Komponenten falls das Fenster vergroessert/verkleinert wird
            glCanvas.addComponentListener(new ComponentAdapter() 
            {
                public void componentResized(ComponentEvent ce) 
                {
                    doResize();
                }
            });
            
            // Aktiviere Eingabemoeglichkeiten fuer Maus und Tastatur!     
            if (!KeyInput.isInited())
            {
	            KeyInput.setProvider(InputSystem.INPUT_SYSTEM_AWT);
	            KeyListener kl = (KeyListener) KeyInput.get();
	            glCanvas.addKeyListener(kl);
	        }

            if (!MouseInput.isInited())
                MouseInput.setProvider(InputSystem.INPUT_SYSTEM_AWT);    
            
            ((AWTMouseInput) MouseInput.get()).setDragOnly(true);
            glCanvas.addMouseListener((MouseListener) MouseInput.get());
        	glCanvas.addMouseWheelListener((MouseWheelListener) MouseInput.get());
            glCanvas.addMouseMotionListener((MouseMotionListener) MouseInput.get());    
            
            // Erstellen eines Implementors zur Behandlung der Update/Render Logik
            impl = new MyImplementor(width, height);
            ((JMECanvas) glCanvas).setImplementor(impl);

            Callable<?> exe = new Callable()
            {
                public Object call()
                {
                    forceUpdateToSize();
                    return null;
                }
            };
            GameTaskQueueManager.getManager().getQueue(GameTaskQueue.RENDER).enqueue(exe);
        }
        return glCanvas;
    }
    
    public void doResize() 
    {
        if (impl != null) 
        {
            impl.resizeCanvas(glCanvas.getWidth(), glCanvas.getHeight());
            if (impl.getCamera() != null) {
                Callable<?> exe = new Callable()
                {
                    public Object call()
                    {
                        impl.getCamera().setFrustumPerspective(45.0f,(float) glCanvas.getWidth()/(float) glCanvas.getHeight(),1,10000);
                        return null;
                    }
                };
                GameTaskQueueManager.getManager().getQueue(GameTaskQueue.RENDER).enqueue(exe);
            }
        }
    }

    public void forceUpdateToSize() 
    {
        glCanvas.setSize(glCanvas.getWidth(), glCanvas.getHeight() + 1);
        glCanvas.setSize(glCanvas.getWidth(), glCanvas.getHeight() - 1);
    }
        
	/**Klasse zum Implementieren der Szene */
    class MyImplementor extends SimpleCanvasImpl
    {
        private LightState lightState;

		public MyImplementor(int iWidth, int iHeight)
        {
            super(iWidth, iHeight);
        }
        
        public void simpleSetup() 
        {        	
        	// Setze die Hintergrundfarbe fuer das OpenGL Fenster
            renderer.setBackgroundColor(ColorRGBA.white);
            
            // Setup Camera
            cam = impl.getCamera();
            cam.setFrustumPerspective(45.0f, (float) glCanvas.getWidth() / (float) glCanvas.getHeight(), 1, 10000);
            //cam.setUp(new Vector3f(0,1,0));
            cam.lookAt(new Vector3f(150,0,150), Vector3f.UNIT_Y); 
            cam.getLocation().y = 20;
            cam.getLocation().x = -15;
            cam.getLocation().z = -15;
            cam.update();
            
            
            
            
            // rootNode einer globalen Knotenvariable zuweisen, um in "nested classes" darauf zuzugreifen
            root = rootNode;
            
            // Knoten "helperNode" erzeugen (fuer Grid)
            helperNode = new Node("helperNode");
            
            createGrid(new Vector2f(500,500), 10);// Grid erzeugen
            helperNode.attachChild(gridNode);// Grid an "helperNode" anhaengen
            helperNode.setLightCombineMode(LightCombineMode.Off);// eliminiere jeglichen Lichteinfluss
            
            // Knoten und Box für das Highlightning beim Mausklick erzeugen
            selectionNode = new Node("selectionNode");
            
            // Knoten für die anzuzeigenden Daten
            dataNode = new Node("dataNode");
            
            URL yellowURL = TestViewer.class.getClassLoader().getResource(
			"de/unibw/fusionvis/img/yellow.gif");
    		Texture texture1 = TextureManager.loadTexture(yellowURL,
    				Texture.MinificationFilter.Trilinear,
    				Texture.MagnificationFilter.Bilinear);
    		TextureState yellow = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
    		yellow.setEnabled(true);
    		yellow.setTexture(texture1);
            
            float alpha = 0.5f;
	        final BlendState alphaState = DisplaySystem.getDisplaySystem().getRenderer().createBlendState();
	        alphaState.setBlendEnabled(true);
	        alphaState.setSourceFunction(BlendState.SourceFunction.SourceAlpha);
	        alphaState.setDestinationFunction(BlendState.DestinationFunction.OneMinusSourceAlpha);
	        alphaState.setTestEnabled(true);
	        alphaState.setTestFunction(BlendState.TestFunction.GreaterThan);
	        alphaState.setEnabled(true);
	        
	        // the sphere material that will be modified to make the sphere
	        // look opaque then transparent then opaque and so on
	        MaterialState materialState = DisplaySystem.getDisplaySystem().getRenderer().createMaterialState();
	        materialState.setAmbient(new ColorRGBA(0.0f, 0.0f, 0.0f, alpha));
	        materialState.setDiffuse(new ColorRGBA(0.1f, 0.5f, 0.8f, alpha));
	        materialState.setSpecular(new ColorRGBA(1.0f, 1.0f, 1.0f, alpha));
	        materialState.setShininess(128.0f);
	        materialState.setEmissive(new ColorRGBA(0.0f, 0.0f, 0.0f, alpha));
	        materialState.setEnabled(true);
	 
	        // IMPORTANT: this is used to handle the internal sphere faces when
	        // setting them to transparent, try commenting this line to see what
	        // happens
	        materialState.setMaterialFace(MaterialState.MaterialFace.FrontAndBack);

            Box box = new Box("selectionBox", new Vector3f(0, 0, 0), unitSize, unitSize, unitSize);
            box.setModelBound(new BoundingSphere());
            box.updateModelBound();
            
            box.setRenderState(materialState);
            box.setRenderState(alphaState);
            box.setRenderState(yellow);
            box.updateRenderState();
            box.setRenderQueueMode(Renderer.QUEUE_TRANSPARENT);
			selectionNode.attachChild(box);
            
            //Licht erzeugen
            // Erzeugen eines LightState an welchen alle Pointlights und Directional Lights
            // hinzugefuegt werden muessen, damit sie sichtbar sind
    		lightState = DisplaySystem.getDisplaySystem().getRenderer().createLightState();
    		lightState.detachAll();
    		lightState.setEnabled(true);
    		root.setRenderState(lightState);
    		root.updateRenderState();
    		
            ColorRGBA diffuseRGBA = new ColorRGBA(ColorRGBA.white);
	        
	        ColorRGBA ambientRGBA = new ColorRGBA(ColorRGBA.white);
			
			DirectionalLight dl = new DirectionalLight();
			dl.setAttenuate(false);
    	    dl.setDirection(new Vector3f(-0.5f,-1,-0.8f));
    	    dl.setDiffuse(diffuseRGBA);
            dl.setAmbient(ambientRGBA);
            dl.setSpecular(new ColorRGBA(0.0f, 0.0f, 0.0f, 1.0f));
    	    dl.setEnabled(true);	   
    	    dl.setShadowCaster(true);
    	    
    	    final PointLight light = new PointLight();
            light.setAttenuate(false);
            light.setDiffuse(ColorRGBA.white);
            light.setSpecular(ColorRGBA.white);
            light.setLocation(new Vector3f(1500.0f, 1500.0f, 1500.0f));
            light.setEnabled(true);
     
            // attach the light to a lightState
            lightState.attach(light);
            
            // our light
            final PointLight light2 = new PointLight();
            light2.setAttenuate(false);
            light2.setDiffuse(ColorRGBA.white);
            light2.setSpecular(ColorRGBA.white);
            light2.setLocation(new Vector3f(-1500.0f, -1500.0f, -1500.0f));
            light2.setEnabled(true);
     
            // attach the light to a lightState
            lightState.attach(light2);
            
            // our light
            final PointLight light3 = new PointLight();
            light3.setAttenuate(false);
            light3.setDiffuse(ColorRGBA.white);
            light3.setSpecular(ColorRGBA.white);
            light3.setLocation(new Vector3f(-1500.0f, 1500.0f, -1500.0f));
            light3.setEnabled(true);
     
            // attach the light to a lightState
            lightState.attach(light3);
    	    
    	    new TrianglePickResults(); 
            
            
            root.attachChild(helperNode);// "helperNode" an "root" anhaengen
            
         // Maus- und Tastatureingaben aktivieren
            createInput();
        };

        public void simpleUpdate() 
        {
        	rootNode.updateGeometricState(0,false);
        	rootNode.updateWorldBound();
        	rootNode.updateRenderState();
        	
        	
				InputSystem.update();
				if (input != null)
					input.update(tpf);

        }
        
        @Override
        public void simpleRender() 
        {        	
        	
        }
    }
    
    /**Funktion erzeugt Rays fuer Mausklicks im glCanvas */
		public void mousePicking()
		{
	
			int xScreen = glCanvas.getMousePosition().x;
			int yScreen = glCanvas.getHeight()-glCanvas.getMousePosition().y;
			
			Vector2f screenPos = new Vector2f(xScreen, yScreen);
			Vector3f worldCoordsStart = DisplaySystem.getDisplaySystem().getWorldCoordinates(screenPos, 0);
			Vector3f worldCoordsEnd = DisplaySystem.getDisplaySystem().getWorldCoordinates(screenPos, 1);
			
			Vector3f startPoint = worldCoordsStart;
			Vector3f endPoint = worldCoordsEnd.subtractLocal(worldCoordsStart);
			
			Ray ray = new Ray(startPoint,endPoint.subtractLocal(startPoint));
			
	//        // Hilfslinien um MouseRays zu visualisieren
	//		ColorRGBA[] col = new ColorRGBA[2];
	//		col[0] = ColorRGBA.orange;
	//		Line line = new Line("line",new Vector3f[]{startPoint,endPoint.subtractLocal(startPoint)},null,col,null);
	//		line.setLightCombineMode(LightCombineMode.Off);
	//        root.attachChild(line);  
			
			PickResults pr = new TrianglePickResults();
			pr.setCheckDistance(true);
			
			root.findPick(ray, pr);
			if (pr.getNumber() > 0)
			{
				float distance = pr.getPickData(0).getDistance()*3;
				deselect();
				
				for (int i = 0; i < pr.getNumber(); i++)
				{
					if (pr.getPickData(i).getDistance() < distance)
					{
						Spatial pickData = pr.getPickData(i).getTargetMesh();
						distance = pr.getPickData(i).getDistance();
						select(pickData);
					}
				}
			}
		
		}

	public void select(Spatial pickData) {
		if(!(pickData instanceof Sphere)) return;
		Spatial selectionBox = selectionNode.getChild("selectionBox");
		selectionBox.setLocalTranslation((pickData.getLocalTranslation()));
		selectionId = pickData.getName();
		root.attachChild(selectionNode);
		observableSupport.markAndNotify(pickData.getName());
	}

	public void deselect() {
		root.detachChild(selectionNode);
		selectionId = null;
	}

	public void createInput()
	{
		// hohle die gegebene Camera!
		cam = impl.getCamera();		 
		// weise dem FirstPersonHandler die cam zu
		input = new FirstPersonHandler(cam,camSpeed, 1);
		
		/* An einem FirstPersonHandler (FPH) sind genau 2 Handler angeschlossen:
		 * 1 MouseLookHandler und 1 KeyboardLookHandler
		 * Der MouseHandler befindet sich an Position 0, der Keyboardhandler an Position 1
		 */
				                    	
		// speichere MouseLook- und KeyboardLookHandler zuerst global
		mouseLook = (MouseLookHandler) input.getFromAttachedHandlers(0);
		keyboardLook = (KeyboardLookHandler) input.getFromAttachedHandlers(1);
		
		/* Weise eine neue Tastenfunktion zu: C fuer "Kamera runter" und SPACE fuer "Kamera rauf".
		 * Steuerung nun angenehmer, da das vormalige Binding "Z" viel zu weit entfernt war;
		 * vgl. "Spectator Modus" in Unreal Tournament 2004
		 */
		KeyBindingManager kmanager = KeyBindingManager.getKeyBindingManager();
		kmanager.add("Strafe Down",KeyInput.KEY_C);
		kmanager.add("Strafe Up",KeyInput.KEY_SPACE);
		
		// fuege neue Aktionen hinzu
		keyboardLook.addAction(new KeyStrafeDownAction(cam,camSpeed),"Strafe Down",true);
		keyboardLook.addAction(new KeyStrafeUpAction(cam,camSpeed),"Strafe Up",true);
		
		// setze die neue Geschwindigkeit fuer die Tastatur
		keyboardLook.setActionSpeed(camSpeed);
		                        	
		input.removeAllFromAttachedHandlers();// entferne alle vorhandenen Handler vom input
		
		// Erstelle einen neuen FirstPersonHandler aus den modifizierten Handlern
		input.addToAttachedHandlers(keyboardLook);
		input.addToAttachedHandlers(mouseLook);
		
		InputAction buttonAction = new InputAction() 
	    {
			/* Der MouseLook ist noch fuer alle Maustasten aktiv; wir wollen
			 * diesen aber nur fuer die mittlere Maustaste einstellen und muessen
			 * ihn deshalb von Maustaste 0 (linke) und Maustaste 1 (rechte)
			 * zuerst entfernen!
			 */
			
	    	boolean mouselookAttached = true;
			
	        public void performAction( InputActionEvent iae ) 
	        {
	        	if(iae.getTriggerPressed())	// ...Events bei gedrueckten Maustasten
	        	{
	        		if(iae.getTriggerIndex()==0)	// Linke Maustaste gedrueckt
	        		{
	        			// Falls noch ein MouseLookHandler dranhaengt diesen entfernen
	        			if(mouselookAttached)
	        			{	                            				
	        				// entferne MouseLookHandler (wird nur 1x ausgefuehrt)
	        				input.removeFromAttachedHandlers(input.getFromAttachedHandlers(1));
	            			mouselookAttached = false;
	        			}
	        			else
	        			{
	        				// System.out.println("MouseLook ist inaktiv!");
	        			}
	        			mousePicking();
	        		}
	        		if(iae.getTriggerIndex()==1)	// Rechte Maustaste gedrueckt
	        		{
	        			// Falls noch ein MouseLookHandler dranhaengt diesen entfernen
	        			if(mouselookAttached)
	        			{	                            				
	        				// entferne MouseLookHandler (wird nur 1x ausgefuehrt)
	        				input.removeFromAttachedHandlers(input.getFromAttachedHandlers(1));
	            			mouselookAttached = false;
	        			}
	        			else
	        			{
	        				// System.out.println("MouseLook ist inaktiv!");
	        			}			
	        		}
	        		if(iae.getTriggerIndex()==2)	// Mittlere Maustaste gedrueckt
	        		{
	        			/* Fokus wird wieder auf das glCanvas gelegt damit der MouseLook
	        			 * darin wieder funktioniert; ansonsten bleibt die Kamera starr nach
	        			 * einem Klick auf Swingkomponenten
	        			 */
	        			glCanvas.requestFocus();
	        			
	        			// Beim ersten Klick soll der MouseLookHandler wieder an den "input" angehaengt werden 
	        			if(!mouselookAttached)
	        			{	
	            			input.addToAttachedHandlers(mouseLook);
	            			mouselookAttached = true;                			
	        			}
	        			else
	        			{
	        				
	        			}
	        		}
	        	}
	        	else
	        	{
	        		if(iae.getTriggerIndex()==0)	// Linke Maustaste losgelassen
	        		{
	        			
	        		}
	        		if(iae.getTriggerIndex()==1)	// Rechte Maustaste losgelassen
	        		{
	        			
	        		}
	        		if(iae.getTriggerIndex()==2)	// Mittlere Maustaste losgelassen
	        		{
	
	        		}
	        	}                	
	        }
	    };
		
	    // Zuerst allen Maustasten die Funktionen "Pressed" und "Released" zuordnen! Falls man diese Zeile 
	    // weglaesst funktioniert "Released" bei all jenen Buttons nicht, die REPEAT auf "true" haben!
	    input.addAction(buttonAction,InputHandler.DEVICE_MOUSE,InputHandler.BUTTON_ALL,InputHandler.AXIS_NONE,false);
	    
	    // Anschliessend erlauben wir Maustaste 1 (rechte) und 2 (mittlere) einen Repeat
		input.addAction(buttonAction,InputHandler.DEVICE_MOUSE,1,InputHandler.AXIS_NONE,true);
		input.addAction(buttonAction,InputHandler.DEVICE_MOUSE,2,InputHandler.AXIS_NONE,true);
	}

	/**
	 * Erzeugt ein Gitter mit angegeben ausmaßen in x und z Richtung
	 * und angegebener Schrittweite.
	 * @param dimension Außmaß  (0|0)->(x|z) in Unit 
	 * @param step Abstand der Linien in Unit
	 */
	public void createGrid(Vector2f dimension, float step) 
	{
		gridNode = new Node("gridNode");
		//gridNode.setLightCombineMode(LightCombineMode.Off);
		float max = Math.max(dimension.x, dimension.y);
		
		/* Wichtig beim Erstellen des Grids ist, dass auch dessen Linien ModelBounds erhalten.
	     * Wenn das nicht gemacht wird werden die ModelBounds von hinzugefuegten Objekten
	     * (z.B. Box) vererbt und Fehler koennen auftreten wie beispielsweise:
	     * Grid verschwindet sobald die Box ausserhalb des Sichtfelds ist. 
	     */
	
		// Gitternetz
	    for (float x = step; x <= max; x=x+step) {
	        Line l = new Line("xLine" + x, new Vector3f[]{new Vector3f((float) x, 0f, 0f), new Vector3f((float) x, 0f, max)}, null, null, null);
	        l.setSolidColor(ColorRGBA.darkGray);
	        l.setModelBound(new BoundingBox());
	        l.updateModelBound();
	        l.setCastsShadows(false);
	        gridNode.attachChild(l);
	    }
	
	    for (float z = step; z <= max; z=z+step) {
	        Line l = new Line("zLine" + z, new Vector3f[]{new Vector3f(0f, 0f, (float) z), new Vector3f(max, 0f, (float) z)}, null, null, null);
	        l.setSolidColor(ColorRGBA.darkGray);
	        l.setModelBound(new BoundingBox());
	        l.updateModelBound();
	        l.setCastsShadows(false);
	        gridNode.attachChild(l);
	    }
	    
	    // rote x-Achse
	    final Line xAxis = new Line("xAxis", new Vector3f[]{new Vector3f(0f, 0f, 0f), new Vector3f(max, 0f, 0f)}, null, null, null);
	    xAxis.setModelBound(new BoundingBox());
	    xAxis.updateModelBound();
	    xAxis.setSolidColor(ColorRGBA.red);
	    xAxis.setCastsShadows(false);
	    gridNode.attachChild(xAxis);
	
	    // blaue z-Achse
	    final Line zAxis = new Line("zAxis", new Vector3f[]{new Vector3f(0f, 0f, 0f), new Vector3f(0f, 0f, max)}, null, null, null);
	    zAxis.setModelBound(new BoundingBox());
	    zAxis.updateModelBound();
	    zAxis.setSolidColor(ColorRGBA.blue);
	    zAxis.setCastsShadows(false);            
	    gridNode.attachChild(zAxis);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof DataSet) { // Datenimport
			DataSet dataSet = (DataSet) arg;
			root.detachChild(dataNode);
			helperNode.detachChild(gridNode);
			
			dataNode = mapper.getDataRoot(dataSet);
			createGrid(new Vector2f(mapper.getSize().x/getMaximalDimenVector3f().x, mapper.getSize().y/getMaximalDimenVector3f().z), 10);
			
			helperNode.attachChild(gridNode);
			root.attachChild(dataNode);
		} else if (arg instanceof String) { //Selection
			deselect();
			String id = (String)arg;
			Spatial toSelect = ((Node)dataNode.getChild(id)).getChild(id);
			if(toSelect!=null)
				select(toSelect);
			else deselect();
		}
	}
	
	/**
	 * @return the maximalDimenVector3f
	 */
	public Vector3f getMaximalDimenVector3f() {
		return maximalDimenVector3f;
	}

	public class ObservableSupport extends Observable {
		public ObservableSupport() {
		}
		public void markAndNotify(Object o){
			setChanged();
			notifyObservers(o);
		}
	}

}