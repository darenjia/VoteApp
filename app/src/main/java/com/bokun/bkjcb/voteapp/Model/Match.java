package com.bokun.bkjcb.voteapp.Model;

import java.io.Serializable;
import java.util.List;

public class Match {


    /**
     * id : a0838f0121f34a5d94f4adf3c3427eff
     * PipeliningID : 2
     * title : 六一儿童节123
     * content : 关于儿童
     * holddate : 2018/5/28
     * remark : 关于儿童
     * filerurl : 12
     * iscompelete : 0
     * person : [{"id":"1527237915039","activit_id":"a0838f0121f34a5d94f4adf3c3427eff","person":"张亮","ptitle":"关爱儿童们","remark":"男 25","score":""},{"id":"1527237954854","activit_id":"a0838f0121f34a5d94f4adf3c3427eff","person":"王璐","ptitle":"关注儿童疾病","remark":"女 20","score":""}]
     * judges : [{"id":"14","judgesname":"李柏","activit_id":"a0838f0121f34a5d94f4adf3c3427eff","judges_id":"1","score":"5958"},{"id":"15","judgesname":"张亮","activit_id":"a0838f0121f34a5d94f4adf3c3427eff","judges_id":"2","score":""},{"id":"16","judgesname":"望江","activit_id":"a0838f0121f34a5d94f4adf3c3427eff","judges_id":"3","score":""}]
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

    public static class PersonBean implements Serializable{
        /**
         * id : 1527237915039
         * activit_id : a0838f0121f34a5d94f4adf3c3427eff
         * person : 张亮
         * ptitle : 关爱儿童们
         * remark : 男 25
         * score :
         */

        private String id;
        private String activit_id;
        private String person;
        private String ptitle;
        private String remark;
        private String score;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getActivit_id() {
            return activit_id;
        }

        public void setActivit_id(String activit_id) {
            this.activit_id = activit_id;
        }

        public String getPerson() {
            return person;
        }

        public void setPerson(String person) {
            this.person = person;
        }

        public String getPtitle() {
            return ptitle;
        }

        public void setPtitle(String ptitle) {
            this.ptitle = ptitle;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }
    }

    public static class JudgesBean {
        /**
         * id : 14
         * judgesname : 李柏
         * activit_id : a0838f0121f34a5d94f4adf3c3427eff
         * judges_id : 1
         * score : 5958
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
