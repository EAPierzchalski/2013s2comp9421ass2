package ass2.spec;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * COMMENT: Comment HeightMap 
 *
 * @author malcolmr
 */
public class Terrain {
    private Logger logger = Logger.getLogger(Terrain.class.getName());
    private Dimension mySize;
    private double[][] myAltitude;
    private List<Tree> myTrees;
    private List<Road> myRoads;
    private float[] mySunlight;

    /**
     * Create a new terrain
     *
     * @param width The number of vertices in the x-direction
     * @param depth The number of vertices in the z-direction
     */
    public Terrain(int width, int depth) {
        mySize = new Dimension(width, depth);
        myAltitude = new double[width][depth];
        myTrees = new ArrayList<Tree>();
        myRoads = new ArrayList<Road>();
        mySunlight = new float[3];
    }
    
    public Terrain(Dimension size) {
        this(size.width, size.height);
    }

    public Dimension size() {
        return mySize;
    }

    public List<Tree> trees() {
        return myTrees;
    }

    public List<Road> roads() {
        return myRoads;
    }

    public float[] getSunlight() {
        return mySunlight;
    }

    /**
     * Set the sunlight direction. 
     * 
     * Note: the sun should be treated as a directional light, without a position
     * 
     * @param dx
     * @param dy
     * @param dz
     */
    public void setSunlightDir(float dx, float dy, float dz) {
        mySunlight[0] = dx;
        mySunlight[1] = dy;
        mySunlight[2] = dz;        
    }
    
    /**
     * Resize the terrain, copying any old altitudes. 
     * 
     * @param width
     * @param height
     */
    public void setSize(int width, int height) {
        mySize = new Dimension(width, height);
        double[][] oldAlt = myAltitude;
        myAltitude = new double[width][height];
        
        for (int i = 0; i < width && i < oldAlt.length; i++) {
            for (int j = 0; j < height && j < oldAlt[i].length; j++) {
                myAltitude[i][j] = oldAlt[i][j];
            }
        }
    }

    /**
     * Get the altitude at a grid point
     * 
     * @param x
     * @param z
     * @return
     */
    public double getGridAltitude(int x, int z) {
        return myAltitude[x][z];
    }

    /**
     * Set the altitude at a grid point
     * 
     * @param x
     * @param z
     * @return
     */
    public void setGridAltitude(int x, int z, double h) {
        myAltitude[x][z] = h;
    }

    /**
     * Get the altitude at an arbitrary point. 
     * Non-integer points should be interpolated from neighbouring grid points
     * 
     * TO BE COMPLETED
     * 
     * @param x
     * @param z
     * @return
     */
    public double altitude(double x, double z) {
        double altitude = 0, altitudeX1Z1, altitudeX1Z2, altitudeX2Z1, altitudeX2Z2;
        int x1, x2, z1, z2;
        double altitudeX1X2Z1, altitudeX1X2Z2, dx, dz;

        x1 = (int) x;
        dx = x - x1;
        if(x1 == x){
            x2 = x1;
        } else {
            x2 = x1 + 1;
        }
        logger.info("x1 = " + x1 + "; x2 = " + x2 + "; dx = " + dx);

        z1 = (int) z;
        dz = z - z1;
        if(z1 == z){
            z2 = z1;
        } else {
            z2 = z1 + 1;
        }
        logger.info("z1 = " + z1 + "; z2 = " + z2 + "; dz = " + dz);

        altitudeX1Z1 = myAltitude[x1][z1];
        altitudeX1Z2 = myAltitude[x1][z2];
        altitudeX2Z1 = myAltitude[x2][z1];
        altitudeX2Z2 = myAltitude[x2][z2];

        altitudeX1X2Z1 = dx * altitudeX1Z1 + (1 - dx) * altitudeX2Z1;
        altitudeX1X2Z2 = dx * altitudeX1Z2 + (1 - dx) * altitudeX2Z2;

        altitude = dz * altitudeX1X2Z1 + (1 - dz) * altitudeX1X2Z2;

        return altitude;
    }

    /**
     * Add a tree at the specified (x,z) point. 
     * The tree's y coordinate is calculated from the altitude of the terrain at that point.
     * 
     * @param x
     * @param z
     */
    public void addTree(double x, double z) {
        double y = altitude(x, z);
        Tree tree = new Tree(x, y, z);
        myTrees.add(tree);
    }


    /**
     * Add a road. 
     * 
     * @param width
     * @param spine
     */
    public void addRoad(double width, double[] spine) {
        Road road = new Road(width, spine);
        myRoads.add(road);        
    }


}
