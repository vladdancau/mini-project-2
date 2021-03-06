package ch.epfl.cs107.play.game.icwars.actor.unit.action;

import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsPlayer;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class AttackAction extends Action {
    private List<Unit> targets;
    private ImageGraphics sprite = null;
    int targetIx;
    ICWarsPlayer player;

    public AttackAction(Unit unit, Area area) {
        super(unit, area);
        name = "(A)ttack";
        key = Keyboard.A;
    }

    public void draw(Canvas canvas) {
        if (sprite != null) {
            player.drawSelectedUnitRange(canvas);
            sprite.draw(canvas);
        }
    }

    public void doAction(float dt, ICWarsPlayer player) {
        if (sprite == null) { // Action not initialized
            this.player = player;
            player.selectedUnit.initAttackRange();
            sprite = new ImageGraphics(ResourcePath.getSprite("icwars/UIpackSheet"), 1f, 1f, new RegionOfInterest(26*18, 19*18,16,16));

            List<Unit> enemies = ((ICWarsArea) area).getEnemyUnits(unit.faction);
            targets = new ArrayList<>();
            for (Unit u : enemies)
                if (unit.canAttack(u))
                    targets.add(u);

            if (targets.isEmpty()) {
                player.deselectUnit();
                return;
            } else
                selectTarget(0);
        }

        if (player.isPressed(Keyboard.ENTER)) {
            targets.get(targetIx).takeDamage(unit.getDamage());
            player.deselectUnit();
            player.centerCamera();
        } else if (player.isPressed(Keyboard.LEFT)) {
            selectTarget(targetIx - 1);
        } else if (player.isPressed(Keyboard.RIGHT)) {
            selectTarget(targetIx + 1);
        }
    }

    private void selectTarget(int ix) {
        targetIx = ix = (ix % targets.size() + targets.size()) % targets.size();
        targets.get(ix).centerCamera();
        sprite.setAnchor(targets.get(ix).getPosition().add(0.5f, -0.5f));
    }
}
