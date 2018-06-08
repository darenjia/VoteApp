package com.bokun.bkjcb.voteapp.Model;

/**
 * Created by DengShuai on 2018/6/8.
 * Description :
 */
public class PersonResult {

    /**
     * id : 1527495569512
     * activit_id : 4c498f3a4d6f44c686beccbbb725670a
     * person : 诸葛青
     * ptitle : 白鹤亮翅
     * remark : null
     * score : 80
     */

    private String id;
    private String activit_id;
    private String person;
    private String ptitle;
    private Object remark;
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

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
        this.remark = remark;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
