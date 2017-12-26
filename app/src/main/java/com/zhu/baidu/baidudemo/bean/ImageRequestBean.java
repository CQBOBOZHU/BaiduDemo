package com.zhu.baidu.baidudemo.bean;

import java.util.List;

/**
 * Created by BoBoZhu on 2017/12/25.
 */

public class ImageRequestBean {

    long log_id;
    List<ResultBean> result;
    String name;

    String score;
    int error_code;
    String error_msg;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    @Override
    public String toString() {
        return "ImageRequestBean{" +
                "log_id=" + log_id +
                ", name='" + name + '\'' +
                ", score='" + score + '\'' +
                '}';
    }

    public long getLog_id() {
        return log_id;
    }

    public void setLog_id(long log_id) {
        this.log_id = log_id;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    /**
     * log_id : 895582300
     * result : {"width":486,"top":76,"left":134,"height":394}
     */


    public static class ResultBean {
        /**
         * width : 486
         * top : 76
         * left : 134
         * height : 394
         */

        private int width;
        private int top;
        private int left;
        private int height;

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getTop() {
            return top;
        }

        public void setTop(int top) {
            this.top = top;
        }

        public int getLeft() {
            return left;
        }

        public void setLeft(int left) {
            this.left = left;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

//          "calorie": "0",
//                  "has_calorie": true,
//                  "name": "非菜",
//                  "probability": "0.999987"
//         "score": 1,
//                 "name": "非车类"

        String calorie;
        String name;
        float probability;
        float score;
        boolean has_calorie;

        public String getCalorie() {
            return calorie;
        }

        public void setCalorie(String calorie) {
            this.calorie = calorie;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public float getProbability() {
            return probability;
        }

        public void setProbability(float probability) {
            this.probability = probability;
        }

        public float getScore() {
            return score;
        }

        public void setScore(float score) {
            this.score = score;
        }

        public boolean isHas_calorie() {
            return has_calorie;
        }

        public void setHas_calorie(boolean has_calorie) {
            this.has_calorie = has_calorie;
        }
    }
}
