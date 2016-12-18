package com.smartdengg.jsonprinter;

interface Printer {

  //Printer t(String tag, int methodCount, int jsonIndent);

  void v(String tag, String message);

  void i(String tag, String message);

  void d(String tag, String message);

  void w(String tag, String message);

  void e(String tag, String message);

  void wtf(String tag, String message);

  void log(int priority, String tag, String message);
}
