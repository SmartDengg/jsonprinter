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
  public static final int METHOD_COUNT = 40;

  private static Printer sPrinter;

  static {
    if (Platform.isAndroid()) sPrinter = AndroidJsonPrinter.newInstance();
  }

  private JsonPrinter() {
    throw new AssertionError("No instance!");
  }

  public static void v(String message) {
    sPrinter.v(TAG, message);
  }

  public static void v(String tag, String message) {
    sPrinter.v(tag, message);
  }

  public static void i(String message) {
    sPrinter.i(TAG, message);
  }

  public static void i(String tag, String message) {
    sPrinter.i(tag, message);
  }

  public static void d(String message) {
    sPrinter.d(TAG, message);
  }

  public static void d(String tag, String message) {
    sPrinter.d(tag, message);
  }

  public static void w(String message) {
    sPrinter.w(TAG, message);
  }

  public static void w(String tag, String message) {
    sPrinter.w(tag, message);
  }

  public static void e(String message) {
    sPrinter.e(TAG, message);
  }

  public static void e(String tag, String message) {
    sPrinter.e(tag, message);
  }

  public static void wtf(String message) {
    sPrinter.wtf(TAG, message);
  }

  public static void wtf(String tag, String message) {
    sPrinter.wtf(TAG, message);
  }
}
