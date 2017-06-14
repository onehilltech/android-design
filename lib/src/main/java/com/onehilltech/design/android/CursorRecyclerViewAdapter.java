package com.onehilltech.design.android;

import android.content.Context;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.support.v7.widget.RecyclerView;

public abstract class CursorRecyclerViewAdapter <VH extends RecyclerView.ViewHolder>
    extends RecyclerView.Adapter<VH>
{
  private Context context_;

  private Cursor cursor_;

  private boolean dataValid_;

  private int idIndex_;

  private final DataSetObserver dataSetObserver_ = new MyDataSetObserver ();

  private final String idFieldName_;

  public CursorRecyclerViewAdapter (Context context)
  {
    this (context, null);
  }

  public CursorRecyclerViewAdapter (Context context, Cursor cursor)
  {
    this (context, null, "_id");
  }

  public CursorRecyclerViewAdapter (Context context, Cursor cursor, String idField)
  {
    this.context_ = context;
    this.cursor_ = cursor;
    this.dataValid_ = cursor != null;
    this.idFieldName_ = idField;

    this.idIndex_ = this.cursor_ != null ? this.cursor_.getColumnIndex (this.idFieldName_) : -1;

    if (this.cursor_ != null)
      this.cursor_.registerDataSetObserver (this.dataSetObserver_);

    // Mark the adapter has having stable ids.
    this.setHasStableIds (true);
  }

  public String getIdFieldName ()
  {
    return this.idFieldName_;
  }

  public Context getContext ()
  {
    return this.context_;
  }

  public Cursor getCursor ()
  {
    return cursor_;
  }

  @Override
  public int getItemCount ()
  {
    return (this.dataValid_ && this.cursor_ != null) ? this.cursor_.getCount () : 0;
  }

  @Override
  public long getItemId (int position)
  {
    return
        this.dataValid_ && this.cursor_ != null && this.cursor_.moveToPosition (position) ?
            this.cursor_.getLong (this.idIndex_) :
            position;
  }

  @Override
  public void setHasStableIds (boolean hasStableIds)
  {
    super.setHasStableIds (true);
  }

  public abstract void onBindViewHolder (VH viewHolder, Cursor cursor);

  @Override
  public void onBindViewHolder (VH viewHolder, int position)
  {
    if (!this.dataValid_)
      throw new IllegalStateException ("this should only be called when the cursor is valid");

    if (!this.cursor_.moveToPosition (position))
      throw new IllegalStateException ("couldn't move cursor to position " + position);

    this.onBindViewHolder (viewHolder, this.cursor_);
  }

  /**
   * Change the underlying cursor to a new cursor. If there is an existing cursor it will be
   * closed.
   */
  public void changeCursor (Cursor cursor)
  {
    Cursor old = swapCursor (cursor);

    if (old != null)
      old.close ();
  }

  /**
   * Swap in a new Cursor, returning the old Cursor.  Unlike
   * {@link #changeCursor(Cursor)}, the returned old Cursor is <em>not</em>
   * closed.
   */
  public Cursor swapCursor (Cursor newCursor)
  {
    if (newCursor == this.cursor_)
      return null;

    final Cursor oldCursor = cursor_;

    if (oldCursor != null)
      oldCursor.unregisterDataSetObserver (this.dataSetObserver_);

    this.cursor_ = newCursor;

    if (cursor_ != null)
    {
      this.cursor_.registerDataSetObserver (this.dataSetObserver_);
      this.idIndex_ = newCursor.getColumnIndexOrThrow ("_id");
      this.dataValid_ = true;

      this.notifyDataSetChanged ();
    }
    else
    {
      this.idIndex_ = -1;
      this.dataValid_ = false;
    }
    return oldCursor;
  }

  private class MyDataSetObserver extends DataSetObserver
  {
    @Override
    public void onChanged ()
    {
      super.onChanged ();
      dataValid_ = true;

      notifyDataSetChanged ();
    }

    @Override
    public void onInvalidated ()
    {
      super.onInvalidated ();
      dataValid_ = false;

      notifyDataSetChanged ();
    }
  }
}