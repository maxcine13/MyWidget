/*
 * project : Linji
 * author : maxinfeng
 * class : CommonDialog.java
 * update:2020-06-12 17:35
 * last:2020-06-12 15:09
 * Copyright © 2020 临集网络技术有限公司
 */

package com.linji.cabinetutil.dia;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.linji.cabinetutil.R;
import com.linji.cabinetutil.util.ScreenUtil;
import com.linji.cabinetutil.widget.BorderTextView;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class CommonDialog extends Dialog implements View.OnClickListener {

    private DialogBuilder.DialogOptions options;
    private int count;
    private TextView mCountDownTime;
    private Timer timer;
    private Handler mHandler = new Handler(msg -> {
        if (options.countDownTime != -1) {
            if (msg.what == options.countDownTime){
                if (timer != null)timer.cancel();
                count = options.countDownTime-1;
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        mHandler.sendEmptyMessage(count--);
                    }
                }, 0, 1000);
            }else if (msg.what == 0){
                dismiss();
            }else {
                mCountDownTime.setText(String.format(Locale.CHINA,"%ds", msg.what));
            }
        }
        return false;
    });

    CommonDialog(DialogBuilder.DialogOptions options) {
        super(options.context, R.style.alert_dialog);
        this.options = options;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dia_common);

        TextView mTitle = findViewById(R.id.dialog_title);
        TextView mContent = findViewById(R.id.dialog_content);

        mCountDownTime = findViewById(R.id.count_down_time);
        BorderTextView mCancel = findViewById(R.id.dialog_cancel);
        BorderTextView mConfirm = findViewById(R.id.dialog_confirm);
        LinearLayout container = findViewById(R.id.dialog_container);
        findViewById(R.id.close).setOnClickListener(v -> dismiss());

        mCancel.setOnClickListener(this);
        mConfirm.setOnClickListener(this);
        //设置dialog title

        if (options.isShowClose){
            findViewById(R.id.close).setVisibility(View.VISIBLE);
        }else {
            findViewById(R.id.close).setVisibility(View.GONE);
        }
        if (options.title == null) {
            mTitle.setVisibility(View.GONE);
        } else {
            mTitle.setText(options.title);
        }

        if (options.content == null) {
            mContent.setVisibility(View.GONE);
        }else {
            mContent.setText(options.content);
        }
        if (options.contentColor != 0) {
            mContent.setTextColor(options.contentColor);
        }
        if (options.leftStartColor != 0 && options.leftEndColor != 0) {
            mCancel.setGradientColor(options.leftStartColor,options.leftEndColor,0);
        }
        if (options.rightStartColor != 0 && options.rightEndColor != 0) {
            mConfirm.setGradientColor(options.rightStartColor,options.rightEndColor,0);
        }

        if (options.view != null) {
            mContent.setVisibility(View.GONE);
            container.addView(options.view);
        }

        if (options.left == null) {
            mCancel.setVisibility(View.GONE);
        } else {
            mCancel.setText(options.left);
        }

        if (options.right == null) {
            mConfirm.setVisibility(View.GONE);
        } else {
            mConfirm.setText(options.right);
        }
        if (options.countDownTime != -1) {
            mHandler.sendEmptyMessage(options.countDownTime);
        }
        mCountDownTime.setVisibility(options.isCountDownTime? View.VISIBLE: View.GONE);
        if (options.dismissListener != null){
            setOnDismissListener(dialog -> options.dismissListener.onClick(dialog));
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams params = window.getAttributes();
        if (options.dialogWidth != 0) {
            params.width = options.dialogWidth;
        } else {
            if (ScreenUtil.isPortraitScreen(getContext())) {
                params.width = ScreenUtil.dip2px(getContext(), 300);
            }else {
                params.width = ScreenUtil.dip2px(getContext(), 500);
            }
        }

        if (options.dialogHeight != 0) {
            params.height = options.dialogHeight;
        }
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

    @Override
    public void dismiss() {
        hideKeyboard();
        mHandler.removeCallbacksAndMessages(null);
        if (timer != null) {
            timer.cancel();
        }
        super.dismiss();
    }

    public void refreshTime(){
        if (options.countDownTime != -1) {
            mHandler.removeCallbacksAndMessages(null);
            if (timer != null) timer.cancel();
            mHandler.sendEmptyMessage(options.countDownTime);
        }
    }
    public void hideKeyboard(){
        View currentFocus = getCurrentFocus();
        if (currentFocus instanceof TextView){
            InputMethodManager mInputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (mInputMethodManager != null){
                mInputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
            }
        }
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.dialog_cancel) {
            if (options.cancelListener != null) {
                options.cancelListener.onClick(this, v);
            }
        } else if (id == R.id.dialog_confirm) {
            if (options.confirmListener != null) {
                options.confirmListener.onClick(this, v);
            }
        }
    }
}
