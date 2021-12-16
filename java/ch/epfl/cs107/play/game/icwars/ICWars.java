package ch.epfl.cs107.play.game.icwars;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icwars.actor.AIPlayer;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsPlayer;
import ch.epfl.cs107.play.game.icwars.actor.RealPlayer;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.game.icwars.area.icwars.Level0;
import ch.epfl.cs107.play.game.icwars.area.icwars.Level1;
import ch.epfl.cs107.play.game.icwars.area.icwars.Level2;
import ch.epfl.cs107.play.game.icwars.area.icwars.Menu;
import ch.epfl.cs107.play.io.DefaultFileSystem;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.io.ResourceFileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.*;
import ch.epfl.cs107.play.window.swing.SwingSound;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ICWars extends AreaGame {

    public final static float CAMERA_SCALE_FACTOR = 13.f;
    private final String[] areas = {"icwars/Level0", "icwars/Level1", "icwars/Level2"};

    Clip openingTrack = ICWars.loadClip("opening.wav");
    Clip menuTrack = ICWars.loadClip("battle-time.wav");
    Clip successTrack = ICWars.loadClip("success.wav");

    private int areaIndex;
    private List<ICWarsPlayer> players;

    private void createAreas(){
        addArea(new Menu("init"));
        addArea(new Menu("selector"));
        addArea(new Menu("gameover"));
        addArea(new Level0());
        addArea(new Level1());
        addArea(new Level2());
    }

    public boolean begin(Window window, FileSystem fileSystem){
        if (super.begin(window, fileSystem)) {
            createAreas();
            areaIndex = 0;
            initArea("icwars/init");
            loopClip(openingTrack, -20);
            return true;
        }
        return false;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Keyboard keyboard = getWindow().getKeyboard();

        if (getCurrentArea().getTitle().equals("icwars/init")) {
            if(keyboard.get(Keyboard.ENTER).isReleased()) {
                initArea("icwars/selector");
                openingTrack.stop();
                loopClip(menuTrack, -20);
            }
            return;
        }

        if (getCurrentArea().getTitle().equals("icwars/selector")) {
            areaIndex = -1;
            if (keyboard.get(Keyboard.ONE).isReleased())
                areaIndex = 0;
            else if (keyboard.get(Keyboard.TWO).isReleased())
                areaIndex = 1;
            else if (keyboard.get(Keyboard.THREE).isReleased())
                areaIndex = 2;
            if (areaIndex != -1) {
                initArea(areas[areaIndex]);
                menuTrack.stop();
            }
            return;
        }

        if (keyboard.get(Keyboard.R).isPressed()) {
            System.out.println("GAME RESET");
            createAreas();
            areaIndex = 0;
            initArea(areas[areaIndex]);
        }

        if (getCurrentArea().getTitle().equals("icwars/gameover"))
            return;

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
            players.get(0).leaveArea();
            players.remove(players.get(0));
            successTrack.setFramePosition(60);
            playClip(successTrack, 0f);
        }

        if(keyboard.get(Keyboard.N).isReleased()) {
            System.out.println("Advancing to next area");
            successTrack.stop();
            areaIndex = (areaIndex + 1);
            if(areaIndex >= areas.length){
                initArea("icwars/gameover");
                menuTrack.start();
                end();
            }
            else {
                initArea(areas[areaIndex]);
            }
        }
    }

    private void initArea(String areaKey) {
        ICWarsArea area = (ICWarsArea) setCurrentArea(areaKey, true);

        if (players != null)
            for (ICWarsPlayer player : players)
                player.leaveArea();
        players = new ArrayList<ICWarsPlayer>();

        if (areaKey.equals("icwars/init") || areaKey.equals("icwars/selector") || areaKey.equals("icwars/gameover"))
            return;

        // Add players based on area

        DiscreteCoordinates coords = new DiscreteCoordinates(3, 3);
        ICWarsPlayer player2 = new RealPlayer(area, Orientation.UP, coords, "blue");
        players.add(player2);

        ICWarsPlayer player1 = new AIPlayer(area, Orientation.UP, coords, "red");
        players.add(player1);

        if (areaKey == "icwars/Level1") {
            ICWarsPlayer player3 = new AIPlayer(area, Orientation.UP, coords, "yellow");
            players.add(player3);
        }

        if (areaKey == "icwars/Level2") {
            ICWarsPlayer player3 = new AIPlayer(area, Orientation.UP, coords, "yellow");
            players.add(player3);
            ICWarsPlayer player4 = new AIPlayer(area, Orientation.UP, coords, "green");
            players.add(player4);
        }

        for (int i = 0; i < players.size(); i++) {
            players.get(i).enterArea(area, coords);
            players.get(i).setNextPlayer(players.get((i + 1) % players.size()));
        }

        players.get(0).setState(ICWarsPlayer.GameState.WAITING_TURN);
    }

    @Override
    public String getTitle() {
        return "ICWars";
    }

    static public Clip loadClip(String name) {
        try {
            InputStream input = (new ResourceFileSystem(DefaultFileSystem.INSTANCE)).read("audio/" + name);
            SwingSound bgSound = new SwingSound(input);
            return bgSound.openedClip(0);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    static public void playClip(Clip clip, float gain) { playClip(clip, gain, 1); }
    static public void loopClip(Clip clip, float gain) { playClip(clip, gain, 1000); }

    static public void playClip(Clip clip, float gain, int count) {
        if (clip != null) {
            clip.loop(count);

            FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(gain);
        }
    }
}
