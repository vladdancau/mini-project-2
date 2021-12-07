package ch.epfl.cs107.play.game.icwars.area.icwars;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Foreground;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsPlayer;
import ch.epfl.cs107.play.game.icwars.actor.RealPlayer;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.game.tutosSolution.actor.SimpleGhost;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;

public class Level0 extends ICWarsArea {
    @Override
    public String getTitle() {
        return "icwars/Level0";
    }

    @Override
    protected void createArea() {
        registerActor(new Background(this));
        registerActor(new Unit(this, "TANK", "ally", new DiscreteCoordinates(0, 0)));
        registerActor(new Unit(this, "SOLDIER", "ally", new DiscreteCoordinates(3, 4)));
        registerActor(new Unit(this, "ROCKET", "ally", new DiscreteCoordinates(0, 0)));
    }
}
