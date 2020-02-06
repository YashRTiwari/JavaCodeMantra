package tech.yashtiwari.verkada.Utils;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class Constant {

    /*
    * Class of constant used in various instances,
    * provides a level consistency to code.
    * Note : These can be included in string.xml, just  a matter of preference.
    * */
    public static final String TAG_YASH = "TAG_YASH";
    public static final String START_DATE = "Start Date";
    public static final String END_DATE = "End Date";
    public static final String LAST_ZONES = "LAST_ZONES";


    public static SimpleDateFormat SDF = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy",
            Locale.ENGLISH);

    /**
     * Enums to catedorize date selected
     */
    public enum Date {
        START, END
    }

    public enum Time {
        START, END
    }


}
