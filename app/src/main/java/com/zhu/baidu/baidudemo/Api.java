package com.zhu.baidu.baidudemo;

/**
 * Created by BoBoZhu on 2017/12/25.
 */

public class Api {

    public static final String  BaseUrl="https://aip.baidubce.com/";

    public static final String get_token="oauth/2.0/token";

    /**
     * 图像主体检测
     */
    public static final String  object_detect="rest/2.0/image-classify/v1/object_detect";
    /**
     * 菜品识别
     */
    public static final String  dish="rest/2.0/image-classify/v2/dish";
    /**
     * 车型识别
     */
    public static final String  car="rest/2.0/image-classify/v1/car";
    /**
     * 动物识别
     */
    public static final String  animal="rest/2.0/image-classify/v1/animal";
    /**
     * 植物识别
     */
    public static final String  plant="rest/2.0/image-classify/v1/plant";
    /**
     * logo商标识别
     */
    public static final String  logo="rest/2.0/image-classify/v2/logo";
}
