package tech.yashtiwari.verkada.fragment.home;

import android.util.Log;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import tech.yashtiwari.verkada.App;
import tech.yashtiwari.verkada.Utils.CommonUtility;
import tech.yashtiwari.verkada.retrofit.RetrofitClient;
import tech.yashtiwari.verkada.retrofit.RetrofitInterface;
import tech.yashtiwari.verkada.retrofit.entity.DateAndDuration;
import tech.yashtiwari.verkada.retrofit.entity.MotionSearchBody;
import tech.yashtiwari.verkada.retrofit.entity.MotionSearchResponse;
import tech.yashtiwari.verkada.room.MotionZoneEntity;
import tech.yashtiwari.verkada.room.MotionZonesDatabase;

public class HomePageViewModel extends ViewModel {

    private static final String TAG = "HomePageViewModel";
    public MutableLiveData<List<DateAndDuration>> mldMotionAt = new MutableLiveData<>();
    private RetrofitInterface apiInterface = RetrofitClient.getInstance().create(RetrofitInterface.class);
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    public ObservableBoolean dataLoading = new ObservableBoolean(true);
    private MotionZonesDatabase db = App.getDatabaseInstance();
    public ObservableBoolean noDataFound = new ObservableBoolean(false);

    private void makeAPICall(final MotionSearchBody motionSearchBody, final String hashCode) {

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
                            addListToDatabase(motionSearchBody.getStartTimeSec(), motionSearchBody.getEndTimeSec(), entity.getMotionAt(), hashCode);
                            showListInRecyclerView(CommonUtility.getDateDurationList(entity.getMotionAt()));
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





    private void showListInRecyclerView(List<DateAndDuration> rvList){
        if (rvList != null) {
            if (rvList.size() > 0) {
                mldMotionAt.setValue(rvList);
                dataLoading.set(false);
                noDataFound.set(false);
            } else {
                mldMotionAt.setValue(null);
                dataLoading.set(false);
                noDataFound.set(true);
            }
        }
    }

    private void addListToDatabase(long sT, long eT, List<List<Long>> motionAt, String hashCode) {
        Log.d(TAG, "addListToDatabase: ");
        for (List<Long> l : motionAt) {
            long time = l.get(0);
            long duration = l.get(1);
            MotionZoneEntity entity = new MotionZoneEntity();
            entity.setsT(sT);
            entity.seteT(eT);
            entity.setTimeInSec(time);
            entity.setDurationSec(duration);
            entity.setHashCode(hashCode);
            db.getMotionZoneDAO()
                    .insertZone(entity);
        }
    }

    public void checkIfInCache(final MotionSearchBody body, final String hashCode) {

        dataLoading.set(true);
        db.getMotionZoneDAO()
                .getMotionsBetween(body.getStartTimeSec(), body.getEndTimeSec(),
                        hashCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<MotionZoneEntity>>() {
                    @Override
                    public void accept(List<MotionZoneEntity> motionZoneEntities) throws Exception {
                        if (motionZoneEntities.size() > 0) {
                            showListInRecyclerView(CommonUtility.getDateDurationList2(motionZoneEntities));
                            noDataFound.set(false);
                        } else {
                            Log.d(TAG, "Data not found in memory");
                            makeAPICall(body, hashCode);
                        }

                    }
                });

    }

}
