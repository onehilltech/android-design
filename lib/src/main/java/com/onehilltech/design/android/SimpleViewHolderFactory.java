package com.onehilltech.design.android;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class SimpleViewHolderFactory implements ViewHolderFactory
{
  private int layoutId_;

  public SimpleViewHolderFactory (int layoutId)
  {
    this.layoutId_ = layoutId;
  }

  @Override
  public final BaseViewHolder createViewHolder (ViewGroup parent)
  {
    LayoutInflater inflater = LayoutInflater.from (parent.getContext ());
    View view = inflater.inflate (this.layoutId_, parent, false);

    return this.onCreateViewHolder (view);
  }

  protected abstract BaseViewHolder onCreateViewHolder (View view);
}
