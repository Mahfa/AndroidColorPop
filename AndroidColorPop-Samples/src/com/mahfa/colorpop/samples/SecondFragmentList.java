package com.mahfa.colorpop.samples;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.ListView;

import com.mahfa.colorpop.ColorPopFragment;
import com.mahfa.colorpop.ColorPopUtils;

public class SecondFragmentList extends ColorPopFragment implements
		AnimationListener {
	private View fragment_view;
	private Toolbar toolbar;
	private ListView list;
	private View list_header;

	@Override
	public View onCreateFragmentView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		fragment_view = inflater.inflate(R.layout.fragment_second_list,
				container, false);
		if (android.os.Build.VERSION.SDK_INT >= 19) {
			fragment_view.setPadding(0,
					ColorPopUtils.getStatusBarHeightPixels(getContext()), 0, 0);
		}
		toolbar = (Toolbar) fragment_view.findViewById(R.id.toolbar);
		list = (ListView) fragment_view.findViewById(R.id.list);
		return fragment_view;
	}

	@Override
	public void onBackgroundAnimationEnd() {
		fragment_view.setVisibility(View.VISIBLE);
		toolbar.setVisibility(View.INVISIBLE);
		Context context = getContext();
		if (context != null) {
			Animation grow = AnimationUtils.loadAnimation(getContext(),
					R.anim.slide_in_bottom);
			grow.setAnimationListener(this);
			list.startAnimation(grow);
			list.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		toolbar.setTitle("AndroidColorPop");
		list_header = LayoutInflater.from(getContext()).inflate(
				R.layout.header_second_fragment_list, list, false);
		list_header.setVisibility(View.INVISIBLE);
		list.addHeaderView(list_header);
		SecondFragmentListAdapter list_adapter = new SecondFragmentListAdapter(getContext());
		list.setAdapter(list_adapter);
	}

	@Override
	public void onAnimationStart(Animation animation) {
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		Context context = getContext();
		if (context != null) {
			Animation fade_in = AnimationUtils.loadAnimation(getContext(),
					R.anim.abc_fade_in);
			toolbar.startAnimation(fade_in);
			list_header.startAnimation(fade_in);
			toolbar.setVisibility(View.VISIBLE);
			list_header.setVisibility(View.VISIBLE);
		}

	}

	@Override
	public void onAnimationRepeat(Animation animation) {

	}

}
