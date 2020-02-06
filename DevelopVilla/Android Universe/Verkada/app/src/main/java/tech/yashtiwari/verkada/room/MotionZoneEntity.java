package tech.yashtiwari.verkada.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MotionZoneEntity {

    @PrimaryKey(autoGenerate = true)
    long id;
    long timeInSec = -1l;
    long nextEndTimeSec = -1l;
    long durationSec = 0;
    String hashCode = "";

    public String getHashCode() {
        return hashCode;
    }

    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }

    public long getDurationSec() {
        return durationSec;
    }

    public void setDurationSec(long durationSec) {
        this.durationSec = durationSec;
    }

    public long getTimeInSec() {
        return timeInSec;
    }

    public void setTimeInSec(long timeInSec) {
        this.timeInSec = timeInSec;
    }

    public long getNextEndTimeSec() {
        return nextEndTimeSec;
    }

    public void setNextEndTimeSec(long nextEndTimeSec) {
        this.nextEndTimeSec = nextEndTimeSec;
    }
}
