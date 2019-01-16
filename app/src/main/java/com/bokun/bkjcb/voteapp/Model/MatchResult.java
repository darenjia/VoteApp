package com.bokun.bkjcb.voteapp.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DengShuai on 2018/6/22.
 * Description :
 */
public class MatchResult {


    /**
     * success : true
     * message : 操作成功
     * data : {"id":"a0838f0121f34a5d94f4adf3c3427eff","pipeliningID":"2","title":"六一儿童节123","content":"关于儿童","holddate":"2018/5/28","remark":"关于儿童","filerurl":"/UpLoad/background/708732.jpg","iscompelete":"0","person":[{"id":"1527237915039","activit_id":"a0838f0121f34a5d94f4adf3c3427eff","person":"张亮","sex":"男","age":"","position":"","ptitle":"关爱儿童们","remark":"男 25","score":"73","fileurl":""},{"id":"1527237954854","activit_id":"a0838f0121f34a5d94f4adf3c3427eff","person":"王璐","sex":"男","age":"","position":"","ptitle":"关注儿童疾病","remark":"女 20","score":"79","fileurl":""}],"judges":[{"id":"14","judgesname":"李柏","activit_id":"a0838f0121f34a5d94f4adf3c3427eff","judges_id":"1","score":"59,58"},{"id":"15","judgesname":"张亮","activit_id":"a0838f0121f34a5d94f4adf3c3427eff","judges_id":"2","score":"80,90"},{"id":"16","judgesname":"望江","activit_id":"a0838f0121f34a5d94f4adf3c3427eff","judges_id":"3","score":"80,90"}]}
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
         * id : a0838f0121f34a5d94f4adf3c3427eff
         * pipeliningID : 2
         * title : 六一儿童节123
         * content : 关于儿童
         * holddate : 2018/5/28
         * remark : 关于儿童
         * filerurl : /UpLoad/background/708732.jpg
         * iscompelete : 0
         * person : [{"id":"1527237915039","activit_id":"a0838f0121f34a5d94f4adf3c3427eff","person":"张亮","sex":"男","age":"","position":"","ptitle":"关爱儿童们","remark":"男 25","score":"73","fileurl":""},{"id":"1527237954854","activit_id":"a0838f0121f34a5d94f4adf3c3427eff","person":"王璐","sex":"男","age":"","position":"","ptitle":"关注儿童疾病","remark":"女 20","score":"79","fileurl":""}]
         * judges : [{"id":"14","judgesname":"李柏","activit_id":"a0838f0121f34a5d94f4adf3c3427eff","judges_id":"1","score":"59,58"},{"id":"15","judgesname":"张亮","activit_id":"a0838f0121f34a5d94f4adf3c3427eff","judges_id":"2","score":"80,90"},{"id":"16","judgesname":"望江","activit_id":"a0838f0121f34a5d94f4adf3c3427eff","judges_id":"3","score":"80,90"}]
         */

        private String id;
        private String pipeliningID;
        private String title;
        private String content;
        private String holddate;
        private String remark;
        private String filerurl;
        private String iscompelete;
        private String type;
        private List<PersonModel> person;
        private List<Judges> judges;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setIscompelete(String iscompelete) {
            this.iscompelete = iscompelete;
        }

        public List<PersonModel> getPerson() {
            return person;
        }

        public void setPerson(List<PersonModel> person) {
            this.person = person;
        }

        public List<Judges> getJudges() {
            return judges;
        }

        public void setJudges(List<Judges> judges) {
            this.judges = judges;
        }

        public static class Person extends PersonModel implements Serializable {
            /**
             * id : 1527237915039
             * activit_id : a0838f0121f34a5d94f4adf3c3427eff
             * person : 张亮
             * sex : 男
             * age :
             * position :
             * ptitle : 关爱儿童们
             * remark : 男 25
             * score : 73
             * fileurl :
             */

            private String id;
            private String activit_id;
            private String person;
            private String sex;
            private String age;
            private String position;
            private String ptitle;
            private String remark;
            private String score;
            private String fileurl;

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

        public static class Judges {
            /**
             * id : 14
             * judgesname : 李柏
             * activit_id : a0838f0121f34a5d94f4adf3c3427eff
             * judges_id : 1
             * score : 59,58
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
}
