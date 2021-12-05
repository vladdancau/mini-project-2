package ch.epfl.cs107.play.game.icwars.area;

import ch.epfl.cs107.play.game.areagame.Area;

public class ICWarsArea extends Area {
    private ICWarsBehavior behavior;

    protected void createArea() {
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public float getCameraScaleFactor() {
        return 0;
    }
}
