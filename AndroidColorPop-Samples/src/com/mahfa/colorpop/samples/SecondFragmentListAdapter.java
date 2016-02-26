package com.mahfa.colorpop.samples;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SecondFragmentListAdapter extends BaseAdapter {
	private LayoutInflater layout_inflater;

	public SecondFragmentListAdapter(Context context) {
		layout_inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return 50;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = layout_inflater.inflate(R.layout.adapter_second_fragment_list,
					parent, false);
			ViewHolder holder = new ViewHolder();
			holder.title = (TextView) convertView.findViewById(R.id.title);
			convertView.setTag(holder);
		}
		ViewHolder holder = (ViewHolder) convertView.getTag();
		holder.title.setText("List Item "+(position+1));
		return convertView;
	}
	private static class ViewHolder {
		public TextView title;
	}
}
