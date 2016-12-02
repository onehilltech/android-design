package com.onehilltech.design.android;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter <ItemAdapter.ViewHolder>
{
  /**
   * Base class for all Item types managed by the ItemAdapter.
   */
  public static abstract class Item
  {
    /**
     * Get the view type for the Item.
     *
     * @return
     */
    public abstract int getItemViewType ();
  }

  /**
   * Implementation of the ViewHolder class for this RecyclerView.Adapter.
   */
  public static class ViewHolder extends RecyclerView.ViewHolder
  {
    /// Item managed by the view holder.
    protected Item item_;

    public ViewHolder (View itemView)
    {
      this (itemView, null);
    }

    public ViewHolder (View itemView, Item item)
    {
      super (itemView);
      this.item_ = item;
    }

    public void setItem (Item item)
    {
      this.item_ = item;
    }
  }

  /**
   * Interface for factory objects that create ViewHolder objects for this
   * implementation of the RecyclerView.Adapter.
   */
  public interface ViewHolderFactory
  {
    ViewHolder createViewHolder (ViewGroup parent);
  }

  /// Collection of items managed by the adapter.
  private final ArrayList<Item> items_ = new ArrayList<> ();

  /// Registered factories for the adapter.
  private final SparseArray<ViewHolderFactory> viewHolderFactories_ = new SparseArray<> ();

  /**
   * Register a factory with the adapter.
   *
   * @param viewType
   * @param factory
   */
  public void registerFactory (int viewType, ViewHolderFactory factory)
  {
    this.viewHolderFactories_.put (viewType, factory);
  }

  /**
   * Add an item to the adapter.
   *
   * @param item
   */
  public void addItem (Item item)
  {
    this.items_.add (item);
  }

  /**
   * Add an item at a position to the adapter.
   *
   * @param position
   * @param item
   */
  public void addItem (int position, Item item)
  {
    this.items_.add (position, item);
  }

  public void setItem (int position, Item item)
  {
    this.items_.set (position, item);
  }

  public Item removeItem (int position)
  {
    return this.items_.remove (position);
  }

  /**
   * Get an item from the adapter.
   *
   * @param position
   * @return
   */
  public Item getItem (int position)
  {
    return this.items_.get (position);
  }

  @Override
  public ItemAdapter.ViewHolder onCreateViewHolder (ViewGroup parent, int viewType)
  {
    ViewHolderFactory factory = this.viewHolderFactories_.get (viewType);
    return factory.createViewHolder (parent);
  }

  @Override
  public void onBindViewHolder (ItemAdapter.ViewHolder holder, int position)
  {
    Item item = this.items_.get (position);
    holder.setItem (item);
  }

  @Override
  public int getItemViewType (int position)
  {
    Item item = this.items_.get (position);
    return item.getItemViewType ();
  }

  @Override
  public long getItemId (int position)
  {
    return position;
  }

  @Override
  public int getItemCount ()
  {
    return this.items_.size ();
  }
}
