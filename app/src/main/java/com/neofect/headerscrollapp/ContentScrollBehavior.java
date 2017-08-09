package com.neofect.headerscrollapp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.neofect.library.ui.foldableheaderview.ContentsScrollBehavior;

import java.util.List;

/**
 * Created by yoojaehong on 2017. 8. 7..
 */

public class ContentScrollBehavior extends ContentsScrollBehavior {
	public ContentScrollBehavior() {
	}

	public ContentScrollBehavior(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected View findHeaderLayout(List<View> views) {
		int i = 0;
		for(View view : views) {
			if (view instanceof MainHeaderView) {
				return view;
			}
		}

		return null;
	}

	@Override
	protected boolean isViewDependsOn(View view) {
		return view instanceof MainHeaderView;
	}
}
