package com.linji.cabinetutil.icondotextview;

import android.graphics.Canvas;

import androidx.annotation.NonNull;

public interface Config {
    int getHeight();

    int getWidth();

    int getDesiredHeight();

    int getDesiredWidth();

    void setMaxWidth(int maxWidth);

    void setMaxHeight(int maxHeight);

    boolean setState(int[] state);

    void draw(@NonNull Canvas canvas);
}