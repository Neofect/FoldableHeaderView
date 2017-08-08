package com.neofect.library.ui.foldableheaderview;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

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

	private final float min = 0f;
	private final float max = 1.0f;
	private float current = min;
	private float step = 0.05f;
	private int threshoulder = 10;
	private boolean state = false;
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

	private void doFold(V view) {
		synchronized(this) {
			FoldRunnable foldRunnable = getRunnable(view);
			if (!state) {
				state = true;
				foldRunnable.destination = max;
				foldRunnable.step = step;
				Thread thread = new Thread(foldRunnable);
				thread.start();
			}
		}
	}

	private void doUnfold(V view) {
		synchronized(this) {
			FoldRunnable foldRunnable = getRunnable(view);
			if (!state) {
				state = true;
				foldRunnable.destination = min;
				foldRunnable.step = -step;
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
		private float destination;
		private float step;

		public FoldRunnable(V view) {
			this.view = view;
		}

		private boolean isDone() {
			if (step > 0) {
				return current >= destination;
			} else {
				return current <= destination;
			}
		}

		private void processStep() {
			current += step;
			if (step > 0) {
				if (current > destination) {
					current = destination;
				}
			} else {
				if (current < destination) {
					current = destination;
				}
			}
		}

		@Override
		public void run() {
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
