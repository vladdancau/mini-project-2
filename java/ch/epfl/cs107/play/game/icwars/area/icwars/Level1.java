package ch.epfl.cs107.play.game.icwars.area.icwars;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.icwars.ICWars;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Level1 extends ICWarsArea {
    @Override
    public String getTitle() {
        return "icwars/Level1";
    }

    @Override
    protected void createArea() {
        bgTrack = ICWars.loadClip("theme-drake.wav");

        registerActor(new Background(this));

        addUnit(new Unit(this, "ROCKET", "red", new DiscreteCoordinates(13, 5)));
        addUnit(new Unit(this, "SOLDIER", "red", new DiscreteCoordinates(14, 5)));
        addUnit(new Unit(this, "TANK", "red", new DiscreteCoordinates(15, 5)));
        addUnit(new Unit(this, "SOLDIER", "red", new DiscreteCoordinates(15, 4)));
        addUnit(new Unit(this, "CITY", "red", new DiscreteCoordinates(15, 6)));

        addUnit(new Unit(this, "TANK", "blue", new DiscreteCoordinates(5, 3)));
        addUnit(new Unit(this, "ROCKET", "blue", new DiscreteCoordinates(7, 3)));
        addUnit(new Unit(this, "SOLDIER", "blue", new DiscreteCoordinates(6, 3)));
        addUnit(new Unit(this, "SOLDIER", "blue", new DiscreteCoordinates(5, 4)));
        addUnit(new Unit(this, "CITY", "blue", new DiscreteCoordinates(5, 2)));

        addUnit(new Unit(this, "ROCKET", "yellow", new DiscreteCoordinates(9, 9)));
        addUnit(new Unit(this, "ROCKET", "yellow", new DiscreteCoordinates(9, 8)));
    }
}

