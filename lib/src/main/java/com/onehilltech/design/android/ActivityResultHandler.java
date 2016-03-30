package com.onehilltech.design.android;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class ActivityResultHandler
{
  /**
   * Listener interface for all result code listeners. The listener is registered
   * with the ActivityResultHandler on a per request code basis.
   */
  public interface OnActivityResultListener
  {
    void onResult (int resultCode, @Nullable Intent data);
  }

  /**
   * Listener for handling standard result codes from an activity. The standard result
   * codes are Activity.RESULT_OK and Activity.RESULT_CANCELED.
   */
  public abstract class OnStandardActivityResultListener implements OnActivityResultListener
  {
    @Override
    public final void onResult (int resultCode, @Nullable Intent data)
    {
      if (resultCode == Activity.RESULT_OK)
        this.onSuccess (data);
      else if (resultCode == Activity.RESULT_CANCELED)
        this.onCanceled (data);
    }

    public abstract void onSuccess (@Nullable Intent data);
    public abstract void onCanceled (@Nullable Intent data);
  }

  /**
   * Listener for handling custom result codes for results from an activity. A
   * custom result code is any result code that starts after Activity.RESULT_FIRST_USER.
   */
  public abstract class OnCustomActivityResultListener implements OnActivityResultListener
  {
    private int resultCode_;

    /**
     * Initializing constructor.
     *
     * @param resultCode
     */
    public OnCustomActivityResultListener (int resultCode)
    {
      this.resultCode_ = resultCode;
    }

    @Override
    public final void onResult (int resultCode, @Nullable Intent data)
    {
      if (resultCode == this.resultCode_)
        this.onResult (data);
    }

    public abstract void onResult (Intent data);
  }

  /// Collection of listeners registered mapped by their request code.
  private final Multimap <Integer, OnActivityResultListener> listeners_ = HashMultimap.create ();

  /**
   * Default constructor.
   */
  @SuppressWarnings ("unused")
  public ActivityResultHandler ()
  {

  }

  /**
   * Add a listener to the handler.
   *
   * @param requestCode
   * @param listener
   * @return
   */
  public boolean add (int requestCode, OnActivityResultListener listener)
  {
    return this.listeners_.put (requestCode, listener);
  }

  /**
   * Handle a result from an activity.
   *
   * @param requestCode
   * @param resultCode
   * @param data
   */
  public void onActivityResult (int requestCode, int resultCode, Intent data)
  {
    for (OnActivityResultListener listener : this.listeners_.get (requestCode))
      listener.onResult (resultCode, data);
  }
}
