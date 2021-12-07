package ch.epfl.cs107.play.game.icwars.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class AIPlayer extends ICWarsPlayer{

    /**
     * Demo actor
     *
     * @param owner
     * @param orientation
     * @param coordinates
     * @param spriteName
     */
    public AIPlayer(Area owner, Orientation orientation, DiscreteCoordinates coordinates, String spriteName) {
        super(owner, orientation, coordinates, spriteName, "enemy");
    }
}
