/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.framework.utils;

import android.app.Activity;
import android.content.DialogInterface;

/**
 * Simple listener used to exit the app in a few cases.
 *
 * @author Sean Owen
 */
public final class FinishListener
    implements DialogInterface.OnClickListener, DialogInterface.OnCancelListener, Runnable {

  private final Activity activityToFinish;

  public FinishListener(Activity activityToFinish) {
    this.activityToFinish = activityToFinish;
  }

  public void onCancel(DialogInterface dialogInterface) {
    run();
  }

  public void onClick(DialogInterface dialogInterface, int i) {
    run();
  }

  public void run() {
    activityToFinish.finish();
  }

}
