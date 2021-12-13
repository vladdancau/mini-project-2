package ch.epfl.cs107.play.game.icwars.actor.unit.action;


import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsPlayer;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public abstract class Action implements Graphics {
    protected Unit unit;
    protected Area area;

    public String name;
    public int key;


    public Action(Unit unit, Area area) {
        this.unit = unit;
        this.area = area;
    }

    public String getName() {
        return name;
    }

    public int getKey() {
        return key;
    }

    public abstract void draw(Canvas canvas);
    public abstract void doAction(float dt, ICWarsPlayer player);
}
