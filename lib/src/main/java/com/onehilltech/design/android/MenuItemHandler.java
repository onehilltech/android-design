package com.onehilltech.design.android;

import android.view.MenuItem;

import java.util.HashMap;

/**
 * Associate MenuItem objects with handlers. By using this class, the client does
 * not have to rely on if/else or switch/case statements to dispatch selecting a
 * menu item.
 */
public class MenuItemHandler
{
  public interface OnMenuItemSelectedListener
  {
    void onMenuItemSelected (MenuItem menuItem);
  }

  /// Mapping of menu items listeners.
  private final HashMap <Integer, OnMenuItemSelectedListener> listeners_ = new HashMap<> ();

  /**
   * Add a menu item to the handler.
   *
   * @param menuItemId        Id of the menu item.
   * @param listener          Target listener
   */
  public OnMenuItemSelectedListener put (int menuItemId, OnMenuItemSelectedListener listener)
  {
    return this.listeners_.put (menuItemId, listener);
  }

  /**
   * Dispatch selection of a menu item.
   *
   * @param item        The selected menu item.
   * @return
   */
  public boolean onMenuItemSelected (MenuItem item)
  {
    OnMenuItemSelectedListener listener = this.listeners_.get (item.getItemId ());

    if (listener == null)
      return false;

    listener.onMenuItemSelected (item);
    return true;
  }
}
