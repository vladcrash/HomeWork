package ru.tinkoff.school.homework;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Влад on 01.11.2017.
 */

public class Plot extends View {
    private static final int NUMBER_OF_UNITS = 6;

    private Paint mPlotPaint;
    private Paint mLinePaint;
    private Path mPath;
    private RectF mBounds;
    private List<PointF> mDots;
    private List<PointF> mScaleDots;
    private int mLineColor;
    private int mHeight;
    private int mWidth;
    private float oneStepXAdjusted;
    private float oneStepYAdjusted;
    private String mLabelY;
    private String mLabelX;

    public Plot(Context context) {
        this(context, null);
    }

    public Plot(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Plot, 0 , 0);
        mLineColor = a.getColor(R.styleable.Plot_lineColor, 0xff000000);
        mLabelY = a.getString(R.styleable.Plot_labelY);
        mLabelX = a.getString(R.styleable.Plot_labelX);
        init();
    }

    public void setDots(List<PointF> dots) {
        mDots = dots;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
        mWidth = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft();
        mHeight = MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop();
        mBounds.set(getPaddingLeft(), getPaddingTop(), mWidth, mHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float oneStepX = (mWidth - getPaddingRight()) / NUMBER_OF_UNITS;
        float oneStepY = (mHeight - getPaddingBottom()) / NUMBER_OF_UNITS;
        oneStepYAdjusted =  Math.round(mDots.get(mDots.size() - 1).y / NUMBER_OF_UNITS);
        oneStepXAdjusted = (float) Math.ceil(mDots.get(mDots.size() - 1).x / NUMBER_OF_UNITS);
        for (PointF dot : mDots) {
            if(dot.x == 0) {
                dot.x += mBounds.left;
            } else {
                dot.x *= oneStepX / oneStepXAdjusted;
                dot.x += mBounds.left;
            }

            if(dot.y == 0) {
                dot.y += mHeight;
            } else {
                dot.y = mHeight - (dot.y * oneStepY / oneStepYAdjusted);
            }
        }

        mScaleDots.add(new PointF(mBounds.left, mHeight));
        for (int i = 1; i < NUMBER_OF_UNITS + 1; i++) {
            mScaleDots.add(new PointF(mScaleDots.get(i - 1).x + oneStepX,
                    mScaleDots.get(i - 1).y - oneStepY));
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPath.reset();
        mPath.moveTo(getPaddingRight(), mHeight);
        for (PointF dot : mDots) {
            mPath.lineTo(dot.x, dot.y);
        }

        canvas.drawPath(mPath, mLinePaint);
        canvas.drawRect(mBounds, mPlotPaint);

        mPlotPaint.setStrokeWidth(3);
        mPlotPaint.setTextSize(30);
        mPlotPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(mLabelY, mBounds.right + getPaddingTop() / 4, (mHeight - getPaddingTop()) / 2 + getPaddingTop(), mPlotPaint);

        mPlotPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(mLabelX, (mWidth - getPaddingLeft()) / 2 + getPaddingLeft(), mBounds.top - getPaddingTop() / 4, mPlotPaint);

        float Y = 0;
        float X = 0;
        for (PointF dot : mScaleDots) {
            mPlotPaint.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText(Double.toString(Y), mBounds.left -  getPaddingTop() / 4, dot.y, mPlotPaint);
            mPlotPaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(Double.toString(X), dot.x, mBounds.bottom + getPaddingTop() / 2, mPlotPaint);
            Y += oneStepYAdjusted;
            X += oneStepXAdjusted;
        }
    }

    private void init() {
        mPlotPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPlotPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        mPlotPaint.setStrokeWidth(10);
        mPlotPaint.setStyle(Paint.Style.STROKE);

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(mLineColor);
        mLinePaint.setStrokeWidth(10);
        mLinePaint.setStyle(Paint.Style.STROKE);

        mPath = new Path();
        mBounds = new RectF();
        mDots = new ArrayList<>();
        mScaleDots = new ArrayList<>();
    }
}
