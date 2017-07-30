package com.smartdengg.androidjsonprinter;

/**
 * 创建时间:  2017/02/01 22:07 <br>
 * 作者:  SmartDengg <br>
 * 描述:
 */
class TimberPlatform {

  static final boolean HAS_TIMBER = hasTimberOnClasspath();

  private TimberPlatform() {
    throw new AssertionError("No instance");
  }

  /**
   * Determines if Timber is available at runtime.
   *
   * @return true if Timber is available, false otherwise
   */
  private static boolean hasTimberOnClasspath() {
    boolean hasTimber = false;
    try {
      Class.forName("timber.log.Timber", false, TimberPlatform.class.getClassLoader());
      hasTimber = true;
    } catch (ClassNotFoundException ignored) {
    }
    return hasTimber;
  }
}
