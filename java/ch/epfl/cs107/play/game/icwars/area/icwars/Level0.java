package ch.epfl.cs107.play.game.icwars.area.icwars;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.icwars.ICWars;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.io.DefaultFileSystem;
import ch.epfl.cs107.play.io.ResourceFileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.swing.SwingSound;

import javax.sound.sampled.FloatControl;
import java.io.InputStream;

public class Level0 extends ICWarsArea {
    @Override
    public String getTitle() {
        return "icwars/Level0";
    }

    @Override
    protected void createArea() {
        bgTrack = ICWars.loadClip("theme-andy.wav");

        registerActor(new Background(this));

        addUnit(new Unit(this, "ROCKET", "red", new DiscreteCoordinates(8, 5)));
        addUnit(new Unit(this, "SOLDIER", "red", new DiscreteCoordinates(9, 6)));
        addUnit(new Unit(this, "TANK", "red", new DiscreteCoordinates(9, 5)));
        addUnit(new Unit(this, "ROCKET", "blue", new DiscreteCoordinates(0, 4)));
        addUnit(new Unit(this, "SOLDIER", "blue", new DiscreteCoordinates(0, 5)));
        addUnit(new Unit(this, "TANK", "blue", new DiscreteCoordinates(1, 4)));
        //addUnit(new Unit(this, BUILDING, "green", new DiscreteCoordinates(2,2)));
    }
}
