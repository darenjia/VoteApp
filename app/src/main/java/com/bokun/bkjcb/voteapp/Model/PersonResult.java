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
    private List<PersonModel> data;

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

    public List<PersonModel> getData() {
        return data;
    }

    public void setData(List<PersonModel> data) {
        this.data = data;
    }

    public static class Person extends PersonModel{
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


    }
}
