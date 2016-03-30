package com.onehilltech.design.android;

import android.support.annotation.NonNull;
import android.view.View;

import java.lang.reflect.Constructor;

public class TypedItemViewHolderFactory <T extends ItemAdapter.ViewHolder>
    extends SimpleItemViewHolderFactory
{
  private final Class <T> clazz_;

  public TypedItemViewHolderFactory (int layoutId, @NonNull Class <T> clazz)
  {
    super (layoutId);

    this.clazz_ = clazz;
  }

  @Override
  protected T onCreateViewHolder (View view)
  {
    try
    {
      Constructor <T> ctor = this.clazz_.getConstructor (View.class);
      return ctor.newInstance (view);
    }
    catch (Exception e)
    {
      throw new RuntimeException ("Failed to create view holder", e);
    }
  }
}
