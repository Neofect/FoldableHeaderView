package com.neofect.headerscrollapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by yoojaehong on 2017. 8. 7..
 */

public class ViewHolder extends RecyclerView.ViewHolder {

	private TextView textView;

	public ViewHolder(View itemView) {
		super(itemView);

		textView = (TextView) itemView.findViewById(R.id.item_text);
	}

	public void setText(String text) {
		textView.setText(text);
	}
}
