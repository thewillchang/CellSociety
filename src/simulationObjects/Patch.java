package simulationObjects;

import java.util.ArrayList;
import javafx.scene.Group;
import controller.GridManager;

public class Patch extends Group {
    protected Cell myCell;
    protected int xCoord;
    protected int yCoord;
    private GridManager manager;
    protected ArrayList<Patch> myNeighbors;

    protected State myState;

    private int myPreviousCellState;

    // protected relativePosition myNorth, mySouth, myWest, myEast,
    // myNorthWest, myNorthEast, mySouthWest, mySouthEast;

    public Patch(int x, int y, GridManager m) {
	super();
	xCoord = x;
	yCoord = y;
	manager = m;
	myState = State.EMPTY;

    }

    protected enum State {
	EMPTY, GENERATING, OCCUPIED, EMPTYING
    }

    public void getNeighbors() {
	myNeighbors = manager.getNeighborsAround(xCoord, yCoord);
    }

    public Cell getCell() {
	return myCell;
    }

    public int getGridX() {
	return xCoord;
    }

    public int getGridY() {
	return yCoord;
    }

    public void prepareToUpdate()
    {
        if (myCell != null){
            myCell.prepareToUpdate(this, myNeighbors);
        }
    }

    public void update() {
	// Update this
	if (myCell != null) {
	    Patch state = myCell.update(this, myNeighbors);
	    if (state == null) {
		this.removeCell();
	    }
	}

    }

    // modified the order for processing 4 directions
    protected enum relativePosition {
	NORTH, SOUTH, EAST, WEST, SOUTHEAST, NORTHEAST, SOUTHWEST, NORTHWEST
    }

    public boolean isEmpty() {
	return myCell == null;
    }

    public void addCell(Cell cell) {
        if (myCell != null){
            removeCell();
        }
        cell.setHeight(manager.getRowConstraints().get(0).getPrefHeight());
        cell.setWidth(manager.getColumnConstraints().get(0).getPrefWidth());
        cell.setArcHeight(cell.getHeight());
        cell.setArcWidth(cell.getWidth());
        getChildren().add(cell);
	myCell = cell;
    }

    public void removeCell() {
	if (myCell != null) {
	    getChildren().remove(myCell);
	    myCell = null;
	    myState = State.EMPTY;
	}
    }

    public Patch randomEmptyPatch() {
	// get an empty patch from grid manager
	return manager.findEmptyPatch();
    }

    public int getPreviousState() {
	return myPreviousCellState;
    }

    public void setPreviousState(int myState) {
	myPreviousCellState = myState;
    }

}
