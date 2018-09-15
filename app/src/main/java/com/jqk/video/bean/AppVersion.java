package com.jqk.video.bean;

/**
 * Created by Administrator on 2017/10/27 0027.
 */

public class AppVersion {

    private String message;
    private VersionBean version;
    private String status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public VersionBean getVersion() {
        return version;
    }

    public void setVersion(VersionBean version) {
        this.version = version;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class VersionBean {
        /**
         * androidVersion : 6
         * androidUrl : http://www.stardaymart.com/stardaymart.apk
         */

        private String androidVersion;
        private String androidUrl;

        public String getAndroidVersion() {
            return androidVersion;
        }

        public void setAndroidVersion(String androidVersion) {
            this.androidVersion = androidVersion;
        }

        public String getAndroidUrl() {
            return androidUrl;
        }

        public void setAndroidUrl(String androidUrl) {
            this.androidUrl = androidUrl;
        }
    }
}
