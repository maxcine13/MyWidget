/*
 * project : Linji
 * author : maxinfeng
 * class : DialogBuilder.java
 * update:2020-06-12 16:52
 * last:2020-06-12 16:52
 * Copyright © 2020 临集网络技术有限公司
 */

package com.linji.cabinetutil.dia;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

public class DialogBuilder {

    private DialogOptions options;

    //Required
    public DialogBuilder(Context context) {
        options = new DialogOptions();
        options.context = context;
    }

    public DialogBuilder setTitle(CharSequence title) {
        options.title = title;
        return this;
    }

    public DialogBuilder setContent(CharSequence content) {
        options.content = content;
        return this;
    }

    public DialogBuilder setContentView(View view) {
        options.view = view;
        return this;
    }

    public DialogBuilder setLeft(CharSequence left) {
        options.left = left;
        return this;
    }

    public DialogBuilder setRight(CharSequence right) {
        options.right = right;
        return this;
    }
    public DialogBuilder setContentColor(int contentColor) {
        options.contentColor = contentColor;
        return this;
    }

    public DialogBuilder setLeftColor(int leftStartColor,int leftEndColor) {
        options.leftStartColor = leftStartColor;
        options.leftEndColor = leftEndColor;
        return this;
    }
    public DialogBuilder setRightColor(int rightStartColor,int rightEndColor) {
        options.rightStartColor = rightStartColor;
        options.rightEndColor = rightEndColor;
        return this;
    }
    public DialogBuilder setRightListener(CommonDialogConfirmListener listener) {
        options.confirmListener = listener;
        return this;
    }

    public DialogBuilder setLeftListener(CommonDialogCancelListener listener) {
        options.cancelListener = listener;
        return this;
    }

    public DialogBuilder setDialogWidth(int dialogWidth) {
        options.dialogWidth = dialogWidth;
        return this;
    }

    public DialogBuilder setDialogHeight(int dialogHeight) {
        options.dialogHeight = dialogHeight;
        return this;
    }
    public DialogBuilder setCountDownTime(int countDownTime){
        options.countDownTime = countDownTime;
        return this;
    }
    public DialogBuilder isCountDownTime(boolean isCountDownTime){
        options.isCountDownTime = isCountDownTime;
        return this;
    }
    public DialogBuilder isShowClose(boolean isShowClose){
        options.isShowClose = isShowClose;
        return this;
    }
    public DialogBuilder setDismissListener(CommonDialogDismissListener dismissListener) {
        this.options.dismissListener = dismissListener;
        return this;
    }
    public CommonDialog build() {
        return new CommonDialog(options);
    }

    class DialogOptions {
        Context context;
        CharSequence title, content, left, right;
        int contentColor, leftStartColor,leftEndColor,rightStartColor,rightEndColor;
        int dialogWidth, dialogHeight;
        int countDownTime = -1;
        boolean isCountDownTime,isShowClose = true;
        View view;
        CommonDialogConfirmListener confirmListener;
        CommonDialogCancelListener cancelListener;
        CommonDialogDismissListener dismissListener;
    }

    public interface CommonDialogConfirmListener {
        void onClick(Dialog dialog, View flag);
    }
    public interface CommonDialogCancelListener {
        void onClick(Dialog dialog, View flag);
    }
    public interface CommonDialogDismissListener {
        void onClick(DialogInterface flag);
    }
}
