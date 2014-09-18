package controller;

import java.util.ArrayList;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import simulationObjects.Patch;

public class GridManager extends GridPane {

    private Patch[][] grid;
    private int[] xDelta = { -1, -1, -1, 0, 0, 1, 1, 1 };
    private int[] yDelta = { -1, 0, 1, -1, 1, -1, 0, 1 };
    private int gWidth;
    private int gHeight;

    // private String patchType;
    // {4,8} indicates adjacent type to be 4 or 8 blocks around
    // private int adjacentType;

    /**
     * Constructs the GridManager, initiates private variables
     * 
     * @param width
     *            of the grid
     * @param height
     *            of the grid
     */
    public GridManager(int width, int height) {
	grid = new Patch[width][height];
	gWidth = width;
	gHeight = height;
    }

    /**
     * Makes a step in the simulation, updates everything sequentially
     */
    public void step() {
	for (Patch[] row : grid) {
	    for (Patch p : row) {
		p.update();
	    }
	}
    }

    /**
     * Adds patch in specific location in the Grid
     * 
     * @param patch
     *            to be added
     * @param xCoord
     *            in grid
     * @param yCoord
     *            in grid
     */
    public void addPatchAtPoint(Patch patch) {
	grid[patch.getGridX()][patch.getGridY()] = patch;
	this.add(patch, patch.getGridX(), patch.getGridY());
    }

    /**
     * Remove cell from specific location in the Grid
     * 
     * @param xCoord
     *            in grid
     * @param yCoord
     *            in grid
     */
    public void removeCellinPatch(int xCoord, int yCoord) {
	grid[xCoord][yCoord].removeCell();
    }

    // TODO Needs to be updated for unique boundary conditions.
    /**
     * Retrieves all neighboring patches around a point
     * 
     * @param xCoord
     * @param yCoord
     * @return
     */
    public ArrayList<Patch> getNeighborsAround(int xCoord, int yCoord) {
	ArrayList<Patch> neighbors = new ArrayList<>();
	for (int i = 0; i < xDelta.length; i++) {
	    int nextX = xCoord + xDelta[i];
	    int nextY = yCoord + yDelta[i];

	    if (!isOutOfBounds(nextX, nextY)) {
		neighbors.add(grid[nextX][nextY]);
	    }
	}
	return neighbors;
    }

    /**
     * Checks if a location is not in the grid
     * 
     * @param xCoord
     *            in grid
     * @param yCoord
     *            in grid
     * @return true if out of bounds, false otherwise
     */
    private boolean isOutOfBounds(int xCoord, int yCoord) {
	return xCoord > gWidth - 1 || xCoord < 0 || yCoord > gHeight - 1
		|| yCoord < 0;
    }

    public Patch getPatchAtPoint(int i, int j) {
	return grid[i][j];
    }
    
    public int getGridWidth(){
        return gWidth;
    }

}
