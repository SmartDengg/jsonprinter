package com.smartdengg.androidjsonprinter;

import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import java.io.PrintWriter;
import java.io.StringWriter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.smartdengg.androidjsonprinter.JsonPrinter.JSON_INDENT;

/**
 * 创建时间: 2016/12/18 19:51 <br>
 * 作者: dengwei <br>
 * 描述: Json格式的日志打印类
 */
class AndroidJsonPrinter implements Printer {

  private final ThreadLocal<String> sLocalTag = new ThreadLocal<>();

  /** Drawing toolbox */
  private static final char TOP_LEFT_CORNER = '╔';
  private static final char BOTTOM_LEFT_CORNER = '╚';
  private static final char MIDDLE_CORNER = '╟';
  private static final char HORIZONTAL_DOUBLE_LINE = '║';
  private static final String DOUBLE_DIVIDER = "════════════════════════════════════════════";
  private static final String SINGLE_DIVIDER = "────────────────────────────────────────────";
  private static final String TOP_BORDER = TOP_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
  private static final String BOTTOM_BORDER = BOTTOM_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
  private static final String MIDDLE_BORDER = MIDDLE_CORNER + SINGLE_DIVIDER + SINGLE_DIVIDER;
  private static final String EMPTY_JSON = " " + "Empty/Null json content";

  /*我们可以通过"adb logcat -g"指令，来查看max payload的值。~ 4*1024 需要注意的是在Android平台编码集是"UTF-8"*/
  //{@link https://github.com/android/platform_frameworks_base/blob/master/core/java/android/util/Log.java}
  private static final int LOGGER_ENTRY_MAX_PAYLOAD = 4000;
  private static final int CALL_STACK_OFFSET = 4;
  private static final String SEPARATOR = System.getProperty("line.separator");

  AndroidJsonPrinter() {

  }

  static Printer newInstance() {
    return new AndroidJsonPrinter();
  }

  /** 将字符串转成Json格式进行输出 */
  private void printer(int priority, String tag, String json, String... extra) {

    if (!compare(tag, JsonPrinter.TAG)) sLocalTag.set(tag);

    if (TextUtils.isEmpty(json)) {
      boxing(priority, tag, EMPTY_JSON, extra);
      return;
    }
    try {
      if (json.startsWith("{")) {
        JSONObject jsonObject = new JSONObject(json);
        boxing(priority, tag, jsonObject.toString(JSON_INDENT), extra);
        return;
      }
      if (json.startsWith("[")) {
        JSONArray jsonArray = new JSONArray(json);
        boxing(priority, tag, jsonArray.toString(JSON_INDENT), extra);
        return;
      }
      innerError(tag, new IllegalArgumentException("Invalid Json"), json);
    } catch (JSONException e) {
      innerError(tag, e, SEPARATOR + json);
    }
  }

  private void innerError(String tag, Throwable throwable, String message, Object... args) {

    if (throwable != null && message != null) message += " : " + getStackTraceString(throwable);
    if (throwable != null && message == null) message = getStackTraceString(throwable);
    if (message == null) message = "No message/exception is set";

    String errorMessage = formatString(message, args);
    boxing(Log.ERROR, tag, errorMessage);
  }

  private void boxing(int priority, String tag, String message, String... extra) {

    logTopBorder(priority, tag);
    if (extra != null && extra.length > 0) {
      //noinspection ForLoopReplaceableByForEach
      for (int i = 0, n = extra.length; i < n; i++) {
        logContent(priority, tag, extra[i]);
      }
      logDivider(priority, tag);
    }

    logHeaderContent(priority, tag, getMethodCount());
    logContent(priority, tag, message);
    logBottomBorder(priority, tag);
  }

  private void logHeaderContent(int priority, String tag, int methodCount) {

    String level = "  \u21e2  ";
    StackTraceElement[] trace = new Throwable().getStackTrace();

    log(priority, tag, HORIZONTAL_DOUBLE_LINE
        + " Thread: ["
        + Thread.currentThread().getName()
        + " ] "
        + SEPARATOR);

    int stackOffset = CALL_STACK_OFFSET;
    //corresponding method count with the current stack may exceeds the stack trace. Trims the count
    if (methodCount + stackOffset > trace.length) methodCount = trace.length - stackOffset - 1;

    for (int i = methodCount; i > 0; i--) {
      int stackIndex = i + stackOffset;
      if (stackIndex >= trace.length) continue;

      //noinspection StringBufferReplaceableByString
      StringBuilder builder = new StringBuilder();
      builder.append(HORIZONTAL_DOUBLE_LINE)
          .append(level)
          .append(getSimpleClassName(trace[stackIndex].getClassName()))
          .append(".")
          .append(trace[stackIndex].getMethodName())
          .append(" ")
          .append(" (")
          .append(trace[stackIndex].getFileName())
          .append(":")
          .append(trace[stackIndex].getLineNumber())
          .append(")");
      level = "  " + level;
      log(priority, tag, builder.toString());
    }
    logDivider(priority, tag);
  }

  private void logTopBorder(int priority, String tag) {
    log(priority, tag, TOP_BORDER);
  }

  private void logContent(int priority, String tag, String message) {
    String[] parts = message.split(SEPARATOR);
    for (String part : parts) log(priority, tag, HORIZONTAL_DOUBLE_LINE + " " + part);
  }

  private void logBottomBorder(int priority, String tag) {
    log(priority, tag, BOTTOM_BORDER);
  }

  private void logDivider(int priority, String tag) {
    log(priority, tag, MIDDLE_BORDER);
  }

  private static String formatString(String message, Object... args) {
    return args.length == 0 ? message : String.format(message, args);
  }

  private static String getSimpleClassName(String name) {
    int lastIndex = name.lastIndexOf(".");
    return name.substring(lastIndex + 1);
  }

  /**
   * Don't replace this with Log.getStackTraceString()
   * - it hides UnknownHostException, which is not what we want.
   */
  private static String getStackTraceString(Throwable throwable) {
    StringWriter stringWriter = new StringWriter(256);
    PrintWriter printWriter = new PrintWriter(stringWriter, false);
    throwable.printStackTrace(printWriter);
    printWriter.flush();
    return stringWriter.toString();
  }

  private String getTag() {

    String tag = sLocalTag.get();
    if (tag != null) {
      sLocalTag.remove();
      return tag;
    }
    if (TextUtils.isEmpty(tag = JsonPrinter.TAG)) {
      throw new IllegalStateException("TAG cannot be null");
    }
    return tag;
  }

  private static int getMethodCount() {
    int count = JsonPrinter.METHOD_COUNT;
    //noinspection ConstantConditions
    if (count < 0) throw new IllegalStateException("methodCount cannot be negative");
    return count;
  }

  private static boolean compare(String str1, String str2) {
    return (str1 == null ? str2 == null : str1.equals(str2));
  }

  @Override public void v(String tag, String json, String... extra) {
    this.printer(Log.VERBOSE, tag, json, extra);
  }

  @Override public void i(String tag, String json, String... extra) {
    this.printer(Log.INFO, tag, json, extra);
  }

  @Override public void d(String tag, String json, String... extra) {
    this.printer(Log.DEBUG, tag, json, extra);
  }

  @Override public void w(String tag, String json, String... extra) {
    this.printer(Log.WARN, tag, json, extra);
  }

  @Override public void e(String tag, String json, String... extra) {
    this.printer(Log.ERROR, tag, json, extra);
  }

  @Override public void wtf(String tag, String json, String... extra) {
    this.printer(Log.ASSERT, tag, json, extra);
  }

  @Override public synchronized void log(int priority, String tag, String json) {
    if (json.length() < LOGGER_ENTRY_MAX_PAYLOAD) {
      if (priority == Log.ASSERT) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) Log.wtf(tag, json);
      } else {
        Log.println(priority, tag, json);
      }
      return;
    }

    //Split by line, then ensure each line can fit into Log 's maximum length.
    for (int i = 0, length = json.length(); i < length; i++) {
      int newline = json.indexOf(SEPARATOR, i);
      newline = newline != -1 ? newline : length;
      do {
        int end = Math.min(newline, i + LOGGER_ENTRY_MAX_PAYLOAD);
        String chunkMessage = json.substring(i, end);
        if (priority == Log.ASSERT) {
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) Log.wtf(tag, chunkMessage);
        } else {
          Log.println(priority, tag, chunkMessage);
        }
        i = end;
      } while (i < newline);
    }
  }
}
