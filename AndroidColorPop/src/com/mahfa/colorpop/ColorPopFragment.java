package com.mahfa.colorpop;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

public abstract class ColorPopFragment extends Fragment implements
		OnClickListener {
	public static final String CIRCLE_COLOR_KEY = "circle_color";
	public static final String START_POINT_X_KEY = "start_point_x";
	public static final String START_POINT_Y_KEY = "start_point_y";
	protected boolean should_animate = true;
	protected FrameLayout container;
	protected PopBackgroundView pop_background;
	private int circle_color;
	protected View fragment_view;

	public ColorPopFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		container = new FrameLayout(getContext());
		container.setLayoutParams(lp);
		pop_background = new PopBackgroundView(getContext());
		pop_background.setLayoutParams(lp);
		pop_background.setOnClickListener(this);
		fragment_view = onCreateFragmentView(inflater, container,
				savedInstanceState);
		fragment_view.setVisibility(View.INVISIBLE);
		container.addView(pop_background, 0);
		container.addView(fragment_view, 1);
		return container;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		if (savedInstanceState == null) {
			Bundle arguments = getArguments();
			if (arguments.containsKey(CIRCLE_COLOR_KEY)) {
				circle_color = arguments.getInt(CIRCLE_COLOR_KEY);
				pop_background.setCircleColor(circle_color);
			}
			if (arguments.containsKey(START_POINT_X_KEY)
					&& arguments.containsKey(START_POINT_Y_KEY)) {
				pop_background.setCircleStartPointCoordinates(
						arguments.getInt(START_POINT_X_KEY),
						arguments.getInt(START_POINT_Y_KEY));
			}
			if (should_animate) {
				pop_background.setAnimationListener(anim_listener);
				pop_background.animatePop();
			}
		}
	}

	public int getCircleColor() {
		return circle_color;
	}

	protected AnimationListener anim_listener = new AnimationListener() {

		@Override
		public void onAnimationEnd(int optional_id) {
			ColorPopFragment.this.onAnimationEnd();
		}
	};

	public abstract View onCreateFragmentView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState);

	public abstract void onAnimationEnd();

	@Override
	public void onClick(View v) {

	}

	@Override
	public void setArguments(Bundle args) {
		Bundle fragment_arguments = getArguments();
		if (fragment_arguments != null) {
			fragment_arguments.putAll(args);
		} else {
			super.setArguments(args);
		}
	}
}
