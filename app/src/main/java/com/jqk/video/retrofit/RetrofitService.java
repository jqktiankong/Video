package com.jqk.video.retrofit;

import com.jqk.video.bean.ActiviCode;
import com.jqk.video.bean.AppVersion;
import com.jqk.video.bean.Login;
import com.jqk.video.bean.Modify;
import com.jqk.video.bean.Register;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2018/5/20.
 */

public interface RetrofitService {
    @POST("login")
    Observable<Login> login(@Query("phone") String userName,
                            @Query("password") String passWord);

    @POST("reg")
    Observable<Register> register(@Query("phone") String userName,
                                  @Query("password") String passWord);


    @POST("modify")
    Observable<Modify> modify(@Query("phone") String userName,
                              @Query("password") String passWord);

    @POST("activiCode")
    Observable<ActiviCode> activiCode(@Query("phone") String userName,
                                      @Query("password") String passWord,
                                      @Query("code") String code);

    /**
     * 获取版本号
     *
     * @return
     */
    @GET("getVersion")
    Observable<AppVersion> getNowAppVersion();
}
