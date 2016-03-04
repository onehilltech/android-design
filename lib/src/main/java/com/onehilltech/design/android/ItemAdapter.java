package com.onehilltech.design.android;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter <BaseViewHolder>
{
  private final ArrayList<RecyclerViewItem> items_ = new ArrayList<> ();

  private final SparseArray<ViewHolderFactory> viewHolderFactories_ = new SparseArray<> ();

  public void registerFactory (int viewType, ViewHolderFactory factory)
  {
    this.viewHolderFactories_.put (viewType, factory);
  }

  public void addItem (RecyclerViewItem item)
  {
    this.items_.add (item);
  }

  public void addItem (int position, RecyclerViewItem item)
  {
    this.items_.add (position, item);
  }

  @Override
  public BaseViewHolder onCreateViewHolder (ViewGroup parent, int viewType)
  {
    return this.viewHolderFactories_.get (viewType).createViewHolder (parent);
  }

  @Override
  public void onBindViewHolder (BaseViewHolder holder, int position)
  {
    holder.setItem (this.items_.get (position));
  }

  @Override
  public int getItemCount ()
  {
    return this.items_.size ();
  }
}
