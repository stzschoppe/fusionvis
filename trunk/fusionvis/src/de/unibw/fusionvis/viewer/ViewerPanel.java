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
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Callable;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

import javax.swing.JPanel;

import com.jme.bounding.BoundingBox;
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
import com.jme.light.DirectionalLight;
import com.jme.math.Vector3f;
import com.jme.renderer.Camera;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Line;
import com.jme.scene.Node;
import com.jme.scene.Spatial.LightCombineMode;
import com.jme.scene.state.LightState;
import com.jme.system.DisplaySystem;
import com.jme.system.canvas.JMECanvas;
import com.jme.system.canvas.SimpleCanvasImpl;
import com.jme.util.GameTaskQueue;
import com.jme.util.GameTaskQueueManager;
import com.jmex.awt.input.AWTMouseInput;
import com.jmex.awt.lwjgl.LWJGLAWTCanvasConstructor;

import de.unibw.fusionvis.importer.Importer;

public class ViewerPanel extends JPanel implements Observer{

    final Logger logger; //= Logger.getLogger(ViewerPanel.class.getName());
    static final long serialVersionUID = 1L;
    Preferences prefs = Preferences.userNodeForPackage(ViewerPanel.class);
    
    int width = 640, height = 480;
    MyImplementor impl;
    Canvas glCanvas;
    
    Node root;
    Node gridNode;
    Node helperNode;
    
    InputHandler input;	
	MouseLookHandler mouseLook;
	KeyboardLookHandler keyboardLook;
    
    Camera cam;
	int camSpeed = 100;

    public ViewerPanel(final Logger logger)
    {    	
    	this.logger = logger;
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
                
        // Groesse vom Fenster
        setSize(new Dimension(800, 600));

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
            cam.setUp(new Vector3f(0,1,0));
            cam.getLocation().y = 50;
            cam.getLocation().x = 0;
            cam.getLocation().z = 0;
            
            
            
            // rootNode einer globalen Knotenvariable zuweisen, um in "nested classes" darauf zuzugreifen
            root = rootNode;
            
            // Knoten "helperNode" erzeugen (fuer Grid)
            helperNode = new Node("helperNode");
            
            createGrid();// Grid erzeugen
            helperNode.attachChild(gridNode);// Grid an "helperNode" anhaengen
            helperNode.setLightCombineMode(LightCombineMode.Off);// eliminiere jeglichen Lichteinfluss
            
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
    	    dl.setDirection(new Vector3f(-0.5f,-1,-0.8f));
    	    dl.setDiffuse(diffuseRGBA);
            dl.setAmbient(ambientRGBA);
            dl.setSpecular(new ColorRGBA(0.0f, 0.0f, 0.0f, 1.0f));
    	    dl.setEnabled(true);	   
    	    dl.setShadowCaster(true);
    	    
    	    lightState.attach(dl);
            
            
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

        // blaue z-Achse
        final Line zAxis = new Line("zAxis", new Vector3f[]{new Vector3f(0f, 0f, -300f), new Vector3f(0f, 0f, 300f)}, null, null, null);
        zAxis.setModelBound(new BoundingBox());
        zAxis.updateModelBound();
        zAxis.setSolidColor(ColorRGBA.blue);
        zAxis.setCastsShadows(false);            
        gridNode.attachChild(zAxis);
    }

	@Override
	public void update(Observable o, Object arg) {
		Importer importer = (Importer)o;
		Node dataNode = importer.getDataNode();
		root.detachAllChildren();
		root.attachChild(helperNode);
		root.attachChild(dataNode);
		//System.out.println(importer.getDataNode(DisplaySystem.getDisplaySystem()).getChildren());
		
	}

}