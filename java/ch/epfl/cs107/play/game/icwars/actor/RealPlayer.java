package ch.epfl.cs107.play.game.icwars.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Keyboard;

public class RealPlayer extends ICWarsPlayer {
    public RealPlayer(Area owner, Orientation orientation, DiscreteCoordinates coordinates, String faction) {
        super(owner, orientation, coordinates, faction);
    }

    public boolean isPressed(int key) {
        Keyboard keyboard = getOwnerArea().getKeyboard();

        if (getState() != GameState.ACTION && key != Keyboard.ENTER)
            return keyboard.get(key).isDown();
        else
            return keyboard.get(key).isPressed();
    }

    /**
     *
     * @param area (Area): initial area, not null
     * @param position (DiscreteCoordinates): initial position, not null
     */
    @Override
    public void enterArea(ICWarsArea area, DiscreteCoordinates position){
        super.enterArea(area, position);
        if (area.bgTrack != null)
            area.bgTrack.start();
    }
    @Override
    public void leaveArea(){
        System.out.println("Leaving area ");
        if (((ICWarsArea) getOwnerArea()).bgTrack != null)
            ((ICWarsArea) getOwnerArea()).bgTrack.stop();
        super.leaveArea();
    }

    public void updateController() { }
}
