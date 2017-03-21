package com.smartdengg.jsonprinter;

import com.smartdengg.printer.Printer;

/**
 * 创建时间:  2017/03/21 15:47 <br>
 * 作者:  SmartDengg <br>
 * 描述:
 */
class JavaJsonPrinter extends AndroidJsonPrinter {

  private static final String SEPARATOR = System.getProperty("line.separator");

  static Printer newInstance() {
    return new JavaJsonPrinter();
  }

  @Override public synchronized void log(int priority, String tag, String json) {
    String[] chunks = json.split(SEPARATOR);
    for (int i = 0, n = chunks.length; i < n; i++) {
      if (priority > ERROR) {
        System.err.println(tag + " : " + chunks[i]);
      } else {
        System.out.println(tag + " : " + chunks[i]);
      }
    }
  }
}
