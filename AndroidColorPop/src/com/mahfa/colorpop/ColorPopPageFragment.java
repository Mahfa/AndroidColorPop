package com.mahfa.colorpop;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

public abstract class ColorPopPageFragment extends ColorPopFragment {
	public static final String RECT_COLOR_KEY = "rect_color";
	public static final String IS_VIEWS_BEHIND_STATUS_BAR = "is_behind_statusbar";
	public static final String STATUSBAR_HEIGHT = "statusbar_height";
	private boolean is_behind_statusbar = false;
	private int statusbar_height = 0;
	protected int header_height = 0;
	public ColorPopPageFragment() {
		should_animate = false;
		
	}
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		if (savedInstanceState == null) {
			Bundle arguments = getArguments();
			pop_background.setHaveRectAnimation(true);
			if (arguments.containsKey(RECT_COLOR_KEY)) {
				pop_background.setRectColor(arguments.getInt(RECT_COLOR_KEY));
			}
			if (arguments.containsKey(IS_VIEWS_BEHIND_STATUS_BAR)) {
				is_behind_statusbar = arguments.getBoolean(IS_VIEWS_BEHIND_STATUS_BAR);
			}
			if (arguments.containsKey(STATUSBAR_HEIGHT)) {
				statusbar_height = arguments.getInt(STATUSBAR_HEIGHT);
			}
			pop_background.setAnimationListener(anim_listener);
			if (haveHeader()) {
				final View header_view = getHeaderView(view);
				ViewTreeObserver viewTreeObserver = header_view
						.getViewTreeObserver();
				if (viewTreeObserver.isAlive()) {
					viewTreeObserver
							.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
								@SuppressLint("NewApi")
								@Override
								public void onGlobalLayout() {
									if (android.os.Build.VERSION.SDK_INT >= 16) {
										header_view.getViewTreeObserver()
												.removeOnGlobalLayoutListener(
														this);
									} else {
										header_view.getViewTreeObserver()
												.removeGlobalOnLayoutListener(
														this);
									}
									int[] layout_location = { 0, 0 };
									header_view.getLocationOnScreen(layout_location);
									header_height += layout_location[1];
									if (!is_behind_statusbar) {
										header_height -= statusbar_height;
									}
									header_height += header_view.getHeight();
									pop_background
											.setRectHeaderHeight(header_height);
									pop_background.animatePop();
								}
							});
				}
			} else {
				pop_background.setRectHeaderHeight(header_height);
				pop_background.animatePop();
			}
		}
	}

	public abstract boolean haveHeader();

	public abstract View getHeaderView(View fragment_view);


}
