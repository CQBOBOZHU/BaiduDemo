package com.zhu.baidu.baidudemo.bean;

/**
 * Created by BoBoZhu on 2017/12/25.
 */

public class ObjectBean {


    /**
     * log_id : 5090608736931131408
     * result : {"width":717,"top":2,"height":718,"left":1}
     */

    private long log_id;
    private ResultBean result;

    public long getLog_id() {
        return log_id;
    }

    public void setLog_id(long log_id) {
        this.log_id = log_id;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * width : 717
         * top : 2
         * height : 718
         * left : 1
         */

        private int width;
        private int top;
        private int height;
        private int left;

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

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getLeft() {
            return left;
        }

        public void setLeft(int left) {
            this.left = left;
        }
    }
}
