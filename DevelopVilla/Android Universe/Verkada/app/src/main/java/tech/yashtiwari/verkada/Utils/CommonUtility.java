package tech.yashtiwari.verkada.Utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import tech.yashtiwari.verkada.R;
import tech.yashtiwari.verkada.retrofit.entity.DateAndDuration;

import static tech.yashtiwari.verkada.Utils.Constant.TAG_YASH;

public class CommonUtility {

    private static SimpleDateFormat sdf = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy",
            Locale.ENGLISH);

    public static String getDateTimeInString(Long dateTimeInSECONDS) {
        Calendar calender = Calendar.getInstance();
        calender.setTimeInMillis(dateTimeInSECONDS);
        Date date = calender.getTime();
        SimpleDateFormat print = new SimpleDateFormat("MMM dd yyyy, HH:mm ");
        Date parsedDate = null;
        try {
            parsedDate = sdf.parse(date.toString());
            return print.format(parsedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getDateInString(Long dateTimeInSECONDS) {
        Calendar calender = Calendar.getInstance();
        calender.setTimeInMillis(dateTimeInSECONDS);
        Date date = calender.getTime();
        SimpleDateFormat print = new SimpleDateFormat("MMM d, yyyy");
        Date parsedDate = null;
        try {
            parsedDate = sdf.parse(date.toString());
            return print.format(parsedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getTimeInString(Long dateTimeInSECONDS) {
        Calendar calender = Calendar.getInstance();
        calender.setTimeInMillis(dateTimeInSECONDS);
        Date date = calender.getTime();
        SimpleDateFormat print = new SimpleDateFormat("HH : mm");
        Date parsedDate = null;
        try {
            parsedDate = sdf.parse(date.toString());
            return print.format(parsedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static final ArrayList<List<Integer>> getListOfArray(ArrayList<Integer> list) {

        ArrayList<List<Integer>> a = new ArrayList<>();

        HashSet<Integer> u = new HashSet<>();
        for (int y : list) {
            u.add(y);
        }

        for (int i : u) {

            List<Integer> l = new ArrayList<>();
            l.add(i % 10);
            l.add(i / 10);
            a.add(l);
        }

        return a;

    }

    public static List<DateAndDuration> getDateDurationList(List<List<Long>> lists) {

        List<DateAndDuration> result = new ArrayList<>();

        int maxDuration = -1;

        for (List<Long> l : lists) {
            DateAndDuration entity = new DateAndDuration();
            long time = l.get(0) * 1000;
            Calendar calender = Calendar.getInstance();
            calender.setTimeInMillis(time);
            Date date = calender.getTime();

            SimpleDateFormat d = new SimpleDateFormat("dd");
            SimpleDateFormat m = new SimpleDateFormat("MMM");
            SimpleDateFormat y = new SimpleDateFormat("yyyy");
            SimpleDateFormat h = new SimpleDateFormat("hh");
            SimpleDateFormat mm = new SimpleDateFormat("mm");

            if (maxDuration < (int) (long) l.get(1)) {
                maxDuration = (int) (long) l.get(1);
            }

            Date parsedDate = null;
            try {
                parsedDate = sdf.parse(date.toString());

                entity.setDay(Integer.parseInt(d.format(parsedDate)));
                entity.setMonth(m.format(parsedDate));
                entity.setYear(Integer.parseInt(y.format(parsedDate)));
                entity.setHour(Integer.parseInt(h.format(parsedDate)));
                entity.setMinute(Integer.parseInt(mm.format(parsedDate)));
                entity.setoDuration((int) (long) l.get(1));

                result.add(entity);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        Log.d(TAG_YASH, "maxDuration: " + maxDuration);
        for (int i = 0; i < result.size(); i++) {
            int inD = result.get(i).getoDuration();
            int fD = (int) (((float) inD / (float) maxDuration) * 100);
            Log.d(TAG_YASH, "finalDuration: " + fD);
            result.get(i).setDuration(fD);
        }

        return result;
    }

    public static Drawable getSignalDrawable(Context context, int value) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (value <= 25) {
                return context.getDrawable(R.drawable.rect_1);
            } else if (value <= 50)
                return context.getDrawable(R.drawable.rect_2);
            else if (value <= 75)
                return context.getDrawable(R.drawable.rect_3);
            else
                return context.getDrawable(R.drawable.rect_4);
        } else
            return null;
    }


//    public static void expand(final View v) {
//        int matchParentMeasureSpec = View.MeasureSpec.makeMeasureSpec(((View)v.getParent()).getWidth(), View.MeasureSpec.EXACTLY);
//        int wrapContentMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//        v.measure(matchParentMeasureSpec, wrapContentMeasureSpec);
//        final int targetHeight = v.getMeasuredHeight();
//
//        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
//        v.getLayoutParams().height = 1;
//        v.setVisibility(View.VISIBLE);
//        Animation a = new Animation() {
//            @Override
//            protected void applyTransformation(float interpolatedTime, Transformation t) {
//                v.getLayoutParams().height = interpolatedTime == 1f ? ViewGroup.LayoutParams.WRAP_CONTENT : (int) (targetHeight * interpolatedTime);
//                v.requestLayout();
//            }
//
//
//            @Override
//            public boolean willChangeBounds(){
//                return true;
//            }
//        };
//
//        // 1dp/ms
//        int x  = (int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density);
//        a.setDuration((long) x);
//        v.startAnimation(a);
//    }
//
//    @JvmStatic
//    public static void collapse(final View  v) {
//        final int initialHeight = v.getMeasuredHeight();
//
//        Animation a = new Animation() {
//            @Override
//            protected void applyTransformation(float interpolatedTime, Transformation t) {
//                if (interpolatedTime == 1f) {
//                    v.setVisibility(View.GONE);
//                } else {
//                    v.getLayoutParams().height = (int) (initialHeight - (initialHeight * interpolatedTime));
//                    v.requestLayout();
//                }
//            }
//
//
//            @Override
//            public boolean willChangeBounds(){
//                return true;
//            }
//        };
//
//        int x  = (int) ((initialHeight / v.getContext().getResources().getDisplayMetrics().density));
//        a.setDuration((long) x);
//        v.startAnimation(a);
//    }
}
