package ch.epfl.cs107.play.game.icwars.actor;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.awt.*;
import java.util.Collections;
import java.util.List;

public class ICWarsPlayer extends ICWarsActor {
    private GameState state = GameState.IDLE;
    ICWarsPlayer nextPlayer;

    public ICWarsPlayer(Area owner, Orientation orientation, DiscreteCoordinates coordinates, String spriteName, String faction) {
        super(owner, orientation, coordinates, spriteName, faction);
        resetMotion();
    }

    /**
     * Leave an area by unregister this player
     */
    public void leaveArea(){
        getOwnerArea().unregisterActor(this);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
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
    public void acceptInteraction(AreaInteractionVisitor v) {
    }

    public void setState(GameState s) {
        state = s;
    }

    public GameState getState() {
        return state;
    }

    public void endTurn() {
        setState(GameState.IDLE);
        nextPlayer.beginTurn();
    }

    public void beginTurn() {
        setState(GameState.WAITING_TURN);
    }

    public void setNextPlayer(ICWarsPlayer nextPlayer) {
        this.nextPlayer = nextPlayer;
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
}
