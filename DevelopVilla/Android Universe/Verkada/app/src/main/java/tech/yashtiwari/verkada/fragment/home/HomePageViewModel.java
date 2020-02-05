package tech.yashtiwari.verkada.fragment.home;

import android.util.Log;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.reactivestreams.Subscription;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.FlowableSubscriber;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import tech.yashtiwari.verkada.App;
import tech.yashtiwari.verkada.retrofit.RetrofitClient;
import tech.yashtiwari.verkada.retrofit.RetrofitInterface;
import tech.yashtiwari.verkada.retrofit.entity.MotionSearchBody;
import tech.yashtiwari.verkada.retrofit.entity.MotionSearchResponse;
import tech.yashtiwari.verkada.room.MotionZoneEntity;
import tech.yashtiwari.verkada.room.MotionZonesDatabase;

public class HomePageViewModel extends ViewModel {

    private static final String TAG = "HomePageViewModel";
    public MutableLiveData<List<List<Long>>> mldMotionAt = new MutableLiveData<>();
    private RetrofitInterface apiInterface = RetrofitClient.getInstance().create(RetrofitInterface.class);
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    public ObservableBoolean isDataFound = new ObservableBoolean(false);
    public MutableLiveData<Long> mldNextEndTime = new MutableLiveData<>();
    private MotionZonesDatabase db = App.getDatabaseInstance();

    public void makeAPICall(MotionSearchBody motionSearchBody) {

        apiInterface.motionSearch(motionSearchBody)
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
                            Log.d(TAG, "onNext: "+response);
                            MotionSearchResponse entity = new Gson().fromJson(response, new TypeToken<MotionSearchResponse>() {
                            }.getType());

                            if (entity != null) {
                                if (entity.getMotionAt().size() > 0) {
                                    mldMotionAt.setValue(entity.getMotionAt());
                                    mldNextEndTime.setValue(entity.getNextEndTimeSec());
                                    isDataFound.set(true);
                                    addListToDatabase(entity.getMotionAt(), entity.getNextEndTimeSec());

                                } else {
                                    mldMotionAt.setValue(null);
                                    isDataFound.set(false);
                                }
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
    }

    private void addListToDatabase(List<List<Long>> motionAt, long nextEndTimeSec) {
        for (List<Long> l : motionAt) {
            long time = l.get(0);
            long duration = l.get(1);
            MotionZoneEntity entity = new MotionZoneEntity();
            entity.setTimeInSec(time);
            entity.setDurationSexc(duration);
            entity.setNextEndTimeSec(nextEndTimeSec);
            db.getMotionZoneDAO()
                    .insertZone(entity);
        }
    }

    public void checkIfInCache(long start, long end, final MotionSearchBody body) {

        db.getMotionZoneDAO()
                .getMotionsBetween(start, end)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<MotionZoneEntity>>() {
                    @Override
                    public void accept(List<MotionZoneEntity> motionZoneEntities) throws Exception {
//                        if (motionZoneEntities.size() > 0) {
//                            Log.d(TAG, "Data found in memory");
//                            List<List<Long>> data = new ArrayList<>();
//                            for (MotionZoneEntity e : motionZoneEntities) {
//                                List<Long> l = new ArrayList<>();
//                                l.add(e.getTimeInSec());
//                                l.add(e.getDurationSexc());
//                                data.add(l);
//                            }
//
//                            Log.d(TAG,"FOUND Start==>"+data.get(0).get(0));
//                            Log.d(TAG,"FOUND End  ==>"+data.get(data.size() -1).get(0));
//                        } else {
//                            Log.d(TAG, "Data not found in memory");
//
//                        }

                        //makeAPICall(body);
                    }
                });

    }

}
