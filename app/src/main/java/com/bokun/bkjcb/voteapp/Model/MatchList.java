package com.bokun.bkjcb.voteapp.Model;

import java.util.List;

/**
 * Created by DengShuai on 2018/6/27.
 * Description :
 */
public class MatchList {


    /**
     * success : true
     * message : 操作成功
     * data : [{"userid":"1","activityid":"401d77449d4d46629e40b5eea5b08cb0","pipeliningID":"1","title":"党员活动","content":"6月23日党员活动","date":"2018/6/23","fileurl":"/votejk/UpLoad/background/yuan.jpg","iscomplete":"1"},{"userid":"1","activityid":"28535fc809ed4538a693dc0d74cf748d","pipeliningID":"2","title":"世纪公园团建","content":"团员建设活动","date":"2018/6/29","fileurl":"/votejk/UpLoad/background/er.jpg","iscomplete":"1"},{"userid":"1","activityid":"4b23ccebd4184eb99e5a5a168441d909","pipeliningID":"5","title":"盛夏","content":"明天是夏至","date":"2018/6/15","fileurl":"/votejk/UpLoad/background/pian.jpg","iscomplete":"1"},{"userid":"1","activityid":"58e08222ce2a44d186db6081cfe4c7e8","pipeliningID":"6","title":"1111","content":"12111","date":"2018/6/26","fileurl":"/votejk/UpLoad/background/green.png","iscomplete":"0"}]
     */

    private boolean success;
    private String message;
    private List<Data> data;

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

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Data {
        /**
         * userid : 1
         * activityid : 401d77449d4d46629e40b5eea5b08cb0
         * pipeliningID : 1
         * title : 党员活动
         * content : 6月23日党员活动
         * date : 2018/6/23
         * fileurl : /votejk/UpLoad/background/yuan.jpg
         * iscomplete : 1
         */

        private String userid;
        private String activityid;
        private String pipeliningID;
        private String title;
        private String content;
        private String date;
        private String fileurl;
        private String iscomplete;

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getActivityid() {
            return activityid;
        }

        public void setActivityid(String activityid) {
            this.activityid = activityid;
        }

        public String getPipeliningID() {
            return pipeliningID;
        }

        public void setPipeliningID(String pipeliningID) {
            this.pipeliningID = pipeliningID;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getFileurl() {
            return fileurl;
        }

        public void setFileurl(String fileurl) {
            this.fileurl = fileurl;
        }

        public String getIscomplete() {
            return iscomplete;
        }

        public void setIscomplete(String iscomplete) {
            this.iscomplete = iscomplete;
        }
    }
}
