package com.bokun.bkjcb.voteapp.Utils;

import com.bokun.bkjcb.voteapp.Model.User;

/**
 * Created by BKJCB on 2017/3/16.
 * 系统常量
 */

public class Constants {

    public final static int NETWORK_WIFI = 1;
    public final static int NETWORK_MOBILE = 2;
    public final static boolean ISLOG = true;
    public static User user;
    /*
     *正式IP地址
     * */

    public static final String URL_SOFT = "http://101.231.52.50:2047/votejk/voteapp.apk";

    /*
     * 测试IP地址
     * */
    public static String TEST_HTTPURL = "http://192.168.100.4:2333/WebService.asmx";
    public static String IMGURL = "http://101.231.52.50:2047";
    //public static String IMGURL = "http://192.168.100.192:80";
    public static final String URL_CHECK = "http://192.168.100.136:8080/zgzxjkWebService.asmx";
    //    public static final String URL_SOFT = "http://101.231.52.50:8081/Aqgl/xiazaiapp";
    public static final String BASE_URL = "http://101.231.52.50:2046/vote/";
    //public static final String BASE_URL = "http://192.168.100.192:2046/vote/";

}
