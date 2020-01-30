package tech.yashtiwari.verkada;

import android.app.Application;
import android.util.Log;

import tech.yashtiwari.verkada.room.MotionZonesDatabase;

public class App extends Application {

    private static MotionZonesDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("YASH123", "APP");

        db = MotionZonesDatabase.getInstance(this);
    }

    public static MotionZonesDatabase getDatabaseInstance(){
        return db;
    }


}
