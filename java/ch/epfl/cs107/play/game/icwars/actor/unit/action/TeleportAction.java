package ch.epfl.cs107.play.game.icwars.actor.unit.action;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsPlayer;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class TeleportAction extends Action {
    public TeleportAction(Unit unit, Area area) {
        super(unit, area);
        name = "(T)eleport";
        key = Keyboard.T;
    }

    public void draw(Canvas canvas) {}
    public void doAction(float dt, ICWarsPlayer player) {
        unit.teleport();
        player.deselectUnit();
    }
}
