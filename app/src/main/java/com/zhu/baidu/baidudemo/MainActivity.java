package com.zhu.baidu.baidudemo;

import android.Manifest;
import android.accounts.NetworkErrorException;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jph.takephoto.model.TResult;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhu.baidu.R;
import com.zhu.baidu.baidudemo.bean.BaiduBean;
import com.zhu.baidu.baidudemo.bean.ImageRequestBean;
import com.zhu.baidu.baidudemo.util.AppImageLoader;
import com.zhu.baidu.baidudemo.util.Base64Util;
import com.zhu.baidu.baidudemo.util.DoubleUtils;
import com.zhu.baidu.baidudemo.util.FileUtil;
import com.zhu.baidu.baidudemo.util.LogUtil;
import com.zhu.baidu.baidudemo.util.RetroFitUtil;
import com.zhu.baidu.baidudemo.util.SharePreferenceUtils;
import com.zhu.baidu.baidudemo.util.ToastUtil;

import java.io.FileNotFoundException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends BaseTakePhotoActivity {

    @BindView(R.id.button)
    Button button;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.button3)
    Button button3;

    ApiService apiService;
    @BindView(R.id.imageView)
    ImageView imageView;

    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    //    String url;
    String url = "/sdcard/DCIM/Camera/IMG_20150102_131855.jpg";

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Retrofit service = RetroFitUtil.createGsonService(Api.BaseUrl);
        apiService = service.create(ApiService.class);
        radioGroup.check(R.id.radioButton1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(getAdapter());
    }


    public Observable getObservable(String image, String token) {
        Observable observable;
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.radioButton1:
                observable = apiService.logo(image, false, token);
                break;
            case R.id.radioButton2:
                observable = apiService.plant(image, token);
                break;
            case R.id.radioButton3:
                observable = apiService.animal(image, 10, token);
                break;
            case R.id.radioButton4:
                observable = apiService.dish(image, 10, 0.95f, token);
                break;
            case R.id.radioButton5:
                observable = apiService.car(image, 10, token);
                break;
            case R.id.radioButton6:
                observable = apiService.object_detect(image, 1, token);
                break;
            default:
                observable = apiService.logo(image, false, token);
                break;
        }
        return observable;

    }


    public void getToken() {
        apiService.getToken("client_credentials", AppConfig.BAIDU_APP_KEY, AppConfig.BAIDU_SECRET_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaiduBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaiduBean baiduBean) {
                        LogUtil.v(baiduBean.toString());
                        SharePreferenceUtils.setObjectToShare(baiduBean, "baiduBean");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.v(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @OnClick({R.id.button, R.id.button2, R.id.button3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button:
                getToken();
                break;
            case R.id.button2:
                doImage();
                break;
            case R.id.button3:
                changeHeadIcon();
                break;
        }
    }

    /**
     * 相机 相册调用状态的回调
     *
     * @param result
     */
    @Override
    public void takeSuccess(TResult result) {
        url = result.getImage().getOriginalPath();
        AppImageLoader.diaplayFitXy(this, url,imageView);
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
        if (msg.contains("取消裁剪")) {
            ToastUtil.show("取消裁剪");
        } else
            ToastUtil.show("图片选择错误");
    }

    private void doImage() {
        RxPermissions permissions = new RxPermissions(this);
        permissions.request(Manifest.permission.READ_EXTERNAL_STORAGE)
                .flatMap(new Function<Boolean, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Boolean aBoolean) throws Exception {
                        LogUtil.v(Thread.currentThread().getName());
                        if (aBoolean) {
                            return new ObservableSource<String>() {
                                @Override
                                public void subscribe(Observer<? super String> observer) {
                                    BaiduBean baiduBean = (BaiduBean) SharePreferenceUtils.getObjectFromShare("baiduBean");
                                    if (baiduBean != null)
                                        observer.onNext(baiduBean.getAccess_token());
                                    else {
                                        observer.onError(new Throwable("请先获取token"));
                                    }
                                }
                            };
                        }
                        return Observable.error(new Throwable("请先开启SD卡读取权限"));
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .map(new Function<String, String[]>() {

                    @Override
                    public String[] apply(String s) throws Exception {
                        LogUtil.v(Thread.currentThread().getName());
                        byte[] bytes = FileUtil.readFileByBytes(url);
                        String encode = Base64Util.encode(bytes);
                        String[] strings = new String[2];
                        strings[0] = s;
                        strings[1] = encode;
                        return strings;
                    }
                })
                .flatMap(new Function<String[], ObservableSource<ImageRequestBean>>() {
                    @Override
                    public ObservableSource<ImageRequestBean> apply(String[] strings) throws Exception {
                        LogUtil.v(Thread.currentThread().getName());
                        return getObservable(strings[1], strings[0]);
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ImageRequestBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ImageRequestBean imageRequestBean) {
                        LogUtil.v(Thread.currentThread().getName());
                        LogUtil.v(imageRequestBean.toString());
                        String error_msg = imageRequestBean.getError_msg();
                        if (!TextUtils.isEmpty(error_msg)) {
                            ToastUtil.show(error_msg);
                        } else {
                            baseQuickAdapter.setNewData(imageRequestBean.getResult());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof FileNotFoundException) {
                            ToastUtil.show("图片地址不正确");
                        } else if (e instanceof UnknownHostException || e instanceof UnknownServiceException) {
                            ToastUtil.show("网络错误");
                        } else if (e instanceof NetworkErrorException) {
                            ToastUtil.show("网络错误");
                        } else {
                            LogUtil.v(Thread.currentThread().getName());
                            LogUtil.v(e.getMessage());
                            ToastUtil.show(e.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    CameraDialog cameraDialog;

    /**
     * 设置用户头像
     */
    private void changeHeadIcon() {
        if (cameraDialog != null) {
            cameraDialog.show();
        } else {
            cameraDialog = new CameraDialog(this, R.layout.item_choice_photo);
            cameraDialog.setOnClickListener(photoListener, R.id.photo_album, R.id.photo_take, R.id.photo_cancel);
            cameraDialog.show();
        }
    }

    View.OnClickListener photoListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            cameraDialog.disMiss();
            switch (v.getId()) {
                case R.id.photo_album:
                    onAlbum(true);
                    break;
                case R.id.photo_take:
                    onCamera(true);
                    break;
                case R.id.photo_cancel:
                    break;
            }
        }
    };

    BaseQuickAdapter baseQuickAdapter;
    public RecyclerView.Adapter getAdapter() {
        if (baseQuickAdapter==null)
         baseQuickAdapter = new BaseQuickAdapter<ImageRequestBean.ResultBean, BaseViewHolder>(R.layout.item_tv, null) {

            @Override
            protected void convert(BaseViewHolder helper, ImageRequestBean.ResultBean item) {
                helper.setText(R.id.textView, item.getName());
                double score = item.getProbability() == 0 ? item.getScore() : item.getProbability();
                Double multiply = DoubleUtils.multiply(score, (double) 100);
                String t = DoubleUtils.getT(multiply);
                helper.setText(R.id.textView2, String.format("相似度%s%%", t));
            }
        };
        return baseQuickAdapter;
    }

}
