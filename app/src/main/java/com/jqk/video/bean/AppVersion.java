package com.jqk.video.bean;

/**
 * Created by Administrator on 2017/10/27 0027.
 */

public class AppVersion {

    /**
     * code : 200
     * message : ok！
     * data : {"ver":"12","dowmLink":"http://download.mybaicai.top/video.apk"}
     */

    private int code;
    private String message;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * ver : 12
         * dowmLink : http://download.mybaicai.top/video.apk
         */

        private String ver;
        private String dowmLink;

        public String getVer() {
            return ver;
        }

        public void setVer(String ver) {
            this.ver = ver;
        }

        public String getDowmLink() {
            return dowmLink;
        }

        public void setDowmLink(String dowmLink) {
            this.dowmLink = dowmLink;
        }
    }
}
