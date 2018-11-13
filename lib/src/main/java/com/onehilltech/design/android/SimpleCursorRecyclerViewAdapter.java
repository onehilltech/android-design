package com.onehilltech.design.android;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;

import androidx.recyclerview.widget.RecyclerView;

public class SimpleCursorRecyclerViewAdapter <VH extends SimpleCursorRecyclerViewAdapter.CursorViewHolder>
    extends CursorRecyclerViewAdapter<VH>
{
  public static abstract class CursorViewHolder extends RecyclerView.ViewHolder
  {
    public CursorViewHolder (View itemView)
    {
      super (itemView);
    }

    public abstract void onBindData (Cursor cursor);
  }

  private final LayoutInflater inflater_;

  private int layoutId_;

  private Class<VH> viewHolderClass_;

  public SimpleCursorRecyclerViewAdapter (Context context, Class<VH> viewHolderClass, int layoutId)
  {
    this (context, viewHolderClass, layoutId, null);
  }

  public SimpleCursorRecyclerViewAdapter (Context context, Class<VH> viewHolderClass, int layoutId, Cursor cursor)
  {
    super (context, cursor);

    this.inflater_ = LayoutInflater.from (context);
    this.viewHolderClass_ = viewHolderClass;
    this.layoutId_ = layoutId;
  }

  @Override
  public VH onCreateViewHolder (ViewGroup parent, int viewType)
  {
    View view = this.inflater_.inflate (this.layoutId_, parent, false);

    try
    {
      Constructor<VH> ctor = this.viewHolderClass_.getConstructor (View.class);
      return ctor.newInstance (view);
    }
    catch (Exception e)
    {
      throw new IllegalStateException (e.getLocalizedMessage (), e);
    }
  }

  @Override
  public void onBindViewHolder (VH viewHolder, Cursor cursor)
  {
    viewHolder.onBindData (cursor);
  }
}
