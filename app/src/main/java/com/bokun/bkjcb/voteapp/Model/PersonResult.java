package com.bokun.bkjcb.voteapp.Model;

import java.util.List;

/**
 * Created by DengShuai on 2018/6/8.
 * Description :
 */
public class PersonResult {


    /**
     * success : true
     * message : 操作成功
     * data : [{"id":"1527237954854","activit_id":"a0838f0121f34a5d94f4adf3c3427eff","person":"王璐","sex":null,"age":null,"position":null,"ptitle":"关注儿童疾病","remark":null,"score":"79","fileurl":null},{"id":"1527237915039","activit_id":"a0838f0121f34a5d94f4adf3c3427eff","person":"张亮","sex":null,"age":null,"position":null,"ptitle":"关爱儿童们","remark":null,"score":"73","fileurl":null}]
     */

    private boolean success;
    private String message;
    private List<Person> data;

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

    public List<Person> getData() {
        return data;
    }

    public void setData(List<Person> data) {
        this.data = data;
    }

    public static class Person {
        /**
         * id : 1527237954854
         * activit_id : a0838f0121f34a5d94f4adf3c3427eff
         * person : 王璐
         * sex : null
         * age : null
         * position : null
         * ptitle : 关注儿童疾病
         * remark : null
         * score : 79
         * fileurl : null
         */

        private String id;
        private String activit_id;
        private String person;
        private Object sex;
        private Object age;
        private Object position;
        private String ptitle;
        private Object remark;
        private String score;
        private Object fileurl;

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

        public Object getSex() {
            return sex;
        }

        public void setSex(Object sex) {
            this.sex = sex;
        }

        public Object getAge() {
            return age;
        }

        public void setAge(Object age) {
            this.age = age;
        }

        public Object getPosition() {
            return position;
        }

        public void setPosition(Object position) {
            this.position = position;
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

        public Object getFileurl() {
            return fileurl;
        }

        public void setFileurl(Object fileurl) {
            this.fileurl = fileurl;
        }
    }
}
