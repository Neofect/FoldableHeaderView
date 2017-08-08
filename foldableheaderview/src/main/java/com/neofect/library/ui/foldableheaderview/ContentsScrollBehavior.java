package com.neofect.library.ui.foldableheaderview;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by yoojaehong on 2017. 8. 8..
 */

public abstract class ContentsScrollBehavior extends CoordinatorLayout.Behavior {

	public ContentsScrollBehavior() {
	}

	public ContentsScrollBehavior(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public final boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
		if(child.getLayoutParams().height == ViewGroup.LayoutParams.MATCH_PARENT) {
			List dependencies = parent.getDependencies(child);
			if(dependencies.isEmpty()) {
				return false;
			}

			View headerLayout = findHeaderLayout(dependencies);
			if (headerLayout != null && ViewCompat.isLaidOut(headerLayout)) {
				int height = parent.getHeight() - headerLayout.getMeasuredHeight();
				child.layout(0, 0, parent.getWidth(), height);
				return true;
			}
		}
		return super.onLayoutChild(parent, child, layoutDirection);
	}

	@Override
	public final boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
		CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) dependency.getLayoutParams()).getBehavior();
		if (behavior instanceof HeaderScrollBehavior) {
			int height = dependency.getHeight();
			ViewCompat.setTranslationY(child, (float) height);
		}

		return false;
	}

	@Override
	public final boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
		return isViewDependsOn(dependency);
	}

	protected abstract View findHeaderLayout(List<View> dependencies);
	protected abstract boolean isViewDependsOn(View view);
}
