package drivingExample;

import ass2.spec.Tree;
import com.jogamp.opengl.util.FPSAnimator;

import javax.media.opengl.*;
import javax.media.opengl.awt.GLJPanel;
import javax.media.opengl.glu.GLU;
import javax.swing.*;
import java.awt.*;

//import pong3d.Keyboard;

/**
 * COMMENT: Comment DrivingGame 
 *
 * @author malcolmr
 */
public class DrivingGame extends JFrame implements GLEventListener {

    private static final double CAMERA_DIST = 5;
    private static final float FOV = 90;
//    private Car myCar;
    private Tree myTree;
    private Floor myFloor;
    private double verticalRotation = 30;
    private double horizontalRotation = 120;
    private boolean upHeld = false;
    private boolean downHeld = false;

    public void setUpHeld(boolean upHeld) {
        this.upHeld = upHeld;
    }

    public void setDownHeld(boolean downHeld) {
        this.downHeld = downHeld;
    }

    public void setLeftHeld(boolean leftHeld) {
        this.leftHeld = leftHeld;
    }

    public void setRightHeld(boolean rightHeld) {
        this.rightHeld = rightHeld;
    }

    private boolean leftHeld = false;
    private boolean rightHeld = false;

    public DrivingGame() {
        super("Driving Game!");   
        
        myFloor = new Floor(10, 10);
        myTree = new Tree(0,0,0);
//        myCar = new Car();

    }
    
    private void run() {
        GLProfile glprofile = GLProfile.getDefault();
        GLCapabilities glcapabilities = new GLCapabilities(glprofile);
        GLJPanel panel = new GLJPanel(glcapabilities);
        MyKeyboard myKeyboard = new MyKeyboard(this);

        panel.addGLEventListener(this);
        panel.addKeyListener(myKeyboard);
        panel.setFocusable(true);
        panel.requestFocus();

        // Add an animator to call 'display' at 60fps        
        FPSAnimator animator = new FPSAnimator(60);
        animator.add(panel);
        animator.start();

        getContentPane().add(panel, BorderLayout.CENTER);
        setSize(800, 800);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);        
    }
    
    
    
    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        
        gl.glClearColor(0.4f, 0.4f, 1, 1);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();

//        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        gl.glColor4d(0,0,0,1);
        
        gl.glTranslated(0,0, -CAMERA_DIST);
        if(upHeld) verticalRotationUp();
        if(downHeld) verticalRotationDown();
        if(leftHeld) horizontalRotationUp();
        if(rightHeld) horizontalRotationDown();
        gl.glRotated(verticalRotation, 1, 0, 0);
        gl.glRotated(horizontalRotation, 0, 1, 0);
        
        myFloor.draw(gl);
//        myCar.draw(gl);
        myTree.draw(gl);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width,
            int height) {
        GL2 gl = drawable.getGL().getGL2();
        
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        
//        gl.glOrtho(-8, 8, -8, 8, -8, 8);
        
        double aspect = 1.0 * width / height;
        
        GLU glu = new GLU();
        glu.gluPerspective(FOV, aspect, 1, CAMERA_DIST + 2);
    }

    public static void main(String[] args) {
        DrivingGame game = new DrivingGame();
        
        game.run();
    }

    public void verticalRotationUp(){
        verticalRotation++;
    }

    public void verticalRotationDown(){
        verticalRotation--;
    }

    public void horizontalRotationUp(){
        horizontalRotation++;
    }

    public void horizontalRotationDown(){
        horizontalRotation--;
    }
}
