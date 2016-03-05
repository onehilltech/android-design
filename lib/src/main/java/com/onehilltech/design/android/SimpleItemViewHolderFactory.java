package com.onehilltech.design.android;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class SimpleItemViewHolderFactory implements ItemAdapter.ViewHolderFactory
{
  private int layoutId_;

  public SimpleItemViewHolderFactory (int layoutId)
  {
    this.layoutId_ = layoutId;
  }

  @Override
  public final ItemAdapter.ViewHolder createViewHolder (ViewGroup parent)
  {
    LayoutInflater inflater = LayoutInflater.from (parent.getContext ());
    View view = inflater.inflate (this.layoutId_, parent, false);

    return this.onCreateViewHolder (view);
  }

  protected abstract ItemAdapter.ViewHolder onCreateViewHolder (View view);
}
