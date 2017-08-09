package com.neofect.library.ui.foldableheaderview;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

/**
 * Created by yoojaehong on 2017. 8. 8..
 */

public abstract class HeaderScrollBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {

	private final String LOG_TAG = HeaderScrollBehavior.class.getSimpleName();

	private final Handler handler = new Handler(Looper.getMainLooper(), new android.os.Handler.Callback() {
		public boolean handleMessage(Message message) {
			switch(message.what) {
				case 0:
					setViewCurrent((V)message.obj, current);
					return true;

				case 1:
					state = false;
					return true;

				default:
					return false;
			}
		}
	});

	private float current = 0;
	private float step = 0.05f;
	private int threshoulder = 10;
	private boolean state = false;
	private Interpolator interpolator = new AccelerateDecelerateInterpolator();
	private FoldRunnable foldRunnable;

	public HeaderScrollBehavior() {
	}

	public HeaderScrollBehavior(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setStep(float step) {
		this.step = step;
	}

	public void setThreshoulder(int threshoulder) {
		this.threshoulder = threshoulder;
	}

	public void setInterpolator(Interpolator interpolator) {
		if (interpolator != null) {
			this.interpolator = interpolator;
		} else {
			this.interpolator = new AccelerateDecelerateInterpolator();
		}
	}

	@Override
	public final boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, V child, View directTargetChild, View target, int nestedScrollAxes) {
		return true;
	}

	@Override
	public final void onNestedPreScroll(CoordinatorLayout coordinatorLayout, V child, View target, int dx, int dy, int[] consumed) {
		if (dy > threshoulder) {
			doFold(child);
		} else if (dy < -threshoulder) {
			doUnfold(child);
		}
	}

	public void doFold(V view) {
		synchronized(this) {
			FoldRunnable foldRunnable = getRunnable(view);
			if (!state) {
				state = true;
				foldRunnable.step = step;
				foldRunnable.increasing = true;
				Thread thread = new Thread(foldRunnable);
				thread.start();
			}
		}
	}

	public void doUnfold(V view) {
		synchronized(this) {
			FoldRunnable foldRunnable = getRunnable(view);
			if (!state) {
				state = true;
				foldRunnable.step = step;
				foldRunnable.increasing = false;
				Thread thread = new Thread(foldRunnable);
				thread.start();
			}
		}
	}

	protected abstract void setViewCurrent(V view, float current);

	private synchronized FoldRunnable getRunnable(V view) {
		if (foldRunnable == null) {
			foldRunnable = new FoldRunnable(view);
		}
		return foldRunnable;
	}

	private class FoldRunnable implements Runnable {

		private final V view;
		private float step;
		private float progress;
		private boolean increasing;

		public FoldRunnable(V view) {
			this.view = view;
		}

		private boolean isDone() {
			return progress >= 1.0;
		}

		private void processStep() {
			progress += step;
			progress = progress>=1.0f ? 1.0f : progress;
			float interpolated = interpolator.getInterpolation(progress);
			if (increasing) {
				current = interpolated;
			} else {
				current = 1.0f - interpolated;
			}
		}

		@Override
		public void run() {
			progress = 0f;
			while (!isDone()) {
				processStep();
				handler.sendMessage(handler.obtainMessage(0, view));
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					Log.e(LOG_TAG, "", e);
				}
			}
			handler.sendMessageDelayed(handler.obtainMessage(1), 500);
		}
	}
}
