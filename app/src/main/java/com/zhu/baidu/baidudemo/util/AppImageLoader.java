package com.zhu.baidu.baidudemo.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.zhu.baidu.R;

/**
 * Created by Administrator on 2017/5/17.
 */

public class AppImageLoader {



    public static int getDefaultPic(int type) {
//        switch (type) {
//            case 0:// 头像加载中
//                return R.drawable.shape_place_bg;
//        }
        return R.mipmap.ic_launcher;
    }


    /**
     * 宽度屏幕宽度。高度自适应
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void diaplayFitXy(final Context context, Object url, final ImageView imageView) {
        Glide.with(context).load(url).asBitmap()
                .placeholder(getDefaultPic(1)).error(getDefaultPic(2))
                .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                setImageBitmap(resource);
            }

            @Override
            public void onLoadStarted(Drawable placeholder) {
                super.onLoadStarted(placeholder);
                Bitmap bitmap = drawableToBitmap(placeholder);
                setImageBitmap(bitmap);
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
                Bitmap bitmap = drawableToBitmap(errorDrawable);
                setImageBitmap(bitmap);
            }

            private void setImageBitmap(Bitmap bitmap) {
                int imageWidth = bitmap.getWidth();
                int imageHeight = bitmap.getHeight();
                int height = ScreenUtils.getScreenWidth(context) * imageHeight / imageWidth;
                ViewGroup.LayoutParams para = imageView.getLayoutParams();
                para.height = height;
                para.width = ScreenUtils.getScreenWidth(context);
                imageView.setImageBitmap(bitmap);
            }
        });
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;

    }

}
