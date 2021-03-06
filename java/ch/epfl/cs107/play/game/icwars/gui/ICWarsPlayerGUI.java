package ch.epfl.cs107.play.game.icwars.gui;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsPlayer;
import ch.epfl.cs107.play.game.icwars.actor.RealPlayer;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Action;
import ch.epfl.cs107.play.game.icwars.area.ICWarsBehavior;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

import java.util.List;

public class ICWarsPlayerGui implements Graphics {
    private ICWarsPlayer player;
    public static float FONT_SIZE = 20f;
  
    ICWarsActionsPanel actionsPanel;
    ICWarsInfoPanel infoPanel;

    public ICWarsPlayerGui(ICWarsPlayer player, float cameraScaleFactor) {
        this.player = player;
        actionsPanel = new ICWarsActionsPanel(cameraScaleFactor);
        infoPanel = new ICWarsInfoPanel(cameraScaleFactor);
    }

    @Override
    public void draw(Canvas canvas) {
        if (player.getState() == ICWarsPlayer.GameState.MOVE_UNIT)
            player.drawSelectedUnitRange(canvas);
        if (player.getState() == ICWarsPlayer.GameState.ACTION_SELECTION)
            actionsPanel.draw(canvas);
        if (player.getState() == ICWarsPlayer.GameState.NORMAL || player.getState() == ICWarsPlayer.GameState.SELECT_CELL) {
            infoPanel.draw(canvas);
        }
    }

    public void setActions(List<Action> actions) {
        actionsPanel.setActions(actions);
    }
    public void setUnit(Unit unit){
        infoPanel.setUnit(unit);
    }
    public void setCurrentCell(ICWarsBehavior.ICWarsCellType currentCell){
        infoPanel.setCurrentCell(currentCell);
    }
}
