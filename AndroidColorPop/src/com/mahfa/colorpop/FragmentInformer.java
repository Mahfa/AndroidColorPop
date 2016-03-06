package com.mahfa.colorpop;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * FragmentInformer is used to give information to color pop animation
 * fragment
 * 
 *
 */
public class FragmentInformer {
	public static final String CIRCLE_COLOR_KEY = "circle_color";
	public static final String START_POINT_X_KEY = "start_point_x";
	public static final String START_POINT_Y_KEY = "start_point_y";
	public static final String RECT_COLOR_KEY = "rect_color";
	public static final String IS_VIEWS_BEHIND_STATUS_BAR = "is_behind_statusbar";
	public static final String STATUSBAR_HEIGHT = "statusbar_height";
	public static final int MODE_LEFT_EDGE = 0;
	public static final int MODE_CENTER = 1;
	public static final int MODE_RIGHT_EDGE = 2;
	private int circle_color;
	private int page_color;
	private boolean is_behind_statusbar = false;
	private int statusbar_height = 0;
	private int start_point_x = 0;
	private int start_point_y = 0;

	public FragmentInformer(Context context) {
		statusbar_height = ColorPopUtils.getStatusBarHeightPixels(context);
	}

	/**
	 * @param color
	 *            the color of circle animation
	 */
	public void setCircleColor(int color) {
		circle_color = color;

	}

	/**
	 * @param color
	 *            the color of the page that grows from bottom
	 */
	public void setPageColor(int color) {
		page_color = color;

	}

	/**
	 * @param view
	 *            the base view that is used for determining the start point of
	 *            the circles animation
	 * @param mode
	 *            determines the position of start point of animation on the
	 *            base view </br> {@code FragmentInformer.MODE_CENTER} </br>
	 *            {@code FragmentInformer.MODE_LEFT_EDGE} </br>
	 *            {@code FragmentInformer.MODE_RIGHT_EDGE}
	 * @param is_views_behind_statusbar
	 *            if your view's are behind status bar set this to true used in
	 *            API +19 </br> Note : if the start point of animation is not
	 *            correct then change this boolean and see if it's correct or
	 *            not
	 */
	public void setBaseView(final View view, int mode,
			boolean is_views_behind_statusbar) {
		is_behind_statusbar = is_views_behind_statusbar;
		int width = view.getWidth();
		int height = view.getHeight();
		int[] layout_location = { 0, 0 };
		view.getLocationOnScreen(layout_location);
		if (mode == MODE_LEFT_EDGE) {
			start_point_x = layout_location[0] + width / 15;
		} else if (mode == MODE_CENTER) {
			start_point_x = layout_location[0] + (width / 2);
		} else if (mode == MODE_RIGHT_EDGE) {
			start_point_x = layout_location[0] + (width - (width / 15));
		}
		start_point_y = layout_location[1] + (height / 2);
		if (!is_behind_statusbar) {
			start_point_y -= statusbar_height;
		}

	}

	/**
	 * @param fragment
	 *            it will give the fragment these informations </br> #1 Int |
	 *            start point of the animation
	 */
	public void informFragment(Fragment fragment) {
		Bundle arguments = new Bundle();
		arguments.putInt(START_POINT_X_KEY, start_point_x);
		arguments.putInt(START_POINT_Y_KEY, start_point_y);
		fragment.setArguments(arguments);
	}

	/**
	 * @param fragment
	 *            it will give the fragment these informations </br> #1 Int |
	 *            the circles color </br> #2 Int | start point of the animation
	 * 
	 */
	public void informColorPopFragment(Fragment fragment) {
		Bundle arguments = new Bundle();
		arguments.putInt(CIRCLE_COLOR_KEY, circle_color);
		arguments.putInt(START_POINT_X_KEY, start_point_x);
		arguments.putInt(START_POINT_Y_KEY, start_point_y);
		fragment.setArguments(arguments);
	}

	/**
	 * @param fragment
	 *            it will give the fragment these informations </br>
	 * 
	 *            #1 Int | the circles color </br> #2 Int | the page color </br>
	 *            #3 Int | start point of the animation </br> #4 Boolean | is
	 *            views behind status bar or not </br>
	 */
	public void informColorPopPageFragment(Fragment fragment) {
		Bundle arguments = new Bundle();
		arguments.putInt(CIRCLE_COLOR_KEY, circle_color);
		arguments.putInt(RECT_COLOR_KEY, page_color);
		arguments.putInt(START_POINT_X_KEY, start_point_x);
		arguments.putInt(START_POINT_Y_KEY, start_point_y);
		arguments.putBoolean(IS_VIEWS_BEHIND_STATUS_BAR, is_behind_statusbar);
		arguments.putInt(STATUSBAR_HEIGHT, statusbar_height);
		fragment.setArguments(arguments);
	}

}
