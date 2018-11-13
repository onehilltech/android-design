package com.onehilltech.design.android;

import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class ItemAdapter extends RecyclerView.Adapter <ItemAdapter.ViewHolder>
{
  /**
   * Base class for all Item types managed by the ItemAdapter.
   */
  public static class Item <T>
  {
    /// The item type.
    private int itemViewType_;

    /// The data contained in the item.
    private T data_;

    /// Unique id for the item.
    private long itemId_;

    /**
     * Initializing constructor.
     *
     * @param itemViewType      View type of the item
     */
    public Item (int itemViewType)
    {
      this (RecyclerView.NO_ID, itemViewType);
    }

    public Item (long itemId, int itemViewType)
    {
      this.itemId_ = itemId;
      this.itemViewType_ = itemViewType;
    }

    /**
     * Get the view type for the Item.
     *
     * @return
     */
    public int getItemViewType ()
    {
      return this.itemViewType_;
    }

    /**
     * Set the data contained in the item.
     *
     * @param data
     */
    public void setData (T data)
    {
      this.data_ = data;
    }

    /**
     * Get the data contained in the item.
     *
     * @return
     */
    public T getData ()
    {
      return this.data_;
    }

    /**
     * Get the id of the item.
     *
     * @return
     */
    public long getItemId ()
    {
      return this.itemId_;
    }
  }

  /**
   * Implementation of the ViewHolder class for this RecyclerView.Adapter.
   */
  public static class ViewHolder <T extends Item> extends RecyclerView.ViewHolder
  {
    /// Item managed by the view holder.
    private T item_;

    public ViewHolder (View itemView)
    {
      super (itemView);
    }

    public T getItem ()
    {
      return this.item_;
    }

    @SuppressWarnings ("unchecked")
    public final void setItem (Item item)
    {
      this.item_ = (T)item;
      this.onItemChanged (this.item_);
    }

    public void onItemChanged (T item)
    {

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
   * Get the position of an item.
   *
   * @param item
   * @return
   */
  public int getPosition (Item item)
  {
    return this.items_.indexOf (item);
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
    long itemId = this.items_.get (position).getItemId ();
    return itemId != -1 ? itemId : position;
  }

  @Override
  public int getItemCount ()
  {
    return this.items_.size ();
  }
}
