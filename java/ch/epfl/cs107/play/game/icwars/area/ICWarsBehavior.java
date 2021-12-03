package ch.epfl.cs107.play.game.icwars.area;

import ch.epfl.cs107.play.game.areagame.AreaBehavior;
import ch.epfl.cs107.play.window.Window;

public class ICWarsBehavior extends AreaBehavior {
    /**
     * Default AreaBehavior Constructor
     *
     * @param window (Window): graphic context, not null
     * @param name   (String): name of the behavior image, not null
     */
    public ICWarsBehavior(Window window, String name) {
        super(window, name);
    }

    public enum ICWarsCellType{
        NONE(0,0), // Should never be used except // in the toType method
        ROAD(-16777216, 0), // the second value is the number // of defense stars
        PLAIN(-14112955, 1),
        WOOD(-65536, 3),
        RIVER(-16776961, 0),
        MOUNTAIN(-256, 4),
        CITY(-1,2);

        final int type;
        final boolean isWalkable;

        ICWarsCellType(int type, boolean isWalkable){
            this.type = type;
            this.isWalkable = isWalkable;
        }

    }
    public class ICWarsCell{

    }
}
