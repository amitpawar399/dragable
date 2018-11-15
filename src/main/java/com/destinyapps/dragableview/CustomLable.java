package com.destinyapps.dragableview;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.widget.TextView;

public class CustomLable extends AppCompatTextView {

    public CustomLable(Context context){
        super(context);
    }
    public Boolean inMyYRange(int yCordinate){
        Log.e("","y---"+this.getY()+"--yCordinate--"+yCordinate);
        if((this.getY()+50)>yCordinate&&(this.getY()-50)<yCordinate){
            return true;
        }else{
            return false;
        }

    }
    public Boolean inMyXRange(int xCordinate){

        if((this.getX()+50)>xCordinate&&(this.getX()-50)<xCordinate){
            return true;
        }else{
            return false;
        }

    }
}
