package com.mahfa.colorpop;

import android.os.Bundle;

/**
 * FragmentInfromationParser parses the informations given by
 * {@link FragmentInformer} to fragment as a {@link Bundle}
 *
 */
public class FragmentInfromationParser {

	private int circle_color = 0;
	private int rect_color = 0;
	private boolean is_behind_statusbar = false;
	private int statusbar_height = 0;
	private int start_point_x = 0;
	private int start_point_y = 0;

	/**
	 * 
	 * @param arguments
	 *            the arguments Bundle of the fragment .
	 */
	public FragmentInfromationParser(Bundle arguments) {
		if (arguments.containsKey(FragmentInformer.CIRCLE_COLOR_KEY)) {
			circle_color = arguments.getInt(FragmentInformer.CIRCLE_COLOR_KEY);
		}
		if (arguments.containsKey(FragmentInformer.START_POINT_X_KEY)
				&& arguments.containsKey(FragmentInformer.START_POINT_Y_KEY)) {
			start_point_x = arguments
					.getInt(FragmentInformer.START_POINT_X_KEY);
			start_point_y = arguments
					.getInt(FragmentInformer.START_POINT_Y_KEY);
		}
		if (arguments.containsKey(FragmentInformer.RECT_COLOR_KEY)) {
			rect_color = arguments.getInt(FragmentInformer.RECT_COLOR_KEY);
		}
		if (arguments.containsKey(FragmentInformer.IS_VIEWS_BEHIND_STATUS_BAR)) {
			is_behind_statusbar = arguments
					.getBoolean(FragmentInformer.IS_VIEWS_BEHIND_STATUS_BAR);
		}
		if (arguments.containsKey(FragmentInformer.STATUSBAR_HEIGHT)) {
			statusbar_height = arguments
					.getInt(FragmentInformer.STATUSBAR_HEIGHT);
		}
	}

	/**
	 * @return Int | the color of circle
	 */
	public int getCircleColor() {
		return circle_color;
	}

	/**
	 * @return Int | the page color that grows from bottom
	 */
	public int getPageColor() {
		return rect_color;
	}

	/**
	 * @return Boolean | is views behind status bar or not
	 */
	public boolean getIsBehindStatusbar() {
		return is_behind_statusbar;
	}

	/**
	 * @return Int | start point x of circles animation
	 */
	public int getStartPointX() {
		return start_point_x;
	}

	/**
	 * @return Int | start point y of circles animation
	 */
	public int getStartPointY() {
		return start_point_y;
	}

	/**
	 * @return Int | height of status bar
	 */
	public int getStatusBarHeight() {
		return statusbar_height;
	}
}
