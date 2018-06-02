package com.my.puzzle;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.lang.ref.WeakReference;
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

    WeakReference<Handler> uiHandlerReference;


    public Puzzle(int row)
    {
        rowNum=row;
        data=new int[rowNum*rowNum];

        direction=new int[]{-rowNum,rowNum,-1,1};

        init();
    }

    protected void init()
    {
        for (int i=0; i<data.length; i++)
            data[i]=i+1;
        missingNum=data.length;
        missingNumIndex=data.length-1;

        getNeighbour();

    }

    @Override
    public void newGame() {
        init();
        if (uiHandlerReference.get()!=null)
            uiHandlerReference.get().sendEmptyMessage(Constants.MSG_REFRESH_ALL);
        shuffle();
    }

    @Override
    public void reset() {

    }

    @Override
    public boolean undo() {
        return false;
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

    @Override
    public void setUiHandler(Handler handler) {
        uiHandlerReference=new WeakReference<Handler>(handler);
    }

    public void onClick(int pos, boolean check)
    {
        if (!neighbourSet.contains(pos))
            return ;

        swap(missingNumIndex,pos);

        Message msg=new Message();
        msg.what=Constants.MSG_REFRESH_UI;
        msg.arg1=missingNumIndex;
        msg.arg2=pos;

        final Handler handler=uiHandlerReference.get();
        if (handler!=null)
            handler.sendMessage(msg);

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
        final Handler handler=uiHandlerReference.get();
        if (handler!=null)
            handler.sendEmptyMessage(Constants.MSG_WIN);
    }

    private void shuffle()
    {

        new Thread(){
            @Override
            public void run()  {
                inner_shuffle();
            }
        }.start();
    }

    private void inner_shuffle()
    {
        int pre=-1;
        Random rand=new Random();
        try {
            int i=0;
            while (i < Constants.SHUFFLE_STEP ) {
                if (uiHandlerReference.get()==null)
                {
                    Log.d(TAG,"Activity already destroy");
                    return;
                }
                Integer ary[] = neighbourSet.toArray(new Integer[neighbourSet.size()]);
                int idx = rand.nextInt(ary.length);

                int pos = ary[idx];
                if (pos == pre)
                    continue;

                i++;
                Log.d(TAG,pre+" - "+pos);

                pre=missingNumIndex;

                onClick(pos, false);
                Thread.sleep(50);

            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

}
