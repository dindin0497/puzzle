package com.my.puzzle;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;


/**
 * puzzle ui
 */
public class PuzzleView extends GridLayout implements View.OnClickListener{

    private static final String TAG = PuzzleView.class.getSimpleName();

    private int rowNum;

    private TextView tvArray[];

    private IPuzzle puzzle;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.MSG_REFRESH_UI:
                    update(msg.arg1);
                    update(msg.arg2);
                    break;
                case Constants.MSG_REFRESH_ALL:
                    for (int i=0;i<tvArray.length;i++)
                        update(i);
                    break;
                case Constants.MSG_WIN:
                    new AlertDialog.Builder(getContext())
                            .setTitle(R.string.win_title)
                            .setMessage(R.string.win_text)
                            .setPositiveButton(R.string.ok, null)
                            .show();
                    break;
            }
        }
    };

    public PuzzleView(Context context) {
        super(context);
        setFocusable(true);
        setFocusableInTouchMode(true);

    }
    public PuzzleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public PuzzleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(widthMeasureSpec));
    }

    public void setData(IPuzzle puzzle)
    {
        this.puzzle=puzzle;
        puzzle.setUiHandler(handler);

        rowNum=puzzle.getRow();

        setRowCount(rowNum);
        setColumnCount(rowNum);

        tvArray=new TextView[puzzle.getSize()];

        for (int i=0;i<puzzle.getSize();i++)
        {
            TextView tv = new TextView(getContext());
            tvArray[i]=tv;

            update(i);

            tv.setTextSize(getResources().getDimension(R.dimen.text_view_font_size));
            tv.setGravity(Gravity.CENTER);
            tv.setOnClickListener(this);

            GridLayout.Spec rowSpec = GridLayout.spec(i / rowNum, 1f);
            GridLayout.Spec columnSpec = GridLayout.spec(i % rowNum, 1f);
            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(rowSpec, columnSpec);

            int margin=(int)getResources().getDimension(R.dimen.text_view_margin);
            layoutParams.setMargins(margin,margin,margin,margin);
            layoutParams.height = 0;
            layoutParams.width = 0;


            addView(tv, layoutParams);

        }

    }


    private void update(int pos)
    {
        if (pos>=tvArray.length)
            return;

        TextView tv=tvArray[pos];
        String str=puzzle.getText(pos);
        tv.setText("");

        if (str.isEmpty())
        {
            tv.setBackgroundColor(getResources().getColor(R.color.colorGreen));
        }
        else {
            tv.setText(str);
            tv.setBackgroundColor(getResources().getColor(R.color.colorYellow));
        }
    }
    @Override
    public void onClick(View v) {
        for (int i=0; i<tvArray.length; i++)
        {
            if (tvArray[i]==v)
            {
                Log.d(TAG,"onClick "+i);
                puzzle.onClick(i, true);

                break;
            }
        }
    }
}
