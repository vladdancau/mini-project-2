package ch.epfl.cs107.play.game.icwars.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Action;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.game.icwars.gui.ICWarsPlayerGui;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.util.List;

public class RealPlayer extends ICWarsPlayer {
    public RealPlayer(Area owner, Orientation orientation, DiscreteCoordinates coordinates, String faction) {
        super(owner, orientation, coordinates, faction);
    }

    public boolean isPressed(int key) {
        Keyboard keyboard = getOwnerArea().getKeyboard();
        return keyboard.get(key).isPressed();
    }

    public void updateController() { }
}
