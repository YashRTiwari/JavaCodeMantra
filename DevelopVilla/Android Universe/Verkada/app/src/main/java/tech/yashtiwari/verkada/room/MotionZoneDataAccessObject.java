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

    /**
     *
     * @return - returns whole data stored in the table
     */
    @Query("Select * from motionzoneentity")
    Observable<List<MotionZoneEntity>> getAllMotionZones();

    /**
     *
     * @param entity - Data to be stored
     */
    @Insert
    void insertZone(MotionZoneEntity entity);


    /**
     *
     * @param start - start time in seconds
     * @param end - end time in seconds
     * @param hashCode - unique value of zones selected
     * @return
     */
    @Query("Select * from motionzoneentity WHERE timeInSec >= :start AND timeInSec <= :end AND hashCode = :hashCode")
    Observable<List<MotionZoneEntity>> getMotionsBetween(long start, long end, String hashCode);

    @Delete
    void delete(MotionZoneEntity entity);

    @Query("DELETE FROM motionzoneentity")
    void delete();

    @Update
    void update(MotionZoneEntity entity);
}