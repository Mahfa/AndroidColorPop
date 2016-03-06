package com.mahfa.colorpop;

import com.mahfa.colorpop.PopBackgroundView.AnimationListener;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

/**
 * ColorPopFragment extends {@link Fragment} </br> <h1>Usage :</h1> your
 * fragment must extend ColorPopFragment . ColorPopFragment creates a
 * {@link FrameLayout} as the fragment base view and then add a
 * {@link PopBackgroundView} . then it will call the
 * {@code onCreateFragmentView} method to get your views . </br> Note : the
 * visibility of your view would be set to {@code View.INVISIBLE} .</br> you
 * should create views in {@code onCreateFragmentView} instead of
 * {@code onViewCreated} . when the background animation ends
 * onBackgroundAnimationEnd() void would be called so you should make your views
 * visible in this void . note that you better use an animation like fade in to
 * make it nicer
 */
public abstract class ColorPopFragment extends Fragment implements
		AnimationListener {

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
			FragmentInfromationParser info_parser = new FragmentInfromationParser(
					arguments);
			circle_color = info_parser.getCircleColor();
			pop_background.setCircleColor(circle_color);
			pop_background.setCircleStartPointCoordinates(
					info_parser.getStartPointX(), info_parser.getStartPointY());
			if (should_animate) {
				pop_background.setAnimationListener(this);
				pop_background.animatePop();
			}
		}
	}

	/**
	 * 
	 * @return the circles aniamtion colorF color
	 */
	public int getCircleColor() {
		return circle_color;
	}

	/**
	 * 
	 * @return returns the {@link PopBackgroundView} used for aniamtions
	 */
	public PopBackgroundView getPopBackgroundView() {
		return pop_background;
	}

	@Override
	public void onAnimationEnd() {
		onBackgroundAnimationEnd();
	}

	/**
	 * you should create your views here instead of {@code onViewCreated}
	 */
	public abstract View onCreateFragmentView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState);

	/**
	 * called when the background animations ends
	 */
	public abstract void onBackgroundAnimationEnd();

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
