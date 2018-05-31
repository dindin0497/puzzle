package com.my.puzzle;

import android.util.Log;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Puzzle implements IPuzzle{

    private static final String TAG = Puzzle.class.getSimpleName();

    private int rowNum;

    private int missingNum;
    private int missingNumIndex;

    private int[] data;

    private Set<Integer> neighbourSet=new HashSet<>();

    int direction[];

    onWinListener winListener;

    IPuzzle.onUIListener uiListener;




    public Puzzle(int row, onWinListener listener)
    {
        winListener=listener;
        rowNum=row;
        data=new int[rowNum*rowNum];
        for (int i=0; i<data.length; i++)
            data[i]=i+1;
        missingNum=data.length;
        missingNumIndex=data.length-1;

        direction=new int[]{-rowNum,rowNum,-1,1};
        getNeighbour();


    }



    public void setUiListener(IPuzzle.onUIListener uiListener) {
        this.uiListener = uiListener;
    }

    private void getNeighbour()
    {
        neighbourSet.clear();

        for (int i: direction)
        {
            int pos=missingNumIndex+i;
            if (isValid(pos))
                neighbourSet.add(pos);
        }
//        Log.d(TAG,"neighbour "+missingNumIndex+ ": "+neighbourSet);
    }

    private boolean isValid(int pos)
    {
        return pos>=0 && pos<data.length;
    }

    private void debug()
    {
        Log.d(TAG, Arrays.toString(data));
    }

    public int getRow() {
        return rowNum;
    }

    public int getSize() {
        return data.length;
    }
    public String getText(int pos)
    {
        if (pos>=data.length)
            return "";
        return data[pos]!=missingNum?String.valueOf(data[pos]):"";

    }

    public void onClick(int pos, boolean check)
    {
        if (!neighbourSet.contains(pos))
            return ;

        swap(missingNumIndex,pos);

        uiListener.refresh(missingNumIndex);
        uiListener.refresh(pos);

        missingNumIndex=pos;
        getNeighbour();
        debug();

        if (check)
            checkWin();

    }

    public void swap(int i, int j)
    {
        int tmp=data[i];
        data[i]=data[j];
        data[j]=tmp;
    }

    public int[] getData() {
        return data;
    }

    private void checkWin()
    {
        for (int i=1; i<data.length; i++)
        {
            if (data[i]-data[i-1]!=1)
                return;
        }
        winListener.onWin();
    }

    public void shuffle()
    {
        int pre=missingNumIndex;
        Random rand=new Random();
        for (int i=0;i<Constants.SHUFFLE_STEP;i++) {
            Integer ary[] = neighbourSet.toArray(new Integer[neighbourSet.size()]);
            int idx = rand.nextInt(ary.length);

            int pos = ary[idx];
            if (pos == pre)
                continue;

            pre = pos;
            onClick(pos,false);
        }
    }

}
