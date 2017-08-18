package com.codejam.gradientprogressbar.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.codejam.gradientprogressbar.R;

/**
 * Project GradientProgressBar.
 * <p>
 * Created by Rhony Abdullah Siagian on 8/18/17.
 */
public class GradientProgressBar extends View {

    /**
     * Segment color
     */
    private static int[] SECTION_COLORS = null;

    /**
     * The maximum value of the progress bar
     */
    private float maxCount;
    /**
     * The current value of the progress bar
     */
    private float currentCount;

    /**
     * Brush, pencil or whatever we call it working like a pencil which is draw something
     */
    private Paint mPaint;

    /**
     * Rectangle float for progress color
     */
    private RectF rectProgress;

    private int mWidth, mHeight;

    /**
     * Padding for progress
     */
    private int progressPaddingLeft = 20, progressPaddingBottom = 20;

    @SuppressLint("NewApi")
    public GradientProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint();
        rectProgress = new RectF();

        initProgressColorsOrange(context);

        setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_square_rect));

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GradientProgressView);
        mHeight = typedArray.getDimensionPixelSize(R.styleable.GradientProgressView_height, 0);
        progressPaddingBottom = typedArray.getDimensionPixelSize(R.styleable.GradientProgressView_padding_bottom, progressPaddingBottom) / 2;
        progressPaddingLeft = typedArray.getDimensionPixelSize(R.styleable.GradientProgressView_padding_left, progressPaddingLeft) / 2;
        setMaxCount(typedArray.getFloat(R.styleable.GradientProgressView_maxCount, 0));
        setCurrentCount(typedArray.getFloat(R.styleable.GradientProgressView_currentCount, 0));
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int round = mHeight / 2;

//        The height of the gradient progress bar
        int locationHeight = mHeight - progressPaddingBottom;

//        The width of the gradient progress bar
        int locationWidth = mWidth - progressPaddingLeft;

//        Current percentage of progress bars
        float section = currentCount / maxCount;

        float unit_section = 1f / SECTION_COLORS.length;

//        color_size not greater than available array colors
        int colors_size = (int) ((section / unit_section) + 1);
        if (colors_size > SECTION_COLORS.length) {
            colors_size = SECTION_COLORS.length;
        }

        float rectTopPbBg = mHeight - locationHeight;
        float rectRightPbBg = locationWidth * section;
        float rectLeftPbBg = progressPaddingLeft;

        rectProgress.set(rectLeftPbBg, rectTopPbBg, rectRightPbBg, (float) locationHeight);

        @SuppressLint("DrawAllocation")
        int[] currentColors = new int[colors_size];

        if (section > 0) {

            System.arraycopy(SECTION_COLORS, 0, currentColors, 0, colors_size);

            if (colors_size < 2) {
                mPaint.setColor(currentColors[0]);
            } else {
//                colors The number must be greater than two
                @SuppressLint("DrawAllocation")
                LinearGradient shader = new LinearGradient(rectLeftPbBg, rectTopPbBg, rectRightPbBg, (float) locationHeight, currentColors, null, Shader.TileMode.REPEAT);
                mPaint.setShader(shader);
            }
        } else if (section == 0) {
            mPaint.setColor(Color.TRANSPARENT);
        }

        canvas.drawRoundRect(rectProgress, round, round, mPaint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthSpecMode == MeasureSpec.EXACTLY || widthSpecMode == MeasureSpec.AT_MOST) {
            mWidth = widthSpecSize;
        } else {
            mWidth = 0;
        }

//        getting height from default android:layout_height for initial runtime.
        if (mHeight == 0) {
            if (heightSpecMode == MeasureSpec.AT_MOST || heightSpecMode == MeasureSpec.UNSPECIFIED) {
                float scale = getContext().getResources().getDisplayMetrics().density;
                int dip = getHeight();
                mHeight = (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
            } else {
                mHeight = heightSpecSize;
            }
        }

        setMeasuredDimension(mWidth, mHeight);
    }

    /***
     * @param maxCount set maximum progress value for progress bar
     */
    public void setMaxCount(float maxCount) {
        this.maxCount = maxCount;
    }

    /***
     * @param currentCount set current value into the Progress bar
     */
    public void setCurrentCount(float currentCount) {
        this.currentCount = currentCount > maxCount ? maxCount : currentCount;
        invalidate();
    }

    private void initProgressColorsOrange(Context context) {
        SECTION_COLORS = new int[10];
        SECTION_COLORS[0] = ContextCompat.getColor(context, R.color.orange_900);
        SECTION_COLORS[1] = ContextCompat.getColor(context, R.color.orange_900);
        SECTION_COLORS[2] = ContextCompat.getColor(context, R.color.orange_800);
        SECTION_COLORS[3] = ContextCompat.getColor(context, R.color.orange_800);
        SECTION_COLORS[4] = ContextCompat.getColor(context, R.color.orange_700);
        SECTION_COLORS[5] = ContextCompat.getColor(context, R.color.orange_600);
        SECTION_COLORS[6] = ContextCompat.getColor(context, R.color.orange_500);
        SECTION_COLORS[7] = ContextCompat.getColor(context, R.color.orange_400);
        SECTION_COLORS[8] = ContextCompat.getColor(context, R.color.orange_300);
        SECTION_COLORS[9] = ContextCompat.getColor(context, R.color.orange_300);
    }
}
