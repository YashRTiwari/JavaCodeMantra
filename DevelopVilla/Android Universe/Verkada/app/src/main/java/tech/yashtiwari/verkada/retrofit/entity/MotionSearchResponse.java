package tech.yashtiwari.verkada.retrofit.entity;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MotionSearchResponse {

    @SerializedName("motionAt")
    @Expose
    private List<List<Long>> motionAt = null;
    @SerializedName("nextEndTimeSec")
    @Expose
    private Integer nextEndTimeSec;

    public List<List<Long>> getMotionAt() {
        return motionAt;
    }

    public void setMotionAt(List<List<Long>> motionAt) {
        this.motionAt = motionAt;
    }

    public Integer getNextEndTimeSec() {
        return nextEndTimeSec;
    }

    public void setNextEndTimeSec(Integer nextEndTimeSec) {
        this.nextEndTimeSec = nextEndTimeSec;
    }

}
