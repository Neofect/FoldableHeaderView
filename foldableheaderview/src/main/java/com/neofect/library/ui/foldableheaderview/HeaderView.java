package com.neofect.library.ui.foldableheaderview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by yoojaehong on 2017. 8. 9..
 */

public class HeaderView extends RelativeLayout {
	public HeaderView(Context context) {
		super(context);
	}

	public HeaderView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	public HeaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public HeaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	public void foldHeader() {
		ViewGroup.LayoutParams params = getLayoutParams();
		if (params instanceof CoordinatorLayout.LayoutParams) {
			CoordinatorLayout.LayoutParams coordinatorLayoutParams = (CoordinatorLayout.LayoutParams) params;
			CoordinatorLayout.Behavior behavior = coordinatorLayoutParams.getBehavior();
			if (behavior instanceof HeaderScrollBehavior) {
				HeaderScrollBehavior headerBehavior = (HeaderScrollBehavior) behavior;
				headerBehavior.doFold(this);
			}
		}
	}

	public void unfoldHeader() {
		ViewGroup.LayoutParams params = getLayoutParams();
		if (params instanceof CoordinatorLayout.LayoutParams) {
			CoordinatorLayout.LayoutParams coordinatorLayoutParams = (CoordinatorLayout.LayoutParams) params;
			CoordinatorLayout.Behavior behavior = coordinatorLayoutParams.getBehavior();
			if (behavior instanceof HeaderScrollBehavior) {
				HeaderScrollBehavior headerBehavior = (HeaderScrollBehavior) behavior;
				headerBehavior.doUnfold(this);
			}
		}
	}
}
