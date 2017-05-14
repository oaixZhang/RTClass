package com.xiao.rtclassteacher.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xiao.rtclassteacher.R;
import com.xiao.rtclassteacher.utils.StringUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button loginBtn;
    private TextView registerTv;
    private boolean isLogin = true;

    private Button stuBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginBtn = (Button) findViewById(R.id.btn_login);
        registerTv = (TextView) findViewById(R.id.tv_for_register);
        stuBtn = (Button) findViewById(R.id.btn_stu);
        loginBtn.setOnClickListener(this);
        registerTv.setOnClickListener(this);
        stuBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
//                Handler handler = new Handler() {
//                    @Override
//                    public void handleMessage(Message msg) {
//                        super.handleMessage(msg);
//
//                    }
//                };
//                register(handler);

                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_for_register:
                if (isLogin) {
                    loginBtn.setText("点击注册");
                    registerTv.setText("已有账号，点击登录");
                    isLogin = false;
                } else {
                    loginBtn.setText("点击登录");
                    registerTv.setText("还没有账号？点击注册");
                    isLogin = true;
                }
                break;
            case R.id.btn_stu:
                Intent stuIntent = new Intent(LoginActivity.this, MainActivity.class);
                stuIntent.putExtra("isTeacher", false);
                startActivity(stuIntent);
                break;
        }

    }

    private void register(final Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                String urlString = StringUtil.baseUrl + "queryUser/3";
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.connect();
                    int code = con.getResponseCode();
                    System.out.println("response code: " + code);
                    System.out.println("response msg: " + con.getResponseMessage());
                    if (code == 200) {
                        InputStream is = con.getInputStream();
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024];
                        int len = 0;
                        while ((len = is.read(buffer)) != -1) {
                            bos.write(buffer, 0, len);
                        }
                        is.close();
                        bos.close();
                        System.out.println("response body: " + bos.toString());
                        Message msg = new Message();
                        msg.obj = bos.toString();
                        handler.sendMessage(msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

}
