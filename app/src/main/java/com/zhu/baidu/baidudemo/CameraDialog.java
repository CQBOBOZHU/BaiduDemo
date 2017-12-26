package com.zhu.baidu.baidudemo;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.zhu.baidu.R;


public class CameraDialog {

    View rootView;
    private Context context;

    public CameraDialog(Context context, int layoutId) {
        this.context = context;
        inite(layoutId);
    }



    Dialog dialog;

    public void inite(int layoutId) {

        dialog = new Dialog(context, R.style.style_dialog);
        rootView = LayoutInflater.from(context).inflate(layoutId, null);
        dialog.setContentView(rootView);
        dialog.show();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setGravity(Gravity.BOTTOM);
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
//        dialog.setCanceledOnTouchOutside(false);
    }

    /**
     * 改变“隐藏”按钮为“显示”
     *
     * @param id
     * @param txt
     */
    public void changeBtTxt(int id, String txt) {
        ((TextView) getView(id)).setText(txt);
    }

    /**
     * 显示“隐藏”按钮
     *
     * @param isShow
     * @param id
     */
    public void showHideBt(int id, boolean isShow) {
        View view = getView(id);
        if (isShow) {
            if (view.getVisibility() != View.VISIBLE) {
                view.setVisibility(View.VISIBLE);
            }
        } else {
            if (view.getVisibility() != View.GONE) {
                view.setVisibility(View.GONE);
            }
        }
    }

    public <T extends View> T getView(int id) {
        T v = (T) rootView.findViewById(id);
        return v;
    }

    public void setOnClickListener(View.OnClickListener onClickListener, int... ids) {
        for (int id : ids) {
            getView(id).setOnClickListener(onClickListener);
        }
    }


    public void show() {
        if (dialog!=null&&!dialog.isShowing()) {
            dialog.show();
        }
    }


    public void disMiss() {
        if (dialog!=null&&dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
