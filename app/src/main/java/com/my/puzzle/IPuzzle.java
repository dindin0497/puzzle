package com.my.puzzle;

/**
 * puzzle interface
 */
public interface IPuzzle {


    /**
     * ui refresh listener
     */
    interface onUIListener
    {
        void refresh(int pos);
    }

    /**
     * win listener
     */
    interface onWinListener
    {
        void onWin();
    }


    /**
     * set ui listener
     */
    void setUiListener(onUIListener uiListener);

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
     * shuffle the data
     */
    void shuffle();

    /**
     * process user click position event
     */
    void onClick(int pos, boolean check);

}
