package com.jikexueyuan.learnusexutils3;

import android.app.Application;

import org.xutils.x;

/**
 * Created by admin on 2017/6/26.
 */

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //对xUtils进行初始化
        x.Ext.init(this);
        //是否是开发、调试模式
        x.Ext.setDebug(BuildConfig.DEBUG);//是否输出debug日志，开启debug会影响性能

        //之后在manifests里注册MyApp
    }
}
