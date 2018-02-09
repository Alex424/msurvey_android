package com.mad.survey.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.BuildConfig;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

public class Utils {
	public static SharedPreferences settings;
	public static final String PREFS_NAME = "MadSurvey";

	public static String convertStreamToString(InputStream inputStream) throws IOException {
		if (inputStream != null) {
			StringBuilder sb = new StringBuilder();
			String line;
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
				while ((line = reader.readLine()) != null) {
					sb.append(line).append("\n");
				}
			} finally {
				inputStream.close();
			}
			return sb.toString();
		} else {
			return "";
		}
	}
	public static String md5(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(input.getBytes());
			BigInteger number = new BigInteger(1,messageDigest);
			String md5 = number.toString(16);
			while (md5.length() < 32) {
				md5 = "0" + md5;
			}
			return md5;
		} catch(NoSuchAlgorithmException e) {
			return null;
		}
	}

    public static void Log(String tag, String message) {
        Log.i(tag, message);
    }

    public static void Log(String type, String tag, String message) {
        if (BuildConfig.DEBUG) {
            if (type == "i") {
                Log.i(tag, message);
            }
            if (type == "e") {
                Log.e(tag, message);
            }
        }
    }

    public static String getPhoneModel()
    {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if(model.startsWith(manufacturer)){
            return capitalize(model);
        }else{
            return capitalize(manufacturer) + " " +model;
        }
    }

    public static String getOSVersion()
    {
        return "Android " + Build.VERSION.RELEASE;
    }

    private static String capitalize(String s){
        if(s == null || s.length() == 0){
            return "";
        }
        char first = s.charAt(0);
        if(Character.isUpperCase(first)){
            return s;
        }else{
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

	public static String getPrefSetting(Context context, String settingName, String defaultValue) {
		settings = context.getSharedPreferences(PREFS_NAME, 0);
		return settings.getString(settingName, defaultValue);
	}

	public static void updatePrefSetting(Context context, String settingName,
                                         String settingValue) {
		if (context != null) {
			settings = context.getSharedPreferences(PREFS_NAME, 0);
			SharedPreferences.Editor editor = settings.edit();
			editor.putString(settingName, settingValue);
			editor.commit();
		}
	}

    public static String getLangCode(Context context) {
        String actualLangCode = Locale.getDefault().getISO3Language();
        String appLangCode = Utils.getPrefSetting(context, "appLanguage", "eng");
        if(!appLangCode.equals(actualLangCode)) {
            enforceLanguage(context);
        } else {
            //enforcefontSize(context);
        }
        return appLangCode;
    }

    public static boolean isArabicLang(Context context){
        String langCode = getLangCode(context);
        return langCode == "ara" || langCode.equals("ara");
    }

    public static void updateLanguageSetting(Context context){
        if(Utils.isArabicLang(context)) {
            Utils.updatePrefSetting(context, "appLanguage", "eng");
        }else{
            Utils.updatePrefSetting(context, "appLanguage", "ara");
        }
    }

    public static void enforceLanguage(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        String languageToLoad = Utils.getPrefSetting(context, "appLanguage", "eng");
        Locale locale = new Locale(languageToLoad.substring(0, 2));
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        //config.fontScale = dm.density / dm.scaledDensity;
        context.getResources().updateConfiguration(config, dm);
    }

    public static void enforcefontSize(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        if(dm.density != dm.scaledDensity) {
            Configuration config = new Configuration();
            config.fontScale = 1;
            context.getResources().updateConfiguration(config, dm);
        }
    }

	public static boolean isConnected(Context context) {
		try {
			ConnectivityManager connectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connectivityManager
					.getActiveNetworkInfo();
			return networkInfo != null && networkInfo.isConnected();
		} catch (NullPointerException e) {
			return false;
		}
	}

	public static Drawable getImageDrawableFromAssetFile(Context context, String assetPath){
		try
		{
			InputStream ims = context.getAssets().open(assetPath);
			Drawable drawable = Drawable.createFromStream(ims, null);
			return drawable;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public static Bitmap getBitmapFromAsset(Context context, String filePath) {
		AssetManager assetManager = context.getAssets();

		InputStream istr;
		Bitmap bitmap = null;
		try {
			istr = assetManager.open(filePath);
			bitmap = BitmapFactory.decodeStream(istr);
		} catch (IOException e) {
			// handle exception
		}

		return bitmap;
	}

	public static String getStringFromAsset(Context context, String filePath){
		AssetManager assetManager = context.getAssets();

		try {
			InputStream ims = assetManager.open(filePath);
			return convertStreamToString(ims);
		}catch(Exception e){
			e.printStackTrace();
		}

		return "";
	}

	public static String getDomainNameFromUrl(String url){
		int slashslash = url.indexOf("//") + 2;
		int slashIndex = url.indexOf('/', slashslash);
		if (slashIndex < 0) slashIndex = url.length();
		return url.substring(slashslash, slashIndex);
	}

	public static final String getVersionName(Context context){
		try{
			PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return pInfo.versionName;
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}

	public static final boolean isGPSEnabled(Context context){
		LocationManager manager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}

	public static final void goToBrowser(Context ctx, String url){
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        ctx.startActivity(i);
    }

	public static final void goToPhoneCall(Context ctx, String phone){
		if (phone == null || phone.equals("")) return;

		String uri = "tel:" + phone.trim() ;
		Intent intent = new Intent(Intent.ACTION_DIAL);
		intent.setData(Uri.parse(uri));
		ctx.startActivity(intent);
	}

	public static final void goToEmail(Context ctx, String email){
		if (email == null || email.equals("")) return;

		Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", email, null));
		ctx.startActivity(Intent.createChooser(emailIntent, "Send Email"));
	}

	public static final void goToGoogleMap(Context ctx, String address){
		Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(address));
		ctx.startActivity(i);
	}

    public static void showKeyboardForce(Context ctx){
        InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }
	public static void showKeyboard(Context ctx, boolean bShow, EditText edtBox){
		if (bShow){
			InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.showSoftInput(edtBox, InputMethodManager.SHOW_IMPLICIT);
		}else{
			InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(edtBox.getWindowToken(), 0);
		}
	}

	public static void hideKeyboard(Activity activity){
		View view = activity.getCurrentFocus();
		if (view != null) {
			InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}

	public static void CreateWorkDirectories(String sPath, boolean bDelOldFiles){
		File file = new File(sPath);
		File[] children = file.listFiles();
		if (children == null) {
			file.mkdir();
		} else if (bDelOldFiles){
			Calendar today = Calendar.getInstance();		// Get today as a Calendar
			today.add(Calendar.DATE, -7);  			// Subtract 1 day
			long _7DaysAgo = today.getTimeInMillis();

			for (int i=0; i<children.length; i++){
				if (children[i].lastModified() < _7DaysAgo) {
					//Log.e(GlobalConstant.TAG, children[i].getAbsolutePath() + ", " + children[i].getName());
					children[i].delete();
				}
			}
		}
	}

    public static String getRealPathFromURI(Activity activity, Uri contentUri) {

        // can post image
        String[] proj={MediaStore.Images.Media.DATA};
        @SuppressWarnings("deprecation")
        Cursor cursor = activity.managedQuery( contentUri,
                proj, // Which columns to return
                null,       // WHERE clause; which rows to return (all rows)
                null,       // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);
    }

    @SuppressWarnings("NewApi")
    public static String getRealPathFromURIKitKat(Activity activity, Uri contentUri) {

        // Will return "image:x*"
        String wholeID = DocumentsContract.getDocumentId(contentUri);

        // Split at colon, use second item in the array
        String id = wholeID.split(":")[1];

        String[] column = { MediaStore.Images.Media.DATA };

        // where id is equal to
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = activity.getContentResolver().
                query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        column, sel, new String[]{ id }, null);

        String filePath = "";

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }

        cursor.close();

        return filePath;
    }

    public synchronized static int GetExifOrientation(String filepath) 	{
        int degree = 0;
        ExifInterface exif = null;

        try    {
            exif = new ExifInterface(filepath);
        } catch (IOException e)  {
            Log.e("StylePhoto", "cannot read exif");
            e.printStackTrace();
        }

        if (exif != null) {
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);

            if (orientation != -1) {
                // We only recognize a subset of orientation tag values.
                switch(orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                }
            }
        }

        return degree;
    }

    public synchronized static Bitmap GetRotatedBitmap(Bitmap bitmap, int degrees) 	{
        if ( degrees != 0 && bitmap != null )     {
            Matrix m = new Matrix();
            m.setRotate(degrees, (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2 );
            try {
                Bitmap b2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
                if (bitmap != b2) {
                    bitmap.recycle();
                    bitmap = b2;
                }
            } catch (OutOfMemoryError ex) {
                // We have no memory to rotate. Return the original bitmap.
            }
        }

        return bitmap;
    }

    @SuppressWarnings("deprecation")
    public synchronized static Bitmap getSafeDecodeBitmap(String strFilePath, int maxSize) {
        try {
            if (strFilePath == null)
                return null;
            // Max image size
            int IMAGE_MAX_SIZE = maxSize;

            File file = new File(strFilePath);
            if (file.exists() == false) {
                //DEBUG.SHOW_ERROR(TAG, "[ImageDownloader] SafeDecodeBitmapFile : File does not exist !!");
                return null;
            }

            BitmapFactory.Options bfo 	= new BitmapFactory.Options();
            bfo.inJustDecodeBounds 		= true;

            BitmapFactory.decodeFile(strFilePath, bfo);

            if (IMAGE_MAX_SIZE > 0)
                if(bfo.outHeight * bfo.outWidth >= IMAGE_MAX_SIZE * IMAGE_MAX_SIZE) {
                    bfo.inSampleSize = (int) Math.pow(2, (int) Math.round(Math.log(IMAGE_MAX_SIZE
                            / (double) Math.max(bfo.outHeight, bfo.outWidth)) / Math.log(0.5)));
                }
            bfo.inJustDecodeBounds = false;
            bfo.inPurgeable = true;
            bfo.inDither = true;

            final Bitmap bitmap = BitmapFactory.decodeFile(strFilePath, bfo);

            int degree = GetExifOrientation(strFilePath);

            return GetRotatedBitmap(bitmap, degree);
        }
        catch(OutOfMemoryError ex)
        {
            ex.printStackTrace();

            return null;
        }
    }

    public static void copyFile(String srcPath, String dstPath) throws IOException {
        File src = new File(srcPath);
        File dst = new File(dstPath);
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);

        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }
    public static String createUUID(){
        return UUIDCreatePart(4) + '-' +
                UUIDCreatePart(2) + '-' +
                UUIDCreatePart(2) + '-' +
                UUIDCreatePart(2) + '-' +
                UUIDCreatePart(6);
    }
    public static String UUIDCreatePart(int length){
        String uuidPart = "";
        int i;
        for(i = 0; i < length ; i++){
            String uuidChar = Integer.toHexString((int)(Math.random() *256.0));
            if(uuidChar.length() == 1)
                uuidChar = "0" + uuidChar;
            uuidPart += uuidChar;
        }
        return uuidPart;
    }
}