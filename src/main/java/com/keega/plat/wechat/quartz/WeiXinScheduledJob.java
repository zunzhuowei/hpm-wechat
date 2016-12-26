package com.keega.plat.wechat.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by zun.wei on 2016/12/6.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public class WeiXinScheduledJob extends QuartzJobBean {

    private RefreshAccessTokenTask refreshAccessTokenTask;

    public void setRefreshAccessTokenTask(RefreshAccessTokenTask refreshAccessTokenTask) {
        this.refreshAccessTokenTask = refreshAccessTokenTask;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            //执行刷新access token
            refreshAccessTokenTask.refreshAccessToken();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

}
