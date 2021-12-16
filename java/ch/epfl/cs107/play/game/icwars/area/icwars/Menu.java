package ch.epfl.cs107.play.game.icwars.area.icwars;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.icwars.ICWars;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Menu extends ICWarsArea {
    String menuName;

    public Menu(String name) {
        super();
        menuName = name;
    }

    @Override
    public String getTitle() {
        return "icwars/" + menuName;
    }

    @Override
    protected void createArea() {
        registerActor(new Background(this));
    }
}
