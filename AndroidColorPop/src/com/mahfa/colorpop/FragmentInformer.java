package com.mahfa.colorpop;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

public class FragmentInformer {
	public static final int MODE_LEFT_EDGE = 0;
	public static final int MODE_CENTER = 1;
	public static final int MODE_RIGHT_EDGE = 2;
	private int circle_color;
	private int page_color;
	private boolean is_behind_statusbar = false;
	private int statusbar_height = 0;
	private int x = 0;
	private int y = 0;

	public FragmentInformer(Context context) {
		statusbar_height = ColorPopUtils.getStatusBarHeightPixles(context);
	}

	public void setCircleColor(int color) {
		circle_color = color;

	}

	public void setPageColor(int color) {
		page_color = color;

	}

	public void setBaseView(final View view, int mode , boolean is_views_behind_statusbar) {
		is_behind_statusbar = is_views_behind_statusbar;
		int width = view.getWidth();
		int height = view.getHeight();
		int[] layout_location = { 0, 0 };
		view.getLocationOnScreen(layout_location);
		if (mode == MODE_LEFT_EDGE) {
			x = layout_location[0] + width / 10;
		} else if (mode == MODE_CENTER) {
			x = layout_location[0] + (width / 2);
		} else if (mode == MODE_RIGHT_EDGE) {
			x = layout_location[0] + (width - (width / 10));
		}
		y = layout_location[1] + (height / 2);
		if (!is_behind_statusbar) {
			y -= statusbar_height;
		}
	}

	public void informColorPopFragment(Fragment fragment) {
		Bundle arguments = new Bundle();
		arguments.putInt(ColorPopFragment.CIRCLE_COLOR_KEY, circle_color);
		arguments.putInt(ColorPopFragment.START_POINT_X_KEY, x);
		arguments.putInt(ColorPopFragment.START_POINT_Y_KEY, y);
		fragment.setArguments(arguments);
	}

	public void informColorPopPageFragment(Fragment fragment) {
		Bundle arguments = new Bundle();
		arguments.putInt(ColorPopPageFragment.CIRCLE_COLOR_KEY, circle_color);
		arguments.putInt(ColorPopPageFragment.RECT_COLOR_KEY, page_color);
		arguments.putInt(ColorPopPageFragment.START_POINT_X_KEY, x);
		arguments.putInt(ColorPopPageFragment.START_POINT_Y_KEY, y);
		arguments.putBoolean(ColorPopPageFragment.IS_VIEWS_BEHIND_STATUS_BAR, is_behind_statusbar);
		arguments.putInt(ColorPopPageFragment.STATUSBAR_HEIGHT, statusbar_height);
		fragment.setArguments(arguments);
	}

}
