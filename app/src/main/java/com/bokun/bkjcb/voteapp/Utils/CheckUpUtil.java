package com.bokun.bkjcb.voteapp.Utils;

import android.content.Context;

import com.bokun.bkjcb.voteapp.HttpService.VersionService;
import com.bokun.bkjcb.voteapp.Model.HttpResult;
import com.bokun.bkjcb.voteapp.Model.VersionInfo;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;

import ezy.boost.update.ICheckAgent;
import ezy.boost.update.IUpdateChecker;
import ezy.boost.update.IUpdateParser;
import ezy.boost.update.UpdateError;
import ezy.boost.update.UpdateInfo;
import ezy.boost.update.UpdateManager;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
            public void check(final ICheckAgent agent, String url) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Constants.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                VersionService service = retrofit.create(VersionService.class);
                try {
                    Response<HttpResult> response = service.checkVersion().execute();
                    if (response.isSuccessful()) {
                        agent.setInfo(response.body().getData());
                    } else {
                        agent.setError(new UpdateError(UpdateError.CHECK_UNKNOWN, ""));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).setManual(isManual).setNotifyId(notifyId).setParser(new IUpdateParser() {
            @Override
            public UpdateInfo parse(String source) throws Exception {
                LogUtil.logI(source);
                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(source);
                VersionInfo version = new Gson().fromJson(element, VersionInfo.class);
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
