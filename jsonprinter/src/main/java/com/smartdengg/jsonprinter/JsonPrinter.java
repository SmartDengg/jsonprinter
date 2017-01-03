package com.smartdengg.jsonprinter;

/**
 * 创建时间:  2016/12/18 20:58 <br>
 * 作者:  SmartDengg <br>
 * 描述:
 */
public class JsonPrinter {

  public static final String TAG = JsonPrinter.class.getSimpleName();

  /**
   * Json格式的字符串缩进长度为"4"
   * 如:
   * <pre>
   * {
   *     "query": "Pizza",
   *     "locations": [
   *         94043,
   *         90210
   *     ]
   * }</pre>
   */
  public static final int JSON_INDENT = 4;
  public static final int METHOD_COUNT = 0;

  private static Printer sPrinter;

  static {
    if (Platform.isAndroid()) sPrinter = AndroidJsonPrinter.newInstance();
  }

  private JsonPrinter() {
    throw new AssertionError("No instance!");
  }

  public static void v(String json, String... extra) {
    sPrinter.v(TAG, json, extra);
  }

  public static void v(String tag, String json, String... extra) {
    sPrinter.v(tag, json, extra);
  }

  public static void i(String json, String... extra) {
    sPrinter.i(TAG, json, extra);
  }

  public static void i(String tag, String json, String... extra) {
    sPrinter.i(tag, json, extra);
  }

  public static void d(String json) {
    sPrinter.d(TAG, json);
  }

  public static void d(String tag, String json, String... extra) {
    sPrinter.d(tag, json, extra);
  }

  public static void w(String json, String... extra) {
    sPrinter.w(TAG, json, extra);
  }

  public static void w(String tag, String json, String... extra) {
    sPrinter.w(tag, json, extra);
  }

  public static void e(String json, String... extra) {
    sPrinter.e(TAG, json, extra);
  }

  public static void e(String tag, String json, String... extra) {
    sPrinter.e(tag, json, extra);
  }

  public static void wtf(String json, String... extra) {
    sPrinter.wtf(TAG, json, extra);
  }

  public static void wtf(String tag, String json, String... extra) {
    sPrinter.wtf(TAG, json, extra);
  }
}
