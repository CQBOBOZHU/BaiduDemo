package com.zhu.baidu.baidudemo;

import com.zhu.baidu.baidudemo.bean.BaiduBean;
import com.zhu.baidu.baidudemo.bean.ImageRequestBean;
import com.zhu.baidu.baidudemo.bean.ObjectBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by BoBoZhu on 2017/12/25.
 */

public interface ApiService {
    @FormUrlEncoded
    @POST(Api.get_token)
    Observable<BaiduBean> getToken(@Field("grant_type") String grant_type, @Field("client_id") String client_id, @Field("client_secret") String client_secret);

    @FormUrlEncoded
    @POST(Api.animal)
    Observable<ImageRequestBean> animal(@Field("image") String image, @Field("top_num") int top_num, @Query("access_token") String token);

    /**
     * @param image
     * @param top_num 返回结果top n，默认5。
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(Api.car)
    Observable<ImageRequestBean> car(@Field("image") String image, @Field("top_num") int top_num, @Query("access_token") String token);

    /**
     * @param image
     * @param top_num 返回结果top n,默认5.
     * @param filter_threshold 默认0.95，可以通过该参数调节识别效果，降低非菜识别率.
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(Api.dish)
    Observable<ImageRequestBean> dish(@Field("image") String image, @Field("top_num") int top_num, @Field("filter_threshold") float filter_threshold, @Query("access_token") String token);

    /**
     * 如果检测主体是人，主体区域是否带上人脸部分，
     * 0-不带人脸区域，其他-带人脸区域，裁剪类需求推荐带人脸，
     * 检索/识别类需求推荐不带人脸。默认取1，带人脸。
     *
     * @param image
     * @param with_face
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(Api.object_detect)
    Observable<ObjectBean> object_detect(@Field("image") String image, @Field("with_face") int with_face, @Query("access_token") String token);

    @FormUrlEncoded
    @POST(Api.plant)
    Observable<ImageRequestBean> plant(@Field("image") String image, @Query("access_token") String token);

    /**
     * @param image
     * @param custom_lib 是否只检索用户子库，true则只检索用户子库，false(默认)为检索底库+用户子库
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST(Api.logo)
    Observable<ImageRequestBean> logo(@Field("image") String image, @Field("custom_lib") boolean custom_lib, @Query("access_token") String token);

}
