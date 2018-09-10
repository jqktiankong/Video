package com.jqk.video.bean;

public class ActiviCode {

    /**
     * code : 201
     * message : 当前账户未过期，请勿重复激活！
     * data : null
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
         * overDate : 2018-09-12
         * phone : 15690846231
         * isOver : 0
         * isAc : 1
         */

        private String overDate;
        private String phone;
        private int isOver;
        private int isAc;

        public String getOverDate() {
            return overDate;
        }

        public void setOverDate(String overDate) {
            this.overDate = overDate;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getIsOver() {
            return isOver;
        }

        public void setIsOver(int isOver) {
            this.isOver = isOver;
        }

        public int getIsAc() {
            return isAc;
        }

        public void setIsAc(int isAc) {
            this.isAc = isAc;
        }
    }
}
