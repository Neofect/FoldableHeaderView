package com.neofect.headerscrollapp;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

	private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setAdapter(new Adapter());

		HeaderView headerView = (HeaderView) findViewById(R.id.headerview);
		CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) headerView.getLayoutParams();
		final HeaderViewScrollBehavior behavior = (HeaderViewScrollBehavior) params.getBehavior();

		EditText editStep = (EditText) findViewById(R.id.edit_step);
		editStep.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				try {
					float step = Float.parseFloat(charSequence.toString());
					if (step > 0 && step < 1) {
						behavior.setStep(step);
					}
				} catch (Exception e) {
					Log.e(LOG_TAG, "", e);
				}
			}

			@Override
			public void afterTextChanged(Editable editable) {

			}
		});
		EditText editThreshold = (EditText) findViewById(R.id.edit_threshold);
		editThreshold.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				try {
					int threshold = Integer.parseInt(charSequence.toString());
					if (threshold > 0) {
						behavior.setThreshoulder(threshold);
					}
				} catch (Exception e) {
					Log.e(LOG_TAG, "", e);
				}
			}

			@Override
			public void afterTextChanged(Editable editable) {

			}
		});
    }
}
