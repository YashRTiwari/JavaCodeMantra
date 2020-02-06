package tech.yashtiwari.verkada.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {MotionZoneEntity.class}, version = 6)
public abstract class MotionZonesDatabase extends RoomDatabase {

    public abstract MotionZoneDataAccessObject getMotionZoneDAO();

    private static MotionZonesDatabase db;

    public static MotionZonesDatabase getInstance(Context context) {
        if (null == db) {
            db = buildDatabaseInstance(context);
        }
        return db;
    }

    private static MotionZonesDatabase buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context,
                MotionZonesDatabase.class,
                MotionZonesDatabase.class.getName())
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    public void cleanUp(){
        db = null;
    }
}
