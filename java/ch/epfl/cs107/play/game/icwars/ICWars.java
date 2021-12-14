package ch.epfl.cs107.play.game.icwars;

import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icwars.actor.AIPlayer;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsPlayer;
import ch.epfl.cs107.play.game.icwars.actor.RealPlayer;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.game.icwars.area.icwars.Level0;
import ch.epfl.cs107.play.game.icwars.area.icwars.Level1;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Sound;
import ch.epfl.cs107.play.window.Window;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ICWars extends AreaGame {

    public final static float CAMERA_SCALE_FACTOR = 13.f;
    private final String[] areas = {"icwars/Level0", "icwars/Level1"};

    private int areaIndex;
    private List<ICWarsPlayer> players;

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

        List<ICWarsPlayer> defeatedPlayers = new ArrayList<>();
        for (ICWarsPlayer player : players)
            if (((ICWarsArea) getCurrentArea()).getFriendlyUnits(player.faction).isEmpty())
                defeatedPlayers.add(player);

        for (ICWarsPlayer player : defeatedPlayers) {
            System.out.println(player.faction + " lost!");
            players.remove(player);
            getCurrentArea().unregisterActor(player);
        }

        for (ICWarsPlayer player : players)
            while (!players.contains(player.getNextPlayer()))
                player.setNextPlayer(player.getNextPlayer().getNextPlayer());

        if (players.size() == 1) {
            System.out.println(players.get(0).faction + " won!");
            players.get(0).endTurn();
            getCurrentArea().unregisterActor(players.get(0));
            players.remove(players.get(0));
        }

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
        else if (keyboard.get(Keyboard.R).isPressed()) {
            System.out.println("GAME RESET");
            createAreas();
            areaIndex = 0;
            initArea(areas[areaIndex]);
        }
    }

    private void initArea(String areaKey) {
        ICWarsArea area = (ICWarsArea) setCurrentArea(areaKey, true);
        players = new ArrayList<ICWarsPlayer>();

        DiscreteCoordinates coords1 = new DiscreteCoordinates(4, 4);
        ICWarsPlayer player1 = new AIPlayer(area, Orientation.UP, coords1, "yellow");
        player1.enterArea(area, coords1);
        players.add(player1);

        DiscreteCoordinates coords2 = new DiscreteCoordinates(3, 3);
        ICWarsPlayer player2 = new AIPlayer(area, Orientation.UP, coords2, "green");
        player2.enterArea(area, coords2);
        players.add(player2);

        player1.setNextPlayer(player2);
        player2.setNextPlayer(player1);

        update(1);

        player2.setState(ICWarsPlayer.GameState.WAITING_TURN);
    }


    @Override
    public String getTitle() {
        return "ICWars";
    }
}
