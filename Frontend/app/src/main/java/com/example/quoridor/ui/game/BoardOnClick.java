package com.example.quoridor.ui.game;

/**
*
 * Simple interface that handles when a wall and tile are clicked on the corresponding QuoridorBoard element.
 * @author Carter Awbrey
* */
public interface BoardOnClick {

    /**
     * Called by the QuoridorBoard component when a wall is touched.
     *
    * @param dir direction of the wall
     * @param y y coordinate of the clicked wall
     * @param x x coordinate of the clicked wall
    * */
    public void clickWall(int x, int y, int dir);

    /**
     * Called by the QuoridorBoard component when a tile is touched.
     *
     * @param y y coordinate of the clicked tile
     * @param x x coordinate of the clicked tile
     * */
    public void clickTile(int x, int y);
}
