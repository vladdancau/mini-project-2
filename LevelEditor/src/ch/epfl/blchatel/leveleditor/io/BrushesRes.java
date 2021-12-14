package ch.epfl.blchatel.leveleditor.io;

/**
 * Brushes Resources "Table of Contents"
 * If we want to add brushes:
 *   1. Add the Backgrounds, Foregrounds and Behaviors Images into the resources folder
 *   2. Use ComputeBrushesRes program to compute this enum content
 *   3. Replace the current content by the computed one
 *   4. Build LevelEditor again
 */
public enum BrushesRes {

    // Do not edit before
    //////////////////////////////////////////////////////////////////////////////

    // Please Copy the following output to the enum BrushesRes
    // ...

    DIRT_1("dirt.1.png");


    //////////////////////////////////////////////////////////////////////////////
    // Do not edit after

    private final static String BRUSHES_BACKGROUND_PATH = "Brushes/Backgrounds/";
    private final static String BRUSHES_FOREGROUND_PATH = "Brushes/Foregrounds/";
    private final static String BRUSHES_BEHAVIOR_PATH = "Brushes/Behaviors/";

    public final String name, background, foreground, behavior;

    BrushesRes(String name){
        this.name = name;
        this.background = BRUSHES_BACKGROUND_PATH + name;
        this.foreground = BRUSHES_FOREGROUND_PATH + name;
        this.behavior = BRUSHES_BEHAVIOR_PATH + name;
    }
}
