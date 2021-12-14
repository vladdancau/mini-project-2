package ch.epfl.cs107.play.game.icwars.actor.unit;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Path;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Action;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.AttackAction;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.WaitAction;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.game.icwars.area.ICWarsRange;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

import java.util.*;
import java.util.List;

public class Unit extends ICWarsActor {

    private String type;
    private int hp;
    private final int REPAIR_HP = 5;
    private ICWarsRange range;
    private boolean waitingStatus;

    public Unit(Area area, String type, String faction, DiscreteCoordinates coord) {
        super(area, Orientation.DOWN, coord, getUnitSprite(type, faction), faction);
        this.type = type;
        this.waitingStatus = false;
        this.hp = maxHp();
        initRange();
    }

    public void setWaitingStatus(boolean status) {
        waitingStatus = status;
        setOpacity(waitingStatus ? 0.5f : 1);
    }

    public boolean getWaitingStatus() {
        return waitingStatus;
    }

    static private String getUnitSprite(String type, String faction) {
        final String spriteName = "icwars/" + faction + "/" + type.toLowerCase();
        return spriteName;
    }

    public void initRange() {
        int unitRange = movableRadius() * 2;
        int fromX = getCurrentMainCellCoordinates().x;
        int fromY = getCurrentMainCellCoordinates().y;

        range = new ICWarsRange();

        for (int x = -unitRange; x <= unitRange; x++)
            for (int y = -unitRange; y <= unitRange; y++) {
                int rx = x + fromX;
                int ry = y + fromY;
                if (canMoveTo(rx, ry)) {
                    boolean hasLeftEdge = canMoveTo(rx - 1, ry);
                    boolean hasUpEdge = canMoveTo(rx, ry + 1);
                    boolean hasRightEdge = canMoveTo(rx + 1, ry);
                    boolean hasDownEdge = canMoveTo(rx, ry - 1);
                    range.addNode(new DiscreteCoordinates(rx, ry), hasLeftEdge, hasUpEdge, hasRightEdge, hasDownEdge);
                }
            }
    }

    public String getName(){
        return type;
    }
    public int getHp(){ return hp; }
    public int getDamage() { return UnitType.valueOf(this.type).damagePoints; }
    public int movableRadius() { return UnitType.valueOf(type).movableRadius; }
    public int maxHp() { return UnitType.valueOf(type).maxHp; }

    public void initAttackRange() {
        int unitRange = movableRadius() * 2;
        int fromX = getCurrentMainCellCoordinates().x;
        int fromY = getCurrentMainCellCoordinates().y;

        range = new ICWarsRange();

        for (int x = -unitRange; x <= unitRange; x++)
            for (int y = -unitRange; y <= unitRange; y++) {
                int rx = x + fromX, ry = y + fromY;
                if (canAttack(rx, ry)) {
                    boolean hasLeftEdge = canAttack(rx - 1, ry);
                    boolean hasUpEdge = canAttack(rx, ry + 1);
                    boolean hasRightEdge = canAttack(rx + 1, ry);
                    boolean hasDownEdge = canAttack(rx, ry - 1);
                    range.addNode(new DiscreteCoordinates(rx, ry), hasLeftEdge, hasUpEdge, hasRightEdge, hasDownEdge);
                }
            }
    }

    public List<Action> getActions() {
        List<Action> actions = new ArrayList<>();
        actions.add(new WaitAction(this, getOwnerArea()));
        actions.add(new AttackAction(this, getOwnerArea()));
        return actions;
    }


    public enum UnitType {
        SOLDIER(2,2,5),
        ROCKET(3,3,6),
        TANK(4,7,10);

        final int damagePoints;
        final int movableRadius;
        final int maxHp;

        UnitType(int movableRadius, int damagePoints, int maxHp){
            this.damagePoints = damagePoints;
            this.movableRadius = movableRadius;
            this.maxHp = maxHp;
        }
    }

    public void takeDamage(int damage) {
        hp = hp - damage;
        if (hp < 0)
            this.destroy();
    }

    public void destroy() {
        ((ICWarsArea) getOwnerArea()).removeUnit(this);
    }

    public boolean canMoveTo(int x, int y) {
        return canMoveTo(new DiscreteCoordinates(x, y));
    }

    public boolean canMoveTo(DiscreteCoordinates coords) {
        if(coords.x >= getOwnerArea().getHeight() || coords.y >= getOwnerArea().getWidth() || coords.x < 0 || coords.y < 0)
            return false;

        // test if tile is within movable radius
        int unitX = getCurrentMainCellCoordinates().x;
        int unitY = getCurrentMainCellCoordinates().y;
        if (Math.abs(coords.x - unitX) + Math.abs(coords.y - unitY) > movableRadius())
            return false;

        // test if tile is walkable (e.g. not water)
        ArrayList<DiscreteCoordinates> arrayCoords = new ArrayList<DiscreteCoordinates>();
        arrayCoords.add(coords);
        if (!getOwnerArea().canEnterAreaCells(this, arrayCoords))
            return false;

        // test if unit not already there
        for (Unit u : ((ICWarsArea) getOwnerArea()).getUnits())
            if(u != this && u.getPosition().equals(coords.toVector()))
                return false;

        return true;
    }

    public boolean canAttack(int x, int y) {
        DiscreteCoordinates coords = getCurrentMainCellCoordinates();
        int damageRadius = movableRadius();
        return Math.abs(coords.x - x) + Math.abs(coords.y - y) <= damageRadius;
    }

    public boolean canAttack(Unit other) {
        DiscreteCoordinates coords = other.getCurrentMainCellCoordinates();
        return canAttack(coords.x, coords.y);
    }
    
    public void setOpacity(float opacity) {
        this.sprite.setAlpha(opacity);
    }

    public boolean takeCellSpace(){
        return true;
    }

    public void drawRangeAndPathTo(DiscreteCoordinates destination, Canvas canvas) {
        range.draw(canvas);
        Queue<Orientation> path = range.shortestPath(getCurrentMainCellCoordinates(), destination);
        //Draw path only if it exists (destination inside the range)
        if (path != null)
            new Path(getCurrentMainCellCoordinates().toVector(), path).draw(canvas);
    }

    @Override
    public boolean changePosition(DiscreteCoordinates newPosition) {
        boolean success = super.changePosition(newPosition);
        initRange();
        return success;
    }
}
