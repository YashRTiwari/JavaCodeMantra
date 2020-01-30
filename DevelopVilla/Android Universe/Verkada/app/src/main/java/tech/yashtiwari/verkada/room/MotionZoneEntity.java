package tech.yashtiwari.verkada.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MotionZoneEntity {

    @PrimaryKey
    long timeInSec = -1l;
    long nextEndTimeSec = -1l;
    long durationSexc = 0;

    public long getDurationSexc() {
        return durationSexc;
    }

    public void setDurationSexc(long durationSexc) {
        this.durationSexc = durationSexc;
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
