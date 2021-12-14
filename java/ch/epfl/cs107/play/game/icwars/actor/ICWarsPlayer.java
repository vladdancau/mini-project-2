package ch.epfl.cs107.play.game.icwars.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Action;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.game.icwars.gui.ICWarsPlayerGui;
import ch.epfl.cs107.play.game.icwars.area.ICWarsBehavior;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.util.Collections;
import java.util.List;

abstract public class ICWarsPlayer extends ICWarsActor {
    ICWarsPlayer nextPlayer;
    protected List<Action> actions;
    protected Action selectedAction;

    public Unit selectedUnit;
    protected GameState state = GameState.IDLE;
    public ICWarsPlayerGui gui;

    public ICWarsPlayer(Area owner, Orientation orientation, DiscreteCoordinates coordinates, String faction) {
        super(owner, orientation, coordinates,  "icwars/" + faction + "/cursor", faction);
        gui = new ICWarsPlayerGui(this, getOwnerArea().getCameraScaleFactor());
        resetMotion();
    }

    public void selectUnit(Unit u) {
        if (u.getWaitingStatus())
            return;

        selectedUnit = u;
        setState(GameState.MOVE_UNIT);
    }
    public void deselectUnit() {
        selectedUnit.setWaitingStatus(true);
        selectedUnit = null;
        selectedAction = null;
        setState(GameState.NORMAL);
    }

    public void drawSelectedUnitRange(Canvas canvas) {
        if (selectedUnit != null) {
            selectedUnit.drawRangeAndPathTo(getCurrentMainCellCoordinates(), canvas);
        }
    }

    abstract public boolean isPressed(int key);
    abstract public void updateController();

    /**
     * Leave an area by unregister this player
     */
    public void leaveArea(){
        getOwnerArea().unregisterActor(this);
    }

    @Override
    public void draw(Canvas canvas) {
        if (state != GameState.IDLE)
            super.draw(canvas);
        gui.draw(canvas);
        if (selectedAction != null)
            selectedAction.draw(canvas);
    }

    @Override
    public boolean takeCellSpace() {
        return true;
    }

    @Override
    public boolean isCellInteractable() {
        return true;
    }

    @Override
    public boolean isViewInteractable() {
        return true;
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v) { }

    public void setState(GameState s) {
        state = s;
    }

    public GameState getState() {
        return state;
    }

    public void endTurn() {
        List<Unit> friendlyUnits = ((ICWarsArea) getOwnerArea()).getFriendlyUnits(faction);
        for (Unit u : friendlyUnits)
            u.setWaitingStatus(false);

        setState(GameState.IDLE);
        nextPlayer.beginTurn();
    }

    public void beginTurn() {
        setState(GameState.WAITING_TURN);
    }

    public void setNextPlayer(ICWarsPlayer nextPlayer) {
        this.nextPlayer = nextPlayer;
    }
    public ICWarsPlayer getNextPlayer() {
        return this.nextPlayer;
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

    public enum GameState {
        IDLE,
        WAITING_TURN,
        NORMAL,
        SELECT_CELL,
        MOVE_UNIT,
        ACTION_SELECTION,
        ACTION;
    }

    /**
     * Orientate and Move this player in the given orientation if the given button is down
     * @param orientation (Orientation): given orientation, not null
     * @param key (int): key code corresponding to the given orientation, not null
     */
    private void moveIfPressed(Orientation orientation, int key){
        if(isPressed(key)) {
            if (!isDisplacementOccurs()) {
                orientate(orientation);
                move(MOVE_DURATION);
            }
        }
    }

    @Override
    public void update(float deltaTime) {
        updateController();
        DiscreteCoordinates coords = getCurrentMainCellCoordinates();

        switch (getState()) {
            case IDLE:
                break;
            case WAITING_TURN:
                setState(GameState.NORMAL);
                centerCamera();
                break;
            case NORMAL:
                doMovement();

                gui.setUnit(null);
                List<Unit> unitList = ((ICWarsArea) getOwnerArea()).getUnits();
                for (Unit u : unitList) {
                    if (u.getPosition().equals(getPosition())) {
                        gui.setUnit(u);
                    }
                }

                ICWarsBehavior.ICWarsCellType cellType = ((ICWarsArea) getOwnerArea()).getCellType(coords.x, coords.y);
                gui.setCurrentCell(cellType);

                if(isPressed(Keyboard.ENTER)) {
                    setState(GameState.SELECT_CELL);
                }
                else if (isPressed(Keyboard.TAB)) {
                    endTurn();
                }
                else if(isPressed(Keyboard.U)) {
                    setState(GameState.SELECT_CELL);
                }

                break;
            case SELECT_CELL:
                setState(GameState.NORMAL);
                List<Unit> units = ((ICWarsArea) getOwnerArea()).getFriendlyUnits(faction);
                for (Unit u : units) {
                    if (u.getPosition().equals(this.getPosition())) {
                        this.selectUnit(u);
                        u.initRange();
                    }
                }

                break;
            case MOVE_UNIT:
                doMovement();

                if(isPressed(Keyboard.ENTER)){
                    Vector d = (getPosition().sub(selectedUnit.getPosition()));
                    int movableRadius = selectedUnit.movableRadius();

                    if (selectedUnit.canMoveTo(coords)) {
                        selectedUnit.changePosition(coords);
                        actions = selectedUnit.getActions();
                        gui.setActions(actions);
                        setState(GameState.ACTION_SELECTION);
                    }
                }
                break;
            case ACTION_SELECTION:
                for (Action a : actions) {
                    if (isPressed(a.key)) {
                        selectedAction = a;
                        setState(GameState.ACTION);
                    }
                }
                break;
            case ACTION:
                selectedAction.doAction(deltaTime, this);
                break;
        }

        super.update(deltaTime);
    }

    public void doMovement() {
        moveIfPressed(Orientation.LEFT, Keyboard.LEFT);
        moveIfPressed(Orientation.UP, Keyboard.UP);
        moveIfPressed(Orientation.RIGHT, Keyboard.RIGHT);
        moveIfPressed(Orientation.DOWN, Keyboard.DOWN);
    }
}
