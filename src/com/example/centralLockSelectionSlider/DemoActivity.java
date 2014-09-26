package com.example.centralLockSelectionSlider;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.centralLockSelectionSlider.CustomExperienceSlider.ItemSelectedListener;

public class DemoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		CustomExperienceSlider slider = (CustomExperienceSlider)findViewById(R.id.custom_exp_slider);
		slider.setItemSelectionListener(itemSelectListener);
		
	}
	
	ItemSelectedListener itemSelectListener = new ItemSelectedListener() {
		
		@Override
		public void onItemSelected(String item, int position) {
			
			Toast.makeText(getApplicationContext(), "Slider"+item,Toast.LENGTH_SHORT).show();
			
		}
	};
	
}
