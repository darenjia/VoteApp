package com.bokun.bkjcb.voteapp.View;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by DengShuai on 2018/6/8.
 * Description :
 */
public class ScannerView extends LinearLayout {

    private ScanView scanView;
    private ImageView imageView;

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
        int width = getWidth() * 13 / 18;
        scanView.layout(getLeft(), getTop(), getRight(), getBottom());
        imageView.layout(getLeft() + getWidth() - width / 2, (getTop() + getHeight() - width) / 2, getLeft() + getWidth() + width / 2, (getTop() + getHeight() - width) / 2);
    }

    private void init(Context context) {
        scanView = new ScanView(context);
        imageView = new ImageView(context);
        scanView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        int width = getWidth() * 13 / 18;
        imageView.setLayoutParams(new LayoutParams(width, 2));
    }
}
