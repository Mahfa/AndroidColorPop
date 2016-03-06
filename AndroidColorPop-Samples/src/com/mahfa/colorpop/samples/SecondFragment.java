package com.mahfa.colorpop.samples;

import com.mahfa.colorpop.ColorPopPageFragment;
import com.mahfa.colorpop.ColorPopUtils;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


public class SecondFragment extends ColorPopPageFragment {
	View fragment_view;
	@Override
	public View onCreateFragmentView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		fragment_view = inflater.inflate(R.layout.fragment_second_fragment,
				container, false);
		if (android.os.Build.VERSION.SDK_INT >= 19) {
			fragment_view.setBackgroundColor(getResources().getColor(R.color.material_blue_grey_800));
		    fragment_view.setPadding(0, ColorPopUtils.getStatusBarHeightPixels(getContext()), 0, 0);
		}
		return fragment_view;
	}

	@Override
	public boolean haveHeader() {
		return true;
	}

	@Override
	public View getHeaderView(View fragment_view) {
		return fragment_view.findViewById(R.id.header_view);
	}

	@Override
	public void onBackgroundAnimationEnd() {
		Context context = getContext();
		if (context!= null) {
			Animation fade_in = AnimationUtils.loadAnimation(getContext(), R.anim.abc_fade_in);
			fragment_view.startAnimation(fade_in);
			fragment_view.setVisibility(View.VISIBLE);
		}
	}
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
		toolbar.setTitle("AndroidColorPop");
	}
}