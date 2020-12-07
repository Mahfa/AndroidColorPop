package com.mahfa.colorpop.demo;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

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