package tech.yashtiwari.verkada.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CommonUtility {

    private static SimpleDateFormat sdf = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy",
            Locale.ENGLISH);

    public static String getDateTimeInString ( Long dateTimeInSECONDS){
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

    public static String getDateInString ( Long dateTimeInSECONDS){
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

    public static String getTimeInString ( Long dateTimeInSECONDS){
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

    public static final ArrayList<List<Integer>> getListOfArray(ArrayList<Integer> list){

        ArrayList<List<Integer>> a = new ArrayList<>();
        for (int i : list){
            List<Integer> l = new ArrayList<>();
            l.add(i/10);
            l.add(i%10);
            a.add(l);
        }

        return a;

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
