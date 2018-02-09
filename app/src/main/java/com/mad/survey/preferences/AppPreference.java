package com.mad.survey.preferences;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.mad.survey.utils.Utils;

import org.json.JSONObject;

public class AppPreference {
	public static final String SHARED_PREF_KEY = "com.madqq.android";
	
	public static final String PREF_KEY_YOUR_NAME = "key_your_name";
	public static final String PREF_KEY_YOUR_EMAIL = "key_your_email";
	public static final String PREF_KEY_YOUR_COMPANY = "key_your_company";
	public static final String PREF_KEY_YOUR_PHONE = "key_your_phone";
	public static final String PREF_KEY_YOUR_STATE_CODE = "key_your_state";
	public static final String PREF_KEY_UNITS_LABEL = "key_units_label";
	public static final String PREF_KEY_FIRST_TIME = "key_first_time";
	public static final String PREF_KEY_PINCH_FIRST_TIME = "key_pinch_first_time";
	public static final String PREF_KEY_EMAIL_REPORT = "key_email_report";


	public static SharedPreferences getPreferences(Context ctx) {
		return ctx.getSharedPreferences(SHARED_PREF_KEY, Activity.MODE_PRIVATE);
	}

	public static void setSharedPrefValue(Context ctx, String key, String value) {
		SharedPreferences.Editor editor = getPreferences(ctx).edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static String getStringPrefValue(Context ctx, String key) {
		return getPreferences(ctx).getString(key, "");
	}

	public static String getStringPrefValue(Context ctx, String key,
			String defval) {
		return getPreferences(ctx).getString(key, defval);
	}

	public static void setSharedPrefValue(Context ctx, String key, int value) {
		SharedPreferences.Editor editor = getPreferences(ctx).edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public static int getIntPrefValue(Context ctx, String key) {
		return getPreferences(ctx).getInt(key, 0);
	}

	public static int getIntPrefValue(Context ctx, String key, int defval) {
		return getPreferences(ctx).getInt(key, defval);
	}

	public static long getLongPrefValue(Context ctx, String key) {
		return getPreferences(ctx).getLong(key, 0L);
	}

	public static long getLongPrefValue(Context ctx, String key, long defval) {
		return getPreferences(ctx).getLong(key, defval);
	}

	public static void setSharedPrefValue(Context ctx, String key, long value) {
		SharedPreferences.Editor editor = getPreferences(ctx).edit();
		editor.putLong(key, value);
		editor.commit();
	}

	public static void setSharedPrefValue(Context ctx, String key, float value) {
		SharedPreferences.Editor editor = getPreferences(ctx).edit();
		editor.putFloat(key, value);
		editor.commit();
	}

	public static Float getFloatPrefValue(Context ctx, String key) {
		return getPreferences(ctx).getFloat(key, 0f);
	}

	public static void setSharedPrefValue(Context ctx, String key, boolean value) {
		SharedPreferences.Editor editor = getPreferences(ctx).edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public static boolean getBooleanPrefValue(Context ctx, String key) {
		return getPreferences(ctx).getBoolean(key, false);
	}

	public static boolean getBooleanPrefValue(Context ctx, String key,
			boolean defval) {
		return getPreferences(ctx).getBoolean(key, defval);
	}

	public static void clearPrefValue(Context ctx, String key) {
		SharedPreferences.Editor editor = getPreferences(ctx).edit();
		editor.remove(key);
		editor.commit();
	}

	public static void setEmailReportPreference(Context ctx, boolean bvalue){
		setSharedPrefValue(ctx, PREF_KEY_EMAIL_REPORT, bvalue);
	}

	public static boolean getEmailReportPreference(Context ctx){
		return getBooleanPrefValue(ctx, PREF_KEY_EMAIL_REPORT, true);
	}

    //"setup":{"version":"1.0","user_name":"test","user_phone":"test","user_state":"test","user_email":"","user_company":"test"}
	public static JSONObject getSetupPostJSON(Context context){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("version", Utils.getVersionName(context));
            jsonObject.put("user_name", getStringPrefValue(context, PREF_KEY_YOUR_NAME));
            jsonObject.put("user_phone", getStringPrefValue(context, PREF_KEY_YOUR_PHONE));
            jsonObject.put("user_state", getStringPrefValue(context, PREF_KEY_YOUR_STATE_CODE));
            jsonObject.put("user_email", getStringPrefValue(context, PREF_KEY_YOUR_EMAIL));
            jsonObject.put("user_company", getStringPrefValue(context, PREF_KEY_YOUR_COMPANY));
        }catch(Exception e){
            e.printStackTrace();
        }

        return jsonObject;
	}
}
