package ch.epfl.cs107.play.game.icwars.actor;
import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Path;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.game.icwars.area.ICWarsRange;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.awt.*;
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
        initRange();
    }

    public void setWaitingStatus(boolean status) {
        waitingStatus = status;
    }

    public boolean getWaitingStatus() {
        return waitingStatus;
    }

    static private String getUnitSprite(String type, String faction) {
        final String capitalizedType = type.substring(0, 1).toUpperCase() + type.substring(1).toLowerCase();
        final String spriteName = "icwars/" + (faction == "ally" ? "friendly" : "enemy") + capitalizedType;
        return spriteName;
    }

    public void initRange() {
        int unitRange = UnitType.valueOf(type).movableRadius * 2;
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

    public List<Action> getActions() {
        List<Action> actions = new ArrayList<>();
        actions.add(new WaitAction(this, getOwnerArea()));
        return actions;
    }

    public String getName(){
        return type;
    }

    public int getHp(){ return hp; }

    public int getDamage(){
        return UnitType.valueOf(this.type).damagePoints;
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

    public void damage(){hp--;}

    public void repair(){hp+=REPAIR_HP;}

    public boolean canMoveTo(int x, int y) {
        return canMoveTo(new DiscreteCoordinates(x, y));
    }

    public boolean canMoveTo(DiscreteCoordinates coords) {
        if(coords.x >= getOwnerArea().getHeight() || coords.y >= getOwnerArea().getWidth())
            return false;
        if (coords.x < 0 || coords.y < 0)
            return false;

        // test if tile is within movable radius
        int unitRange = UnitType.valueOf(type).movableRadius;
        int unitX = getCurrentMainCellCoordinates().x;
        int unitY = getCurrentMainCellCoordinates().y;
        double diffX = coords.x - unitX ;
        double diffY = coords.y - unitY;
        if (Math.abs(diffX) + Math.abs(diffY) > unitRange)
            return false;

        // test if tile is walkable (e.g. not water)
        // implement&use canEnter() method in ICWarsCell
        ArrayList<DiscreteCoordinates> arrayCoords = new ArrayList<DiscreteCoordinates>();
        arrayCoords.add(coords);
        boolean isWalkable = !getOwnerArea().canEnterAreaCells(this, arrayCoords);
        if(isWalkable) {
            return false;
        }

        // test if unit not already there
        List<Unit> units = ((ICWarsArea) getOwnerArea()).getUnits();
        // coords
        for(int i = 0; i < units.size(); i++)
            if(units.get(i) != this && units.get(i).getPosition().equals(coords.toVector()))
                return false;

        return true;
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
        if (path != null){
            new Path(getCurrentMainCellCoordinates().toVector(), path).draw(canvas);
        }
    }

    @Override
    public boolean changePosition(DiscreteCoordinates newPosition) {
        super.changePosition(newPosition);
        initRange();
        return true;
    }

    public abstract class Action implements Graphics {
        protected Unit unit;
        protected Area area;
        private boolean ongoing;

        public String name;
        public int key;


        public Action(Unit unit, Area area) {
            this.unit = unit;
            this.area = area;
            this.ongoing = true;
        }

        public String getName() {
            return name;
        }

        public int getKey() {
            return key;
        }

        public abstract void draw(Canvas canvas);
        public abstract void doAction(float dt, ICWarsPlayer player, Keyboard keyboard);

        protected void endAction() {
            ongoing = false;
        }

        private boolean isOngoing() {
            return ongoing;
        }
    }

    private class WaitAction extends Action {
        public WaitAction(Unit unit, Area area) {
            super(unit, area);
            name = "(W)ait";
            key = Keyboard.W;
        }

        public void draw(Canvas canvas) {}
        public void doAction(float dt, ICWarsPlayer player, Keyboard keyboard) {
            unit.setWaitingStatus(true);
            player.selectUnit(null);
            player.setState(ICWarsPlayer.GameState.NORMAL);
            endAction();
        }
    }
}
