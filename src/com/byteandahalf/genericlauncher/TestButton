package com.byteandahalf.genericlauncher;

import android.app.*;
import android.content.*;
import android.graphics.drawable.*;
import android.view.*;
import android.widget.*;

public class TestButton extends PopupWindow {

	public Button mainButton;

	public TestButton(Activity activity) {
		super(activity);
		setContentView(activity.getLayoutInflater().inflate(R.layout.testbutton, null));
		mainButton = (Button) getContentView().findViewById(R.id.testbutton);
		setBackgroundDrawable(new ColorDrawable(0x77ffffff));
		setWidth(100);
		setHeight(50);
		mainButton.setWidth(100);
		mainButton.setHeight(50);
	}

	public void show(View parentView) {
		showAtLocation(parentView, Gravity.RIGHT | Gravity.TOP, 0, 0);
	}

}
