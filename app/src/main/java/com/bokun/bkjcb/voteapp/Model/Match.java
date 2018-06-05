package com.bokun.bkjcb.voteapp.Model;

import java.io.Serializable;
import java.util.List;

public class Match {


    /**
     * id : 4c498f3a4d6f44c686beccbbb725670a
     * PipeliningID : 1
     * title : 没什么主题
     * content : 随便写的
     * holddate : 2018/6/4
     * remark : 没有备注
     * judges : 张亮,李柏
     * filerurl : /UpLoad/background/004.jpg.jpg
     * person : [{"id":"1527495539159","activit_id":"4c498f3a4d6f44c686beccbbb725670a","person":"林天骄","ptitle":"参赛标题","remark":"123","score":"55"},{"id":"1527495569512","activit_id":"4c498f3a4d6f44c686beccbbb725670a","person":"诸葛青","ptitle":"白鹤亮翅","remark":"123","score":"80"}]
     */

    private String id;
    private String PipeliningID;
    private String title;
    private String content;
    private String holddate;
    private String remark;
    private String judges;
    private String filerurl;
    private List<PersonBean> person;

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

    public String getJudges() {
        return judges;
    }

    public void setJudges(String judges) {
        this.judges = judges;
    }

    public String getFilerurl() {
        return filerurl;
    }

    public void setFilerurl(String filerurl) {
        this.filerurl = filerurl;
    }

    public List<PersonBean> getPerson() {
        return person;
    }

    public void setPerson(List<PersonBean> person) {
        this.person = person;
    }

    public static class PersonBean  implements Serializable{
        /**s
         * id : 1527495539159
         * activit_id : 4c498f3a4d6f44c686beccbbb725670a
         * person : 林天骄
         * ptitle : 参赛标题
         * remark : 123
         * score : 55
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

}
