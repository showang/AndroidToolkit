package showang.toolkit.utils;

import android.content.Context;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

public class Debug {
	private static boolean debugEnabled;
	private static String debugLogPath;
	private static DataOutputStream logStream;
	private static long runningTimestamp = 0;
	private static String TAG = "app";

	public static void init(Context context){
		TAG = context.getApplicationInfo().name;
	}

	public static void setDebugEnabled(boolean enabled) {
		debugEnabled = enabled;
	}

	public static void setDebugLogPath(String path) {
		debugLogPath = path;
	}

	public static boolean isDebugEnabled() {
		return debugEnabled;
	}

	public static void i(Object msg) {
		if (debugEnabled) {
			Log.i(TAG, "" + msg);
			writeLog("" + msg);
		}
	}

	public static void i(String tag, Object msg) {
		if (debugEnabled) {
			Log.i(tag, "" + msg);
			writeLog("" + msg);
		}
	}

	public static void e(Object msg) {
		if (debugEnabled) {
			Log.e(TAG, getClassLineNumber() + msg);
			writeLog("" + msg);
		}
	}

	public static void e(String tag, Object msg) {
		if (debugEnabled) {
			Log.e(tag, getClassLineNumber() + msg);
			writeLog("" + msg);
		}
	}

	public static void w(Object msg) {
		if (debugEnabled) {
			Log.w(TAG, "" + msg);
			writeLog("" + msg);
		}
	}

	public static void w(String tag, Object msg) {
		if (debugEnabled) {
			Log.w(tag, "" + msg);
			writeLog("" + msg);
		}
	}

	public static void wtf(Object msg) {
		if (debugEnabled) {
			Log.wtf(TAG, "" + msg);
			writeLog("" + msg);
		}
	}

	public static void startToMeasureRunningTime() {
		runningTimestamp = System.currentTimeMillis();
	}

	public static long printRunningTime(Object msg) {
		long timeLength = System.currentTimeMillis() - runningTimestamp;
		Debug.i(msg + " running time: " + timeLength);
		return timeLength;
	}

	private static void writeLog(String msg) {
		if (debugLogPath == null) {
			return;
		}
		try {
			if (logStream == null) {
				final Calendar calendar = Calendar.getInstance();
				final String dateTime = String.format("%04d%02d%02d_%02d%02d", calendar.get(Calendar.YEAR),
						calendar.get(Calendar.MONTH) + 1,
						calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
				final String logTimeFolderPath = debugLogPath + File.separator + dateTime;
				final File logTimeFolder = new File(logTimeFolderPath);
				if (!logTimeFolder.exists()) {
					if (!logTimeFolder.mkdir()) {
						return;
					}
				}
				final File logFile = new File(logTimeFolderPath + File.separator + "citytalk.log");
				if (!logFile.exists()) {
					logFile.createNewFile();
				}
				logStream = new DataOutputStream(new FileOutputStream(logFile));
			}
			logStream.write(String.format("[%s]\t%s\n", DateFormat.getDateTimeInstance().format(new Date()), msg).getBytes());
		} catch (Exception e) {
			Log.e(TAG, Log.getStackTraceString(e));
		}
	}

	private static String getClassLineNumber() {
		try {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			if (stackTraceElements == null || stackTraceElements.length == 0) {
				return "";
			}
			int deep = 0;
			for (int i = 0; i < stackTraceElements.length; i++) {
				String className = stackTraceElements[i].getClassName();
				if (className.indexOf("Debug") >= 0) {
					deep++;
				}
				if (deep == 2) {
					return "[" + stackTraceElements[i + 1].getFileName() + " line:"
							+ stackTraceElements[i + 1].getLineNumber() + "] ";
				}
			}
			return "";
		} catch (Exception e) {
			Log.i(TAG, Log.getStackTraceString(e));
			return "";
		}
	}

	public static void printHeapMemory() {
		Double allocated = new Double(android.os.Debug.getNativeHeapAllocatedSize()) / new Double((1048576));
		Double available = new Double(android.os.Debug.getNativeHeapSize()) / 1048576.0;
		Double free = new Double(android.os.Debug.getNativeHeapFreeSize()) / 1048576.0;
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(2);
		Debug.i("Heap native: allocated " + df.format(allocated) + "MB of " + df.format(available) + "MB (" + df.format(free) + "MB " +
				"free)");
		Debug.i("Heap memory: allocated " + df.format(new Double(Runtime.getRuntime().totalMemory() / 1048576)) + "MB of " +
				df.format(new Double(Runtime.getRuntime().maxMemory() / 1048576)) + "MB (" + df.format(new Double(Runtime.getRuntime()
				.freeMemory() / 1048576)) + "MB free)");
	}
}
