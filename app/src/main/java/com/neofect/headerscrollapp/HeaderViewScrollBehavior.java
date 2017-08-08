package com.neofect.headerscrollapp;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.neofect.library.ui.foldableheaderview.HeaderScrollBehavior;

/**
 * Created by yoojaehong on 2017. 8. 7..
 */

public class HeaderViewScrollBehavior extends HeaderScrollBehavior<HeaderView> {

	private final String LOG_TAG = HeaderViewScrollBehavior.class.getSimpleName();

	public HeaderViewScrollBehavior() {
	}

	public HeaderViewScrollBehavior(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void setViewCurrent(HeaderView view, float current) {
		TextView textView = (TextView) view.findViewById(R.id.title);
		float alpha = 1.0f - current * 1.5f;
		if (alpha < 0f) {
			alpha = 0f;
		}
		textView.setAlpha(alpha);
		TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab);
		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) tabLayout.getLayoutParams();
		layoutParams.topMargin = -(int) (tabLayout.getHeight() * current);
		tabLayout.setLayoutParams(layoutParams);
	}
}
