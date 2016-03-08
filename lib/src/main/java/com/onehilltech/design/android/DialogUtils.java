package com.onehilltech.design.android;

import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.view.View;

@SuppressWarnings ("unused")
public class DialogUtils
{
  public static void setView (AlertDialog.Builder builder, View view)
  {
    Resources r = builder.getContext ().getResources ();
    int leftSpacing = r.getDimensionPixelSize (R.dimen.material_design_dialog_content_margin_left);
    int rightSpacing = r.getDimensionPixelSize (R.dimen.material_design_dialog_content_margin_right);
    int bottomSpacing = r.getDimensionPixelSize (R.dimen.material_design_dialog_content_margin_bottom);
    int topSpacing = r.getDimensionPixelSize (R.dimen.material_design_dialog_content_margin_top);

    builder.setView (view, leftSpacing, topSpacing, rightSpacing, bottomSpacing);
  }
}
