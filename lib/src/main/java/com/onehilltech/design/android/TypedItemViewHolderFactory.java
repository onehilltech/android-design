package com.onehilltech.design.android;

import android.support.annotation.NonNull;
import android.view.View;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

public class TypedItemViewHolderFactory <T extends ItemAdapter.ViewHolder>
    extends SimpleItemViewHolderFactory
{
  private final Class <T> clazz_;

  public TypedItemViewHolderFactory (int layoutId, @NonNull Class <T> clazz)
  {
    super (layoutId);

    if ((clazz.getModifiers () & Modifier.STATIC) == 0)
      throw new IllegalArgumentException ("ViewHolder must be a static class");

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
