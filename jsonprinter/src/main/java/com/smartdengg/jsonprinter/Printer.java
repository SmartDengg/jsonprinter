package com.smartdengg.jsonprinter;

interface Printer {

  //Printer t(String tag, int methodCount, int jsonIndent);

  void v(String tag, String json, String... extra);

  void i(String tag, String json, String... extra);

  void d(String tag, String json, String... extra);

  void w(String tag, String json, String... extra);

  void e(String tag, String json, String... extra);

  void wtf(String tag, String json, String... extra);

  void log(int priority, String tag, String json);
}
