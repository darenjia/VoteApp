package com.bokun.bkjcb.voteapp.Utils;

import android.content.Context;

import com.bokun.bkjcb.voteapp.Model.HttpResult;
import com.bokun.bkjcb.voteapp.Model.VersionInfo;
import com.bokun.bkjcb.voteapp.NetWork.JsonParser;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.net.ConnectException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;

import ezy.boost.update.ICheckAgent;
import ezy.boost.update.IUpdateChecker;
import ezy.boost.update.IUpdateParser;
import ezy.boost.update.UpdateError;
import ezy.boost.update.UpdateInfo;
import ezy.boost.update.UpdateManager;

/**
 * Created by DengShuai on 2017/11/2.
 * Description :更新工具类
 */

public class CheckUpUtil {
    private Context context;
    private String mCheckUrl = Constants.TEST_HTTPURL;
    private String mUpdateUrl = Constants.URL_SOFT;

    public CheckUpUtil(Context context) {
        this.context = context;
    }

    public void checkUpadte(boolean isManual, boolean isAutoInstall) {
        UpdateManager.setDebuggable(true);
        UpdateManager.setWifiOnly(false);
//        UpdateManager.check(context);
        check(isManual, true, false, false, false, 998, isAutoInstall);
    }

    void check(boolean isManual, final boolean hasUpdate, final boolean isForce, final boolean isSilent, final boolean isIgnorable, final int
            notifyId, final boolean isAutoInstall) {
        UpdateManager.create(context).setUrl(mCheckUrl).setChecker(new IUpdateChecker() {
            @Override
            public void check(ICheckAgent agent, String url) {
                /*HttpURLConnection connection = null;
                try {
                    connection = (HttpURLConnection) new URL(url).openConnection();
                    connection.setRequestProperty("Accept", "application/json");
                    connection.connect();
                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        agent.setInfo(UpdateUtil.readString(connection.getInputStream()));
                    } else {
                        agent.setError(new UpdateError(UpdateError.CHECK_HTTP_STATUS, "" + connection.getResponseCode()));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    agent.setError(new UpdateError(UpdateError.CHECK_NETWORK_IO));
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }*/
                HttpTransportSE ht;
                try {
                    String NAMESPACE = "http://votejk/";
                    String METHOD_NAME = "Getedition";
                    String URL = url;
                    // 新建 SoapObject 对象
                    SoapObject rpc = new SoapObject(NAMESPACE, METHOD_NAME);
//                    rpc.addProperty("", "");
                    ht = new HttpTransportSE(URL);
                    ht.debug = true;
                    SoapSerializationEnvelope envelope =
                            new SoapSerializationEnvelope(SoapEnvelope.VER12);
                    envelope.bodyOut = rpc;
                    envelope.dotNet = true;
                    envelope.setOutputSoapObject(rpc);
                    ht.call(null, envelope);
                    // 获取返回结果
                    SoapObject result = (SoapObject) envelope.bodyIn;
                    if (result != null) {
                        HttpResult httpResult = JsonParser.getData(result);
                        if (httpResult.isSuccess()) {
                            agent.setInfo(httpResult.getData());
                        } else {
                            agent.setError(new UpdateError(UpdateError.CHECK_UNKNOWN, ""));
                        }
                    } else {
                        agent.setError(new UpdateError(UpdateError.CHECK_HTTP_STATUS, ""));
                    }
                } catch (ProtocolException e) {
                    agent.setError(new UpdateError(UpdateError.CHECK_HTTP_STATUS, "ProtocolException:" + e.getMessage()));
                } catch (SocketTimeoutException e) {
                    agent.setError(new UpdateError(UpdateError.CHECK_HTTP_STATUS, "SocketTimeoutException:" + e.getMessage()));
                } catch (ConnectException e) {
                    agent.setError(new UpdateError(UpdateError.CHECK_HTTP_STATUS, "ConnectException:" + e.getMessage()));
                } catch (Exception e) {
                    e.printStackTrace();
                    agent.setError(new UpdateError(UpdateError.CHECK_HTTP_STATUS, "Exception:" + e.getMessage()));
                }
            }
        }).setManual(isManual).setNotifyId(notifyId).setParser(new IUpdateParser() {
            @Override
            public UpdateInfo parse(String source) throws Exception {
                LogUtil.logI(source);
                VersionInfo version = JsonParser.getVersionResult(source);
                UpdateInfo info = new UpdateInfo();
                info.hasUpdate = !Utils.getVersion(context).equals(version.getEdition());
                info.updateContent = version.getRemark();
//                info.versionCode = 587;
                info.versionName = version.getEdition();
                info.url = mUpdateUrl;
                info.md5 = version.getMd5();
                info.size = Long.parseLong(version.getSize());
                info.isForce = isForce;
                info.isIgnorable = isIgnorable;
                info.isSilent = isSilent;
                info.isAutoInstall = isAutoInstall;
                return info;
            }
        }).check();
    }
}
