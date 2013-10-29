package com.tolkachov.clientsmanager.util;

import java.util.HashMap;
import java.util.Map;

import android.util.Log;

/**
 * The class is based on standard class "android.util.Log" <br>
 * and provides additional possibilities. 
 * <br><br>
 * <b>From android.util.Log</b>
 * <br><br>
 * Generally, use the Logger.v() Logger.d() Logger.i() Logger.w() and Logger.e() methods. 
 * <br><br>
 * The order in terms of verbosity, from least to most is ERROR, WARN, INFO, DEBUG, VERBOSE.
 * Verbose should never be compiled into an application except during development. 
 * Debug logs are compiled in but stripped at runtime. Error, warning and info logs are always kept. 
 * <br><br>
 * <b>Tip:</b> A good convention is to declare a TAG constant in your class: 
 * <br><br>
 * <i>private static final String TAG = "MyActivity";</i>
 * <br><br>
 * and use that in subsequent calls to the log methods. 
 * <br><br>
 * <b>Tip:</b> Don't forget that when you make a call like 
 * <br><br>
 * <i>Log.v(TAG, "index=" + i);</i>
 * <br><br>
 * that when you're building the string to pass into Log.d, the compiler uses a StringBuilder and at least three allocations occur:
 * the StringBuilder itself, the buffer, and the String object. Realistically, there is also another buffer allocation and copy,
 * and even more pressure on the gc. That means that if your log message is filtered out, you might be doing significant work 
 * and incurring significant overhead. 
 * <br><br>
 * <b>Summary</b>
 * <br>
 * <center><b>Constants</b></center>
 * 		int ASSERT Priority constant for the println method. <br>
 * 		int DEBUG Priority constant for the println method; use Log.d. <br>
 * 		int ERROR Priority constant for the println method; use Log.e. <br>
 * 		int INFO Priority constant for the println method; use Log.i. <br>
 * 		int VERBOSE Priority constant for the println method; use Log.v.<br> 
 * 		int WARN Priority constant for the println method; use Log.w.
 */
public final class Logger {
	
	public static final String DEFAULT_TAG = "Usefull_Sites_Log";
	
	private static boolean mIsLogModeOn = true;
	private static Map<String, Boolean> tagRegister;
	
	public static void init(){
		tagRegister = new HashMap<String, Boolean>();
		tagRegister.put(DEFAULT_TAG, true);
	}
	
	/**
	 * public static void Logger.registerTag(String tag, boolean mode)<br><br>
	 * Tries to register the tag with special mode.<br><br>
	 * @param tag The tag you would like register.
	 * @param mode The mode which will associated with tag. 
	 * @throws NullPointerException Throws NullPointerException if tag is empty or tag is null.
	 */
	public static void registerTag(String tag, boolean mode) throws NullPointerException {
		if (mIsLogModeOn) {
			if (Util.isNullOrEmpty(tag)){
				throw new NullPointerException("Tag cannot be null or empty!");
			} else {
				tagRegister.put(tag, mode);
			}
		}
	}
	
	public static void setLogMode(boolean mode){
		mIsLogModeOn = mode;
	}
	
	public static boolean getLogMode(){
		return mIsLogModeOn;
	}
	
	/**
	 * private static boolean Logger.verifyTag(String tag)<br><br>
	 * Verifies the tag.<br><br>
	 * @param tag The tag that must be verified.
	 * @return Returns true if the tag was verified successfully, otherwise returns false.
	 */
	private static boolean verifyTag(String tag){
		boolean result = false;
		if (mIsLogModeOn && tagRegister.containsKey(tag)) {
			result = tagRegister.get(tag);
		}
		return result;
	}
	
	/**
	 * public static void Logger.w(String tag, String msg)<br><br>
	 * Send a WARN log message. It based on the standard function <br>
	 * int android.util.Log.w(String tag, String msg)<br>
	 * Added in API level 1 <br><br>
	 * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs. 
	 * @param msg The message you would like logged.
	 */
	public static void w(String tag, String msg){
		if (Util.isNullOrEmpty(tag)){
			tag = DEFAULT_TAG;
		} 
		if (verifyTag(tag)){
			Log.w(tag, msg);
		}
	}
	
	/**
	 * public static void Logger.w(String tag, String msg)<br><br>
	 * Send a WARN log message with additional prefix.<br>
	 * It based on standard function <br>
	 * int android.util.Log.w(String tag, String msg)<br>
	 * Added in API level 1 <br><br>
	 * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs. 
	 * @param msgPrefix The prefix which will be shown before the message you would like logged.
	 * @param msg The message you would like logged.  
	 */
	public static void w(String tag, String msgPrefix, String msg){
		if (Util.isNullOrEmpty(tag)){
			tag = DEFAULT_TAG;
		} 
		if (verifyTag(tag)){
				Log.w(tag, msgPrefix + " // " + msg);
		}
	}
		
	/**
	 * public static void Logger.w(String tag, Throwable tr)<br><br>
	 * Send a WARN log with the exception.<br>
	 * It based on standard function <br>
	 * public static int w (String tag, String msg, Throwable tr)<br>
	 * Added in API level 1<br><br>
	 * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
	 * @param tr An exception to log
	 */
	public static void w(String tag, Throwable tr){
		if (Util.isNullOrEmpty(tag)){
			tag = DEFAULT_TAG;
		} 
		if (verifyTag(tag)){
				Log.w (tag, tr);
		}
	}
	
	/**
	 * public static void Logger.w(String tag, String msg, Throwable tr)<br><br>
	 * Send a WARN log message and log the exception.<br>
	 * It based on standard function <br>
	 * public static int w (String tag, String msg, Throwable tr)<br>
	 * Added in API level 1<br><br>
	 * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
	 * @param msg The message you would like logged.
	 * @param tr An exception to log
	 */
	public static void w(String tag, String msg, Throwable tr){
		if (Util.isNullOrEmpty(tag)){
			tag = DEFAULT_TAG;
		} 
		if (verifyTag(tag)){
				Log.w(tag, msg, tr);
		}
	}
	
	public static void e(String tag, String msg){
		if (Util.isNullOrEmpty(tag)){
			tag = DEFAULT_TAG;
		} 
		if (verifyTag(tag)){
			Log.e(tag, msg);
		}
	}
	
	public static void e(String tag, String msgPrefix, String msg){
		if (Util.isNullOrEmpty(tag)){
			tag = DEFAULT_TAG;
		} 
		if (verifyTag(tag)){
			Log.e(tag, msgPrefix + " // " + msg);
		}
	}
	
	public static void e(String tag, String msg, Throwable tr){
		if (Util.isNullOrEmpty(tag)){
			tag = DEFAULT_TAG;
		} 
		if (verifyTag(tag)){
			Log.e(tag, msg, tr);
		}
	}
	
	public static void i(String tag, String msg){
		if (Util.isNullOrEmpty(tag)){
			tag = DEFAULT_TAG;
		} 
		if (verifyTag(tag)){
			Log.i(tag, msg);
		}
	}
	
	public static void i(String tag, String msgPrefix, String msg){
		if (Util.isNullOrEmpty(tag)){
			tag = DEFAULT_TAG;
		} 
		if (verifyTag(tag)){
			Log.i(tag, msgPrefix + " // " + msg);
		}
	}
	
	public static void i(String tag, String msg, Throwable tr){
		if (Util.isNullOrEmpty(tag)){
			tag = DEFAULT_TAG;
		} 
		if (verifyTag(tag)){
			Log.i(tag, msg, tr);
		}
	}
	
	public static void d(String tag, String msg){
		if (Util.isNullOrEmpty(tag)){
			tag = DEFAULT_TAG;
		} 
		if (verifyTag(tag)){
			Log.d(tag, msg);
		}
	}
	
	public static void d(String tag, String msgPrefix, String msg){
		if (Util.isNullOrEmpty(tag)){
			tag = DEFAULT_TAG;
		} 
		if (verifyTag(tag)){
			Log.d(tag, msgPrefix + " // " + msg);
		}
	}
	
	public static void d(String tag, String msg, Throwable tr){
		if (Util.isNullOrEmpty(tag)){
			tag = DEFAULT_TAG;
		} 
		if (verifyTag(tag)){
			Log.d(tag, msg, tr);
		}
	}
	
	public static void v(String tag, String msg){
		if (Util.isNullOrEmpty(tag)){
			tag = DEFAULT_TAG;
		} 
		if (verifyTag(tag)){
			Log.v(tag, msg);
		}
	}
	
	public static void v(String tag, String msgPrefix, String msg){
		if (Util.isNullOrEmpty(tag)){
			tag = DEFAULT_TAG;
		} 
		if (verifyTag(tag)){
			Log.v(tag, msgPrefix + " // " + msg);
		}
	}
	
	public static void v(String tag, String msg, Throwable tr){
		if (Util.isNullOrEmpty(tag)){
			tag = DEFAULT_TAG;
		} 
		if (verifyTag(tag)){
			Log.v(tag, msg, tr);
		}
	}

}
