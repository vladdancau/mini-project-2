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

    DIRT_1("dirt.1.png"),
    DIRT_BORDER_1("dirt.border.1.png"),
    DIRT_BORDER_10("dirt.border.10.png"),
    DIRT_BORDER_11("dirt.border.11.png"),
    DIRT_BORDER_12("dirt.border.12.png"),
    DIRT_BORDER_2("dirt.border.2.png"),
    DIRT_BORDER_3("dirt.border.3.png"),
    DIRT_BORDER_4("dirt.border.4.png"),
    DIRT_BORDER_5("dirt.border.5.png"),
    DIRT_BORDER_6("dirt.border.6.png"),
    DIRT_BORDER_7("dirt.border.7.png"),
    DIRT_BORDER_8("dirt.border.8.png"),
    DIRT_BORDER_9("dirt.border.9.png"),
    DOOR_CLOSE_1("door.close.1.png"),
    DOOR_CLOSE_2("door.close.2.png"),
    DOOR_OPEN_1("door.open.1.png"),
    DOOR_OPEN_2("door.open.2.png"),
    DOOR_OPEN_3("door.open.3.png"),
    DOOR_OPEN_4("door.open.4.png"),
    FENCE_1("fence.1.png"),
    FENCE_2("fence.2.png"),
    FENCE_3("fence.3.png"),
    FENCE_4("fence.4.png"),
    FENCE_5("fence.5.png"),
    FENCE_6("fence.6.png");


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
