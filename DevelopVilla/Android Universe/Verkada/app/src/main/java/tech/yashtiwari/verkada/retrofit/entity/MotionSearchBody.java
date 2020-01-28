
package tech.yashtiwari.verkada.retrofit.entity;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MotionSearchBody {

    @SerializedName("motionZones")
    @Expose
    private List<List<Integer>> motionZones = null;
    @SerializedName("startTimeSec")
    @Expose
    private Long startTimeSec;
    @SerializedName("endTimeSec")
    @Expose
    private Long endTimeSec;

    public List<List<Integer>> getMotionZones() {
        return motionZones;
    }

    public void setMotionZones(List<List<Integer>> motionZones) {
        this.motionZones = motionZones;
    }

    public Long getStartTimeSec() {
        return startTimeSec;
    }

    public void setStartTimeSec(Long startTimeSec) {
        this.startTimeSec = startTimeSec;
    }

    public Long getEndTimeSec() {
        return endTimeSec;
    }

    public void setEndTimeSec(Long endTimeSec) {
        this.endTimeSec = endTimeSec;
    }

}
