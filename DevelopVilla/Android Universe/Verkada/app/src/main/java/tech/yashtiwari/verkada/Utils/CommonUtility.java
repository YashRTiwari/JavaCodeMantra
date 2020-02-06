package tech.yashtiwari.verkada.Utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import tech.yashtiwari.verkada.R;
import tech.yashtiwari.verkada.retrofit.entity.DateAndDuration;
import tech.yashtiwari.verkada.room.MotionZoneEntity;

import static tech.yashtiwari.verkada.Utils.Constant.SDF;

public class CommonUtility {


//    public static String getDateTimeInString(Long dateTimeInSECONDS) {
//        Calendar calender = Calendar.getInstance();
//        calender.setTimeInMillis(dateTimeInSECONDS);
//        Date date = calender.getTime();
//        SimpleDateFormat print = new SimpleDateFormat("MMM dd yyyy, HH:mm ");
//        Date parsedDate = null;
//
//
//        try {
//            parsedDate = SDF.parse(date.toString());
//            return print.format(parsedDate);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    /**
     * Converts long valued time in string format.
     * Mainly for getting Month, day and year
     *
     * @param dateTimeInSECONDS
     * @return
     */
    public static String getDateInString(Long dateTimeInSECONDS) {
        Calendar calender = Calendar.getInstance();
        calender.setTimeInMillis(dateTimeInSECONDS);
        Date date = calender.getTime();
        SimpleDateFormat print = new SimpleDateFormat("MMM d, yyyy");
        Date parsedDate = null;
        try {
            parsedDate = SDF.parse(date.toString());
            return print.format(parsedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Converts long valued time in string format.
     * Mainly for getting hour and minute
     *
     * @param dateTimeInSECONDS
     * @return
     */
    public static String getTimeInString(Long dateTimeInSECONDS) {
        Calendar calender = Calendar.getInstance();
        calender.setTimeInMillis(dateTimeInSECONDS);
        Date date = calender.getTime();
        SimpleDateFormat print = new SimpleDateFormat("HH : mm");
        Date parsedDate = null;
        try {
            parsedDate = SDF.parse(date.toString());
            return print.format(parsedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Helper function to convert recyclerview position value
     * to cordinate format [x, y]
     *
     * @param list
     * @return
     */
    public static final ArrayList<List<Integer>> getListOfArray(ArrayList<Integer> list) {
        ArrayList<List<Integer>> a = new ArrayList<>();
        Iterator<Integer> liU = list.iterator();
        while (liU.hasNext()) {
            List<Integer> l = new ArrayList<>();
            int i = liU.next();
            l.add(i % 10);
            l.add(i / 10);
            a.add(l);
        }
        return a;
    }

    /**
     * Generates a string value unique for all zone selection
     * used in searching database for select zones.
     *
     * @param list
     * @return
     */
    public static String generateUniqueHashCodeForArrayList(ArrayList<Integer> list) {

        if (list == null)
            if (list.size() == 0)
                return null;
        String hashCode = "";
        Collections.sort(list);
        for (int z : list) {
            hashCode += String.valueOf(z);
        }
        return hashCode;
    }


    /**
     * converts cordinate based data to DateAndDuration class
     * format, so as to be used in recyclerview
     *
     * @param lists
     * @return
     */
    public static List<DateAndDuration> getDateDurationList(List<List<Long>> lists) {

        List<DateAndDuration> result = new ArrayList<>();
        int maxDuration = -1;

        ListIterator<List<Long>> liResponse = lists.listIterator();

        while (liResponse.hasNext()) {
            List<Long> current = liResponse.next();
            long time = current.get(0) * 1000;
            int duration = (int) (long) current.get(1);
            if (maxDuration < duration) {
                maxDuration = duration;
            }
            DateAndDuration entity = new DateAndDuration(time, duration);
            result.add(entity);
        }

        ListIterator<DateAndDuration> listIterator = result.listIterator();
        while (listIterator.hasNext()) {
            listIterator.next().updateDurationToPercent(maxDuration);
        }
        return result;
    }


    /**
     * converts database response to DateAndDuration format
     *
     * @param lists
     * @return
     */
    public static List<DateAndDuration> getDateDurationList2(List<MotionZoneEntity> lists) {
        int maxDuration = -1;
        List<DateAndDuration> result = new ArrayList<>();
        ListIterator<MotionZoneEntity> liMotionZoneEntity = lists.listIterator();

        while (liMotionZoneEntity.hasNext()) {
            MotionZoneEntity current = liMotionZoneEntity.next();
            long time = current.getTimeInSec() * 1000;
            int duration = (int) current.getDurationSec();
            if (maxDuration < duration) {
                maxDuration = duration;
            }
            DateAndDuration dateAndDuration = new DateAndDuration(time, duration);
            result.add(dateAndDuration);
        }

        ListIterator<DateAndDuration> listIterator = result.listIterator();
        while (listIterator.hasNext()) {
            listIterator.next().updateDurationToPercent(maxDuration);
        }

        return result;
    }

    /**
     * Maps the percentile value of duration
     * to four of the images,
     * Used to provide a different ui experience
     *
     * @param context
     * @param value
     * @return
     */
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
        } else {
            if (value <= 25) {
                return context.getResources().getDrawable(R.drawable.rect_1);
            } else if (value <= 50)
                return context.getResources().getDrawable(R.drawable.rect_2);
            else if (value <= 75)
                return context.getResources().getDrawable(R.drawable.rect_3);
            else
                return context.getResources().getDrawable(R.drawable.rect_4);
        }
    }


    /*
     * Functions implemented to expand and collapse the view
     * out of scope for this project, as of now.
     * */
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
