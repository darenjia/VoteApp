package com.bokun.bkjcb.voteapp.Model;

/**
 * Created by DengShuai on 2018/6/19.
 * Description :
 */
public class VersionInfo {

    /**
     * id : 1
     * edition : sdc
     * md5 : sff
     * size : 125
     * remark : ddf
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
