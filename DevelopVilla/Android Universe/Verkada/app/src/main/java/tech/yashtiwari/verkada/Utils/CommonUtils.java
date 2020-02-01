package tech.yashtiwari.verkada.Utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class CommonUtils {

    public static String getDateTimeInString(long dateTimeInSECONDS){
        Calendar calender = Calendar.getInstance();
        calender.setTimeInMillis(dateTimeInSECONDS * 1000);

        return calender.getTime().toString();
    }

}
