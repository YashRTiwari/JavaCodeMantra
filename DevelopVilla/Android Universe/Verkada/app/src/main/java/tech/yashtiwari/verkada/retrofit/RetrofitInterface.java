package tech.yashtiwari.verkada.retrofit;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import tech.yashtiwari.verkada.retrofit.entity.MotionSearchBody;

public interface RetrofitInterface {

    @Headers("Content-Type: application/json")
    @POST("/ios/search")
    Observable<ResponseBody> motionSearch(@Body MotionSearchBody motionSearchRequest);

}
