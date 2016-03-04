package com.onehilltech.design.android;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public class BaseViewHolder extends RecyclerView.ViewHolder
{
  protected RecyclerViewItem item_;

  @SuppressWarnings ("unused")
  public BaseViewHolder (View itemView)
  {
    this (itemView, null);
  }

  public BaseViewHolder (View itemView, RecyclerViewItem item)
  {
    super (itemView);

    this.item_ = item;
  }

  public void setItem (RecyclerViewItem item)
  {
    this.item_ = item;
  }
}
