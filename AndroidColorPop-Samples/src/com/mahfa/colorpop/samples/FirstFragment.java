package com.mahfa.colorpop.samples;

import com.mahfa.colorpop.ColorPopUtils;
import com.mahfa.colorpop.FragmentInformer;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.GridView;

public class FirstFragment extends Fragment {
	public FirstFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_first_fragment,
				container, false);
		if (android.os.Build.VERSION.SDK_INT >= 19) {
			view.setBackgroundColor(getResources().getColor(
					R.color.material_blue_grey_800));
			view.setPadding(0,
					ColorPopUtils.getStatusBarHeightPixles(getContext()), 0, 0);
		}
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
		toolbar.setTitle("AndroidColorPop");
		GridView grid = (GridView) view.findViewById(R.id.list);
		GridAdapter adapter = new GridAdapter(getActivity());
		grid.setAdapter(adapter);
		final FloatingActionButton fab = (FloatingActionButton) view
				.findViewById(R.id.floating_button);
		fab.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SecondFragment fragment = new SecondFragment();
				FragmentInformer informer = new FragmentInformer(getContext());
				informer.setCircleColor(getActivity().getResources().getColor(
						R.color.material_blue_grey_800));
				informer.setPageColor(Color.WHITE);
				boolean is_views_behind_status_bar = false;
				if (android.os.Build.VERSION.SDK_INT >= 19) {
					is_views_behind_status_bar = true;
				}
				informer.setBaseView(v, FragmentInformer.MODE_CENTER,
						is_views_behind_status_bar);
				informer.informColorPopPageFragment(fragment);
				FragmentTransaction transaction = getActivity()
						.getSupportFragmentManager().beginTransaction();
				transaction.setCustomAnimations(0, R.anim.abc_popup_exit, 0,
						R.anim.abc_popup_exit);
				transaction.addToBackStack(null);
				transaction.add(android.R.id.content, fragment);
				transaction.commit();
			}
		});
	}
}