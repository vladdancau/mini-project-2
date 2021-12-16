package ch.epfl.cs107.play.game.icwars.area.icwars;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.icwars.ICWars;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Level2 extends ICWarsArea {
    @Override
    public String getTitle() {
        return "icwars/Level2";
    }

    @Override
    protected void createArea() {
        bgTrack = ICWars.loadClip("theme-eagle.wav");

        startCoordinates = new DiscreteCoordinates(4, 14);

        registerActor(new Background(this));

        addUnit(new Unit(this, "CITY", "blue", new DiscreteCoordinates(5, 14)));
        addUnit(new Unit(this, "TANK", "blue", new DiscreteCoordinates(5, 15)));
        addUnit(new Unit(this, "TANK", "blue", new DiscreteCoordinates(5, 16)));
        addUnit(new Unit(this, "ROCKET", "blue", new DiscreteCoordinates(6, 14)));
        addUnit(new Unit(this, "ROCKET", "blue", new DiscreteCoordinates(6, 15)));
        addUnit(new Unit(this, "ROCKET", "blue", new DiscreteCoordinates(6, 16)));
        addUnit(new Unit(this, "SOLDIER", "blue", new DiscreteCoordinates(7, 14)));
        addUnit(new Unit(this, "SOLDIER", "blue", new DiscreteCoordinates(7, 15)));
        addUnit(new Unit(this, "SOLDIER", "blue", new DiscreteCoordinates(7, 16)));

        addUnit(new Unit(this, "CITY", "red", new DiscreteCoordinates(27, 17)));
        addUnit(new Unit(this, "TANK", "red", new DiscreteCoordinates(25, 17)));
        addUnit(new Unit(this, "TANK", "red", new DiscreteCoordinates(25, 18)));
        addUnit(new Unit(this, "TANK", "red", new DiscreteCoordinates(24, 17)));
        addUnit(new Unit(this, "TANK", "red", new DiscreteCoordinates(24, 18)));
        addUnit(new Unit(this, "SOLDIER", "red", new DiscreteCoordinates(23, 17)));
        addUnit(new Unit(this, "SOLDIER", "red", new DiscreteCoordinates(23, 18)));
        addUnit(new Unit(this, "SOLDIER", "red", new DiscreteCoordinates(22, 17)));
        addUnit(new Unit(this, "SOLDIER", "red", new DiscreteCoordinates(22, 18)));

        addUnit(new Unit(this, "ROCKET", "yellow", new DiscreteCoordinates(8, 3)));
        addUnit(new Unit(this, "SOLDIER", "yellow", new DiscreteCoordinates(9, 4)));
        addUnit(new Unit(this, "TANK", "yellow", new DiscreteCoordinates(9, 3)));

        addUnit(new Unit(this, "ROCKET", "green", new DiscreteCoordinates(8, 26)));
        addUnit(new Unit(this, "SOLDIER", "green", new DiscreteCoordinates(9, 25)));
        addUnit(new Unit(this, "TANK", "green", new DiscreteCoordinates(9, 26)));
    }
}

