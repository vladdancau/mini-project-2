package ch.epfl.cs107.play.game.icwars.area.icwars;

import ch.epfl.cs107.play.game.areagame.actor.Background;
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
        registerActor(new Background(this));
        addUnit(new Unit(this, "ROCKET", "blue", new DiscreteCoordinates(5, 5)));
        addUnit(new Unit(this, "SOLDIER", "blue", new DiscreteCoordinates(3, 4)));
        addUnit(new Unit(this, "TANK", "blue", new DiscreteCoordinates(5, 3)));
    }
}

