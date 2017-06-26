package com.jikexueyuan.learnusexutils3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 学习使用xUtils3
 */

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewInject(R.id.tv)
    public TextView tv;

    @ViewInject(value = R.id.tv1_include, parentId = R.layout.layout_include)
    public TextView tv1_include;

    @ViewInject(R.id.tv2_include)
    public TextView tv2_include;
    //tv2_include虽然是在include布局中,但是它也在activity_main布局的视图树中,
    //所以不指定父id也能被找到, 实际上,注解中的parentId这个属性是多余的

    //为按钮添加点击事件
    @ViewInject(R.id.button)
    public Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }

    /**
     * 用注解的方式为按钮添加点击事件，方法声明必须为private
     * type默认View.OnClickListener.class，故此处可以简化不写，@Event(R.id.bt_main)
     */
    @Event(type = View.OnClickListener.class, value = R.id.button)
    private void testInjectOnClick(View v) {
        Toast.makeText(this, "点击了button", Toast.LENGTH_SHORT).show();
    }

    /**
     * 长按事件
     */
    @Event(type = View.OnLongClickListener.class, value = R.id.button)
    private boolean textOnLongClick(View v) {
        Toast.makeText(this, "长按点击了button", Toast.LENGTH_SHORT).show();
        return true;
    }

    /**
     * xUtils3网络模块的使用
     */


    /**
     * GET请求
     */
    String url = "http://www.baidu.com";

    @Event(R.id.get)
    private void get(View v) {
        //请求参数
        RequestParams params = new RequestParams(url);
        //添加请求参数
        params.addQueryStringParameter("username", "abc");
        params.addQueryStringParameter("password", "123");
        //Callback.CommonCallback<String>String-->请求成功后返回的数据类型
        Callback.Cancelable cancelable = x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                //请求成功回调，result:返回的结果
                System.out.println("Get: " + result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                //请求发生异常回调
                //ex--->异常信息
                //isOnCallback--->true说明异常信息来源于回调方法(onSuccess,onCancelled,onFinished)
                //isOnCallback--->false说明不是来源于回调方法
            }

            @Override
            public void onCancelled(CancelledException cex) {
                //主动调用取消请求后回调
            }

            @Override
            public void onFinished() {
                //不管成功失败都会回调
            }
        });
//        cancelable.cancel();//将会调用onCancelled()
    }

    /**
     * POST请求
     */
    @Event(R.id.post)
    private void post(View v) {
        RequestParams params = new RequestParams(url);
        //将请求参数添加至body中
        //根据请求方式不同将参数添加至body或链接地址后面
        params.addBodyParameter("username", "abc");
        params.addBodyParameter("password", "123");
        //为当前请求添加一个头
        params.addHeader("head", "android");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("POST: " + result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 使用其他请求方式
     */
    @Event(R.id.other)
    private void other(View v) {
        RequestParams params = new RequestParams(url);
        x.http().request(HttpMethod.GET, params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("OTHER: " + result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
