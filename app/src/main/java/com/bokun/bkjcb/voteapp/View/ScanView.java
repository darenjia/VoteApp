package com.bokun.bkjcb.voteapp.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.bokun.bkjcb.voteapp.R;

/**
 * Created by DengShuai on 2018/6/7.
 * Description :
 */
public class ScanView extends View {

    private Paint paint;
    private Shader shader;
    private Bitmap bitmap;

    public ScanView(Context context) {
        super(context);
        init();
    }

    public ScanView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScanView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ScanView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(getResources().getColor(R.color.colorAccent));

        paint.setShader(shader);
        canvas.drawRoundRect(getLeft(), getTop(), getRight(), getBottom(), 40, 40, paint);
        paint.setAlpha(1);
        paint.setShader(null);
        paint.setColor(getResources().getColor(R.color.textColor));
        int length = getWidth() * 2 / 3;
        int start_rx = (getLeft() + getWidth() - length) / 2;
        int start_ry = (getTop() + getHeight() - length) / 2;
        int stop_rx = (getLeft() + getWidth() + length) / 2;
        int stop_ry = (getTop() + getHeight() + length) / 2;

        canvas.drawRect(start_rx, start_ry, stop_rx, stop_ry, paint);

        canvas.drawBitmap(bitmap, null, new Rect(start_rx, start_ry, stop_rx, stop_ry), paint);
        paint.setColor(getResources().getColor(R.color.textSecondGary));
        paint.setStrokeWidth(4);
        float line_length = length / 8;
        float space_length = length / 12;
        canvas.drawLine(start_rx + space_length, start_ry + space_length, start_rx + space_length + line_length, start_ry + space_length, paint);
        canvas.drawLine(start_rx + space_length, start_ry + space_length, start_rx + space_length, start_ry + space_length + line_length, paint);
        canvas.drawLine(stop_rx - space_length, start_ry + space_length, stop_rx - space_length - line_length, start_ry + space_length, paint);
        canvas.drawLine(stop_rx - space_length, start_ry + space_length, stop_rx - space_length, start_ry + line_length + space_length, paint);
        canvas.drawLine(start_rx + space_length, stop_ry - space_length, start_rx + space_length + line_length, stop_ry - space_length, paint);
        canvas.drawLine(start_rx + space_length, stop_ry - space_length, start_rx + space_length, stop_ry - space_length - line_length, paint);
        canvas.drawLine(stop_rx - space_length, stop_ry - space_length, stop_rx - space_length - line_length, stop_ry - space_length, paint);
        canvas.drawLine(stop_rx - space_length, stop_ry - space_length, stop_rx - space_length, stop_ry - line_length - space_length, paint);
        //paint.setColor(getResources().getColor(R.color.orange));
        //canvas.drawLine(start_rx+space_length,start_ry+length/2,stop_rx-space_length,start_ry+length/2,paint);

    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        shader = new LinearGradient(100, 100, 500, 500, Color.parseColor("#80cbc4"),
                Color.parseColor("#03A9F4"), Shader.TileMode.CLAMP);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.qcode);
    }
}
