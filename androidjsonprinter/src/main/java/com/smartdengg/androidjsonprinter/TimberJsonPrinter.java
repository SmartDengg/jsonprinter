package com.smartdengg.androidjsonprinter;

import timber.log.Timber;

/**
 * 创建时间:  2017/02/01 22:10 <br>
 * 作者:  SmartDengg <br>
 * 描述:
 */
class TimberJsonPrinter extends AndroidJsonPrinter {

  private TimberJsonPrinter() {
    super();
  }

  static Printer newInstance() {
    return new TimberJsonPrinter();
  }

  @Override public synchronized void log(int priority, String tag, String json) {
    Timber.tag("timber-" + tag).log(priority, json);
  }
}
