import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas; // !! CU AWT
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.GL2;
import org.javatuples.Triplet;
import org.jetbrains.annotations.*;



//https://en.wikiversity.org/wiki/Computer_graphics/2013-2014/Laboratory_2

import javax.swing.*;
import java.awt.*;

public class MainFrame
        extends JFrame
        implements GLEventListener
{

    public MainFrame()
    {
        super("Java OpenGL");

        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setSize(800, 600);

        // This method will be explained later
        this.initializeJogl();

        this.setVisible(true);
    }

    public Animator animator;

    private void initializeJogl() {

        // Obtaining a reference to the default GL profile
        GLProfile glProfile = GLProfile.getDefault();
        // Creating an object to manipulate OpenGL parameters.
        GLCapabilities capabilities = new GLCapabilities(glProfile);

        // Setting some OpenGL parameters.
        capabilities.setHardwareAccelerated(true);
        capabilities.setDoubleBuffered(true);

        // Creating an OpenGL display widget -- canvas.
        this.canvas = new GLCanvas();

        // Adding the canvas in the center of the frame.
        this.getContentPane().add(this.canvas);

        // Adding an OpenGL event listener to the canvas.
        this.canvas.addGLEventListener(this);

        this.animator = new Animator();

        this.animator.add(this.canvas);

        this.animator.start();
    }


    public GLCanvas canvas;
    @SuppressWarnings("unused")
    double equation[] = { 1f, 1f, 1f, 1f};

    static Triplet<Float, Float, Float> colorRed = Triplet.with(0.7f, 0.1f, 0.0f);
    static Triplet<Float, Float, Float> colorBlue = Triplet.with(0.0f, 0.8f, 0.0f);
    static Triplet<Float, Float, Float> colorGreen = Triplet.with(0.0f, 0.0f, 0.7f);
    static Triplet<Float, Float, Float> colorWhite = Triplet.with(0.8f, 0.8f, 0.9f);

    static Triplet<Float, Float, Float> colorYellow = Triplet.with(0.7f, 0.8f, 0.0f);

    private GLU glu;
    private float sunX = 0;
    private float sunY = 0;
    private float sunRadius = 0.1f;
    private int houseDisplayList;

    public void init(GLAutoDrawable canvas) {
        GL2 gl = canvas.getGL().getGL2();
        glu = new GLU();

        houseDisplayList = gl.glGenLists(1);
        gl.glNewList(houseDisplayList, GL2.GL_COMPILE);
        drawHouse(gl);
        gl.glEndList();
    }

    private void drawHouse(GL2 gl) {
        gl.glBegin(GL2.GL_TRIANGLES);

        // Roof
        gl.glColor3f(colorGreen.getValue0(), colorGreen.getValue1(), colorGreen.getValue2());
        gl.glVertex2f(-0.5f, 0.5f);
        gl.glVertex2f(0.5f, 0.5f);
        gl.glVertex2f(0.0f, 0.75f);

        // Left wall
        gl.glColor3f(colorBlue.getValue0(), colorBlue.getValue1(), colorBlue.getValue2());
        gl.glVertex2f(-0.5f, 0.5f);
        gl.glVertex2f(-0.5f, -0.5f);
        gl.glVertex2f(0.0f, 0.0f);

        // Right wall
        gl.glColor3f(colorRed.getValue0(), colorRed.getValue1(), colorRed.getValue2());
        gl.glVertex2f(0.5f, 0.5f);
        gl.glVertex2f(0.5f, -0.5f);
        gl.glVertex2f(0.0f, 0.0f);

        // Floor
        gl.glColor3f(colorYellow.getValue0(), colorYellow.getValue1(), colorYellow.getValue2());
        gl.glVertex2f(-0.5f, -0.5f);
        gl.glVertex2f(0.5f, -0.5f);
        gl.glVertex2f(0.0f, 0.0f);

        gl.glEnd();
    }


    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }


    public void display(GLAutoDrawable canvas) {
        GL2 gl = canvas.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);

        // Draw the sun
        gl.glColor3f(colorYellow.getValue0(), colorYellow.getValue1(), colorYellow.getValue2());
        gl.glBegin(GL2.GL_POLYGON);
        for (int i = 0; i < 360; i++) {
            double angle = Math.toRadians(i);
            gl.glVertex2d(sunX + sunRadius * Math.cos(angle), sunY + sunRadius * Math.sin(angle));
        }
        gl.glEnd();

        // Draw the house
        gl.glCallList(houseDisplayList);

        // Update the sun position
        sunX += 0.01f;
        if (sunX > 1.0f) {
            sunX = -1.0f;
        }
    }





    public void reshape(GLAutoDrawable canvas, int left, int top, int width, int height) {
        GL2 gl = canvas.getGL().getGL2();
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluOrtho2D(-1.0, 1.0, -1.0, 1.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    @SuppressWarnings("unused")
    public void displayChanged(GLAutoDrawable canvas, boolean modeChanged, boolean deviceChanged)
    {}
}
