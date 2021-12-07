package ch.epfl.cs107.play.game.icwars;

import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsPlayer;
import ch.epfl.cs107.play.game.icwars.actor.RealPlayer;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.game.icwars.area.icwars.Level0;
import ch.epfl.cs107.play.game.icwars.area.icwars.Level1;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

import java.util.Objects;

public class ICWars extends AreaGame {

    public final static float CAMERA_SCALE_FACTOR = 13.f;
    private final String[] areas = {"icwars/Level0", "icwars/Level1"};

    private int areaIndex;
    private ICWarsPlayer player;

    private void createAreas(){
        addArea(new Level0());
        addArea(new Level1());
    }

    public boolean begin(Window window, FileSystem fileSystem){
        if (super.begin(window, fileSystem)) {
            createAreas();
            areaIndex = 0;
            initArea(areas[areaIndex]);
            return true;
        }
        return false;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        Keyboard keyboard = getWindow().getKeyboard();
        if(keyboard.get(Keyboard.N).isPressed()) {
            areaIndex = (areaIndex + 1);
            if(areaIndex >= areas.length){
                end();
                System.out.println("END");
            }
            else {
                initArea(areas[areaIndex]);
            }
        }
        else if(keyboard.get(Keyboard.R).isPressed()) {
            System.out.println("GAME RESET");
            createAreas();
            areaIndex = 0;
            initArea(areas[areaIndex]);
        }

        if (keyboard.get(Keyboard.U).isReleased()) {
            ((RealPlayer)player).selectUnit (1); // 0, 1 ...
        }
    }

    private void initArea(String areaKey) {
        ICWarsArea area = (ICWarsArea) setCurrentArea(areaKey, true);
        DiscreteCoordinates coords = new DiscreteCoordinates(5, 5);
        player = new RealPlayer(area, Orientation.UP, coords, "icwars/allyCursor");
        player.enterArea(area, coords);
    }

    @Override
    public String getTitle() {
        return "ICWars";
    }
}
