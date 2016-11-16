package com.sh.lynn.hz.lehe.module.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

public class MainFrameTask {
    private Context mainFrame = null;
    private CustomProgressDialog progressDialog = null;
    private boolean isCancel = false;

//    private ProgressDialogFragment progressDialog= null;

    public MainFrameTask(Context mainFrame) {
        this.mainFrame = mainFrame;

    }

    public void setDialogCancel(boolean b) {
        isCancel = b;
    }

    public void startProgressDialog(String values) {

        progressDialog = new CustomProgressDialog();

        // progressDialog = ProgressDialogFragment.newInstance(values);
        Bundle args = new Bundle();

        args.putString("message", values);

        progressDialog.setArguments(args);

        progressDialog.show(((Activity) mainFrame).getFragmentManager(), "ProgressBar");

        progressDialog.setCancelable(isCancel);

    }

    public boolean getState() {
        if (progressDialog != null) {
            return progressDialog.getDialog().isShowing();
        }
        return false;
    }

    public void stopProgressDialog() {
        if (progressDialog != null) {
            //避免 java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState异常
            progressDialog.dismissAllowingStateLoss();

        }
    }


}