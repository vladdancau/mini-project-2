package ch.epfl.cs107.play.game.icwars.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.game.icwars.area.ICWarsRange;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Keyboard;

import java.util.HashMap;

public class AIPlayer extends ICWarsPlayer{

    /**
     * Demo actor
     *
     * @param owner
     * @param orientation
     * @param coordinates
     * @param faction
     */
    HashMap<Integer, Boolean> controller;

    public AIPlayer(Area owner, Orientation orientation, DiscreteCoordinates coordinates, String faction) {
        super(owner, orientation, coordinates, faction);
    }

    public boolean isPressed(int key) {
        return controller.containsKey(key) && controller.get(key);
    }

    public void updateController() {
        controller = new HashMap<>();
        Integer key = null;

        Unit closestTarget = null;
        if (selectedUnit != null)
            for (Unit u : ((ICWarsArea) getOwnerArea()).getEnemyUnits(faction))
                if (!u.getWaitingStatus() && (closestTarget == null || getDistance(u) < getDistance(closestTarget)))
                    closestTarget = u;

        switch (getState()) {
            case NORMAL:
                Unit closestUnit = null;
                for (Unit u : ((ICWarsArea) getOwnerArea()).getFriendlyUnits(faction))
                    if (!u.getWaitingStatus() && u.movableRadius() > 0 && (closestUnit == null || getDistance(u) < getDistance(closestUnit)))
                        closestUnit = u;
                if (closestUnit == null)
                    key = Keyboard.TAB;
                else
                    setTargetPosition(closestUnit.getPosition());
                //System.out.println(key + " " + (closestUnit == null));
                break;
            case MOVE_UNIT:
                if (closestTarget == null || selectedUnit.canAttack(closestTarget))
                    key = Keyboard.ENTER;
                else {
                    int unitRange = selectedUnit.movableRadius() * 2;
                    int fromX = (int) selectedUnit.getPosition().x;
                    int fromY = (int) selectedUnit.getPosition().y;
                    float tx = fromX, ty = fromY;

                    for (int x = -unitRange; x <= unitRange; x++)
                        for (int y = -unitRange; y <= unitRange; y++) {
                            int rx = x + fromX;
                            int ry = y + fromY;
                            if (selectedUnit.canMoveTo(rx, ry) && closestTarget.getDistance(rx, ry) < closestTarget.getDistance(tx, ty)) {
                                tx = rx;
                                ty = ry;
                            }
                        }

                    setTargetPosition(new Vector(tx, ty));
                    break;
                }
                break;
            case ACTION_SELECTION:
                if (closestTarget != null && selectedUnit.canAttack(closestTarget))
                    key = Keyboard.A;
                else
                    key = Keyboard.W;
                break;
            case ACTION:
                key = Keyboard.ENTER;
                break;
        }
        if (key != null) {
            //System.out.println(key);
            controller.put(key, true);
        }
    }

    private void setTargetPosition(Vector tPos) {
        Integer key = Keyboard.ENTER;
        Vector pos = getPosition();
        //System.out.println(pos.x + " " + pos.y + " " + tPos.);
        if (tPos.x > pos.x)
            key = Keyboard.RIGHT;
        else if (tPos.x < pos.x)
            key = Keyboard.LEFT;
        else if (tPos.y > pos.y)
            key = Keyboard.UP;
        else if (tPos.y < pos.y)
            key = Keyboard.DOWN;
        controller.put(key, true);
        System.out.println(key);
    }
}
