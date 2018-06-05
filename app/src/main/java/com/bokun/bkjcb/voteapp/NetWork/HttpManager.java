package com.bokun.bkjcb.voteapp.NetWork;

import android.content.Context;

import com.bokun.bkjcb.voteapp.Utils.Constants;
import com.bokun.bkjcb.voteapp.Utils.NetworkUtils;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by BKJCB on 2017/3/16.
 * 网络请求管理
 */

public class HttpManager implements Runnable {
    private Context context;
    private RequestListener listener;
    private Thread currentRequest = null;
    HttpURLConnection conn = null;
    InputStream input = null;
    private HttpRequestVo requestVo;
    private static final String ENCODING = "UTF-8";
    private static final int TIME = 40 * 1000;
    public static final int GET_MOTHOD = 1;
    public static final int POST_MOTHOD = 2;
    /**
     * 1： get请求 2： post请求
     */
    private int requestStatus = 1;

    public HttpManager(Context mContext, RequestListener mListener,
                       HttpRequestVo vo, int mRequeststatus) {

        this.context = mContext;
        this.listener = mListener;
        this.requestVo = vo;
        this.requestStatus = mRequeststatus;
    }

    public HttpManager(Context mContext, RequestListener mListener,
                       HttpRequestVo vo) {
        this.context = mContext;
        this.listener = mListener;
        this.requestVo = vo;
    }

    public void postRequest() {
        requestStatus = 2;
        currentRequest = new Thread(this);
        currentRequest.start();
    }


    /**
     * 对请求的字符串进行编码
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String requestEncodeStr(String requestStr)
            throws UnsupportedEncodingException {
        return URLEncoder.encode(requestStr, ENCODING);
    }

    /**
     * post请求
     *
     * @return
     */
    private void sendPostRequest() {
        HttpTransportSE ht;
        try {

            String NAMESPACE = "http://votejk/";
            String METHOD_NAME = requestVo.methodName;
            //String URL = "http://192.168.137.1:1856/zgzxjkWebService.asmx";
            String URL = Constants.TEST_HTTPURL;
//            Logger.i(URL);

            // 新建 SoapObject 对象
            SoapObject rpc = new SoapObject(NAMESPACE, METHOD_NAME);
            HashMap<String, String> map = requestVo.requestDataMap;
            for (Map.Entry<String, String> entry : map.entrySet()) {
                rpc.addProperty(entry.getKey(), entry.getValue());
            }
            // 创建 HttpTransportSE 对象,并指定 WebService 的 WSDL 文档的 URL
            ht = new HttpTransportSE(URL);
            // 设置 debug 模式
            ht.debug = true;
            // 获得序列化的 envelope
            SoapSerializationEnvelope envelope =
                    new SoapSerializationEnvelope(SoapEnvelope.VER12);
            // 设置 bodyOut 属性的值为 SoapObject 对象 rpc
            envelope.bodyOut = rpc;
            // 指定 webservice 的类型为 dotNet
            envelope.dotNet = true;
            envelope.setOutputSoapObject(rpc);
            // 使用 call 方法调用 WebService 方法
            ht.call(null, envelope);
//            ht.call(null, null);
            // 获取返回结果
            Object result = envelope.bodyIn;

            if (result != null) {
                listener.action(RequestListener.EVENT_GET_DATA_SUCCESS, result);
            } else {
                ht.call(null, envelope);
//                ht.call(null, null);
                result = (SoapObject) envelope.bodyIn;
                listener.action(RequestListener.EVENT_GET_DATA_SUCCESS, result);
            }
        } catch (ProtocolException e) {
            listener.action(RequestListener.EVENT_GET_DATA_EEEOR, null);
        } catch (SocketTimeoutException e) {
            listener.action(RequestListener.EVENT_GET_DATA_EEEOR, null);
        } catch (ConnectException e) {
            listener.action(RequestListener.EVENT_GET_DATA_EEEOR, null);
        } catch (Exception e) {
            e.printStackTrace();
            listener.action(RequestListener.EVENT_NETWORD_EEEOR, null);
        }
    }

    public boolean isRunning() {
        if (currentRequest != null && currentRequest.isAlive()) {
            return true;
        }
        return false;
    }

    /**
     * 取消当前HTTP连接处理
     */
    public void cancelHttpRequest() {
        if (currentRequest != null && currentRequest.isAlive()) {
            if (input != null) {
                try {
                    input.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            input = null;
            if (conn != null) {
                try {
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            conn = null;
            // currentRequest.stop();
            currentRequest = null;
            System.gc();
        }
    }

    public void run() {
        // 0：无网络 1：WIFI 2：CMWAP 3：CMNET
        boolean isEnable = NetworkUtils.isEnable(context);
        if (isEnable) {
                sendPostRequest();
        } else {
            listener.action(RequestListener.EVENT_NOT_NETWORD, null);
        }
    }
}


