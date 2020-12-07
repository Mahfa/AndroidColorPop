package com.mahfa.colorpop.demo;

import com.mahfa.colorpop.ColorPopUtils;
import com.mahfa.colorpop.FragmentInformationParser;
import com.mahfa.colorpop.PopBackgroundView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class SearchFragment extends Fragment {
	private PopBackgroundView pop_background;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.fragment_search, container, false);
		if (android.os.Build.VERSION.SDK_INT >= 19) {
			view.setPadding(0,
					ColorPopUtils.getStatusBarHeightPixels(getContext()), 0, 0);
		}
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		pop_background = (PopBackgroundView) view
				.findViewById(R.id.pop_background);
		pop_background.setCircleColor(Color.WHITE);
		Bundle arguments = getArguments();
		FragmentInformationParser info_parser = new FragmentInformationParser(
				arguments);
		pop_background.setCircleStartPointCoordinates(
				info_parser.getStartPointX(), info_parser.getStartPointY());
		pop_background
				.setCirclesFillType(PopBackgroundView.CIRLCES_FILL_WIDTH_TYPE);
		pop_background.animatePop();
	}
}
