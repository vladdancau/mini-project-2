package ch.epfl.cs107.play.game.icwars.actor;

import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.awt.*;
import java.util.List;

public class ICWarsPlayer extends MovableAreaEntity {
    private float hp;
    private TextGraphics message;
    private Sprite sprite;
    private final static int MOVE_DURATION = 8;

    public ICWarsPlayer(Area owner, Orientation orientation, DiscreteCoordinates coordinates, String faction) {
        super(owner, orientation, coordinates);
        this.hp = 10;
        message = new TextGraphics(Integer.toString((int)hp), 0.4f, Color.BLUE);
        message.setParent(this);
        message.setAnchor(new Vector(-0.3f, 0.1f));
        sprite = new Sprite(faction, 1.f, 1.f,this);

        resetMotion();
    }
    public void centerCamera() {
        getOwnerArea().setViewCandidate(this);
    }

    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return null;
    }

    @Override
    public boolean takeCellSpace() {
        return false;
    }

    @Override
    public boolean isCellInteractable() {
        return false;
    }

    @Override
    public boolean isViewInteractable() {
        return false;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {

    }
}
