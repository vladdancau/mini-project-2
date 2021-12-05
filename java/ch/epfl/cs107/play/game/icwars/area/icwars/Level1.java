package ch.epfl.cs107.play.game.icwars.area.icwars;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;

public class Level1 extends ICWarsArea {
    @Override
    public String getTitle() {
        return "icwars/Level1";
    }

    @Override
    protected void createArea() {
        registerActor(new Background(this));
    }
}

