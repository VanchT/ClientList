package com.tolkachov.clientsmanager.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

public final class Util {
	
	private static final String TAG = Util.class.getSimpleName();
	private static final String HASH_ALGORITHM_MD5 = "MD5";
	private static final String URL_SCHEME = "https";
	
	/**
     * 
     * @param context
     * @param domain
     */
    private static void clearCookiesForDomain(Context context, String domain) {
        // This is to work around a bug where CookieManager may fail to instantiate if CookieSyncManager
        // has never been created.
        CookieSyncManager syncManager = CookieSyncManager.createInstance(context);
        syncManager.sync();

        CookieManager cookieManager = CookieManager.getInstance();

        String cookies = cookieManager.getCookie(domain);
        if (cookies == null) {
            return;
        }

        String[] splitCookies = cookies.split(";");
        for (String cookie : splitCookies) {
            String[] cookieParts = cookie.split("=");
            if (cookieParts.length > 0) {
                String newCookie = cookieParts[0].trim() + "=;expires=Sat, 1 Jan 2000 00:00:01 UTC;";
                cookieManager.setCookie(domain, newCookie);
            }
        }
        cookieManager.removeExpiredCookie();
    }
	
	/**
	 * Prints Hash Key of the application.
	 * @param context The activity or application context.
	 */
    public static void printHashKey(Context context) {
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(
					context.getPackageName(), PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				Log.d(TAG, "Application hashKey = " + Base64.encodeToString(md.digest(), Base64.DEFAULT));
			}
		} catch (NameNotFoundException e) {
		
		} catch (NoSuchAlgorithmException e) {
		
		}
	}
    
    /**
     * Returns true if all items in subset are in superset, treating null and
     * empty collections as the same.
     * @param subset
     * @param superset
     * @return True if all items in subset are in superset.
     */
    public static <T> boolean isSubset(Collection<T> subset, Collection<T> superset) {
        if ((superset == null) || (superset.size() == 0)) {
            return ((subset == null) || (subset.size() == 0));
        }

        HashSet<T> hash = new HashSet<T>(superset);
        for (T t : subset) {
            if (!hash.contains(t)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * 
     * @param c
     * @return
     */
    public static <T> boolean isNullOrEmpty(Collection<T> c) {
        return (c == null) || (c.size() == 0);
    }

    /**
     * 
     * @param s
     * @return
     */
    public static boolean isNullOrEmpty(String s) {
        return (s == null) || (s.length() == 0);
    }
    
    /**
     * 
     * @param ts
     * @return
     */
    public static <T> ArrayList<T> arrayList(T... ts) {
        ArrayList<T> arrayList = new ArrayList<T>(ts.length);
        for (T t : ts) {
            arrayList.add(t);
        }
        return arrayList;
    }
    
    /**
     * 
     * @param key
     * @return
     */
    public static String md5hash(String key) {
        MessageDigest hash = null;
        try {
            hash = MessageDigest.getInstance(HASH_ALGORITHM_MD5);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }

        hash.update(key.getBytes());
        byte[] digest = hash.digest();
        StringBuilder builder = new StringBuilder();
        for (int b : digest) {
            builder.append(Integer.toHexString((b >> 4) & 0xf));
            builder.append(Integer.toHexString((b >> 0) & 0xf));
        }
        return builder.toString();
    }
    
    /**
     * 
     * @param authority
     * @param path
     * @param parameters
     * @return
     */
    public static Uri buildUri(String authority, String path, Bundle parameters) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(URL_SCHEME);
        builder.authority(authority);
        builder.path(path);
        for (String key : parameters.keySet()) {
            Object parameter = parameters.get(key);
            if (parameter instanceof String) {
                builder.appendQueryParameter(key, (String) parameter);
            }
        }
        return builder.build();
    }
    
    /**
     * 
     * @param bundle
     * @param key
     * @param value
     * @throws Exception 
     */
    public static void putObjectInBundle(Bundle bundle, String key, Object value) throws Exception {
        if (value instanceof String) {
            bundle.putString(key, (String) value);
        } else if (value instanceof Parcelable) {
            bundle.putParcelable(key, (Parcelable) value);
        } else if (value instanceof byte[]) {
            bundle.putByteArray(key, (byte[]) value);
        } else {
            throw new Exception("Attempted to add unsupported type to Bundle");
        }
    }
    
    /**
     * 
     * @param closeable
     */
    public static void closeQuietly(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException ioe) {}
    }

    /**
     * 
     * @param connection
     */
    public static void disconnectQuietly(URLConnection connection) {
        if (connection instanceof HttpURLConnection) {
            ((HttpURLConnection)connection).disconnect();
        }
    }
    
    /**
     * 
     * @param context
     * @param key
     * @return
     */
    public static String getMetadataString(Context context, String key) {
    	String result = null;
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            if (ai.metaData != null) {
                result = ai.metaData. getString(key);
            }
        } catch (PackageManager.NameNotFoundException e) {
            // if we can't find it in the manifest, just return null
        } 
        return result;
    }
    
    /**
     * 
     * @param jsonObject
     * @return
     */
    static Map<String, Object> convertJSONObjectToHashMap(JSONObject jsonObject) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        JSONArray keys = jsonObject.names();
        for (int i = 0; i < keys.length(); ++i) {
            String key;
            try {
                key = keys.getString(i);
                Object value = jsonObject.get(key);
                if (value instanceof JSONObject) {
                    value = convertJSONObjectToHashMap((JSONObject) value);
                }
                map.put(key, value);
            } catch (JSONException e) {
            }
        }
        return map;
    }
    
    /**
     * 
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static String readStreamToString(InputStream inputStream) throws IOException {
        BufferedInputStream bufferedInputStream = null;
        InputStreamReader reader = null;
        try {
            bufferedInputStream = new BufferedInputStream(inputStream);
            reader = new InputStreamReader(bufferedInputStream);
            StringBuilder stringBuilder = new StringBuilder();

            final int bufferSize = 1024 * 2;
            char[] buffer = new char[bufferSize];
            int n = 0;
            while ((n = reader.read(buffer)) != -1) {
                stringBuilder.append(buffer, 0, n);
            }

            return stringBuilder.toString();
        } finally {
            closeQuietly(bufferedInputStream);
            closeQuietly(reader);
        }
    }
    
    /**
     * 
     * @param str1
     * @param str2
     * @return
     */
    public static boolean stringsEqualOrEmpty(String str1, String str2) {
        boolean str1Empty = TextUtils.isEmpty(str1);
        boolean str2Empty = TextUtils.isEmpty(str2);

        if (str1Empty && str2Empty) {
            // Both null or empty, they match.
            return true;
        }
        if (!str1Empty && !str2Empty) {
            // Both non-empty, check equality.
            return str1.equals(str2);
        }
        // One empty, one non-empty, can't match.
        return false;
    }

    /**
	 * Translates a bitmap to byte array.
	 * @param bitmap The bitmap you would like to translate.
	 * @return Returns a byte array of null if bitmap is null.
	 */
	public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
		Logger.d(TAG, "Get bitmap as ByteArray.");
		byte[] result = null;
		if (bitmap == null) {
			Logger.w(TAG, "Image is null!");
		} else {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		    bitmap.compress(CompressFormat.PNG, 0, outputStream); 
		    result = outputStream.toByteArray();
		}
	    return result;
	}
	
	/**
	 * Translates a byte array to bitmap.
	 * @param bitmapAsArray The array of bytes you would like to translate.
	 * @return Returns a bitmap or null if array of bytes is null.
	 */
	public static Bitmap getByteArrayAsBitmap(byte[] bitmapAsArray){
		Logger.d(TAG, "Get ByteArray as bitmap.");
		Bitmap bitmap = null;
		if (bitmapAsArray == null) {
			Logger.w(TAG, "Image byte array is null!");
		} else {
			bitmap = BitmapFactory.decodeByteArray(bitmapAsArray, 0, bitmapAsArray.length);
		}
		return bitmap;
	}
    
	public static Date convertToDate(String dateString, String formatString){
	    SimpleDateFormat dateFormat = new SimpleDateFormat(formatString, Locale.getDefault());
	    Date convertedDate = new Date();
	    try {
	        convertedDate = dateFormat.parse(dateString);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return convertedDate;
	}

	public static boolean compareByManthAndDay(String dateString){
		boolean resalt = false;
		Calendar currentCalendar = Calendar.getInstance();
		Date birthday = Util.convertToDate(dateString, "MM/dd/yyyy");
		Calendar birthdayCalendar = Calendar.getInstance();
		birthdayCalendar.setTime(birthday);
		if (currentCalendar.get(Calendar.MONTH) == birthdayCalendar.get(Calendar.MONTH) &&
				currentCalendar.get(Calendar.DAY_OF_MONTH) == birthdayCalendar.get(Calendar.DAY_OF_MONTH)){
			resalt = true;
		}
		return resalt;
	}
}
