package com.bokun.bkjcb.voteapp.Model;

import java.io.Serializable;

/**
 * Created by DengShuai on 2018/6/25.
 * Description :
 */
public class PersonModel implements Serializable{
    protected String id;
    protected String activit_id;
    protected String person;
    protected String sex;
    protected String age;
    protected String position;
    protected String ptitle;
    protected String remark;
    protected String score;
    protected String fileurl;

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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

    public String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }
}
