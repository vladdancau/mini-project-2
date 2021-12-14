package ch.epfl.cs107.play.game.icwars.area.icwars;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.io.DefaultFileSystem;
import ch.epfl.cs107.play.io.ResourceFileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.swing.SwingSound;

import java.io.InputStream;

public class Level0 extends ICWarsArea {
    @Override
    public String getTitle() {
        return "icwars/Level0";
    }

    @Override
    protected void createArea() {
        try {
            InputStream input = (new ResourceFileSystem(DefaultFileSystem.INSTANCE)).read("audio/theme-andy.wav");
            SwingSound bgSound = new SwingSound(input);
            bgSound.openedClip(0).start();
        } catch (Exception e) {
            System.out.println(e);
        }
        registerActor(new Background(this));
        addUnit(new Unit(this, "ROCKET", "yellow", new DiscreteCoordinates(8, 3)));
        addUnit(new Unit(this, "SOLDIER", "yellow", new DiscreteCoordinates(9, 4)));
        addUnit(new Unit(this, "TANK", "yellow", new DiscreteCoordinates(9, 3)));
        addUnit(new Unit(this, "TANK", "green", new DiscreteCoordinates(0, 9)));
        addUnit(new Unit(this, "ROCKET", "green", new DiscreteCoordinates(0, 8)));
        addUnit(new Unit(this, "SOLDIER", "green", new DiscreteCoordinates(0, 7)));
        //addUnit(new Unit(this, BUILDING, "green", new DiscreteCoordinates(2,2)));
    }
}
