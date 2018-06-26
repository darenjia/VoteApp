package com.bokun.bkjcb.voteapp.Model;

import java.io.Serializable;
import java.util.List;

public class Match {


    /**
     * id : 94607fe5019f443a9d8e12d3718a00ed
     * PipeliningID : 14
     * title : 123
     * content : 123
     * holddate : 2018/6/15
     * remark :
     * filerurl :
     * iscompelete : 0
     * person : [{"id":"1529049333490","activit_id":"94607fe5019f443a9d8e12d3718a00ed","person":"巴奴","sex":"男","age":"30","position":"职员","ptitle":"阿萨德","remark":"","score":"","fileurl":""},{"id":"1529047806970","activit_id":"94607fe5019f443a9d8e12d3718a00ed","person":"刘欢","sex":"男","age":"26","position":"职员","ptitle":"123","remark":"","score":"","fileurl":"/UpLoad/Headportrait/20183015033017099.png"}]
     * judges : [{"id":"24","judgesname":"李柏","activit_id":"94607fe5019f443a9d8e12d3718a00ed","judges_id":"1","score":""}]
     */

    private String id;
    private String PipeliningID;
    private String title;
    private String content;
    private String holddate;
    private String remark;
    private String filerurl;
    private String iscompelete;
    private List<PersonBean> person;
    private List<JudgesBean> judges;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPipeliningID() {
        return PipeliningID;
    }

    public void setPipeliningID(String PipeliningID) {
        this.PipeliningID = PipeliningID;
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

    public String getHolddate() {
        return holddate;
    }

    public void setHolddate(String holddate) {
        this.holddate = holddate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFilerurl() {
        return filerurl;
    }

    public void setFilerurl(String filerurl) {
        this.filerurl = filerurl;
    }

    public String getIscompelete() {
        return iscompelete;
    }

    public void setIscompelete(String iscompelete) {
        this.iscompelete = iscompelete;
    }

    public List<PersonBean> getPerson() {
        return person;
    }

    public void setPerson(List<PersonBean> person) {
        this.person = person;
    }

    public List<JudgesBean> getJudges() {
        return judges;
    }

    public void setJudges(List<JudgesBean> judges) {
        this.judges = judges;
    }

    public static class PersonBean extends PersonModel implements Serializable {
        /**
         * id : 1529049333490
         * activit_id : 94607fe5019f443a9d8e12d3718a00ed
         * person : 巴奴
         * sex : 男
         * age : 30
         * position : 职员
         * ptitle : 阿萨德
         * remark :
         * score :
         * fileurl :
         */


    }

    public static class JudgesBean {
        /**
         * id : 24
         * judgesname : 李柏
         * activit_id : 94607fe5019f443a9d8e12d3718a00ed
         * judges_id : 1
         * score :
         */

        private String id;
        private String judgesname;
        private String activit_id;
        private String judges_id;
        private String score;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getJudgesname() {
            return judgesname;
        }

        public void setJudgesname(String judgesname) {
            this.judgesname = judgesname;
        }

        public String getActivit_id() {
            return activit_id;
        }

        public void setActivit_id(String activit_id) {
            this.activit_id = activit_id;
        }

        public String getJudges_id() {
            return judges_id;
        }

        public void setJudges_id(String judges_id) {
            this.judges_id = judges_id;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }
    }
}
