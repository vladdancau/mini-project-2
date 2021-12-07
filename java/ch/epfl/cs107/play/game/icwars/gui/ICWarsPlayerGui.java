package ch.epfl.cs107.play.game.icwars.gui;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsPlayer;
import ch.epfl.cs107.play.game.icwars.actor.RealPlayer;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Image;

public class ICWarsPlayerGui implements Graphics {
    private RealPlayer player;

    public ICWarsPlayerGui(RealPlayer player) {
        this.player = player;
    }

    @Override
    public void draw(Canvas canvas) {
        player.drawSelectedUnitRange(canvas);
    }
}
