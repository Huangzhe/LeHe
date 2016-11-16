/**************************************************************************************
 * [Project]
 *       MyProgressDialog
 * [Package]
 *       com.lxd.widgets
 * [FileName]
 *       CustomProgressDialog.java
 * [Copyright]
 *       Copyright 2012 LXD All Rights Reserved.
 * [History]
 *       Version          Date              Author                        Record
 *--------------------------------------------------------------------------------------
 *       1.0.0           2012-4-27         lxd (rohsuton@gmail.com)        Create
 **************************************************************************************/

package com.sh.lynn.hz.lehe.module.view;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.sh.lynn.hz.lehe.R;


public class CustomProgressDialog extends DialogFragment {
	private Context context = null;
	private CustomProgressDialog customProgressDialog = null;
	public ImageButton imageBtn = null;
	ImageView imageView;
	TextView tvMsg;
	AnimationDrawable animationDrawable;


	@Override
	public void onSaveInstanceState(Bundle outState) {

	}


	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		String message = getArguments().getString("message");

		Dialog customProgressDialog = new Dialog(getActivity(), R.style.MyDialog);

		customProgressDialog.setContentView(R.layout.load_dialog);

		TextView messageView = ((TextView) customProgressDialog.findViewById(R.id.dialog_loading));


		if (TextUtils.isEmpty(message)) {

			messageView.setText("正在加载...");
		} else {
			messageView.setText(message);
		}


		return customProgressDialog;
	}



}
