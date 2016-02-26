package com.mahfa.colorpop.samples;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

@SuppressLint("NewApi")
public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (android.os.Build.VERSION.SDK_INT >= 19) {
		getWindow().getDecorView().setSystemUiVisibility(
			    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
			    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
		}
		if (android.os.Build.VERSION.SDK_INT >= 21) {
			getWindow().setStatusBarColor(Color.TRANSPARENT);
		}
		
		FirstFragment fragment = new FirstFragment();
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		transaction.add(android.R.id.content, fragment);
		transaction.commit();
	}

}