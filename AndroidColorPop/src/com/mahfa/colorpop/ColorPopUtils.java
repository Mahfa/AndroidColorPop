package com.mahfa.colorpop;

import android.content.Context;

public class ColorPopUtils {
	public static int getStatusBarHeightPixles(Context context) {
		int status_bar_height = 0;
		int resourceId = context.getResources().getIdentifier(
				"status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			status_bar_height = context.getResources().getDimensionPixelSize(
					resourceId);
		}
		return status_bar_height;
	}
}
