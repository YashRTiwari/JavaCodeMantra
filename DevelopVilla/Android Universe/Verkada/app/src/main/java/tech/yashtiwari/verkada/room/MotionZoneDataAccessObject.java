package tech.yashtiwari.verkada.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SimpleSQLiteQuery;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

@Dao
public interface MotionZoneDataAccessObject {

    @Query("Select * from motionzoneentity")
    public Observable<List<MotionZoneEntity>> getAllMotionZones();

    @Insert
    public void insertZone(MotionZoneEntity entity);

    @Query("Select * from motionzoneentity WHERE timeInSec >= :start AND timeInSec <= :end")
    public Observable<List<MotionZoneEntity>> getMotionsBetween(long start, long end);



    @Delete
    void delete(MotionZoneEntity entity);

    @Query("DELETE FROM motionzoneentity")
    void delete();

    @Update
    void update(MotionZoneEntity entity);
}