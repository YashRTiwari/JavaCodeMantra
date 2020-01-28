package tech.yashtiwari.verkada;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import tech.yashtiwari.verkada.databinding.ActivityMainBinding;
import tech.yashtiwari.verkada.fragment.BottomSheetDateDailog;
import tech.yashtiwari.verkada.retrofit.entity.MotionSearchResponse;
import tech.yashtiwari.verkada.retrofit.RetrofitClient;
import tech.yashtiwari.verkada.retrofit.RetrofitInterface;
import tech.yashtiwari.verkada.retrofit.entity.MotionSearchBody;

public class MainActivity extends AppCompatActivity {

    RetrofitInterface apiInterface;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        Log.d(TAG, "" + width);
        Log.d(TAG, "" + height);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        long startMilli = System.currentTimeMillis() - 100*24*60*60*1000;
        long endMilli = System.currentTimeMillis() ;
        Log.d(TAG, "Start : "+startMilli+" End :"+endMilli);

        apiInterface = RetrofitClient.getInstance().create(RetrofitInterface.class);

        apiInterface.motionSearch(getMotionSearchBody(startMilli/1000, endMilli/1000, getMotionZones()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {

                        String response = null;
                        try {
                            response = responseBody.string();
                            MotionSearchResponse entity = new Gson().fromJson(response, new TypeToken<MotionSearchResponse>() {}.getType());

                            if(entity != null){
                                updateUI(entity);
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });


        BottomSheetDateDailog dialog = BottomSheetDateDailog.getInstance();

        dialog.show(getSupportFragmentManager(), BottomSheetDateDailog.TAG);
        dialog.setCancelable(false);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void updateUI(MotionSearchResponse motionSearchResponse) {

        List<List<Long>> motionAt = motionSearchResponse.getMotionAt();
        if(motionAt!= null){
            if(motionAt.size() > 0){

                for (List<Long> mAt : motionAt){
                    binding.tv.append((new Date(mAt.get(0))).toString()+"\n");
                }

            } else {
                Log.d(TAG, "motionAt size is zero");
            }
        } else {
            Log.d(TAG, "motionAt is null");
        }

    }

    private List<List<Integer>> getMotionZones(){
        List<List<Integer>> zones = new ArrayList<>();
        ArrayList<Integer> a = new ArrayList<>();
        a.add(5);
        a.add(5);
        zones.add(a);
        return zones;
    }

    private MotionSearchBody getMotionSearchBody(long startTimeSec, long endTimeSec, List<List<Integer>> motionZones){
        Log.d(TAG, startTimeSec + ", "+endTimeSec);
        MotionSearchBody motionSearchBody = new MotionSearchBody();
        motionSearchBody.setEndTimeSec(1580186124l);
        motionSearchBody.setStartTimeSec(1500005480l);
        motionSearchBody.setMotionZones(motionZones);

        return motionSearchBody;
    }

}

