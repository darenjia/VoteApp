package com.bokun.bkjcb.voteapp.View;

import android.content.Context;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bokun.bkjcb.voteapp.R;

/**
 * Created by DengShuai on 2018/6/8.
 * Description :
 */
public class ScannerView extends FrameLayout {

    private ScanView scanView;
    private TextView textView;
    private int width;

    public ScannerView(Context context) {
        super(context);
        init(context);
    }


    public ScannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ScannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        width = getWidth()* 11 / 18;
        scanView.layout(getLeft(), getTop(), getRight(), getBottom());
        textView.layout(getLeft()+(getWidth()- width)/2, (getBottom()-getTop())/2, getLeft()+ width +(getWidth()- width)/2, (getBottom()-getTop())/2+10);
    }

    private void init(Context context) {
        scanView = new ScanView(context);
        textView = new TextView(context);
        scanView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        int width = getWidth() * 13 / 18;
        //textView.setLayoutParams(new LayoutParams(width, 20));
        textView.setBackgroundColor(getResources().getColor(R.color.textPrimary));
        addView(scanView);
        addView(textView);
    }

    @Override
    protected void onAnimationStart() {
        super.onAnimationStart();
    }
    public void startAnimate(){
        Animation animation = new TranslateAnimation(0, 0, -width / 2, width/ 2);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setDuration(2000);
        animation.setInterpolator(new FastOutSlowInInterpolator());
        textView.setAnimation(animation);
        animation.start();
    }
    public void stopAnimate(){

    }
}
