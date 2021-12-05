package ch.epfl.cs107.play.game.icwars;

import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.window.Window;

public class ICWars extends AreaGame {

    public final static float CAMERA_SCALE_FACTOR = 13.f;
    private final String[] areas = {"icwars/Level0", "icwars/Level1"};

    private int areaIndex;

    private void createAreas(){
        //De completat o data cu crearea zonelor
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

    private void initArea(String areaKey) {
        // La fel

    }

    @Override
    public String getTitle() {
        return "ICWars";
    }
}