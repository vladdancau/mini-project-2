package ch.epfl.cs107.play.game.icwars.actor;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.game.icwars.gui.ICWarsPlayerGui;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

import java.util.List;

public class RealPlayer extends ICWarsPlayer {
    public ICWarsPlayerGui gui;

    public RealPlayer(Area owner, Orientation orientation, DiscreteCoordinates coordinates, String spriteName, String faction) {
        super(owner, orientation, coordinates, spriteName, faction);
        gui = new ICWarsPlayerGui(this, getOwnerArea().getCameraScaleFactor());
    }

    /**
     * Orientate and Move this player in the given orientation if the given button is down
     * @param orientation (Orientation): given orientation, not null
     * @param b (Button): button corresponding to the given orientation, not null
     */
    private void moveIfPressed(Orientation orientation, Button b){
        if(b.isDown()) {
            if (!isDisplacementOccurs()) {
                orientate(orientation);
                move(MOVE_DURATION);
            }
        }
    }

    @Override
    public void update(float deltaTime) {
        Keyboard keyboard = getOwnerArea().getKeyboard();

        switch (getState()) {
            case IDLE:
                break;
            case WAITING_TURN:
                setState(GameState.NORMAL);
                centerCamera();
                break;
            case NORMAL:
                movePlayer();

                if(keyboard.get(Keyboard.ENTER).isPressed()) {
                    setState(GameState.SELECT_CELL);
                }
                else if (keyboard.get(Keyboard.TAB).isPressed()) {
                    endTurn();
                }
                else if(keyboard.get(Keyboard.U).isPressed()) {
                    setState(GameState.SELECT_CELL);
                }

                break;
            case SELECT_CELL:
                setState(GameState.NORMAL);
                List<Unit> units = ((ICWarsArea) getOwnerArea()).getFriendlyUnits(faction);
                for (Unit u : units) {
                    if (u.getPosition().equals(this.getPosition())) {
                        this.selectUnit(u);
                    }
                }
                break;
            case MOVE_UNIT:
                movePlayer();

                if(keyboard.get(Keyboard.ENTER).isPressed()){
                    Vector d = (getPosition().sub(selectedUnit.getPosition()));
                    int movableRadius = Unit.UnitType.valueOf(selectedUnit.getName()).movableRadius;

                    DiscreteCoordinates coords = getCurrentMainCellCoordinates();
                    if (selectedUnit.canMoveTo(coords)) {
                        selectedUnit.changePosition(coords);
                        selectedUnit.setWaitingStatus(true);
                        actions = selectedUnit.getActions();
                        gui.setActions(actions);
                        setState(GameState.ACTION_SELECTION);
                    }
                }
                break;
            case ACTION_SELECTION:
                for (Unit.Action a : actions) {
                    if (keyboard.get(a.key).isPressed()) {
                        selectedAction = a;
                        setState(GameState.ACTION);
                    }
                }
                break;
            case ACTION:
                selectedAction.doAction(deltaTime, this, keyboard);
                break;
        }

        super.update(deltaTime);
    }

    public void movePlayer() {
        Keyboard keyboard = getOwnerArea().getKeyboard();

        // Control player position
        moveIfPressed(Orientation.LEFT, keyboard.get(Keyboard.LEFT));
        moveIfPressed(Orientation.UP, keyboard.get(Keyboard.UP));
        moveIfPressed(Orientation.RIGHT, keyboard.get(Keyboard.RIGHT));
        moveIfPressed(Orientation.DOWN, keyboard.get(Keyboard.DOWN));
    }

    /**
     *
     * @param area (Area): initial area, not null
     * @param position (DiscreteCoordinates): initial position, not null
     */
    @Override
    public void enterArea(ICWarsArea area, DiscreteCoordinates position){
        super.enterArea(area, position);
        centerCamera();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        gui.draw(canvas);
    }

    public void drawSelectedUnitRange(Canvas canvas) {
        if (selectedUnit != null)
            selectedUnit.drawRangeAndPathTo(getCurrentMainCellCoordinates(), canvas);
    }
}
