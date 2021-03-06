package simulationObjects;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;

public class ForestCell extends Cell {

    final private int EMPTY = 0;
    final private int ONFIRE = 1;
    final private int TREE = 2;

    public ForestCell() {
	super();
    }

    private void burnDown(Patch currentPatch) {
	setState(EMPTY);
    }

    public void catchFire(Patch currentPatch) {
	setState(ONFIRE);
    }

    @Override
    public ArrayList<Color> getInitialColors() {
	ArrayList<Color> myStateColors = new ArrayList<Color>();
	myStateColors.add(Color.WHITE);
	myStateColors.add(Color.RED);
	myStateColors.add(Color.GREEN);
	return myStateColors;
    }

    @Override
    public int getNextState() {
	return myState == TREE ? 0 : myState + 1;
    }

    @Override
    public int getState() {
	return myState;
    }

    @Override
    public ArrayList<String> getStateTypes() {
	ArrayList<String> myStateType = new ArrayList<String>();
	myStateType.add("BACKGROUND");
	myStateType.add("ONFIRE");
	myStateType.add("TREE");
	return myStateType;
    }

    @Override
    public void prepareToUpdate(Patch currentPatch, List<Patch> neighbors) {
	currentPatch.setPreviousState(myState);
    }

    @Override
    public void setState(int state) {
	if (state == ONFIRE) {
	    setFill(infoSheet.getColor("ONFIRE"));
	} else if (state == TREE) {
	    setFill(infoSheet.getColor("TREE"));
	}
	myState = state;
    }

    @Override
    public void update(Patch currentPatch, List<Patch> neighbors) {
	switch (myState) {
	case TREE:

	    if (willCatchFire(neighbors)) {
		catchFire(currentPatch);
	    } else {
		setState(TREE);
	    }
	    break;

	case ONFIRE:
	    burnDown(currentPatch);
	    break;
	}
    }

    public boolean willCatchFire(List<Patch> neighbors) {
	boolean haveNeighborOnFire = false;
	for (Patch neighborPatch : neighbors) {
	    if (neighborPatch.getPreviousState() == ONFIRE) {
		haveNeighborOnFire = true;
		break;
	    }
	}
	return ((haveNeighborOnFire) && (Math.random() <= infoSheet.getParam()));

    }

}
