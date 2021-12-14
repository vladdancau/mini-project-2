package ch.epfl.cs107.play.game.icwars.area;

import ch.epfl.cs107.play.game.areagame.AreaBehavior;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsPlayer;
import ch.epfl.cs107.play.game.icwars.actor.RealPlayer;
import ch.epfl.cs107.play.game.icwars.gui.ICWarsInfoPanel;
import ch.epfl.cs107.play.game.icwars.gui.ICWarsPlayerGui;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

public class ICWarsBehavior extends AreaBehavior {

    public ICWarsCellType getCellType(int x, int y){
        return ((ICWarsCell) this.getCell(x, y)).type;
    }

    public enum ICWarsCellType {
        //https://stackoverflow.com/questions/25761438/understanding-bufferedimage-getrgb-output-values
        NONE(0,0), // Should never be used except // in the toType method
        ROAD(-16777216, 0), // the second value is the number // of defense stars
        PLAIN(-14112955, 1),
        WOOD(-65536, 3),
        RIVER(-16776961, 0),
        MOUNTAIN(-256, 4),
        CITY(-1,2);

        final int type;
        private int defenseStars = 0;

        ICWarsCellType(int type, int defenseStars){
            this.type = type;
            this.defenseStars = defenseStars;
        }

        public static ICWarsCellType toType(int type){
            for(ICWarsCellType ict : ICWarsCellType.values()){
                if(ict.type == type)
                    return ict;
            }
            // When you add a new color, you can print the int value here before assign it to a type
            System.out.println(type);
            return null;
        }

        public String typeToString() { return this.toString(); }

        public int getDefenseStar() {
            return defenseStars;
        }

    }
    /**
     * Default Tuto2Behavior Constructor
     * @param window (Window), not null
     * @param name (String): Name of the Behavior, not null
     */
    public ICWarsBehavior(Window window, String name){
        super(window, name);
        int height = getHeight();
        int width = getWidth();
        for(int y = 0; y < height; y++) {
            for (int x = 0; x < width ; x++) {
                ICWarsCellType color = ICWarsCellType.toType(getRGB(height-1-y, x));
                setCell(x,y, new ICWarsCell(x,y,color));
            }
        }
    }

    /**
     * Cell adapted to the Tuto2 game
     */
    public class ICWarsCell extends AreaBehavior.Cell {
        /// Type of the cell following the enum
        private final ICWarsCellType type;

        /**
         * Default Tuto2Cell Constructor
         * @param x (int): x coordinate of the cell
         * @param y (int): y coordinate of the cell
         * @param type (EnigmeCellType), not null
         */
        public ICWarsCell(int x, int y, ICWarsCellType type){
            super(x, y);
            this.type = type;
        }

        @Override
        protected boolean canLeave(Interactable entity) {
            return true;
        }

        @Override
        protected boolean canEnter(Interactable entity) {
            if (entity instanceof ICWarsPlayer)
                return true;
          
            if(this.type == ICWarsCellType.RIVER || this.type == ICWarsCellType.CITY)
                return false;

            return true;
        }

        @Override
        public boolean isCellInteractable() {
            return true;
        }

        @Override
        public boolean isViewInteractable() {
            return true;
        }

        @Override
        public void acceptInteraction(AreaInteractionVisitor v) {
            System.out.println(this.type);
        }

    }
}