package com.linji.cabinetutil.icondotextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.linji.cabinetutil.R;

/**
 * Created by assistne on 17/1/20.
 */
// TODO: 17/1/25 dot的颜色根据state变化?是否需要
public class DotConfig implements Config {
    private Context context;
    private static int DEFAULT_SIZE;
    @ColorInt
    private static final int DEFAULT_COLOR = Color.rgb(211, 47, 47);// #D32F2F
    private static int DEFAULT_TEXT_SIZE;
    @ColorInt
    private static final int DEFAULT_TEXT_COLOR = Color.WHITE;

    private static final boolean DEFAULT_DOT_RING = false;

    private int mSize;
    private boolean isRing;
    @ColorInt
    private int mColor, mDotTextColor;

    @Nullable
    TextConfig textConfig;
    private Paint mPaint;
    private int mMaxWidth = Integer.MAX_VALUE;
    private int mMaxHeight = Integer.MAX_VALUE;

    public DotConfig(Context context, TypedArray typedArray) {
        this.context = context;
        DEFAULT_SIZE = IconDotTextView.sDefaultDotSize;
        DEFAULT_TEXT_SIZE = IconDotTextView.sDefaultDotTextSize;
        if (typedArray != null) {
            mSize = typedArray.getDimensionPixelSize(R.styleable.IconDotTextView_dot_size, DEFAULT_SIZE);
            mColor = typedArray.getColor(R.styleable.IconDotTextView_dot_color, DEFAULT_COLOR);
            String text = typedArray.getString(R.styleable.IconDotTextView_dot_text);
            int textSize = typedArray.getDimensionPixelSize(R.styleable.IconDotTextView_dot_textSize, DEFAULT_TEXT_SIZE);
            mDotTextColor = typedArray.getColor(R.styleable.IconDotTextView_dot_textColor,DEFAULT_TEXT_COLOR);
            isRing = typedArray.getBoolean(R.styleable.IconDotTextView_dot_isRing, DEFAULT_DOT_RING);
            textConfig = new TextConfig(text, textSize, mDotTextColor);
            textConfig.setMaxHeight(mSize);
            textConfig.setMaxWidth(mSize);
        }
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mColor);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public int getHeight() {
        return Math.min(getDesiredHeight(), mMaxHeight);
    }

    @Override
    public int getWidth() {
        return Math.min(getDesiredWidth(), mMaxWidth);
    }

    @Override
    public int getDesiredHeight() {
        return mSize;
    }

    @Override
    public int getDesiredWidth() {
        return mSize;
    }

    @Override
    public void setMaxWidth(int maxWidth) {
        mMaxWidth = maxWidth;
        if (textConfig != null) {
            textConfig.setMaxWidth(Math.min(maxWidth, mSize));
        }
    }

    @Override
    public void setMaxHeight(int maxHeight) {
        mMaxHeight = maxHeight;
        if (textConfig != null) {
            textConfig.setMaxHeight(Math.min(maxHeight, mSize));
        }
    }

    // TODO: 17/1/25 状态没改变时返回false
    @Override
    public boolean setState(int[] state) {
        return false;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        if (textConfig != null) {
            canvas.save();
            int radius = mSize / 2;
            mPaint.setColor(mColor);
            mPaint.setAntiAlias(true);
            canvas.translate((getWidth() - mSize) / 2, (getHeight() - mSize) / 2);
            canvas.drawCircle(radius, radius, radius, mPaint);
            canvas.restore();
            if (isRing) {
                canvas.save();
                mPaint.setColor(Color.WHITE);
                canvas.translate((getWidth() - mSize) / 2, (getHeight() - mSize) / 2);
                canvas.drawCircle(radius, radius, radius - 2, mPaint);
                canvas.restore();
            }
            textConfig.setColor(isRing ? mColor : mDotTextColor);
            canvas.save();
            int tLeft = Math.max(0, (getWidth() - textConfig.getWidth()) / 2);
            int tTop = Math.max(0, (getHeight() - textConfig.getHeight()) / 2);
            canvas.translate(tLeft, tTop);
            canvas.clipRect(0, 0, getWidth() - tLeft, getHeight() - tTop);
            textConfig.draw(canvas);
            canvas.restore();
        }
    }

    public void setSize(int size) {
        if (mSize != size) {
            mSize = size;
            if (textConfig != null) {
                textConfig.setMaxHeight(Math.min(mMaxHeight, size));
                textConfig.setMaxWidth(Math.min(mMaxWidth, size));
            }
        }
    }

    public int getSize() {
        return mSize;
    }

    public void setColor(@ColorInt int color) {
        if (mColor != color) {
            mPaint.setColor(color);
        }
    }
}