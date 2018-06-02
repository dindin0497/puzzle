package com.my.puzzle;


import android.os.Handler;

/**
 * puzzle interface
 */
public interface IPuzzle {

    /**
     * set ui handler
     */
    void newGame();

    /**
     * reset to initial state
     */
    void reset();

    /**
     * undo last step
     */
    boolean undo();

    /**
     * set ui handler
     */
    void setUiHandler(Handler handler);

    /**
     * get row number
     */
    int getRow();

    /**
     * get total length of data
     */
    int getSize();

    /**
     * get text of a position
     */
    String getText(int pos);


    /**
     * process user click position event
     */
    void onClick(int pos, boolean check);

}
