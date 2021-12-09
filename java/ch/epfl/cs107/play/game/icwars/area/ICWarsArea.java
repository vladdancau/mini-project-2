package ch.epfl.cs107.play.game.icwars.area;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.area.icwars.Level0;
import ch.epfl.cs107.play.game.tutosSolution.Tuto1;
import ch.epfl.cs107.play.game.tutosSolution.Tuto2Behavior;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Image;
import ch.epfl.cs107.play.window.Window;

import java.util.ArrayList;
import java.util.List;

abstract public class ICWarsArea extends Area {
    private Window window;
    private ICWarsBehavior behavior;
    /**
     * Create the area by adding all its actors
     * called by the begin method, when the area starts to play
     */
    protected abstract void createArea();

    public List<Unit> units;

    public ICWarsArea() {
        super();
        units = new ArrayList<Unit>();
    }

    public void addUnit(Unit u) {
        units.add(u);
        registerActor(u);
    }

    public List<Unit> getFriendlyUnits(String faction) {
        List<Unit> unitList = new ArrayList<>();
        for (Unit u : units)
            if (u.faction == faction)
                unitList.add(u);
        return unitList;
    }

    public List<Unit> getEnemyUnits(String faction) {
        List<Unit> unitList = new ArrayList<>();
        for (Unit u : units)
            if (u.faction != faction)
                unitList.add(u);
        return unitList;
    }

    @Override
    public int getWidth() {
        Image behaviorMap = window.getImage(ResourcePath.getBehavior(getTitle()), null, false);
        return  behaviorMap.getWidth();
    }

    @Override
    public int getHeight() {
        Image behaviorMap = window.getImage(ResourcePath.getBehavior(getTitle()), null, false);
        return  behaviorMap.getHeight();

    }

    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        this.window = window;
        if (super.begin(window, fileSystem)) {
            // Set the behavior map
            behavior = new ICWarsBehavior(window, getTitle());
            setBehavior(behavior);
            createArea();
            return true;
        }
        return false;
    }

    @Override
    public final float getCameraScaleFactor() {
        return Tuto1.CAMERA_SCALE_FACTOR;
    }
}
