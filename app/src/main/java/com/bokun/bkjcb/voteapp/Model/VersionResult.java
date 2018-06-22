package com.bokun.bkjcb.voteapp.Model;

/**
 * Created by DengShuai on 2018/6/22.
 * Description :
 */
public class VersionResult {


    /**
     * success : true
     * message : 操作成功
     * data : {"id":"1","edition":"1.0","md5":"sff","size":"1250","remark":"","systime":"2018/6/19 13:29:11"}
     */

    private boolean success;
    private String message;
    private Data data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        /**
         * id : 1
         * edition : 1.0
         * md5 : sff
         * size : 1250
         * remark :
         * systime : 2018/6/19 13:29:11
         */

        private String id;
        private String edition;
        private String md5;
        private String size;
        private String remark;
        private String systime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEdition() {
            return edition;
        }

        public void setEdition(String edition) {
            this.edition = edition;
        }

        public String getMd5() {
            return md5;
        }

        public void setMd5(String md5) {
            this.md5 = md5;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getSystime() {
            return systime;
        }

        public void setSystime(String systime) {
            this.systime = systime;
        }

    }
}
