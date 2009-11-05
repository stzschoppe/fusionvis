package de.unibw.fusionvis.viewer;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.concurrent.Callable;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

import javax.swing.JPanel;

import com.jme.math.Vector3f;
import com.jme.renderer.Camera;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Node;
import com.jme.system.DisplaySystem;
import com.jme.system.canvas.JMECanvas;
import com.jme.system.canvas.SimpleCanvasImpl;
import com.jme.util.GameTaskQueue;
import com.jme.util.GameTaskQueueManager;
import com.jmex.awt.lwjgl.LWJGLAWTCanvasConstructor;

public class ViewerPanel extends JPanel{

    final Logger logger; //= Logger.getLogger(ViewerPanel.class.getName());
    static final long serialVersionUID = 1L;
    Preferences prefs = Preferences.userNodeForPackage(ViewerPanel.class);
    
    int width = 640, height = 480;
    MyImplementor impl;
    Canvas glCanvas;
    
    Node root;
    
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
        public MyImplementor(int iWidth, int iHeight)
        {
            super(iWidth, iHeight);
        }
        
        public void simpleSetup() 
        {        	
        	// Setze die Hintergrundfarbe fuer das OpenGL Fenster
            renderer.setBackgroundColor(ColorRGBA.black);
            
            // Setup Camera
            cam = impl.getCamera();
            cam.setFrustumPerspective(45.0f, (float) glCanvas.getWidth() / (float) glCanvas.getHeight(), 1, 10000);
            cam.setUp(new Vector3f(0,1,0));
            
            // rootNode einer globalen Knotenvariable zuweisen, um in "nested classes" darauf zuzugreifen
            root = rootNode;
        };

        public void simpleUpdate() 
        {
        	rootNode.updateGeometricState(0,false);
        	rootNode.updateWorldBound();
        	rootNode.updateRenderState();
        }
        
        @Override
        public void simpleRender() 
        {        	
        	
        }
    }

}