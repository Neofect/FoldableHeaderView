package com.neofect.headerscrollapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by yoojaehong on 2017. 8. 7..
 */

public class Adapter extends RecyclerView.Adapter<ViewHolder> {
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler, parent, false);
		ViewHolder viewHolder = new ViewHolder(view);
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		holder.setText(String.valueOf(position));
	}

	@Override
	public int getItemCount() {
		return 50;
	}
}
