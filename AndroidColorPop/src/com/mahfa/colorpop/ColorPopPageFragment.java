package com.mahfa.colorpop;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

/**
 * ColorPopPageFragment extends {@link ColorPopFragment} <h1>Usage :</h1> it
 * works the same as ColorPopFragment plus it will animate a page that grow's
 * from the bottom</br> if {@code haveHeader()} boolean is set to true then you
 * should set the header view of the page to {@code getHeaderView()} method
 * </br> Note: the header view is used to get the location and height of that
 * view
 * 
 *
 */
public abstract class ColorPopPageFragment extends ColorPopFragment {

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
			pop_background.setHaveRectAnimation(true);
			Bundle arguments = getArguments();
			FragmentInfromationParser info_parser = new FragmentInfromationParser(
					arguments);
			pop_background.setRectColor(info_parser.getPageColor());
			is_behind_statusbar = info_parser.getIsBehindStatusbar();
			statusbar_height = info_parser.getStatusBarHeight();
			pop_background.setAnimationListener(this);
			// use a OnGlobalLayoutListener to measure the header view's
			// location and height
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
									header_view
											.getLocationOnScreen(layout_location);
									header_height += layout_location[1];
									if (!is_behind_statusbar) {
										header_height -= statusbar_height;
									}
									header_height += header_view.getHeight();
									pop_background
											.setRectSpaceTop(header_height);
									pop_background.animatePop();
								}
							});
				}
			} else {
				pop_background.setRectSpaceTop(header_height);
				pop_background.animatePop();
			}
		}
	}

	/**
	 * if set to true then you should set the header view to
	 * {@code getHeaderView()}
	 */
	public abstract boolean haveHeader();

	/**
	 * the header view must be set here
	 * 
	 * @param fragment_view
	 *            the base view of fragment
	 */
	public abstract View getHeaderView(View fragment_view);

}
