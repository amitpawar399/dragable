package com.destinyapps.dragableview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.destinyapps.dragableview.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    float dX;
    float dY;
    int lastAction;
    ArrayList<CustomLable> yLableList = new ArrayList<CustomLable>();
    ArrayList<CustomLable> xLableList = new ArrayList<CustomLable>();
    ImageView dragView;
    LinearLayout y_cordinator_line;
    LinearLayout x_cordinator_line;
    RelativeLayout graph_container;
    int ponterRadius = 30;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dragView = (ImageView)findViewById(R.id.draggable_image_view);
        graph_container = (RelativeLayout) findViewById(R.id.graph_container);
        x_cordinator_line = (LinearLayout) findViewById(R.id.x_cordinator_line);
        y_cordinator_line = (LinearLayout) findViewById(R.id.y_cordinator_line);
        dragView.setOnTouchListener(this);


        LinearLayout y_axis_container = (LinearLayout)findViewById(R.id.y_axis_container);
        LinearLayout x_axis_container = (LinearLayout)findViewById(R.id.x_axis_container);

        for(int i=0;i<3;i++){
            // y lable
            CustomLable yCustomLable = new CustomLable(this);
            yCustomLable.setText("Y - "+i);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0,0,0,300);
            yCustomLable.setLayoutParams(params);

            y_axis_container.addView(yCustomLable);
            yLableList.add(yCustomLable);

            // x lable
            CustomLable xCustomLable = new CustomLable(this);
            xCustomLable.setText("X - "+i);
            LinearLayout.LayoutParams paramsx = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            paramsx.setMargins(0,0,200,0);
            xCustomLable.setLayoutParams(paramsx);

            x_axis_container.addView(xCustomLable);
            xLableList.add(xCustomLable);
        }

    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                dX = view.getX() - event.getRawX();
                dY = view.getY() - event.getRawY();
                lastAction = MotionEvent.ACTION_DOWN;
                break;

            case MotionEvent.ACTION_MOVE:
                view.setY(event.getRawY() + dY);
                view.setX(event.getRawX() + dX);
                lastAction = MotionEvent.ACTION_MOVE;
                dragView.setImageDrawable(getDrawable(R.drawable.ic_launcher_foreground));
                // y range tracking
                for (CustomLable mCustomLable : yLableList) {

                    if(mCustomLable.inMyYRange((int)dragView.getY()+ponterRadius)){

                        dragView.setImageDrawable(getDrawable(R.drawable.ic_launcher));
                    }

                }
                //x range racking
                for (CustomLable mCustomLable : xLableList) {

                    if(mCustomLable.inMyXRange((int)dragView.getX()+ponterRadius)){

                        dragView.setImageDrawable(getDrawable(R.drawable.ic_launcher));
                    }
                }

                // cordinator lines
                drawXLineTracker(dragView);
                drawYLineTracker(dragView);
                break;

            case MotionEvent.ACTION_UP:
                if (lastAction == MotionEvent.ACTION_DOWN)
                    Toast.makeText(MainActivity.this, "Clicked!", Toast.LENGTH_SHORT).show();
                break;

            default:
                return false;
        }
        return true;
    }

    void drawXLineTracker(View view){

        x_cordinator_line.setX(view.getX()+ponterRadius*2);
        x_cordinator_line.setY(view.getY()+ponterRadius*2);

        FrameLayout.LayoutParams paramsx = new FrameLayout.LayoutParams(
                (int) ((graph_container.getWidth()-view.getX())+ponterRadius),
                2);

        x_cordinator_line.setLayoutParams(paramsx);
        x_cordinator_line.requestLayout();
        x_cordinator_line.setBackgroundColor(Color.BLACK);
    }
    void drawYLineTracker(View view){

        y_cordinator_line.setX(view.getX()+ponterRadius*2);
        y_cordinator_line.setY(view.getY()+ponterRadius*2);

        FrameLayout.LayoutParams paramsx = new FrameLayout.LayoutParams(
                2,
                (int) ((graph_container.getHeight()-view.getY())+ponterRadius));

        y_cordinator_line.setLayoutParams(paramsx);
        y_cordinator_line.requestLayout();
        y_cordinator_line.setBackgroundColor(Color.BLACK);
    }
}

