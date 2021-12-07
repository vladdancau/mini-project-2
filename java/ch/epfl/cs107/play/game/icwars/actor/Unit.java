package ch.epfl.cs107.play.game.icwars.actor;


import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Path;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.game.icwars.area.ICWarsRange;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Locale;
import java.util.Queue;
import java.util.Vector;

public class Unit extends ICWarsActor {

    private String type;
    private int hp;
    private final int REPAIR_HP = 5;
    private ICWarsRange range;

    public Unit(Area area, String type, String faction, DiscreteCoordinates coord) {
        super(area, Orientation.DOWN, coord, getUnitSprite(type, faction), faction);
        this.type = type;
        initRange();
    }

    static private String getUnitSprite(String type, String faction) {
        final String capitalizedType = type.substring(0, 1).toUpperCase() + type.substring(1).toLowerCase();
        final String spriteName = "icwars/" + (faction == "ally" ? "friendly" : "enemy") + capitalizedType;
        return spriteName;
    }

    private void initRange() {
        int unitRange = UnitType.valueOf(type).movableRadius;
        int fromX = getCurrentMainCellCoordinates().x;
        int fromY = getCurrentMainCellCoordinates().y;

        range = new ICWarsRange();

        for (int x = -unitRange; x <= unitRange; x++)
            for (int y = -unitRange; y <= unitRange; y++) {
                DiscreteCoordinates coord = new DiscreteCoordinates(fromX + x, fromY + y);
                if (canMoveTo(coord)) {
                    range.addNode(coord, x > -unitRange, y < unitRange, x < unitRange, y > -unitRange);
                }
            }
    }

    public String getName(){
        return type;
    }

    public int getHp(int maxHp){
        if(hp<0){return 0;}
        else if(hp>maxHp){return maxHp;}
        return 0;
    }

    public int getDamage(String type){
        return UnitType.valueOf(type).damagePoints;
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

    public boolean canMoveTo(DiscreteCoordinates coords) {
        if(coords.x >= getOwnerArea().getHeight() || coords.y >= getOwnerArea().getWidth())
            return false;
        return (coords.x >= 0 && coords.y >= 0);
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
}
