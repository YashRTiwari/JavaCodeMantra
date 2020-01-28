package tech.yashtiwari.verkada;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Dependencies in build.gradle
 * implementation "com.squareup.retrofit2:retrofit:2.4.0"
 * implementation "com.squareup.retrofit2:converter-gson:2.4.0"
 * implementation "com.google.code.gson:gson:2.8.6"
 */
public class VJavaEndpoint implements HttpLoggingInterceptor.Logger {

    private static final String BASE_URL = "http://ec2-54-187-236-58.us-west-2.compute.amazonaws.com:8021";

    private final Retrofit retrofit;

    public VJavaEndpoint() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(this);
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }

    public void searchMotion(int row, int column, long startDate, long end, @Nullable final CompletionCallback callback) {
        final Api api = retrofit.create(Api.class);

        final long startTimeSec = /*startDate /1000*/ 1500005480;
        final long endTimeSec = /*end /1000*/ 1580186124;

        final List<List<Integer>> list = new ArrayList<>();
        final List<Integer> motion = new ArrayList<>();
        motion.add(row);
        motion.add(column);
        list.add(motion);
        final MotionSearchBody body = new MotionSearchBody(
                list,
                startTimeSec,
                endTimeSec
        );
        api.motionSearch(body).enqueue(new Callback<MotionSearchResponse>() {

            @Override
            public void onResponse(@NonNull Call<MotionSearchResponse> call, @NonNull Response<MotionSearchResponse> response) {
                Log.d("YASH123", response.toString());
                MotionSearchResponse motionSearchResponse = response.body();

                if (motionSearchResponse != null) {
                    if (callback != null) callback.onComplete(motionSearchResponse.getMotionAt(), null);
                } else {
                    if (callback != null) callback.onComplete(new ArrayList<Motion>(), null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<MotionSearchResponse> call, @NonNull Throwable t) {
                if (callback != null) callback.onComplete(new ArrayList<Motion>(), t);
            }
        });
    }

    @Override
    public void log(@NotNull String s) {
        Log.d("YASH123", "LOG: ==>" + s);
    }

    public interface CompletionCallback {
        void onComplete(@NonNull List<Motion> motionList, @Nullable Throwable error);
    }

    public interface Api {

        @Headers("Content-Type: application/json")
        @POST("/ios/search")
        Call<MotionSearchResponse> motionSearch(@Body MotionSearchBody motionSearchRequest);
    }

    @SuppressWarnings("unused")
    public class MotionSearchBody {

        @NonNull
        @SerializedName("motionZones")
        @Expose
        private final List<List<Integer>> motionZones;

        @SerializedName("startTimeSec")
        @Expose
        private final long startTimeSec;

        @SerializedName("endTimeSec")
        @Expose
        private final long endTimeSec;

        MotionSearchBody(@NonNull List<List<Integer>> motionZones, long startTimeSec, long endTimeSec) {
            this.motionZones = motionZones;
            this.startTimeSec = startTimeSec;
            this.endTimeSec = endTimeSec;
        }


    }

    @SuppressWarnings("unused")
    public class MotionSearchResponse {

        @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
        @SerializedName("motionAt")
        @Expose
        private List<List<Long>> motionAt;

        @SerializedName("nextEndTimeSec")
        @Expose
        private int nextEndTimeSec;

        public int getNextEndTimeSec() {
            return nextEndTimeSec;
        }

        List<Motion> getMotionAt() {
            List<Motion> list = new ArrayList<>();
            for (List<Long> motion : motionAt) {
                list.add(new Motion(new Date(motion.get(0) * 1000), motion.get(1)));
            }
            return list;
        }


    }

    public class Motion {
        private Date date;
        private long durationSeconds;

        Motion(Date date, long durationSeconds) {
            this.date = date;
            this.durationSeconds = durationSeconds;
        }

        Date getDate() {
            return date;
        }

        long getDurationSeconds() {
            return durationSeconds;
        }

        @Override
        public String toString() {
            return "Motion(date=" + getDate() + ", durationSeconds=" + getDurationSeconds() + ")";
        }
    }
}
