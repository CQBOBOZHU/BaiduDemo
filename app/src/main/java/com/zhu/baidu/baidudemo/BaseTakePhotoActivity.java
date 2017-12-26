package com.zhu.baidu.baidudemo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.compress.CompressImageUtil;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.zhu.baidu.R;

import java.io.File;

/**
 * Created by BoBoZhu on 2017/12/7.
 */

public abstract class BaseTakePhotoActivity extends AppCompatActivity implements TakePhoto.TakeResultListener, InvokeListener {

    private static final String TAG = TakePhotoActivity.class.getName();
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;


    /**
     * 获取TakePhoto实例
     *
     * @return
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void takeSuccess(TResult result) {
    }

    @Override
    public void takeFail(TResult result, String msg) {
    }

    @Override
    public void takeCancel() {
        Log.i(TAG, getResources().getString(R.string.msg_operation_canceled));
    }

    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (getTakePhoto() != null)
            getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 获取相册图片
     *
     * @param isCrop 是否裁剪
     */
    public void onAlbum(boolean isCrop) {
        if (isCrop) {
            getTakePhoto().onPickMultipleWithCrop(1, getCropBuilder().create());
        } else
            getTakePhoto().onPickMultiple(1);
    }

    public CropOptions.Builder getCropBuilder() {
        CropOptions.Builder builder = new CropOptions.Builder();
        builder.setWithOwnCrop(false);
        builder.setOutputX(720).setOutputY(720);
        return builder;
    }

    /**
     * 获取相册图片 固定比例
     *
     * @param isCrop 是否裁剪
     */
    public void onAlbum(boolean isCrop, boolean isfixed, float ratio) {
        if (isCrop) {
            getTakePhoto().onPickFromGalleryWithCrop(getOutPutUri(), getCropBuilder(isfixed, ratio).create());
        } else
            getTakePhoto().onPickFromGallery();
    }

    public CropOptions.Builder getCropBuilder(boolean isFix, float ratio) {
        CropOptions.Builder builder = new CropOptions.Builder();
        builder.setWithOwnCrop(false);
        if (isFix) {
            builder.setAspectX((int) (720 * ratio)).setAspectY(720);
        } else
            builder.setOutputX(720).setOutputY(720);
        return builder;
    }

    /**
     * 打开相机拍照  裁剪
     */
    public void onCamera(boolean isCrop) {
        Uri uri = getOutPutUri();
        if (uri == null) {
            return;
        }
        if (isCrop) {
            getTakePhoto().onPickFromCaptureWithCrop(uri, getCropBuilder().create());
        } else
            getTakePhoto().onPickFromCapture(uri);
    }

    /**
     * 打开相机拍照  裁剪 固定比例
     */
    public void onCamera(boolean isCrop, boolean isFixed,float ratoio) {
        Uri uri = getOutPutUri();
        if (uri == null) {
            return;
        }
        if (isCrop) {
            getTakePhoto().onPickFromCaptureWithCrop(uri, getCropBuilder(isFixed,ratoio).create());
        } else
            getTakePhoto().onPickFromCapture(uri);
    }

    /**
     * 获取拍照的照片uri
     *
     * @return
     */
    public Uri getOutPutUri() {
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        Uri imageUri = Uri.fromFile(file);
        return imageUri;
    }

    /**
     * 压缩图片
     *
     * @param url
     * @param listener
     */
    public void compress(String url, CompressImageUtil.CompressListener listener) {
        CompressImageUtil compressImageUtil = new CompressImageUtil(this, null);
        compressImageUtil.compress(url, listener);
    }

    /**
     * 压缩图片
     *
     * @param url
     * @param listener
     */
    public void compressBig(String url, CompressImageUtil.CompressListener listener) {
        CompressConfig compressConfig = new CompressConfig.Builder().
                setMaxSize(AppConfig.MAX_COMPRESS).
                enableReserveRaw(true).
                create();
        getTakePhoto().onEnableCompress(compressConfig, true);
        CompressImageUtil compressImageUtil = new CompressImageUtil(this, compressConfig);
        compressImageUtil.compress(url, listener);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

}
