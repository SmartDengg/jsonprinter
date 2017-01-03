package com.smartdengg.jsonprinter;

import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * 创建时间: 2016/12/18 19:59 <br>
 * 作者: dengwei <br>
 * 描述: check for Android platform
 */
final class Platform {

  private static final int ANDROID_API_VERSION_IS_NOT_ANDROID = 0;
  private static final int ANDROID_API_VERSION = resolveAndroidApiVersion();
  private static final boolean IS_ANDROID =
      ANDROID_API_VERSION != ANDROID_API_VERSION_IS_NOT_ANDROID;

  /** Utility class. */
  private Platform() {
    throw new AssertionError("No instances!");
  }

  /**
   * Returns {@code true} if and only if the current platform is Android.
   *
   * @return {@code true} if and only if the current platform is Android
   */
  static boolean isAndroid() {
    return IS_ANDROID;
  }

  /**
   * Returns version of Android API.
   *
   * @return version of Android API or {@link #ANDROID_API_VERSION_IS_NOT_ANDROID } if version
   * can not be resolved or if current platform is not Android.
   */
  static int getAndroidApiVersion() {
    return ANDROID_API_VERSION;
  }

  /**
   * Resolves version of Android API.
   *
   * @return version of Android API or {@link #ANDROID_API_VERSION_IS_NOT_ANDROID} if version can
   * not be resolved
   * or if the current platform is not Android.
   * @see <a href="http://developer.android.com/reference/android/os/Build.VERSION.html#SDK_INT">Documentation</a>
   */
  private static int resolveAndroidApiVersion() {
    try {
      return (Integer) Class.forName("android.os.Build$VERSION", true, getSystemClassLoader())
          .getField("SDK_INT")
          .get(null);
    } catch (Exception e) { // NOPMD
      // Can not resolve version of Android API, maybe current platform is not Android
      // or API of resolving current Version of Android API has changed in some release of Android
      return ANDROID_API_VERSION_IS_NOT_ANDROID;
    }
  }

  /**
   * Return the system {@link ClassLoader}.
   */
  private static ClassLoader getSystemClassLoader() {
    if (System.getSecurityManager() == null) {
      return ClassLoader.getSystemClassLoader();
    } else {
      return AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() {
        @Override public ClassLoader run() {
          return ClassLoader.getSystemClassLoader();
        }
      });
    }
  }
}
